package com.backend_project_template.exposition.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderRequest(@NotNull Long userId, @NotEmpty List<OrderItemDto> items) {}
