package ru.hogwarts.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDTO {
    private Long id;
    private URL avatarLink;
    private Long studentId;
    private String studentName;
}
