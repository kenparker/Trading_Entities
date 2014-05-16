

package Entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * ***********************************************************************************************
 *
 * @author magang
 * create date : 20.07.2013
 * change date : 
 *
 * description: Quote Entity 
 *
 * 
 
 *
 * Change Log:
 * 28.07.2013 Refactoring and refining
 
 */
@Entity
public class Quote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column( name = "QUOTE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Barsize barsizeowner;
    
    //@Column( name = "DATEQUOTE")
    private String datequote;
    private double openquote;
    private double highquote;
    private double lowquote;
    private double closequote;
    private double volume;
    private int count;
    private double WAP;
    private double valueext;
    
    
    public Quote() {
        
    }
    public Quote(String datequote, double openquote, double highquote, double lowquote,
            double closequote, double volume, int count, double WAP, double valueext)
    {
        this.datequote = datequote;
        this.openquote = openquote;
        this.highquote = highquote;
        this.lowquote = lowquote;
        this.closequote = closequote;
        this.volume = volume;
        this.count = count;
        this.WAP = WAP;
        this.valueext = valueext;
    }

    public Barsize getBarsizeowner()
    {
        return barsizeowner;
    }

    public void setBarsizeowner(Barsize barsizeowner)
    {
        this.barsizeowner = barsizeowner;
    }

    
    public String getDatequote()
    {
        return datequote;
    }

    public void setDatequote(String datequote)
    {
        this.datequote = datequote;
    }

    public double getOpenquote()
    {
        return openquote;
    }

    public void setOpenquote(double openquote)
    {
        this.openquote = openquote;
    }

    public double getHighquote()
    {
        return highquote;
    }

    public void setHighquote(double highquote)
    {
        this.highquote = highquote;
    }

    public double getLowquote()
    {
        return lowquote;
    }

    public void setLowquote(double lowquote)
    {
        this.lowquote = lowquote;
    }

    public double getClosequote()
    {
        return closequote;
    }

    public void setClosequote(double closequote)
    {
        this.closequote = closequote;
    }

    public double getVolume()
    {
        return volume;
    }

    public void setVolume(double volume)
    {
        this.volume = volume;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public double getWAP()
    {
        return WAP;
    }

    public void setWAP(double WAP)
    {
        this.WAP = WAP;
    }

    public double getValueext()
    {
        return valueext;
    }

    public void setValueext(double valueext)
    {
        this.valueext = valueext;
    }
    

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quote)) {
            return false;
        }
        Quote other = (Quote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return " Quotes[ id=" + id + " ]" 
                + " Date :" + this.getDatequote()
                + " Close:" + this.getClosequote()
                + " Low  :" + this.getLowquote()
                + " High :" + this.getHighquote()
                + " Volume :" + this.getVolume();
    }

}
