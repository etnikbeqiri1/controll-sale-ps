package al.brain.saleforce.restsaleforce.controllers;

import al.brain.saleforce.restsaleforce.dtos.SessionResponseDTO;
import al.brain.saleforce.restsaleforce.helpers.ApiResponse;
import al.brain.saleforce.restsaleforce.models.Session;
import al.brain.saleforce.restsaleforce.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Session>>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();

        LocalDateTime currentTime = LocalDateTime.now();

        for (Session session : sessions) {
            LocalDateTime startTime = session.getStartedAt();
            int durationInMinutes = (int) Duration.between(startTime, currentTime).toMinutes();
            session.setDuration(durationInMinutes);
        }
        ApiResponse<List<Session>> response = new ApiResponse<>(true, "Sessions retrieved successfully", sessions, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Session>> getSessionById(@PathVariable UUID id) {
        Session session = sessionService.getSessionById(id);
        ApiResponse<Session> response = new ApiResponse<>(true, "Session retrieved successfully", session, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unpaid")
    public ResponseEntity<ApiResponse<List<Session>>> getUnpaidSessions() {
        List<Session> unpaidSessions = sessionService.getUnpaidSessions();
        ApiResponse<List<Session>> response = new ApiResponse<>(true, "Unpaid sessions retrieved successfully", unpaidSessions, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<List<Session>>> getSessionsByItemId(@PathVariable Integer itemId) {
        List<Session> sessionsByItemId = sessionService.getSessionsByItemId(itemId);
        ApiResponse<List<Session>> response = new ApiResponse<>(true, "Sessions retrieved successfully", sessionsByItemId, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/started-after")
    public ResponseEntity<ApiResponse<List<Session>>> getSessionsStartedAfter(@RequestParam("dateTime") LocalDateTime dateTime) {
        List<Session> sessionsStartedAfter = sessionService.getSessionsStartedAfter(dateTime);
        ApiResponse<List<Session>> response = new ApiResponse<>(true, "Sessions retrieved successfully", sessionsStartedAfter, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ended-before")
    public ResponseEntity<ApiResponse<List<Session>>> getSessionsEndedBefore(@RequestParam("dateTime") LocalDateTime dateTime) {
        List<Session> sessionsEndedBefore = sessionService.getSessionsEndedBefore(dateTime);
        ApiResponse<List<Session>> response = new ApiResponse<>(true, "Sessions retrieved successfully", sessionsEndedBefore, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/{priceId}")
    public ResponseEntity<ApiResponse<String>> startSession(@PathVariable Integer id, @PathVariable Integer priceId) {
        String createdSession = sessionService.startSession(id, priceId);
        ApiResponse<String> response = new ApiResponse<>(true, "Trying to allocate session item", createdSession, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSession(@PathVariable UUID id) {
        sessionService.deleteSession(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Session deleted successfully", null, LocalDateTime.now());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/mark-paid")
    public ResponseEntity<ApiResponse<Session>> markSessionAsPaid(@PathVariable UUID id) {
        Session markedAsPaidSession = sessionService.markSessionAsPaid(id);
        ApiResponse<Session> response = new ApiResponse<>(true, "Session marked as paid", markedAsPaidSession, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/extend-duration")
    public ResponseEntity<ApiResponse<Session>> extendSessionDuration(@PathVariable UUID id,
                                                                      @RequestParam("additionalDuration") int additionalDuration) {
        Session extendedSession = sessionService.extendSessionDuration(id, additionalDuration);
        ApiResponse<Session> response = new ApiResponse<>(true, "Session duration extended successfully", extendedSession, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/end")
    public ResponseEntity<ApiResponse<Float>> endSession(@PathVariable UUID id) {
        Session endedSession = sessionService.endSession(id);

        Float itm = 0.01F;
        //todo save as history
        ApiResponse<Float> response = new ApiResponse<>(true, "Session ended successfully", itm, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<ApiResponse<SessionResponseDTO>> getPreviewSessionById(@PathVariable UUID id) {
        SessionResponseDTO sessionResponseDTO = sessionService.getSessionPreview(id);

        ApiResponse<SessionResponseDTO> response = new ApiResponse<>(true, "Session retrieved successfully", sessionResponseDTO, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
