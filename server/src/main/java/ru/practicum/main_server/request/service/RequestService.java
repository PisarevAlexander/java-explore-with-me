package ru.practicum.main_server.request.service;

import ru.practicum.main_server.request.model.Request;

import java.util.List;

public interface RequestService {

    List<Request> findAllByUser(Long userID);

    Request create(Long userId, Long eventId);

    Request update(Long userId, Long requestId);
}
