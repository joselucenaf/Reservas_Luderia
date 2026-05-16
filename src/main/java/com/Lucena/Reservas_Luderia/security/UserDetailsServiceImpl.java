package com.Lucena.Reservas_Luderia.security;

import com.Lucena.Reservas_Luderia.business.dto.UsuarioDTO;
import com.Lucena.Reservas_Luderia.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UsuarioClient usuarioClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UsuarioDTO dto = usuarioClient.buscaUsuarioPorLogin(username);

            if (dto == null) {
                throw new UsernameNotFoundException("Usuário não encontrado no microsserviço externo: " + username);
            }

            return User.withUsername(dto.getEmail())
                    .password("")
                    .authorities(new ArrayList<>())
                    .build();
        } catch (Exception e) {
            log.error("FALHA NA SEGURANÇA: Erro ao integrar com microsserviço de usuário para o login: " + username, e);
            throw new UsernameNotFoundException("Erro ao comunicar com microserviço de usuário.", e);
        }
    }
}