package com.Lucena.Reservas_Luderia.infrastructure.client;

import com.Lucena.Reservas_Luderia.business.dto.UsuarioDTO;
import com.Lucena.Reservas_Luderia.security.FeignClientConfig; // Garanta a importação correta
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "usuario-service",
        url = "http://localhost:8080/usuario",
        configuration = FeignClientConfig.class // Adicionado para repassar o Token JWT
)
public interface UsuarioClient {

    // Usado pelo Service de Reservas para validar o JSON (usuarioId: 2)
    @GetMapping("/{id}")
    UsuarioDTO buscarUsuarioPorId(@PathVariable("id") Long id);

    // Usado pela Segurança (UserDetailsServiceImpl) para encontrar o usuário pelo e-mail do token
    @GetMapping
    UsuarioDTO buscaUsuarioPorLogin(@RequestParam("login") String login);
}