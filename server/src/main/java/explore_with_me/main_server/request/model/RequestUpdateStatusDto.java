package explore_with_me.main_server.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Request update status dto object
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateStatusDto {

    @NotNull
    private List<Long> requestIds;
    @NotNull
    private RequestStatus status;
}