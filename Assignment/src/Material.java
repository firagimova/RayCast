
public abstract class Material {

    Vector4 diffuseColor;
    Vector4 reflectiveColor;
    Vector4 transparentColor;
    float indexOfRefraction;

    public Material(Vector4 diffuseColor, Vector4 reflectiveColor, Vector4 transparentColor, float indexOfRefraction) {
        this.diffuseColor = diffuseColor;
        this.reflectiveColor = reflectiveColor;
        this.transparentColor = transparentColor;
        this.indexOfRefraction = indexOfRefraction;
    }
    
    public Material() {
        this.diffuseColor = new Vector4();
        this.reflectiveColor = new Vector4();
        this.transparentColor = new Vector4();
        this.indexOfRefraction = 0;
    }

    public abstract Vector4 shade(Ray ray, Hit hit, DirectionalLight light);
    
    //public abstract Vector4 shade(Ray ray, Hit hit, DirectionalLight light, float ambientIntesity, Vector4 ambientColor);
}
