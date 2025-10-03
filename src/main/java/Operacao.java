import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Operacao {

    private ArrayList<Evento> eventos;
    private ArrayList<Participante> participantes;
    private final Scanner input;

    public Operacao() {
        this.eventos = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.input = new Scanner(System.in);
        inicializarDados();
    }

    public void executa() {
        menu();
    }

    private void menu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("--- MENU ---");
            menuInicial();
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarNovoEvento();
                    break;
                case 2:
                    listarEventos();
                    break;
                case 3:
                    procurarEventoPorNome();
                    break;
                case 4:
                    consultarEvento();
                    break;
                case 5:
                    emitirIngresso();
                    break;
                case 6:
                    registrarEntrada();
                    break;
                case 7:
                    relatorioMensal();
                    break;
                case 8:
                    cancelaEvento();
                    break;
                case 9:
                    listarParticipantesEspeciaisDeUmEvento();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private void cadastrarNovoEvento() {
        Evento evento = new Evento();

        System.out.println("Digite o código do Evento:");
        evento.setCodigoUnico(input.nextInt());
        input.nextLine();

        System.out.println("Insira o nome do evento: ");
        evento.setNomeEvento(input.nextLine());

        boolean verif = false;
        while (!verif) {
            System.out.println("Insira a data do evento: ");
            String data = input.nextLine();
            if (Pattern.matches("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}", data)) {
                DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(data, br);

                if (LocalDate.now().isAfter(date)) {
                    System.out.println("Erro: Insira uma data futura para o evento.");
                } else if (LocalDate.now().isEqual(date)) {
                    System.out.println("Erro: Insira uma data futura para o evento.");
                } else {
                    evento.setDataEvento(date);
                    verif = true;
                }
            } else {
                System.out.println("Erro: formato esperado: \"dd/MM/yyyy\"");
            }
        }

        System.out.println("Insira o valor do ingresso: ");
        double aux = input.nextDouble();
        input.nextLine();

        if (aux > 0.0) evento.setValorIngresso(aux);
        else System.out.println("Erro: O valor não pode ser negativo, redefina o valor posteriormente");


        System.out.println("Insira o nome do responsável pelo Evento:");
        evento.setNomeResponsavel(input.nextLine());

        System.out.println("Insira a capacidade máxima em número total de ingressos: ");
        evento.setQuantidadeTotalIngressos(input.nextInt());
        input.nextLine();

        this.eventos.add(evento);
    }

    private void listarEventos() {
        System.out.println("------ EVENTOS CADASTRADOS ------\n");
        this.eventos.forEach(System.out::println);
    }

    private void procurarEventoPorNome() {
        System.out.println("Digite o nome do evento a ser procurado: ");
        String nome = input.nextLine();

        Optional<Evento> evento = this.eventos.stream()
                .filter(e -> e.getNomeEvento().equalsIgnoreCase(nome)).findFirst();

        if (evento.isEmpty()) {
            System.out.println("Nenhum evento foi encontrado com o nome: " + nome);
            return;
        }

        System.out.println(evento.get());
    }

    private void consultarEvento() {
        System.out.println("Digite o codigo do evento: ");
        int cod = input.nextInt();
        input.nextLine();

        Evento evento = getEventoById(cod);
        if (evento == null) {
            System.out.println("Evento nao encontrado. ");
            return;
        }

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("------ [" + evento.getCodigoUnico() + "] " + evento.getNomeEvento() + " ------");
            System.out.println("Escolha a o que você deseja consultar sobre esse evento:\n"
                    + "[0] Sair\n"
                    + "[1] Número total de ingressos disponíveis (lotação máxima)\n"
                    + "[2] Número de ingressos de cada tipo (normal e especial – 15%)\n"
                    + "[3] Número de ingressos vendidos de cada tipo e respectivos percentuais\n"
                    + "[4] Percentual de ocupação total do evento (total de vendidos vs lotação máxima)\n"
            );

            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("O número total de ingressos disponíveis é "
                            + evento.getQuantidadeTotalIngressos());
                    break;
                case 2:
                    System.out.println("Quantidade de ingressos para o público geral: "
                            + evento.getCapacidadeIngressos(false));

                    System.out.println("Quantidade de ingressos especiais: "
                            + evento.getCapacidadeIngressos(true));
                    break;
                case 3:
                    int vendidosGeral = evento.getQuantidadeIngressosVendidos(false);
                    int vendidosEsp = evento.getQuantidadeIngressosVendidos(true);

                    double porcGeral = ((double) vendidosGeral /
                            (evento.getQuantidadeIngressosDisponiveis(false)
                                    + evento.getQuantidadeIngressosVendidos(false))) * 100;

                    double porcEsp = ((double) vendidosEsp /
                            (evento.getQuantidadeIngressosDisponiveis(true)
                                    + evento.getQuantidadeIngressosVendidos(true))) * 100;

                    System.out.printf("Quantidade de ingressos para o público geral vendidos: %d %.2f%%\n",
                            vendidosGeral, porcGeral);

                    System.out.printf("Quantidade de ingressos especiais vendidos: %d %.2f%%\n",
                            vendidosEsp, porcEsp);
                    break;
                case 4:
                    int totalVendidos = evento.getQuantidadeIngressosVendidos(true)
                            + evento.getQuantidadeIngressosVendidos(false);
                    double percTotal = ((double) totalVendidos / evento.getQuantidadeTotalIngressos()) * 100;

                    System.out.printf("De %d ingressos, %d foram vendidos, uma ocupação de %.2f%%\n",
                            evento.getQuantidadeTotalIngressos(), totalVendidos, percTotal);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void emitirIngresso() {
        int tipo, cod, capacidadeEspecial, capacidadeGeral;
        long valorArredondadoCapacEsp; String idIngresso;

        System.out.println("Digite o codigo do evento: ");
        cod = input.nextInt();
        input.nextLine();

        Evento evento = getEventoById(cod);
        if (evento == null) {
            System.out.println("Evento nao encontrado. ");
            return;
        }

        System.out.println("Escolha o tipo de ingresso:" +
                "\n[1] - Publico Geral" +
                "\n[2] - Condicoes Especiais");
        tipo = input.nextInt();
        input.nextLine();

        valorArredondadoCapacEsp = Math.round(evento.getQuantidadeTotalIngressos() * 0.15);
        capacidadeEspecial = (int) valorArredondadoCapacEsp;
        capacidadeGeral = evento.getQuantidadeTotalIngressos() - capacidadeEspecial;

        if (tipo == 2) {
            if (evento.getIngressosEspeciais().size() < capacidadeEspecial) {
                int numero = capacidadeGeral + evento.getIngressosEspeciais().size() + 1;
                idIngresso = evento.getCodigoUnico() + "-" + numero + "E";

                System.out.println("Digite o nome do participante: ");
                String nome = input.nextLine();

                System.out.println("Digite o cpf do participante: ");
                String cpf = input.nextLine();

                Participante novoParticipante = new Participante(nome, cpf);
                Ingresso ing = new Ingresso(idIngresso, true, novoParticipante);

                evento.addIngresso(true, ing);
                participantes.add(novoParticipante);

            } else {
                System.out.println("Todos os ingressos especiais a foram emitidos.");
            }

        } else {
            if (evento.getIngressosGeral().size() < capacidadeGeral) {
                int numero = evento.getIngressosGeral().size() + 1;
                idIngresso = evento.getCodigoUnico() + "-" + numero;

                System.out.println("Digite o nome do participante: ");
                String nome = input.nextLine();

                System.out.println("Digite o cpf do participante: ");
                String cpf = input.nextLine();

                Participante novoParticipante = new Participante(nome, cpf);
                Ingresso ing = new Ingresso(idIngresso, false, novoParticipante);
                evento.addIngresso(false, ing);
                participantes.add(novoParticipante);
            } else {
                System.out.println("Todos os ingressos de público geral ja foram emitidos.");
            }
        }
    }

    private void registrarEntrada() {
        System.out.println("Digite o código do evento: ");
        int cod = input.nextInt();
        input.nextLine();

        Evento evento = getEventoById(cod);

        if (evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.println("Digite o código do ingresso: ");
        String codigoIngresso = input.nextLine();

        Ingresso ingressoEncontrado = null;
        for (Ingresso ing : evento.getIngressosGeral()) {
            if (ing.getCodigo().equals(codigoIngresso)) {
                ingressoEncontrado = ing;
                break;
            }
        }
        if (ingressoEncontrado == null) {
            for (Ingresso ing : evento.getIngressosEspeciais()) {
                if (ing.getCodigo().equals(codigoIngresso)) {
                    ingressoEncontrado = ing;
                    break;
                }
            }
        }

        if (ingressoEncontrado == null) {
            System.out.println("Ingresso não encontrado.");
            return;
        }

        ingressoEncontrado.registrarEntrada();
    }

    private void relatorioMensal() {
        boolean verif = false;

        while (!verif) {
            System.out.println("Digite um mês e ano (MM/YYYY):");
            String data = input.nextLine();

            if (Pattern.matches("[0-9]{2}[/]{1}[0-9]{4}", data)) {
                DateTimeFormatter br = DateTimeFormatter.ofPattern("MM/yyyy");
                YearMonth ym = YearMonth.parse(data, br);
                LocalDate dateInicial = ym.atDay(1).minusDays(1);
                LocalDate dateFinal = ym.atEndOfMonth().plusDays(1);

                if (LocalDate.now().isAfter(dateInicial)) {
                    System.out.println("Erro: Insira uma data futura para ver o relatório.");
                    continue;
                } else {
                    verif = true;
                }

                boolean existe = false;
                System.out.println("--- RELATÓRIO " + ym + " ---");

                for (Evento e : eventos) {
                    if (e.getDataEvento().isBefore(dateFinal) && e.getDataEvento().isAfter(dateInicial)) {
                        System.out.println(e);
                        existe = true;
                    }
                }

                if (!existe) {
                    System.out.println("Nenhum evento encotrado nesse mês!");
                } else break;
            } else {
                System.out.println("Erro: formato esperado: \"MM/yyyy\"");
            }
        }
    }

    private Evento getEventoById(int id) {
        return eventos.stream().filter(evento -> evento.getCodigoUnico() == id)
                .findFirst()
                .orElse(null);
    }

    private void cancelaEvento() {
        System.out.println("Digite o código do evento a ser cancelado: ");
        int cod = input.nextInt();
        input.nextLine();

        Evento eventoParaCancelar = getEventoById(cod);

        if (eventoParaCancelar == null) {
            System.out.println("Evento com o código " + cod + " não encontrado.");
            return;
        }

        System.out.println("\nConfirma o cancelamento do evento: "
                + eventoParaCancelar.getNomeEvento() + " (" + cod + ")? (S/N)");
        String confirmacao = input.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            this.eventos.remove(eventoParaCancelar);
            System.out.println("Evento " + eventoParaCancelar.getNomeEvento() + " (código " + cod + ") cancelado com sucesso.");
        } else {
            System.out.println("Cancelamento do evento não concluido.");
        }
    }

    private void listarParticipantesEspeciaisDeUmEvento() {
        if (participantes.isEmpty())
            System.out.println("Não há participantes cadastrados.");

        System.out.println("Digite o código do evento: ");
        int cod = input.nextInt();
        input.nextLine();

        Evento evento = getEventoById(cod);

        if (evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.println("Participantes do evento " + evento.getNomeEvento()
                + "[" + evento.getCodigoUnico() + "]");
        evento.getIngressosEspeciais().forEach(ingresso -> {
            System.out.println(ingresso.getParticipante());
        });
    }

    private void menuInicial() {
        System.out.println("[0] Sair\n" +
                "[1] Cadastrar novo evento \n" +
                "[2] Listar eventos \n" +
                "[3] Procurar evento por nome\n" +
                "[4] Consultar evento\n" +
                "[5] Emitir ingresso\n" +
                "[6] Registrar entrada\n" +
                "[7] Gerar relatório mensal\n" +
                "[8] Cancelar evento\n" +
                "[9] Listar participantes especiais\n" +
                "\nOPÇÃO:");
    }

    private void inicializarDados() {
        eventos.add(new Evento(111, "Show de Rock Internacional", LocalDate.of(2025, 11, 15),
                250.0, "João Silva", 100));
        eventos.add(new Evento(222, "Peça de Teatro Clássico", LocalDate.of(2025, 10, 22),
                120.0, "Maria Oliveira", 80));
        eventos.add(new Evento(333, "Palestra sobre IA", LocalDate.of(2025, 12, 5),
                75.0, "Carlos Pereira", 30));
        eventos.add(new Evento(444, "Festival de Comida de Rua", LocalDate.of(2026, 1, 20),
                30.0, "Ana Souza", 20));
        eventos.add(new Evento(555, "Pré-estreia de Filme", LocalDate.of(2025, 10, 10),
                45.0, "CinePlus", 40));

        participantes.add(new Participante("Adrian Fachi", "111.222.333-44"));
        participantes.add(new Participante("Dougla Silvano", "222.333.444-55"));
        participantes.add(new Participante("Ellen Miranda", "333.444.555-66"));
        participantes.add(new Participante("Guilherme Royer", "444.555.666-77"));
        participantes.add(new Participante("Julia Tietbohl", "555.666.777-88"));
        participantes.add(new Participante("Lucas Henz", "666.777.888-99"));
        participantes.add(new Participante("Raffaella Aranha", "777.888.999-00"));
        participantes.add(new Participante("Ana Lech", "888.999.000-11"));
        participantes.add(new Participante("Vicenzo Másera", "999.000.111-22"));
        participantes.add(new Participante("Luiza Militão", "000.111.222-33"));

        for (Evento e : eventos) {
            for (int i = 0; i < participantes.size(); i++) {
                boolean especial = (i % 2 == 0); // alterna especial/geral

                Ingresso ingresso = getIngresso(e, especial, i);

                if (especial) {
                    e.getIngressosEspeciais().add(ingresso);
                } else {
                    e.getIngressosGeral().add(ingresso);
                }
            }
        }
    }

    private Ingresso getIngresso(Evento e, boolean especial, int i) {
        String codigoIngresso;
        if (especial) {
            int numero = e.getIngressosEspeciais().size() + 1;
            codigoIngresso = e.getCodigoUnico() + "-" + numero + "E";
        } else {
            int numero = e.getIngressosGeral().size() + 1;
            codigoIngresso = e.getCodigoUnico() + "-" + numero;
        }

        return new Ingresso(
                codigoIngresso,
                especial,
                participantes.get(i)
        );
    }
}