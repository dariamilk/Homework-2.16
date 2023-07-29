package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController (StudentService studentService) {
        this.studentService = studentService;
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
}
