import java.io.*;
import java.util.Scanner;

public class Corretor {

    public static void corrigirProvas(Scanner scanner, File diretorio) {
        System.out.println("Digite o nome da disciplina:");
        String nomeDisciplina = scanner.nextLine();

        File gabaritoFile = new File(diretorio, nomeDisciplina + "_gabarito.txt");
        File alunosFile = new File(diretorio, nomeDisciplina + ".txt");

        if (!gabaritoFile.exists() || !alunosFile.exists()) {
            System.out.println("Disciplina ou gabarito não encontrados.");
            return;
        }

        String gabarito = "";
        try (BufferedReader br = new BufferedReader(new FileReader(gabaritoFile))) {
            gabarito = br.readLine().trim();
        } catch (IOException e) {
            System.out.println("Erro ao ler gabarito: " + e.getMessage());
            return;
        }

        System.out.println("\n📄 Resultados da correção:");
        try (BufferedReader br = new BufferedReader(new FileReader(alunosFile))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" {3}"); // separa por 3 espaços
                if (partes.length < 2) continue;

                String respostasAluno = partes[0].trim();
                String nomeAluno = partes[1].trim();

                int acertos = corrigir(gabarito, respostasAluno);
                System.out.println(nomeAluno + " - Acertos: " + acertos + "/" + gabarito.length());
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler alunos: " + e.getMessage());
        }
    }

    private static int corrigir(String gabarito, String respostaAluno) {
        int acertos = 0;
        int tamanho = Math.min(gabarito.length(), respostaAluno.length());

        for (int i = 0; i < tamanho; i++) {
            if (gabarito.charAt(i) == respostaAluno.charAt(i)) {
                acertos++;
            }
        }
        return acertos;
    }
}
