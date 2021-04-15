/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.modeladoresdetabela;

import controller.ProdutoJpaController;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.table.AbstractTableModel;
import model.Categoria;
import model.Produto;

/**
 *
 * @author Tembe
 */
public class ProdutoTableModel extends AbstractTableModel{
    ProdutoJpaController prodJpa  = new ProdutoJpaController(Persistence.createEntityManagerFactory("projectoPU"));
    List<Produto> lista = prodJpa.findProdutoEntities();
    
    private String [] colunas = {"ID", "DESCRIÇÃO", "REFERÊNCIA","QUANTIDADE"," ALERTA ","P. VENDA","P. COMPRA", "VALOR ESPERADO"};
    
    
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
            case 1: return lista.get(linha).getDescricao();
            case 2: return lista.get(linha).getReferencia();
            case 3: return lista.get(linha).getQuantidade();
            case 4: return lista.get(linha).getAlerta();
            case 5: return lista.get(linha).getPrecoVenda();
            case 6: return lista.get(linha).getPrecoCompra();
            case 7: return lista.get(linha).getPrecoCompra()*lista.get(linha).getQuantidade();
            
            
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
    
    /**
     * 
     * @param lista permite actualizar a lista dos produtos
     */
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
