package ru.ob11to.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ob11to.springapp.dto.ExecutionTimeMethodDto;
import ru.ob11to.springapp.entity.ExecutionTimeMethod;

import java.util.List;

public interface ExecutionTimeMethodRepository extends JpaRepository<ExecutionTimeMethod, Long> {

    @Query("SELECT new ru.ob11to.springapp.dto.ExecutionTimeMethodDto(e.methodName, AVG(e.timeTaken)) " +
            "FROM ExecutionTimeMethod e " +
            "GROUP BY e.methodName")
    List<ExecutionTimeMethodDto> findAverageTimeTakenByMethodName();
}
