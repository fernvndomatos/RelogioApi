package com.github.fernvndomatos.RelogioApi.controller;

import com.github.fernvndomatos.RelogioApi.dto.AtualizarRelogioRequest;
import com.github.fernvndomatos.RelogioApi.dto.CriarRelogioRequest;
import com.github.fernvndomatos.RelogioApi.dto.PaginaRelogioDto;
import com.github.fernvndomatos.RelogioApi.dto.RelogioDto;
import com.github.fernvndomatos.RelogioApi.service.RelogioServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/relogios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RelogioController {
    private final RelogioServices service;

    @GetMapping
    public PaginaRelogioDto listar(
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "12") int porPagina,
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String tipoMovimento,
            @RequestParam(required = false) String materialCaixa,
            @RequestParam(required = false) String tipoVidro,
            @RequestParam(required = false) Integer resistenciaMin,
            @RequestParam(required = false) Integer resistenciaMax,
            @RequestParam(required = false) Long precoMin,
            @RequestParam(required = false) Long precoMax,
            @RequestParam(required = false) Integer diametroMin,
            @RequestParam(required = false) Integer diametroMax,
            @RequestParam(required = false) String ordenar
    ) {
        return service.listar(
                pagina, porPagina, busca, marca, tipoMovimento, materialCaixa, tipoVidro, resistenciaMin,
                resistenciaMax, precoMin, precoMax, diametroMin, diametroMax, ordenar);
    }

    @GetMapping("/{id}")
    public RelogioDto buscarPorId(@PathVariable UUID id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public RelogioDto criar(@Valid @RequestBody CriarRelogioRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public RelogioDto atualizar(@PathVariable UUID id, @Valid @RequestBody AtualizarRelogioRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }

}
