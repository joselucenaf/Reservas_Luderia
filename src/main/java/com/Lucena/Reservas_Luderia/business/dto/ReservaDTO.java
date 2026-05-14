package com.Lucena.Reservas_Luderia.business.dto;

import com.Lucena.Reservas_Luderia.infrastructure.enums.StatusReserva;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id;
    private Long usuarioId;
    private Long jogoId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusReserva status;
}