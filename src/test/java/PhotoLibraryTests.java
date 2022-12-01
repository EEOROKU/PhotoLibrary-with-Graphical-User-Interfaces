import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import photos.Album;
import photos.PhotoLibrary;
import photos.Photograph;

import static org.junit.jupiter.api.Assertions.*;

public class PhotoLibraryTests {

    @Timeout(100)
    @Test()
    public void constructor() {
        PhotoLibrary b = new PhotoLibrary("MyLibrary", 14);
        assertEquals("MyLibrary", b.getName(), "photos.PhotoLibrary's constructor failed to initialize name (getter did not return expected value)");
        assertEquals(14, b.getId(),"photos.PhotoLibrary's constructor failed to initialize id (getter did not return expected value)");
        assertEquals(new ArrayList<Photograph>(), b.getPhotos(),"photos.PhotoLibrary's constructor failed to initialize photos (getter did not return expected value)");
        assertEquals(new ArrayList<Album>(), b.getAlbums(),"photos.PhotoLibrary's constructor failed to initialize albums (getter did not return expected value)");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void setName() {
        PhotoLibrary b = new PhotoLibrary("Arphaxad", 14);
        b.setName("Peleg");
        assertEquals("Peleg", b.getName(),"photos.PhotoLibrary.setName() failed (getter did not return expected value)");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void eraseEmpty() {
        PhotoLibrary b = new PhotoLibrary("Peleg", 17);
        assertFalse(b.removePhoto(new Photograph("Caption","Filename")), "photos.PhotoLibrary.erasePhoto() failed (edge case)");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void eraseOnly() {
        PhotoLibrary b = new PhotoLibrary("Peleg", 17);
        Photograph p = new Photograph("Caption","Filename");
        b.getPhotos().add(p);
        assertTrue(b.createAlbum("Vacation"),"photos.PhotoLibrary createAlbum did not create the specified album.");
        assertTrue(b.addPhotoToAlbum(p, "Vacation"),"photos.PhotoLibrary addPhotoToAlbum did not add a photo to an empty album");
        assertTrue(b.removePhoto(new Photograph("Caption","Filename")), "photos.PhotoLibrary.erasePhoto() failed (erasePhoto() returned wrong result) (also check getPhotos())");
        assertEquals(new ArrayList<Photograph>(), b.getPhotos(),"photos.PhotoLibrary.erasePhoto() failed (photo wasn't removed) (also check getPhotos())");
        Album a = null;
        for (Album c: b.getAlbums()) {
            if (c.getName().equals("Vacation"))
                a = c;
        }
        assertNotNull(a, "expected to find album \"Vacation\"");
        assertEquals(new ArrayList<Photograph>(), a.getPhotos(),"photos.PhotoLibrary.erasePhoto() failed (photo wasn't removed from the albums) (also check getAlbums())");

    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void eraseThere() {
        PhotoLibrary b = new PhotoLibrary("Peleg", 17);
        b.getPhotos().add(new Photograph("C1", "F1"));
        b.getPhotos().add(new Photograph("C2", "F2"));
        b.getPhotos().add(new Photograph("C3", "F3"));
        assertTrue(b.createAlbum("Vacation"),"photos.PhotoLibrary createAlbum did not create the specified album.");
        assertTrue(b.addPhotoToAlbum(new Photograph("C2", "F2"), "Vacation"), "photos.PhotoLibrary addPhotoToAlbum did not add a photo to an empty album");
        assertTrue(b.createAlbum("Vacation2"),"photos.PhotoLibrary createAlbum did not create the specified album.");
        assertTrue(b.addPhotoToAlbum(new Photograph("C1", "F1"), "Vacation2"),"photos.PhotoLibrary addPhotoToAlbum did not add a photo to an empty album");
        assertTrue(b.addPhotoToAlbum(new Photograph("C2", "F2"), "Vacation2"),"photos.PhotoLibrary addPhotoToAlbum did not add a photo to an empty album");
        assertTrue(b.addPhotoToAlbum(new Photograph("C3", "F3"), "Vacation2"),"photos.PhotoLibrary addPhotoToAlbum did not add a photo to an empty album");

        assertTrue(b.removePhoto(new Photograph("C2","F2")));
        assertFalse(b.getPhotos().contains(new Photograph("C2","F2")),"photos.PhotoLibrary.erasePhoto() failed (something wasn't removed) (also check getPhotos())");
        Album a = null;
        for (Album c: b.getAlbums()) {
            if (c.getName().equals("Vacation"))
                a = c;
        }
        assertEquals(new ArrayList<Photograph>(), a.getPhotos(),"photos.PhotoLibrary.erasePhoto() failed (photo wasn't removed from the albums) (also check getAlbums())");
        a = null;
        for (Album c: b.getAlbums()) {
            if (c.getName().equals("Vacation2"))
                a = c;
        }
        assertFalse(a.getPhotos().contains(new Photograph("C2","F2")),"photos.PhotoLibrary.erasePhoto() failed (something wasn't removed from an album) (also check getPhotos())");
        assertTrue(a.getPhotos().contains(new Photograph("C1","F1")),"photos.PhotoLibrary.erasePhoto() failed (removed too much from albums) (also check getPhotos())");
        assertTrue(a.getPhotos().contains(new Photograph("C3","F3")),"photos.PhotoLibrary.erasePhoto() failed (removed too much from albums) (also check getPhotos())");
        assertEquals(2, a.getPhotos().size(),"photos.PhotoLibrary erasePhoto removed too much from albums");
        assertTrue(b.getPhotos().contains(new Photograph("C1","F1")),"photos.PhotoLibrary.erasePhoto() failed (removed too much) (also check getPhotos())");
        assertTrue(b.getPhotos().contains(new Photograph("C3","F3")),"photos.PhotoLibrary.erasePhoto() failed (removed too much) (also check getPhotos())");
        assertEquals(2, b.getPhotos().size(),"photos.PhotoLibrary erasePhoto removed too much");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosEdge() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        assertEquals(new ArrayList<Photograph>(), a.getPhotos(2),"photos.PhotoLibrary getPhotos(rating) did not return an empty list if no photos");

    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotos() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d), "photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(3, a.getPhotos(0).size(),"photos.PhotoLibrary getPhotos(rating) did not return photos of rating 0 or higher");
        assertTrue(a.getPhotos(0).contains(b),"photos.PhotoLibrary getPhotos(rating) did not return correct photos of rating 0 or higher");
        assertTrue(a.getPhotos(0).contains(c),"photos.PhotoLibrary getPhotos(rating) did not return correct photos of rating 0 or higher");
        assertTrue(a.getPhotos(0).contains(d),"photos.PhotoLibrary getPhotos(rating) did not return correct photos of rating 0 or higher");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotos2() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d),"photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(2, a.getPhotos(2).size(),"photos.PhotoLibrary getPhotos(rating) did not return photos of rating 2 or higher");
        assertTrue(a.getPhotos(2).contains(b),"photos.PhotoLibrary getPhotos(rating) did not return correct photos of rating 2 or higher");
        assertTrue(a.getPhotos(2).contains(c),"photos.PhotoLibrary getPhotos(rating) did not return correct photos of rating 2 or higher");
        assertEquals(1, a.getPhotos(5).size(),"photos.PhotoLibrary getPhotos(rating) did not return photos of rating 5 or higher");
        assertTrue(a.getPhotos(5).contains(c),"photos.PhotoLibrary getPhotos(rating) did not return correct photos of rating 5 or higher");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotos3() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 3);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d), "photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(0, a.getPhotos(4).size(), "photos.PhotoLibrary getPhotos(rating) returned photos when it shouldn't have");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosYear() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d), "photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(2, a.getPhotosInYear(2016).size(), "photos.PhotoLibrary getPhotosInYear(year) did not return photos in year");
        assertTrue(a.getPhotosInYear(2016).contains(d), "photos.PhotoLibrary getPhotosInYear(year) did not return correct photos in year");
        assertTrue(a.getPhotosInYear(2016).contains(c), "photos.PhotoLibrary getPhotosInYear(year) did not return correct photos in year");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosYear2() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d), "photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(null, a.getPhotosInYear(-2229), "photos.PhotoLibrary getPhotosInYear() returned photos year is negative");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosYear3() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 3);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d), "photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(0, a.getPhotosInYear(2019).size(), "photos.PhotoLibrary getPhotosInYear() returned photos when it shouldn't have");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosMonth() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-09-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d), "photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(1, a.getPhotosInMonth(9, 2014).size(), "photos.PhotoLibrary getPhotosInMonth(Month) did not return photos in Month");
        assertTrue(a.getPhotosInMonth(9, 2016).contains(d), "photos.PhotoLibrary getPhotosInMonth(Month) did not return correct photos in Month");
        assertFalse(a.getPhotosInMonth(9, 2016).contains(c), "photos.PhotoLibrary getPhotosInMonth(Month) did not return correct photos in Month");
        assertFalse(a.getPhotosInMonth(9, 2016).contains(b), "photos.PhotoLibrary getPhotosInMonth(Month) did not return correct photos in Month");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosMonth2() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d),"photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(null, a.getPhotosInMonth(13, 2019), "photos.PhotoLibrary getPhotosInMonth() returned photos when Month is not formatted correctly");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosMonth3() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 3);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c), "photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d), "photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(0, a.getPhotosInMonth(4, 2016).size(), "photos.PhotoLibrary getPhotosInMonth() returned photos when it shouldn't have");
        assertEquals(0, a.getPhotosInMonth(5, 2019).size(), "photos.PhotoLibrary getPhotosInMonth() returned photos when it shouldn't have");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testaddPhoto() {
        PhotoLibrary b = new PhotoLibrary("Arphaxad", 14);
        Photograph k = new Photograph("some title", "some author");
        assertTrue(b.addPhoto(k), "photos.PhotoLibrary.addPhoto() failed");
        assertTrue(b.getPhotos().contains(k), "photos.PhotoLibrary.addPhoto() failed (photos list doesn't include taken photo)");
    }


    // TO - ADD

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosBetween() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2016-09-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d),"photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(2, a.getPhotosBetween("2016-05-12", "2017-01-01").size(),"photos.PhotoLibrary getPhotosBetween() did not return photos in Between");
        assertTrue(a.getPhotosBetween("2016-05-12", "2017-01-01").contains(d),"photos.PhotoLibrary getPhotosBetween() did not return correct photos between good dates");
        assertTrue(a.getPhotosBetween("2016-05-12", "2017-01-01").contains(b),"photos.PhotoLibrary getPhotosBetween() did not return correct photos between good dates");
        assertFalse(a.getPhotosBetween("2016-05-12", "2017-01-01").contains(c),"photos.PhotoLibrary getPhotosBetween() did not return correct photos between good dates");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosBetween2() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 5);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d),"photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(null, a.getPhotosBetween("2016", "2017"),"photos.PhotoLibrary getPhotosInBetween() returned photos when dates not formatted correctly");
        assertEquals(null, a.getPhotosBetween("2016-12-33", "2017-01-02"),"photos.PhotoLibrary getPhotosInBetween() returned photos when dates not formatted correctly");
        assertEquals(null, a.getPhotosBetween("2016-15-11", "2017-01-02"),"photos.PhotoLibrary getPhotosInBetween() returned photos when dates not formatted correctly");
        assertEquals( null, a.getPhotosBetween("2016-10-11", "2017-21-02"),"photos.PhotoLibrary getPhotosInBetween() returned photos when dates not formatted correctly");
        assertEquals(null, a.getPhotosBetween("2016-10-11", "2017-02-34"),"photos.PhotoLibrary getPhotosInBetween() returned photos when dates not formatted correctly");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testGetPhotosBetween3() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph b = new Photograph("mypic45.jpg", "Grand Canyon", "2014-01-11", 2);
        Photograph c = new Photograph("water.jpg", "Rafting", "2016-05-11", 3);
        Photograph d = new Photograph("water2.jpg", "Rafting 2", "2016-09-30", 1);

        assertTrue(a.addPhoto(b),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(c),"photos.PhotoLibrary addPhoto did not add a photo");
        assertTrue(a.addPhoto(d),"photos.PhotoLibrary addPhoto did not add a photo");
        assertEquals(0, a.getPhotosBetween("2019-01-01", "2019-02-11").size(),"photos.PhotoLibrary getPhotosInBetween() returned photos when it shouldn't have");
        assertEquals(0, a.getPhotosBetween("2016-05-12", "2016-09-29").size(),"photos.PhotoLibrary getPhotosInBetween() returned photos when it shouldn't have");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testCreateAlbum() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);

        assertTrue(a.createAlbum("My photos.Album"),"photos.PhotoLibrary createAlbum() did not create an album");
        boolean contains = false;
        Album c = new Album("My photos.Album");
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                contains = true;
        }
        assertTrue(contains,"photos.PhotoLibrary createAlbum() did not create an album appropriately");
        assertEquals(1, a.getAlbums().size(),"photos.PhotoLibrary createAlbum() created too many albums");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testCreateAlbumDuplicate() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);

        assertTrue(a.createAlbum("My photos.Album"),"photos.PhotoLibrary createAlbum() did not create an album");
        boolean contains = false;
        Album c = new Album("My photos.Album");
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                contains = true;
        }       
        assertTrue(contains,"photos.PhotoLibrary createAlbum() did not create an album appropriately");
        assertEquals(1, a.getAlbums().size(),"photos.PhotoLibrary createAlbum() created too many albums");
        assertFalse(a.createAlbum("My photos.Album"),"photos.PhotoLibrary createAlbum() allowed creation of a second album with the same name");
        contains = false;
        c = new Album("My photos.Album");
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                contains = true;
        }    
        assertTrue(contains,"photos.PhotoLibrary createAlbum() allowed creation of a second album with the same name");
        assertEquals(1, a.getAlbums().size(),"photos.PhotoLibrary createAlbum() allowed creation of a second album with the same name");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemoveAlbumEdge() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);

        assertFalse(a.removeAlbum("My photos.Album"), "photos.PhotoLibrary removeAlbum() failed on an edge case");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemoveAlbum() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);

        assertTrue(a.createAlbum("My photos.Album"),"photos.PhotoLibrary createAlbum() did not create an album");
        assertTrue(a.createAlbum("Vacation Photos"),"photos.PhotoLibrary createAlbum() did not create an album");
        assertTrue(a.createAlbum("Vacation Photos 2"),"photos.PhotoLibrary createAlbum() did not create an album");

        assertTrue(a.removeAlbum("My photos.Album"),"photos.PhotoLibrary removeAlbum() failed to remove an album");
        boolean contains = false;
        Album c = new Album("My photos.Album");
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                contains = true;
        }
        assertFalse(contains,"photos.PhotoLibrary removeAlbum() failed to remove an album");

        assertTrue(a.createAlbum("My photos.Album"),"photos.PhotoLibrary createAlbum() did not create an album");
        contains = false;
        c = new Album("My photos.Album");
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                contains = true;
        }
        assertTrue(contains, "photos.PhotoLibrary createAlbum() did not allow creation of an album (that was previously removed)");
        assertTrue(a.removeAlbum("My photos.Album"), "photos.PhotoLibrary removeAlbum() failed to remove an album");
        contains = false;
        c = new Album("My photos.Album");
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                contains = true;
        }
        assertFalse(contains, "photos.PhotoLibrary removeAlbum() failed to remove an album");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemoveAlbumNotThere() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);

        assertTrue(a.createAlbum("My photos.Album"),"photos.PhotoLibrary createAlbum() did not create an album");
        assertTrue(a.createAlbum("Vacation Photos"),"photos.PhotoLibrary createAlbum() did not create an album");
        assertTrue(a.createAlbum("Vacation Photos 2"), "photos.PhotoLibrary createAlbum() did not create an album");

        assertFalse(a.removeAlbum("Vacation Photos 3"), "photos.PhotoLibrary removeAlbum() allowed removal of an album that was not there");
        boolean contains = false;
        Album c = new Album("Vacation Photos 3");
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                contains = true;
        }
        assertFalse(contains, "photos.PhotoLibrary removeAlbum() accidentally added an album instead of removing");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testAddPhotoToAlbum() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph p = new Photograph("Caption", "My Filename");

        assertTrue(a.createAlbum("My photos.Album"), "photos.PhotoLibrary createAlbum() did not create an album");
        
        assertFalse(a.addPhotoToAlbum(p, "My photos.Album"), "photos.PhotoLibrary addPhotoToAlbum() allowed adding a photo that is not in the photo stream");
        boolean contains = false;
        Album c = new Album("My photos.Album");
        Album real = null;
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                real = b;
        }
        assertNotNull(real, "photos.PhotoLibrary createAlbum() did not create an album");
        assertFalse(real.hasPhoto(p), "photos.PhotoLibrary addPhotoToAlbum() allowed adding a photo that is not in the photo stream");

    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testAddPhotoToAlbum2() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph p = new Photograph("Caption", "My Filename");
        a.addPhoto(p);

        assertTrue(a.createAlbum("My photos.Album"), "photos.PhotoLibrary createAlbum() did not create an album");
        
        
        assertTrue(a.addPhotoToAlbum(p, "My photos.Album"), "photos.PhotoLibrary addPhotoToAlbum() did not add photo to album");
        boolean contains = false;
        Album c = new Album("My photos.Album");
        Album real = null;
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                real = b;
        }
        assertNotNull(real, "photos.PhotoLibrary createAlbum() did not create an album");
        assertTrue(real.hasPhoto(p), "photos.PhotoLibrary addPhotoToAlbum() did not correctly add photo to album");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testAddPhotoToAlbumEdge() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph p = new Photograph("Caption", "My Filename");
        a.addPhoto(p);

        assertFalse(a.addPhotoToAlbum(p, "My photos.Album"), "photos.PhotoLibrary addPhotoToAlbum() allowed adding photo to non-existant album");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemovePhotoFromAlbum() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph p = new Photograph("Caption", "My Filename");
        a.addPhoto(p);
        assertTrue(a.createAlbum("My photos.Album"), "photos.PhotoLibrary createAlbum() did not create an album");

        assertFalse(a.removePhotoFromAlbum(p, "My photos.Album"), "photos.PhotoLibrary removePhotoFromAlbum() allowed removing a photo from an album it was not added to");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemovePhotoFromAlbum2() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph p = new Photograph("Caption", "My Filename");
        a.addPhoto(p);
        assertTrue(a.createAlbum("My photos.Album"), "photos.PhotoLibrary createAlbum() did not create an album");
        a.addPhotoToAlbum(p, "My photos.Album");

        assertTrue(a.removePhotoFromAlbum(p, "My photos.Album"), "photos.PhotoLibrary removePhotoFromAlbum() did not remove a photo from an album correctly");
        boolean contains = false;
        Album c = new Album("My photos.Album");
        Album real = null;
        for (Album b : a.getAlbums()) {
            if (c.equals(b))
                real = b;
        }
        assertNotNull(real, "photos.PhotoLibrary createAlbum() did not create an album");
        assertFalse(real.hasPhoto(p), "photos.PhotoLibrary removePhotoFromAlbum() did not correctly remove the photo from an album");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testRemovePhotoFromAlbumEdge() {
        PhotoLibrary a = new PhotoLibrary("MyLibrary", 1);
        Photograph p = new Photograph("Caption", "My Filename");
        a.addPhoto(p);

        assertFalse(a.removePhotoFromAlbum(p, "My photos.Album"), "photos.PhotoLibrary removePhotoFromAlbum() allowed removing photo from non-existant album");
    }


    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsType() throws Exception {
        try {
            PhotoLibrary.class.getDeclaredMethod("equals", Object.class);
        } catch (NoSuchMethodException e) {
            fail("photos.PhotoLibrary equals method not specified correctly.");
        } catch (Exception e) {
            throw e;
        }
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsSame() {
        PhotoLibrary b = new PhotoLibrary("Le Petit Prince", 42);
        assertTrue(b.equals(b), "photos.PhotoLibrary.equals() failed: Symmetric");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsSimilar() {
        PhotoLibrary b = new PhotoLibrary("Le Petit Prince", 42);
        PhotoLibrary c = new PhotoLibrary("Le Petit Prince", 42);
        assertTrue(b.equals(c), "photos.PhotoLibrary.equals() failed: Same id and name");
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsDifferentNames() {
        PhotoLibrary b = new PhotoLibrary("Le Petit Prince", 42);
        PhotoLibrary c = new PhotoLibrary("The Little Prince", 42);
        assertTrue(b.equals(c), "photos.PhotoLibrary.equals() failed: Same id, different names");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsDifferentReads() {
        PhotoLibrary b = new PhotoLibrary("Le Petit Prince", 42);
        PhotoLibrary c = new PhotoLibrary("Le Petit Prince", 42);
        ((ArrayList<Photograph>)c.getPhotos()).add(new Photograph("Picture of a Sheep", "Me"));
        assertTrue(b.equals(c), "photos.PhotoLibrary.equals() failed: Same id, different names");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsDifferentIds() {
        PhotoLibrary b = new PhotoLibrary("Le Petit Prince", 42);
        PhotoLibrary c = new PhotoLibrary("Le Petit Prince", 2110);
        assertFalse(b.equals(c), "photos.PhotoLibrary.equals() failed: Different id, same names");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsEverythingDifferent() {
        PhotoLibrary b = new PhotoLibrary("Le Petit Prince", 42);
        PhotoLibrary c = new PhotoLibrary("The Little Prince", 2110);
        assertFalse(b.equals(c), "photos.PhotoLibrary.equals() failed: Different id, different names");
    }
    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void equalsDifferentTypes() throws Exception {
        try {
            PhotoLibrary b = new PhotoLibrary("Le Petit Prince", 42);
            assertFalse(b.equals(Integer.valueOf(42)), "photos.PhotoLibrary.equals() failed: Compare photos.PhotoLibrary to Integer");
        } catch (ClassCastException e) {
            fail("photos.PhotoLibrary.equals() failed: Casting a non-photos.PhotoLibrary to a photos.PhotoLibrary");
        } catch (Exception e) {
            throw e;
        }
    }

    @Timeout(value=100, unit= TimeUnit.MILLISECONDS)
    @Test
    public void testToString() {
        PhotoLibrary b = new PhotoLibrary("Grand Canyon Photos", 402);
        b.getPhotos().add(new Photograph("Photo1", "File1.jpg"));
        b.createAlbum("My New photos.Album");
        b.createAlbum("Water Pics");
        String s = b.toString();
        assertTrue(s.contains("Grand Canyon Photos"), "photos.PhotoLibrary.toString must have name");
        assertTrue(s.contains("402"), "photos.PhotoLibrary.toString must have id");
        assertTrue(s.contains("Photo1"), "photos.PhotoLibrary.toString must have list of Photographs (also check getPhotos())");
        assertTrue(s.contains("File1.jpg"), "photos.PhotoLibrary.toString must have list of Photographs (also check getPhotos())");
        assertTrue(s.contains("My New photos.Album"), "photos.PhotoLibrary.toString must have album names");
        assertTrue(s.contains("Water Pics"), "photos.PhotoLibrary.toString must have album names");
    }

}

