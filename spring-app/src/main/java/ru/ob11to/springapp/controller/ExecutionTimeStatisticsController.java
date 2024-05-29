package ru.ob11to.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ob11to.springapp.dto.ExecutionTimeMethodDto;
import ru.ob11to.springapp.service.ExecutionTimeStatisticsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "ExecutionTimeStatistic", description = "Статистика времени выполнения методов")
@RequestMapping("/statistics")
public class ExecutionTimeStatisticsController {

    private final ExecutionTimeStatisticsService statisticsService;

    @Operation(summary = "Получить среднее время выполнения всех методов помеченными аннотациями @TrackTime и @TrackAsyncTime")
    @GetMapping("/average-execution-time-methods")
    public ResponseEntity<List<ExecutionTimeMethodDto>> getStatistics() {
        return ResponseEntity.ok(statisticsService.getAverageExecutionTimeMethods());
    }


}
