package com.github.fernvndomatos.RelogioApi.dto;

import java.util.List;

public record PaginaRelogioDto(
        List<RelogioDto> itens,
        long total
) {
}
