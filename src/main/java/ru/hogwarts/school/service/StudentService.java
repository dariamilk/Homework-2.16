package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NoSuchFacultyException;
import ru.hogwarts.school.exceptions.NoSuchStudentException;
import ru.hogwarts.school.exceptions.StudentAlreadyExistsException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService (StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student add(Student student) {
        logger.info("Was invoked method to add student");
        if (!studentRepository.findAllByNameIgnoreCaseAndAge(student.getName(), student.getAge()).isEmpty()) {
            logger.error("Such student already exists");
            throw new StudentAlreadyExistsException();
        }
        return studentRepository.save(student);
    }

    public Student update(Long id, Student student) {
        logger.info("Was invoked method to update student");
        studentRepository.findById(id).orElseThrow(NoSuchStudentException::new);
        return studentRepository.save(student);
    }

    public Student get(Long id) {
        logger.info("Was invoked method to get student by ID");
        return studentRepository.findById(id).orElseThrow(NoSuchStudentException::new);
    }

    public Collection<Student> getAll() {
        logger.info("Was invoked method to get all students");
        return studentRepository.findAll();
    }

    public Student remove (Long id) {
        logger.info("Was invoked method to remove student");
        Student student = studentRepository.findById(id).orElseThrow(NoSuchStudentException::new);
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> getAllByAge (int age) {
        logger.info("Was invoked method to get all students by age");
        return studentRepository.findStudentByAge(age).stream().collect(Collectors.toUnmodifiableList());
    }

    public Collection<Student> getAllByAgeBetween (int min, int max) {
        logger.info("Was invoked method to all students by age between");
        return studentRepository.findByAgeBetween(min, max).stream().collect(Collectors.toUnmodifiableList());
    }

    public Collection<Student> getStudentByFacultyIdIn (Long id) {
        logger.info("Was invoked method to get student by faculty ID");
        return facultyRepository.findById(id).map(Faculty::getStudents).orElseThrow(NoSuchFacultyException::new);
    }

    public Long countAllStudents() {
        logger.info("Was invoked method to count all students");
        return studentRepository.countAllStudents();
    }

    public Double findAverageAge() {
        logger.info("Was invoked method to find average age");
        return studentRepository.findAverageAge();
    }

    public Collection<Student> findLastStudents() {
        logger.info("Was invoked method to find last added students");
        return studentRepository.findLastStudents();
    }
}
