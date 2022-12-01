import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import photos.Album;
import photos.Photograph;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumTests {

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
	@Test
	public void testConstructor() {
		Album a = new Album("Vacation");
        assertEquals("Vacation", a.getName(), "photos.Album constructor (or getter) failed for name");
	}
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testConstructor2() {
        Album a = new Album("Vacation");
        assertNotNull(a.getPhotos(), "photos.Album constructor did not initialize photos list (or getter failed)");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testAddPhotoEdge() {
        Album a = new Album("Vacation");
        Photograph b = new Photograph("mypic4.jpg", "Grand Canyon", "2019-01-11", 2);
        assertEquals(0, a.getPhotos().size(), "photos.Album constructor did not create an empty photos list");
        a.addPhoto(null);
        assertEquals(0, a.getPhotos().size(), "photos.Album allowed adding null photograph");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testAddPhoto() {
        Album a = new Album("Vacation");
        Photograph b = new Photograph("mypic4.jpg", "Grand Canyon", "2019-01-11", 2);
        assertEquals(0, a.getPhotos().size(), "photos.Album constructor did not create an empty photos list");
        assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo");
        assertEquals(1, a.getPhotos().size(),"photos.Album addPhoto did not add a photo");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testAddPhoto2() {
        Album a = new Album("Vacation");
        Photograph b = new Photograph("mypic4.jpg", "Grand Canyon", "2019-01-11", 2);
        assertEquals(0, a.getPhotos().size(),"photos.Album constructor did not create an empty photos list");
        assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo");
        assertEquals(1, a.getPhotos().size(),"photos.Album addPhoto did not add a photo");
        assertFalse(a.addPhoto(b),"photos.Album addPhoto incorrectly allowed a photo to be added twice");
        assertEquals(1, a.getPhotos().size(),"photos.Album addPhoto incorrectly allowed a photo to be added twice");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testHasPhoto() {
        Album a = new Album("Vacation");
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        assertEquals(0, a.getPhotos().size(),"photos.Album constructor did not create an empty photos list");
        assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo");
        assertTrue(a.hasPhoto(b),"photos.Album hasPhoto did not find a photo in the album");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testHasPhotoEdge() {
        Album a = new Album("Vacation");
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        assertEquals(0, a.getPhotos().size(),"photos.Album constructor did not create an empty photos list");
        assertFalse(a.hasPhoto(b),"photos.Album hasPhoto found a photo that wasn't in the album (empty)");
        assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo");
        assertFalse(a.hasPhoto(c),"photos.Album hasPhoto found a photo that wasn't in the album");
        assertFalse(a.hasPhoto(null),"photos.Album hasPhoto found a null photo");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testHasPhoto2() {
        Album a = new Album("Vacations");
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);
        assertEquals(0, a.getPhotos().size(),"photos.Album constructor did not create an empty photos list");
        assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.Album addPhoto did not add a photo");
        assertTrue(a.addPhoto(d),"photos.Album addPhoto did not add a photo");
        assertTrue(a.hasPhoto(c),"photos.Album hasPhoto did not find a photo in the album");
        assertTrue(a.hasPhoto(b),"photos.Album hasPhoto did not find a photo in the album");
        assertTrue(a.hasPhoto(d),"photos.Album hasPhoto did not find a photo in the album");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemovePhoto() {
        Album a = new Album("Vacations");
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);
        assertEquals(0, a.getPhotos().size(),"photos.Album constructor did not create an empty photos list");
        assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.Album addPhoto did not add a photo");
        assertTrue(a.addPhoto(d),"photos.Album addPhoto did not add a photo");
        assertEquals(3, a.getPhotos().size(),"Repeated photos.Album addPhoto didn't add the right number of photos");
        assertTrue(a.removePhoto(c),"photos.Album removePhoto did not remove a photo in the album");
        assertFalse(a.hasPhoto(c),"photos.Album removePhoto did not remove the right photo in the album");
        assertEquals(2, a.getPhotos().size(),"photos.Album removePhoto did not remove a photo in the album");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemovePhotoEdge() {
        Album a = new Album("Vacation Picts");
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        assertFalse(a.removePhoto(c),"photos.Album removePhoto returned true when removing a photo that didn't exist");
        assertFalse(a.removePhoto(null),"photos.Album removePhoto returned true when removing null");
    }
    
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testNumPhotographs() {
        Album a = new Album("Vacation photos.Album");
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);
        assertEquals(0, a.getPhotos().size(), "photos.Album constructor did not create an empty photos list");
        assertEquals(0, a.numPhotographs(), "photos.Album numPhotographs not returning 0 if empty");
        assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo" );
        assertEquals(1, a.numPhotographs(),"photos.Album numPhotographs not returning correct count");
        assertTrue(a.addPhoto(c),"photos.Album addPhoto did not add a photo");
        assertEquals(2, a.numPhotographs(),"photos.Album numPhotographs not returning correct count");
        assertTrue(a.addPhoto(d),"photos.Album addPhoto did not add a photo");
        assertEquals(3, a.numPhotographs(),"photos.Album numPhotographs not returning correct count");
        
    }
    

	@Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
	public void equalsType() throws Exception {
	    try {
	        Album.class.getDeclaredMethod("equals", Object.class);
	    } catch (NoSuchMethodException e) {
	        fail("photos.Album equals method not specified correctly.");
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
	@Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
	public void equalsSame() {
        Album a = new Album("Vacation Picts");
		assertTrue(a.equals(a),"photos.Album.equals() failed: Not symmetric");
	}
	@Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
	public void equalsSimilar() {
        Album a = new Album("UVA Picts");
        Album b = new Album("UVA Picts");
		assertTrue(b.equals(a),"photos.Album.equals() failed: Two Albums with same name not equal");
	}
	@Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
	public void equalsDifferentTypes() throws Exception {
        try {
            Album a = new Album("Rafting");
            assertFalse(a.equals("Rafting"),"photos.Album.equals() failed: Compared photos.Album to String");
        } catch (ClassCastException e) {
            fail("photos.Album.equals() failed: Casting a non-photos.Album to an photos.Album");
        } catch (Exception e) {
            throw e;
        }
	}

	@Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
	public void testToString() {        
	    Album a = new Album("Vacations");
	    Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
	    Photograph c = new Photograph("green.jpg", "Rafting", "2016-05-11", 5);
	    Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);
	    assertEquals(0, a.getPhotos().size(),"photos.Album constructor did not create an empty photos list");
	    assertTrue(a.addPhoto(b),"photos.Album addPhoto did not add a photo");
	    assertTrue(a.addPhoto(c),"photos.Album addPhoto did not add a photo");
	    assertTrue(a.addPhoto(d),"photos.Album addPhoto did not add a photo");
	    String s = a.toString();
		assertTrue(s.contains("Vacations"),"photos.Album.toString must have name");
		assertTrue(s.contains("mypic45.jpg"),"photos.Album.toString must have photograph filenames");
        assertTrue(s.contains("green.jpg"),"photos.Album.toString must have photograph filenames");
        assertTrue(s.contains("water2.jpg"),"photos.Album.toString must have photograph filenames");
	}

}
