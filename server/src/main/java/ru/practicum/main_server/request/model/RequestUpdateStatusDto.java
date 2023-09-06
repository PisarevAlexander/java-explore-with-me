package ru.practicum.main_server.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateStatusDto {

    private List<Long> requestIds;
    private RequestStatus status;
}
