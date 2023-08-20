package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(StudentController.class)
class StudentControllerMvcTest {

    @SpyBean
    StudentService studentService;

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    AvatarService avatarService;

    @MockBean
    FacultyRepository facultyRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    void add() throws Exception {
        Student student =  new Student(1L, "name", 20, null);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("20"));
    }

    @Test
    void update() throws Exception{
        Student student =  new Student(1L, "name", 20, null);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.put("/student/" + student.getId())
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("20"));
    }

    @Test
    void delete() throws Exception {
        Student student =  new Student(1L, "name", 20, null);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/" + student.getId())
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("20"));
    }

    @Test
    void get() throws Exception {
        Student student =  new Student(1L, "name", 20, null);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("20"));
    }

    @Test
    void getAll() throws Exception {
        Student student =  new Student(1L, "name", 20, null);
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(student.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    void getAllByAge() throws Exception {
        Student student =  new Student(1L, "name", 20, null);
        Mockito.when(studentRepository.findStudentByAge(student.getAge())).thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/age/" + student.getAge())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(student.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(student.getAge()));

    }

    @Test
    void getAllByAgeBetween() throws Exception {
        Student student =  new Student(1L, "name", 20, null);
        Mockito.when(studentRepository.findByAgeBetween(19, 25)).thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/filtered-by-age?min=19&max=25")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(student.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    void getStudentsByFaculty() throws Exception {
        Student student =  new Student(1L, "name", 20, null);
        Faculty faculty = new Faculty(1L, "faculty10","color", List.of(student));
        Mockito.when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/find-by-faculty?id=" + faculty.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(student.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(student.getAge()));
    }
}