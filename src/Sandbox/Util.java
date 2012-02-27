/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sandbox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author loris
 */
public class Util {
    public Util() {
    }

    /**
     * Utility function to retrieve a property from a .cfg file
     * @param fileName the configuration file 
     * @param propertyName the name of the property
     * @return the value of the property
     */
    public String getProperty(String fileName, String propertyName) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith(propertyName)) {
                int equalsIndex = line.indexOf("=");
                String retValue = line.substring(equalsIndex + 1);
                while (retValue.startsWith(" ")) { // get rid of trailing spaces
                    retValue = retValue.substring(1);
                }
                scanner.close();
                return retValue;
            }
        }
        return null;
    }
}
