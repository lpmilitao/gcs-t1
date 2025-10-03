import java.util.List;

public class Participante {
    private String nome;
    private String cpf;

    public Participante(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public static boolean cpfJaCadastrado(List<Participante> participantes, String cpf) {
        for (Participante p : participantes) {
            if (p.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
