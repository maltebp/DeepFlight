package Respons;

import kong.unirest.json.JSONObject;

/*
Class for carring responses to request
 */
public class Response{


    public static String generateResponse(String error, String content){
        JSONObject respons = new JSONObject();

        respons.put("error",error);
        respons.put("content",content);
        System.out.println("Generate respons: "+respons.toString());
        return respons.toString();
    }
}
