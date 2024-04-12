
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Transformation extends Object3D{

    Matrix4 m; //transformation matrix
    Object3D object;
    
    
    public Transformation(Matrix4 m, Object3D object) {
        this.m = m;
        this.object = object;
    }
    
    public Transformation(JSONObject transformationObject) {
        this.m = new Matrix4();
        if (transformationObject.containsKey("transformations")) {
            JSONArray transformationsArray = (JSONArray) transformationObject.get("transformations");

            for (int i = 0; i < transformationsArray.size(); i++) {
                if (transformationsArray.get(i) instanceof JSONObject transformObj) {
                    if (transformObj.containsKey("translate")) {
                        Vector4 translateVec = new Vector4((JSONArray) transformObj.get("tranlate"));
                        Matrix4 translateMatrix = new Matrix4();
                        translateMatrix = translateMatrix.Translate(translateVec.v[0], translateVec.v[1], translateVec.v[2]);
                        m = m.multi(translateMatrix);
                    }
                    
                    if (transformObj.containsKey("scale")) {
                        Vector4 scaleVec = new Vector4((JSONArray) transformObj.get("scale"));
                        Matrix4 scaleMatrix = new Matrix4();
                        scaleMatrix = scaleMatrix.Scale(scaleVec.v[0], scaleVec.v[1], scaleVec.v[2]);
                        m = m.multi(scaleMatrix);
                    }

                    if (transformObj.containsKey("xrotate")) {
                        JSONObject xRotObj = (JSONObject) transformObj;
                        float xRotAngle = ((Number) xRotObj.get("xrotate")).floatValue();
                        Matrix4 xRotationMatrix = new Matrix4();
                        xRotationMatrix = xRotationMatrix.RotateX(xRotAngle);
                        m = m.multi(xRotationMatrix);
                    }
                    if (transformObj.containsKey("yrotate")) {
                        JSONObject yRotObj =  (JSONObject) transformObj;
                        float yRotAngle = ((Number) yRotObj.get("yrotate")).floatValue();
                        Matrix4 yRotationMatrix = new Matrix4();
                        yRotationMatrix = yRotationMatrix.RotateY(yRotAngle);
                        m = m.multi(yRotationMatrix);
                    }
                    if (transformObj.containsKey("zrotate")) {
                        JSONObject zRotObj =  (JSONObject) transformObj;
                        float zRotAngle = ((Number) zRotObj.get("zrotate")).floatValue();
                        Matrix4 zRotationMatrix = new Matrix4();
                        zRotationMatrix = zRotationMatrix.RotateZ(-zRotAngle);
                        m = m.multi(zRotationMatrix);
                    }
                }
            }
        } 
        if (transformationObject.containsKey("object")) {
            JSONObject sceneObject = (JSONObject) transformationObject.get("object");
            if (sceneObject.containsKey("sphere")) {
                this.object = new Sphere((JSONObject) sceneObject.get("sphere"));
            }
        }
    }
    
    @Override
    public void intersect(Ray ray, Hit hit, float tmin) {
        
        Matrix4 mInverse = m.inverse(); // We'll add this method in the Matrix4 class
        
        Vector4 transformedOrigin = mInverse.multi(ray.origin);
        Vector4 transformedDirection = mInverse.multi(ray.direction).normalize();
        
        Ray transformedRay = new Ray(transformedOrigin, transformedDirection);
        
        object.intersect(transformedRay, hit, tmin);
        
        if (hit.t < Float.MAX_VALUE) {
            Matrix4 mTransposeInverse = mInverse.transpose(); // We'll add this method in the Matrix4 class
            Vector4 transformedNormal = mTransposeInverse.multi(hit.normal).normalize();
            
            hit.normal = transformedNormal;
        }
        
    }
    
}
