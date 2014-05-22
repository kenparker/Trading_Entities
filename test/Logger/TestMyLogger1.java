package Logger;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TestMyLogger1 {
public static void main(String[] args) {

        testLogger();

    }

    public static void testLogger() {
        MyLogger.setup();
        MyLogger.setup();
        Logger logger = MyLogger.getLogger();
        logger.setLevel(Level.INFO);
        logger.fine("Message Fine");
        logger.info("Message Info");
        MyLogger.closeHandler();
    }
    
    public static void testPath() {
        /*
         * Get java.class.path system property using
         * public static String getProperty(String name) method of
         * System class.
         */
        String strClassPath = System.getProperty("java.class.path");
        String workingdirectory = System.getProperty("user.dir");
        System.out.println("Classpath is " + strClassPath);
        System.out.println("Working Directory is " + workingdirectory);
    }
}
