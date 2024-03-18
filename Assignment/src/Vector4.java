
public class Vector4 {

    

    float[] v= new float[4];

    public Vector4() {
        v = new float[]{0, 0, 0, 1};
    }

    public Vector4(float x, float y, float z) {
        this();
        this.v[0] = x;
        this.v[1] = y;
        this.v[2] = z;

    }

    public Vector4(float x, float y, float z, float w) {
        this(x, y, z);
        this.v[3] = w;
    }

    public Vector4(Vector4 v) {
        this.v[0] = v.v[0];
        this.v[1] = v.v[1];
        this.v[2] = v.v[2];
        this.v[3] = v.v[3];
    }

    public Vector4(org.json.simple.JSONArray jsonArray) {
        this(((Number) jsonArray.get(0)).floatValue(), ((Number) jsonArray.get(1)).floatValue(),
                ((Number) jsonArray.get(2)).floatValue());
    }

    //adding constructor that gets json file
    public float magnitude() {
        return (float) Math.sqrt((this.v[0]) * (this.v[0]) + (this.v[1]) * (this.v[1]) + (this.v[2]) * (this.v[2]));
    }

    public float dot(Vector4 v) {

        return this.v[0] * v.v[0] + this.v[1] * v.v[1] + this.v[2] * v.v[2];
    }

    public Vector4 cross(Vector4 v) {

        return new Vector4(this.v[1] * v.v[2] - this.v[2] * v.v[1], this.v[2] * v.v[0] - this.v[0] * v.v[2], this.v[0] * v.v[1] - this.v[1] * v.v[0], 1);
    }
    
    public Vector4 MultV(float constant) {

        return new Vector4((this.v[0] * constant), (this.v[1] * constant), (this.v[2] * constant));
    }
    
    public Vector4 addition(Vector4 v1) {
        

        return new Vector4((this.v[0] + v1.v[0]), (this.v[1] + v1.v[1]), (this.v[2] + v1.v[2]));
    }
    
    public Vector4 subtract(Vector4 v1) {
        

        return new Vector4((this.v[0] - v1.v[0]), (this.v[1] - v1.v[1]), (this.v[2] - v1.v[2]));
    }

    public void normalize() {

        float length = magnitude();
        this.v[0] = this.v[0] / length;
        this.v[1] = this.v[1] / length;
        this.v[2] = this.v[2] / length;
    }

}
