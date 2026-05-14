package com.Lucena.Reservas_Luderia.business.converter;

import com.Lucena.Reservas_Luderia.business.dto.ReservaDTO;
import com.Lucena.Reservas_Luderia.infrastructure.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaConverter {

    public Reserva paraEntity(ReservaDTO dto) {
        return Reserva.builder()
                .usuarioId(dto.getUsuarioId())
                .jogoId(dto.getJogoId())
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .status(dto.getStatus())
                .build();
    }

    public ReservaDTO paraDTO(Reserva entity) {
        return ReservaDTO.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .jogoId(entity.getJogoId())
                .dataInicio(entity.getDataInicio())
                .dataFim(entity.getDataFim())
                .status(entity.getStatus())
                .build();
    }
}