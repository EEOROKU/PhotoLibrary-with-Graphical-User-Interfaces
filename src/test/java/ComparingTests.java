import org.junit.jupiter.api.Test;
import photos.CompareByRating;
import photos.Photograph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparingTests {
    
    private CompareByRating byRating = new CompareByRating();

    @Test
    public void testPhotographCompare() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 5);
        assertEquals( 0, b.compareTo(b), "Photograph compareTo didn't say two photos were equal (return 0)");
        assertEquals( 0, b.compareTo(c), "Photograph compareTo didn't say two photos were equal (return 0)");
    }
    
    @Test
    public void testPhotographCompare2() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-02-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 5);
        assertTrue(b.compareTo(c) < 0, "Photograph compareTo didn't compare photos properly by date");
        assertTrue(c.compareTo(b) > 0, "Photograph compareTo didn't compare photos properly by date");
    }
    
    @Test
    public void testPhotographCompare3() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2017-12-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-02-13", 5);
        assertTrue(b.compareTo(c) < 0, "Photograph compareTo didn't compare photos properly by date");
        assertTrue(c.compareTo(b) > 0, "Photograph compareTo didn't compare photos properly by date");
    }
    
    @Test
    public void testPhotographCompare4() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-01-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-04-11", 5);
        assertTrue(b.compareTo(c) < 0, "Photograph compareTo didn't compare photos properly by date");
        assertTrue(c.compareTo(b) > 0, "Photograph compareTo didn't compare photos properly by date");
    }
    
    @Test
    public void testPhotographCompare5() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 1", "2018-01-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-01-13", 5);
        assertTrue(b.compareTo(c) < 0, "Photograph compareTo didn't compare photos properly by caption if dates equal");
        assertTrue(c.compareTo(b) > 0, "Photograph compareTo didn't compare photos properly by caption if dates equal");
    }
    
    
    @Test
    public void testPhotographCompare6() {
        Photograph b = new Photograph("mypic3.jpg", "A great photo!", "2018-01-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-01-13", 5);
        assertTrue(b.compareTo(c) < 0, "Photograph compareTo didn't compare photos properly by caption if dates equal");
        assertTrue( c.compareTo(b) > 0, "Photograph compareTo didn't compare photos properly by caption if dates equal");
    }
    
    @Test
    public void testCompareByRating() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 3);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 3);
        assertEquals(0, byRating.compare(b,b), "CompareByRating compare() didn't say two photos were equal (return 0)");
        assertEquals(0, byRating.compare(b, c), "CompareByRating compare() didn't say two photos were equal (return 0)");
    }
    
    @Test
    public void testCompareByRating2() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-02-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 2);
        assertTrue(byRating.compare(b,c) < 0, "CompareByRating compare() didn't compare photos properly by rating");
        assertTrue(byRating.compare(c,b) > 0, "CompareByRating compare() didn't compare photos properly by rating");
    }
    
    @Test
    public void testCompareByRating3() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2017-12-13", 1);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-02-13", 0);
        assertTrue(byRating.compare(b,c) < 0, "CompareByRating compare() didn't compare photos properly by rating");
        assertTrue(byRating.compare(c,b) > 0, "CompareByRating compare() didn't compare photos properly by rating");
    }
    
    @Test
    public void testCompareByRating5() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 1", "2018-01-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-01-13", 5);
        assertTrue(byRating.compare(b,c) < 0, "CompareByRating compare() didn't compare photos properly by caption if rating equal");
        assertTrue(byRating.compare(c,b) > 0, "CompareByRating compare() didn't compare photos properly by caption if rating equal");
    }
    
    
    @Test
    public void testCompareByRating6() {
        Photograph b = new Photograph("mypic3.jpg", "A great photo!", "2018-01-13", 2);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2018-01-13", 2);
        assertTrue(byRating.compare(b,c) < 0, "CompareByRating compare() didn't compare photos properly by caption if rating equal");
        assertTrue(byRating.compare(c,b) > 0, "CompareByRating compare() didn't compare photos properly by caption if rating equal");
    }
}
