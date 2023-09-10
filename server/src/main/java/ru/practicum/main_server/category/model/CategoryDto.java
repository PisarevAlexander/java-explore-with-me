package ru.practicum.main_server.category.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotBlank
    @Size(min = 1, message = "name size to short")
    @Size(max = 50, message = "name size to long")
    private String name;
}
