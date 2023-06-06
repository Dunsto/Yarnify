package com.yarnify.API;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.yarnify.API.ResponseModels.ResponsePatternList;
import com.yarnify.API.ResponseUtilities.ToPojo;

import org.junit.Test;
public class RequestTest {
    @Test
    public void getResponseTest() {
        Request request =
                new Request("patterns.json?ids=1335913");


        assertNotNull(request.getResponse());
        assertEquals("test", request.getResponse());
    }

    @Test
    public void getResponseToPojoTest() {
        Request request =
                new Request("patterns.json?ids=" + "1335913");

        ToPojo toPojo =
                new ToPojo();

        ResponsePatternList ac = toPojo.fromJSONSimple(request.getResponse());


        assertNotNull(ac.getPatterns().getPatternAttributes());

        //Shows that ToPojo is deserializing into ResponsePatternList and ResponsePattern correctly
        //Next steps would be to
        //      How to get and set serialized name of pattern...
        //      create more accurate classes and work through them
        //assertEquals("test", ac.getPatterns().getPatternAttributes().getTitle());
        assertEquals("test", ac.getPatterns().getPatternAttributes().getPhotos().get(0).getMedium_url());
    }
}