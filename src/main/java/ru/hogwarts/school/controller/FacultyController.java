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
}
