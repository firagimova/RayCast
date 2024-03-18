
import java.awt.Color;
import java.awt.image.BufferedImage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Main {

    
    public static void main(String[] args) {
        
        JSONObject jsonObj = JSONReader.ReadJSON("scene2.json");
        
        int resolution = 500;
        float near = 5.0f;
        float far = 15.0f;

        BufferedImage bufferedImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        BufferedImage depthImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);

        JSONObject background = (JSONObject) jsonObj.get("background");
        Vector4 backgroundColor = new Vector4((JSONArray) background.get("color"));

        OrthographicCamera orthoCamObj = new OrthographicCamera((JSONObject) jsonObj.get("orthocamera"));

        JSONArray group = (JSONArray) jsonObj.get("group");
        Group groupObject = new Group((group));

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                Hit h = new Hit(far, backgroundColor);

                Ray r = orthoCamObj.generateRay((float) i / resolution, 1 - (float) (j) / resolution);
                groupObject.intersect(r, h, 0.001f);

                Color colorNumbers = new Color((int) h.color.v[0], (int) h.color.v[1], (int) h.color.v[2]);
                int rgbNumbers = colorNumbers.getRGB();
                float tempColor = ((far - h.t) / (far - near)) * 255.0f;

                Color depthColor = new Color((int) tempColor, (int) tempColor, (int) tempColor);
                int depthRgbNumbers = depthColor.getRGB();

                bufferedImage.setRGB(i, j, rgbNumbers);
                depthImage.setRGB(i, j, depthRgbNumbers);

            }
        }
        
        ImageWriter.WriteImage(bufferedImage, "image.png");
        ImageWriter.WriteImage(depthImage, "depth_image.png");
        
    }
    
}
