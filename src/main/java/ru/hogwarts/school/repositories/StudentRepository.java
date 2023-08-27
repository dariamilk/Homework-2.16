package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByNameIgnoreCaseAndAge(String name, int age);
    List<Student> findStudentByAge(int age);
    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "select count(*) from student", nativeQuery = true)
    Long countAllStudents ();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double findAverageAge();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> findLastStudents();
}
