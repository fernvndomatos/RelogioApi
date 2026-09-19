package com.github.fernvndomatos.RelogioApi.mapper;

import com.github.fernvndomatos.RelogioApi.dto.RelogioDto;
import com.github.fernvndomatos.RelogioApi.entity.Relogio;
import com.github.fernvndomatos.RelogioApi.entity.enums.MaterialCaixa;
import com.github.fernvndomatos.RelogioApi.entity.enums.TipoMovimento;
import com.github.fernvndomatos.RelogioApi.entity.enums.TipoVidro;
import org.springframework.stereotype.Component;

@Component
public class RelogioMapper {

    public RelogioDto toDto(Relogio r){
        return RelogioDto.builder()
                .id(r.getId())
                .marca(r.getMarca())
                .referencia(r.getReferencia())
                .tipoMovimento(r.getTipoMovimento().toApi())
                .materialCaixa(r.getMaterialCaixa().toApi())
                .tipoVidro(r.getTipoVidro().toApi())
                .resistenciaAguaM(r.getResistenciaAguaM())
                .diametroMm(r.getDiametroMm())
                .lugTolugMm(r.getLugToLugMm())
                .espessuraMm(r.getEspessuraMm())
                .larguraMm(r.getLarguraLugMm())
                .precoEmCentavos(r.getPrecoEmCentavos())
                .urlImagem(r.getUrlImagem())
                .etiquetaResistenciaAgua(etiquetaResistencia(r.getResistenciaAguaM()))
                .pontuacaoColecionador(pontuacaoColecionador(r))
                .build();
    }

    private String etiquetaResistencia(int resistetnciaM){
        if (resistetnciaM < 50) return "respingos";
        if (resistetnciaM < 100) return "uso_diario";
        if (resistetnciaM < 200) return "natacao";
        return "mergulho";
    }

    private int pontuacaoColecionador(Relogio r) {
        int pontos = 0;

        if (r.getTipoVidro() == TipoVidro.SAFIRA) pontos += 25;

        if (r.getResistenciaAguaM() >= 100) pontos += 15;
        if (r.getResistenciaAguaM() >= 200) pontos += 10;

        if (r.getTipoMovimento() == TipoMovimento.AUTOMATICO) pontos += 20;

        if (r.getMaterialCaixa() == MaterialCaixa.CERAMICA) pontos += 12;
        if (r.getMaterialCaixa() == MaterialCaixa.TITANIO) pontos += 12;

        if (r.getDiametroMm() >= 38 && r.getDiametroMm() <= 42) pontos += 8;

        return pontos;
    }
}
