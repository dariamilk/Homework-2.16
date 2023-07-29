package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NoSuchStudentException;
import ru.hogwarts.school.exceptions.StudentAlreadyExistsException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService (StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
}
