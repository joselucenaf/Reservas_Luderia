package com.Lucena.Reservas_Luderia.business.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JogoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String tipo;
}