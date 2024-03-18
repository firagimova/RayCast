
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Sphere extends Object3D{

    float radius;
    Vector4 center;
    Vector4 color = new Vector4();
    
    public Sphere(){
        center = new Vector4();
        radius = 0.0f;
        color = new Vector4();
    }
    
    public Sphere(Vector4 color, float radius, Vector4 center) {
        super(color);
        this.radius = radius;
        this.center = center;
    }
    
    public Sphere(JSONObject sphereObject){
        JSONArray sphereCenter = (JSONArray) sphereObject.get("center");
        this.center = new Vector4(sphereCenter);

        this.radius = ((Number) sphereObject.get("radius")).floatValue();

        JSONArray sphereColor = (JSONArray) sphereObject.get("color");
        this.color = new Vector4(sphereColor);
    }
    
    @Override
    public void intersect(Ray ray, Hit hit, float tmin) {
        Vector4 tempOrigin = new Vector4(ray.origin.subtract(this.center));
        float b = ray.direction.dot(tempOrigin) * 2;

        float c = (float) (tempOrigin.dot(tempOrigin) - Math.pow(radius, 2));

        if(Math.pow(b,2) - 4 * c > 0){
            float d = (float) Math.sqrt(Math.pow(b, 2) - 4 * c);
            float temp_t = (-b - d) / 2;

            if(temp_t > tmin && temp_t < hit.t){

                hit.t = temp_t;
                hit.color = this.color;
            }
            temp_t = (-b +d) / 2;
            if(temp_t > tmin && temp_t < hit.t){
                hit.t = temp_t;
                hit.color = this.color;
            }
        }
    }
    
}
