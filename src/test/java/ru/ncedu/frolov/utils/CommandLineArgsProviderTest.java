package ru.ncedu.frolov.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ncedu.frolov.utils.CommandLineArgsProvider;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineArgsProviderTest {
    private CommandLineArgsProvider lineArgs;

    @BeforeEach
    public void setUp(){
        lineArgs = new CommandLineArgsProvider();
    }

    @Test
    void zeroArgs() {
        assertThrows(RuntimeException.class, () -> lineArgs.setArgs(new String[]{}));
    }

    @Test
    void setOneValidArg() throws MalformedURLException {
        lineArgs.setArgs(new String[]{"http://google.com/"});
        assertEquals(new URL("http://google.com/"), lineArgs.getUrl());
    }

    @Test
    void setOneInvalidArg() {
        assertThrows(MalformedURLException.class, () -> lineArgs.setArgs(new String[]{"qwe"}));
    }

    @Test
    void setTwoValidArgs1() throws MalformedURLException {
        lineArgs.setArgs(new String[]{"http://google.com/", "C:\\Users"});
        assertEquals(new URL("http://google.com/"), lineArgs.getUrl());
        assertEquals("C:\\Users", lineArgs.getPath());
        assertFalse(lineArgs.isOpenAfter());
    }

    @Test
    void setTwoValidArgs2() throws MalformedURLException {
        lineArgs.setArgs(new String[]{"http://google.com/", "-o"});
        assertEquals(new URL("http://google.com/"), lineArgs.getUrl());
        assertTrue(lineArgs.isOpenAfter());
    }

    @Test
    void setThreeValidArgs() throws MalformedURLException {
        lineArgs.setArgs(new String[]{"http://google.com/", "C:\\Users", "-o"});
        assertEquals(new URL("http://google.com/"), lineArgs.getUrl());
        assertEquals("C:\\Users", lineArgs.getPath());
        assertTrue(lineArgs.isOpenAfter());
    }

}