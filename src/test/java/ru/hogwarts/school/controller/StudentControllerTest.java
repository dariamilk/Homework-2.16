package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    TestRestTemplate template;

    @Test
    void add() {
        Student student =  new Student(1L, "name", 20, null);
        ResponseEntity<Student> response = template.postForEntity("/student", student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("name");
        assertThat(response.getBody().getAge()).isEqualTo(20);
    }

    @Test
    void update() {
        Student student =  new Student(1L, "name2", 20, null);
        ResponseEntity<Student> response = template.postForEntity("/student", student, Student.class);
        Long id = response.getBody().getId();
        Student updatedStudent = new Student(id, "name3", 20, null);
        template.put("/student/" + id, updatedStudent);
        ResponseEntity<Student> getResponse = template.getForEntity("/student/" + id, Student.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo(updatedStudent.getName());

    }

    @Test
    void delete() {
        Student student =  new Student(1L, "name4", 20, null);
        ResponseEntity<Student> response = template.postForEntity("/student", student, Student.class);
        Long id = response.getBody().getId();
        template.delete("/student/" + id);
        ResponseEntity<Student> getResponse = template.getForEntity("/student/" + id, Student.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void get() {
        Student student =  new Student(1L, "name5", 20, null);
        ResponseEntity<Student> response = template.postForEntity("/student", student, Student.class);
        Long id = response.getBody().getId();
        ResponseEntity<Student> getResponse = template.getForEntity("/student/" + id, Student.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo(student.getName());
        assertThat(response.getBody().getAge()).isEqualTo(student.getAge());

    }

    @Test
    void getAll() {
        Student student =  new Student(1L, "name6", 20, null);
        ResponseEntity<Student> response = template.postForEntity("/student", student, Student.class);
        ResponseEntity<Collection> getAllResponse = template.getForEntity("/student", Collection.class);
        assertThat(getAllResponse.getBody().size()).isEqualTo(1);

    }

    @Test
    void getAllByAge() {
        Student student =  new Student(1L, "name7", 22, null);
        ResponseEntity<Student> response = template.postForEntity("/student", student, Student.class);
        ResponseEntity<Collection> getResponse = template.getForEntity("/student/age/" + response.getBody().getAge(),Collection.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().size()).isEqualTo(1);
    }

    @Test
    void getAllByAgeBetween() {
        Student student =  new Student(1L, "name8", 28, null);
        ResponseEntity<Student> response = template.postForEntity("/student", student, Student.class);
        ResponseEntity<Collection> getResponse = template.getForEntity("/student/filtered-by-age?min=25&max=29",Collection.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().size()).isEqualTo(1);

    }

    @Test
    void getStudentsByFaculty() {
        Faculty faculty = new Faculty(1L, "faculty11","color", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        Student student =  new Student(1L, "name9", 20, response.getBody());
        template.postForEntity("/student", student, Student.class);
        ResponseEntity<Collection> studentResponseEntity = template.getForEntity("/student/find-by-faculty?id=" + response.getBody().getId(), Collection.class);
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody().size()).isEqualTo(1);
    }
}