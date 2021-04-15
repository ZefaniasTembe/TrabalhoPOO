/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tembe
 */
@Entity
@Table(name = "actividade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividade.findAll", query = "SELECT a FROM Actividade a")
    , @NamedQuery(name = "Actividade.findById", query = "SELECT a FROM Actividade a WHERE a.id = :id")
    , @NamedQuery(name = "Actividade.findByDia", query = "SELECT a FROM Actividade a WHERE a.dia = :dia")})
public class Actividade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dia;
    @JoinColumn(name = "encomenda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Encomenda encomendaId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Actividade() {
    }

    public Actividade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Encomenda getEncomendaId() {
        return encomendaId;
    }

    public void setEncomendaId(Encomenda encomendaId) {
        this.encomendaId = encomendaId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
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
        if (!(object instanceof Actividade)) {
            return false;
        }
        Actividade other = (Actividade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Actividade[ id=" + id + " ]";
    }
    
}
