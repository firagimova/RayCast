
public class Hit {
    float t;
    
    Material material;
    Vector4 normal;
    
    public Hit() {
        material = new PhongMaterial();
        normal = new Vector4();
        t = 0;
    }
    
    public Hit(float t, Material material) {
        this.t = t;
        this.material = material;
        this.normal = new Vector4();
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Vector4 getNormal() {
        return normal;
    }

    public void setNormal(Vector4 normal) {
        this.normal = normal;
    }
    
    
}
