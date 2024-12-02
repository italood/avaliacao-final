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

public class PacoteViagem {
    int id;
    private int vagasTotais;
    private int vagasDisponiveis;
    private String destino;
    private double preco;
    private LocalDate dataPartida;
    private LocalDate dataRetorno;
    private List<Reserva> reservas;

    public PacoteViagem(int id, int vagasTotais, String destino, double preco, LocalDate dataPartida, LocalDate dataRetorno) {
        if (!validarDatas(dataPartida, dataRetorno)) {
            throw new IllegalArgumentException("Datas inválidas: a data de retorno deve ser após a data de partida.");
        }
        this.id = id;
        this.vagasTotais = vagasTotais;
        this.vagasDisponiveis = vagasTotais;
        this.destino = destino;
        this.preco = preco;
        this.dataPartida = dataPartida;
        this.dataRetorno = dataRetorno;
        this.reservas = new ArrayList<>();
    }

    public boolean validarDatas(LocalDate partida, LocalDate retorno) {
        return !partida.isAfter(retorno);
    }


    public boolean cancelarReserva() {
        if (vagasDisponiveis < vagasTotais) {
            vagasDisponiveis++;
            return true;
        }
        return false;
    }

    public String getDestino() {
        return destino;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    @Override
    public String toString() {
        return String.format("Pacote ID: %d | Destino: %s | Preço: R$%.2f | Vagas Disponíveis: %d/%d",
                id, destino, preco, vagasDisponiveis, vagasTotais);
    }

    boolean reservarVaga() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

