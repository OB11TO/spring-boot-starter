package ru.ob11to.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ob11to.springapp.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
