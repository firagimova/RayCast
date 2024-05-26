
//Firuza Rahimova 20050141056

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Main {

    
    public static void main(String[] args) {
        /*
        //ASSIGNMENT 3 
        
        JSONObject jsonObj = JSONReader.ReadJSON("scene5_sphere_triangle.json");
        
        int resolution = 500;
        float near = 5.0f;
        float far = 15.0f;

        BufferedImage bufferedImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        BufferedImage depthImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);

        JSONObject background = (JSONObject) jsonObj.get("background");
        Vector4 backgroundColor = new Vector4((JSONArray) background.get("color"));
        Vector4 ambientColor = new Vector4((JSONArray) background.get("ambient"));
        
        float ambientIntensity = ambientColor.magnitude();

        JSONObject lightObject = (JSONObject) jsonObj.get("light");
        Vector4 ligthDirection = new Vector4((JSONArray) lightObject.get("direction"));
        Vector4 lightColor = new Vector4((JSONArray) lightObject.get("color"));
        
        PerspectiveCamera perspectiveCamObj = new PerspectiveCamera(((JSONObject) jsonObj.get("perspectivecamera")));
        //OrthographicCamera orthoCamObj = new OrthographicCamera(((JSONObject) jsonObj.get("orthocamera")));
        
        
        JSONArray group = (JSONArray) jsonObj.get("group");
        Group groupObject = new Group((group));

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                // Initialize hit with far distance and background color
                Hit h = new Hit(far, backgroundColor);

                // Generate ray from the camera for the current pixel
                Ray r = perspectiveCamObj.generateRay((float) i / resolution, 1 - (float) (j) / resolution);
                //Ray r = orthoCamObj.generateRay((float) i / resolution, 1 - (float) (j) / resolution);

                // Check for intersections with objects in the scene
                groupObject.intersect(r, h, 0.001f);

                Vector4 ambientContribution = h.color.MultV(ambientIntensity);

                Vector4 diffuseContribution = new Vector4();

                // Calculate the direction to the light
                Vector4 lightDir = ligthDirection.MultV(-1);

                // Calculate diffuse component (dot product of light direction and normal)
                float diffuseIntensity = Math.max(lightDir.dot(h.normal), 0);
                // Calculate diffuse color contribution
                diffuseContribution = h.color.multVectors(lightColor).MultV(diffuseIntensity);

                // Calculate final pixel color
                Vector4 pixelColor = ambientContribution.addition(diffuseContribution);
                
                // Clamp color components to [0, 1] interval
                pixelColor.v[0] = Math.min(Math.max(pixelColor.v[0], 0), 1);
                pixelColor.v[1] = Math.min(Math.max(pixelColor.v[1], 0), 1);
                pixelColor.v[2] = Math.min(Math.max(pixelColor.v[2], 0), 1);

                // Calculate RGB color and depth based on intersection results
                Color colorNumbers = new Color((int) (int) (pixelColor.v[0] * 255), (int) (int) (pixelColor.v[1] * 255), (int) (int) (pixelColor.v[2] * 255));
                int rgbNumbers = colorNumbers.getRGB();
                float tempColor = ((far - h.t) / (far - near)) * 255.0f;
                Color depthColor = new Color((int) tempColor, (int) tempColor, (int) tempColor);
                int depthRgbNumbers = depthColor.getRGB();

                // Assign calculated RGB values to the corresponding pixels in the buffered images
                bufferedImage.setRGB(i, j, rgbNumbers);
                depthImage.setRGB(i, j, depthRgbNumbers);
            }
        }
        */
        
        //ASSIGNMENT 4 PART 1
        
        JSONObject jsonObj = JSONReader.ReadJSON("scene1_exponent_variations.json");

        int resolution = 500;
        float near = 5.0f;
        float far = 15.0f;

        BufferedImage bufferedImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);
        BufferedImage depthImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB);

        JSONObject background = (JSONObject) jsonObj.get("background");
        Vector4 backgroundColor = new Vector4((JSONArray) background.get("color"));
        Vector4 ambientColor = new Vector4((JSONArray) background.get("ambient"));

        float ambientIntensity = ambientColor.magnitude();

        JSONObject lightObject = (JSONObject) jsonObj.get("light");
        DirectionalLight dirLight = new DirectionalLight(
                new Vector4((JSONArray) lightObject.get("direction")),
                new Vector4((JSONArray) lightObject.get("color")));

        Camera camObj;

        if (jsonObj.containsKey("orthocamera")) {
            camObj = new OrthographicCamera((JSONObject) jsonObj.get("orthocamera"));
        } else {
            camObj = new PerspectiveCamera((JSONObject) jsonObj.get("perspectivecamera"));
        }

        JSONArray materialsObj = (JSONArray) jsonObj.get("materials");
        ArrayList<PhongMaterial> phongMaterials = new ArrayList<PhongMaterial>();

        for (int i = 0; i < materialsObj.size(); i++) {
            JSONObject phongMatObj = (JSONObject) materialsObj.get(i);
            JSONObject phongProperties = (JSONObject) phongMatObj.get("phongMaterial");
            PhongMaterial material = new PhongMaterial(
                    new Vector4((JSONArray) phongProperties.get("diffuseColor")),
                    new Vector4((JSONArray) phongProperties.get("specularColor")),
                    ((Number) phongProperties.get("exponent")).floatValue());
            phongMaterials.add(material);
        }

        JSONArray group = (JSONArray) jsonObj.get("group");
        Group groupObject = new Group(group, phongMaterials);

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                // Initialize hit with far distance and background color
                Hit h = new Hit(far, new PhongMaterial(backgroundColor, new Vector4(), 0));

                // Generate ray from the camera for the current pixel
                Ray r = camObj.generateRay((float) i / resolution, 1 - (float) (j) / resolution);

                // Check for intersections with objects in the scene
                groupObject.intersect(r, h, 0.001f);

                Vector4 ambientContribution = h.getMaterial().shade(r, h, dirLight);

                
                ambientContribution.v[0] = Math.min(Math.max(ambientContribution.v[0], 0), 1);
                ambientContribution.v[1] = Math.min(Math.max(ambientContribution.v[1], 0), 1);
                ambientContribution.v[2] = Math.min(Math.max(ambientContribution.v[2], 0), 1);

                // Calculate RGB color and depth based on intersection results
                Color colorNumbers = new Color((int) (ambientContribution.v[0] * 255), (int) (ambientContribution.v[1] * 255), (int) (ambientContribution.v[2] * 255));
                int rgbNumbers = colorNumbers.getRGB();
                float tempColor = ((far - h.t) / (far - near)) * 255.0f;
                Color depthColor = new Color((int) tempColor, (int) tempColor, (int) tempColor);
                int depthRgbNumbers = depthColor.getRGB();

                // Assign calculated RGB values to the corresponding pixels in the buffered images
                bufferedImage.setRGB(i, j, rgbNumbers);
                depthImage.setRGB(i, j, depthRgbNumbers);
            }
        }
        
        
        
        
        ImageWriter.WriteImage(bufferedImage, "image.png");
        ImageWriter.WriteImage(depthImage, "depth_image.png");
        
    }
    
}
