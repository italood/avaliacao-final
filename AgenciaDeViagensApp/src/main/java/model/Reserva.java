/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Reserva {
    
    private Cliente cliente;
    private PacoteViagem pacote;

    public Reserva(Cliente cliente, PacoteViagem pacote) {
        this.cliente = cliente;
        this.pacote = pacote;
    }

    public Cliente getCliente() { return cliente; }
    public PacoteViagem getPacote() { return pacote; }

   @Override
public String toString() {
    return "Cliente: " + cliente.getNome() + " (CPF: " + cliente.getCpf() + "), Pacote: " +
           pacote.getDestino() + " (Preço: R$" + pacote.getPreco() + ")";
}
   
}
