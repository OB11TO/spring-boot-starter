package ru.ob11to.springapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ob11to.springapp.annotation.TrackAsyncTime;
import ru.ob11to.springapp.annotation.TrackTime;
import ru.ob11to.springapp.dto.StudentCreateDto;
import ru.ob11to.springapp.dto.StudentReadDto;
import ru.ob11to.springapp.mapper.StudentMapper;
import ru.ob11to.springapp.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @TrackTime
    @Transactional
    public Optional<StudentReadDto> findById(Long id) {
        log.info("Get student with id : {}", id);
        return studentRepository.findById(id)
                .map(studentMapper::toDto);
    }

    @TrackAsyncTime
    @Transactional
    @Async
    public CompletableFuture<List<StudentReadDto>> findAll() {
        log.info("Get all students");
        return CompletableFuture.completedFuture(studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(toList()));
    }

    @Transactional
    public StudentReadDto createStudent(StudentCreateDto studentCreateDto) {
        log.info("Create student : {}", studentCreateDto);
        return Optional.of(studentCreateDto)
                .map(studentMapper::toEntity)
                .map(studentRepository::save)
                .map(studentMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<StudentReadDto> updateStudent(Long id, StudentCreateDto studentCreateDto) {
        log.info("Update student with id : {}, data : {}", id, studentCreateDto);
        return studentRepository.findById(id).map(existingStudent -> {
            existingStudent.setName(studentCreateDto.name());
            existingStudent.setAddress(studentCreateDto.address());
            return studentMapper.toDto(studentRepository.saveAndFlush(existingStudent));
        });
    }


    @Transactional
    public boolean deleteStudent(Long id) {
        log.info("Remove student with id : {}", id);
        return studentRepository.findById(id)
                .map(entity -> {
                    studentRepository.delete(entity);
                    studentRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
