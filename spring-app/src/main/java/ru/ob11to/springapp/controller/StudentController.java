package ru.ob11to.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.ob11to.springapp.dto.StudentCreateDto;
import ru.ob11to.springapp.dto.StudentReadDto;
import ru.ob11to.springapp.service.StudentService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Student", description = "Cтуденты")
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Получить студента по его id")
    @GetMapping("/{id}")
    public ResponseEntity<StudentReadDto> findById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(studentReadDto -> ResponseEntity.ok().body(studentReadDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Получить всех студентов")
    @GetMapping
    public ResponseEntity<List<StudentReadDto>> findAll() {
        try {
            return ResponseEntity.ok(studentService.findAll().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    @Operation(summary = "Сохранить нового студента")
    @PostMapping
    public ResponseEntity<StudentReadDto> createStudent(@Validated @RequestBody StudentCreateDto studentCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(studentCreateDto));
    }

    @Operation(summary = "Изменить данные о конкретном студенте по его id")
    @PutMapping("/{id}")
    public ResponseEntity<StudentReadDto> updateStudent(@PathVariable Long id,
                                                        @Validated @RequestBody StudentCreateDto studentCreateDto) {
        return studentService.updateStudent(id, studentCreateDto)
                .map(chatReadDto -> ResponseEntity.ok().body(chatReadDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Удалить конкретного студента по его id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteStudent(@PathVariable Long id) {
        if (!studentService.deleteStudent(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(id);
    }

}
