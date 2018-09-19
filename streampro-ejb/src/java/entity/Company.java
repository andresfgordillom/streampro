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
@Table(name = "company")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")
    , @NamedQuery(name = "Company.findByIdcompany", query = "SELECT c FROM Company c WHERE c.idcompany = :idcompany")
    , @NamedQuery(name = "Company.findByCompName", query = "SELECT c FROM Company c WHERE c.compName = :compName")
    , @NamedQuery(name = "Company.findByIdentificator", query = "SELECT c FROM Company c WHERE c.identificator = :identificator")})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompany")
    private Integer idcompany;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "comp_name")
    private String compName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "identificator")
    private String identificator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcompany")
    private Collection<Album> albumCollection;
    @OneToMany(mappedBy = "idcompany")
    private Collection<Deezifyuser> deezifyuserCollection;

    public Company() {
    }

    public Company(Integer idcompany) {
        this.idcompany = idcompany;
    }

    public Company(Integer idcompany, String compName, String identificator) {
        this.idcompany = idcompany;
        this.compName = compName;
        this.identificator = identificator;
    }

    public Integer getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(Integer idcompany) {
        this.idcompany = idcompany;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getIdentificator() {
        return identificator;
    }

    public void setIdentificator(String identificator) {
        this.identificator = identificator;
    }

    @XmlTransient
    public Collection<Album> getAlbumCollection() {
        return albumCollection;
    }

    public void setAlbumCollection(Collection<Album> albumCollection) {
        this.albumCollection = albumCollection;
    }

    @XmlTransient
    public Collection<Deezifyuser> getDeezifyuserCollection() {
        return deezifyuserCollection;
    }

    public void setDeezifyuserCollection(Collection<Deezifyuser> deezifyuserCollection) {
        this.deezifyuserCollection = deezifyuserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcompany != null ? idcompany.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.idcompany == null && other.idcompany != null) || (this.idcompany != null && !this.idcompany.equals(other.idcompany))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Company[ idcompany=" + idcompany + " ]";
    }
    
}
