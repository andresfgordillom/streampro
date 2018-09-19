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
@Table(name = "playlisthassong")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlisthassong.findAll", query = "SELECT p FROM Playlisthassong p")
    , @NamedQuery(name = "Playlisthassong.findByIdlistsong", query = "SELECT p FROM Playlisthassong p WHERE p.idlistsong = :idlistsong")})
public class Playlisthassong implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "idlistsong")
    private String idlistsong;
    @JoinColumn(name = "idplaylist", referencedColumnName = "idplaylist")
    @ManyToOne(optional = false)
    private Playlist idplaylist;
    @JoinColumn(name = "idsong", referencedColumnName = "idsong")
    @ManyToOne(optional = false)
    private Song idsong;

    public Playlisthassong() {
    }

    public Playlisthassong(String idlistsong) {
        this.idlistsong = idlistsong;
    }

    public String getIdlistsong() {
        return idlistsong;
    }

    public void setIdlistsong(String idlistsong) {
        this.idlistsong = idlistsong;
    }

    public Playlist getIdplaylist() {
        return idplaylist;
    }

    public void setIdplaylist(Playlist idplaylist) {
        this.idplaylist = idplaylist;
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
        hash += (idlistsong != null ? idlistsong.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlisthassong)) {
            return false;
        }
        Playlisthassong other = (Playlisthassong) object;
        if ((this.idlistsong == null && other.idlistsong != null) || (this.idlistsong != null && !this.idlistsong.equals(other.idlistsong))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Playlisthassong[ idlistsong=" + idlistsong + " ]";
    }
    
}
