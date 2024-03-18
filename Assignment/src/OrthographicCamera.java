
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class OrthographicCamera extends Camera{

    // cross product of direction and up vectors gives the horizontal vector
    //center + (x-0.5)*size*horizontal + (y-0.5)*size*up
    
    Vector4 center;
    Vector4 direction;
    Vector4 up;
    Vector4 horizontal;
    float size;
    
    public OrthographicCamera(Vector4 center, Vector4 direction, Vector4 up, float size) {
        this.center = center;
        this.direction = direction;
        this.up = up;
        this.horizontal = direction.cross(up);
        this.size = size;
    }
    
    public OrthographicCamera(JSONObject orthocamera){
        JSONArray orthocameraCenter = (JSONArray) orthocamera.get("center");
        this.center = new Vector4(orthocameraCenter);

        JSONArray orthocameraDirection = (JSONArray) orthocamera.get("direction");
        this.direction = new Vector4(orthocameraDirection);

        JSONArray orthocameraUp = (JSONArray) orthocamera.get("up");
        this.up = new Vector4(orthocameraUp);
        this.horizontal = direction.cross(up);
        this.size = ((Number) orthocamera.get("size")).floatValue();
    }
    
    @Override
    public Ray generateRay(float x, float y) {
        Vector4 horizontal = new Vector4();
        horizontal = direction.cross(up);
        
        Ray orthoCamRay = new Ray();

        orthoCamRay.direction = this.direction;

        orthoCamRay.origin = (this.center.addition(horizontal.MultV((float) ((x - 0.5) * size))).addition(up.MultV((float) ((y - 0.5) * size))));

        return orthoCamRay;
    }
    
}
