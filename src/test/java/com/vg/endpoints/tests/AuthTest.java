package com.vg.endpoints.tests;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.vg.endpoints.endpoints.AuthEndPoint;
import com.vg.endpoints.utils.FormatUtil;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.LocalDate;

public class AuthTest {
    private static final String SCHEMA_AUTH = ".\\schemas\\authGameSchemas\\auth_schemas.json";
    private static final String CONTENT_TYPE = "application/json";
    private static final String SCHEMA_ERROR = ".\\schemas\\errorMessageSchemas.json";
    static SoftAssert softAssert = new SoftAssert();


    @Test
    public static void login() throws IOException, ProcessingException {
        Response resLogin = AuthEndPoint.login();
        //Assert Headers
        softAssert.assertEquals(resLogin.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resLogin.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resLogin.getBody().asString(),SCHEMA_AUTH);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();

    }

    @Test
    public  void loginWrongUsername() throws IOException, ProcessingException {
        Response resLogin = AuthEndPoint.login();
        //Assert Headers
        softAssert.assertEquals(resLogin.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resLogin.getHeader("date")),LocalDate.now());
        //Assert Status Code


        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resLogin.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test
    public static void loginWrongPassword() throws IOException, ProcessingException {
        Response resLogin = AuthEndPoint.login();
        //Assert Headers
        softAssert.assertEquals(resLogin.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resLogin.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resLogin.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();

    }


}
