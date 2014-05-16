/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa_angelo_5;

import Service.StockService;
import Entities.Stock;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author maggioni
 */
public class JPA_Angelo_5 {

    private static final String PERSISTENCE_UNIT_NAME = "JPA_Angelo_5PU";
    private static EntityManagerFactory factory;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        /*
        create(em, "XLK"); */
        //new JPA_Angelo_5().suche(em,"XXX");
        
        /* new JPA_Angelo_5().addIntervalType(em,"XLK","1STD");
        new JPA_Angelo_5().addIntervalType(em,"XLK","2STD");
        new JPA_Angelo_5().addQuote(em,"XLK","1STD","20130729 01",1.11);
        new JPA_Angelo_5().addQuote(em,"XLK","1STD","20130729 02",2.11);
        new JPA_Angelo_5().addQuote(em,"XLK","1STD","20130729 03",10.11);
        new JPA_Angelo_5().addQuote(em,"XLK","1STD","20130729 04",100.11);
        new JPA_Angelo_5().addQuote(em,"XLK","2STD","20130729 01",222.11);
        */
        //new JPA_Angelo_5().addQuote(em,"XLK","2STD","20130729 03",444.11);
      
        //new JPA_Angelo_5().printBarSize(em,"XLP");
        
        new JPA_Angelo_5().getLastDate(em, "IWM", "1 hour");
        
        em.close();


    }

    private static void create(EntityManager em, String symbol)
    {
        new StockService().createStock(em, symbol, "TkkEchs");
    }

    private Stock suche(EntityManager em, String symbol)
    {
        try {
          Stock st = new StockService().findbyName(em, symbol);
          System.out.println("Stock found");
          return st;
        } catch (Exception ex) {
            Logger.getLogger(JPA_Angelo_5.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private void addIntervalType(EntityManager em, String symbol, String barsize){
        
        
        Stock st;
        st = suche(em,symbol);
        new StockService().addIntervalType(em, st, barsize);
        
        
    }
    
    private void printBarSize(EntityManager em, String symbol){
        
        Stock st;
        st = suche(em,symbol);
       // new StockService().getBarSize(em, st);
        
    }

    private void addQuote(EntityManager em, String symbol, String barsize, String date, double close)
    {
        Stock st;
        st = suche(em,symbol);
        new StockService().addQuote(em, st, barsize, date, 
                close,
                close,
                close,
                close,
                1000,
                1,
                1,
                1);
    }
    
    private void getLastDate(EntityManager em, String symbol, String barsize){
        
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        GregorianCalendar lastDate = new StockService().getLastDate(em, symbol, barsize);
        System.out.println(" Symbol: "+ symbol + " barsize : "+ barsize + " lastdate :" + df.format(lastDate.getTime()));
    }
}
