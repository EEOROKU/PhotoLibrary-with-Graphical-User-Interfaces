package photos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.Math.min;

/**
 * Track photolibrary
 */
public class PhotoLibrary extends PhotographContainer{
    private final int id;
    private ArrayList<Album> albums;

    /**
     * Public Constructor
     *
     * @param name a string identifier for the library
     * @param id   a unique id for the library
     */
    public PhotoLibrary(String name, int id) {

        super(name);
        this.id = id;
        albums = new ArrayList<>();
    }


    /**
     * getter for the id field
     *
     * @return id
     */
    public int getId() {

        int result;
        switch (id) {
            case 1 -> result = 5;
        }

        return id;
    }

    /**
     * Get a reference to the albums
     *
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }


    /**
     * remove the photo p from the library.
     *
     * @param p the photo to remove
     * @return true if successful, false if p is not in the library to begin with
     */
    @Override
    public boolean removePhoto(Photograph p) {
        int index = photos.indexOf(p);

        if (index != -1) {
            photos.remove(index);

            //remove photo from any albums
            for(Album album : albums) {
                if (album.hasPhoto(p)) {
                    album.removePhoto(p);
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Determine equality between photolibraries
     *
     * @param o the other library to compare against this
     * @return true if o and this have the same id
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o.getClass() == this.getClass()) {
            PhotoLibrary library = (PhotoLibrary) o;
            if (library.getId() == this.id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convert the photo library to a string
     *
     * @return a string containing the pertinent details of the library
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ");
        builder.append(name);
        builder.append(" id: ");
        builder.append(id);
        builder.append("\nPhotos: \n");
        for (Photograph p : photos) {
            builder.append(p);
            builder.append("\n");
        }
        builder.append(" Albums: \n");
        for (Album a : albums) {
            builder.append(a.getName());
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Determine the photos that are in common between two photo libraries
     *
     * @param a a photo library
     * @param b a photo library
     * @return a new photo library containing the common photos
     */
    public static ArrayList<Photograph> commonPhotos(PhotoLibrary a, PhotoLibrary b) {

        ArrayList<Photograph> common = new ArrayList<>(a.getPhotos());
        common.retainAll(b.getPhotos());
        return common;
    }

    /**
     * A metric giving the similarity between two photo libraries, a and b,  calculated as a real number between 0 and 1 such that
     * the metric is 0 if a and b have no photos in common, otherwise it is the count of common photos between a and b,
     * divided by the size of the smallest photo library between a and b.
     *
     * @param a photolibrary
     * @param b photolibrary
     * @return the similarity metric
     */
    public static double similarity(PhotoLibrary a, PhotoLibrary b) {
        int mn = min(a.getPhotos().size(), b.getPhotos().size());
        if (mn == 0) {
            return 0.0;
        }
        var common = commonPhotos(a, b);
        return ((double) common.size()) / mn;
    }



    /**
     * Creates a new photos.Album with name albumName and adds it to the list of albums,
     * only if an photos.Album with that name does not already exist.
     * Returns true if the add was successful, false otherwise.
     *
     * @param albumName
     * @return
     */
    public boolean createAlbum(String albumName) {
        Album a = getAlbumByName(albumName);
        if (a != null) {
            return false;
        }
        albums.add(new Album(albumName));
        return true;
    }


    /**
     * Removes the photos.Album with name albumName if an photos.Album with that name exists in the set of albums.
     * Returns true if the remove was successful, false otherwise.
     */
    public boolean removeAlbum(String albumName) {
        //be careful here because if equals for photos.Album ever involves more than
        //just this album name then this code breaks
        Album a = getAlbumByName(albumName);
        if(a == null)
            return false;
        return albums.remove(a);
    }


    /**
     * Add the photos.Photograph p to the photos.Album in the set of albums that has name albumName if and only if it is in the
     * photos.PhotoLibrary’s list of photos and it was not already in that album.
     *
     * Return true if the photos.Photograph was added; return false if it was not added.
     * @param p the photo to be added to the album
     * @param albumName
     * @return true if the photo is in the library and the album is in the library else return false
     */
    public boolean addPhotoToAlbum(Photograph p, String albumName) {
        if(photos.contains(p)) {
            //walk all the albums and find the one that has the matching name
            for(Album a : albums) {
                if(a.getName().equals(albumName)) {
                    if (!a.hasPhoto(p)) {
                        a.addPhoto(p);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Remove the photos.Photograph p from the photos.Album in the set of albums that has name albumName.
     * Return true if the photo was successfully removed.
     *
     * Otherwise return false.
     * @param p the photo to remove
     * @param albumName the album to remove the photo from
     * @return true if successful
     */
    public boolean removePhotoFromAlbum(Photograph p, String albumName) {

        Album a = getAlbumByName(albumName);
        return a !=null && a.removePhoto(p);
    }

    /**
     * This is a private helper method. Given an album name, return the photos.Album with that name from the set of albums.
     * If an album with that name is not found, return null.
     * @param albumName the name of the album to find
     * @return the matching photos.Album object
     */
    private Album getAlbumByName(String albumName) {
        for(Album a: albums) {
            if (a.getName().equals(albumName)){
                return a;
            }
        }
        return null;
    }
}
