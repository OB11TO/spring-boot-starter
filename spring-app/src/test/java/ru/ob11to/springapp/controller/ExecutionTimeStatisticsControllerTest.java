package ru.ob11to.springapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ob11to.springapp.dto.ExecutionTimeMethodDto;
import ru.ob11to.springapp.service.ExecutionTimeStatisticsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecutionTimeStatisticsControllerTest {

    @Mock
    private ExecutionTimeStatisticsService statisticsService;

    @InjectMocks
    private ExecutionTimeStatisticsController controller;

    @Test
    void getStatistics_ReturnsOKWithExecutionTimeMethodDto() {
        // Arrange
        List<ExecutionTimeMethodDto> dto = new ArrayList<>();
        dto.add(new ExecutionTimeMethodDto("method1", 10.5));
        dto.add(new ExecutionTimeMethodDto("method2", 20.3));

        when(statisticsService.getAverageExecutionTimeMethods()).thenReturn(dto);

        // Act
        ResponseEntity<List<ExecutionTimeMethodDto>> response = controller.getStatistics();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());

        verify(statisticsService, times(1)).getAverageExecutionTimeMethods();
    }
}