package ass1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * The camera is a GameObject that can be moved, rotated and scaled like any other.
 * 
 * TODO: You need to implment the setView() and reshape() methods.
 *       The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class Camera extends GameObject {

    private float[] myBackground;

    public Camera(GameObject parent) {
        super(parent);

        myBackground = new float[4];
    }

    public Camera() {
        this(GameObject.ROOT);
    }
    
    public float[] getBackground() {
        return myBackground;
    }

    public void setBackground(float[] background) {
        myBackground = background;
    }

    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
   
    
    public void setView(GL2 gl) {
        double[] pos = new double[2];
        // TODO 1. clear the view to the background colour
        gl.glClearColor(myBackground[0], myBackground[1], myBackground[2], myBackground[3]);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        // TODO 2. set the view matrix to account for the camera's position
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    	gl.glScaled(1 / this.getGlobalScale(), 1 / this.getGlobalScale(), 1);
    	gl.glRotated(-1 * this.getGlobalRotation(), 0, 0, 1);
    	
    	pos = this.getGlobalPosition();
    	gl.glTranslated(-pos[0], -pos[1], 0);
    }

    public void reshape(GL2 gl, int x, int y, int width, int height) {
        // TODO  1. match the projection aspect ratio to the viewport
        // to avoid stretching
    	gl.glMatrixMode(GL2.GL_PROJECTION);
    	gl.glLoadIdentity();
        
    	double aspect = (1.0 * width) / height;
    	GLU glu = new GLU();
    	glu.gluOrtho2D(-aspect, aspect, -1.0, 1.0);
    }
}
