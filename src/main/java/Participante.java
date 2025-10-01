import java.util.List;

public class Participante {
    public String nome;
    public String cpf;


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



    @Override
    public String toString() {
        return "Participante{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';


    }
}