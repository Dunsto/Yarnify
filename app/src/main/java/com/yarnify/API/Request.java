
package com.yarnify.API;
import static android.content.ContentValues.TAG;
import static com.example.yarnify.BuildConfig.API_KEY;

import android.util.Log;

import com.example.yarnify.BuildConfig;

import java.io.IOException;
import okhttp3.OkHttpClient;

/*

        This class builds an HTTP request given a URL
        and returns a String of the body of the response.

        I don't know if it's better to build as an Object
        or as utility methods. This is the Object version.
        I tried to hide potentially sensitive details.



 this is a test pattern url "https://api.ravelry.com/patterns.json?ids=1335913"

 */
public class Request {
    private String response;

    public Request(String url) {
        this.response = requestResponse(url);
    }


    private String requestResponse(String url){
        String responseString = "";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://api.ravelry.com/" + url)
                .method("GET", null)
                .addHeader("Authorization", API_KEY)
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            responseString = response.body().string();
        } catch (IOException e) {
            Log.d(TAG, "RequestBuilder: Request response failed");
        }
        return responseString;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}