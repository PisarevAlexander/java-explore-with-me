package explore_with_me.main_server.request.model;

/**
 * Request mapper
 */

public class RequestMapper {

    /**
     * Request to request dto.
     * @param request the request
     * @return the request dto
     */

    public static RequestDto toRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setCreated(request.getCreated());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setStatus(request.getStatus().toString());
        return requestDto;
    }
}