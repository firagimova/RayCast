
public class Hit {
    float t;
    
    Vector4 color = new Vector4();
    Vector4 normal = new Vector4();
    
    public Hit() {
        color = new Vector4();
        normal = new Vector4();
    }
    
    public Hit(float t, Vector4 color) {
        this.t = t;
        this.color = color;
    }
}
