import org.junit.jupiter.api.Test;
import photos.Album;
import photos.PhotoLibrary;
import photos.Photograph;
import photos.PhotographContainer;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PhotoContainerTests {
    
    private class PCExtended extends PhotographContainer {
        public PCExtended() {
            super("Extended");
        }
        
    }

    @Test
    public void testIsAbstract() {
        if (!Modifier.isAbstract(PhotographContainer.class.getModifiers()))
            fail("PhotographContainer class is not abstract.");
    }
    
    @Test
    public void testPhotoLibraryImplements() {
        if (!PhotoLibrary.class.getGenericSuperclass().equals(PhotographContainer.class)) {
            fail("PhotoLibrary does not extend PhotographContainer.");
        }
    }
    
    @Test
    public void testAlbumImplements() {
        if (!Album.class.getGenericSuperclass().equals(PhotographContainer.class)) {
            fail("Album does not extend PhotographContainer.");
        }
    }
    
    @Test
    public void testConstructor() {
        try {
            PCExtended p = new PCExtended();
            assertEquals("Extended", p.getName(), "PhotographContainer constructor failed to set the name (or getter failed to return it).");
            assertEquals(new ArrayList<Photograph>(), p.getPhotos(), "PhotographContainer constructor failed to initialize photos (or getter failed to return it).");
        } catch (Exception e) {
            fail("PhotographContainer constructor failed to initialize when called from an extending class.");
        }
    }
    
    @Test
    public void testMethods() {
        try {
            PCExtended p = new PCExtended();
            PCExtended.class.getMethod("getPhotosBetween", String.class, String.class);
        } catch (Exception e) {
            fail("PhotographContainer did not have getPhotosBetween method.");
        }
    }
    
    @Test
    public void testMethods2() {
        try {
            PCExtended p = new PCExtended();
            PCExtended.class.getMethod("getPhotos", Integer.TYPE);
        } catch (Exception e) {
            fail("PhotographContainer did not have getPhotos method.");
        }
    }
    
    
    @Test
    public void testMethods3() {
        try {
            PCExtended p = new PCExtended();
            PCExtended.class.getMethod("getPhotosInYear", Integer.TYPE);
        } catch (Exception e) {
            fail("PhotographContainer did not have getPhotosInYear method.");
        }
    }    
    
    @Test
    public void testMethods4() {
        try {
            PCExtended p = new PCExtended();
            PCExtended.class.getMethod("getPhotosInMonth", Integer.TYPE, Integer.TYPE);
        } catch (Exception e) {
            fail("PhotographContainer did not have getPhotosInMonth method.");
        }
    }

    @Test
    public void testMethod5() {
        try {
            PhotoLibrary.class.getDeclaredMethod("addPhoto", Photograph.class);
            fail("PhotoLibrary should inherit addPhoto not implement it.");
            PhotoLibrary.class.getDeclaredMethod("getPhotosBetween", String.class, String.class);
            fail("PhotoLibrary should inherit getPhotosBetween not implement it.");
            PhotoLibrary.class.getDeclaredMethod("getPhotosInYear", Integer.TYPE);
            fail("PhotoLibrary should inherit getPhotosInYear not implement it.");
            PhotoLibrary.class.getDeclaredMethod("getPhotosInMonth", Integer.TYPE, Integer.TYPE);
            fail("PhotoLibrary should inherit getPhotosInMonth not implement it.");
        } catch (Exception e) {
            // all good!
        }
    }
    

    @Test
    public void testMethod6() {
        try {
            Album.class.getDeclaredMethod("addPhoto", Photograph.class);
            fail("Album should inherit addPhoto not implement it.");
            Album.class.getDeclaredMethod("removePhoto", Photograph.class);
            fail("Album should inherit removePhoto not implement it.");
            Album.class.getDeclaredMethod("getPhotosBetween", String.class, String.class);
            fail("Album should inherit getPhotosBetween not implement it.");
            Album.class.getDeclaredMethod("getPhotosInYear", Integer.TYPE);
            fail("Album should inherit getPhotosInYear not implement it.");
            Album.class.getDeclaredMethod("getPhotosInMonth", Integer.TYPE, Integer.TYPE);
            fail("Album should inherit getPhotosInMonth not implement it.");
        } catch (Exception e) {
            // all good!
        }
    }
    

    @Test
    public void testMethod7() {
        try {
            PhotoLibrary.class.getDeclaredMethod("removePhoto", Photograph.class);
        } catch (Exception e) {
            fail("PhotoLibrary should override removePhoto from the PhotographContainer.");
        }
    }

}
