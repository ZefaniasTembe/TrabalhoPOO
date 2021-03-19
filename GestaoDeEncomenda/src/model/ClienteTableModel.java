package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Tembe
 */
public class ClienteTableModel extends AbstractTableModel {
    
    private List <Cliente> lista = new ArrayList<>();
    private String [] colunas = {"NOME", "CONTACTO", "LOCALIZAÇÃO","OBSERVAÇÃO"," Nº Compras "};
    
    
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
            case 0: return lista.get(linha).getNome();
            case 1: return lista.get(linha).getContacto();
            case 2: return lista.get(linha).getLocalizacao();
            case 3: return lista.get(linha).getNota();
            case 4: return lista.get(linha).getNumEncomendas();
        }
        return null;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna]; 
    }
    
    public void addRow(Cliente p){
        this.lista.add(p);
        this.fireTableDataChanged(); // esse metodo ajuda a actualizar a nossa tabela sicronizando com a lista
    }
    public void removeRow(int linha){
        this.lista.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public List <Cliente> getLista() {
        return lista;
    }

    public void setLista(List <Cliente> lista) {
        this.lista = lista;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }
    
}
