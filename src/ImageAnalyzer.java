import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.vecmath.*;

public class ImageAnalyzer extends Applet{

	// File representing the folder that you select using a FileChooser
	static final File dir = new File(System.getProperty("user.dir"));

	// array of supported extensions (use a List if you prefer)
	static final String[] EXTENSIONS = new String[]{
		"gif", "png", "bmp", "jpg" // and other formats you need
	};
	// filter to identify images based on their extensions
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	public static BufferedImage img;

	public void paint(Graphics g) {
		g.drawImage(img, 0,0, null);
	}

	@Override
	public void init() {//main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hey ");
		System.out.println(dir.isDirectory());
		//    BufferedImage hugeImage = ImageIO.read(PerformanceTest.class.getResource("12000X12000.jpg"));
		if (dir.isDirectory()) { // make sure it's a directory


			/*for (final File f : dir.listFiles(IMAGE_FILTER)) {
              BufferedImage img = null;	        
              try {
                  img = ImageIO.read(f);

                  // you probably want something more involved here
                  // to display in your UI
                  System.out.println("image: " + f.getName());
                  System.out.println(" width : " + img.getWidth());
                  System.out.println(" height: " + img.getHeight());
                  System.out.println(" size  : " + f.length());
              } catch (final IOException e) {
                  // handle errors here
              }
          }*/

			try{
				System.out.println(dir.getParent());
				File f = new File(dir.getParent()+"\\img\\stormtrooper rising sun.jpg");
				// 		dir.listFiles(IMAGE_FILTER)[0];
				img = ImageIO.read(f);

				System.out.println("image: " + f.getName());
				System.out.println(" width : " + img.getWidth());
				System.out.println(" height: " + img.getHeight());
				System.out.println(" size  : " + f.length());
				
				Vector4d pixel;
				HashMap<Tuple2d, Color> colours = new HashMap<Tuple2d, Color>();
				
				int[][] pixels = convertTo2DWithoutUsingGetRGB(img);
				for(int i = 0; i < pixels.length; i++)
				{
					for(int j = 0; j < pixels[i].length; j++)
					{
						pixel = toRGB(pixels[i][j]);
						System.out.println(pixel);
						colours.put(new Vector2d(i,j),new Color((float)(pixel.y/255),(float)(pixel.z/255),(float) (pixel.w/255)));
					}
				}


			} catch (final IOException e) {
				// handle errors here
				System.err.println("crap happened");
			}
		}


	}

	static double getAvg(int[] intArray)
	{
		double mean = 0;
		for(int i: intArray)
		{
			mean += intArray[i];
		}
		return mean/intArray.length;
	}

	static double getStdDev(int[] intArray)
	{
		double stdDev = 0;
		double mean = getAvg(intArray);
		for(int i: intArray)
		{
			stdDev += Math.pow((intArray[i] - mean),2);    		
		}
		stdDev *= 1/intArray.length;
		stdDev = Math.sqrt(stdDev);
		return stdDev;
	}

	public Vector4d toRGB(int rgb)
	{
		return new Vector4d((rgb & 0xff000000)/16777216, (((rgb & 0x00ff0000)%251658240)%3280)%255, 
				((rgb & 0x0000ff00)%3280)%255, (rgb & 0x000000ff)%255);
	}
	
	
	public int filterRGB(int rgb, boolean red, boolean green, boolean blue) {
		// Filter the colors
		int r = red ? 0 : ((rgb >> 16) & 0xff);
		int g = green ? 0 : ((rgb >> 8) & 0xff);
		int b = blue ? 0 : ((rgb >> 0) & 0xff);

		// Return the result
		return (rgb & 0xff000000) | (r << 16) | (g << 8) | (b << 0);
	}

	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}
}
