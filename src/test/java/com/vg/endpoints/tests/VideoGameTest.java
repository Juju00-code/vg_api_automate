package com.vg.endpoints.tests;

import com.vg.endpoints.utils.FormatUtil;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.text.Format;

public class VideoGameTest {

    private static final String VIDEOGAMESEGMENTROUTE = "video_Game_v1_routes";
    private static final String AUTHENTICATIONSEGMENTROUTE = "auth_routes";
    private static final String CG_VALID_PAYLOAD="createGamePayloads";
    private static final String CG_INVALID_DATA_TYPE = "invalidDataType";
    private static final String CG_MISSING_FIELD = "missingField";
    private static final String UPDATEGAME = "updateFields";
    private static final String UD_INVALID_DATA_TYPE = "updateInvalidDataType";
    private static final String UD_MISSING_FIELD = "updateMissingField";
    private static final String IVDT_RELEASEDATE ="ivdtReleaseDateFormat";
    private static String active_token;
    private static String expired_token;
    private static String invalid_token = "adfdsfaserefregfds";

    @BeforeGroups
    public static void auth_token(){
        //Use loginEndpoint function
    }

    @Test
    public static void createGame() throws FileNotFoundException {
        String payloadlocation = FormatUtil .getUrl(VIDEOGAMESEGMENTROUTE,CG_INVALID_DATA_TYPE);
        JSONObject payload = FormatUtil.getJSONPayload(payloadlocation,IVDT_RELEASEDATE);
        //Response createVideoGame = game.createvideoGame(payload);

        //Assert Headers

        //Assert Status Code

        //Assert Response Body

    }












}
