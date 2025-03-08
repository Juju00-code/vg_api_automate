package com.vg.endpoints.tests;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.vg.endpoints.endpoints.AuthEndPoint;
import com.vg.endpoints.endpoints.VideoGameEndPoint;
import com.vg.endpoints.utils.FormatUtil;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Format;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VideoGameTest {

    private static final String VIDEOGAMESEGMENTROUTE = "video_Game_v1_routes";
    private static final String CONTENT_TYPE = "application/json";
    private static final String CG_VALID_PAYLOAD=".\\test_data\\payloads\\video_game_payloads\\createVideoGamesPayloads\\createGame.json";
    private static final String CG_INVALID_DATA_TYPE = ".\\test_data\\payloads\\video_game_payloads\\createVideoGamesPayloads\\invalidDatatype.json";
    private static final String CG_MISSING_FIELD = ".\\test_data\\payloads\\video_game_payloads\\createVideoGamesPayloads\\missingFields.json";



    private static final String UD_UPDATEGAME_PAYLOAD = ".\\test_data\\payloads\\video_game_payloads\\updateVideoGamePayloads\\updateFields.json";
    private static final String UD_INVALID_DATA_TYPE = ".\\test_data\\payloads\\video_game_payloads\\updateVideoGamePayloads\\invalidDatatypes.json";
    private static final String UD_MISSING_FIELD = ".\\test_data\\payloads\\video_game_payloads\\updateVideoGamePayloads\\missingFields.json";
    private static final String SCHEMA_GAME = ".\\schemas\\videoGameSchemas\\video_game_schemas.json";
    private static final String SCHEMA_ERROR = ".\\schemas\\errorMessageSchemas.json";
    private static final String CONTENT_TYPE_TEXT = "text/plain;charset=UTF-8";


    private static String gameID = String.valueOf(3);
    private static int gameListRange;
    private static String invalidGameId = String.valueOf(322222);
    private static String active_token;
    private static String expired_token = "esaefewrefadfsdf";
    private static String invalid_token = "adfdsfaserefregfds";
    private static LocalDate todayDate = LocalDate.now();
    SoftAssert softAssert = new SoftAssert();






    @BeforeGroups(groups = "token")
    public static void auth_token(){
        //Use loginEndpoint function
        Response resLogin = AuthEndPoint.login();
        active_token = FormatUtil.getAuthToken(resLogin);

    }
    @Test
    public void viewAllGames() throws IOException, ProcessingException {
        Response resGameList = VideoGameEndPoint.videoGameList();
        List<Map<String,Object>> videoGameList = FormatUtil.selectedGameList(resGameList.jsonPath().getList("$"));
        //Assert Headers
        softAssert.assertEquals(resGameList.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resGameList.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateGameListSchema(videoGameList,SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();


    }
    @Test(groups = "token")
    public void createGame() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil .getUrl(VIDEOGAMESEGMENTROUTE,CG_VALID_PAYLOAD);
        JSONObject payload = FormatUtil.getJSONPayload(CG_VALID_PAYLOAD,"validPayload");
        Response resCreateVideoGame = VideoGameEndPoint.createVideoGame(payload,active_token);
        //gameID = String.valueOf(resCreateVideoGame.jsonPath().getInt("id"));




        //Assert Headers
        softAssert.assertEquals(resCreateVideoGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resCreateVideoGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resCreateVideoGame.getBody().asString(),SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();


    }
    @Test
    public void viewGame() throws IOException, ProcessingException {
        Response resGameDetails = VideoGameEndPoint.viewGame(gameID);
        //Assert Headers
        softAssert.assertEquals(resGameDetails.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resGameDetails.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resGameDetails.getBody().asString(),SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }
    @Test
    public void viewGameInvalidId(){
        Response resGameDetails = VideoGameEndPoint.viewGame(invalidGameId);

        //Assert Headers
        softAssert.assertEquals(resGameDetails.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resGameDetails.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
    }
    /*@Test(groups = "token")
    public void updateGame() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert
        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }*/

    @Test(groups = "token")
    public void updateGameExpToken() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,expired_token);

        //Assert Headers
        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void updateGameInvalidToken() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,invalid_token);

        //Assert Headers
        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void updateGameInvalidId() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateAll");
        Response resUpdateGame = VideoGameEndPoint.updateGame(invalidGameId,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    //UPDATE FIELDS

    @Test(groups = "token")
    public void updateNameFieldOnly() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateName");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);
        System.out.println(resUpdateGame.getBody().asString());



        //Assert Headers
        //softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        //softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        Assert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        Assert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_GAME);
        //softAssert.assertTrue(isResponseMatchSchema);
        Assert.assertTrue(isResponseMatchSchema);
        //softAssert.assertAll();
    }

    @Test(groups = "token")
    public void updateReleaseDateFieldOnly() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateReleaseDate");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void updateReviewScoreFieldOnly() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateReviewScore");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void updateCategoryFieldOnly() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateCategory");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void updateRatingFieldOnly() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_UPDATEGAME_PAYLOAD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_UPDATEGAME_PAYLOAD,"updateRating");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);
        System.out.println(resUpdateGame.getBody().asString());

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_GAME);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }



    @Test(groups = "token")
    public void ud_ivdt_Namebool() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtNamebool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }
    @Test(groups = "token")
    public void ud_ivdt_NameInt() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtNameInt");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_ReleaseDateBool() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtReleaseDateBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_ReleaseDateFormat() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtReleaseDateFormat");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreBool() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtReviewScoreBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreString() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtReviewScoreString");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreAboveMax() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtReviewScoreAboveMax");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_ReviewScoreBelowMin() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtReviewScoreBelowMin");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_CategoryInt() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtCategoryInt");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_CategoryBool() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtCategoryBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_CategoryEnum() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtCategoryEnum");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_RatingInt() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtRatingInt");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_RatingBool() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtRatingBool");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers
        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_ivdt_RatingEnum() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_INVALID_DATA_TYPE);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_INVALID_DATA_TYPE,"updateToIvdtRatingEnum");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }


    //UPDATE MISSING FIELDS
    @Test(groups = "token")
    public void ud_mf_RatingField() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_MISSING_FIELD,"missingRatingField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers
        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }
    @Test(groups = "token")
    public void ud_mf_missingNameField() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_MISSING_FIELD,"missingNameField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_mf_missingReleaseDateField() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_MISSING_FIELD,"missingReleaseDateField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_mf_missingReviewScoreField() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_MISSING_FIELD,"missingReviewScoreField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test(groups = "token")
    public void ud_mf_missingCategoryField() throws IOException, ProcessingException {
        //String payLoadLocation = FormatUtil.getUrl(VIDEOGAMESEGMENTROUTE,UD_MISSING_FIELD);
        JSONObject payLoad = FormatUtil.getJSONPayload(UD_MISSING_FIELD,"missingCategoryField");
        Response resUpdateGame = VideoGameEndPoint.updateGame(gameID,payLoad,active_token);

        //Assert Headers

        softAssert.assertEquals(resUpdateGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resUpdateGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resUpdateGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }


    public void deleteGameExpToken() throws IOException, ProcessingException {
        Response resDeleteGame = VideoGameEndPoint.deleteGame(gameID,active_token);

        //Assert Headers

        softAssert.assertEquals(resDeleteGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resDeleteGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resDeleteGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test
    public void deleteGameInvalidToken() throws IOException, ProcessingException {
        Response resDeleteGame = VideoGameEndPoint.deleteGame(gameID,expired_token);

        //Assert Headers
        softAssert.assertEquals(resDeleteGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resDeleteGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resDeleteGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }

    @Test
    public void deleteGameInvalid() throws IOException, ProcessingException {
        Response resDeleteGame = VideoGameEndPoint.deleteGame(invalidGameId,active_token);


        //Assert Headers

        softAssert.assertEquals(resDeleteGame.contentType(), CONTENT_TYPE);
        softAssert.assertEquals(FormatUtil.standardizeDate(resDeleteGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        boolean isResponseMatchSchema = FormatUtil.validateResponseWithSchema(resDeleteGame.getBody().asString(),SCHEMA_ERROR);
        softAssert.assertTrue(isResponseMatchSchema);
        softAssert.assertAll();
    }



    @Test(groups = "token")
    public void deleteGame() throws IOException, ProcessingException {
        Response resDeleteGame = VideoGameEndPoint.deleteGame(gameID,active_token);

        //Assert Headers
        softAssert.assertEquals(resDeleteGame.contentType(), CONTENT_TYPE_TEXT);
        softAssert.assertEquals(FormatUtil.standardizeDate(resDeleteGame.getHeader("date")),LocalDate.now());
        //Assert Status Code

        //Assert Response Body
        softAssert.assertEquals(resDeleteGame.getBody().asString(),"Video game deleted");
        softAssert.assertAll();

    }












}
