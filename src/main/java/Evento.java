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

    public void setCodigoUnico(int codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public double getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(double valorIngresso) {
        this.valorIngresso = valorIngresso;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public int getQuantidadeTotalIngressos() {
        return quantidadeTotalIngressos;
    }

    public void setQuantidadeTotalIngressos(int quantidadeTotalIngressos) {
        this.quantidadeTotalIngressos = quantidadeTotalIngressos;
    }

    public ArrayList<Ingresso> getIngressosGeral() {
        return ingressosGeral;
    }

    public ArrayList<Ingresso> getIngressosEspeciais() {
        return ingressosEspeciais;
    }

    @Override
    public String toString() {
        DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dataEvento.format(br);
        long valorArredondadocapacEsp = Math.round(quantidadeTotalIngressos * 0.15);
        int capEsp = (int) valorArredondadocapacEsp, capGer = quantidadeTotalIngressos - capEsp;

        return "--- EVENTO [" + codigoUnico + "] " + nomeEvento + " ---" +
                "\nDATA: " + date +
                "\nVALOR INGRESSO: R$ " + valorIngresso +
                "\nOFERECIDO POR: " + nomeResponsavel +
                "\nLOTAÇÃO MÁXIMA: " + quantidadeTotalIngressos + " pessoas." +
                "\nPÚBLICO GERAL: " + capGer + " ingressos." +
                "\nCONDIÇÕES ESPECIAIS: " + capEsp + " ingressos.\n";

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
