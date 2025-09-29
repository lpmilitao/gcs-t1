import java.util.ArrayList;
import java.util.List;

public class GeradorDeIngressos {

    String codigoDoEvento;
    int totalDeIngressos;

    public GeradorDeIngressos(String codigo, int total) {
        this.codigoDoEvento = codigo;
        this.totalDeIngressos = total;
    }

    public List<String> gerar() {
        List<String> listaDeCodigos = new ArrayList<>();

        if (this.totalDeIngressos <= 0) {
            return listaDeCodigos;
        }

        int quantidadeEspeciais = (int) Math.ceil(this.totalDeIngressos * 0.15);
        int quantidadeNormais = this.totalDeIngressos - quantidadeEspeciais;

        for (int i = 1; i <= quantidadeNormais; i++) {
            String numeroFormatado;
            if (i < 10) {
                numeroFormatado = "00" + i;
            } else if (i < 100) {
                numeroFormatado = "0" + i;
            } else {
                numeroFormatado = "" + i;
            }
            listaDeCodigos.add(this.codigoDoEvento + "-" + numeroFormatado);
        }

        for (int i = quantidadeNormais + 1; i <= this.totalDeIngressos; i++) {
            String numeroFormatado;
            if (i < 10) {
                numeroFormatado = "00" + i;
            } else if (i < 100) {
                numeroFormatado = "0" + i;
            } else {
                numeroFormatado = "" + i;
            }
            listaDeCodigos.add(this.codigoDoEvento + "-" + numeroFormatado + "E");
        }

        return listaDeCodigos;
    }
}