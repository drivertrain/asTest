package com.theboyz.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ffsAPIRegisterTest {

    /*
        This method tests the register() function with valid credentials.
        The method should return true.
     */

    @Test
    public void register() {
        assertTrue(ffsAPI.register("chase.uphaus@yahoo.com", "Chase",
                "test"));
    }
}