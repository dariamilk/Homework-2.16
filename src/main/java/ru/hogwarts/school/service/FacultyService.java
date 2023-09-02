package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService (FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method to add faculty");
        if (!facultyRepository.findAllByNameIgnoreCase(faculty.getName()).isEmpty()) {
            logger.error("Faculty already exists");
            throw new FacultyAlreadyExistsException();
        }
        return facultyRepository.save(faculty);
    }

    public Faculty update(Long id, Faculty faculty) {
        logger.info("Was invoked method to update faculty");
        facultyRepository.findById(id).orElseThrow(NoSuchFacultyException::new);
        return facultyRepository.save(faculty);
    }

    public Faculty get(Long id) {
        logger.info("Was invoked method to get faculty by ID");
        return facultyRepository.findById(id).orElseThrow(NoSuchFacultyException::new);
    }

    public Collection<Faculty> getAll() {
        logger.info("Was invoked method to get the collection of all faculties");
        return facultyRepository.findAll();
    }

    public Faculty remove (Long id) {
        logger.info("Was invoked method to remove faculty");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(NoSuchFacultyException::new);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> getAllByColor (String color) {
        logger.info("Was invoked method to get all faculties by color");
        return facultyRepository.findAllByColorIgnoreCase(color).stream().collect(Collectors.toUnmodifiableList());
    }

    public Collection<Faculty> getAllByColorOrName (String color, String name) {
        logger.info("Was invoked method to get all faculties by color or name");
       return facultyRepository.findAllByColorLikeIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Faculty getFacultyByStudentIdIn (Long id) {
        logger.info("Was invoked method to get the faculty by student ID");
        return studentRepository.findById(id).map(Student::getFaculty).orElseThrow(NoSuchStudentException::new);
    }

}
