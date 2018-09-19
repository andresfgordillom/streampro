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
@Table(name = "deezifyuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deezifyuser.findAll", query = "SELECT d FROM Deezifyuser d")
    , @NamedQuery(name = "Deezifyuser.findByIduser", query = "SELECT d FROM Deezifyuser d WHERE d.iduser = :iduser")
    , @NamedQuery(name = "Deezifyuser.findByEsCliente", query = "SELECT d FROM Deezifyuser d WHERE d.esCliente = :esCliente")
    , @NamedQuery(name = "Deezifyuser.findByNickname", query = "SELECT d FROM Deezifyuser d WHERE d.nickname = :nickname")
    , @NamedQuery(name = "Deezifyuser.findByPassword", query = "SELECT d FROM Deezifyuser d WHERE d.password = :password")
    , @NamedQuery(name = "Deezifyuser.findByNames", query = "SELECT d FROM Deezifyuser d WHERE d.names = :names")
    , @NamedQuery(name = "Deezifyuser.findByLastnames", query = "SELECT d FROM Deezifyuser d WHERE d.lastnames = :lastnames")})
public class Deezifyuser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iduser")
    private Integer iduser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "es_cliente")
    private boolean esCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nickname")
    private String nickname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "names")
    private String names;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "lastnames")
    private String lastnames;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iduser")
    private Collection<Playlist> playlistCollection;
    @JoinColumn(name = "idcompany", referencedColumnName = "idcompany")
    @ManyToOne
    private Company idcompany;

    public Deezifyuser() {
    }

    public Deezifyuser(Integer iduser) {
        this.iduser = iduser;
    }

    public Deezifyuser(Integer iduser, boolean esCliente, String nickname, String password, String names, String lastnames) {
        this.iduser = iduser;
        this.esCliente = esCliente;
        this.nickname = nickname;
        this.password = password;
        this.names = names;
        this.lastnames = lastnames;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public boolean getEsCliente() {
        return esCliente;
    }

    public void setEsCliente(boolean esCliente) {
        this.esCliente = esCliente;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    @XmlTransient
    public Collection<Playlist> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(Collection<Playlist> playlistCollection) {
        this.playlistCollection = playlistCollection;
    }

    public Company getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(Company idcompany) {
        this.idcompany = idcompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduser != null ? iduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deezifyuser)) {
            return false;
        }
        Deezifyuser other = (Deezifyuser) object;
        if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Deezifyuser[ iduser=" + iduser + " ]";
    }
    
}
