import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * A utility class that provides methods to read and write files.
 */
public class IOUtils {
    /***
     * Read a properties file and return a Properties object
     * @param configFile: the path to the properties file
     * @return: Properties object
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }

}
