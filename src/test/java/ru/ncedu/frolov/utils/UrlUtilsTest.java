package ru.ncedu.frolov.utils;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlUtilsTest {

    @Test
    void createFileName() throws MalformedURLException {
        String fileName = UrlUtils.createFileName(new URL("http://google.com/"));
        assertEquals("/index.html", fileName);

        fileName = UrlUtils.createFileName(new URL("http://google.com/?q=123"));
        assertEquals("/index.html", fileName);

        fileName = UrlUtils.createFileName(new URL("http://google.com/some?q=123"));
        assertEquals("/some.html", fileName);

        fileName = UrlUtils.createFileName(new URL("http://google.com/some.pdf"));
        assertEquals("/some.pdf", fileName);

        fileName = UrlUtils.createFileName(new URL("http://google.com/some"));
        assertEquals("/some.html", fileName);
    }
}