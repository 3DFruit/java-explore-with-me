package ru.practicum.explorewithme.model.category;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {
    private Long id;
    @NotBlank
    private String name;
}
