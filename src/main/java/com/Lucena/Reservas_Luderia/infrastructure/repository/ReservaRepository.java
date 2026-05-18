package com.Lucena.Reservas_Luderia.infrastructure.repository;

import com.Lucena.Reservas_Luderia.infrastructure.entity.Reserva;
import com.Lucena.Reservas_Luderia.infrastructure.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);

    /**
     * Verifica sobreposição de horários para um jogo específico.
     * A lógica (NovoInicio < FimExistente) AND (NovoFim > InicioExistente)
     * garante a detecção de qualquer interseção entre os períodos.
     * Também ignoramos reservas CANCELADAS na verificação.
     */
    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.jogoId = :jogoId " +
            "AND r.status <> 'CANCELADA' " +
            "AND (:inicio < r.dataFim AND :fim > r.dataInicio)")
    boolean verificarSobreposicao(@Param("jogoId") Long jogoId,
                                  @Param("inicio") LocalDateTime inicio,
                                  @Param("fim") LocalDateTime fim);

    List<Reserva> findByDataFimBeforeAndStatus(LocalDateTime data, StatusReserva status);
}