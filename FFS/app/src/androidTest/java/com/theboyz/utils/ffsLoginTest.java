package com.theboyz.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ffsLoginTest {

    //authenticateFalse will test two null values in the authentication function and it is expected to return false
    @Test
    public void authenticateFalse() {
        assertNull(ffsAPI.authenticate("bademail", "badpass"));
    }

    //authenticateTrue will test a known email and password and it is expected to return true
    @Test
    public void authenticateTrue() {
        assertNotNull(ffsAPI.authenticate("chase.uphaus@yahoo.com", "test"));
    }
}