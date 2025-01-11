package com.vg.endpoints.tests;

import com.vg.endpoints.endpoints.AuthEndPoint;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class AuthTest {


    @Test
    public static void login(){
        Response resLogin = AuthEndPoint.login();
        System.out.println(" -----Login Response ------ " + resLogin.asString());
        //Assert Headers

        //Assert Status Code

        //Assert Response Body

    }

    @Test
    public static void loginWrongUsername(){
        Response resLogin = AuthEndPoint.login();
        System.out.println(" -----Login Response ------ " + resLogin.asString());
        //Assert Headers

        //Assert Status Code

        //Assert Response Body

    }

    @Test
    public static void loginWrongPassword(){
        Response resLogin = AuthEndPoint.login();
        System.out.println(" -----Login Response ------ " + resLogin.asString());
        //Assert Headers

        //Assert Status Code

        //Assert Response Body

    }


}
