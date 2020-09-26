package ru.ncedu.frolov;

import ru.ncedu.frolov.utils.CommandLineArgsProvider;
import ru.ncedu.frolov.utils.UrlUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class App {

    public static void main(String[] args) {
        try {
            CommandLineArgsProvider argsProvider = new CommandLineArgsProvider(args);

            URL url = argsProvider.getUrl();
            String fileName = UrlUtils.createFileName(url);
            File file = new File(argsProvider.getPath() + fileName);

            UrlUtils.copyURLContentToFile(url, file);

            if (argsProvider.isOpenAfter()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            }
        } catch (MalformedURLException e) {
            System.err.println("Invalid or empty URL provided");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Invalid file");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Something went wrong");
            System.exit(1);
        }
    }
}
