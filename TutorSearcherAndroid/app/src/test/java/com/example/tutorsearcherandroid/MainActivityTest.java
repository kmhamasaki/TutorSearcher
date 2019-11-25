package com.example.tutorsearcherandroid;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainActivityTest{

    @Test
    public void passwordHash_ValidHash() {
        String validPassword = "password";
        String expectedHash = "5f4dcc3b5aa765d61d8327deb882cf99";
        String actualHash = MainActivity.hashPassword(validPassword);
        assertEquals(expectedHash,actualHash);
    }
}
