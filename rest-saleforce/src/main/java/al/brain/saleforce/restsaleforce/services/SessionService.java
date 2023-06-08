package al.brain.saleforce.restsaleforce.services;

import al.brain.saleforce.restsaleforce.dtos.SessionResponseDTO;
import al.brain.saleforce.restsaleforce.exceptions.SessionNotFoundException;
import al.brain.saleforce.restsaleforce.models.Item;
import al.brain.saleforce.restsaleforce.models.Pricing;
import al.brain.saleforce.restsaleforce.models.Session;
import al.brain.saleforce.restsaleforce.repositories.ItemRepository;
import al.brain.saleforce.restsaleforce.repositories.PricingRepository;
import al.brain.saleforce.restsaleforce.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ItemRepository itemRepository;
    private final PricingRepository pricingRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, ItemRepository itemRepository, PricingRepository pricingRepository) {
        this.sessionRepository = sessionRepository;
        this.itemRepository = itemRepository;
        this.pricingRepository = pricingRepository;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException("Session not found with ID: " + id));
    }

    public SessionResponseDTO getSessionPreview(UUID id) {
        Session session = this.getSessionById(id);
        double pricePerHour = session.getPricing().getPrice();
        LocalDateTime startTime = session.getStartedAt();
        LocalDateTime currentTime = LocalDateTime.now();
        if (session.isPaid()) {
            currentTime = session.getEndedAt();
        }
        int durationInMinutes = (int) Duration.between(startTime, currentTime).toMinutes();
        double price = (double) durationInMinutes / 60 * pricePerHour;

        double minPrice = session.getPricing().getStartPrice();

        double roundedPrice = (Math.ceil(price / 0.05) * 0.05); // Round up to the nearest 0.05 increment
        price = Math.max(roundedPrice, minPrice); // Ensure the price is at least the minimum price

        DecimalFormat df = new DecimalFormat("#.##");
        double formattedPrice = Double.parseDouble(df.format(price));

        return new SessionResponseDTO(
                session.getId(),
                durationInMinutes,
                session.getPricing(),
                session.getItem().getId(),
                session.getItem().getName(),
                session.getItem().getState(),
                session.isPaid(),
                formattedPrice,
                startTime,
                session.getEndedAt()
        );
    }

    public List<Session> getUnpaidSessions() {
        return sessionRepository.findByPaidFalse();
    }

    public List<Session> getSessionsByItemId(Integer itemId) {
        return sessionRepository.findByItemId(itemId);
    }

    public List<Session> getSessionsStartedAfter(LocalDateTime dateTime) {
        return sessionRepository.findByStartedAtAfter(dateTime);
    }

    public List<Session> getSessionsEndedBefore(LocalDateTime dateTime) {
        return sessionRepository.findByEndedAtBefore(dateTime);
    }

    public String startSession(Integer id,Integer priceId) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        Optional<Pricing> optionalPricing = pricingRepository.findById(id);

        if (optionalItem.isPresent() &&  optionalPricing.isPresent()) {
            Item item = optionalItem.get();
            Pricing pricing = optionalPricing.get();

            if (item.getEnabled() == 1 && item.getState() == 0 && pricing.getEnabled() == 1) {
                Session session = new Session();
                session.setItem(item);
                session.setPricing(pricing);
                sessionRepository.save(session);

                item.setState(1);
                itemRepository.save(item);
                return "Session Started Successfully";
            } else {
                return "Session is in use!";
            }
        } else {
            return "Item not found";
        }
    }

    public Session updateSession(UUID id, Session sessionDetails) {
        Session session = getSessionById(id);
        session.setPaid(sessionDetails.isPaid());
        session.setItem(sessionDetails.getItem());
        return sessionRepository.save(session);
    }

    public void deleteSession(UUID id) {
        Session session = getSessionById(id);
        sessionRepository.delete(session);
    }

    public Session markSessionAsPaid(UUID id) {
        Session session = getSessionById(id);
        session.setPaid(true);
        return sessionRepository.save(session);
    }

    public Session extendSessionDuration(UUID id, int additionalDuration) {
        Session session = getSessionById(id);
        session.setDuration(session.getDuration() + additionalDuration);
        return sessionRepository.save(session);
    }

    public Session endSession(UUID id) {
        Session session = getSessionById(id);
        LocalDateTime now = LocalDateTime.now();
        session.setEndedAt(now);
        int durationInSeconds = (int) java.time.Duration.between(session.getStartedAt(), now).getSeconds();
        session.setDuration((int)durationInSeconds / 60);
        session.setPaid(true);

        Item item = session.getItem();
        if (item != null) {
            item.setState(0);
        }

        sessionRepository.save(session);
        return session;
    }

}
