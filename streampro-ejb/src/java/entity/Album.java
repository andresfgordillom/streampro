/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "album")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
    , @NamedQuery(name = "Album.findByIdalbum", query = "SELECT a FROM Album a WHERE a.idalbum = :idalbum")
    , @NamedQuery(name = "Album.findByTitle", query = "SELECT a FROM Album a WHERE a.title = :title")
    , @NamedQuery(name = "Album.findByDate", query = "SELECT a FROM Album a WHERE a.date = :date")
    , @NamedQuery(name = "Album.findByCoverpath", query = "SELECT a FROM Album a WHERE a.coverpath = :coverpath")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalbum")
    private Integer idalbum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "coverpath")
    private String coverpath;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idalbum")
    private Collection<Song> songCollection;
    @JoinColumn(name = "idcompany", referencedColumnName = "idcompany")
    @ManyToOne(optional = false)
    private Company idcompany;
    @JoinColumn(name = "idgenre", referencedColumnName = "idgenre")
    @ManyToOne(optional = false)
    private Genre idgenre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idalbum")
    private Collection<Albumhasartist> albumhasartistCollection;

    public Album() {
    }

    public Album(Integer idalbum) {
        this.idalbum = idalbum;
    }

    public Album(Integer idalbum, String title, Date date, String coverpath) {
        this.idalbum = idalbum;
        this.title = title;
        this.date = date;
        this.coverpath = coverpath;
    }

    public Integer getIdalbum() {
        return idalbum;
    }

    public void setIdalbum(Integer idalbum) {
        this.idalbum = idalbum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCoverpath() {
        return coverpath;
    }

    public void setCoverpath(String coverpath) {
        this.coverpath = coverpath;
    }

    @XmlTransient
    public Collection<Song> getSongCollection() {
        return songCollection;
    }

    public void setSongCollection(Collection<Song> songCollection) {
        this.songCollection = songCollection;
    }

    public Company getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(Company idcompany) {
        this.idcompany = idcompany;
    }

    public Genre getIdgenre() {
        return idgenre;
    }

    public void setIdgenre(Genre idgenre) {
        this.idgenre = idgenre;
    }

    @XmlTransient
    public Collection<Albumhasartist> getAlbumhasartistCollection() {
        return albumhasartistCollection;
    }

    public void setAlbumhasartistCollection(Collection<Albumhasartist> albumhasartistCollection) {
        this.albumhasartistCollection = albumhasartistCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalbum != null ? idalbum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.idalbum == null && other.idalbum != null) || (this.idalbum != null && !this.idalbum.equals(other.idalbum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Album[ idalbum=" + idalbum + " ]";
    }
    
}
