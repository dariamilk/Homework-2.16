package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping("/{id}")
    public Faculty update (@PathVariable("id") Long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty delete (@PathVariable("id") Long id) {
        return facultyService.remove(id);
    }

    @GetMapping("/{id}")
    public Faculty get (@PathVariable("id") Long id){
        return facultyService.get(id);
    }

    @GetMapping
    public Collection<Faculty> getAll () {
        return facultyService.getAll();
    }

    @GetMapping("/color")
    public Collection<Faculty> getAllByColor (String color) {
        return facultyService.getAllByColor(color);
    }

    @GetMapping("/filtered-by-color-or-name")
    public Collection<Faculty> getAllByColorOrName (@RequestParam String colorOrName) {
        return facultyService.getAllByColorOrName(colorOrName, colorOrName);
    }

    @GetMapping ("/find-by-student")
    public Faculty getFacultyByStudent (Long id) {
        return facultyService.getFacultyByStudentIdIn(id);
    }

    @GetMapping("/faculty-with-the-longest-name")
    public String getLongestFacultyName() {
        return facultyService.getLongestFacultyName();
    }
}
