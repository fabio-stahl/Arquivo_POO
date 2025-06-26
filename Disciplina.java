import java.io.*;
import java.util.Scanner;

public class Disciplina {

    public static void cadastrar(Scanner scanner, File diretorio) {
        diretorio.mkdirs(); // Garante que o diretório exista

        System.out.println("Digite o nome da disciplina: ");
        String nome = scanner.nextLine();

        File arquivo = new File(diretorio, nome + ".txt");
        File gabarito = new File(diretorio, nome + "_gabarito.txt");

        try {
            if (arquivo.createNewFile() && gabarito.createNewFile()) {
                System.out.println("Disciplina e gabarito criados com sucesso.");
            } else {
                System.out.println("A disciplina já existe ou houve erro.");
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void cadastrarAlunos(Scanner scanner, File diretorio) {
        File[] arquivos = diretorio.listFiles();

        System.out.println("Disciplinas disponíveis:");
        if (arquivos != null) {
            for (File arq : arquivos) {
                if (arq.isFile() && arq.getName().endsWith(".txt") && !arq.getName().contains("_gabarito")) {
                    System.out.println("- " + arq.getName().replace(".txt", ""));
                }
            }
        }

        System.out.println("Digite o nome da disciplina:");
        String nome = scanner.nextLine();
        File txt = new File(diretorio, nome + ".txt");

        while (true) {
            System.out.println("Digite o gabarito do aluno ou 'sair': ");
            String respostas = scanner.nextLine();
            if (respostas.equalsIgnoreCase("sair")) break;

            System.out.println("Nome do aluno:");
            String nomeAluno = scanner.nextLine();

            Aluno aluno = new Aluno(nomeAluno, respostas);
            aluno.salvarNoArquivo(txt);
        }
    }

    public static void cadastrarGabarito(Scanner scanner, File diretorio) {
        System.out.println("Digite o nome da disciplina:");
        String nome = scanner.nextLine();
        System.out.println("Digite o gabarito correto:");
        String respostas = scanner.nextLine();

        File arquivo = new File(diretorio, nome + "_gabarito.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write(respostas);
            System.out.println("Gabarito salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar gabarito: " + e.getMessage());
        }
    }
}
