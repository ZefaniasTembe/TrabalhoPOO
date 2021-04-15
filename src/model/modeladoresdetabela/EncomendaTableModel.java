/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.modeladoresdetabela;

import controller.EncomendaJpaController;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.table.AbstractTableModel;
import model.Encomenda;

/**
 *
 * @author Tembe
 */
public class EncomendaTableModel extends AbstractTableModel {
    EncomendaJpaController encJpa  = new EncomendaJpaController(Persistence.createEntityManagerFactory("projectoPU"));
    List<Encomenda> lista = encJpa.findEncomendaEntities();
    
    private String [] colunas = {"ID", "DIA ENTREGA", "ESTADO ENTREGA","CLIENTE","ENDEREÇO ","CONTACTO","PRODUTOS", "PAGAMENTO"};
    
    
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
            case 1: return lista.get(linha).getEntregaDia();
            case 2: return lista.get(linha).getEstadoEntrega();
            case 3: return lista.get(linha).getClienteId().getNome();
            case 4: return lista.get(linha).getClienteId().getLocalizacao();
            case 5: return lista.get(linha).getClienteId().getContactoPrincipal();
            case 6: return lista.get(linha).getProdutoid().getEncomendaCollection().size();
            case 7: return lista.get(linha).getTotalPorPagar();
            
            
        }
        return null;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna]; 
    }
    
    public void addRow(Encomenda p){
        this.lista.add(p);
        this.fireTableDataChanged(); // esse metodo ajuda a actualizar a nossa tabela sicronizando com a lista
    }
    public void removeRow(int linha){
        this.lista.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public List <Encomenda> getLista() {
        return lista;
    }
    
    /**
     * 
     * @param lista perimite actualizar a lista a qualquer momento 
     */
    public void setLista(List <Encomenda> lista) {
        this.lista = lista;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }
    
    
}
