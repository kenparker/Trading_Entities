package Service;

import Entities.Barsize;
import Entities.Quote;
import Entities.Stock;
import core.PriceBar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 * ***********************************************************************************************
 *
 * @author magang create date : 03.01.2013 change date : 11.03.2013
 *
 * description: The class manage a trading strategy for multiple symbols
 *
 * flow:
 * <p/>
 *
 * Change Log: 28.01.2013 OCAGroup added by Orders 31.07.2013 refactored 07.08.2013 hetQuotes added
 * <p/>
 */
public class StockService
{

    public List<PriceBar> getQuotes(EntityManager em, String symbol, String barsize)
    {

        Stock st = null;
        try {
            st = findbyName(em, symbol);
        } catch (Exception ex) {
            Logger.getLogger(StockService.class.getName()).log(Level.INFO,
                    "getQuotes: Stock {0} not found", symbol);
            return null;
        }

        //<editor-fold defaultstate="collapsed" desc="build return Value">
        Barsize oneBarSize = st.getOneBarSize(barsize);
        if (oneBarSize != null) {

            //<editor-fold defaultstate="collapsed" desc="transform Quote to PriceBar">
            List<Quote> quotes = oneBarSize.getQuotes();

            if (quotes != null) {
                List<PriceBar> lpb = new ArrayList();
                for (Quote quote : quotes) {
                    PriceBar pb = new PriceBar();
                    pb.setDate(quote.getDatequote());
                    pb.setClose(quote.getClosequote());
                    pb.setCount(quote.getCount());
                    pb.setHigh(quote.getHighquote());
                    pb.setLow(quote.getLowquote());
                    pb.setOpen(quote.getOpenquote());
                    pb.setValueext(quote.getValueext());
                    pb.setVolume(quote.getVolume());
                    pb.setWAP(quote.getWAP());
                    lpb.add(pb);
                }
                return lpb;

            } else {
                String outString = "getQuotes: Stock :" + symbol + " Barsize :" + barsize + " not quotes found";
                Logger.getLogger(StockService.class.getName()).log(Level.INFO, outString);
                return null;

            }
            //</editor-fold>

        } else {
            String outString = "getQuotes: Stock :" + symbol + " Barsize :" + barsize + " not found";
            Logger.getLogger(StockService.class.getName()).log(Level.INFO, outString);
            return null;
        }
        //</editor-fold>

    }

    public GregorianCalendar getLastDate(EntityManager em, String symbol, String intervaltype)
    {
        GregorianCalendar gregorianCalendar = null;
        Stock st = null;
        try {
            st = findbyName(em, symbol);

            Barsize oneBarSize = st.getOneBarSize(intervaltype);

            Quote lastQuote = oneBarSize.getLastQuote();
            if (oneBarSize != null && lastQuote != null) {

                String datequote = lastQuote.getDatequote();

                System.out.println("Symbol :" + symbol
                        + " Barsize :" + intervaltype
                        + " lastdate :" + Integer.valueOf(datequote.substring(0, 4))
                        + " " + Integer.valueOf(datequote.substring(4, 6))
                        + " " + Integer.valueOf(datequote.substring(6, 8)));

                gregorianCalendar = new GregorianCalendar(
                        Integer.valueOf(datequote.substring(0, 4)),
                        Integer.valueOf(datequote.substring(4, 6)) - 1,
                        Integer.valueOf(datequote.substring(6, 8)));


            }

        } catch (Exception ex) {
            Logger.getLogger(StockService.class
                    .getName()).log(Level.INFO,
                    "getLastDate: Stock {0} not found", symbol);
        }


        return gregorianCalendar;
    }

    public Stock createStock(EntityManager em, String symbol, String name)
    {
        em.getTransaction().begin();

        Stock st = null;
        try {
            st = findbyName(em, symbol);
            // Stock found do not add
            Logger
                    .getLogger(StockService.class
                    .getName()).log(Level.INFO,
                    "Stock {0} cannot be added, it already exist, ", symbol);
        } catch (Exception ex) {
            // Stock not found, then add
            st = new Stock(symbol, name);
            em.persist(st);
        }

        em.getTransaction().commit();

        return st;

    }

    public void addIntervalType(EntityManager em, Stock stock, String intervaltype)
    {
        em.getTransaction().begin();

        stock = em.merge(stock);
        if (stock.getOneBarSize(intervaltype) == null) {
            stock.addIntervalType(intervaltype);
        }

        // em.flush();
        em.getTransaction().commit();


    }

    public void addQuote(EntityManager em, Stock stock, String intervaltype, String datequote,
            double openquote,
            double highquote,
            double lowquote,
            double closequote,
            double volume,
            int count,
            double WAP,
            double valueext)
    {
        em.getTransaction().begin();


        stock = em.merge(stock);
        Barsize oneBarSize = stock.getOneBarSize(intervaltype);
        if (oneBarSize == null) {
            System.out.println(" BarSize for " + intervaltype + " not found");
        } else {
            //oneBarSize = em.merge(oneBarSize);

            if ((oneBarSize.getLastQuote() != null && oneBarSize.getLastQuote().getDatequote().compareTo(
                    datequote) < 0)
                    || oneBarSize.getLastQuote() == null) {
                oneBarSize.addQuote(datequote,
                        openquote,
                        highquote,
                        lowquote,
                        closequote,
                        volume,
                        count,
                        WAP,
                        valueext);

            }
        }


        em.getTransaction().commit();

    }

    public Stock findbyName(EntityManager em, String symbol) throws Exception
    {

        List<Stock> rl = em.createNamedQuery("Stock.findBySymbol", Stock.class)
                .setParameter("sy", symbol)
                .getResultList();
        if (rl
                != null && rl.size()
                > 1) {
            throw new Exception(
                    "findbySymbol: Symbol named " + symbol + " was found more than once.");
        }
        if (rl
                != null && rl.size()
                == 1) {
            //System.out.println("findbySymbol: Symbol found");
            return rl.get(0);
        }
        //System.out.println("findbySymbol: Symbol not found");

        throw new Exception(
                "findbySymbol: Symbol " + symbol + " doesn't exist.");
    }

    /*
     public void getBarSize(EntityManager em, Stock st) {


     st = em.merge(st);

     System.out.println(" Stock :" + st.toString());
     System.out.println(" BarSize :" + st.intervalToString());

     }

     * */
    public int getQuotesSize(Stock stock, String barsize)
    {

        return stock.getOneBarSize(barsize).getQuotes().size();
    }

    public void addQuotesList(EntityManager em, Stock stock, String intervaltype, List<PriceBar> pb)
    {
        em.getTransaction().begin();

        stock = em.merge(stock);

        Barsize oneBarSize = stock.getOneBarSize(intervaltype);
        if (oneBarSize == null) {
            System.out.println(" BarSize for " + intervaltype + " not found");
        } else {

            String datequote = "";
            Quote lastQuote = null;

            final int startsize = oneBarSize.getQuotes().size() - 1;
            if (startsize > 0) {
                // Remove Lastquote
                oneBarSize.removeQuote(startsize);
                //
                lastQuote = oneBarSize.getLastQuote();
                datequote = lastQuote.getDatequote();
            }

            for (PriceBar priceBar : pb) {

                if ((lastQuote != null && datequote.compareTo(priceBar.getDate()) < 0)
                        || lastQuote == null) {
                    oneBarSize.addQuote(priceBar.getDate(),
                            priceBar.getOpen(),
                            priceBar.getHigh(),
                            priceBar.getLow(),
                            priceBar.getClose(),
                            priceBar.getVolume(),
                            priceBar.getCount(),
                            priceBar.getWAP(),
                            priceBar.getValueext());

                }

            }

            final int endsize = oneBarSize.getQuotes().size() - 1;

            Logger
                    .getLogger(StockService.class
                    .getName()).log(Level.INFO,
                    "Added " + (endsize - startsize) + " quotes for Symbol" + stock.getSymbol());

        }

        em.getTransaction().commit();
    }

    public void clearQuotes(EntityManager em, Stock stock, String intervaltype)
    {
        em.getTransaction().begin();

        stock = em.merge(stock);

        Barsize oneBarSize = stock.getOneBarSize(intervaltype);
        if (oneBarSize == null) {
            System.out.println(" BarSize for " + intervaltype + " not found");
        } else {


            oneBarSize.getQuotes().clear();
            Logger
                    .getLogger(StockService.class
                    .getName()).log(Level.INFO,
                    "Cleared quotes for Symbol" + stock.getSymbol() + " barsize :" + intervaltype);



        }

        em.getTransaction().commit();
    }
}
