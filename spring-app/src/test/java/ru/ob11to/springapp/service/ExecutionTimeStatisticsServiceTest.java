package ru.ob11to.springapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ob11to.springapp.dto.ExecutionTimeMethodDto;
import ru.ob11to.springapp.repository.ExecutionTimeMethodRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ExecutionTimeStatisticsServiceTest {

    @Mock
    private ExecutionTimeMethodRepository executionTimeMethodRepository;

    @InjectMocks
    private ExecutionTimeStatisticsService statisticsService;

    @Test
    void getAverageExecutionTimeMethods_ReturnsExecutionTimeMethodDto() {
        // Arrange
        List<ExecutionTimeMethodDto> expecteddto = new ArrayList<>();
        expecteddto.add(new ExecutionTimeMethodDto("method1", 10.5));
        expecteddto.add(new ExecutionTimeMethodDto("method2", 20.3));

        when(executionTimeMethodRepository.findAverageTimeTakenByMethodName()).thenReturn(expecteddto);

        // Act
        List<ExecutionTimeMethodDto> actualdto = statisticsService.getAverageExecutionTimeMethods();

        // Assert
        assertEquals(expecteddto.size(), actualdto.size());
        for (int i = 0; i < expecteddto.size(); i++) {
            assertEquals(expecteddto.get(i), actualdto.get(i));
        }

        verify(executionTimeMethodRepository, times(1)).findAverageTimeTakenByMethodName();
    }
}