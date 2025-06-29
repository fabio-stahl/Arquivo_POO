import java.io.*;

public class Aluno {
    private String nome;
    private String gabarito;
    private Tela tela = new Tela();

    public Aluno(String nome, String gabarito) {
        this.nome = nome;
        this.gabarito = gabarito;
    }

    public void salvarNoArquivo(File arquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(gabarito + "   " + nome);
            writer.newLine();
        } catch (IOException e) {
            tela.mostrarErro("Algo deu errado ao salvar aluno -> " + e.getMessage());
        }
    }
}
