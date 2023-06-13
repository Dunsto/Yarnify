package com.yarnify.API.ResponseUtilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yarnify.API.ResponseModels.ResponsePatternList;


/*
    This class take the String returned from a Request, builds a Gson object which
    will deserialize the JSON into the parent class "alexPatternClassFromJSON"
 */

public class ToPojo {



    public ToPojo(){}
    public ResponsePatternList fromJSONSimple(String json){

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        Gson gson = new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation()
                .create();

        ResponsePatternList alexPatternClassFromJSON = gson.fromJson(jsonObject, ResponsePatternList.class);

        return  alexPatternClassFromJSON;
    }
}
