package com.Lucena.Reservas_Luderia.infrastructure.client;

import com.Lucena.Reservas_Luderia.business.dto.JogoDTO;
import com.Lucena.Reservas_Luderia.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;

@FeignClient(
        name = "catalogo-jogos",
        url = "${catalogo_jogos.url}",
        configuration = FeignClientConfig.class
)
public interface CatalogoClient {

    @GetMapping("/jogos/{id}")
    JogoDTO buscarJogoPorId(@PathVariable("id") Long id);

    //endpoint de decremento do Catálogo
    @PatchMapping("/jogos/{id}/decrementar-estoque")
    void decrementarEstoque(@PathVariable("id") Long id);
}