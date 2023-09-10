package ru.practicum.main_server.request.service;

import ru.practicum.main_server.request.model.RequestDto;

import java.util.List;

public interface RequestService {

    List<RequestDto> findAllByUser(Long userID);

    RequestDto create(Long userId, Long eventId);

    RequestDto update(Long userId, Long requestId);
}
