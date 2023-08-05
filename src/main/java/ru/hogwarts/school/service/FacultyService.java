package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyAlreadyExistsException;
import ru.hogwarts.school.exceptions.NoSuchFacultyException;
import ru.hogwarts.school.exceptions.NoSuchStudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService (FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty add(Faculty faculty) {
        if (!facultyRepository.findAllByNameIgnoreCase(faculty.getName()).isEmpty()) {
            throw new FacultyAlreadyExistsException();
        }
        return facultyRepository.save(faculty);
    }

    public Faculty update(Long id, Faculty faculty) {
        facultyRepository.findById(id).orElseThrow(NoSuchFacultyException::new);
        return facultyRepository.save(faculty);
    }

    public Faculty get(Long id) {
        return facultyRepository.findById(id).orElseThrow(NoSuchFacultyException::new);
    }

    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty remove (Long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(NoSuchFacultyException::new);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> getAllByColor (String color) {
        return facultyRepository.findAllByColorIgnoreCase(color).stream().collect(Collectors.toUnmodifiableList());
    }

    public Collection<Faculty> getAllByColorOrName (String color, String name) {
       return facultyRepository.findAllByColorLikeIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Faculty getFacultyByStudentIdIn (Long id) {
        return studentRepository.findById(id).map(Student::getFaculty).orElseThrow(NoSuchStudentException::new);
    }

}
