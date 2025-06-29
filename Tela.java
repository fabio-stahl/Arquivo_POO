import java.io.File;
import java.util.List;


public class Tela {
    private void limparTela(){
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            mostrarErro("Não foi possível limpar a tela.");
        }
    }

    private void darDelay(int tempo){
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            mostrarErro("Delay interrompido!");
        }
    }

    private void titulo(String nome){
        limparTela();
        System.out.println("=".repeat(nome.length() + 8));
        System.out.println("~~~ " + nome.toUpperCase() + " ~~~");
        System.out.println("=".repeat(nome.length() + 8) + "\n");
    }

    public void mostrarErro(String erro){
        System.out.print("\nErro: " + erro);
        darDelay(2000);
    }

    public void mensagemSimples(String mensagem){
        System.out.println("\n" + mensagem);
        darDelay(2000);
    }

    // menu
    public void mostrarMenu(){
        titulo("menu");

        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
        System.out.println("1 - Cadastrar Disciplina");
        System.out.println("2 - Escolher Disciplina e cadastrar Alunos");
        System.out.println("3 - Cadastrar gabarito");
        System.out.println("4 - Corrigir provas");
        System.out.println("0 - Sair");
        System.out.print("-> ");
    }

    public void mostrarSaida(){
        titulo("aguardamos seu retorno");

        System.out.print("S A I N D O ");
        for(int i = 0; i < 3; i++){
            System.out.print(". ");
            darDelay(1000);
        }
    }
    //

    public void pedirNomeDisciplina(){
        titulo("cadastrar disciplina");

        System.out.println("DIGITE O NOME DA DISCIPLINA");
        System.out.print("-> ");
    }

    public void pedirDisciplina(File[] todosArquivos, List<File> arquivosValidos){
        titulo("cadastrar alunos");

        System.out.println("DISCIPLINAS DISPONÍVEIS:");

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
        System.out.print("-> ");
    }
    public void desejaContinuar(){
        titulo("cadastrar alunos");

        System.out.println("DESEJA CONTINUAR?");
        System.out.println("1 - Sim");
        System.out.println("0 - Não");
        System.out.print("-> ");
    }
    public void pedirGabarito(String tipoCadastro, String correto){
        titulo(tipoCadastro);

        System.out.println("Digite o gabarito " + correto + " (10 letras - V ou F)");
        System.out.print("-> ");
    }
    public void pedirNomeAluno(){
        titulo("cadastrar alunos");

        System.out.println("DIGITE O NOME DO ALUNO");
        System.out.print("-> ");
    }

    public void mostrarArquivosGerados(File arquivoNota, File arquivoAlfabeto){
        titulo("corrigindo provas");

        System.out.println("ARQUIVOS GERADOS:");
        System.out.println(" - " + arquivoNota.getName());
        System.out.println(" - " + arquivoAlfabeto.getName());

        darDelay(2000);
    }
}
