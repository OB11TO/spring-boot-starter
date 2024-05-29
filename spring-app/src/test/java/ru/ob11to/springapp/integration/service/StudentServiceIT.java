package ru.ob11to.springapp.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ob11to.springapp.dto.StudentCreateDto;
import ru.ob11to.springapp.dto.StudentReadDto;
import ru.ob11to.springapp.integration.IntegrationTestBase;
import ru.ob11to.springapp.service.StudentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentServiceIT extends IntegrationTestBase {

    @Autowired
    private StudentService studentService;

    @Test
    void findById_ReturnsStudent_WhenStudentExists() {
        // Arrange
        Long id = 1L;
        String expectedName = "John Doe";
        String expectedAddress = "123 Main St";

        // Act
        Optional<StudentReadDto> result = studentService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedName, result.get().name());
        assertEquals(expectedAddress, result.get().address());
    }

    @Test
    void findById_ReturnsEmptyOptional_WhenStudentDoesNotExist() {
        // Arrange
        Long id = 999L;

        // Act
        Optional<StudentReadDto> result = studentService.findById(id);

        // Assert
        assertFalse(result.isPresent());
    }


    @Test
    void updateStudent_ReturnsUpdatedStudent_WhenStudentExists() {
        // Arrange
        Long id = 1L;
        String updatedName = "Jane Smith";
        String updatedAddress = "456 Elm St";
        StudentCreateDto studentCreateDto = new StudentCreateDto(updatedName, updatedAddress);

        // Act
        Optional<StudentReadDto> result = studentService.updateStudent(id, studentCreateDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());
        assertEquals(updatedName, result.get().name());
        assertEquals(updatedAddress, result.get().address());
    }

    @Test
    void updateStudent_ReturnsEmptyOptional_WhenStudentDoesNotExist() {
        // Arrange
        Long id = 999L;
        String updatedName = "Jane Smith";
        String updatedAddress = "456 Elm St";
        StudentCreateDto studentCreateDto = new StudentCreateDto(updatedName, updatedAddress);

        // Act
        Optional<StudentReadDto> result = studentService.updateStudent(id, studentCreateDto);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void deleteStudent_ReturnsTrue_WhenStudentExists() {
        // Arrange
        Long id = 1L;

        // Act
        boolean result = studentService.deleteStudent(id);

        // Assert
        assertTrue(result);
    }

    @Test
    void deleteStudent_ReturnsFalse_WhenStudentDoesNotExist() {
        // Arrange
        Long id = 999L;

        // Act
        boolean result = studentService.deleteStudent(id);

        // Assert
        assertFalse(result);
    }
}
    

