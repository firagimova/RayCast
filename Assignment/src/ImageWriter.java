import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageWriter {
     
    BufferedImage im;
    String name;
    
    public ImageWriter(BufferedImage image, String name){
        this.im = image;
        this.name = name;
    }
    
    //backgroudn color
    float[] color = new float[3]; 
    
     public static void WriteImage(BufferedImage image, String outputPath){
        try {
            File outputFile = new File(outputPath); // Specify the output PNG file path
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved successfully as " + outputFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}
