package explore_with_me.main_server.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import explore_with_me.main_server.request.model.RequestDto;
import explore_with_me.main_server.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@Slf4j
public class PrivateRequestController {

    private final RequestService requestService;

    @GetMapping
    public List<RequestDto> findAllByUser(@PathVariable Long userId) {
        log.info("Get requests by userId={}", userId);
        return requestService.findAllByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Create request by userId={} for eventId={}", userId, eventId);
        return requestService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto update(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Cancel request by userId={} for eventId={}", userId, requestId);
        return requestService.update(userId, requestId);
    }
}
