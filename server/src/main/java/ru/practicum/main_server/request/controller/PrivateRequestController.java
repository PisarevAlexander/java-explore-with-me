package ru.practicum.main_server.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.request.model.Request;
import ru.practicum.main_server.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@Slf4j
public class PrivateRequestController {

    private final RequestService requestService;

    @GetMapping
    public List<Request> findAllByUser(@PathVariable Long userId) {
        log.info("Get requests by userId={}", userId);
        return requestService.findAllByUser(userId);
    }

    @PostMapping
    public Request create(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Create request by userId={} for eventId={}", userId, eventId);
        return requestService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public Request update(@PathVariable Long userId, @RequestParam Long requestId) {
        log.info("Cancel request by userId={} for eventId={}", userId, requestId);
        return requestService.update(userId, requestId);
    }
}
