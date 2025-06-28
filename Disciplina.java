import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Disciplina {

    public static void cadastrar(Scanner scanner, File diretorio) {
        diretorio.mkdirs();

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
        String respostas;
        File txt = escolherDisciplina(scanner, diretorio);
        if (txt == null) return;

        while (true) {
            System.out.println("Para continuar digite 1, para sair digite 0:");
            int decisao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            if (decisao == 0) {
                System.out.println("Saindo do cadastro de alunos.");
                return;
            } else if (decisao != 1) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            while (true) {
            System.out.println("Digite o gabarito correto (10 letras - V ou F):");
            respostas = scanner.nextLine().toUpperCase().replaceAll("\\s+", "");

            if (respostas.length() != 10) {
                System.out.println("Erro: O gabarito deve conter exatamente 10 caracteres.");
                continue;
            }

            boolean valido = true;
            for (char c : respostas.toCharArray()) {
                if (c != 'V' && c != 'F') {
                    valido = false;
                    break;
                }
            }

            if (!valido) {
                System.out.println("Erro: Apenas caracteres V ou F são permitidos.");
            } else {
                break;
            }
        }

            scanner.nextLine(); // limpar buffer
            System.out.println("Nome do aluno:");
            String nomeAluno = scanner.nextLine();

            Aluno aluno = new Aluno(nomeAluno, respostas);
            aluno.salvarNoArquivo(txt);
        }
    }

    public static void cadastrarGabarito(Scanner scanner, File diretorio) {
        File disciplinaFile = escolherDisciplina(scanner, diretorio);
        if (disciplinaFile == null) return;

        String nome = disciplinaFile.getName().replace(".txt", "");
        File arquivo = new File(diretorio, nome + "_gabarito.txt");

        String respostas;
        while (true) {
            System.out.println("Digite o gabarito correto (10 letras - V ou F):");
            respostas = scanner.nextLine().toUpperCase().replaceAll("\\s+", "");

            if (respostas.length() != 10) {
                System.out.println("Erro: O gabarito deve conter exatamente 10 caracteres.");
                continue;
            }

            boolean valido = true;
            for (char c : respostas.toCharArray()) {
                if (c != 'V' && c != 'F') {
                    valido = false;
                    break;
                }
            }

            if (!valido) {
                System.out.println("Erro: Apenas caracteres V ou F são permitidos.");
            } else {
                break;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write(respostas);
            System.out.println("Gabarito salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar gabarito: " + e.getMessage());
        }
    }

    public static File escolherDisciplina(Scanner scanner, File diretorio) {
        File[] todosArquivos = diretorio.listFiles();
        List<File> arquivosValidos = new ArrayList<>();

        System.out.println("Disciplinas disponíveis:");
        if (todosArquivos != null) {
            int i = 0;
            for (File arq : todosArquivos) {
                if (arq.isFile() && arq.getName().endsWith(".txt") &&
                    !arq.getName().contains("_gabarito") &&
                    !arq.getName().contains("_resultado_alfabetico") &&
                    !arq.getName().contains("_resultado_notas")) {
                    
                    System.out.println(i + " - " + arq.getName().replace(".txt", ""));
                    arquivosValidos.add(arq);
                    i++;
                }
            }
        }

        if (arquivosValidos.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return null;
        }

        while (true) {
            System.out.print("Digite o número da disciplina: ");
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); // limpar o buffer

                if (escolha >= 0 && escolha < arquivosValidos.size()) {
                    return arquivosValidos.get(escolha);
                } else {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine(); // limpar o buffer
            }
        }
    }

}
