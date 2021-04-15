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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "encomenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encomenda.findAll", query = "SELECT e FROM Encomenda e")
    , @NamedQuery(name = "Encomenda.findByEntregaDia", query = "SELECT e FROM Encomenda e WHERE e.entregaDia = :entregaDia")
    , @NamedQuery(name = "Encomenda.findByDiaMarcacao", query = "SELECT e FROM Encomenda e WHERE e.diaMarcacao = :diaMarcacao")
    , @NamedQuery(name = "Encomenda.findByValorEntrada", query = "SELECT e FROM Encomenda e WHERE e.valorEntrada = :valorEntrada")
    , @NamedQuery(name = "Encomenda.findByTotalPorPagar", query = "SELECT e FROM Encomenda e WHERE e.totalPorPagar = :totalPorPagar")
    , @NamedQuery(name = "Encomenda.findByHoraEntrega", query = "SELECT e FROM Encomenda e WHERE e.horaEntrega = :horaEntrega")
    , @NamedQuery(name = "Encomenda.findByEstadoEntrega", query = "SELECT e FROM Encomenda e WHERE e.estadoEntrega = :estadoEntrega")
    , @NamedQuery(name = "Encomenda.findById", query = "SELECT e FROM Encomenda e WHERE e.id = :id")
    , @NamedQuery(name = "Encomenda.findByObservacao", query = "SELECT e FROM Encomenda e WHERE e.observacao = :observacao")})
public class Encomenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "entrega_dia")
    @Temporal(TemporalType.DATE)
    private Date entregaDia;
    @Column(name = "dia_marcacao")
    @Temporal(TemporalType.DATE)
    private Date diaMarcacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_entrada")
    private Double valorEntrada;
    @Column(name = "total_por_pagar")
    private Double totalPorPagar;
    @Column(name = "horaEntrega")
    @Temporal(TemporalType.TIME)
    private Date horaEntrega;
    @Column(name = "estadoEntrega")
    private String estadoEntrega;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "observacao")
    private String observacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encomendaId")
    private Collection<Actividade> actividadeCollection;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente clienteId;
    @JoinColumn(name = "Produto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Produto produtoid;

    public Encomenda() {
    }

    public Encomenda(Integer id) {
        this.id = id;
    }

    public Date getEntregaDia() {
        return entregaDia;
    }

    public void setEntregaDia(Date entregaDia) {
        this.entregaDia = entregaDia;
    }

    public Date getDiaMarcacao() {
        return diaMarcacao;
    }

    public void setDiaMarcacao(Date diaMarcacao) {
        this.diaMarcacao = diaMarcacao;
    }

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Double getTotalPorPagar() {
        return totalPorPagar;
    }

    public void setTotalPorPagar(Double totalPorPagar) {
        this.totalPorPagar = totalPorPagar;
    }

    public Date getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(Date horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @XmlTransient
    public Collection<Actividade> getActividadeCollection() {
        return actividadeCollection;
    }

    public void setActividadeCollection(Collection<Actividade> actividadeCollection) {
        this.actividadeCollection = actividadeCollection;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public Produto getProdutoid() {
        return produtoid;
    }

    public void setProdutoid(Produto produtoid) {
        this.produtoid = produtoid;
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
        if (!(object instanceof Encomenda)) {
            return false;
        }
        Encomenda other = (Encomenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Encomenda[ id=" + id + " ]";
    }
    
}
