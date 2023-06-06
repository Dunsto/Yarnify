package com.yarnify.API.ResponseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePattern {


    //need to dynamically get this field from query?
    @SerializedName(value = "1335913", alternate = {"1337254", "asdasdasd"})
    @Expose(deserialize = true)
    private ResponsePatternAttributes pattern;

    public ResponsePatternAttributes getPatternAttributes() {

        return pattern;
    }

    public void setPattern(ResponsePatternAttributes pattern) {
        this.pattern = pattern;
    }
}
