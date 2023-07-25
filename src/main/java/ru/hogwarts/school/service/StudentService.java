package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NoSuchStudentException;
import ru.hogwarts.school.exceptions.StudentAlreadyExistsException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private static Long COUNTER = 0L;
    private Map<Long, Student> storage = new HashMap<>();

    public Student add(Student student) {
        if (storage.containsKey(student.getId())) {
            throw new StudentAlreadyExistsException();
        }
        Long nextId = COUNTER++;
        student.setId(nextId);
        storage.put(nextId, student);
        return student;
    }

    public Student update(Long id, Student student) {
        if (!storage.containsKey(id)) {
            throw new NoSuchStudentException();
        }
        storage.put(id, student);
        return student;
    }

    public Student get(Long id) {
        return storage.get(id);
    }

    public Collection<Student> getAll() {
        return storage.values();
    }

    public Student remove (Long id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchStudentException();
        }
        return storage.remove(id);
    }

    public Collection<Student> getAllByAge (int age) {
        return storage.values().stream().filter(e -> e.getAge() == age).collect(Collectors.toList());
    }
}
