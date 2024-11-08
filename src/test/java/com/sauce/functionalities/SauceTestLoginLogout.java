package com.sauce.functionalities;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.sauce.login.SauceLoginLogout;

public class SauceTestLoginLogout {

    SauceLoginLogout loginlogout = new SauceLoginLogout();

    @Parameters({"username", "password"})
    @Test
    public void testLogin(String username, String password) {
        loginlogout.sauceLogin(username, password);
    }
}
