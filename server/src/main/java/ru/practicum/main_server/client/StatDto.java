package ru.practicum.main_server.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {

    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
