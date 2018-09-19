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
@Table(name = "songhasartist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Songhasartist.findAll", query = "SELECT s FROM Songhasartist s")
    , @NamedQuery(name = "Songhasartist.findByIdsongartist", query = "SELECT s FROM Songhasartist s WHERE s.idsongartist = :idsongartist")})
public class Songhasartist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "idsongartist")
    private String idsongartist;
    @JoinColumn(name = "idartist", referencedColumnName = "idartist")
    @ManyToOne(optional = false)
    private Artist idartist;
    @JoinColumn(name = "idsong", referencedColumnName = "idsong")
    @ManyToOne(optional = false)
    private Song idsong;

    public Songhasartist() {
    }

    public Songhasartist(String idsongartist) {
        this.idsongartist = idsongartist;
    }

    public String getIdsongartist() {
        return idsongartist;
    }

    public void setIdsongartist(String idsongartist) {
        this.idsongartist = idsongartist;
    }

    public Artist getIdartist() {
        return idartist;
    }

    public void setIdartist(Artist idartist) {
        this.idartist = idartist;
    }

    public Song getIdsong() {
        return idsong;
    }

    public void setIdsong(Song idsong) {
        this.idsong = idsong;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsongartist != null ? idsongartist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Songhasartist)) {
            return false;
        }
        Songhasartist other = (Songhasartist) object;
        if ((this.idsongartist == null && other.idsongartist != null) || (this.idsongartist != null && !this.idsongartist.equals(other.idsongartist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Songhasartist[ idsongartist=" + idsongartist + " ]";
    }
    
}
