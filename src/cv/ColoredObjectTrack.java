package cv;


import javax.swing.JPanel;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class ColoredObjectTrack implements Runnable {
 
   /* public static void main(String[] args) {
        ColoredObjectTrack cot = new ColoredObjectTrack();
        Thread th = new Thread(cot);
        th.start();
    }*/

    final int INTERVAL = 10;// 1sec
    final int CAMERA_NUM = 0; // Default camera for this time

    /**
     * Correct the color range- it depends upon the object, camera quality,
     * environment.
     */
    static CvScalar rgba_min = cvScalar(0, 0, 130, 0);// RED wide dabur birko
    static CvScalar rgba_max = cvScalar(80, 80, 255, 0);

    IplImage image;
    Mat	image_mat;
    Mat image_mat2;
    Mat image_mat3;
    CanvasFrame original = new CanvasFrame("Original");
    CanvasFrame HUE = new CanvasFrame("HUE");
    CanvasFrame edge = new CanvasFrame("edge");
    
    CanvasFrame path = new CanvasFrame("Detection");
    int ii = 0;
    JPanel jp = new JPanel();

    public ColoredObjectTrack() {
        original.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        HUE.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        edge.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        //path.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        //path.setContentPane(jp);
    }

    @Override
    public void run() {
        try {
            FrameGrabber grabber = FrameGrabber.createDefault(CAMERA_NUM);
            //OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            OpenCVFrameConverter.ToMat matconverter = new OpenCVFrameConverter.ToMat();
            grabber.start();
            
           
            
            BytePointer dat;
            int i;
            
     
    
            while (true) {
            	
                image_mat = matconverter.convert(grabber.grab());
                image_mat = extract_edges(image_mat);
        /*        image_mat2 = matconverter.convert(grabber.grab());
                image_mat2 = extract_edges(image_mat2);
                compare(image_mat,image_mat2,image_mat2,CMP_NE);
                compare(image_mat,image_mat2,image_mat,CMP_EQ);
                image_mat3 = matconverter.convert(grabber.grab());
                image_mat3 = extract_edges(image_mat3);
                compare(image_mat,image_mat3,image_mat3,CMP_NE);
                compare(image_mat,image_mat3,image_mat,CMP_EQ); */
                original.showImage(matconverter.convert(image_mat));
              
             
  /*              
                           
      
                if (image_mat != null) {
                	original.showImage(matconverter.convert(image_mat));
                	
                    cvtColor(image_mat, image_mat, COLOR_BGR2HSV);
                    dat = image_mat.data();
                    for (i = 0; i < (image_mat.arrayHeight()*image_mat.arrayWidth()*3);i += 3)
                    {
                    
                    		
                    		dat = dat.put(0 + i , (byte) dat.get(i+2));
                    		dat = dat.put(1 + i  , (byte) dat.get(i+2));
                    		
                    	
                    }
                	  
                    image_mat = image_mat.data(dat);
                    cvtColor(image_mat, image_mat, COLOR_BGR2GRAY);
                    //medianBlur(image_mat, image_mat, 5);
                    HUE.showImage(matconverter.convert(image_mat));
                    
                    
                    Canny(image_mat,image_mat, 10, 100);
                    
                    edge.showImage(matconverter.convert(image_mat));
                    
                	
                    
                    
                }
    */            // Thread.sleep(INTERVAL);
            }
        } catch (Exception e) {
        }
    }

 Mat extract_edges(Mat picture)
 {
	 int i;
	 Vec3fVector circle = new Vec3fVector();
	 BytePointer dat;
	 cvtColor(picture, picture, COLOR_BGR2HSV);
     dat = picture.data();
     
     for (i = 0; i < (picture.arrayHeight()*picture.arrayWidth()*3);i += 3)
     	{
     	 dat = dat.put(0 + i , (byte) dat.get(i+2));
     	 dat = dat.put(1 + i  , (byte) dat.get(i+2));     	
     	}
     picture = picture.data(dat);
     cvtColor(picture, picture, COLOR_BGR2GRAY); 
     //Canny(picture,picture, 10, 100);
  	 HoughCircles(picture, circle , CV_HOUGH_GRADIENT, 1, 50, 120, 50, 50, 100);
  	 if (circle.size() > 0) {
  	 System.out.println((circle.get(0).get(0))+"  "+(circle.get(0).get(1))+"  "+(circle.get(0).get(2)));
  	cvtColor(picture, picture, COLOR_GRAY2RGB);
  	
  	 circle(picture, new Point((int)circle.get(0).get(0),(int)circle.get(0).get(1)),(int) circle.get(0).get(2), Scalar.RED  );
  	 }
  
   
	 return picture;
 }
    
    


}
