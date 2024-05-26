
public class PhongMaterial extends Material {

    public Vector4 specularColor;
    public float exponent;

    public PhongMaterial() {
        super();
        this.specularColor = new Vector4();
        this.exponent = 0;
    }

    public PhongMaterial(Vector4 diffuseColor, Vector4 specularColor, float exponent) {
        this();
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.exponent = exponent;
    }

    public PhongMaterial(Vector4 diffuseColor, Vector4 reflectiveColor, Vector4 transparentColor, float indexOfRefraction, Vector4 specularColor, float exponent) {
        super(diffuseColor, reflectiveColor, transparentColor, indexOfRefraction);
        this.specularColor = specularColor;
        this.exponent = exponent;
    }

    /*
    //shading calculation
    @Override
    public Vector4 shade(Ray ray, Hit hit, Light light) {
        Vector4 surfaceNormal = hit.getNormal();
        Vector4 lightDirection = ((DirectionalLight) light).getDirection();

        Vector4 ambientComponent = new Vector4();

        // Diffuse component
        float diffuseFactor = Math.max(0, surfaceNormal.dot(lightDirection));
        Vector4 diffuseComponent = new Vector4(
                this.diffuseColor.v[0] * light.color.v[0] * diffuseFactor,
                this.diffuseColor.v[1] * light.color.v[1] * diffuseFactor,
                this.diffuseColor.v[2] * light.color.v[2] * diffuseFactor    
        ); 
        
        //Specular component
        Vector4 viewDirection = ray.direction.negate();
        Vector4 reflectedLightDirection = surfaceNormal.multV(2 * lightDirection.dot(surfaceNormal)).subtract(lightDirection).normalize();
        float specularFactor = (float) Math.pow(Math.max(0, viewDirection.dot(reflectedLightDirection)), this.exponent);
        Vector4 specularComponent = new Vector4(
                this.specularColor.v[0] * light.color.v[0] * specularFactor,
                this.specularColor.v[1] * light.color.v[1] * specularFactor,
                this.specularColor.v[2] * light.color.v[2] * specularFactor
        );
      
        Vector4 finalColor = ambientComponent.addition(diffuseComponent).addition(specularComponent);
        return finalColor;
    }
    */
    
    @Override
    public Vector4 shade(Ray ray, Hit hit, DirectionalLight light) {
        
        
        // Diffuse Component
        Vector4 normal = hit.getNormal().negate();
        float diffuseIntensity = Math.max(normal.dot(light.direction), 0);
        Vector4 diffuse = diffuseColor.multVectors(light.color).multV(diffuseIntensity);
        
        //Specular Component
        Vector4 viewDirection = ray.direction.multV(-1).normalize();
        Vector4 reflectionDirection = light.direction.reflect(normal).normalize();
        float specularIntensity = (float) Math.pow(Math.max(viewDirection.dot(reflectionDirection), 0), exponent);
        Vector4 specular = specularColor.multVectors(light.color).multV(specularIntensity);
        
        return diffuse.addition(specular);
    }
}


/*
        //the direction of the light source
        Vector4 lightDirection = light.getDirection().normalize();

        //the normal at the hit point
        Vector4 normal = hit.getNormal().normalize();

        //the view direction (direction from hit point to camera)
        Vector4 viewDirection = ray.getDirection().negate().normalize();

        //the reflection direction (direction of reflection of the light)
        Vector4 reflectionDirection = normal.multiply(normal.dot(lightDirection) * 2).subtract(lightDirection).normalize();

        //diffuse component
        float diffuseIntensity = Math.max(0, normal.dot(lightDirection));
        Vector4 diffuse = diffuseColor.multiply(diffuseIntensity);

        //specular component
        float specularIntensity = (float) Math.pow(Math.max(0, reflectionDirection.dot(viewDirection)), exponent);
        Vector4 specular = specularColor.multiply(specularIntensity);

        // Combine ambient, diffuse, and specular components
        Vector4 ambient = diffuseColor.multiply(0.1f); // Assuming ambient intensity is 0.1
        Vector4 finalColorVector = ambient.add(diffuse).add(specular);

        // Convert final color vector to RGBColor
        int red = (int) (finalColorVector.getX() * 255);
        int green = (int) (finalColorVector.getY() * 255);
        int blue = (int) (finalColorVector.getZ() * 255);

        return new RGBColor(red, green, blue); 
*/
