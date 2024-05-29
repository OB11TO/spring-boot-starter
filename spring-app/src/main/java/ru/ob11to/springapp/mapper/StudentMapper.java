package ru.ob11to.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.ob11to.springapp.dto.StudentCreateDto;
import ru.ob11to.springapp.dto.StudentReadDto;
import ru.ob11to.springapp.entity.Student;


@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "address", source = "address"),
    })
    StudentReadDto toDto(Student student);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "address", source = "address"),
    })
    Student toEntity(StudentCreateDto studentCreateDto);
}
