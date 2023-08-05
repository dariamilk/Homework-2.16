package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByNameIgnoreCase(String name);
    List<Faculty> findAllByColorIgnoreCase(String color);
    List<Faculty> findAllByColorLikeIgnoreCaseOrNameIgnoreCase(String color, String name);
}
