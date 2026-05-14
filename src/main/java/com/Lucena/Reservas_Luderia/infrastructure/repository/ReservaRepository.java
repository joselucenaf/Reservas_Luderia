package com.Lucena.Reservas_Luderia.infrastructure.repository;

import com.Lucena.Reservas_Luderia.infrastructure.entity.Reserva;
import com.Lucena.Reservas_Luderia.infrastructure.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Busca todas as reservas de um usuário específico
    List<Reserva> findByUsuarioId(Long usuarioId);

    // Busca reservas por status (Ex: ver todos os que estão PENDENTES)
    List<Reserva> findByStatus(StatusReserva status);

    // Verifica se um jogo já está reservado em um determinado período
    // Importante para evitar que duas pessoas reservem o mesmo jogo no mesmo horário
    boolean existsByJogoIdAndDataInicioBetween(Long jogoId, LocalDateTime inicio, LocalDateTime fim);

    // Busca reservas que terminam hoje (útil para disparar avisos de devolução)
    List<Reserva> findByDataFimBeforeAndStatus(LocalDateTime data, StatusReserva status);
}