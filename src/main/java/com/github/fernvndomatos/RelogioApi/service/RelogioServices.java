package com.github.fernvndomatos.RelogioApi.service;

import com.github.fernvndomatos.RelogioApi.dto.AtualizarRelogioRequest;
import com.github.fernvndomatos.RelogioApi.dto.CriarRelogioRequest;
import com.github.fernvndomatos.RelogioApi.dto.PaginaRelogioDto;
import com.github.fernvndomatos.RelogioApi.dto.RelogioDto;
import com.github.fernvndomatos.RelogioApi.entity.Relogio;
import com.github.fernvndomatos.RelogioApi.entity.enums.MaterialCaixa;
import com.github.fernvndomatos.RelogioApi.entity.enums.TipoMovimento;
import com.github.fernvndomatos.RelogioApi.entity.enums.TipoVidro;
import com.github.fernvndomatos.RelogioApi.exception.NaoEncontradoException;
import com.github.fernvndomatos.RelogioApi.mapper.RelogioMapper;
import com.github.fernvndomatos.RelogioApi.repository.RelogioRepository;
import com.github.fernvndomatos.RelogioApi.service.enums.OrdenacaoRelogios;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.github.fernvndomatos.RelogioApi.service.RelogioSpecs.*;

@Service
@RequiredArgsConstructor
public class RelogioServices {

    private final RelogioRepository relogioRepository;
    private final RelogioMapper relogioMapper;

    public PaginaRelogioDto listar(
            int pagina,
            int porPagina,
            String busca,
            String marca,
            String tipoMovimento,
            String materialCaixa,
            String tipoVidro,
            Integer resistenciaMin,
            Integer resistenciaMax,
            Long precoMin,
            Long precoMax,
            Integer diametroMin,
            Integer diametroMax,
            String ordenar
    ){
        int paginaSegura = Math.max(1,pagina);
        int porPaginaSegura = Math.min(60,Math.max(1,porPagina));

        TipoMovimento movimento = TipoMovimento.fromApi(tipoMovimento);
        MaterialCaixa material = MaterialCaixa.fromApi(materialCaixa);
        TipoVidro vidro = TipoVidro.fromApi(tipoVidro);

        OrdenacaoRelogios ordenacao = OrdenacaoRelogios.fromApi(ordenar);

        Sort sort = switch (ordenacao){
            case MAIS_RECENTES -> Sort.by(Sort.Direction.DESC, "criadoEm");
            case PRECO_CRESC -> Sort.by(Sort.Direction.ASC, "precoEmCentavos");
            case PRECO_DESC -> Sort.by(Sort.Direction.DESC, "precoEmCentavos");
            case DIAMETRO_CRESC -> Sort.by(Sort.Direction.ASC, "diametroMm");
            case RESISTENCIA_DESC -> Sort.by(Sort.Direction.DESC, "resistenciaAguaM");
        };

        Pageable pageable = PageRequest.of(paginaSegura - 1, porPaginaSegura, sort);

        Specification<Relogio> spec = Specification.where(busca(busca))
                .and(marcaIgual(marca))
                .and(tipoMovimentoIgual(movimento))
                .and(materialCaixaIgual(material))
                .and(tipoVidroIgual(vidro))
                .and(resistenciaAguaEntre(resistenciaMin, resistenciaMax))
                .and(precoEntre(precoMin, precoMax))
                .and(diametroEntre(diametroMin, diametroMax));

        Page<Relogio> resultado = relogioRepository.findAll(spec, pageable);

        return new PaginaRelogioDto(
                resultado.getContent().stream().map(relogioMapper::toDto).toList(),
                resultado.getTotalElements()
        );
    }

    public RelogioDto buscarPorId(UUID id){
        Relogio r = relogioRepository.findById(id).orElseThrow(()->new NaoEncontradoException("Relógio Não Encontrado " + id));
        return relogioMapper.toDto(r);
    }

    public RelogioDto criar(CriarRelogioRequest request){
        Relogio r = Relogio.builder()
                .id(UUID.randomUUID())
                .marca(request.marca())
                .modelo(request.modelo())
                .referencia(request.referencia())
                .tipoMovimento(TipoMovimento.fromApi(request.tipoMovimento()))
                .materialCaixa(MaterialCaixa.fromApi(request.materialCaixa()))
                .tipoVidro(TipoVidro.fromApi(request.tipoVidro()))
                .resistenciaAguaM(request.resistenciaAguaM())
                .diametroMm(request.diametroMm())
                .lugToLugMm(request.lugToLugMm())
                .espessuraMm(request.espessuraMm())
                .larguraLugMm(request.larguraLugMm())
                .precoEmCentavos(request.precoEmCentavos())
                .urlImagem(request.urlImagem())
                .criadoEm(Instant.now())
                .build();
        return relogioMapper.toDto(relogioRepository.save(r));
    }

    public RelogioDto atualizar(UUID id, AtualizarRelogioRequest request){
        Relogio r = relogioRepository.findById(id)
                .orElseThrow(()->new NaoEncontradoException("Relógio Não Encontrado: " + id));
        r.setMarca(request.marca());
        r.setModelo(request.modelo());
        r.setReferencia(request.referencia());
        r.setTipoMovimento(TipoMovimento.fromApi(request.tipoMovimento()));
        r.setMaterialCaixa(MaterialCaixa.fromApi(request.materialCaixa()));
        r.setTipoVidro(TipoVidro.fromApi(request.tipoVidro()));
        r.setResistenciaAguaM(request.resistenciaAguaM());
        r.setDiametroMm(request.diametroMm());
        r.setLugToLugMm(request.lugToLugMm());
        r.setEspessuraMm(request.espessuraMm());
        r.setLarguraLugMm(request.larguraLugMm());
        return relogioMapper.toDto(relogioRepository.save(r));
    }

    public void remover(UUID id){
        if (!relogioRepository.existsById(id)){
            throw new NaoEncontradoException("Relógio Não Encontrado: " + id);
        }
        relogioRepository.deleteById(id);
    }

}
