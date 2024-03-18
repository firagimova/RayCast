
public class Ray {
    Vector4 origin = new Vector4();
    Vector4 direction = new Vector4();
    
    
    public Ray(Vector4 origin, Vector4 direction) {
        this.origin = origin;
        this.direction = direction;
    }
    
    public Ray() {
        origin = new Vector4();
        direction = new Vector4();
    }
    
}
