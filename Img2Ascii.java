/*
 * Credit for this class goes to the origional poster @ 
 * https://www.intosec.org/index.php?topic=119.0
 */
package javareview;

import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

class Img2Ascii 
{
    static double pixval;
    PrintWriter prntwrt;
    static FileWriter filewrt;


    public Img2Ascii() {
        try {
            prntwrt = new PrintWriter(filewrt = new FileWriter("asciiart.txt",true));
        } catch (IOException ex) {
        }
    }
    
    public static ArrayList<String> convertToAscii(BufferedImage img) {

        ArrayList<String> ASCIIString = new ArrayList<String>();
        
        for (int i = 0; i < img.getHeight(); i++)
        {
            String line = "";
                    
            for (int j = 0; j < img.getWidth(); j++)
            {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol.getGreen() * 0.11)));
                line += strChar(pixval);
            }
            ASCIIString.add(line);
        }
        
        return ASCIIString;
    }


    public static String strChar(double g)
    {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str;
    }


    public void print(String str)
    {
        try {
            prntwrt.print(str);
            prntwrt.flush();
            filewrt.flush();
        } catch (Exception ex) {
        }
    }
}
    

