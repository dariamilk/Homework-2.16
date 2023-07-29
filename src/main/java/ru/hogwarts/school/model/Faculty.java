package ru.hogwarts.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Faculty {
    private Long id;
    private String name;
    private String color;
}
