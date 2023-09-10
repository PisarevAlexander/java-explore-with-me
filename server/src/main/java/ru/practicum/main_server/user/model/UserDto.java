package ru.practicum.main_server.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Size(min = 2, message = "name size to short")
    @Size(max = 250, message = "name size to long")
    private String name;

    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "email is not valid")
    @Size(min = 6, message = "name size to short")
    @Size(max = 254, message = "name size to long")
    private String email;
}
