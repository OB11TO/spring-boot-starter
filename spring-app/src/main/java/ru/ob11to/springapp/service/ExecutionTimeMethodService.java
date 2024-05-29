package ru.ob11to.springapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ob11to.springapp.entity.ExecutionTimeMethod;
import ru.ob11to.springapp.repository.ExecutionTimeMethodRepository;

import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutionTimeMethodService {

    private final ExecutionTimeMethodRepository methodRepository;

    @Async
    @Transactional
    public void saveExecutionTime(String methodName, Long timeTaken) {
        log.info("Saving execution time for method: {}", methodName);
        methodRepository.save(ExecutionTimeMethod.builder()
                .methodName(methodName)
                .createdAt(ZonedDateTime.now())
                .timeTaken(timeTaken)
                .build());
    }

}
