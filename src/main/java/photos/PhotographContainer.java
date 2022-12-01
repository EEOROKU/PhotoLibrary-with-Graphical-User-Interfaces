package photos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.Math.min;

public abstract class PhotographContainer {

    protected String name;
    protected ArrayList<Photograph> photos;


    public PhotographContainer(String name) {
        photos = new ArrayList<>();
        this.name = name;
    }


    /**
     * Getter for the name field
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     *
     * @param name the new name for the photo container
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the photos
     *
     * @return the arraylist of photos
     */
    public ArrayList<Photograph> getPhotos() {
        return photos;
    }

    /**
     * Add a photo to the container of photos
     * if the photo p is already in the container then nothing is added and false is returned.
     * Otherwise the photo is added to the container and true is returned
     *
     * @param p the photo to add
     * @return false if the container already contains that photo and true otherwise
     */
    public boolean addPhoto(Photograph p) {
        if(p == null) {
            return false;
        }
        if (photos.contains(p)) {
            return false;
        }
        photos.add(p);
        return true;
    }

    /**
     * Determine if p is in the container
     *
     * @param p the photo to determine if it is already in the container or not
     * @return true if p is in the container and false o.w.
     */
    public boolean hasPhoto(Photograph p) {
        return photos.contains(p);
    }

    /**
     * remove the photo p from the container.
     * @param p the photo to remove
     * @return true if successful, false if p is not in the container to begin with
     */
    public boolean removePhoto(Photograph p) {
        int index = photos.indexOf(p);

        if (index != -1) {
            photos.remove(index);
            return true;
        }
        return false;
    }

    /**
     * How many photos in the container
     * @return the number of photos
     */
    public int numPhotographs() {
        return photos.size();
    }

    /**
     * Determine equality between photolibraries
     *
     * @param o the other container to compare against this
     * @return true if o and this have the same id
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o.getClass() == this.getClass()) {
            PhotographContainer container = (PhotographContainer) o;
            if (container.getName().equals(this.name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convert the photocontainer to a string
     * @return a string containing the pertinent details of the photocontainer
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ");
        builder.append(name);
        builder.append("\nPhotos: \n");
        for (Photograph p : photos) {
            builder.append(p);
            builder.append("\n");
        }
        return builder.toString();
    }

    private void photoMatches(Predicate<Photograph> predicate, Consumer<Photograph> consumer) {
        for (Photograph p : photos) {
            if (predicate.test(p)) {
                consumer.accept(p);
            }
        }
    }


    /**
     * Return an ArrayList of photos from the photos feed that have a rating
     * greater than or equal to the given parameter. If the rating is incorrectly formatted,
     * return null. If there are no photos of that rating or higher,
     * return an empty ArrayList.
     *
     * @param rating the rating for which returned photos should be equal or greater
     * @return ArrayList of photos matching or bettering the rating, or null if rating is incorrect
     */
    public ArrayList<Photograph> getPhotos(int rating) {
        if (rating < 0 || rating > 5) {
            return null;
        }
        ArrayList<Photograph> matches = new ArrayList<>();
        photoMatches(pic -> pic.getRating() >= rating, matches::add);
        return matches;
    }

    /**
     * Return an ArrayList of photos from the photos feed that were taken in the year provided.
     * For example, getPhotosInYear(2018) would return a list of photos that were taken in 2018.
     * If the year is incorrectly formatted (less than 0 or greater than 3000 (Y3K anyone?), return null.
     * If there are no photos taken that year, return an empty ArrayList.
     *
     * @param year the year to match photos from
     * @return an arraylist of photos from that year or null if the year is incorrectly formatted
     */
    public ArrayList<Photograph> getPhotosInYear(int year) {
        if (year <= 0 || year > 3000) {
            return null;
        }
        ArrayList<Photograph> matches = new ArrayList<>();

        //note this line could also be written as per below
        photoMatches(pic -> pic.getDateTaken().startsWith(String.valueOf(year)), matches::add);

        //note we could also write the above as follows:
        /*
        for(photos.Photograph p : photos) {
            if(p.getDateTaken().startsWith(String.valueOf(year))) {
                matches.add(p);
            }
        }*/

        return matches;

    }


    /**
     * Return an ArrayList of photos from the photos feed that were taken in the month and year provided.
     * For example, getPhotosInMonth(7, 2018) would return a list of photos that were taken in July 2018.
     * If the month or year are incorrectly formatted, return null.
     * If there are no photos taken that month, return an empty ArrayList.
     *
     * @param month an integer between 1 and 12 inclusive
     * @param year  an integer between 0 and 3000 inclusive
     * @return all photos having a date taken matching the given month and year, or null if the month and year are invalid
     */
    public ArrayList<Photograph> getPhotosInMonth(int month, int year) {
        if (month < 1 || month > 12 || year < 0 || year > 3000) {
            return null;
        }
        ArrayList<Photograph> matches = new ArrayList<>();
        String strMonth  = month < 10? "0" + String.valueOf(month) : String.valueOf(month);
        String datePattern = String.valueOf(year) + "-" + strMonth;
        photoMatches(pic -> pic.getDateTaken().startsWith(datePattern), matches::add);
        return matches;
    }

    private boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Return an ArrayList of photos from the photos feed that were taken between beginDate
     * and endDate (inclusive).
     * <p>
     * For example, getPhotosBetween("2019-01-23", "2019-02-13") would return a list of photos
     * that were taken in between January 23 and February 13 of 2019.
     * If the begin and end dates are incorrectly formatted, or beginDate is after endDate, return null.
     * If there are no photos taken during the period, return an empty ArrayList.
     *
     * @param beginDate the starting date
     * @param endDate   the ending date
     * @return
     */
    public ArrayList<Photograph> getPhotosBetween(String beginDate, String endDate) {

        //first handle the incorrectly formatted dates
        if (!isValidDate(beginDate) || !isValidDate(endDate) || beginDate.compareTo(endDate) > 0) {
            return null;
        }

        ArrayList<Photograph> matches = new ArrayList<>();
        photoMatches(pic -> pic.getDateTaken().compareTo(beginDate) >= 0 &&
                        pic.getDateTaken().compareTo(endDate) <= 0,
                matches::add);

        return matches;
    }

    /**
     * The hashcode is determined as the name's hashcode
     * Note: two albums are the equal if their name is equal
     * @return the hashcode of name
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
