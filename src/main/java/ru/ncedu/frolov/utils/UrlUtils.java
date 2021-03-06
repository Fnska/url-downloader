package ru.ncedu.frolov.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class UrlUtils {
    public static final String DEFAULT_FILENAME = "/index.html";

    /**
     * Copies content on URL with embedded resources (images)
     *
     * @param source      {@link URL} of base resource
     * @param destination {@link File} where to copy
     * @throws IOException
     */
    public static void copyURLFullContentToFiles(URL source, File destination) throws IOException {
        copyURLContentToFile(source, destination); //Copy Base index.html or file
        String fileName = destination.getName(); //Embedded resources if .html
        if (fileName.contains(".html")) {
            Document doc = Jsoup.parse(destination, null, source.toString());
            Elements images = doc.getElementsByTag("img");
            Elements links = doc.getElementsByTag("link");
            String fileNameWithoutFormat = fileName.substring(0, fileName.length() - 5);
            for (Element img : images) {
                if (!img.attr("src").isEmpty()) {
                    URL src = new URL(img.absUrl("src"));
                    String imageName = createFileName(src);
                    String imagePath = destination.getParent() + "/" + fileNameWithoutFormat + "_files/" + imageName;
                    File image = new File(imagePath);
                    copyURLContentToFile(src, image);
                    img.attr("src", imagePath);
                }
            }
            try (BufferedWriter w = new BufferedWriter
                    (new OutputStreamWriter(openOutputStream(destination), doc.charset()))) {
                w.write(doc.toString());
            }
        }

    }

    public static void copyURLContentToFile(URL source, File destination) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(source.openStream());
             BufferedOutputStream out = new BufferedOutputStream(openOutputStream(destination))
        ) {
            copy(in, out);
        }
    }

    public static void copy(BufferedInputStream in, BufferedOutputStream out) throws IOException {
        byte[] buff = new byte[32 * 1024];
        int len = 0;
        while ((len = in.read(buff)) > 0) {
            out.write(buff, 0, len);
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

    /**
     * Open output stream to file and ask for replace
     *
     * @param file {@link File} to open stream
     * @return {@link FileOutputStream}
     * @throws IOException
     */
    private static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            System.out.println("File " + file.getName() + " is already exists. Do you want to replace it? Type: yes or no");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String answer = reader.readLine();
            if ("yes".equals(answer)) {
                return new FileOutputStream(file);
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
