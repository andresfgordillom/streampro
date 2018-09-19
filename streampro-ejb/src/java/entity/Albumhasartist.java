/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "albumhasartist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Albumhasartist.findAll", query = "SELECT a FROM Albumhasartist a")
    , @NamedQuery(name = "Albumhasartist.findByIdalbumartist", query = "SELECT a FROM Albumhasartist a WHERE a.idalbumartist = :idalbumartist")})
public class Albumhasartist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "idalbumartist")
    private String idalbumartist;
    @JoinColumn(name = "idalbum", referencedColumnName = "idalbum")
    @ManyToOne(optional = false)
    private Album idalbum;
    @JoinColumn(name = "idartist", referencedColumnName = "idartist")
    @ManyToOne(optional = false)
    private Artist idartist;

    public Albumhasartist() {
    }

    public Albumhasartist(String idalbumartist) {
        this.idalbumartist = idalbumartist;
    }

    public String getIdalbumartist() {
        return idalbumartist;
    }

    public void setIdalbumartist(String idalbumartist) {
        this.idalbumartist = idalbumartist;
    }

    public Album getIdalbum() {
        return idalbum;
    }

    public void setIdalbum(Album idalbum) {
        this.idalbum = idalbum;
    }

    public Artist getIdartist() {
        return idartist;
    }

    public void setIdartist(Artist idartist) {
        this.idartist = idartist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalbumartist != null ? idalbumartist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Albumhasartist)) {
            return false;
        }
        Albumhasartist other = (Albumhasartist) object;
        if ((this.idalbumartist == null && other.idalbumartist != null) || (this.idalbumartist != null && !this.idalbumartist.equals(other.idalbumartist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Albumhasartist[ idalbumartist=" + idalbumartist + " ]";
    }
    
}
