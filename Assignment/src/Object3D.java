
public abstract class Object3D {
    
    Material material;
    
    public Object3D() {
        this.material = new PhongMaterial();   
    }

    public Object3D(Material material) {
        this.material = material;
    }
    

    
    public abstract void intersect(Ray ray, Hit hit, float tmin);
    
}
