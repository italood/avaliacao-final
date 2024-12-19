/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import service.GerenciadorAgencia;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Scanner;

public class AgenciaDeViagensApp {

    public static void main(String[] args) {
        GerenciadorAgencia gerenciador = new GerenciadorAgencia();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenuPrincipal();
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1: 
                    cadastrarPacote(scanner, gerenciador);
                    break;

                case 2:
                    cadastrarCliente(scanner, gerenciador);
                    break;

                case 3:
                    reservarOuCancelar(scanner, gerenciador);
                    break;

                case 4:
                    listarDados(scanner, gerenciador);
                    break;

                case 0:
                    System.out.println("\nEncerrando o sistema... Até mais!");
                    break;

                default:
                    System.out.println("\nOpção inválida. Por favor, tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=========================================");
        System.out.println("       Sistema de Agência de Viagens");
        System.out.println("=========================================");
        System.out.println("1. Cadastrar Pacote");
        System.out.println("2. Cadastrar Cliente");
        System.out.println("3. Reservar ou Cancelar Pacote");
        System.out.println("4. Listar Informações");
        System.out.println("0. Sair");
        System.out.println("=========================================");
    }

    private static void cadastrarPacote(Scanner scanner, GerenciadorAgencia gerenciador) {
        System.out.println("\n--- Cadastro de Pacote ---");
        System.out.print("Destino: ");
        String destino = scanner.nextLine();

        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Consome quebra de linha

        System.out.print("Data de partida (DD-MM-AAAA): ");
        String dataPartidaStr = scanner.nextLine();

        System.out.print("Data de retorno (DD-MM-AAAA): ");
        String dataRetornoStr = scanner.nextLine();

        System.out.print("Vagas disponíveis: ");
        int vagas = scanner.nextInt();
        scanner.nextLine(); // Consome quebra de linha

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            LocalDate dataPartida = LocalDate.parse(dataPartidaStr, formatter);
            LocalDate dataRetorno = LocalDate.parse(dataRetornoStr, formatter);

            gerenciador.cadastrarPacote(destino, preco, dataPartida, dataRetorno, vagas);
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido. Use o formato DD-MM-AAAA.");
        }
    }

    private static void cadastrarCliente(Scanner scanner, GerenciadorAgencia gerenciador) {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        gerenciador.cadastrarCliente(nome, cpf, email, telefone);
    }

    private static void reservarOuCancelar(Scanner scanner, GerenciadorAgencia gerenciador) {
        System.out.println("\n1. Reservar Pacote");
        System.out.println("2. Cancelar Reserva");
        System.out.print("Escolha uma opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();

        System.out.print("Destino do pacote: ");
        String destino = scanner.nextLine();

        if (escolha == 1) {
            if (gerenciador.reservarPacote(cpf, destino)) {
                System.out.println("Reserva realizada com sucesso!");
            } else {
                System.out.println("Erro ao realizar reserva. Verifique os dados.");
            }
        } else if (escolha == 2) {
            if (gerenciador.cancelarReserva(cpf, destino)) {
                System.out.println("Reserva cancelada com sucesso!");
            } else {
                System.out.println("Erro ao cancelar reserva. Verifique os dados.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void listarDados(Scanner scanner, GerenciadorAgencia gerenciador) {
        System.out.println("\n1. Listar Pacotes");
        System.out.println("2. Listar Reservas");
        System.out.println("3. Buscar Reservas por CPF");
        System.out.println("4. Listar Pacotes Mais Reservados");
        System.out.println("5. Listar Clientes com Mais Reservas");
        System.out.print("Escolha uma opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                gerenciador.listarPacotes();
                break;

            case 2:
                gerenciador.listarReservas();
                break;

            case 3:
                System.out.print("Digite o CPF do cliente: ");
                String cpf = scanner.nextLine();
                gerenciador.buscarReservasPorCpf(cpf);
                break;

            case 4:
                gerenciador.listarPacotesMaisReservados();
                break;

            case 5:
                gerenciador.listarClientesComMaisReservas();
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }
}
