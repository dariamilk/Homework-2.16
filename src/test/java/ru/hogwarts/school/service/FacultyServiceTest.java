package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.FacultyAlreadyExistsException;
import ru.hogwarts.school.exceptions.NoSuchFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    FacultyRepository facultyRepository;

    @InjectMocks
    FacultyService facultyService;

    @Test
    void addTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.save(faculty)).thenReturn(faculty);
        Assertions.assertEquals(faculty, facultyService.add(faculty));
    }

    @Test
    void addThrowsExceptionTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.findAllByNameIgnoreCase(faculty.getName())).thenReturn(List.of(faculty));
        Assertions.assertThrows(FacultyAlreadyExistsException.class, () -> facultyService.add(faculty));
    }

    @Test
    void updateThrowsExceptionTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchFacultyException.class, () -> facultyService.update(faculty.getId(), faculty));
    }

    @Test
    void getTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        Assertions.assertEquals(faculty, facultyService.get(faculty.getId()));
    }

    @Test
    void getThrowsExceptionTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchFacultyException.class, () -> facultyService.get(faculty.getId()));
    }

    @Test
    void getAllTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.findAll()).thenReturn(List.of(faculty));
        Assertions.assertEquals(List.of(faculty), facultyService.getAll());
    }

    @Test
    void removeTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.doNothing().when(facultyRepository).deleteById(faculty.getId());
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        facultyService.remove(faculty.getId());
        Mockito.verify(facultyRepository, times(1)).deleteById(faculty.getId());
    }
    @Test
    void removeThrowsExceptionTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchFacultyException.class, () -> facultyService.remove(faculty.getId()));
    }

    @Test
    void getAllByAgeTest () {
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Mockito.when(facultyRepository.findAllByColor(faculty.getColor())).thenReturn(List.of(faculty));
        Assertions.assertEquals(List.of(faculty), facultyService.getAllByColor(faculty.getColor()));
    }

}