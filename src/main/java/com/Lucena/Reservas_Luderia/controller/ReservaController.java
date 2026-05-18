package com.Lucena.Reservas_Luderia.controller;

import com.Lucena.Reservas_Luderia.business.converter.ReservaConverter;
import com.Lucena.Reservas_Luderia.business.dto.ReservaDTO;
import com.Lucena.Reservas_Luderia.business.service.ReservaService;
import com.Lucena.Reservas_Luderia.infrastructure.entity.Reserva;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaConverter reservaConverter;

    @PostMapping
    public ResponseEntity<ReservaDTO> salvarReserva(@RequestBody ReservaDTO dto) {
        Reserva reserva = reservaConverter.paraEntity(dto);
        Reserva reservaSalva = reservaService.salvarReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaConverter.paraDTO(reservaSalva));
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> buscarTodas() {
        List<ReservaDTO> lista = reservaService.buscarTodas()
                .stream()
                .map(reservaConverter::paraDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaDTO>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<ReservaDTO> lista = reservaService.buscarPorUsuario(usuarioId)
                .stream()
                .map(reservaConverter::paraDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarReserva(@PathVariable Long id) {
        reservaService.confirmarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarReserva(@PathVariable Long id) {
        reservaService.finalizarReserva(id);
        return ResponseEntity.noContent().build();
    }
}