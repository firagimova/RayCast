
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Group extends Object3D {

     ArrayList<Object3D> objects;
    
     public Group(){
        objects = new ArrayList<Object3D>();
    }
     
     public Group(org.json.simple.JSONArray jArray) {
        this();
        for (int i = 0; i < jArray.size(); i++) {
            JSONObject tempObject = (JSONObject) jArray.get(i);
            if(tempObject.containsKey("sphere")){
                addObject(new Sphere((JSONObject) tempObject.get("sphere")));
            }
            else if(tempObject.containsKey("plane")){
                addObject(new Plane((JSONObject) tempObject.get("plane")));
            }
            else if(tempObject.containsKey("triangle")) {
                addObject(new Triangle((JSONObject) tempObject.get("triangle")));
            }
            else if(tempObject.containsKey("transform")) {
                addObject(new Transformation((JSONObject)tempObject.get("transform")));
            }
            else if(tempObject.containsKey("group")){
                addObject(new Group((JSONArray) tempObject.get("group")));
            }
        }
    }
     
     public void addObject(Object3D obj){
        objects.add(obj);
    }
     
    @Override
    public void intersect(Ray ray, Hit hit, float tmin) {
       for(int i = 0; i < objects.size(); i++){
            objects.get(i).intersect(ray, hit, tmin);
        }
    }
    
}
