/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cliente
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GerenciadorSistema {
    private List<PacoteViagem> pacotes;
    private List<Cliente> clientes;
    private List<Reserva> reservas;

    public GerenciadorSistema() {
        this.pacotes = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public void cadastrarPacote(PacoteViagem pacote) {
        pacotes.add(pacote);
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Reserva reservarPacote(String cpfCliente, int idPacote) {
        Cliente cliente = buscarClientePorCpf(cpfCliente);
        PacoteViagem pacote = buscarPacotePorId(idPacote);

        Reserva reserva = new Reserva(reservas.size() + 1, cliente, pacote, LocalDate.now());
        reservas.add(reserva);
        return reserva;
    }

    public void cancelarReserva(String cpfCliente, int idPacote) {
        Reserva reserva;
        reserva = reservas.stream()
                .filter(new Predicate<Reserva>() {
            @Override
            public boolean test(Reserva r) {
                return r.getCliente().getCpf().equals(cpfCliente) && r.getPacote().getDestino().equals(buscarPacotePorId(idPacote).getDestino());
            }
        })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));
        reserva.cancelar();
        reservas.remove(reserva);
    }

    public List<Reserva> listarReservasPorCliente(String cpfCliente) {
        return reservas.stream()
                .filter(new Predicate<Reserva>() {
            @Override
            public boolean test(Reserva r) {
                return r.getCliente().getCpf().equals(cpfCliente);
            }
        })
                .collect(Collectors.toList());
    }

    public List<PacoteViagem> listarPacotes() {
        return pacotes;
    }

    private Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
    }

    private PacoteViagem buscarPacotePorId(int id) {
        return pacotes.stream()
                .filter(p -> p.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Pacote não encontrado."));
    }
}

