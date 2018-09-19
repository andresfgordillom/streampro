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
@Table(name = "song")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Song.findAll", query = "SELECT s FROM Song s")
    , @NamedQuery(name = "Song.findByIdsong", query = "SELECT s FROM Song s WHERE s.idsong = :idsong")
    , @NamedQuery(name = "Song.findByTitle", query = "SELECT s FROM Song s WHERE s.title = :title")
    , @NamedQuery(name = "Song.findByLength", query = "SELECT s FROM Song s WHERE s.length = :length")
    , @NamedQuery(name = "Song.findBySongUrl", query = "SELECT s FROM Song s WHERE s.songUrl = :songUrl")
    , @NamedQuery(name = "Song.findByDiscnumber", query = "SELECT s FROM Song s WHERE s.discnumber = :discnumber")
    , @NamedQuery(name = "Song.findBySongnumber", query = "SELECT s FROM Song s WHERE s.songnumber = :songnumber")})
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsong")
    private Integer idsong;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "length")
    @Temporal(TemporalType.TIME)
    private Date length;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "song_url")
    private String songUrl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "discnumber")
    private int discnumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "songnumber")
    private String songnumber;
    @JoinColumn(name = "idalbum", referencedColumnName = "idalbum")
    @ManyToOne(optional = false)
    private Album idalbum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idsong")
    private Collection<Playlisthassong> playlisthassongCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idsong")
    private Collection<Songhasartist> songhasartistCollection;

    public Song() {
    }

    public Song(Integer idsong) {
        this.idsong = idsong;
    }

    public Song(Integer idsong, String title, Date length, String songUrl, int discnumber, String songnumber) {
        this.idsong = idsong;
        this.title = title;
        this.length = length;
        this.songUrl = songUrl;
        this.discnumber = discnumber;
        this.songnumber = songnumber;
    }

    public Integer getIdsong() {
        return idsong;
    }

    public void setIdsong(Integer idsong) {
        this.idsong = idsong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLength() {
        return length;
    }

    public void setLength(Date length) {
        this.length = length;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public int getDiscnumber() {
        return discnumber;
    }

    public void setDiscnumber(int discnumber) {
        this.discnumber = discnumber;
    }

    public String getSongnumber() {
        return songnumber;
    }

    public void setSongnumber(String songnumber) {
        this.songnumber = songnumber;
    }

    public Album getIdalbum() {
        return idalbum;
    }

    public void setIdalbum(Album idalbum) {
        this.idalbum = idalbum;
    }

    @XmlTransient
    public Collection<Playlisthassong> getPlaylisthassongCollection() {
        return playlisthassongCollection;
    }

    public void setPlaylisthassongCollection(Collection<Playlisthassong> playlisthassongCollection) {
        this.playlisthassongCollection = playlisthassongCollection;
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
        hash += (idsong != null ? idsong.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Song)) {
            return false;
        }
        Song other = (Song) object;
        if ((this.idsong == null && other.idsong != null) || (this.idsong != null && !this.idsong.equals(other.idsong))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Song[ idsong=" + idsong + " ]";
    }
    
}
