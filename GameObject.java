package ass1;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import javax.media.opengl.GL2;


/**
 * A GameObject is an object that can move around in the game world.
 * 
 * GameObjects form a scene tree. The root of the tree is the special ROOT object.
 * 
 * Each GameObject is offset from its parent by a rotation, a translation and a scale factor. 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class GameObject {

    // the list of all GameObjects in the scene tree
    public final static List<GameObject> ALL_OBJECTS = new ArrayList<GameObject>();
    
    // the root of the scene tree
    public final static GameObject ROOT = new GameObject();
    
    // the links in the scene tree
    private GameObject myParent;
    private List<GameObject> myChildren;

    // the local transformation
    private double myRotation;
    private double myScale;
    private double[] myTranslation;
    
    // is this part of the tree showing?
    private boolean amShowing;

    /**
     * Special private constructor for creating the root node. Do not use otherwise.
     */
    private GameObject() {
        myParent = null;
        myChildren = new ArrayList<GameObject>();

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        amShowing = true;
        
        ALL_OBJECTS.add(this);
    }

    /**
     * Public constructor for creating GameObjects, connected to a parent (possibly the ROOT).
     *  
     * New objects are created at the same location, orientation and scale as the parent.
     *
     * @param parent
     */
    public GameObject(GameObject parent) {
        myParent = parent;
        myChildren = new ArrayList<GameObject>();

        parent.myChildren.add(this);

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        // initially showing
        amShowing = true;

        ALL_OBJECTS.add(this);
    }

    /**
     * Remove an object and all its children from the scene tree.
     */
    public void destroy() {
        for (GameObject child : myChildren) {
            child.destroy();
        }
        
        myParent.myChildren.remove(this);
        ALL_OBJECTS.remove(this);
    }

    /**
     * Get the parent of this game object
     * 
     * @return
     */
    public GameObject getParent() {
        return myParent;
    }

    /**
     * Get the children of this object
     * 
     * @return
     */
    public List<GameObject> getChildren() {
        return myChildren;
    }

    /**
     * Get the local rotation (in degrees)
     * 
     * @return
     */
    public double getRotation() {
        return myRotation;
    }

    /**
     * Set the local rotation (in degrees)
     * 
     * @return
     */
    public void setRotation(double rotation) {
        myRotation = rotation;
    }

    /**
     * Rotate the object by the given angle (in degrees)
     * 
     * @param angle
     */
    public void rotate(double angle) {
        myRotation += angle;
    }

    /**
     * Get the local scale
     * 
     * @return
     */
    public double getScale() {
        return myScale;
    }

    /**
     * Set the local scale
     * 
     * @param scale
     */
    public void setScale(double scale) {
        myScale = scale;
    }

    /**
     * Multiply the scale of the object by the given factor
     * 
     * @param factor
     */
    public void scale(double factor) {
        myScale *= factor;
    }

    /**
     * Get the local position of the object 
     * 
     * @return
     */
    public double[] getPosition() {
        double[] t = new double[2];
        t[0] = myTranslation[0];
        t[1] = myTranslation[1];

        return t;
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
        myTranslation[0] = x;
        myTranslation[1] = y;
    }

    /**
     * Move the object by the specified offset in local coordinates
     * 
     * @param dx
     * @param dy
     */
    public void translate(double dx, double dy) {
        myTranslation[0] += dx;
        myTranslation[1] += dy;
    }

    /**
     * Test if the object is visible
     * 
     * @return
     */
    public boolean isShowing() {
        return amShowing;
    }

    /**
     * Set the showing flag to make the object visible (true) or invisible (false).
     * This flag should also apply to all descendents of this object.
     * 
     * @param showing
     */
    public void show(boolean showing) {
        amShowing = showing;
    }

    /**
     * Update the object. This method is called once per frame. 
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param dt The amount of time since the last update (in seconds)
     */
    public void update(double dt) {
        // do nothing
    }

    /**
     * Draw the object (but not any descendants)
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param gl
     */
    public void drawSelf(GL2 gl) {
        // do nothing
    }

    
    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    
    /**
     * Draw the object and all of its descendants recursively.
     * 
     * TODO: Complete this method
     * 
     * @param gl
     */
    public void draw(GL2 gl) {
        
        // don't draw if it is not showing
        if (!amShowing) {
            return;
        }

        // TODO: draw the object and all its children recursively
        // setting the model transform appropriately 
        if(!myChildren.iterator().hasNext()) {
        	this.drawSelf(gl);
        	return;
        } else {
        	gl.glPushMatrix();
        	{
        		double[] pos = this.getGlobalPosition();
        		gl.glTranslated(pos[0], pos[1], 0);
        		gl.glRotated(this.getGlobalRotation(), 0, 0, 1);
        		gl.glScaled(this.getGlobalScale(), this.getGlobalScale(), 1);
        	}
        	gl.glPopMatrix();
        	//draw(gl);
        }
    
        // Call drawSelf() to draw the object itself
        //this.draw(gl);
    }

    /**
     * Compute the model-view matrix 
     * 
     * @return a 3x3 matrix
     */
    private double[][] getModelViewMatrix() {
        double[][] rotation = MathUtil.rotationMatrix(myRotation);        
        double[][] translation = MathUtil.translationMatrix(myTranslation);        
        double[][] scale = MathUtil.scaleMatrix(myScale);
        double[][] m = MathUtil.multiply(translation, rotation);
        m = MathUtil.multiply(m, scale);
    	return m;
    }
    
    /**
     * Compute the object's position in world coordinates
     * 
     * @return a point in world coordinats in [x,y] form
     */
    public double[] getGlobalPosition() {
        double[] p = new double[2];
        double[][] m = getModelViewMatrix();
        if(myParent != null) {
        	m = MathUtil.multiply(myParent.getModelViewMatrix(), m);
        }
        
        p[0] = m[0][2];
        p[1] = m[1][2];
        
        return p; 
    }
    
    private void printMatrix(double[][] m) {
        for(int i = 0; i < 3; i++) {
        	System.out.println(m[i][0] + "," + m[i][1] + "," + m[i][2]);
        }
        System.out.println("");
    }

    /**
     * Compute the object's rotation in the global coordinate frame
     * 
     * @return the global rotation of the object (in degrees) 
     */
    public double getGlobalRotation() {
        double[][] m = getModelViewMatrix();
        if(myParent != null) {
        	m = MathUtil.multiply(myParent.getModelViewMatrix(), m);
        }
        
    	return Math.toDegrees(Math.atan2(m[1][0], m[0][0]));
    }

    /**
     * Compte the object's scale in global terms
     * 
     * @return the global scale of the object 
     */
    public double getGlobalScale() {
        double[][] m = getModelViewMatrix();
        if(myParent != null) {
        	m = MathUtil.multiply(myParent.getModelViewMatrix(), m);
        }
    	
    	return Math.sqrt(Math.pow(m[0][0],2) + Math.pow(m[1][0],2) + Math.pow(m[2][0],2));
    }

    /**
     * Change the parent of a game object.
     * 
     * @param parent
     */
    public void setParent(GameObject parent) {
        double[][] mParent = new double[3][3];
        double[][] mChild = new double[3][3];
        myParent.myChildren.remove(this);
        myParent = parent;
        
        //change local information
        mParent = myParent.getModelViewMatrix();
        mChild = getModelViewMatrix();
        mParent = MathUtil.affineInverse(mParent);
        mChild = MathUtil.multiply(mParent, mChild);
        
        //update child information
        this.setPosition(mChild[0][2], mChild[1][2]);
        this.setRotation(Math.toDegrees(Math.atan2(mChild[1][0],mChild[0][0])));
        this.setScale(Math.sqrt(Math.pow(mChild[0][0],2) + Math.pow(mChild[1][0],2) + Math.pow(mChild[2][0],2)));
        
        myParent.myChildren.add(this);  
    }
    

}
