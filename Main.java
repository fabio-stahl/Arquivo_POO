import java.io.File;

public class Main {
    public static void main(String[] args) {
        File diretorio = new File("Arquivo_POO\\arquivos_txt");
        diretorio.mkdirs(); // Garante que o diretório exista

        Sistema sistema = new Sistema(diretorio);
        sistema.iniciar();
    }
}
