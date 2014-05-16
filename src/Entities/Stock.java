package Entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * ***********************************************************************************************
 *
 * @author magang
 * create date : 20.07.2013
 * change date : 
 *
 * description: Stock Entity 
 *
 * flow:
 
 *
 * Change Log:
 * 28.07.2013 Refactoring and refining
 
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Stock.FIND_ALL, query = "select p from Stock p order by p.symbol"),
    @NamedQuery(name = Stock.FIND_BY_SYMBOL, query = "select p from Stock p where p.symbol=:sy")
})
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "Stock.findAll";
    public static final String FIND_BY_SYMBOL = "Stock.findBySymbol";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String symbol;
    private String name;
    
    @OneToMany(mappedBy = "stock", cascade= CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name="INTERVAL_TYPE_ID")
    private Map<String,Barsize> barsize = new HashMap();;

    public Stock()
    {
    }

    public Stock(String symbol, String name)
    {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
       
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Map<String, Barsize> getBarSize() {
        return barsize;
    }

    public Barsize getOneBarSize(String intervaltype){
        
        return this.barsize.get(intervaltype);
    }
    
    public void setBarSize(Map<String, Barsize> quotes) {
        this.barsize = quotes;
    }
    
    public void addIntervalType(String intervaltype){
        Barsize bz = new Barsize();
        bz.setStock(this);
        this.barsize.put(intervaltype, bz);
    }

    public List<Quote> getQuotes(String intervaltype)
    {
        return this.barsize.get(intervaltype).getQuotes();
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
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return " Symbol[ id=" + id + " ] "
                + " Symbol : " + this.symbol
                + " Name : " + this.name;
    }
    
    public String intervalToString(){
        
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Barsize> entry : barsize.entrySet()) {
            String intervaltype = entry.getKey();
            Barsize barsize = entry.getValue();
            sb.append(" IntervalType : ");
            sb.append(intervaltype);
            sb.append(barsize.quotesToString());
            sb.append("\n"); 
            sb.append("Last Quote"); 
            sb.append(barsize.getLastQuote().toString());
            sb.append("\n");
                                 
        } 
        
        
        return sb.toString();
    }

}
