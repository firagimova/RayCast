
public class DirectionalLight extends Light {
    Vector4 direction;

    public DirectionalLight(Vector4 direction, Vector4 color) {
        this.direction = direction;
        this.color = color;
    }

    public Vector4 getDirection() {
        return direction;
    }
    
    public void setDirection(Vector4 direction) {
        this.direction = direction;
    }
    
    public Vector4 getColor() {
        return color;
    }

    public void setColor(Vector4 color) {
        this.color = color;
    }
    
    
}
