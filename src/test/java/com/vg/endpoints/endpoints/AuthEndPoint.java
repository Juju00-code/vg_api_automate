package com.vg.endpoints.endpoints;

import com.vg.endpoints.utils.FormatUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthEndPoint {

    public static final String AUTHENTICATIONSEGMENTROUTE = "auth_routes";
    public static final String AUTHLOGIN = "auth_url";


    public static Response login(){
        String getLoginUrl = FormatUtil.getUrl(AUTHENTICATIONSEGMENTROUTE,AUTHLOGIN);
        Map<String,Object> loginLoad = new HashMap<>();
        loginLoad.put("password","admin");
        loginLoad.put("username","admin");
        Response res = given().contentType(ContentType.JSON)
                .when()
                .body(loginLoad)
                .post(getLoginUrl);
        return res;
    }
}
