package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;
@WebMvcTest(FacultyController.class)
class FacultyControllerMvcTest {

    @SpyBean
    FacultyService facultyService;

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    FacultyRepository facultyRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void add() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Mockito.when(facultyRepository.findAllByNameIgnoreCase(faculty.getName())).thenReturn(List.of());
        Mockito.when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("color"));
    }

    @Test
    void update() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        Mockito.when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/" + faculty.getId())
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("color"));
    }

    @Test
    void delete() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + faculty.getId())
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("color"));
    }

    @Test
    void get() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + faculty.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("color"));
    }

    @Test
    void getAll() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Mockito.when(facultyRepository.findAll()).thenReturn(List.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color").value("color"));

    }

    @Test
    void getAllByColor() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Mockito.when(facultyRepository.findAllByColorIgnoreCase(faculty.getColor())).thenReturn(List.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color?color=color")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color").value("color"));
    }

    @Test
    void getAllByColorOrName() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Mockito.when(facultyRepository.findAllByColorLikeIgnoreCaseOrNameIgnoreCase(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(List.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/filtered-by-color-or-name?colorOrName=faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color").value("color"));
    }

    @Test
    void getFacultyByStudent() throws Exception {
        Faculty faculty = new Faculty(1L, "faculty","color", null);
        Student student = new Student(2L, "name", 20, faculty);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/find-by-student?id=" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("faculty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("color"));

    }
}