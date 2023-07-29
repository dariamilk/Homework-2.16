package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.NoSuchStudentException;
import ru.hogwarts.school.exceptions.StudentAlreadyExistsException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @Test
    void addTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Assertions.assertEquals(student, studentService.add(student));
    }

    @Test
    void addThrowsExceptionTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.findAllByNameIgnoreCaseAndAge(student.getName(), student.getAge())).thenReturn(List.of(student));
        Assertions.assertThrows(StudentAlreadyExistsException.class, () -> studentService.add(student));
    }

    @Test
    void updateThrowsExceptionTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchStudentException.class, () -> studentService.update(student.getId(), student));
    }

    @Test
    void getTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        Assertions.assertEquals(student, studentService.get(student.getId()));
    }

    @Test
    void getThrowsExceptionTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchStudentException.class, () -> studentService.get(student.getId()));
    }

    @Test
    void getAllTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student));
        Assertions.assertEquals(List.of(student), studentService.getAll());
    }

    @Test
    void removeTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.doNothing().when(studentRepository).deleteById(student.getId());
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        studentService.remove(student.getId());
        Mockito.verify(studentRepository, times(1)).deleteById(student.getId());
    }
    @Test
    void removeThrowsExceptionTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchStudentException.class, () -> studentService.remove(student.getId()));
    }

    @Test
    void getAllByAgeTest () {
        Student student = new Student(1L, "Ivan", 23);
        Mockito.when(studentRepository.findStudentByAge(student.getAge())).thenReturn(List.of(student));
        Assertions.assertEquals(List.of(student), studentService.getAllByAge(student.getAge()));
    }



}