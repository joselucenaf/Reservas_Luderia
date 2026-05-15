package com.Lucena.Reservas_Luderia.business.service;

import com.Lucena.Reservas_Luderia.infrastructure.client.CatalogoClient;
import com.Lucena.Reservas_Luderia.infrastructure.client.UsuarioClient;
import com.Lucena.Reservas_Luderia.infrastructure.entity.Reserva;
import com.Lucena.Reservas_Luderia.infrastructure.enums.StatusReserva;
import com.Lucena.Reservas_Luderia.infrastructure.exceptions.ConflictException;
import com.Lucena.Reservas_Luderia.infrastructure.exceptions.ResourceNotFoundException;
import com.Lucena.Reservas_Luderia.infrastructure.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final CatalogoClient catalogoClient;
    private final UsuarioClient usuarioClient;

    @Override
    public Reserva salvarReserva(Reserva reserva) {
        // 1. Validação centralizada de Usuário e Jogo (via Feign)
        validarIntegridadeExterna(reserva);

        // 2. Validação de conflito de horário usando o novo método refatorado
        // Alterado de 'existsByJogoIdAndDataInicioBetween' para 'verificarSobreposicao'
        if (reservaRepository.verificarSobreposicao(
                reserva.getJogoId(), reserva.getDataInicio(), reserva.getDataFim())) {
            throw new ConflictException("Este jogo já possui uma reserva ativa para o período selecionado.");
        }

        // Configurações automáticas para nova reserva
        reserva.setDataReserva(LocalDateTime.now());
        reserva.setStatus(StatusReserva.PENDENTE);

        return reservaRepository.save(reserva);
    }

    private void validarIntegridadeExterna(Reserva reserva) {
        try {
            catalogoClient.buscarJogoPorId(reserva.getJogoId());
            // Busca usuário por ID convertendo para String para o parâmetro 'login' do microserviço
            usuarioClient.buscaUsuarioPorLogin(reserva.getUsuarioId().toString());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Falha na integração: Jogo ou Usuário não localizado nos serviços externos.");
        }
    }

    @Override
    public List<Reserva> buscarTodas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> buscarPorUsuario(Long usuarioId) {
        List<Reserva> reservas = reservaRepository.findByUsuarioId(usuarioId);
        if (reservas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma reserva encontrada para o usuário ID: " + usuarioId);
        }
        return reservas;
    }

    @Override
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com o ID: " + id));

        reserva.setStatus(StatusReserva.CANCELADA);
        reserva.setDataFim(LocalDateTime.now()); // Registra o momento do cancelamento lógico

        reservaRepository.save(reserva);
    }
}