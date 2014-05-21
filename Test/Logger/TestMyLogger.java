/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maggioni
 */
public class TestMyLogger {

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
