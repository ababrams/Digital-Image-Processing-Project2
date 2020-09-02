import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Interpolation {
	public static void main(String args[]) {
	
	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	    Mat src = Imgcodecs.imread("/home/corwin/samples/animals/camel.jpeg");
	    
	    BufferedImage buf1 = interpolation(src, Imgproc.INTER_NEAREST);
	    BufferedImage buf2 = interpolation(src, Imgproc.INTER_LINEAR);
	    BufferedImage buf3 = interpolation(src, Imgproc.INTER_LINEAR_EXACT);
	    BufferedImage buf4 = interpolation(src, Imgproc.INTER_CUBIC);
	    
	    ImageIcon icon1 = new ImageIcon(buf1);
	    ImageIcon icon2 = new ImageIcon(buf2);
	    ImageIcon icon3 = new ImageIcon(buf3);
	    ImageIcon icon4 = new ImageIcon(buf4);
	    JLabel label1 = new JLabel(icon1);
	    JLabel label2 = new JLabel(icon2);
	    JLabel label3 = new JLabel(icon3);
	    JLabel label4 = new JLabel(icon4);
	    JFrame frame = new JFrame();
	    JPanel panel = new JPanel();
	    JPanel panel2 = new JPanel();
	    GridLayout grid = new GridLayout(2,2);
	    label1.setHorizontalTextPosition(JLabel.CENTER);
	    label1.setVerticalTextPosition(JLabel.BOTTOM);
	    label2.setHorizontalTextPosition(JLabel.CENTER);
	    label2.setVerticalTextPosition(JLabel.BOTTOM);
	    label3.setHorizontalTextPosition(JLabel.CENTER);
	    label3.setVerticalTextPosition(JLabel.BOTTOM);
	    label4.setHorizontalTextPosition(JLabel.CENTER);
	    label4.setVerticalTextPosition(JLabel.BOTTOM);
	    
	   
	    panel.add(label1);
	    panel.add(label2);
	    panel2.add(label3);
	    panel2.add(label4);
	    label1.setText("Nearest Neighbor");
	    label2.setText("Linear");
	    label3.setText("Bilinear");
	    label4.setText("Bicubic");
	    frame.setLayout(grid);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(panel2);
		frame.pack();
		frame.setVisible(true);
	   }
	
	public static BufferedImage interpolation(Mat src, int inter) {
		
		Mat dst = new Mat();

	    Size size = new Size(src.cols()*1.5, src.rows()*1.5);

	    Imgproc.resize(src, dst, size, 0, 0, inter);
	    
	    BufferedImage bufImage = null;
		
		MatOfByte matOfByte = new MatOfByte();       
	    Imgcodecs.imencode(".jpeg", dst, matOfByte);

	    byte[] byteArray = matOfByte.toArray(); 

	    //Preparing the Buffered Image 
	    InputStream in = new ByteArrayInputStream(byteArray); 
	    try {
	    	bufImage = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bufImage;
	}
}
