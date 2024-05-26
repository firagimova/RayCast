
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Plane extends Object3D {

    Vector4 normal;
    float d; //offset
    Vector4 color;

    public Plane(Vector4 normal, float d, Vector4 color) {
        this.normal = normal;
        this.d = d;
        this.color = color;
    }

    public Plane(JSONObject planeObject){
        this.normal = new Vector4((JSONArray) planeObject.get("normal"));
        this.d = ((Number) planeObject.get("offset")).floatValue();
        JSONArray sphereColor = (JSONArray) planeObject.get("color");
        this.color = new Vector4(sphereColor);
    }	
    
    @Override
    public void intersect(Ray ray, Hit hit, float tmin) {

        // Calculate the denominator of the intersection formula
        float denom = normal.dot(ray.direction);

        // If the denominator is close to 0, the ray is parallel to the plane
        if (Math.abs(denom) > 1e-6) {
            Vector4 p0 = normal.multVectors(new Vector4(d, d, d)); // Any point P on the plane that satisfies P.n = d
            float t = (p0.subtract(ray.origin)).dot(normal) / denom;
            // If t is within the acceptable range
            if (t >= tmin && t < hit.t) {
                hit.t = t;
                hit.material = this.material;
                hit.normal = this.normal;
            }
        }

    }

}
