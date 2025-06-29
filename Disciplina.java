import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Disciplina {
    private static final Tela tela = new Tela();

    public static void cadastrar(Scanner scanner, File diretorio) {
        diretorio.mkdirs();

        tela.pedirNomeDisciplina();
        String nome = scanner.nextLine();

        File arquivo = new File(diretorio, nome + ".txt");
        File gabarito = new File(diretorio, nome + "_gabarito.txt");

        try {
            if (arquivo.createNewFile() && gabarito.createNewFile()) {
                tela.mensagemSimples("Disciplina e gabarito criados com sucesso.");
            } else {
                tela.mostrarErro("A disciplina já existe ou houve erro.");
            }
        } catch (IOException e) {
            tela.mostrarErro("Erro: " + e.getMessage());
        }
    }

    public static void cadastrarAlunos(Scanner scanner, File diretorio) {
        String respostas;
        File txt = escolherDisciplina(scanner, diretorio);
        if (txt == null) return;

        while (true) {
            tela.desejaContinuar();
            int decisao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            if (decisao == 0) {
                tela.mensagemSimples("Saindo do cadastro de alunos.");
                return;
            } else if (decisao != 1) {
                tela.mostrarErro("Opção inválida. Tente novamente.");
                continue;
            }

            while (true) {
                tela.pedirGabaritoCadastrarAluno();
                respostas = scanner.nextLine().toUpperCase().replaceAll("\\s+", "");

                if (respostas.length() != 10) {
                    tela.mostrarErro("O gabarito deve conter exatamente 10 caracteres.");
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
                    tela.mostrarErro("Apenas caracteres V ou F são permitidos.");
                } else {
                    break;
                }
            }

            scanner.nextLine(); // limpar buffer
            tela.pedirNomeAluno();
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
            tela.pedirDisciplina(todosArquivos, arquivosValidos);
        }

        if (arquivosValidos.isEmpty()) {
            tela.mensagemSimples("Nenhuma disciplina cadastrada.");
            return null;
        }

        while (true) {
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); // limpar o buffer

                if (escolha >= 0 && escolha < arquivosValidos.size()) {
                    return arquivosValidos.get(escolha);
                } else {
                    tela.mostrarErro("Número inválido. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                tela.mostrarErro("Entrada inválida. Digite um número.");
                scanner.nextLine(); // limpar o buffer
            }
        }
    }

}
