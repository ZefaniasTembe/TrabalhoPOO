package model;

import java.util.ArrayList;
import java.util.List;

public class Testes {
    
    public static void main(String[] args) {
        // Criando um caminho para as encomendas;
        String caminho ="encomendas.dat";
        
        // TESTANDO O FUNCIONMENTO DAS CLASSES
        
        //Criando o container de encomendas
        //Stack< Encomenda> lista = new Stack<>();
        ArrayList<Encomenda> lista = new ArrayList<>();
        
        Cliente c  = new Cliente("846517462" ,"Zefanias Tembe","Masculino","A. Neto");
        Cliente c1 = new Cliente("846517462", "Rodson", "Masculino", "Kumbeza");
        Cliente c2 = new Cliente("846517462", "Calidia", "Feminino", "Bobole");
        
        Produto p  = new Produto("Samsung A2", "Celulares", 15, 9000.50);
        Produto p1 = new Produto("Samsung A20", "Celulares", 15, 9000.50);
        Produto p2 = new Produto("Samsung A25", "Celulares", 15, 9000.50);
        
        // criando a propria encomena
        Encomenda e = new Encomenda(p, c,  1, 1);
        Encomenda e1 = new Encomenda(p1, c1,  1, 1);
        Encomenda e2 = new Encomenda(p2, c2, 1, 1);
        lista.add(e);
        lista.add(e1);
        lista.add(e2);
        
        
        // gravando as encomendas no arquivo
        arquivos.Arquivo.escrever(lista, caminho);
        
        ArrayList<Object> list = null;
        list = arquivos.Arquivo.ler(caminho);
        
        if (list != null){
            for (Object encomenda : list) {
                Encomenda ea = (Encomenda) encomenda;

                System.out.println("Listando: "+ea.getProduto());
                System.out.println("Listando: "+ea.getCliente());
                System.out.println("Listando: "+ea.getLocalDeEntrega());
                System.out.println("Listando: "+ea.getClass());

                System.out.println();
                System.out.println(list.size());
                System.out.println();
            }
        } else System.out.println("A lista esta vazia");
 
    }
}
