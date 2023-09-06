package ru.practicum.main_server.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateDto {

    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}
