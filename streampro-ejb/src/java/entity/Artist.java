/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "artist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a")
    , @NamedQuery(name = "Artist.findByIdartist", query = "SELECT a FROM Artist a WHERE a.idartist = :idartist")
    , @NamedQuery(name = "Artist.findByStageName", query = "SELECT a FROM Artist a WHERE a.stageName = :stageName")})
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idartist")
    private Integer idartist;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "stage_name")
    private String stageName;
    @JoinColumn(name = "idcountry", referencedColumnName = "idcountry")
    @ManyToOne(optional = false)
    private Country idcountry;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idartist")
    private Collection<Albumhasartist> albumhasartistCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idartist")
    private Collection<Songhasartist> songhasartistCollection;

    public Artist() {
    }

    public Artist(Integer idartist) {
        this.idartist = idartist;
    }

    public Artist(Integer idartist, String stageName) {
        this.idartist = idartist;
        this.stageName = stageName;
    }

    public Integer getIdartist() {
        return idartist;
    }

    public void setIdartist(Integer idartist) {
        this.idartist = idartist;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Country getIdcountry() {
        return idcountry;
    }

    public void setIdcountry(Country idcountry) {
        this.idcountry = idcountry;
    }

    @XmlTransient
    public Collection<Albumhasartist> getAlbumhasartistCollection() {
        return albumhasartistCollection;
    }

    public void setAlbumhasartistCollection(Collection<Albumhasartist> albumhasartistCollection) {
        this.albumhasartistCollection = albumhasartistCollection;
    }

    @XmlTransient
    public Collection<Songhasartist> getSonghasartistCollection() {
        return songhasartistCollection;
    }

    public void setSonghasartistCollection(Collection<Songhasartist> songhasartistCollection) {
        this.songhasartistCollection = songhasartistCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idartist != null ? idartist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.idartist == null && other.idartist != null) || (this.idartist != null && !this.idartist.equals(other.idartist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Artist[ idartist=" + idartist + " ]";
    }
    
}
