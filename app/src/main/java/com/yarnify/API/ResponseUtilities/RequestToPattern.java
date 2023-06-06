package com.yarnify.API.ResponseUtilities;

import com.yarnify.API.Request;
import com.yarnify.API.ResponseModels.ResponsePatternList;
import com.yarnify.API.ResponseUtilities.ToPojo;
import com.yarnify.model.Pattern;

public final class RequestToPattern {

    private RequestToPattern() {
        // Private constructor to prevent instantiation
    }

    public static Pattern toPatternByUrl(String url) {
        Request request = new Request(url);
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());

        // I can make cleaner methods for these, but this is an example of how the classes
        // are deserialize and accessed
        String photoURL = ac.getPatterns().getPatternAttributes().getPhotos().get(0).getMedium_url();
        String title = ac.getPatterns().getPatternAttributes().getTitle();
        String name = ac.getPatterns().getPatternAttributes().getPattern_author().getName();
        String craft = ac.getPatterns().getPatternAttributes().getCraft().getName();
        String permalink = "https://www.ravelry.com/patterns/library/" + ac.getPatterns().getPatternAttributes().getPermalink();
        int minYardage = ac.getPatterns().getPatternAttributes().getMinYardage();
        int maxYardage = ac.getPatterns().getPatternAttributes().getMaxYardage();

        return new Pattern(photoURL, title, name, craft, permalink, minYardage, maxYardage);
    }
}
