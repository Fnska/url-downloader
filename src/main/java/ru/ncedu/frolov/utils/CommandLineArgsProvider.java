package ru.ncedu.frolov.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The class represents state of passed arguments
 */
public class CommandLineArgsProvider {
    private URL url;
    private String path = System.getProperty("user.dir");
    private boolean isOpenAfter = false;

    /**
     * Command line arguments like: URL [path\to\file -o]<br>
     * Example: http://www.example.org/ [C:\\path\\to\\folder] [-o] <br>
     * where default path is a current directory <br>
     * where "-o" is flag to open file after downloading
     */
    private String[] args;

    public CommandLineArgsProvider() {
    }

    public CommandLineArgsProvider(String[] args) throws MalformedURLException {
        setArgs(args);
    }

    /**
     * Checks for validity of passed arguments
     *
     * @throws MalformedURLException 1st argument is not correct URL
     */
    private void checkArgs() throws MalformedURLException {
        if (args.length < 1) {
            throw new RuntimeException("Not any URL provided");
        }
        url = new URL(args[0]);
        if (args.length == 2) {
            if ("-o".equals(args[1])) {
                isOpenAfter = true;
            } else {
                path = args[1];
            }
        }
        if (args.length == 3) {
            path = args[1];
            if ("-o".equals(args[2])) {
                isOpenAfter = true;
            }
        }
    }

    public URL getUrl() {
        return url;
    }

    public void setArgs(String[] args) throws MalformedURLException {
        this.args = args;
        checkArgs();
    }

    public String getPath() {
        return path;
    }

    public boolean isOpenAfter() {
        return isOpenAfter;
    }
}
