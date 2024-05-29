package ru.ob11to.springapp.dto;

import jakarta.validation.constraints.NotEmpty;

public record StudentCreateDto(@NotEmpty(message = "Name cannot be empty") String name,
                               @NotEmpty(message = "Address cannot be empty") String address) {
}
