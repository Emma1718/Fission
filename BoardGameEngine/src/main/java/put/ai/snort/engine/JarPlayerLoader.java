/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.engine;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import put.ai.snort.game.Player;

public class JarPlayerLoader {

    private JarPlayerLoader() {
    }

    private static String getPlayerClassName(URL url) throws MalformedURLException, IOException {
        URL u = new URL("jar", "", url + "!/");
        JarURLConnection uc = (JarURLConnection) u.openConnection();
        Attributes attr = uc.getMainAttributes();
        return attr != null ? attr.getValue(Attributes.Name.MAIN_CLASS) : null;
    }

    public static Class<? extends Player> load(String jarFile) {
        try {
            URL url = new File(jarFile).toURI().toURL();
            String playerClassName = getPlayerClassName(url);
            if (playerClassName == null) {
                throw new RuntimeException(String.format("Can not find class name for %s", jarFile));
            }
            System.err.printf("Found %s for %s\n", playerClassName, url);
            URLClassLoader loader = new URLClassLoader(new URL[]{url});
            if (loader == null) {
                throw new RuntimeException(String.format("%s is not proper jar", jarFile));
            }
            Class<?> playerClass = loader.loadClass(playerClassName);
            if (playerClass == null) {
                throw new RuntimeException(String.format("Can not find class for name %s in %s", playerClassName, jarFile));
            }
            if (!Player.class.isAssignableFrom(playerClass)) {
                throw new RuntimeException(String.format("%s is not propert Player", playerClassName));
            }
            return (Class<? extends Player>) playerClass;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(JarPlayerLoader.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
}
