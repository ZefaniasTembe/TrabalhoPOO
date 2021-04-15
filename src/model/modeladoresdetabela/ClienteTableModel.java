/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.modeladoresdetabela;

import controller.ClienteJpaController;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.table.AbstractTableModel;
import model.Cliente;

/**
 *
 * @author Tembe
 */
public class ClienteTableModel extends AbstractTableModel {

    
    
    ClienteJpaController clienteJpa  = new ClienteJpaController(Persistence.createEntityManagerFactory("projectoPU"));
    List<Cliente> lista = clienteJpa.findClienteEntities();
    
    private String [] colunas = {"ID", "NOME", "LOCALICAÇÃO"," CONT. PRINCIPAL","CONT. ALTERNATIVO","HISTÓRICO", "ULTIMA COMPRA", "E-MAIL"};
    
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
            case 2: return lista.get(linha).getLocalizacao();
            case 3: return lista.get(linha).getContactoPrincipal();
            case 4: return lista.get(linha).getContactoAlternativo();
            case 5: return lista.get(linha).getEncomendaCollection().size();
            case 6: return lista.get(linha).getUltimaCompra();
            case 7: return lista.get(linha).getEmail();
        }
        
        return null;
    }
    
    public Object getRow(int linha, int coluna) {
        
        switch(coluna){
            case 0: return lista.get(linha).getId();
            case 1: return lista.get(linha).getNome();
            case 2: return lista.get(linha).getLocalizacao();
            case 3: return lista.get(linha).getContactoPrincipal();
            case 4: return lista.get(linha).getContactoAlternativo();
            case 5: return lista.get(linha).getEncomendaCollection().size();
            case 6: return lista.get(linha).getUltimaCompra();
            case 7: return lista.get(linha).getEmail();
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

    public void setLista(List <Cliente> lista){
        this.lista = lista;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas){
        this.colunas = colunas;
    }
}   
