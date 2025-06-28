import java.io.*;
import java.util.*;

public class Corretor {

    public static void corrigirProvas(Scanner scanner, File diretorio) {
        // Usa o método reutilizável da classe Disciplina
        File disciplinaFile = Disciplina.escolherDisciplina(scanner, diretorio);
        int media = 0;
        if (disciplinaFile == null) return; // Nenhuma disciplina foi escolhida

        String nomeDisciplina = disciplinaFile.getName().replace(".txt", "");
        File gabaritoFile = new File(diretorio, nomeDisciplina + "_gabarito.txt");

        if (!gabaritoFile.exists()) {
            System.out.println("❌ Gabarito não encontrado para essa disciplina.");
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
        int total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(disciplinaFile))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" {3}"); // separa por 3 espaços
                if (partes.length < 2) continue;

                String respostasAluno = partes[0].trim();
                String nomeAluno = partes[1].trim();

                int acertos = corrigir(gabarito, respostasAluno);
                total += acertos; // Acumula o total de acertos
                listaNotas.add(new AlunoNota(nomeAluno, acertos));
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao ler respostas dos alunos: " + e.getMessage());
            return;
        }
        
        // Ordenações
        List<AlunoNota> porNota = new ArrayList<>(listaNotas);
        porNota.sort(Comparator.comparingInt(AlunoNota::getNota).reversed());

        List<AlunoNota> porNome = new ArrayList<>(listaNotas);
        porNome.sort(Comparator.comparing(AlunoNota::getNome));
        media = total / listaNotas.size();
        porNota.add(new AlunoNota("Média", media));
        porNome.add(new AlunoNota("Média", media));


        // Arquivos de saída
        File arquivoNota = new File(diretorio, nomeDisciplina + "_resultado_notas.txt");
        File arquivoAlfabeto = new File(diretorio, nomeDisciplina + "_resultado_alfabetico.txt");

        salvarResultados(arquivoNota, porNota);
        salvarResultados(arquivoAlfabeto, porNome);

        System.out.println("✅ Arquivos gerados:");
        System.out.println(" - " + arquivoNota.getName());
        System.out.println(" - " + arquivoAlfabeto.getName());
    }

    private static int corrigir(String gabarito, String respostaAluno) {
        if (respostaAluno.equals("VVVVVVVVVV") || respostaAluno.equals("FFFFFFFFFF")) {
            return 0;            
        }
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

    // Classe auxiliar interna para guardar nome e nota
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
