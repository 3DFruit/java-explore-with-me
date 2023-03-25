package ru.practicum.explorewithme.model.category;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class NewCategoryDto {
    @NotBlank
    private String name;
}
