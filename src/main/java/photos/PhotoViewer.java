package photos;

import photos.Photograph;
import photos.PhotographContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * PhotoViewer Graphical User Interface 
 * 
 * This class contains the graphical user interface for the Photograph Organization
 * software. 
 * 
 * You will need to implement certain portions of this class, marked with comments starting with "TODO" to connect 
 * it with your existing code. 
 * 
 * This class provides an example layout for the GUI. You are encouraged to be creative in your design. 
 * More information about Swing is online at: 
 * https://docs.oracle.com/javase/tutorial/uiswing/components/componentlist.html.
 * 
 * 
 */
public class PhotoViewer extends JFrame  {

    /**
     * Serialization string required by extending JFrame
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Image Library that is displayed in this GUI
     */
    private PhotographContainer imageLibrary;
    private int albumPosition;

    //TODO remove these buttons
    private JButton nextButton;
    private JButton previousButton;
    private JButton dateSortButton;
    private JButton ratingSortButton;

    private ButtonGroup ratingButtonGroup;

    private JRadioButton rb1;
    private JRadioButton rb2;
    private JRadioButton rb3;
    private JRadioButton rb4;
    private JRadioButton rb5;

    private BorderLayout panelLayout;
    private BoxLayout iconLayout;

    private JLabel imageDisplayLabel;
    
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JPanel thumbnailPanel;
    private JPanel bottomPanel;
    private JPanel panelPane;


    //TODO Add a dateSortMenuItem and a ratingSortMenuItem

    //TODO Add a JMenuBar createMenu() method

    /**
     * Getter for image library
     * 
     * @return The PhotographContainer associated with this graphical user interface
     */
    public PhotographContainer getImageLibrary() {
        return imageLibrary;
    }

    /**
     * Set the PhotographContainer associated with this graphical user interface
     * 
     * @param imageLibrary The PhotographContainer to display
     */
    public void setImageLibrary(PhotographContainer imageLibrary) {
        this.imageLibrary = imageLibrary;
    }



    /**
     * Button listener for the Next and Previous Buttons
     */
    class NavigationListener implements ActionListener {
        //TODO change this to be a KeyListener instead of ActionListener
        //some of the code will be quite similar

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == nextButton) {
                albumPosition = (albumPosition + 1) % imageLibrary.numPhotographs();
            }

            else if(e.getSource() == previousButton) {
                albumPosition = (albumPosition - 1);
                if (albumPosition < 0) {
                    albumPosition += imageLibrary.numPhotographs();
                }
            }
            displayPhoto(albumPosition);
        }
    }
    
    /**
     * Button Listener for the Rating radio buttons
     */
    class RatingChangeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int rating = 1;

            if(e.getSource() == rb1) {
                rating = 1;
            }
            else if (e.getSource() == rb2) {
                rating = 2;
            }
            else if (e.getSource() == rb3) {
                rating = 3;
            }
            else if (e.getSource() == rb4) {
                rating = 4;
            }
            else if (e.getSource() == rb5){
                rating = 5;
            }
            imageLibrary.getPhotos().get(albumPosition).setRating(rating);
        }
    }
    
    /**
     * Button Listener for the Sort buttons
     */
    class SortNavigationListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            //TODO: Ensure the dataSortMenuItem and ratingSortMenuItem are also handled here

            if(e.getSource() == dateSortButton ) {
                getImageLibrary().getPhotos().sort(Photograph::compareTo);
            }
            else if(e.getSource() == ratingSortButton) {
                getImageLibrary().getPhotos().sort(new CompareByRating());
            }

            //redraw the thumbnails and main photograph
            drawThumbnails();
            albumPosition=0;
            displayPhoto(albumPosition);
        }
    }

    /**
     * Main method.  This method initializes a PhotoViewer, loads images into a PhotographContainer, then
     * initializes the Graphical User Interface.
     * 
     * @param args  Optional command-line arguments
     */
    public static void main(String[] args) {

        // Instantiate the PhotoViewer Class
        PhotoViewer myViewer = new PhotoViewer();

        // The relative image directory (for Windows, change to "images\\")
        //String imageDirectory = "images/";

        myViewer.setImageLibrary( new PhotoLibrary("Famous CS People", 1));

        Path p = Paths.get("src","main", "resources", "Ada_Lovelace.jpg");
        String s = p.toAbsolutePath().toString();
        myViewer.getImageLibrary().addPhoto(new Photograph(s, "Ada Lovelace, often considered the first programmer", "1924-01-01", 5));

        p = Paths.get("src","main", "resources", "Alan_Turing_Aged_16.jpg");
        s = p.toAbsolutePath().toString();
        myViewer.getImageLibrary().addPhoto(new Photograph(s, "Alan Turing, widely considered to the be the father of Artificial Intelligence and Theoretical Computer Science", "1928-01-01", 4));


        p = Paths.get("src","main", "resources", "Dorothy_Vaughan.jpg");
        s = p.toAbsolutePath().toString();
        myViewer.getImageLibrary().addPhoto(new Photograph(s, "Dorthy Vaughn, leader in transition from human computers to digital computers.", "unknown", 5));

        p = Paths.get("src","main", "resources", "ae.jpg");
        s = p.toAbsolutePath().toString();
        myViewer.getImageLibrary().addPhoto(new Photograph(s, "Annie Easley at NASA", "1955-01-01", 5));

        p = Paths.get("src","main", "resources", "Grace_Hopper_and_UNIVAC.jpg");
        s = p.toAbsolutePath().toString();
        myViewer.getImageLibrary().addPhoto(new Photograph(s, "Grace Hopper, early creator of high-level programming languages", "1960-01-01", 4));


        // Invoke and start the Graphical User Interface
        javax.swing.SwingUtilities.invokeLater(() -> myViewer.initialize());
    }

    /**
     * Initialize all the GUI components.  This method will be called by
     * SwingUtilities when the application is started.
     */
    private void initialize() {

        // Tell Java to exit the program when the window is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Tell Java to title the window based on the imageLibrary's name
        this.setTitle(this.imageLibrary.getName());

        // Display panels for the window.  They default to FLowLayout.
        controlPanel = new JPanel();
        displayPanel = new JPanel();
        thumbnailPanel = new JPanel();
        bottomPanel = new JPanel();

        // We will use border layout on the main panel, since it is much easier for organizing panels.
        panelLayout = new BorderLayout();
        panelPane = new JPanel(panelLayout);

        // Create a label to display the full image.
        imageDisplayLabel = new JLabel();

        //TODO Add a KeyListener to the imageDisplayLabel (passing a NavigationListener object)
        //then call imageDisplayLabel.setFocusable(true)


        imageDisplayLabel.requestFocusInWindow();
        // Initialize listeners for all components
        NavigationListener controlNavigationListener = new NavigationListener();
        RatingChangeListener ratingButtonListener = new RatingChangeListener();
        SortNavigationListener sortListener = new SortNavigationListener();

        //TODO: Remove these buttons

        // Add buttons for sorting thumbnails.
        dateSortButton = new JButton("Sort by Date");
        dateSortButton.setActionCommand("dateSort");
        dateSortButton.addActionListener(sortListener);

        ratingSortButton = new JButton("Sort by Rating");
        ratingSortButton.setActionCommand("ratingSort");
        ratingSortButton.addActionListener(sortListener);
 
        controlPanel.add(dateSortButton);
        controlPanel.add(ratingSortButton);

        displayPanel.add(imageDisplayLabel);

        // Use a BoxLayout to display the thumbnails.

        //TODO What happens if that is BoxLayout.X_AXIS?
        iconLayout = new BoxLayout(thumbnailPanel, BoxLayout.Y_AXIS);
        thumbnailPanel.setLayout(iconLayout);
        drawThumbnails();


        //TODO Remove these buttons

        // Add buttons for next and previous navigation
        previousButton = new JButton("Previous");
        previousButton.setActionCommand("previous");
        previousButton.addActionListener(controlNavigationListener);
        bottomPanel.add(previousButton);
        
        nextButton = new JButton("Next");
        nextButton.setActionCommand("next");
        nextButton.addActionListener(controlNavigationListener);
        bottomPanel.add(nextButton);


        //TODO Leave these buttons for now

        // Use a set of radio buttons to set and display the ratings of the current image.
        // Many other interesting controls exist (like the slider) but radio buttons are familiar.
        ratingButtonGroup = new ButtonGroup();
        rb1 = new JRadioButton("1");
        rb2 = new JRadioButton("2");
        rb3 = new JRadioButton("3");
        rb4 = new JRadioButton("4");
        rb5 = new JRadioButton("5");

        // Add Rating options to the bottom panel
        bottomPanel.add(new JLabel("   Rating"));

        rb1.setActionCommand("rate1");
        rb1.addActionListener(ratingButtonListener);
        ratingButtonGroup.add(rb1);
        bottomPanel.add(rb1);

        rb2.setActionCommand("rate2");
        rb2.addActionListener(ratingButtonListener);
        ratingButtonGroup.add(rb2);
        bottomPanel.add(rb2);

        rb3.setActionCommand("rate3");
        rb3.addActionListener(ratingButtonListener);
        ratingButtonGroup.add(rb3);
        bottomPanel.add(rb3);

        rb4.setActionCommand("rate4");
        rb4.addActionListener(ratingButtonListener);
        ratingButtonGroup.add(rb4);
        bottomPanel.add(rb4);

        rb5.setActionCommand("rate5");
        rb5.addActionListener(ratingButtonListener);
        ratingButtonGroup.add(rb5);
        bottomPanel.add(rb5);

        // Call the method that displays the main photograph
        displayPhoto(albumPosition);

        // Add all the panels to the main display based on BorderLayout
        panelPane.add(controlPanel, BorderLayout.PAGE_START);
        panelPane.add(displayPanel, BorderLayout.CENTER);
        panelPane.add(thumbnailPanel, BorderLayout.WEST);
        panelPane.add(bottomPanel, BorderLayout.SOUTH);
        this.getContentPane().add(panelPane);

        //TODO Create the menu using setJMenuBar (calling your new method you created)

        // Show the main application window
        this.pack();
        this.setVisible(true);
    }

    /**
     * Display thumbnails in the thumbnailPanel.  
     */
    private void drawThumbnails() {
        // Clear the panel of all thumbnails
        thumbnailPanel.removeAll();
        // For each photograph in the library, add it to the panel in the appropriate order
        for (int i = 0; i < imageLibrary.numPhotographs(); i++) {
            final int imagePosition = i;
            Photograph p = imageLibrary.getPhotos().get(i);

            // Create a label for image information, including caption, date taken, and rating

            //TODO use setToolTipText to hide the text from the label until mouse-over
            JLabel thumbnailLabel = new JLabel("<html><body width='300'>"
                    + p.getCaption() + "<br>(" + p.getDateTaken() + ")<br>" 
                    + "rated: " + p.getRating() + "</body></html>");

            thumbnailLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    displayPhoto(imagePosition);
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            // TODO Scale the thumbnail
            try {
                BufferedImage b = p.getImageData();

                thumbnailLabel.setIcon(new ImageIcon(b.getScaledInstance(102, 77, Image.SCALE_FAST)));
            } catch (Exception e) {
                System.err.println("Could not scale the image.");
            }
            // Add this thumbnail and label to the thumbnailPanel
            thumbnailPanel.add(thumbnailLabel);
        }
        thumbnailPanel.revalidate();
    }

    /**
     * Displays a photo, given by the index into the imageLibrary's photos, into the main display.
     * 
     * @param position The position into the imageLibrary's photos list
     */
    private void displayPhoto(int position) {
        albumPosition = position;

        Photograph photograph = imageLibrary.getPhotos().get(position);
        BufferedImage img = photograph.getImageData();
        int rating = photograph.getRating();

        imageDisplayLabel.setIcon(new ImageIcon(img));
        ratingButtonGroup.clearSelection();

        //update the rating toggle buttons based on the photo's rating
        switch(rating) {
            case 1: rb1.setSelected(true);
                    break;
            case 2: rb2.setSelected(true);
                    break;
            case 3: rb3.setSelected(true);
                    break;
            case 4: rb4.setSelected(true);
                    break;
            case 5: rb5.setSelected(true);
                    break;
            default: assert(false);
        }
        // Repaint the display so that the new image gets displayed
        displayPanel.repaint();
    }
}
