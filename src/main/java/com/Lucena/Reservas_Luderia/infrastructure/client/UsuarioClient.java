package com.Lucena.Reservas_Luderia.infrastructure.client;

import com.Lucena.Reservas_Luderia.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping("/usuario")
    UsuarioDTO buscaUsuarioPorLogin(@RequestParam("login") String login);
}