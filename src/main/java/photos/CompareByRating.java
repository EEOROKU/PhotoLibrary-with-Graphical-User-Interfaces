package photos;

import java.util.Comparator;
import java.util.Locale;

/**
 * Compare photos to order them by rating in descending order
 * and then by caption alphabetically
 */
public class CompareByRating implements Comparator<Photograph> {
    @Override
    public int compare(Photograph o1, Photograph o2) {
        int value = o2.getRating() - o1.getRating();
        if (value == 0) {
            value = o1.getCaption().toLowerCase().compareTo(o2.getCaption().toLowerCase());
        }
        return value;
    }
}
