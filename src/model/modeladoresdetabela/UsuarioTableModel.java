package model.modeladoresdetabela;

import controller.UsuarioJpaController;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.table.AbstractTableModel;
import model.Usuario;

/**
 *
 * @author Tembe
 */
public class UsuarioTableModel extends AbstractTableModel {
    UsuarioJpaController usuarioJpa  = new UsuarioJpaController(Persistence.createEntityManagerFactory("projectoPU"));
    List<Usuario> lista = usuarioJpa.findUsuarioEntities();
    
    private String [] colunas = {"ID", "NOME", "CARGO","E-MAIL","MORADA ","CONTACTO","CONTRATAÇÃO"};
    
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
            case 0: return lista.get(linha).getId();
            case 1: return lista.get(linha).getNome();
            case 2: return lista.get(linha).getCargo();
            case 3: return lista.get(linha).getEmail();
            case 4: return lista.get(linha).getMorada();
            case 5: return lista.get(linha).getContacto();
            case 6: return lista.get(linha).getAnoContratacao();
            
        }
        return null;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna]; 
    }
    
    public void addRow(Usuario p){
        this.lista.add(p);
        this.fireTableDataChanged(); 
    }
    public void removeRow(int linha){
        this.lista.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public List <Usuario> getLista() {
        return lista;
    }

    public void setLista(List <Usuario> lista) {
        this.lista = lista;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }
    
}