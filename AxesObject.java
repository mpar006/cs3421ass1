package ass1;

import java.awt.BorderLayout;

import javax.media.opengl.GL2;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * A simple axes object for debugging the assignment.
 * 
 * This class implements a GameObject that draws a set of axes.
 * 
 * The x-axis is drawn as a red line of length 1 from (0,0) to (1,0)
 * The y-axis is drawn as a green line of length 1 from (0,0) to (0,1)
 *
 * @author malcolmr
 */
public class AxesObject extends GameObject {


    public AxesObject(GameObject parent) {
        super(parent);
    }

    @Override
    public void drawSelf(GL2 gl) {
        gl.glLineWidth(2);

        // draw the x-axis in red
        gl.glColor3d(1,0,0);
        gl.glBegin(GL2.GL_LINES);
        {
            gl.glVertex2d(0, 0);
            gl.glVertex2d(1, 0);
        }
        gl.glEnd();

        // draw the y-axis in green
        gl.glColor3d(0,1,0);
        gl.glBegin(GL2.GL_LINES);
        {
            gl.glVertex2d(0, 0);
            gl.glVertex2d(0, 1);
        }
        gl.glEnd();

    }


    /**
     * An example of how to use this class.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Initialise OpenGL
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        
        // create a GLJPanel to draw on
        GLJPanel panel = new GLJPanel(glcapabilities);

        // Create a camera
        Camera camera = new Camera(GameObject.ROOT);
        camera.setScale(2); // scale up the camera so we can see more of the world  
        
        // A set of axes showing the world coordinate frame 
        AxesObject axesWorld = new AxesObject(GameObject.ROOT);
        
        // A set of axes showing a transformed coordinate frame
        //AxesObject axesParent = new AxesObject(GameObject.ROOT);
        //axesParent.setPosition(1, 1);
        //axesParent.setRotation(45);
        //axesParent.setScale(0.5);

        // A set of axes that are a child of the above
        //AxesObject axesChild = new AxesObject(axesParent);
        //axesChild.setPosition(-2, -1);
        //axesChild.setRotation(-90);
        //axesChild.setScale(2);
        
        // Add the game engine
        GameEngine engine = new GameEngine(camera);
        panel.addGLEventListener(engine);

        // Add an animator to call 'display' at 60fps        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();

        // Put it in a window
        JFrame jFrame = new JFrame("Test");
        jFrame.getContentPane().add(panel, BorderLayout.CENTER);
        jFrame.setSize(400, 400);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
