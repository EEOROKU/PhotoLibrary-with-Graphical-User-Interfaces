import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import photos.Photograph;

import java.util.concurrent.TimeUnit;

public class PhotographTests {

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testConstructor() {
        Photograph b = new Photograph("mypic.jpg", "Grand Canyon", "2019-02-13", 2);
        assertEquals("Grand Canyon", b.getCaption(),"photos.Photograph constructor (or getter) failed for caption");
        assertEquals("mypic.jpg", b.getFilename(),"photos.Photograph constructor (or getter) failed for filename");
        assertEquals("2019-02-13", b.getDateTaken(),"photos.Photograph constructor (or getter) failed for dateTaken");
        assertEquals(2, b.getRating(),"photos.Photograph constructor (or getter) failed for rating");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testConstructorOriginal() {
        Photograph b = new Photograph("mypic.jpg", "Grand Canyon");
        assertEquals("Grand Canyon", b.getCaption(),"Original photograph constructor (or getter) failed for caption");
        assertEquals("mypic.jpg", b.getFilename(),"Original photograph constructor (or getter) failed for filename");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsType() throws Exception {
        try {
            Photograph.class.getDeclaredMethod("equals", Object.class);
        } catch (NoSuchMethodException e) {
            fail("photos.Photograph equals method not specified correctly.");
        } catch (Exception e) {
            throw e;
        }
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsSame() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 2", "2019-02-13", 3);
        assertTrue(b.equals(b),"photos.Photograph.equals() failed: Not symmetric");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsSimilar() {
        Photograph b = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 5);
        Photograph c = new Photograph("mypic3.jpg", "Grand Canyon 3", "2019-02-13", 5);
        assertTrue(b.equals(c),"photos.Photograph.equals() failed: Two photographs with same caption and file name not equal");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsDifferentTypes() throws Exception {
        try {
            Photograph b = new Photograph("yosemite.png", "Family Vacation");
            assertFalse(b.equals(42),"photos.Photograph.equals() failed: Compared photos.Photograph to Integer");
        } catch (ClassCastException e) {
            fail("photos.Photograph.equals() failed: Casting a non-photos.Photograph to a photos.Photograph");
        } catch (Exception e) {
            throw e;
        }
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRatingMutator() {
        Photograph b = new Photograph("mypic.jpg", "Grand Canyon", "2019-02-13", 2);
        assertEquals(2, b.getRating(),"photos.Photograph constructor (or getter) failed for rating");
        b.setRating(3);
        assertEquals(3, b.getRating(),"photos.Photograph rating mutator (or getter) failed");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRatingMutatorEdge1() {
        Photograph b = new Photograph("mypic.jpg", "Grand Canyon", "2019-02-13", 2);
        assertEquals(2, b.getRating(),"photos.Photograph constructor (or getter) failed for rating");
        b.setRating(0);
        assertEquals(0, b.getRating(),"photos.Photograph rating mutator (or getter) failed (edge case)");
        b.setRating(5);
        assertEquals(5, b.getRating(),"photos.Photograph rating mutator (or getter) failed (edge case)");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRatingMutatorEdge2() {
        Photograph b = new Photograph("mypic.jpg", "Grand Canyon", "2019-02-13", 2);
        assertEquals(2, b.getRating(),"photos.Photograph constructor (or getter) failed for rating");
        b.setRating(-5);
        assertEquals(2, b.getRating(),"photos.Photograph rating mutator (or getter) failed (edge case)");
        b.setRating(6);
        assertEquals(2, b.getRating(),"photos.Photograph rating mutator (or getter) failed (edge case)");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testToString() {
        Photograph b = new Photograph("mypic.jpg", "Grand Canyon", "2019-02-13", 2);
        String s = b.toString();
        assertTrue(s.contains("Grand Canyon"),"photos.Photograph.toString must have caption");
    }

}
