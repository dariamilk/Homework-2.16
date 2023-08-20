package ru.hogwarts.school.service;

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

    public StudentService (StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student add(Student student) {
        if (!studentRepository.findAllByNameIgnoreCaseAndAge(student.getName(), student.getAge()).isEmpty()) {
            throw new StudentAlreadyExistsException();
        }
        return studentRepository.save(student);
    }

    public Student update(Long id, Student student) {
        studentRepository.findById(id).orElseThrow(NoSuchStudentException::new);
        return studentRepository.save(student);
    }

    public Student get(Long id) {
        return studentRepository.findById(id).orElseThrow(NoSuchStudentException::new);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student remove (Long id) {
        Student student = studentRepository.findById(id).orElseThrow(NoSuchStudentException::new);
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> getAllByAge (int age) {
        return studentRepository.findStudentByAge(age).stream().collect(Collectors.toUnmodifiableList());
    }

    public Collection<Student> getAllByAgeBetween (int min, int max) {
        return studentRepository.findByAgeBetween(min, max).stream().collect(Collectors.toUnmodifiableList());
    }

    public Collection<Student> getStudentByFacultyIdIn (Long id) {
        return facultyRepository.findById(id).map(Faculty::getStudents).orElseThrow(NoSuchFacultyException::new);
    }

    public Long countAllStudents() {
        return studentRepository.countAllStudents();
    }

    public Double findAverageAge() {
        return studentRepository.findAverageAge();
    }

    public Collection<Student> findLastStudents() {
        return studentRepository.findLastStudents();
    }
}
