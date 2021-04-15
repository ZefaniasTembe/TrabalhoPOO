/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tembe
 */
@Entity
@Table(name = "produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p")
    , @NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p WHERE p.id = :id")
    , @NamedQuery(name = "Produto.findByDescricao", query = "SELECT p FROM Produto p WHERE p.descricao = :descricao")
    , @NamedQuery(name = "Produto.findByPrecoVenda", query = "SELECT p FROM Produto p WHERE p.precoVenda = :precoVenda")
    , @NamedQuery(name = "Produto.findByPrecoCompra", query = "SELECT p FROM Produto p WHERE p.precoCompra = :precoCompra")
    , @NamedQuery(name = "Produto.findByQuantidade", query = "SELECT p FROM Produto p WHERE p.quantidade = :quantidade")
    , @NamedQuery(name = "Produto.findByAlerta", query = "SELECT p FROM Produto p WHERE p.alerta = :alerta")
    , @NamedQuery(name = "Produto.findByReferencia", query = "SELECT p FROM Produto p WHERE p.referencia = :referencia")
    , @NamedQuery(name = "Produto.findByUltimoAbastecimento", query = "SELECT p FROM Produto p WHERE p.ultimoAbastecimento = :ultimoAbastecimento")
    , @NamedQuery(name = "Produto.findByCategoria", query = "SELECT p FROM Produto p WHERE p.categoria = :categoria")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco_venda")
    private Double precoVenda;
    @Column(name = "preco_compra")
    private Double precoCompra;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "alerta")
    private Integer alerta;
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "ultimo_abastecimento")
    @Temporal(TemporalType.DATE)
    private Date ultimoAbastecimento;
    @Column(name = "categoria")
    private Integer categoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produtoid")
    private Collection<Encomenda> encomendaCollection;

    public Produto() {
    }

    public Produto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getAlerta() {
        return alerta;
    }

    public void setAlerta(Integer alerta) {
        this.alerta = alerta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getUltimoAbastecimento() {
        return ultimoAbastecimento;
    }

    public void setUltimoAbastecimento(Date ultimoAbastecimento) {
        this.ultimoAbastecimento = ultimoAbastecimento;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public Collection<Encomenda> getEncomendaCollection() {
        return encomendaCollection;
    }

    public void setEncomendaCollection(Collection<Encomenda> encomendaCollection) {
        this.encomendaCollection = encomendaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " "+ descricao + " "+ referencia;
    }
    
    // escrever um metodo que substitua o toString
}
