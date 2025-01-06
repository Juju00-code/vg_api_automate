package com.vg.endpoints.endpoints;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class authEndPoint {


    public Response login(){
        Response res = given()
                .when()
                .post();

        return res;
    }
}
