package com.vg.endpoints.endpoints;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class videoGameEndPoint {

    private static final String VIDEOGAMESEGMENTROUTE = "video_Game_v1_routes";
    private static final String AUTHENTICATIONSEGMENTROUTE = "auth_routes";
    private static final String VIEWALLGAME = "viewAllGamesUrl";
    private static final String CREATEGAME="createGameUrl";
    private static final String VIEWGAME="viewGameUrl";
    private static final String UPDATEGAME="updateGameUrl";
    private static final String DELETEGAME="deleteGameUrl";

    public Response videoGameList(){
        Response res = given()
                .when()
                .get(VIEWALLGAME);
        return res;
    }

    public Response createVideoGame(){
        Response res = given()
                .when()
                .post(CREATEGAME);
                return res;
    }

    public Response viewGame(){
        Response res = given()
                .when()
                .get(VIEWGAME);
        return res;
    }

    public Response updateGame(){
        Response res = given()
                .when()
                .put(UPDATEGAME);
        return res;
    }

    public Response deleteGame(){
        Response res = given()
                .when()
                .delete(DELETEGAME);
    }

}
