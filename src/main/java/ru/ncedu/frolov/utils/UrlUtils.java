package ru.ncedu.frolov.utils;

import java.io.*;
import java.net.URL;

public class UrlUtils {
    public static final String DEFAULT_FILENAME = "/index.html";

    public static void copyURLContentToFile(URL source, File destination) throws IOException {
        try (InputStream in = new BufferedInputStream(source.openStream());
             OutputStream out = openOutputStream(destination)
        ) {
            copy(in, out);
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        int data = in.read();
        while (data != -1) {
            out.write(data);
            data = in.read();
        }
    }

    public static String createFileName(URL url) {
        if (url.getPath().equals("/")) {
            return DEFAULT_FILENAME;
        }
        if (url.getPath().matches(".+\\..+")) {
            return url.getPath();
        }
        return url.getPath() + ".html";
    }

    private static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            System.out.println("File is already exists. Do you want to replace it? Type: yes or no");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String answer = reader.readLine();
            reader.close();
            if ("yes".equals(answer)) {
                return new FileOutputStream(file);
            } else {
                System.out.println("See you next time");
                System.exit(0);
            }
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }
}
