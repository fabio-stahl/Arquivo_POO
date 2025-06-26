import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File diretorio = new File("Arquivo_POO\\arquivos_txt");
        diretorio.mkdirs(); // Garante que o diretório exista

        Scanner scanner = new Scanner(System.in);
        int esc;

        while (true) {
            System.out.println("\nDigite a Opção desejada: ");
            System.out.println("1 - Cadastrar Disciplina");
            System.out.println("2 - Escolher Disciplina e cadastrar Alunos");
            System.out.println("3 - Cadastrar gabarito");
            System.out.println("4 - Corrigir provas");
            System.out.println("0 - Sair");

            esc = scanner.nextInt();
            scanner.nextLine(); // limpar o buffer

            switch (esc) {
                case 0:
                    System.out.println("Saindo...");
                    return;

                case 1:
                    System.out.println("Digite o nome da disciplina: ");
                    String disciplina = scanner.nextLine();

                    File file = new File(diretorio, disciplina + ".txt");
                    File gaba = new File(diretorio, disciplina + "_gabarito.txt");

                    try {
                        boolean criadoArquivo = file.createNewFile();
                        boolean criadoGabarito = gaba.createNewFile();

                        if (criadoArquivo && criadoGabarito) {
                            System.out.println("Disciplina e gabarito cadastrados com sucesso!");
                        } else {
                            System.out.println("A disciplina já existe ou houve um erro.");
                        }
                    } catch (IOException e) {
                        System.out.println("Erro ao criar arquivos: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Escolher Disciplina");
                    File[] arquivos = diretorio.listFiles();

                    if (arquivos != null) {
                        for (File arquivo : arquivos) {
                            if (arquivo.isFile() && arquivo.getName().endsWith(".txt") && !arquivo.getName().contains("_gabarito")) {
                                System.out.println(" - " + arquivo.getName().replace(".txt", ""));
                            }
                        }
                    }

                    System.out.println("Digite o nome da disciplina escolhida: ");
                    String disciplinaEscolhida = scanner.nextLine();
                    File txt = new File(diretorio, disciplinaEscolhida + ".txt");

                    while (true) {
                        System.out.println("Digite o gabarito do aluno ou 'sair' para finalizar: ");
                        String gabaritoAluno = scanner.nextLine();
                        if (gabaritoAluno.equalsIgnoreCase("sair")) break;

                        System.out.println("Digite o nome do aluno: ");
                        String nomeAluno = scanner.nextLine();

                        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(txt, true))) {
                            escritor.write(gabaritoAluno + "   " + nomeAluno);
                            escritor.newLine();
                        } catch (IOException e) {
                            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                        }
                    }
                    break; // <- ESSENCIAL

                case 3:
                    System.out.println("Cadastrar gabarito");
                    System.out.println("Digite o nome da disciplina: ");
                    String nomeDisciplina = scanner.nextLine();
                    System.out.println("Digite o gabarito correto: ");
                    String gabarito = scanner.nextLine();

                    File gabaritoFile = new File(diretorio, nomeDisciplina + "_gabarito.txt");

                    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(gabaritoFile))) {
                        escritor.write(gabarito);
                        System.out.println("Gabarito cadastrado com sucesso!");
                    } catch (IOException e) {
                        System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Funcionalidade de correção de provas ainda não implementada.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
