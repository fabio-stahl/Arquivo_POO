import java.io.*;
import java.util.Scanner;

public class Visualizar {
    private static final Tela tela = new Tela();

    public static void visualizarNotasAlfabeticas(Scanner scanner, File diretorio) {
        File disciplinaFile = Disciplina.escolherDisciplina(scanner, diretorio);
        if (disciplinaFile == null) return;

        String nomeDisciplina = disciplinaFile.getName().replace(".txt", "");
        File arquivoAlfabetico = new File(diretorio, nomeDisciplina + "_resultado_alfabetico.txt");

        if (!arquivoAlfabetico.exists()) {
            tela.mostrarErro("Resultados não encontrados para essa disciplina. Corrija as provas primeiro.");
            return;
        }

        tela.exibirNotasFormatadas(("Notas em Ordem Alfabética - " + nomeDisciplina), arquivoAlfabetico);
        scanner.nextLine();
    }

    public static void visualizarNotasDecrescentes(Scanner scanner, File diretorio) {
        File disciplinaFile = Disciplina.escolherDisciplina(scanner, diretorio);
        if (disciplinaFile == null) return;

        String nomeDisciplina = disciplinaFile.getName().replace(".txt", "");
        File arquivoNotas = new File(diretorio, nomeDisciplina + "_resultado_notas.txt");

        if (!arquivoNotas.exists()) {
            tela.mostrarErro("Resultados não encontrados para essa disciplina. Corrija as provas primeiro.");
            return;
        }

        tela.exibirNotasFormatadas(("Notas em Ordem Decrescente - " + nomeDisciplina), arquivoNotas);
        scanner.nextLine();
    }
}