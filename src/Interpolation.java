import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Interpolation {
	static ArrayList<Mat> mats = new ArrayList<Mat>();
	static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	static ArrayList<String> labelText = new ArrayList<String>();
	static JFrame frame = new JFrame();
	static int index = 0;
	
	public static void main(String args[]) throws IOException {
	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    
	    // file directory of images
	    File dir = new File("../Interpolation/src/images");
	    
	    if(dir.exists()) {
	    	for (File f : dir.listFiles()) {
	    		addMat(f);
	    	}
	    }else {
	    	System.out.println("File does not exist");
	    	System.exit(0);
	    }
	    
	    listener();

	    Mat src = mats.get(0);
	    double scale = 400.0;
	    
	    // scale to fit
	    interpolation(src, Imgproc.INTER_NEAREST , scale/src.rows());
	    labelText.add("Nearest Neighbor las-vegas-1");
	    interpolation(src, Imgproc.INTER_LINEAR , scale/src.rows());
	    labelText.add("Bilinear las-vegas-1");
	    interpolation(src, Imgproc.INTER_CUBIC , scale/src.rows());
	    labelText.add("Bicubic las-vegas-1");
	    
	    src = mats.get(1);
	    
	    // scale to fit
	    interpolation(src, Imgproc.INTER_NEAREST , scale/src.rows());
	    labelText.add("Nearest Neighbor las-vegas-2");
	    interpolation(src, Imgproc.INTER_LINEAR , scale/src.rows());
	    labelText.add("Bilinear las-vegas-2");
	    interpolation(src, Imgproc.INTER_CUBIC , scale/src.rows());
	    labelText.add("Bicubic las-vegas-2");
	    
	    src = mats.get(2);
	    
	    // scale to fit
	    interpolation(src, Imgproc.INTER_NEAREST , scale/src.rows());
	    labelText.add("Nearest Neighbor las-vegas-3");
	    interpolation(src, Imgproc.INTER_LINEAR , scale/src.rows());
	    labelText.add("Bilinear las-vegas-3");
	    interpolation(src, Imgproc.INTER_CUBIC , scale/src.rows());
	    labelText.add("Bicubic las-vegas-3");
	    
	    showFrame();
	  	    
	   }
	
	public static void addMat(File f) {
		Mat img = Imgcodecs.imread(f.toString());
		mats.add(img);
	}
	
	public static void interpolation(Mat src, int inter, double ratio) {
		
		Mat dst = new Mat();

	    Size size = new Size(src.cols()*ratio, src.rows()*ratio);

	    Imgproc.resize(src, dst, size, 0, 0, inter);
	    
	    BufferedImage bufImage = null;
		
		MatOfByte matOfByte = new MatOfByte();       
	    Imgcodecs.imencode(".jpg", dst, matOfByte);

	    byte[] byteArray = matOfByte.toArray(); 

	    //Preparing the Buffered Image 
	    InputStream in = new ByteArrayInputStream(byteArray); 
	    try {
	    	bufImage = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		images.add(bufImage);
	}
	
	/**
	 * adds key listener to frame.
	 * n/space - next image
	 * p - previous image
	 * q - exit program
	 */
	public static void listener() {
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent a) {
				if(a.getKeyChar() == 'n' || a.getKeyCode() == KeyEvent.VK_SPACE) {
					nextImage();
				}
				
				if(a.getKeyChar() == 'p') {
					previousImage();
				}
				
				if(a.getKeyChar() == 'q') {
					System.exit(0);
				}	
			}

			@Override
			public void keyReleased(KeyEvent a) {}
			@Override
			public void keyTyped(KeyEvent a) {}
		});
	}
	
	/**
	 * sets index to next within bounds value
	 */
	public static void nextImage() {
		if (index < images.size() - 1) {
			index++;
		} else {
			index = 0;
		}
		showFrame();
	}
	
	/**
	 * sets index to previous within bounds value
	 */
	public static void previousImage() {
		if (index == 0) {
			index = images.size() - 1;
		} else {
			index--;
		}
		showFrame();
	}
	
	public static void showFrame() {
		BufferedImage buf = images.get(index);
		ImageIcon icon = new ImageIcon(buf);
	    JLabel label = new JLabel(icon);

	    frame.add(label);
		frame.getContentPane();
		frame.pack();
		frame.setVisible(true);
		System.out.println(index + " " + labelText.get(index));
	}
}