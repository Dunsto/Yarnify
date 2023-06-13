package com.yarnify.DatabaseModelTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.yarnify.model.User;
import com.yarnify.model.Needle;
import com.yarnify.model.Pattern;
import com.yarnify.model.Yarn;

import org.junit.Test;

import java.util.Optional;

public class ModelTests {
    @Test
    public void getUserTest() {
        User user = new User("BeforeUser", "BeforeRavelry");
        assertEquals("BeforeUser", user.getUsername());
        assertEquals("BeforeRavelry", user.getRavelryUsername());

        user.setUsername("AfterUser");
        user.setRavelryUsername("AfterRavelry");
        assertEquals("AfterUser", user.getUsername());
        assertEquals("AfterRavelry", user.getRavelryUsername());
    }

    @Test
    public void getNeedleTest() {
        Needle needle =
                new Needle("double pointed", "crochet", 3.25, true, "us",
                        5, "Boye", "metal", 2);

        assertEquals("double pointed", needle.getType());
        assertEquals("crochet", needle.getCraft());
        //assertSame(3.25, needle.getMetric());
        assertEquals(3.25, needle.getMetric(), 0); //Delta (Epsilon) is how much it can be off by.
        //assertEquals(3.10, needle.getMetric(), 0.25);
        assertEquals(true, needle.getIsHook());
        assertEquals("us", needle.getUs());
        assertEquals(5, needle.getLength());
        assertEquals("Boye", needle.getBrand());
        assertEquals("metal", needle.getMaterial());
        assertEquals(2, needle.getQty());
    }

    @Test
    public void setNeedleTest() {
        Needle needle =
                new Needle("double pointed", "crochet", 3.25, true, "us",
                        5, "Boye", "metal", 2);

        needle.setType("Straight");         assertEquals("Straight", needle.getType());
        needle.setCraft("Knitting"  );      assertEquals("Knitting", needle.getCraft());
        needle.setMetric(2.75);             assertEquals(2.75, needle.getMetric(), 0);
        needle.setIsHook(false);            assertEquals(false, needle.getIsHook());
        needle.setUS("US");                 assertEquals("US", needle.getUs());
        needle.setLength(3);                assertEquals(3, needle.getLength());
        needle.setBrand("Target");          assertEquals("Target", needle.getBrand());
        needle.setMaterial("wood");         assertEquals("wood", needle.getMaterial());
        needle.setQty(4);                   assertEquals(4, needle.getQty());
    }

    @Test
    public void getPatternTest() {
        Pattern pattern =
                new Pattern("http..", "Title", "Creator",
                        "Craft", "URL", 1, 2);

        assertEquals("http..", pattern.getImageResource());
        assertEquals("Title", pattern.getTitle());
        assertEquals("Creator", pattern.getCreator())       ;
        assertEquals("Craft", pattern.getCraft());
        assertEquals("URL", pattern.getURL());
        assertEquals(1, pattern.getMinYardage());
        assertEquals(2, pattern.getMaxYardage());
    }

    @Test
    public void setPatternTest() {
        Pattern pattern =
                new Pattern("test", "test", "test",
                        "test", "test", 0, 0);

        pattern.setImageResource("http..");       assertEquals("http..", pattern.getImageResource());
        pattern.setTitle("Title");                assertEquals("Title", pattern.getTitle());
        pattern.setCreator("Creator");            assertEquals("Creator", pattern.getCreator())       ;
        pattern.setCraft("Craft");                assertEquals("Craft", pattern.getCraft());
        pattern.setURL("URL");                    assertEquals("URL", pattern.getURL());
        pattern.setMinYardage(1);                 assertEquals(1, pattern.getMinYardage());
        pattern.setMaxYardage(2);                 assertEquals(2, pattern.getMaxYardage());

/*        Pattern patternTest =
                new Pattern("http..", "Title", "Creator",
                        "Craft", "URL", 1, 2);

        assertEquals(patternTest, pattern);*/
    }

    @Test
    public void getYarnTest() {
        Yarn yarn =
                new Yarn("name", "yarnWeight", "lengthUnits", 5,
                        "weightUnits", 3, "colorFamily", "colorway", "dyeLot");

        assertEquals("name", yarn.getName());
        assertEquals("yarnWeight", yarn.getYarnWeight());
        assertEquals("lengthUnits", yarn.getLengthUnits());
        assertEquals(5, yarn.getTotalLength());
        assertEquals("weightUnits", yarn.getWeightUnits());
        assertEquals(3, yarn.getTotalWeight());
        assertEquals("colorFamily", yarn.getColorFamily());
        assertEquals("colorway", yarn.getColorway());
        assertEquals("dyeLot", yarn.getDyeLot());
    }

    @Test
    public void setYarnTest() {
        Yarn yarn =
                new Yarn("test", "test", "test", 0,
                        "test", 0, "test", "test", "test");

        yarn.setName("name");                       assertEquals("name", yarn.getName());
        yarn.setYarnWeight("yarnWeight");           assertEquals("yarnWeight", yarn.getYarnWeight());
        yarn.setLengthUnits("lengthUnits");         assertEquals("lengthUnits", yarn.getLengthUnits());
        yarn.setTotalLength(5);                     assertEquals(5, yarn.getTotalLength());
        yarn.setWeightUnits("weightUnits");         assertEquals("weightUnits", yarn.getWeightUnits());
        yarn.setTotalWeight(3);                     assertEquals(3, yarn.getTotalWeight());
        yarn.setColorFamily("colorFamily");         assertEquals("colorFamily", yarn.getColorFamily());
        yarn.setColorway("colorway");               assertEquals("colorway", yarn.getColorway());
        yarn.setDyeLot("dyeLot");                   assertEquals("dyeLot", yarn.getDyeLot());
    }
}
