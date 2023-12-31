package explore_with_me.main_server.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Request update dto object
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateDto {

    private List<RequestDto> confirmedRequests = new ArrayList<>();
    private List<RequestDto> rejectedRequests = new ArrayList<>();
}