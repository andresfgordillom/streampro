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
@Table(name = "playlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p")
    , @NamedQuery(name = "Playlist.findByIdplaylist", query = "SELECT p FROM Playlist p WHERE p.idplaylist = :idplaylist")
    , @NamedQuery(name = "Playlist.findByIsPublic", query = "SELECT p FROM Playlist p WHERE p.isPublic = :isPublic")
    , @NamedQuery(name = "Playlist.findByListTitle", query = "SELECT p FROM Playlist p WHERE p.listTitle = :listTitle")
    , @NamedQuery(name = "Playlist.findByCoverUrl", query = "SELECT p FROM Playlist p WHERE p.coverUrl = :coverUrl")})
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplaylist")
    private Integer idplaylist;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_public")
    private boolean isPublic;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "list_title")
    private String listTitle;
    @Size(max = 2147483647)
    @Column(name = "cover_url")
    private String coverUrl;
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private Deezifyuser iduser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idplaylist")
    private Collection<Playlisthassong> playlisthassongCollection;

    public Playlist() {
    }

    public Playlist(Integer idplaylist) {
        this.idplaylist = idplaylist;
    }

    public Playlist(Integer idplaylist, boolean isPublic, String listTitle) {
        this.idplaylist = idplaylist;
        this.isPublic = isPublic;
        this.listTitle = listTitle;
    }

    public Integer getIdplaylist() {
        return idplaylist;
    }

    public void setIdplaylist(Integer idplaylist) {
        this.idplaylist = idplaylist;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Deezifyuser getIduser() {
        return iduser;
    }

    public void setIduser(Deezifyuser iduser) {
        this.iduser = iduser;
    }

    @XmlTransient
    public Collection<Playlisthassong> getPlaylisthassongCollection() {
        return playlisthassongCollection;
    }

    public void setPlaylisthassongCollection(Collection<Playlisthassong> playlisthassongCollection) {
        this.playlisthassongCollection = playlisthassongCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplaylist != null ? idplaylist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) object;
        if ((this.idplaylist == null && other.idplaylist != null) || (this.idplaylist != null && !this.idplaylist.equals(other.idplaylist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Playlist[ idplaylist=" + idplaylist + " ]";
    }
    
}
