import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;

import javax.imageio.*;
 
public class LoadImageApplet extends Applet {
 
     private BufferedImage img;
 
     public void init() {
         try {
             URL url = new URL(getCodeBase(), System.getProperty("user.dir")+"img/InterstellarDoilies-1024.jpg");
             System.out.println(System.getProperty("user.dir"));
             img = ImageIO.read(url);
         } catch (IOException e) {
        	 System.err.println("this is stupid");
         }
     }
 
     public void paint(Graphics g) {
       g.drawImage(img, 50, 50, null);
       
     }
}