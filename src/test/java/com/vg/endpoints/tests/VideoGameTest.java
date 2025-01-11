package com.vg.endpoints.tests;

import com.vg.endpoints.endpoints.AuthEndPoint;
import com.vg.endpoints.endpoints.VideoGameEndPoint;
import com.vg.endpoints.utils.FormatUtil;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.text.Format;
import java.util.List;
import java.util.Map;

public class VideoGameTest {

    private static final String VIDEOGAMESEGMENTROUTE = "video_Game_v1_routes";
    //private static final String AUTHENTICATIONSEGMENTROUTE = "auth_routes";
    private static final String CG_VALID_PAYLOAD=".\\test_data//payloads\\video_game_payloads\\createVideoGamesPayloads\\createGame.json";
    private static final String CG_INVALID_DATA_TYPE = ".\\test_data\\payloads\\video_game_payloads\\createVideoGamesPayloads\\invalidDatatype.json";
    private static final String CG_MISSING_FIELD = ".\\test_data\\payloads\\video_game_payloads\\createVideoGamesPayloads\\missingFields.json";



    private static final String UD_UPDATEGAME_PAYLOAD = ".\\test_data\\payloads\\video_game_payloads\\updateVideoGamesPayloads\\updateFields.json";
    private static final String UD_INVALID_DATA_TYPE = ".\\test_data\\payloads\\video_game_payloads\\updateVideoGamesPayloads\\invalidDatatypes.json";
    private static final String UD_MISSING_FIELD = ".\\test_data\\payloads\\video_game_payloads\\updateVideoGamesPayloads\\missingFields.json";


    private static String gameID;
    private static int gameListRange;
    private static String invalidGameId;
    private static String active_token;
    private static String expired_token;
    private static String invalid_token = "adfdsfaserefregfds";






    @BeforeGroups(groups = "token")
    public static void auth_token(){
        //Use loginEndpoint function
        Response resLogin = AuthEndPoint.login();
        active_token = FormatUtil.getAuthToken(resLogin);

    }
    @Test
    public void viewAllGames(){
        Response resGameList = VideoGameEndPoint.videoGameList();
        List<Map<String,Object>> videoGameList = FormatUtil.selectedGameList(resGameList.jsonPath().getList("$"));
        System.out.println(resGameList.asString());
        System.out.println(videoGameList);
        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }
    @Test(groups = "token")
    public void createGame() throws FileNotFoundException {
        String payLoadLocation = FormatUtil .getUrl(VIDEOGAMESEGMENTROUTE,CG_VALID_PAYLOAD);
        JSONObject payload = FormatUtil.getJSONPayload(payLoadLocation,"validPayload");
        Response resCreateVideoGame = VideoGameEndPoint.createVideoGame(payload,active_token);
        gameID = String.valueOf(resCreateVideoGame.jsonPath().getInt("id"));



        //Assert Headers

        //Assert Status Code

        //Assert Response Body

    }
    @Test
    public void viewGame(){
        Response resGameDetails = VideoGameEndPoint.viewGame(gameID);
        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }
    @Test
    public void viewGameInvalidId(){
        Response resGameDetails = VideoGameEndPoint.viewGame(invalidGameId);
        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }
    @Test(groups = "token")
    public void updateGame() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void updateGameExpToken() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,expired_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void updateGameInvalidToken() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,invalid_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void updateGameInvalidId() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(invalidGameId,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    //UPDATE FIELDS

    @Test(groups = "token")
    public void updateNameFieldOnly() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void updateReleaseDateFieldOnly() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void updateReviewScoreFieldOnly() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void updateCategoryFieldOnly() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void updateRatingFieldOnly() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }



    @Test(groups = "token")
    public void ud_ivdt_Namebool() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtNamebool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }
    @Test(groups = "token")
    public void ud_ivdt_NameInt() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtNameInt");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_ReleaseDateBool() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtReleaseDateBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_ReleaseDateFormat() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtReleaseDateFormat");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreBool() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtReviewScoreBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreString() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtReviewScoreString");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreAboveMax() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtReviewScoreAboveMax");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreBelowMin() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtReviewScoreBelowMin");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_CategoryInt() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtCategoryInt");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_CategoryBool() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtCategoryBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_CategoryEnum() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtCategoryEnum");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_RatingInt() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtRatingInt");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_RatingBool() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtRatingBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_ivdt_RatingEnum() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"updateToIvdtRatingEnum");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }


    //UPDATE MISSING FIELDS
    @Test(groups = "token")
    public void ud_mf_RatingEnum() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"missingNameField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }
    @Test(groups = "token")
    public void ud_mf_missingNameField() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"missingNameField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_mf_missingReleaseDateField() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"missingReleaseDateField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_mf_missingReviewScoreField() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"missingReviewScoreField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_mf_missingCategoryField() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"missingCategoryField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test(groups = "token")
    public void ud_mf_missingRatingField() throws FileNotFoundException {
        String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(payLoadLocation,"missingRatingField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    @Test
    public void deleteGameExpToken(){
        Response resDeleteGame = VideoGameEndPoint.deleteGame(gameID,active_token);
        System.out.println(resDeleteGame.asString());

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }

    public void deleteGameInvalidToken(){
        Response resDeleteGame = VideoGameEndPoint.deleteGame(gameID,active_token);
        System.out.println(resDeleteGame.asString());

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }
    @Test
    public void deleteGameInvalid(){
        Response resDeleteGame = VideoGameEndPoint.deleteGame(invalidGameId,active_token);
        System.out.println(resDeleteGame.asString());

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }



    @Test
    public void deleteGame(){
        Response resDeleteGame = VideoGameEndPoint.deleteGame(gameID,active_token);
        System.out.println(resDeleteGame.asString());

        //Assert Headers

        //Assert Status Code

        //Assert Response Body
    }












}
