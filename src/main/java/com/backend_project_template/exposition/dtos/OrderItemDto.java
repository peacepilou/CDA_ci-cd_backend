package com.backend_project_template.exposition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemDto(
        @NotBlank String productName,
        @NotNull @Positive int quantity,
        @NotNull @Positive double unitPrice
) {}
