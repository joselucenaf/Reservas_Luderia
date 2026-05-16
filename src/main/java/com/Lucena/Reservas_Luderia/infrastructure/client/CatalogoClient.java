package com.Lucena.Reservas_Luderia.infrastructure.client;

import com.Lucena.Reservas_Luderia.business.dto.JogoDTO;
import com.Lucena.Reservas_Luderia.security.FeignClientConfig; // Garanta a importação correta da classe que criamos
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "catalogo-jogos",
        url = "${catalogo_jogos.url}",
        configuration = FeignClientConfig.class // Adicionado para repassar o Token JWT
)
public interface CatalogoClient {

    @GetMapping("/jogos/{id}") // Corrigido de /catalogo/{id} para /jogos/{id}
    JogoDTO buscarJogoPorId(@PathVariable("id") Long id);
}