import java.io.File;
import java.util.Scanner;

public class Sistema {
    private final Scanner scanner = new Scanner(System.in);
    private final File diretorio;
    private final Tela tela = new Tela();

    public Sistema(File diretorio) {
        this.diretorio = diretorio;
    }

    public void iniciar() {
        while (true) {
            tela.mostrarMenu();
            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                tela.mostrarErro("Entrada inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 0 -> {
                    tela.mostrarSaida();
                    scanner.close();
                    return;
                }
                case 1 -> Disciplina.cadastrar(scanner, diretorio);
                case 2 -> Disciplina.cadastrarAlunos(scanner, diretorio);
                case 3 -> Disciplina.cadastrarGabarito(scanner, diretorio);
                case 4 -> Corretor.corrigirProvas(scanner, diretorio);
                default -> tela.mostrarErro("Opção inválida.");
            }
        }
    }
}
