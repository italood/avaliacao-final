/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class PacoteViagem {
    
    private String destino;
    private double preco;
    private LocalDate dataPartida;
    private LocalDate dataRetorno;
    private int vagasDisponiveis;

    public PacoteViagem(String destino, double preco, LocalDate dataPartida, LocalDate dataRetorno, int vagas) {
        this.destino = destino;
        this.preco = preco;
        this.dataPartida = dataPartida;
        this.dataRetorno = dataRetorno;
        this.vagasDisponiveis = vagas;
    }

    public String getDestino() { return destino; }
    public double getPreco() { return preco; }
    public LocalDate getDataPartida() { return dataPartida; }
    public LocalDate getDataRetorno() { return dataRetorno; }
    public int getVagasDisponiveis() { return vagasDisponiveis; }

    public boolean reservarVaga() {
        if (vagasDisponiveis > 0) {
            vagasDisponiveis--;
            return true;
        }
        return false;
    }

    public void cancelarReserva() {
        vagasDisponiveis++;
    }

    @Override
    public String toString() {
        return "Destino: " + destino + ", Preço: R$" + preco + ", Partida: " + dataPartida +
               ", Retorno: " + dataRetorno + ", Vagas: " + vagasDisponiveis;
    }
    
}
