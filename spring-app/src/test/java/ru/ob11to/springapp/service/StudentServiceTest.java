package ru.ob11to.springapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ob11to.springapp.dto.StudentCreateDto;
import ru.ob11to.springapp.dto.StudentReadDto;
import ru.ob11to.springapp.entity.Student;
import ru.ob11to.springapp.mapper.StudentMapper;
import ru.ob11to.springapp.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Test
    void findById_ExistingId_ReturnsStudentReadDto() {
        // Arrange
        Long id = 1L;
        Student student = new Student(id, "John Doe", "123 Main St");
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentMapper.toDto(any(Student.class))).thenReturn(new StudentReadDto(id, "John Doe", "123 Main St"));

        // Act
        Optional<StudentReadDto> result = studentService.findById(id);

        // Assert
        assertEquals("John Doe", result.get().name());
        assertEquals("123 Main St", result.get().address());
    }

    @Test
    void findAll_ReturnsListOfStudentReadDto() {
        // Arrange
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John Doe", "123 Main St"));
        students.add(new Student(2L, "Jane Smith", "456 Oak St"));
        when(studentRepository.findAll()).thenReturn(students);
        when(studentMapper.toDto(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "John Doe", "123 Main St"))
                .thenReturn(new StudentReadDto(2L, "Jane Smith", "456 Oak St"));

        // Act
        CompletableFuture<List<StudentReadDto>> result = studentService.findAll();

        // Assert
        assertEquals(2, result.join().size());
        assertEquals("John Doe", result.join().get(0).name());
        assertEquals("123 Main St", result.join().get(0).address());
        assertEquals("Jane Smith", result.join().get(1).name());
        assertEquals("456 Oak St", result.join().get(1).address());
    }

    @Test
    void createStudent_ValidStudent_ReturnsStudentReadDto() {
        // Arrange
        StudentCreateDto studentCreateDto = new StudentCreateDto("John Doe", "123 Main St");
        Student student = new Student(1L, "John Doe", "123 Main St");
        when(studentMapper.toEntity(any(StudentCreateDto.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDto(any(Student.class))).thenReturn(new StudentReadDto(1L, "John Doe", "123 Main St"));

        // Act
        StudentReadDto result = studentService.createStudent(studentCreateDto);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.name());
        assertEquals("123 Main St", result.address());
    }

    @Test
    void updateStudent_ExistingIdAndValidData_ReturnsUpdatedStudentReadDto() {
        // Arrange
        Long id = 1L;
        StudentCreateDto studentCreateDto = new StudentCreateDto("John Doe", "123 Main St");
        Student existingStudent = new Student(id, "Existing Name", "Existing Address");
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentMapper.toDto(any(Student.class))).thenReturn(new StudentReadDto(id, "John Doe", "123 Main St"));
        when(studentRepository.saveAndFlush(any(Student.class))).thenReturn(existingStudent);

        // Act
        Optional<StudentReadDto> result = studentService.updateStudent(id, studentCreateDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().name());
        assertEquals("123 Main St", result.get().address());
        verify(studentRepository, times(1)).saveAndFlush(existingStudent);
    }

    @Test
    void updateStudent_NonExistingId_ReturnsEmptyOptional() {
        // Arrange
        Long id = 1L;
        StudentCreateDto studentCreateDto = new StudentCreateDto("John Doe", "123 Main St");
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<StudentReadDto> result = studentService.updateStudent(id, studentCreateDto);

        // Assert
        assertFalse(result.isPresent());
        verify(studentRepository, never()).saveAndFlush(any());
    }

    @Test
    void deleteStudent_ExistingId_ReturnsTrue() {
        // Arrange
        Long id = 1L;
        Student existingStudent = new Student(id, "John Doe", "123 Main St");
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));

        // Act
        boolean result = studentService.deleteStudent(id);

        // Assert
        assertTrue(result);
        verify(studentRepository, times(1)).delete(existingStudent);
        verify(studentRepository, times(1)).flush();
    }

    @Test
    void deleteStudent_NonExistingId_ReturnsFalse() {
        // Arrange
        Long id = 1L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = studentService.deleteStudent(id);

        // Assert
        assertFalse(result);
        verify(studentRepository, never()).delete(any());
        verify(studentRepository, never()).flush();
    }
}