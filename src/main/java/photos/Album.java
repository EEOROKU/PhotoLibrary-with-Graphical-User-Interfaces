package photos;

import java.util.ArrayList;

/**
 * Albums have photos associated with them
 */
public class Album extends PhotographContainer{

    //name of the album
    //private String name;

    //all of the photos associated with the album
    //private ArrayList<Photograph> photos;

    /**
     * Constructor
     * @param name name of the album
     */
    public Album (String name) {
        super(name);
        //this.name = name;
        photos = new ArrayList<>();
    }

    /**
     * Getter for Name
     * @return the name
     */
    /*public String getName() {
        return name;
    }*/

    /**
     * Getter for the photos
     * @return the arraylist of photos in the album
     */
    /*public ArrayList<Photograph> getPhotos() {
        return photos;
    }*/

    /**
     * Set the name of the album
     * @param name the name to use for the album
     */
    /*public void setName(String name) {
        this.name = name;
    }*/

    /**
     * Return true if the current object has p in its list of photos.
     * Otherwise return false.
     * @param p the photo to check if it is in the album
     * @return Return true if the current object has p in its list of photos.
     *         Otherwise return false.
     */
    /*public boolean hasPhoto(Photograph p) {
        return photos.contains(p);
    }*/

    /**
     * Add the photos.Photograph p to the list of the current object’s photos
     * if and only if it was not already in that list.
     *
     * @param p the photo to add
     * @return Return true if the photos.Photograph was added;
     *         return false if it was not added.
     *         Return false if p is null;
     */
    /*public boolean addPhoto(Photograph p) {
        if(p == null) {
            return false;
        }
        if(hasPhoto(p)) {
            return false;
        }
        photos.add(p);
        return true;
    }*/


    /**
     * Remove photos.Photograph p from the album, if it exists in the list of photos.
     * If successful, return true; else return false.
     * @param p the photo to remove
     * @return If successful, return true; else return false.
     */
    /*public boolean removePhoto(Photograph p) {

        if (hasPhoto(p)) {
            photos.remove(p);
            return true;
        }
        return false;
    }*/

    /**
     * Return the number of Photographs in the current album (in photos).
     * @return the number of photographs
     */
    /*public int numPhotographs() {
        return photos.size();
    }*/

    /**
     * return true if the current photos.Album object’s name value is equal
     * to the name value of the photos.Album object passed to equals().
     * Otherwise, return false.
     * @param o the other photos.Album object
     * @return true o equals this photos.Album's name
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        else if( o == null  || o.getClass() != this.getClass()) {
            return false;
        }

        //TODO 1: Finish this method

        //step a: cast to the correct type

        //step b: compare the appropriate instance fields for equality.
        // in this case two albums are equal if they have the same name

        Album a = (Album) o;
        return this.name.equals(a.name);
    }

    /**
     * a String that has the name of the album on the first line,
     * followed by a list of the contained photos’ filenames.
     * @return as explained above
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        for(Photograph p : photos) {
            sb.append(p.getFilename()).append("\n");
        }
        return sb.toString();
    }

    /**
     * The hashcode is determined as the name's hashcode
     * Note: two albums are the equal if their name is equal
     * @return the hashcode of name
     */
    /*@Override
    public int hashCode() {
        return name.hashCode();
    }*/

}
