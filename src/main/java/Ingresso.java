public class Ingresso {

    private String codigo;
    private boolean especial;
    private Participante participante;
    private boolean utilizado;

    public Ingresso(String codigo, boolean especial) {
        this.codigo = codigo;
        this.especial = especial;
        this.utilizado = false;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isEspecial() {
        return especial;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    public void registrarEntrada() {
        this.utilizado = true;
    }


    @Override
    public String toString() {
        return "Ingresso{" +
                "codigo='" + codigo + '\'' +
                ", especial=" + especial +
                //", participante=" + (participante != null ? participante.getNome() : "Não associado") +
                ", utilizado=" + utilizado +
                '}';
    }
}