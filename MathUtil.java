package ass1;

import java.lang.Math;
/**
 * A collection of useful math methods 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class MathUtil {
	private static final int dim = 3;
    /**
     * Normalise an angle to the range (-180, 180]
     * 
     * @param angle 
     * @return
     */
    static public double normaliseAngle(double angle) {
        return ((angle + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
    }

    /**
     * Clamp a value to the given range
     * 
     * @param value
     * @param min
     * @param max
     * @return
     */

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Multiply two matrices
     * 
     * @param p A 3x3 matrix
     * @param q A 3x3 matrix
     * @return
     */
    public static double[][] multiply(double[][] p, double[][] q) {

        double[][] m = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                   m[i][j] += p[i][k] * q[k][j]; 
                }
            }
        }

        return m;
    }

    /**
     * Multiply a vector by a matrix
     * 
     * @param m A 3x3 matrix
     * @param v A 3x1 vector
     * @return
     */
    public static double[] multiply(double[][] m, double[] v) {

        double[] u = new double[3];

        for (int i = 0; i < 3; i++) {
            u[i] = 0;
            for (int j = 0; j < 3; j++) {
                u[i] += m[i][j] * v[j];
            }
        }

        return u;
    }



    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    

    /**
     * TODO: A 2D translation matrix for the given offset vector
     * 
     * @param pos
     * @return
     */
    public static double[][] translationMatrix(double[] v) {
    	double[][] m = new double[dim][dim];
    	int k = 0;
    	for(int i = 0; i < dim; i++) {
    		for(int j = 0; j < dim; j++) {
    			//set diagonals to one
    			if(i == j) {
    				m[i][j] = 1;
    			//set last column to offset
    			} else if(j == dim-1) {
    				if(k < 2) {
    					m[i][j] = v[k];
        				k++;
    				} else {
    					m[i][j] = 1;
    				}
    				
    			//everything else is zero
    			} else {
    				m[i][j] = 0;
    			}
    		}
    	}
        return m;
    }

    /**
     * TODO: A 2D rotation matrix for the given angle
     * 
     * @param angle
     * @return
     */
    public static double[][] rotationMatrix(double angle) {
    	double[][] m = new double[dim][dim];
    	for(int i = 0; i < dim; i++) {
    		for(int j = 0; j < dim; j++) {
    			if(i == j) {
    				if(i != dim-1) {
    					m[i][j] = Math.cos(Math.toRadians(angle));
    				} else {
    					m[i][j] = 1;
    				}
    			} else if((i == 0) && (j == 1)) {
    				m[i][j] = -1 * Math.sin(Math.toRadians(angle));
    			} else if((i == 1) && (j == 0)) {
    				m[i][j] = Math.sin(Math.toRadians(angle));
    			} else {
    				m[i][j] = 0;
    			}
    		}
    	}

        return m;
    }

    /**
     * TODO: A 2D scale matrix that scales both axes by the same factor
     * 
     * @param scale
     * @return
     */
    public static double[][] scaleMatrix(double scale) {
    	double[][] m = new double[dim][dim];
    	
    	for(int i = 0; i < dim; i++) {
    		for(int j = 0; j < dim; j++) {
    			if(i == j) {
    				if(i != dim-1) {
    					m[i][j] = scale;
    				} else {
    					m[i][j] = 1;
    				}
    			} else {
    				m[i][j] = 0;
    			}
    		}
    	}
        return m;
    }

    /*
     * calculates the inverse of an affine matrix
     * 
     * @param m an affine matrix
     * @return the matrix inverse
     */
    public static double[][] affineInverse(double[][] m) {
    	double[][] mInv = new double[3][3];
    	double[] b = new double[2];
    	//the determinant of m
    	final double detM = m[0][0] * m[1][1] - m[0][1] * m[1][0];
    	
    	b[0] = -1 * m[0][2];
    	b[1] = -1 * m[1][2];
    	
    	//the top right corner of the inverse matrix is the inverse of the sub-matrix
    	mInv[0][0] = m[1][1] / detM;
    	mInv[0][1] = -1 * m[0][1] / detM;
    	mInv[1][0] = -1 * m[1][0] / detM;
    	mInv[1][1] = m[0][0] / detM;
    	
    	//fill in (most of) the last column of the inverse matrix
    	for(int i = 0; i < 2; i++) {
            mInv[i][2] = 0;
            for(int j = 0; j < 2; j++) {
                mInv[i][2] += mInv[i][j] * b[j];
            }
        }
    	
    	//add in the final row of the inverse matrix
    	for(int i = 0; i < 3; i++) {
    		mInv[2][i] = 0;
    		if(i == 2) {
    			mInv[2][i] = 1;
    		}
    	}
    	
    	return mInv;
    }
}
