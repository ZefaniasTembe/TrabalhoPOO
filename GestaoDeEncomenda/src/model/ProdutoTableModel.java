package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Tembe
 */
public class ProdutoTableModel extends AbstractTableModel {
    
    private List <Produto> lista = new ArrayList<>();
    private String [] colunas = {"CATEGORIA", "NOME", "PREÇO","QUANTIDADE","SUB-TOTAL"};
    
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        
        switch(coluna){
            case 0: return lista.get(linha).getCategoria();
            case 1: return lista.get(linha).getNome();
            case 2: return lista.get(linha).getPreco();
            case 3: return lista.get(linha).getQuantidade();
            case 4: return lista.get(linha).getSubTotal();
        }
        return null;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna]; 
    }
    
    public void addRow(Produto p){
        this.lista.add(p);
        this.fireTableDataChanged(); // esse metodo ajuda a actualizar a nossa tabela sicronizando com a lista
    }
    public void removeRow(int linha){
        this.lista.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public List <Produto> getLista() {
        return lista;
    }

    public void setLista(List <Produto> lista) {
        this.lista = lista;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }
}
