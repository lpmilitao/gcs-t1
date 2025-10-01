import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Operacao {

    private ArrayList<Evento> eventos;
    private final Scanner input;

    public Operacao() {
        this.eventos = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    public void executa() {
        menu();
    }

    private void menu() {
        // TODO
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
                    procurarEventoPorNome();
                    break;
                case 5:
                    emitirIngresso();
                    break;
                case 8:
                    relatorioMensal();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private void cadastrarNovoEvento() {
        /* TODO
        O sistema deverá permitir ao operador cadastrar novos eventos.
        Deve-se validar que a data é futura (não se pode cadastrar um evento no dia atual ou no passado).
        Todos os campos são obrigatórios.

        a) Quando um evento for cadastrado, o sistema deverá atribuir um código único ao evento.
        b) Ao cadastrar um evento, o usuário deverá informar quantos ingressos estarão disponíveis.

            i) O sistema deverá reservar 15% dos ingressos disponíveis para casos especiais
            (ex. cadeirantes, deficientes físicos e outras situações).

            ii) Os ingressos deverão ser identificados como {cod-evento}{seq-ingresso}{E}.
            Por exemplo, para um evento cujo código único é 121, com 100 lugares, os ingressos deverão ser
            identificados como 121-001; 121-002; 121-003 ... até 121-074
            e mais os 15% especiais, identificados como 121-075E, 121-076E ... até 121-100E.
         */
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
        // TODO
        // 3) O sistema deverá permitir ao operador listar todos os eventos
        System.out.println("------ EVENTOS CADASTRADOS ------\n");
        this.eventos.forEach(System.out::println);
    }

    private void procurarEventoPorNome() {
        //4) O sistema deverá permitir ao operador procurar por um evento específico (por parte do nome).
        System.out.println("Digite o nome do evento a ser procurado: ");
        String nome = input.nextLine();

        Optional<Evento> evento = this.eventos.stream()
                .filter(e -> e.getNomeEvento().equalsIgnoreCase(nome))
                .findFirst();

        if (evento.isEmpty()) {
            System.out.println("Nenhum evento foi encontrado com o nome: " + nome);
            return;
        }

        System.out.println(evento.get());
    }

    private void consultarEvento() {
        /* TODO
        7) O sistema deverá permitir ao operador consultar os detalhes de um evento específico, incluindo:
            a) Número total de ingressos disponíveis (lotação máxima)
            b) Número de ingressos de cada tipo (normal e especial – 15%)
            c) Número de ingressos vendidos de cada tipo e respectivos percentuais
            d) Percentual de ocupação total do evento (total de vendidos vs lotação máxima).
         */
    }

    private void emitirIngresso() {
        /* TODO
        5) Depois que o operador abrir os detalhes de um evento, poderá emitir ingressos
        (dentro do limite especificado na criação do evento)
            a) O operador deverá indicar o tipo de ingresso (normal ou especial)
            b) Os ingressos deverão ser emitidos sequencialmente
                (1, 2, 3 etc e respeitando a quantidade de ingressos normais e especiais)
            c) Não poderão ser emitidos mais ingressos do que o limite do evento.
         */
        int tipo = 0;
        int cod = 0;
        long valorArredondadoCapacEsp = 0;
        int capEsp = 0;
        int capGer = 0;
        String idIngresso;
        Ingresso ing;
        System.out.println("Digite o codigo do evento: ");
        cod = input.nextInt();
        input.nextLine(); //limpa

        Evento evento = null;
        for(Evento e : eventos){
            if(e.getCodigoUnico() == cod){
                evento = e;
                break;
            }
        }
        if(evento == null){
            System.out.println("Evento nao encontrado. ");
            return;
        }
        System.out.println("Escolha o tio de ingresso: " + "\n" + "[1] - Publico Geral "+ "\n" + "[2] - Condicoes Esppeciais");
        tipo = input.nextInt();
        input.nextLine(); //limpa

        valorArredondadoCapacEsp = Math.round(evento.getQuantidadeTotalIngressos() * 0.15);
        capEsp = (int) valorArredondadoCapacEsp;
        capGer = evento.getQuantidadeTotalIngressos() - capEsp;

        if(tipo==1){
            if(evento.getIngressosGeral().size()<capGer){
                int numero = evento.getIngressosGeral().size()+1;
                idIngresso = evento.getCodigoUnico() + "-" + numero; // id juntando o cod unico com numero
                ing = new Ingresso(idIngresso, false);
                evento.getIngressosGeral().add(ing);
            }else{
                System.out.println("Todos os ingressos de Publico Geral ja foram emitidos.");
                return;
            }
        }else if(tipo ==2){
            if(evento.getIngressosGeral().size()<capGer){
                int numero = capGer + evento.getIngressosGeral().size()+1;
                idIngresso = evento.getCodigoUnico() + "-" + numero + "E"; //E no final de especial
                ing = new Ingresso(idIngresso, true);
                evento.getIngressosEspeciais().add(ing);
            }else{
                System.out.println("Todos os ingressos Especiais a foram emitidos.");
                return;
            }
        }
    }

    private void registrarEntrada() {

       // TODO
                System.out.println("Digite o código do evento: ");
        int cod = input.nextInt();
        input.nextLine();

        Evento evento = null;
        for (Evento e : eventos) {
            if (e.getCodigoUnico() == cod) {
                evento = e;
                break;
            }
        }
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
        /*
        TODO
        8) O sistema deverá permitir ao operador gerar um relatório geral por mês:
            a) Escolher mês e ano
            b) Mostar cada evento naquele mês e naquele ano, bem como estatísticas de cada evento.
         */

        boolean verif = false;
        while (!verif) {
            System.out.println("Digite um mês e ano (MM/YYYY):");
            String data = input.nextLine();
            if (Pattern.matches("[0-9]{2}[/]{1}[0-9]{4}", data)) {
                DateTimeFormatter br = DateTimeFormatter.ofPattern("MM/yyyy");
                YearMonth ym = YearMonth.parse(data, br);
                LocalDate dateInicial = ym.atDay(1);
                LocalDate dateFinal = ym.atEndOfMonth();

                if (LocalDate.now().isAfter(dateInicial)) {
                    System.out.println("Erro: Insira uma data futura para ver o relatório.");
                } else {
                    verif = true;
                }

                boolean existe = false;
                System.out.println("--- RELATÓRIO "+ ym +" ---");

                for(Evento e: eventos) {
                    if (e.getDataEvento().isBefore(dateFinal) && e.getDataEvento().isAfter(dateInicial)) {
                        System.out.println(e);
                        existe = true;
                    }
                }

                if (!existe) {
                    System.out.println("Nenhum evento encotrado nesse mês!");
                }
            } else {
                System.out.println("Erro: formato esperado: \"MM/yyyy\"");
            }
        }

    }

    private void menuInicial() {
        System.out.println("[1] Cadastrar novo evento \n[2] Listar eventos \n[3] Procurar evento por nome");
    }


}
