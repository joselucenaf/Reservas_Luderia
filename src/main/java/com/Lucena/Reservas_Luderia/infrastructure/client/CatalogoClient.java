package com.Lucena.Reservas_Luderia.infrastructure.client;

import com.Lucena.Reservas_Luderia.business.dto.JogoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogo-jogos", url = "${catalogo_jogos.url}")
public interface CatalogoClient {
    @GetMapping("/jogos/{id}")
    JogoDTO buscarJogoPorId(@PathVariable("id") Long id);
}