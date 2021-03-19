 package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tembe
 */
public class Encomenda implements Serializable{
    // atributos
    private Produto produto;
    private Cliente cliente;
    private String localDeEntrega;
    private double total;
    private double adiantamento;
    private Date diaMarcacao;
    private Date diaEntrega;
    private List<Produto> carrinho;
    
    // construtores

    public Encomenda(Produto produto, Cliente cliente, double total, double adiantamento, Date diaMarcacao, Date diaEntrega) {
        this.produto = produto;
        this.cliente = cliente;
        this.localDeEntrega = cliente.getLocalizacao();
        this.total = total;
        this.adiantamento = adiantamento;
        this.diaMarcacao = diaMarcacao;
        this.diaEntrega = diaEntrega;
    }
    public Encomenda(Produto produto, Cliente cliente,  double total, double adiantamento) {
        this.produto = produto;
        this.cliente = cliente;
        this.localDeEntrega = cliente.getLocalizacao();
        this.total = total;
        this.adiantamento = adiantamento;
        
    }
    
    // Setters and getters

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getLocalDeEntrega() {
        return localDeEntrega;
    }

    public void setLocalDeEntrega(String localDeEntrega) {
        this.localDeEntrega = localDeEntrega;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAdiantamento() {
        return adiantamento;
    }

    public void setAdiantamento(double adiantamento) {
        this.adiantamento = adiantamento;
    }

    public Date getDiaMarcacao() {
        return diaMarcacao;
    }

    public void setDiaMarcacao(Date diaMarcacao) {
        this.diaMarcacao = diaMarcacao;
    }

    public Date getDiaEntrega() {
        return diaEntrega;
    }

    public void setDiaEntrega(Date diaEntrega) {
        this.diaEntrega = diaEntrega;
    }
    

    // aiciona os produtos ao carrinho que o clinte vai levar
    public List<Produto> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<Produto> carrinho) {
        this.carrinho = carrinho;
    }
      
    // metodo to string

    @Override
    public String toString() {
        return "Encomenda{" + "produto=" + produto.toString() + ", cliente=" + cliente.toString() + ", localDeEntrega=" + localDeEntrega + ", total=" + total + ", adiantamento=" + adiantamento + ", diaMarcacao=" + diaMarcacao + ", diaEntrega=" + diaEntrega + ", carrinho=" + carrinho + '}';
    }
    
}
