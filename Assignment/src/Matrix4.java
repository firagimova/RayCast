


public class Matrix4 {

    float mdata[][] = new float[4][4];

    public Matrix4() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                
                if(i == j){
                    this.mdata[i][j] = 1.0f;
                }
                else {
                    this.mdata[i][j] = 0.0f;
                }
            }
        }
    }

    public Matrix4(Matrix4 m) {
        this.mdata = m.mdata;
    }

    public Matrix4(float[][] arr) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.mdata[i][j] = arr[i][j];
            }
        }
    }

    public Matrix4 Translate(float tx, float ty, float tz) {
        Matrix4 translationMatrix = new Matrix4();

        
        translationMatrix.mdata[0][3] = tx;
        translationMatrix.mdata[1][3] = ty;
        translationMatrix.mdata[2][3] = tz;

        return translationMatrix;
    }

    public Matrix4 Scale(float sx, float sy, float sz) {
        Matrix4 scaleMatrix = new Matrix4();

        scaleMatrix.mdata[0][0] = sx;
        scaleMatrix.mdata[1][1] = sy;
        scaleMatrix.mdata[2][2] = sz;

        return scaleMatrix;
    }

    public Matrix4 RotateX(float angle) {
        float[][] tempMatrix = {
            {1, 0, 0, 0},
            {0, (float) Math.cos(angle), (float) -Math.sin(angle), 0},
            {0, (float) Math.sin(angle), (float) Math.cos(angle), 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(tempMatrix);
    }

    public Matrix4 RotateY(float angle) {
        float[][] tempMatrix = {
            {(float) Math.cos(angle), 0, (float) Math.sin(angle), 0},
            {0, 1, 0, 0},
            {(float) -Math.sin(angle), 0, (float) Math.cos(angle), 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(tempMatrix);
    }

    public Matrix4 RotateZ(float angle) {
        float[][] tempMatrix = {
            {(float) Math.cos(angle), (float) Math.sin(angle), 0, 0},
            {(float) -Math.sin(angle), (float) Math.cos(angle), 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(tempMatrix);
    }

    public Matrix4 Rotate(float x, float y, float z, float angle) {
        // Normalize the rotation axis
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        x /= length;
        y /= length;
        z /= length;

        // Precompute trigonometric values
        float cosTheta = (float) Math.cos(angle);
        float sinTheta = (float) Math.sin(angle);
        float oneMinusCosTheta = 1 - cosTheta;

        // Compute rotation matrix elements
        float[][] rotationMatrix = {
            {(cosTheta + x * x * oneMinusCosTheta), (x * y * oneMinusCosTheta - z * sinTheta), (x * z * oneMinusCosTheta + y * sinTheta), 0},
            {(y * x * oneMinusCosTheta + z * sinTheta), (cosTheta + y * y * oneMinusCosTheta), (y * z * oneMinusCosTheta - x * sinTheta), 0},
            {(z * x * oneMinusCosTheta - y * sinTheta), (z * y * oneMinusCosTheta + x * sinTheta), (cosTheta + z * z * oneMinusCosTheta), 0},
            {0, 0, 0, 1}
        };

        return new Matrix4(rotationMatrix);
    }

    public Vector4 multi(Vector4 v) {
        Vector4 multVector = new Vector4(0, 0, 0, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                multVector.v[j] += (this.mdata[j][i] * v.v[i]);
            }
        }
        return multVector;
    }

    public Matrix4 multi(Matrix4 m) {
        Matrix4 multMatrix = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                multMatrix.mdata[i][j] = 0.0f;
                for (int k = 0; k < 4; k++) {
                    multMatrix.mdata[i][j] += (this.mdata[i][k] * m.mdata[k][j]);
                }

            }
        }
        return multMatrix;

    }

    public float det() {

        float[][] matrix = this.mdata;

        float det = matrix[0][0] * matrix[1][1] * matrix[2][2] * matrix[3][3]
                + matrix[0][0] * matrix[1][2] * matrix[2][3] * matrix[3][1]
                + matrix[0][0] * matrix[1][3] * matrix[2][1] * matrix[3][2]
                + matrix[0][1] * matrix[1][0] * matrix[2][3] * matrix[3][2]
                + matrix[0][1] * matrix[1][2] * matrix[2][0] * matrix[3][3]
                + matrix[0][1] * matrix[1][3] * matrix[2][2] * matrix[3][0]
                + matrix[0][2] * matrix[1][0] * matrix[2][1] * matrix[3][3]
                + matrix[0][2] * matrix[1][1] * matrix[2][3] * matrix[3][0]
                + matrix[0][2] * matrix[1][3] * matrix[2][0] * matrix[3][1]
                + matrix[0][3] * matrix[1][0] * matrix[2][2] * matrix[3][1]
                + matrix[0][3] * matrix[1][1] * matrix[2][0] * matrix[3][2]
                + matrix[0][3] * matrix[1][2] * matrix[2][1] * matrix[3][0]
                - matrix[0][0] * matrix[1][1] * matrix[2][3] * matrix[3][2]
                - matrix[0][0] * matrix[1][2] * matrix[2][1] * matrix[3][3]
                - matrix[0][0] * matrix[1][3] * matrix[2][2] * matrix[3][1]
                - matrix[0][1] * matrix[1][0] * matrix[2][2] * matrix[3][3]
                - matrix[0][1] * matrix[1][2] * matrix[2][3] * matrix[3][0]
                - matrix[0][1] * matrix[1][3] * matrix[2][0] * matrix[3][2]
                - matrix[0][2] * matrix[1][0] * matrix[2][3] * matrix[3][1]
                - matrix[0][2] * matrix[1][1] * matrix[2][0] * matrix[3][3]
                - matrix[0][2] * matrix[1][3] * matrix[2][1] * matrix[3][0]
                - matrix[0][3] * matrix[1][0] * matrix[2][1] * matrix[3][2]
                - matrix[0][3] * matrix[1][1] * matrix[2][2] * matrix[3][0]
                - matrix[0][3] * matrix[1][2] * matrix[2][0] * matrix[3][1];

        return det;
    }
    
    public Matrix4 transpose() {
        Matrix4 transposeMatrix = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transposeMatrix.mdata[i][j] = this.mdata[j][i];
            }
        }
        return transposeMatrix;
    }
    
    
    private float determinant3x3(int i1, int i2, int i3, int j1, int j2, int j3) {
        return
            mdata[i1][j1] * (mdata[i2][j2] * mdata[i3][j3] - mdata[i2][j3] * mdata[i3][j2]) -
            mdata[i1][j2] * (mdata[i2][j1] * mdata[i3][j3] - mdata[i2][j3] * mdata[i3][j1]) +
            mdata[i1][j3] * (mdata[i2][j1] * mdata[i3][j2] - mdata[i2][j2] * mdata[i3][j1]);
    }
    
    
    public float determinant() {
        float det = 0.0f;
        for (int i = 0; i < 4; i++) {
            det += (i % 2 == 0 ? 1 : -1) * mdata[0][i] * determinant3x3(1, 2, 3, (i + 1) % 4, (i + 2) % 4, (i + 3) % 4);
        }
        return det;
    }
    
    
    public Matrix4 inverse() {
        float det = determinant();
        if (det == 0) {
            throw new ArithmeticException("Matrix is not invertible.");
        }

        Matrix4 inverse = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Cofactor (with sign flip)
                float cofactor = determinant3x3((j + 1) % 4, (j + 2) % 4, (j + 3) % 4, (i + 1) % 4, (i + 2) % 4, (i + 3) % 4);
                cofactor = (i + j) % 2 == 0 ? cofactor : -cofactor;
                // Adjugate and divide by determinant
                inverse.mdata[i][j] = cofactor / det;
            }
        }
        return inverse;
    }

}
