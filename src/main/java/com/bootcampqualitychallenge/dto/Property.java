package com.bootcampqualitychallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "^([A-Z][a-z]+\\s)*[A-Z][a-z]+$", message = "Cada palavra do nome deve começar com uma letra maiúscula seguida de minúsculas.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres.")
    private String name;
    @NotBlank(message = "O nome do bairro não pode estar vazio.")
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$", message = "O bairro deve ser alfanumérico.")
    @Size(max = 45, message = "O comprimento do bairro não pode exceder 45 caracteres.")
    private String neighborhood;
    @NotEmpty(message = "É necessário informar ao menos 1 cômodo.")
    @Valid
    private List<Room> rooms;
}
