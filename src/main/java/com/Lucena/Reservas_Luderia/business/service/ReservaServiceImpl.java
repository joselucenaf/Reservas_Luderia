package com.Lucena.Reservas_Luderia.business.service;

import com.Lucena.Reservas_Luderia.business.converter.ReservaConverter;
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
    private final ReservaConverter reservaConverter;

    @Override
    public Reserva salvarReserva(Reserva reserva) {
        // 1. Validação via Feign: O Jogo existe no Catálogo?
        try {
            catalogoClient.buscarJogoPorId(reserva.getJogoId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Jogo com ID " + reserva.getJogoId() + " não encontrado no catálogo.");
        }

        // 2. Validação via Feign: O Usuário existe?
        try {
            // Note: Ajustei para converter o ID em String se o seu client esperar String login
            usuarioClient.buscaUsuarioPorLogin(reserva.getUsuarioId().toString());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Usuário com ID " + reserva.getUsuarioId() + " não encontrado.");
        }

        // 3. Validação de conflito de horário: O jogo está livre nessa data?
        boolean jaReservado = reservaRepository.existsByJogoIdAndDataInicioBetween(
                reserva.getJogoId(),
                reserva.getDataInicio(),
                reserva.getDataFim()
        );

        if (jaReservado) {
            throw new ConflictException("Conflito: Este jogo já possui uma reserva para o período selecionado.");
        }

        // Configurações automáticas para nova reserva
        reserva.setDataReserva(LocalDateTime.now());
        reserva.setStatus(StatusReserva.PENDENTE);

        return reservaRepository.save(reserva);
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
        reserva.setDataFim(LocalDateTime.now()); // Registra o momento do cancelamento

        reservaRepository.save(reserva);
    }
}