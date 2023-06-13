package com.yarnify.API.ResponseUtilities;

import android.util.Log;

import com.yarnify.API.Request;
import com.yarnify.model.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class UrlToPattern {

    public UrlToPattern() {
        // Private constructor to prevent instantiation
    }

    //pass in a piece of as url like "patterns/search.json?page_size=100" and it will return the list of
    //patterns from the url
    public ArrayList<Pattern> UrlToPatternList(String url) throws JSONException {

        Request request = new Request(url);
        String jsonString = request.getResponse();
        ArrayList<Pattern> patterns = new ArrayList<>();

        JSONObject response = new JSONObject(jsonString);
        JSONArray patternArray = response.getJSONArray("patterns");

        for (int i = 0; i < patternArray.length(); i++) {
            JSONObject patternObject = patternArray.getJSONObject(i);

            // Extract pattern data from the JSON object
            long id = patternObject.getLong("id");
            String image = patternObject.getJSONObject("first_photo").getString("medium_url");
            String title = patternObject.getString("name");
            String creator = patternObject.getJSONObject("designer").getString("name");
            String craft = "knitting";  // Assuming a default value
            String patternURL = "https://www.ravelry.com/patterns/library/" + patternObject.getString("permalink");
            int minYardage = 0;  // Assuming a default value
            int maxYardage = 0;  // Assuming a default value

            // Create a new Pattern object with the extracted data
            Pattern pattern = new Pattern(id, image, title, creator, craft, patternURL, minYardage, maxYardage);
            patterns.add(pattern);
        }

        return patterns;
    }

    public Pattern UrlToPatternById(long id) throws JSONException {

        Request request = new Request("patterns.json?ids=" + String.valueOf(id));
        String jsonString = request.getResponse();

        JSONObject response = new JSONObject(jsonString);
        JSONObject patternObject = response.getJSONObject("patterns").getJSONObject(String.valueOf(id));

        // Extract pattern data from the JSON object
        long apiId = patternObject.optLong("id", -1);  // Default value -1 if "id" is null

        JSONArray photosArray = patternObject.optJSONArray("photos");
        String image = null;
        if (photosArray != null && photosArray.length() > 0) {
            JSONObject photosObject = photosArray.getJSONObject(0); // Assuming you want the first photo
            image = photosObject.optString("medium_url", null); // Default value null if "medium_url" is null
        }

        String title = patternObject.optString("name", null); // Default value null if "name" is null

        JSONObject designerObject = patternObject.getJSONObject("pattern_author");
        String creator = null;
        if (designerObject != null) {
            creator = designerObject.optString("name", null); // Default value null if "name" is null
        }

        String craft = patternObject.getJSONObject("craft").getString("name");
        String patternURL = "https://www.ravelry.com/patterns/library/" + patternObject.optString("permalink", null); // Default value null if "permalink" is null

        int minYardage = patternObject.optInt("yardage", 0); // Default value 0 if "yardage" is null
        int maxYardage = patternObject.optInt("yardage_max", 0); // Default value 0 if "yardage_max" is null

        // Create a new Pattern object with the extracted data
        Pattern pattern = new Pattern(apiId, image, title, creator, craft, patternURL, minYardage, maxYardage);

        return pattern;
    }

    /*public Pattern UrlToPatternById(long id) throws JSONException {

        Request request = new Request("patterns.json?ids=" + String.valueOf(id));
        String jsonString = request.getResponse();

        JSONObject response = new JSONObject(jsonString);
        JSONObject patternObject = response.getJSONObject("patterns").getJSONObject(String.valueOf(id));

        // Extract pattern data from the JSON object
        long apiId = patternObject.getLong("id");

        JSONArray photosArray = patternObject.getJSONArray("photos");
        JSONObject photosObject = photosArray.getJSONObject(0); // Assuming you want the first photo

        String image = photosObject.getString("medium_url");
        String title = patternObject.getString("name");
        String creator = patternObject.getJSONObject("pattern_author").getString("name");
        String craft = patternObject.getJSONObject("craft").getString("name");
        String patternURL = "https://www.ravelry.com/patterns/library/" + patternObject.getString("permalink");
        int minYardage = patternObject.getInt("yardage");
        int maxYardage = patternObject.getInt("yardage_max");

        // Create a new Pattern object with the extracted data
        Pattern pattern = new Pattern(apiId, image, title, creator, craft, patternURL, minYardage, maxYardage);

        return pattern;
    }*/
}
