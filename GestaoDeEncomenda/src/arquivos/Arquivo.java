package arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Encomenda;

/**
 *
 * @author Zefanias Tembe
 */
public class Arquivo {
    // Metodo de gravacao de dados, recebe a lista que contem os dados e a caminho do espaco a ser ocupado
    public static void escrever(ArrayList lista, String caminho){
        // cria o file
        File arquivo = new File(caminho);
        try {
            if (!arquivo.exists()){
                arquivo.mkdirs();
            }
            // apaga o arquivo antigo
            arquivo.delete();
            // cria um novo arquivo
            arquivo.createNewFile();
            
            // escreve os novos dados no arquivo...
            ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(arquivo));
            escritor.writeObject(lista);
            escritor.close();
        } catch (Exception e) {
            System.out.println("Ocorreu uma exepcao: "+e);
        }
    }
    
    // Metodo de leitura de dados a partir do arquivo, recebe o caminho do arquivo
    public static ArrayList<Object> ler(String caminho){
        ArrayList<Object> lista = null;
        try {
            
            File arquivo = new File(caminho);
            
            if (arquivo.exists()) {
                try (ObjectInputStream leitor = new ObjectInputStream(new FileInputStream(arquivo))) {
                    lista = (ArrayList<Object>)leitor.readObject();
                System.out.println(arquivo.getAbsolutePath());
                    leitor.close();
                }
            } else{
                System.out.println("Nao foi encontrado nenhum arquivo correspondente");
            }
            
        } catch (Exception e) {
            System.out.println("Erro encontrado: "+e);
        }
        return lista;
    }
    
}

