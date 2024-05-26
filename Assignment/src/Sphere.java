
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Sphere extends Object3D{

    float radius;
    Vector4 center;
    int materialIndex = -1;
    
    public Sphere(){
        center = new Vector4();
        radius = 0.0f;
        this.material = new PhongMaterial();
    }
    
    public Sphere(float radius, Vector4 center, Material material) {
        super(material);
        this.radius = radius;
        this.center = center;
    }
    
    public Sphere(JSONObject sphereObject){
        this();
        this.center = new Vector4((JSONArray) sphereObject.get("center"));
        this.radius = ((Number) sphereObject.get("radius")).floatValue();
        this.materialIndex = ((Number) sphereObject.get("material")).intValue();
    }
    /*
    public Sphere(Object3D object3DToSphere) {
        this();
        this.color = object3DToSphere.color;
    }
*/
    
    @Override
    public void intersect(Ray ray, Hit hit, float tmin) {
        Vector4 tempOrigin = new Vector4(ray.origin.subtract(this.center));
        float b = ray.direction.dot(tempOrigin) * 2;
        float c = (float) (tempOrigin.dot(tempOrigin) - radius * radius);

        float discriminant = b * b - 4 * c;
        if(discriminant > 0){
            float sqrtDiscriminant = (float) Math.sqrt(discriminant);
            float temp_t = (-b - sqrtDiscriminant) / 2;

            if(temp_t > tmin && temp_t < hit.t){
                hit.t = temp_t;
                hit.material = this.material;
                Vector4 intersectionPoint = ray.origin.addition(ray.direction.multV(temp_t));
                Vector4 normal = intersectionPoint.subtract(this.center).normalize();
                hit.normal = normal;
            }
            temp_t = (-b + sqrtDiscriminant) / 2;
            if(temp_t > tmin && temp_t < hit.t){
                hit.t = temp_t;
                hit.material = this.material;
                Vector4 intersectionPoint = ray.origin.addition(ray.direction.multV(temp_t));
                Vector4 normal = intersectionPoint.subtract(this.center).normalize();
                hit.normal = normal;
            }
        }
    }
    
}
