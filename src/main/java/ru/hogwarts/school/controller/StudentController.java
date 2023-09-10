package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController (StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @PutMapping("/{id}")
    public Student update (@PathVariable("id") Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    public Student delete (@PathVariable("id") Long id) {
        return studentService.remove(id);
    }

    @GetMapping("/{id}")
    public Student get (@PathVariable("id") Long id){
        return studentService.get(id);
    }

    @GetMapping
    public Collection<Student> getAll () {
        return studentService.getAll();
    }

    @GetMapping("/age/{age}")
    public Collection<Student> getAllByAge (@PathVariable("age") int age) {
        return studentService.getAllByAge(age);
    }

    @GetMapping("/filtered-by-age")
    public Collection<Student> getAllByAgeBetween (@RequestParam("min") int min, @RequestParam("max") int max) {
        return studentService.getAllByAgeBetween(min, max);
    }

    @GetMapping("/find-by-faculty")
    public Collection<Student> getStudentsByFaculty (Long id) {
        return studentService.getStudentByFacultyIdIn(id);
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> addAvatar (@PathVariable ("studentId") Long id, @RequestBody MultipartFile multipartFile) {
        try {
            return ResponseEntity.ok(avatarService.addAvatar(id, multipartFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/count")
    public Long countAllStudents() {
        return studentService.countAllStudents();
    }

    @GetMapping("/average-age")
    public Double findAverageAge() {
        return studentService.findAverageAge();
    }

    @GetMapping("/last-students")
    public Collection<Student> findLastStudents() {
        return studentService.findLastStudents();
    }

    @GetMapping("/student-by-fist-character-in-name")
    public Collection<String> findAllStudentByFirstLetterInName(char ch) {
        return studentService.findAllStudentByFirstLetterInName(ch);
    }

    @GetMapping("/average-age-by-stream")
    public Double findAverageAgeUsingStream() {
       return studentService.findAverageAgeUsingStream();
    }
}
