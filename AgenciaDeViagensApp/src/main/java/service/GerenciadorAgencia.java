/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.Cliente;
import model.PacoteViagem;
import model.Reserva;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GerenciadorAgencia {
    private List<PacoteViagem> pacotes = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

        // Cadastro de pacotes
    public void cadastrarPacote(String destino, double preco, LocalDate partida, LocalDate retorno, int vagas) {
        
        // Lista para armazenar as mensagens de erro
        List<String> erros = new ArrayList<>();

        if (!validarDestino(destino)) {
            erros.add("Erro: O destino não pode estar vazio.");
        }

        if (!validarPreco(preco)) {
            erros.add("Erro: O preço não pode ser negativo.");
        }

        if (!validarVagas(vagas)) {
            erros.add("Erro: A quantidade de vagas não pode ser negativa.");
        }

        if (!validarDatas(partida, retorno)) {
            erros.add("Erro: A data de retorno não pode ser anterior à data de partida.");
        }

        if (!erros.isEmpty()) {
            erros.forEach(System.out::println); // Exibe cada erro
            System.out.println("Pacote não registrado. Total de erros encontrados: " + erros.size());
            return; 
        }
        
        // Cadastro do pacote, caso não haja erros
        pacotes.add(new PacoteViagem(destino, preco, partida, retorno, vagas));
        System.out.println("Pacote cadastrado com sucesso!");
    }

        private boolean validarDestino(String destino) {
            return destino != null && !destino.trim().isEmpty();
        }

        private boolean validarPreco(double preco) {
            return preco >= 0;
        }

        private boolean validarVagas(int vagas) {
            return vagas >= 0;
        }

        private boolean validarDatas(LocalDate partida, LocalDate retorno) {
            return partida != null && retorno != null && !retorno.isBefore(partida);
        }

   

    public void cadastrarCliente(String nome, String cpf, String email, String telefone) {
        // Validação do nome: não pode conter números, mas aceita acentos e caracteres especiais
        if (!nome.matches("[A-Za-zÀ-ÿ\\s]+")) {
            System.out.println("Erro: O nome não pode conter números ou caracteres inválidos.");
            return;
        }

        // Validação do CPF: permite o formato com pontos e hífen
        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            System.out.println("Erro: CPF inválido. O CPF deve estar no formato XXX.XXX.XXX-XX.");
            return;
        }

        // Validação do e-mail: deve seguir um formato básico de e-mail
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("Erro: E-mail inválido. Verifique o formato.");
            return;
        }

        // Validação do telefone: considerando formatos como (XX) XXXXX-XXXX ou XXXXXX-XXXX
        if (!telefone.matches("\\(?\\d{2}\\)? ?\\d{4,5}-?\\d{4}")) {
            System.out.println("Erro: Telefone inválido. O telefone deve estar no formato (XX) XXXXX-XXXX.");
            return;
        }

        // Se todas as validações passarem, adiciona o cliente
        clientes.add(new Cliente(nome, cpf, email, telefone));
        System.out.println("Cliente cadastrado com sucesso!");
    }


        // Reserva de pacotes
    public boolean reservarPacote(String cpf, String destino) {
        Cliente cliente = buscarClientePorCpf(cpf);
        PacoteViagem pacote = buscarPacotePorDestino(destino);

        // Verifica se o cliente e o pacote já existem
        if (cliente == null) {
            System.out.println("Erro: Cliente com CPF " + cpf + " não encontrado.");
            return false;
        }
        if (pacote == null) {
            System.out.println("Erro: Pacote com destino " + destino + " não encontrado.");
            return false;
        }

        // Verifica se o cliente já tem uma reserva para o mesmo pacote
        boolean jaReservado = reservas.stream()
                .anyMatch(r -> r.getCliente().getCpf().equals(cpf) && r.getPacote().getDestino().equalsIgnoreCase(destino));

        if (jaReservado) {
            System.out.println("Erro: O cliente com CPF " + cpf + " já possui uma reserva para o destino " + destino + ".");
            return false;
        }

        // Reserva vaga no pacote
        if (pacote.reservarVaga()) {
            reservas.add(new Reserva(cliente, pacote));
            return true;
        }

        // Caso as vagas estejam indisponíveis
        System.out.println("Erro: Não foi possível realizar a reserva. Vagas indisponíveis para o destino " + destino + ".");
        return false;
        }

    // Cancela reserva
    public boolean cancelarReserva(String cpf, String destino) {
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().getCpf().equals(cpf) && reserva.getPacote().getDestino().equalsIgnoreCase(destino)) {
                reserva.getPacote().cancelarReserva();
                reservas.remove(reserva);
                return true;
            }
        }

        return false;
    }

    // Lista todos os pacotes
    public void listarPacotes() {
        pacotes.forEach(System.out::println);
    }

    // Busca de reservas por CPF
    public void buscarReservasPorCpf(String cpf) {
        System.out.println("=== Reservas para CPF: " + cpf + " ===");
        reservas.stream()
                .filter(r -> r.getCliente().getCpf().equals(cpf))
                .forEach(System.out::println);
    }

    // Lista de pacotes com maior número de reservas
    public void listarPacotesMaisReservados() {
        Map<String, Long> contagem = reservas.stream()
                .collect(Collectors.groupingBy(r -> r.getPacote().getDestino(), Collectors.counting()));

        contagem.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Destino: " + entry.getKey() + ", Reservas: " + entry.getValue()));
    }

    // Lista de clientes com mais reservas
    public void listarClientesComMaisReservas() {
        Map<String, Long> contagem = reservas.stream()
                .collect(Collectors.groupingBy(r -> r.getCliente().getCpf(), Collectors.counting()));

        contagem.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    Cliente cliente = buscarClientePorCpf(entry.getKey());
                    System.out.println("Cliente: " + cliente.getNome() + ", CPF: " + cliente.getCpf() + ", Reservas: " + entry.getValue());
                });
    }

        private Cliente buscarClientePorCpf(String cpf) {
            return clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
        }

        private PacoteViagem buscarPacotePorDestino(String destino) {
            return pacotes.stream().filter(p -> p.getDestino().equalsIgnoreCase(destino)).findFirst().orElse(null);
        }

        public void listarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva encontrada.");
        } else {
            reservas.forEach(reserva -> System.out.println(reserva));
        }
    }

}
