package com.yarnify.API;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.yarnify.API.ResponseModels.ResponsePatternList;
import com.yarnify.API.ResponseUtilities.ToPojo;

import org.junit.Test;

public class RequestTest {
    @Test
    public void getResponseNotNull() {
        Request request =
                new Request("patterns.json?ids=1335913");
        assertNotNull(request.getResponse());
    }

    @Test
    public void getResponseDeserializedTitle() {
        Request request = new Request("patterns.json?ids=" + "1335913");
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());

        assertNotNull(ac.getPatterns().getPatternAttributes());

        //Shows that ToPojo is deserializing into ResponsePatternList and ResponsePattern correctly
        //Next steps would be to
        //      How to get and set serialized name of pattern...
        //      create more accurate classes and work through them
        //assertEquals("test", ac.getPatterns().getPatternAttributes().getTitle());
        assertEquals("test", ac.getPatterns().getPatternAttributes().getPhotos().get(0).getMedium_url());
        assertEquals("Schelpje", ac.getPatterns().getPatternAttributes().getTitle()); //Schelpje
    }

    @Test
    public void getResponseDeserializedCraft() {
        Request request = new Request("patterns.json?ids=" + "1335913");
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());
        assertEquals("Knitting", ac.getPatterns().getPatternAttributes().getCraft().getName()); //Knitting
    }

    @Test
    public void getResponseDeserializedPattern_author() {
        Request request = new Request("patterns.json?ids=" + "1335913");
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());
        assertEquals("Natasja Hornby", ac.getPatterns().getPatternAttributes().getPattern_author().getName()); //Natasja Hornby
    }

    @Test
    public void getResponseDeserializedMaxYardage() {
        Request request = new Request("patterns.json?ids=" + "1335913");
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());
        assertEquals(350, ac.getPatterns().getPatternAttributes().getMaxYardage());
    }

    @Test
    public void getResponseDeserializedMinYardage() {
        Request request = new Request("patterns.json?ids=" + "1335913");
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());
        assertEquals(230, ac.getPatterns().getPatternAttributes().getMinYardage());
    }

    @Test
    //ArrayList<ResponsePatternAttributesPhoto>
    public void getResponseDeserializedPhotos() {
        Request request = new Request("patterns.json?ids=" + "1335913");
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());
        assertEquals("https://images4-g.ravelrycache.com/uploads/Moonstruckmermaid/924818664/Collage_medium.jpg"
                , ac.getPatterns().getPatternAttributes().getPhotos().get(0).getMedium_url());//https://images4-g.ravelrycache.com/uploads/Moonstruckmermaid/924818664/Collage_medium.jpg
    }

    @Test
    //String
    //Actual: schelpje
    public void getResponseDeserializedPermalink() {
        Request request = new Request("patterns.json?ids=" + "1335913");
        ToPojo toPojo = new ToPojo();
        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());
        String permalink = "https://www.ravelry.com/patterns/library/" + ac.getPatterns().getPatternAttributes().getPermalink();
        assertEquals("https://www.ravelry.com/patterns/library/schelpje", permalink);
    }
}