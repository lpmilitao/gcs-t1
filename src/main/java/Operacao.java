import java.util.ArrayList;

public class Operacao {

    private ArrayList<Evento> eventos;

    public Operacao() {
        this.eventos = new ArrayList<>();
    }

    public void executa(){
        menu();
    }

    private void menu(){
        // TODO
    }

    private void cadastrarNovoEvento(){
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
    }

    private void listarEventos(){
        // TODO
        // 3) O sistema deverá permitir ao operador listar todos os eventos
    }

    private void procurarEventoPorNome(){
        //4) O sistema deverá permitir ao operador procurar por um evento específico (por parte do nome).
    }

    private void consultarEvento(){
        /* TODO
        7) O sistema deverá permitir ao operador consultar os detalhes de um evento específico, incluindo:
            a) Número total de ingressos disponíveis (lotação máxima)
            b) Número de ingressos de cada tipo (normal e especial – 15%)
            c) Número de ingressos vendidos de cada tipo e respectivos percentuais
            d) Percentual de ocupação total do evento (total de vendidos vs lotação máxima).
         */
    }

    private void emitirIngresso(){
        /* TODO
        5) Depois que o operador abrir os detalhes de um evento, poderá emitir ingressos
        (dentro do limite especificado na criação do evento)
            a) O operador deverá indicar o tipo de ingresso (normal ou especial)
            b) Os ingressos deverão ser emitidos sequencialmente
                (1, 2, 3 etc e respeitando a quantidade de ingressos normais e especiais)
            c) Não poderão ser emitidos mais ingressos do que o limite do evento.
         */
    }

    private void registrarEntrada(){
        /*
        TODO
        6) O sistema deverá permitir ao operador (no dia do evento) registrar a entrada de cada participante
        mediante a apresentação do ingresso. Para cada participante, o sistema deverá registrar a presença,
        de modo que posteriormente possamos saber quem esteve e quem não esteve no evento (o ingresso foi emitido,
        mas o participante não compareceu).
         */
    }

    private void relatorioMensal(){
        /*
        TODO
        8) O sistema deverá permitir ao operador gerar um relatório geral por mês:
            a) Escolher mês e ano
            b) Mostar cada evento naquele mês e naquele ano, bem como estatísticas de cada evento.
         */
    }


}
