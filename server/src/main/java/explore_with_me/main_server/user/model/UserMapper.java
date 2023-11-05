package explore_with_me.main_server.user.model;

/**
 * User mapper
 */

public class UserMapper {

    /**
     * User DTO to User
     * @param userDto the user dto
     * @return the user
     */

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        return user;
    }
}