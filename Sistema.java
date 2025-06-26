import java.io.File;
import java.util.Scanner;

public class Sistema {
    private final Scanner scanner = new Scanner(System.in);
    private final File diretorio;

    public Sistema(File diretorio) {
        this.diretorio = diretorio;
    }

    public void iniciar() {
        while (true) {
            System.out.println("\nDigite a Opção desejada: ");
            System.out.println("1 - Cadastrar Disciplina");
            System.out.println("2 - Escolher Disciplina e cadastrar Alunos");
            System.out.println("3 - Cadastrar gabarito");
            System.out.println("4 - Corrigir provas");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                case 1 -> Disciplina.cadastrar(scanner, diretorio);
                case 2 -> Disciplina.cadastrarAlunos(scanner, diretorio);
                case 3 -> Disciplina.cadastrarGabarito(scanner, diretorio);
                case 4 -> Corretor.corrigirProvas(scanner, diretorio);
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
