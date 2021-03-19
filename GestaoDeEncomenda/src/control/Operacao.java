package control;

import java.util.ArrayList;

/**
 *
 * @author Tembe
 */
public class Operacao {
    
    /**
     *  
     * 
     * @param lista  esse metodo recebe a lista e a posicao do que deve ser apagada e no final salva a lista no arquivo
     * @param pos 
     * @param caminho 
     * 
     */
    public static void apagar(ArrayList lista, int pos, String caminho){
        lista.remove(pos);
        
        arquivos.Arquivo.escrever(lista, caminho);
    }
}
