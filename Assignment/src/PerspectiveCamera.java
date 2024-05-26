
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class PerspectiveCamera extends Camera {
    
    Vector4 center;
    Vector4 direction;
    Vector4 up;
    Vector4 horizontal;
    float angle;
    
    public PerspectiveCamera(Vector4 center, Vector4 direction, Vector4 up, float angle) {
        this.center = center;
        this.direction = direction;
        this.up = up;
        this.horizontal = direction.cross(up);
        this.angle = -angle;
    }
    
    public PerspectiveCamera(JSONObject perspectiveCamera) {
        JSONArray perspectiveCameraCenter = (JSONArray) perspectiveCamera.get("center");
        this.center = new Vector4(perspectiveCameraCenter);

        JSONArray perspectiveCameraDirection = (JSONArray) perspectiveCamera.get("direction");
        this.direction = new Vector4(perspectiveCameraDirection);

        JSONArray perspectiveCameraUp = (JSONArray) perspectiveCamera.get("up");
        this.up = new Vector4(perspectiveCameraUp);

        horizontal = direction.cross(up);

        this.angle = -((Number) perspectiveCamera.get("angle")).floatValue();
    }

    @Override
    public Ray generateRay(float x, float y) {
        
        Ray perspectiveCamRay = new Ray();

        perspectiveCamRay.origin = this.center;
        
        float halfAngle = (float) Math.tan(angle / 2);
        Vector4 rayDirection = direction.addition(horizontal.multV((x - 0.5f) * halfAngle)).addition(up.multV((y - 0.5f) * halfAngle));
        
        perspectiveCamRay.direction = rayDirection.normalize();
        
        return perspectiveCamRay;
        
    }
    
}
