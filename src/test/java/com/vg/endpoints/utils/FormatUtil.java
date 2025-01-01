package com.vg.endpoints.utils;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FormatUtil {
    /*public void checkJsonFormat() throws FileNotFoundException {
        File file = new File("src\\test\\resources\\payloads\\video_game_payloads\\createVideoGamesPayloads\\invalidDataType.json");
        FileReader readFile = new FileReader(file);
        JSONTokener tokenize = new JSONTokener(readFile);
        JSONObject data  = new JSONObject(tokenize);
        System.out.println(data.get("ivdtNameInt"));
    }*/

    public String getAuthToken(Response authResponse){
        String authResponseToken = authResponse.getBody().asString();
        String[] getToken = authResponseToken.split(":");
        String token = getToken[1].trim().replace("\"","").replace("}","");
        return token;
    }

    public JSONObject getJSONPayload(String jsonFileLocation) throws FileNotFoundException {
        File file = new File(jsonFileLocation);
        FileReader readFile = new FileReader(file);
        JSONTokener tokenize = new JSONTokener(readFile);
        JSONObject data = new JSONObject(tokenize);
        return data;
    }

    public String getUrl(String routeLocation,String route){
        ResourceBundle segmentRoute = ResourceBundle.getBundle(routeLocation)  ;
        return segmentRoute.getString(route);
    }

    public int[] listTest(int listLength){
        int counter = 5;
        int[] listIndex = new int[5];
        for (int i=0; i < counter; i++){
            int randomListIndex = (int)(Math.random() * listLength);
            listIndex[i] = randomListIndex;
        }

        return listIndex;
    }

    public boolean validateResponseWithSchema(String responseData, String schemaPath) throws IOException, ProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resNode = mapper.readTree(responseData);
        JsonNode schemaNode = mapper.readTree(new File(schemaPath));
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(schemaNode);
        ProcessingReport report = schema.validate(resNode);
        return report.isSuccess();
    }

    public boolean validateGameListSchema(List<Map<String,Object>>games,String schemaFilePath) throws IOException, ProcessingException {
        for(Map<String,Object>game:games){
            ObjectMapper mapper = new ObjectMapper();
            String gameJson = mapper.writeValueAsString(game);
            JsonNode jsonNode = mapper.readTree(gameJson);
            JsonNode schemaNode = mapper.readTree(new File(schemaFilePath));
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(schemaNode);
            ProcessingReport report = schema.validate(jsonNode);
            if(!report.isSuccess()){
                return false;
            }
        }
        return true;
    }

    public LocalDate standardizeDate(String responseDate){
        ZonedDateTime dateTime = ZonedDateTime.parse(responseDate, DateTimeFormatter.RFC_1123_DATE_TIME);
        LocalDate headerDate = dateTime.toLocalDate();
        return headerDate;
    }








}
