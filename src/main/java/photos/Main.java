package photos;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Demo of Comparator and Comparable to
 * show custom sorting of Photos
 */
public class Main {
    public static void main(String[] args) {

        ArrayList<Photograph> myList = new ArrayList<>();

        Photograph p1 = new Photograph("zfile1.bmp", "the first", "2022-11-09", 5);
        Photograph p2 = new Photograph("yfile2.bmp", "second", "2022-11-10", 2);
        Photograph p3 = new Photograph("xfile3.bmp", "third", "2022-11-11", 3);

        myList.add(p1);
        myList.add(p2);
        myList.add(p3);

        //natural ordering
        Collections.sort(myList);

        System.out.println("first sort (date then caption):");
        for(Photograph p: myList) {
            System.out.println(p);
        }

        //custom ordering
        System.out.println("\n2nd sort (rating):");
        Collections.sort(myList, new CompareByRating());
        for(Photograph p : myList) {
            System.out.println(p);
        }


        System.out.println("\n3rd sort (filename using a lambdad):");
        //custom sort by lambda expression
        Collections.sort(myList, (x,y)->x.getFilename().compareTo(y.getFilename()));
        for(Photograph p : myList) {
            System.out.println(p);
        }
    }


}
