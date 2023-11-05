package explore_with_me.stat_server.stat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stat hits dto object
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatHitsDto {

    private String app;
    private String uri;
    private Long hits;
}