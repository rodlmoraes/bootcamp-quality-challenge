package com.bootcampqualitychallenge.dto;

import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class Room {
    @NotBlank(message = "O nome do cômodo não pode estar vazio.")
    @Pattern(regexp = "^([A-Z][a-z]+\\s)*[A-Z][a-z]+$", message = "Cada palavra do nome deve começar com uma letra maiúscula seguida de minúsculas.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres.")
    private String name;
    @NotNull(message = "A largura do cômodo não pode estar vazia.")
    @DecimalMax(value = "25", message = "A largura máxima permitida por cômodo é de 25 metros.")
    @Positive(message = "A largura deve ser um número positivo.")
    private Double width;
    @NotNull(message = "O comprimento do cômodo não pode estar vazio.")
    @DecimalMax(value = "33", message = "O comprimento máximo permitido por cômodo é de 33 metros.")
    @Positive(message = "O comprimento deve ser um número positivo.")
    private Double length;
}
