import java.util.*;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.Math;

public class Evento {
    private int codigoUnico;
    private String nomeEvento;
    private LocalDate dataEvento;
    private double valorIngresso;
    private String nomeResponsavel;
    private int quantidadeTotalIngressos;
    private ArrayList<Ingresso> ingressosGeral;
    private ArrayList<Ingresso> ingressosEspeciais;

    public Evento() {
        this.codigoUnico = 0;
        this.nomeEvento = "";
        this.dataEvento = null;
        this.valorIngresso = 0;
        this.nomeResponsavel = "";
        this.quantidadeTotalIngressos = 0;
        this.ingressosGeral = new ArrayList<>();
        this.ingressosEspeciais = new ArrayList<>();
    }

    public int getCodigoUnico() {
        return codigoUnico;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public double getValorIngresso() {
        return valorIngresso;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public int getQuantidadeTotalIngressos() {
        return quantidadeTotalIngressos;
    }

    public ArrayList<Ingresso> getIngressosGeral() {
        return ingressosGeral;
    }

    public ArrayList<Ingresso> getIngressosEspeciais() {
        return ingressosEspeciais;
    }

    public void setValorIngresso(double valor) {
        this.valorIngresso = valor;
    }

    @Override
    public String toString() {
        DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dataEvento.format(br);
        long valorArredondadocapacEsp = Math.round(quantidadeTotalIngressos * 0.15);
        int capEsp = (int) valorArredondadocapacEsp, capGer = quantidadeTotalIngressos - capEsp;

        return "EVENTO NÚMERO: " + codigoUnico
                + "\n--- " + nomeEvento + " ---" +
                "\nDATA: " + date +
                "\nVALOR INGRESSO: R$ " + valorIngresso +
                "\nOFERECIDO POR: " + nomeResponsavel +
                "\nLOTAÇÃO MÁXIMA: " + quantidadeTotalIngressos + " pessoas." +
                "\nPÚBLICO GERAL: " + capGer + " ingressos." +
                "\nCONDIÇÕES ESPECIAIS: " + capEsp + " ingressos.";

    }

    public void preenche() {
        Scanner input = new Scanner(System.in);
        System.out.println("Digite o código do Evento:");
        codigoUnico = input.nextInt();
        input.nextLine();
        System.out.println("Insira o nome do evento: ");
        nomeEvento = input.nextLine();
        boolean verif = false;
        while (verif == false) {
            System.out.println("Insira a data do evento: ");
            String data = input.nextLine();
            if (Pattern.matches("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}", data) == true) {
                DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(data, br);
                if (LocalDate.now().isAfter(date)) {
                    verif = false;
                    System.out.println("Erro: Insira uma data futura para o evento.");
                } else if (LocalDate.now().isEqual(date)) {
                    verif = false;
                    System.out.println("Erro: Insira uma data futura para o evento.");
                } else {
                    dataEvento = date;
                    verif = true;
                }

            } else {
                System.out.println("Erro: formato esperado: \"dd/MM/yyyy\"");
            }
        }
        System.out.println("Insira o valor do ingresso: ");
        double aux = input.nextDouble();
        input.nextLine();
        if (aux > 0.0) {
            valorIngresso = aux;
        } else {
            System.out.println("Erro: O valor não pode ser negativo, redefina o valor posteriormente");
        }
        System.out.println("Insira o nome do responsável pelo Evento:");
        nomeResponsavel = input.nextLine();
        System.out.println("Insira a capacidade máxima em número total de ingressos: ");
        quantidadeTotalIngressos = input.nextInt();
        input.nextLine();
    }

    public void venderIngresso(Ingresso ing) {
        long valorArredondadocapacEsp = Math.round(quantidadeTotalIngressos * 0.15);
        int capEsp = (int) valorArredondadocapacEsp, capGer = quantidadeTotalIngressos - capEsp;
        //boolean esp = ing.getEspecial();// esperar implementação da classe ingresso
        //if(esp == false){
        if (ingressosGeral.size() <= capGer) {
            ingressosGeral.add(ing);
        } else {
            System.out.println("Erro: não há mais ingressos disponíveis para essa categoria.");
        }
        // }else{
        if (ingressosEspeciais.size() <= capEsp) {
            ingressosEspeciais.add(ing);
        } else {
            System.out.println("Erro: não há mais ingressos disponíveis para essa categoria.");
        }
        //}
    }

    public void listarIngressos() {
        System.out.println("****** INGRESSOS ACESSO UNIVERSAL ******");
        for (int i = 0; i < ingressosGeral.size(); i++) {
            if (ingressosGeral.get(i) != null) {
                //ingressosGeral.get(i).toStr();
            }
        }
        System.out.println("****** INGRESSOS CONDIÇÕES ESPECIAIS ******");
        for (int i = 0; i < ingressosEspeciais.size(); i++) {
            if (ingressosEspeciais.get(i) != null) {
                //ingressosEspeciais.get(i).toStr();
            }
        }
    }

    public void listarIngressosDisponiveis() {
        long valorArredondadocapacEsp = Math.round(quantidadeTotalIngressos * 0.15);
        int capEsp = (int) valorArredondadocapacEsp, capGer = quantidadeTotalIngressos - capEsp;
        int contG = 0, contE = 0;
        for (int i = 0; i < ingressosGeral.size(); i++) {
            if (ingressosGeral.get(i) != null) {
                contG++;
            }
        }
        for (int i = 0; i < ingressosEspeciais.size(); i++) {
            if (ingressosEspeciais.get(i) != null) {
                contE++;
            }
        }
        System.out.println("------DISPONÍVEIS------" +
                "\nPublico Geral: restam " + (capGer - contG) + " ingressos." +
                "\nCondições Especiais: restam " + (capEsp - contE) + " ingressos.");

    }

    public int numIngressosVendidos() {
        int cont = 0;
        for (int i = 0; i < ingressosGeral.size(); i++) {
            if (ingressosGeral.get(i) != null) {
                cont++;
            }
        }
        for (int i = 0; i < ingressosEspeciais.size(); i++) {
            if (ingressosEspeciais.get(i) != null) {
                cont++;
            }
        }
        return cont;
    }
}
