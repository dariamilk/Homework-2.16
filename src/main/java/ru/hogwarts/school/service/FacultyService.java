package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyAlreadyExistsException;
import ru.hogwarts.school.exceptions.NoSuchFacultyException;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private static Long COUNTER = 0L;
    private Map<Long, Faculty> storage = new HashMap<>();

    public Faculty add(Faculty faculty) {
        if (storage.containsKey(faculty.getId())) {
            throw new FacultyAlreadyExistsException();
        }
        Long nextId = COUNTER++;
        faculty.setId(nextId);
        storage.put(nextId, faculty);
        return faculty;
    }

    public Faculty update(Long id, Faculty faculty) {
        if (!storage.containsKey(id)) {
            throw new NoSuchFacultyException();
        }
        storage.put(id, faculty);
        return faculty;
    }

    public Faculty get(Long id) {
        return storage.get(id);
    }

    public Collection<Faculty> getAll() {
        return storage.values();
    }

    public Faculty remove (Long id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchFacultyException();
        }
        return storage.remove(id);
    }

    public Collection<Faculty> getAllByColor (String color) {
        return storage.values().stream().filter(e -> e.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
    }
}
