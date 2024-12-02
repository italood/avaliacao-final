/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cliente
 */
import java.time.LocalDate;

public class Reserva {
    private int id;
    private Cliente cliente;
    private PacoteViagem pacote;
    private LocalDate dataReserva;

    public Reserva(int id, Cliente cliente, PacoteViagem pacote, LocalDate dataReserva) {
        this.id = id;
        this.cliente = cliente;
        this.pacote = pacote;
        this.dataReserva = dataReserva;

        if (!pacote.reservarVaga()) {
            throw new IllegalStateException("Não há vagas disponíveis para o pacote.");
        }

        cliente.adicionarReserva(this);
        pacote.getReservas().add(this);
    }

    public void cancelar() {
        pacote.cancelarReserva();
        cliente.removerReserva(this);
        pacote.getReservas().remove(this);
    }

    @Override
    public String toString() {
        return String.format("Reserva ID: %d | Cliente: %s | Pacote: %s | Data: %s",
                id, cliente.getCpf(), pacote.getDestino(), dataReserva);
    }


    Object getPacote() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object getCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

