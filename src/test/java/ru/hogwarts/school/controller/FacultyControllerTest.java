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
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {

    @Autowired
    TestRestTemplate template;


    @Test
    void add() {
        Faculty faculty = new Faculty(1L, "faculty","color", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("faculty");
        assertThat(response.getBody().getColor()).isEqualTo("color");
    }

    @Test
    void update() {
        Faculty faculty = new Faculty(1L, "faculty2","color", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        Long id = response.getBody().getId();
        Faculty updatedFaculty = new Faculty(id, "faculty3","color1", new ArrayList<>());
        template.put("/faculty/" + id, updatedFaculty);
        ResponseEntity<Faculty> getResponse = template.getForEntity("/faculty/" + id, Faculty.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo(updatedFaculty.getName());
    }

    @Test
    void delete() {
        Faculty faculty = new Faculty(1L, "faculty4","color", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        Long id = response.getBody().getId();
        template.delete("/faculty/" + id);
        ResponseEntity<Faculty> getResponse = template.getForEntity("/faculty/" + id, Faculty.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void get() {
        Faculty faculty = new Faculty(1L, "faculty5","color", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        Long id = response.getBody().getId();
        ResponseEntity<Faculty> getResponse = template.getForEntity("/faculty/" + id, Faculty.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo(faculty.getName());
        assertThat(response.getBody().getColor()).isEqualTo(faculty.getColor());
    }

    @Test
    void getAll() {
        Faculty faculty = new Faculty(1L, "faculty6","color", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        ResponseEntity<Collection> getAllResponse = template.getForEntity("/faculty", Collection.class);
        assertThat(getAllResponse.getBody().size()).isEqualTo(1);
    }

    @Test
    void getAllByColor() {
        Faculty faculty = new Faculty(1L, "faculty7","color2", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        ResponseEntity<Collection> getResponse = template.getForEntity("/faculty/color?color=color2",Collection.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().size()).isEqualTo(1);

    }

    @Test
    void getAllByColorOrName() {
        Faculty faculty = new Faculty(1L, "faculty8","color", new ArrayList<>());
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        ResponseEntity<Collection> getResponse = template.getForEntity("/faculty/filtered-by-color-or-name?colorOrName=faculty8",Collection.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().size()).isEqualTo(1);
    }

    @Test
    void getFacultyByStudent() {
        Faculty faculty = new Faculty(1L, "faculty9","color", null);
        ResponseEntity<Faculty> response = template.postForEntity("/faculty", faculty, Faculty.class);
        Student student =  new Student(1L, "name", 20, response.getBody());
        ResponseEntity<Student> studentResponseEntity = template.postForEntity("/student", student, Student.class);
        ResponseEntity<Faculty> facultyResponseEntity = template.getForEntity("/faculty/find-by-student?id="
                + studentResponseEntity.getBody().getId(), Faculty.class);
        assertThat(facultyResponseEntity.getBody().getName()).isEqualTo(faculty.getName());
        assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo(faculty.getColor());
    }
}