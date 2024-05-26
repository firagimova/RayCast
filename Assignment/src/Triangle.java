
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Triangle extends Object3D {

    Vector4 v1;
    Vector4 v2;
    Vector4 v3;
    Vector4 color = new Vector4();

    public Triangle(Vector4 vect1, Vector4 vect2, Vector4 vect3) {
        v1 = vect1;
        v2 = vect2;
        v3 = vect3;
    }

    public Triangle(JSONObject triangleObject) {
        this.v1 = new Vector4((JSONArray) triangleObject.get("v1"));
        this.v2 = new Vector4((JSONArray) triangleObject.get("v2"));
        this.v3 = new Vector4((JSONArray) triangleObject.get("v3"));
        this.color = new Vector4((JSONArray) triangleObject.get("color"));
    }

    @Override
    public void intersect(Ray ray, Hit hit, float tmin) {
        // Compute vectors along two edges of the triangle
        Vector4 edge1 = v2.subtract(v1);
        Vector4 edge2 = v3.subtract(v1);

        // Compute the determinant
        Vector4 h = ray.direction.cross(edge2);
        float a = edge1.dot(h);

        // If the determinant is near zero, the ray is parallel to the triangle
        if (a > -0.00001f && a < 0.00001f) {
            return;
        }

        float f = 1.0f / a;

        // Compute the distance from v1 to the ray origin
        Vector4 s = ray.origin.subtract(v1);
        float u = f * (s.dot(h));

        // Check for intersection with the edge1-edge2 plane
        if (u < 0.0f || u > 1.0f) {
            return;
        }

        // Compute the distance from v1 to the hit point in plane
        Vector4 q = s.cross(edge1);
        float v = f * ray.direction.dot(q);

        // Check for intersection with the triangle
        if (v < 0.0f || u + v > 1.0f) {
            return;
        }

        // At this stage we can compute t to find out where the intersection point is on the line
        float t = f * edge2.dot(q);
        if (t > tmin && t < hit.t) { // Ray intersection
            hit.t = t;
            hit.material = this.material; // Assume you have the color of the triangle

            // Compute the normal at the intersection point
            Vector4 normal = edge1.cross(edge2).normalize();
            hit.normal = normal;
        }
    }

}
