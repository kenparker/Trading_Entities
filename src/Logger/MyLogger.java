package Logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

    //private static final String FILE_PATH = "C:/Users/magang/Documents/Logs/";
    private static final String FILE_PATH = System.getProperty("user.dir") + "\\";
    private static FileHandler fileHdlr;
    private static MyFormatter formatterTxt;
    private static Handler conHdlr;
    private static boolean firsttime = true;

    private static final Logger logger = Logger.getLogger("StockServiceLogger");

    static public void setup()  {
        if (firsttime) {
            try {
                System.out.println("Log File :"+FILE_PATH + "StockServiceLogging.txt");
                logger.setUseParentHandlers(false);
                fileHdlr = new FileHandler(FILE_PATH + "StockServiceLogging.txt", true);
                conHdlr = new ConsoleHandler();
                formatterTxt = new MyFormatter();
                fileHdlr.setFormatter(formatterTxt);
                fileHdlr.setLevel(Level.ALL);
                conHdlr.setFormatter(formatterTxt);
                conHdlr.setLevel(Level.ALL);
                logger.addHandler(fileHdlr);
                logger.addHandler(conHdlr);
                logger.setLevel(Level.INFO);
                
                firsttime = false;
                
                logger.finer("Logger setup done");
            } catch (IOException ex) {
                Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            logger.finer("Logger is already setup");
        }
    }

    static public void closeHandler() {
        fileHdlr.close();
    }

    public static Logger getLogger() {
        return logger;
    }

    
}
