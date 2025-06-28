import java.io.*;
import java.util.*;

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

        List<AlunoNota> listaNotas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(alunosFile))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" {3}"); // separa por 3 espaços
                if (partes.length < 2) continue;

                String respostasAluno = partes[0].trim();
                String nomeAluno = partes[1].trim();

                int acertos = corrigir(gabarito, respostasAluno);
                listaNotas.add(new AlunoNota(nomeAluno, acertos));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler alunos: " + e.getMessage());
            return;
        }

        
        List<AlunoNota> porNota = new ArrayList<>(listaNotas);
        porNota.sort(Comparator.comparingInt(AlunoNota::getNota).reversed());

        
        List<AlunoNota> porNome = new ArrayList<>(listaNotas);
        porNome.sort(Comparator.comparing(AlunoNota::getNome));

        
        File arquivoNota = new File(diretorio, nomeDisciplina + "_resultado_notas.txt");
        File arquivoAlfabeto = new File(diretorio, nomeDisciplina + "_resultado_alfabetico.txt");

        salvarResultados(arquivoNota, porNota);
        salvarResultados(arquivoAlfabeto, porNome);

        System.out.println("✅ Arquivos gerados:");
        System.out.println(" - " + arquivoNota.getName());
        System.out.println(" - " + arquivoAlfabeto.getName());
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

    private static void salvarResultados(File arquivo, List<AlunoNota> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (AlunoNota alunoNota : lista) {
                bw.write(alunoNota.getNome() + " - Nota: " + alunoNota.getNota());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar resultados: " + e.getMessage());
        }
    }

    
    private static class AlunoNota {
        private final String nome;
        private final int nota;

        public AlunoNota(String nome, int nota) {
            this.nome = nome;
            this.nota = nota;
        }

        public String getNome() {
            return nome;
        }

        public int getNota() {
            return nota;
        }
    }
}
