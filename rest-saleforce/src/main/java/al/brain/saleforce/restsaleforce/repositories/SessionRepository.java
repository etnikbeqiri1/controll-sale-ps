package al.brain.saleforce.restsaleforce.repositories;

import al.brain.saleforce.restsaleforce.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByPaidFalse();

    List<Session> findByItemId(Integer itemId);

    @Query("SELECT s FROM Session s WHERE s.startedAt > :dateTime")
    List<Session> findByStartedAtAfter(LocalDateTime dateTime);

    @Query("SELECT s FROM Session s WHERE s.endedAt < :dateTime")
    List<Session> findByEndedAtBefore(LocalDateTime dateTime);
}