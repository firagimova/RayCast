
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSONReader {

    public static JSONObject ReadJSON(String pathName) {
        JSONParser parser = new JSONParser();
        FileReader fileReader;
        JSONObject obj = null;

        try {
            File file = new File(pathName);
            fileReader = new FileReader(file);

            obj = (JSONObject) parser.parse(fileReader);
            //parseObject(obj);
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace() + " :File Not Found");
        } catch (ParseException e) {
            System.out.println(e.getStackTrace() + " :Could not parse");
        } catch (IOException e) {
            System.out.println(e.getStackTrace() + " :IOException");
        }
        return obj;
    }

    public static void parseObject(JSONObject sceneObject) {
        int resolution = 500;
        float near = 5.0f;
        float far = 15.0f;

        BufferedImage bufferedImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        BufferedImage depthImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);

        JSONObject background = (JSONObject) sceneObject.get("background");
        Vector4 backgroundColor = new Vector4((JSONArray) background.get("color"));

        OrthographicCamera orthoCamObj = new OrthographicCamera((JSONObject) sceneObject.get("orthocamera"));

        JSONArray group = (JSONArray) sceneObject.get("group");
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

        ImageWriter imgWriter = new ImageWriter(bufferedImage, "image.png");
        ImageWriter imgWriterDepth = new ImageWriter(depthImage, "depth_image.png");

        
    }

}
