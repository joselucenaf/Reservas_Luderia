package com.Lucena.Reservas_Luderia.security;

import com.Lucena.Reservas_Luderia.business.dto.UsuarioDTO;
import com.Lucena.Reservas_Luderia.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioClient usuarioClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // O Feign busca o usuário (por email ou CPF) no outro microsserviço
            UsuarioDTO dto = usuarioClient.buscaUsuarioPorLogin(username);

            if (dto == null) {
                throw new UsernameNotFoundException("Usuário não encontrado: " + username);
            }

            // Retorna o objeto que o Spring Security usa para validar o contexto
            return User.withUsername(dto.getEmail())
                    .password("") // Senha vazia pois a validação é via Token JWT
                    .authorities(new ArrayList<>())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Erro ao comunicar com microserviço de usuário.");
        }
    }
}