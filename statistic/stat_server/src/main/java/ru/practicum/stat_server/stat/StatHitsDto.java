package ru.practicum.stat_server.stat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatHitsDto {

    private String app;
    private String uri;
    private Long hits;
}
