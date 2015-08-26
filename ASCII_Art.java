/*
 * John August 
 * Homework #1
 * ECEC 301
 * Drexel University 
 */
package javareview;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.*;
import org.json.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;


public class ASCII_Art {

    public static void main(String[] args) {

        // Continue to ask the user what to draw untill they are finished
        boolean isUserFinishedDrawing = false;
        boolean isThisTheFirstLoop = true;
        while (!isUserFinishedDrawing) {

            // Ask the user what they want to draw
            System.out.println("\nTell me what you want to see and I will create an ASCII art version of it! What do you want me to draw?");
            BufferedImage origionalImage;
            if (isThisTheFirstLoop) {
                System.out.println("\n\nIf you can't think of anything, let me make a few sugesstions...");
                System.out.println("1.)  The Drexel logo");
                System.out.println("2.)  The Java logo");
                System.out.println("3.)  A camera");
                System.out.println("4.)  A ghost");
                System.out.println("5.)  Headphones");
                System.out.println("6.)  A bicycle");
                System.out.println("7.)  A desktop computer");
                System.out.println("8.)  Packman");
                System.out.println("9.)  The Playstation logo");
                System.out.println("10.) The American Flag");
                System.out.println("\nType in anything you want me to draw or type any number from 1 to 10 to load a suggestion, then press enter:");
                isThisTheFirstLoop = false; // Set to false so sugestions are turne off

                Scanner userInput = new Scanner(System.in);
                String searchRequestItem = userInput.nextLine();
                
                // Detect if the idiot user enters nothing!
                while(searchRequestItem.equals(""))
                {                    
                    if(searchRequestItem.equals(""))
                    {
                         System.out.println("Hey, you didnt type anything!");
                         System.out.println("Type in anything you want me to draw or type any number from 1 to 10 to load a suggestion, then press enter:");
                         searchRequestItem = userInput.nextLine();
                    }
                }
                
                // Set search item to user selection
                if (searchRequestItem.equals(String.valueOf(1))) {
                    searchRequestItem = "drexel";
                } else if (searchRequestItem.equals(String.valueOf(2))) {
                    searchRequestItem = "java logo";
                } else if (searchRequestItem.equals(String.valueOf(3))) {
                    searchRequestItem = "a camera";
                } else if (searchRequestItem.equals(String.valueOf(4))) {
                    searchRequestItem = "a ghost";
                } else if (searchRequestItem.equals(String.valueOf(5))) {
                    searchRequestItem = "headphones";
                } else if (searchRequestItem.equals(String.valueOf(6))) {
                    searchRequestItem = "a bicycle";
                } else if (searchRequestItem.equals(String.valueOf(7))) {
                    searchRequestItem = "a desktop computer";
                } else if (searchRequestItem.equals(String.valueOf(8))) {
                    searchRequestItem = "packman";
                } else if (searchRequestItem.equals(String.valueOf(9))) {
                    searchRequestItem = "playstatoin";
                } else if (searchRequestItem.equals(String.valueOf(10))) {
                    searchRequestItem = "the american flag";
                }

                System.out.println("\nOkay! Give me a few seconds to draw " + searchRequestItem + " for you! ...");
                // Start a google image search for the request 
                origionalImage = searchGoogleImages(searchRequestItem);
            } else {
                System.out.println("Type anything you want me to draw below, then press enter:");
                isThisTheFirstLoop = false; // Set to false so sugestions are turne off

                Scanner userInput = new Scanner(System.in);
                String searchRequestItem = userInput.nextLine();
                
                // Detect if the idiot user enters nothing!
                while(searchRequestItem.equals(""))
                {                    
                    if(searchRequestItem.equals(""))
                    {
                         System.out.println("Hey, you didnt type anything!");
                         System.out.println("Type anything you want me to draw below, then press enter:");
                         searchRequestItem = userInput.nextLine();
                    }
                }
                
                System.out.println("\nOkay! Give me a few seconds to draw " + searchRequestItem + " for you! ...");
                // Start a google image search for the request 
                origionalImage = searchGoogleImages(searchRequestItem);
            }

            if (origionalImage == null && searchWasSuccessful == true) {
                System.out.println("\nOpps!  ....  That is too complicated for me to draw! Try something simpler next time!");
            } else if (origionalImage == null && searchWasSuccessful == false) {
                System.out.println("\nOpps!  ....  I can't find my drawing pen! Please check your internet connection before trying again.");
                String answer;
                System.out.println("Did you fix your internet connectoin yet? \nType 'y' for yes or 'q' to quit no.");
                Scanner input = new Scanner(System.in);

                while (true) {
                    answer = input.nextLine().trim().toLowerCase();

                    if (answer.equals("y")) {
                        isUserFinishedDrawing = false;
                        break;
                    } else if (answer.equals("q")) {
                        isUserFinishedDrawing = true;
                        break;
                    } else {
                        System.out.println("Sorry, I didn't catch that. Please answer y/q");
                    }
                }
            } else {
                // Resize the image to fit into console window
                int height = origionalImage.getHeight();
                int width = origionalImage.getWidth();
                int fixedWidthRatio = width / 60;                                       // Determine ratio to create a fixed width
                int smallWidth = (int) Math.round(width / fixedWidthRatio);             // Divide by ratio to fix image width 
                double widthToHeightRatio = width / height;                             // Determine width to heigt ratio
                double factor = (1 / (smallWidth / widthToHeightRatio)) * height;       // Determn what factor to divide hieght by
                int smallHeight = (int) Math.round((height * 2.5) / factor);            // Correct image height

                if (smallHeight <= 0 || smallWidth < 0) // Make sure that height and width are never negative or zero
                {
                    smallHeight = height;
                    smallWidth = width;
                    System.out.println("I have to make this drawing big, this might take a second!");
                }

                // Convert the origional image into a graphic, then copy into a new resized buffered image
                BufferedImage smallImage = new BufferedImage(smallHeight, smallWidth, BufferedImage.TYPE_INT_RGB);
                Graphics g = smallImage.createGraphics();
                g.drawImage(origionalImage, 0, 0, smallHeight, smallWidth, null);
                g.dispose();

                // View refactored image
                if (viewDebugWindow == true) {
                    // create a jframe
                    JFrame frame = new JFrame("JOptionPane showMessageDialog example"); // Create a test window
                    frame.setAlwaysOnTop(true);         // Set the frame so it shows up on top of the IDE
                    JOptionPane.showMessageDialog(frame, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(smallImage));
                }

                // Generate the ASCII art
                ArrayList<String> ASCIIArt = Img2Ascii.convertToAscii(smallImage);
                System.out.println("All done, enjoy your artwork!   ^_^\n\n");

                // Print the ASCII art!!
                for (String line : ASCIIArt) {
                    System.out.println(line);
                }

                String answer;
                System.out.println("\n\n\nDo you want to keep drawing with me!? \nType 'y' for yes and 'n' for no.");
                Scanner input = new Scanner(System.in);
                while (true) {
                    answer = input.nextLine().trim().toLowerCase();

                    if (answer.equals("y")) {
                        isUserFinishedDrawing = false;
                        break;
                    } else if (answer.equals("n")) {
                        isUserFinishedDrawing = true;
                        System.out.println("\nOkay, see you later!");
                        break;
                    } else {
                        System.out.println("Sorry, I didn't catch that. Please answer y/n");
                    }
                }

            }
        }
    } // end of main

// Bool determines if origional images are displayed
    private static boolean viewDebugWindow = false;         // Turned off for final version
    private static boolean searchWasSuccessful = false;     // Set to true if google search suceeds
// This method searches google with the user input and returns
// an image to be converted to ASCII art

    public static BufferedImage searchGoogleImages(String requestedImage) {
        // Split the input up by word
        String[] subStrings = requestedImage.split(" ");

        // Add the words to a reformated string
        String googleAPI_Request = new String();
        for (String string : subStrings) {
            if (!string.isEmpty()) {
                if (string != subStrings[subStrings.length - 1]) {
                    googleAPI_Request += string + "%20";
                } else {
                    googleAPI_Request += string;
                }
            }
        }

        try {
            StringBuilder builder = new StringBuilder();
            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?"
                    + "v=1.0&q=" + googleAPI_Request + "&userip=INSERT-USER-IP");

            URLConnection connection = url.openConnection();
            // connection.addRequestProperty("Referer", /* Enter the URL of your site here */);

            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject json = new JSONObject(builder.toString());    // Create the JASON object
            //System.out.println(json.toString());      // For debugging 

            // Handle the cases where the google image result is corrupt
            int amtOfGoogleImages = json.getJSONObject("responseData").getJSONArray("results").length();
            BufferedImage image = null;
            for (int imageNumber = 0; imageNumber <= amtOfGoogleImages; imageNumber++) {
                try {
                    String imageURL = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(imageNumber).getString("url");
                    URL imgURL = new URL(imageURL);
                    image = ImageIO.read(imgURL);
                } catch (Exception e) {
                    image = null;
                }

                if (image != null) {
                    break;  // Get out of the loop
                }
            }

            // View Origional image
            if (viewDebugWindow == true) {
                // create a jframe
                JFrame frame = new JFrame("JOptionPane showMessageDialog example"); // Create a test window
                frame.setAlwaysOnTop(true);         // Set the frame so it shows up on top of the IDE
                JOptionPane.showMessageDialog(frame, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
            }
            searchWasSuccessful = true;
            return image;
        } catch (Exception e) {
            searchWasSuccessful = false;
        }
        return null;
    }

// This method determines if a Character can be flipped. If so, the pixle is flipped
    public static char flipBackwardsPixles(char dragonPixle) {
        char correctedPixle = dragonPixle;  // Add a place to store the flipped Character

        Map<Character, Character> flippableDictoinary = new HashMap(); // Create a dictoinary
        String flippable = new String("bd/(),`");  // Define backwards Characters
        String flipped = new String("db\\)(`'"); // Define corrected Characters

        // Add the flippable char definitions to a Dictoinary array
        for (int i = 0; i < flippable.length(); i++) {
            flippableDictoinary.put(flippable.charAt(i), flipped.charAt(i));
        }

        // Replace the method input pixle with the flipped version if flip is defined
        for (Map.Entry<Character, Character> pixle : flippableDictoinary.entrySet()) {
            if (dragonPixle == pixle.getKey()) // Determine if there is a definition for the flip
            {
                correctedPixle = pixle.getValue();       // Replaced with flipped version
                return correctedPixle;
            }
        }
        return correctedPixle;  // If there is no match, return the origional Character
    }

// Method to determine length of the longest line in a ListArray of strings
    public static int determineMaxLength(ArrayList<String> arrayOfStrings) {
        int maxCount = 0;

        for (String line : arrayOfStrings) {
            if (maxCount < line.length()) {
                maxCount = line.length();
            }
        }
        return maxCount;
    }

} // end of class
