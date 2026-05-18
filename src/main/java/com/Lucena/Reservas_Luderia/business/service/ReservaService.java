package com.Lucena.Reservas_Luderia.business.service;

import com.Lucena.Reservas_Luderia.infrastructure.entity.Reserva;
import java.util.List;

public interface ReservaService {
    Reserva salvarReserva(Reserva reserva);
    List<Reserva> buscarTodas();
    List<Reserva> buscarPorUsuario(Long usuarioId);
    void cancelarReserva(Long id);
    void confirmarReserva(Long id);
    void finalizarReserva(Long id);
}