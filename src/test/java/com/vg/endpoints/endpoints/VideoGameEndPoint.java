package com.vg.endpoints.endpoints;

import com.vg.endpoints.utils.FormatUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;


public class VideoGameEndPoint {

    private static final String VIDEOGAMESEGMENTROUTE = "video_game_v1_routes";
    //private static final String AUTHENTICATIONSEGMENTROUTE = "auth_routes";
    private static final String VIEWALLGAME = "viewAllGamesUrl";
    private static final String CREATEGAME="createGameUrl";
    private static final String VIEWGAME="viewGameUrl";
    private static final String UPDATEGAME="updateGameUrl";
    private static final String DELETEGAME="deleteGameUrl";

    public static  Response videoGameList(){
        String getGameListUrl = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,VIEWALLGAME);
        Response res = given()
                .when()
                .get(getGameListUrl);
        return res;
    }

    public static Response createVideoGame(JSONObject payload,String token){
        String getCreateGameUrl = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,CREATEGAME);
        System.out.println(getCreateGameUrl);
        Response res = given()
                .headers("Authorization","Bearer "+ token)
                .contentType(ContentType.JSON)
                .when()
                .body(payload.toString())
                .post(getCreateGameUrl);
                return res;
    }

    public static Response viewGame(String id){
        String getViewGame = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,VIEWGAME);
        Response res = given()
                .pathParam("id", id)
                .when()
                .get(getViewGame);
        return res;
    }

    public static Response updateGame(String id, JSONObject payload,String token){
        String getUpdateGameUrl = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UPDATEGAME);
        Response res = given()
                .pathParam("id",id)
                .body(payload.toString())
                .when()
                .put(getUpdateGameUrl);
        return res;
    }

    public static Response deleteGame(String id,String token){
        String getDeleteGameUrl = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,VIEWGAME);
        Response res = given()
                .pathParam("id",id)
                .when()
                .delete(DELETEGAME);
        return res;
    }

}
