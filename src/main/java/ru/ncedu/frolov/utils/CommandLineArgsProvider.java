package ru.ncedu.frolov.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class CommandLineArgsProvider {
    private URL url;
    private String path = System.getProperty("user.dir");
    private boolean isOpenAfter = false;
    private String[] args;

    public CommandLineArgsProvider() {
    }

    public CommandLineArgsProvider(String[] args) throws MalformedURLException {
        setArgs(args);
    }

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

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isOpenAfter() {
        return isOpenAfter;
    }

    public void setOpenAfter(boolean openAfter) {
        isOpenAfter = openAfter;
    }
}
