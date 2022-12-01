package photos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Photograph implements Comparable<Photograph>{

    private String caption;
    private String filename;

    //this is formatted as: YYYY-MM-DD
    private String dateTaken;

    private int rating;


    //TODO add the BufferedImage
    private BufferedImage imageData;

    /**
     * Public constructor
     * @param theFileName local filename of the photograph
     * @param theCaption caption associated with the photo
     */
    public Photograph(String theFileName, String theCaption) {
        caption = theCaption;
        filename = theFileName;
        dateTaken = "";

        //TODO call loadImageData passing in the filename
        loadImageData(theFileName);
    }

    public Photograph(String filename, String caption, String dateTaken, int rating) {
        this.caption = caption;
        this.filename = filename;
        this.dateTaken = dateTaken;
        this.rating = rating;

        //TODO call loadImageData passing in the filename
        loadImageData(filename);
    }

    /**
     * getter for the caption
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * getter for the filename
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * getter for rating
     * @return rating
     */
    public int getRating(){
        return rating;
    }

    /**
     * getter for date taken
     * @return dateTaken
     */
    public String getDateTaken() {
        return dateTaken;
    }

    public void setRating(int rating) {
        if (rating < 0 || rating > 5){
            return;
        }
        this.rating = rating;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    //TODO add a getImageData method to return the BufferedImage
    public BufferedImage getImageData() {
        return imageData;
    }

    //TODO add a setImageData method that takes in a BufferedImage and
    //assigns it to the instance field of the class
    public void setImageData(BufferedImage img) {
        imageData = img;
    }

    //TODO add a method called loadImageData as below
    //ensure to use a try catch block so that you can
    //return false if there is a file IOException
    /**
     * Given a filename as String load the Image data from that file
     * @param inFilename the filename to save and load image data from
     * @return true if successful and false otherwise
     */
    public boolean loadImageData(String inFilename)  {
        this.filename = inFilename;

        try {
            imageData = ImageIO.read(new File(inFilename));
        }catch(IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Determine equality as having the same filename and caption
     * @param o another object to check for equivalence against this
     * @return true if o contains equivalent data as this
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Photograph photo = (Photograph) o;
        return (photo.filename.equals(this.filename)
                && photo.caption.equals(this.caption)
                && photo.dateTaken.equals(this.dateTaken));
    }

    @Override
    public int hashCode() {
        String uniqueStr = filename + "---" + caption + "---" + dateTaken;
        return uniqueStr.hashCode();
    }

    /**
     * Convert the photos.Photograph to a String by including the filename and caption
     * @return a String containing the filename and caption
     */
    public String toString() {
        return "filename: " + filename + " caption: " + caption;
    }


    /**
     Compares the dateTaken of the current Photograph object with the parameter p.
     If the current object’s dateTaken is before p’s, return a negative number.
     If p’s is earlier, return a positive number.
     If they are equal, return the comparison of this object’s caption with p’s caption.

     @param p the photograph to compare to this
     @return negative if this comes before p, positive if this comes after p and 0 if they are equal
     **/
    @Override
    public int compareTo(Photograph p) {
        int value = this.dateTaken.compareTo(p.dateTaken);
        if(value == 0) {
            value = this.caption.compareTo(p.caption);
        }
        return value;
    }
}
