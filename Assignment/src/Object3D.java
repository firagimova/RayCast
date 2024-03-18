
public abstract class Object3D {
    
    Vector4 color = new Vector4();
    
    public Object3D() {
        color = new Vector4();
    }

    public Object3D(Vector4 color) {
        this.color = color;
    }
    
    public abstract void intersect(Ray ray, Hit hit, float tmin);
    
}
