package ass1;

import org.junit.Test;

import ass1.GameObject;

import junit.framework.TestCase;

/**
 * COMMENT: Comment GameObjectTest 
 *
 * @author malcolmr
 */
public class GameObjectTest extends TestCase {

    // to account for rounding errors on doubles, we will
    // test to within epsilon of the correct answer:
 
    private static final double EPSILON = 0.001;
    
    @Test
    public void testGlobal0() {
        GameObject obj = new GameObject(GameObject.ROOT);
        
        double[] p = obj.getGlobalPosition();
        double r = obj.getGlobalRotation();
        double s = obj.getGlobalScale();
        
        assertEquals(0, p[0], EPSILON);
        assertEquals(0, p[1], EPSILON);
        assertEquals(0, r, EPSILON);
        assertEquals(1, s, EPSILON);
    }
    
    @Test
    public void testGlobal1() {
        GameObject obj = new GameObject(GameObject.ROOT);
        
        obj.translate(-2, 3);
        obj.rotate(90);
        obj.scale(2);

        double[] p = obj.getGlobalPosition();
        double r = obj.getGlobalRotation();
        double s = obj.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(3, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);
    }

    @Test
    public void testGlobal2() {
        GameObject parent = new GameObject(GameObject.ROOT);
        GameObject child = new GameObject(parent);
        
        parent.translate(-2, 3);
        parent.rotate(90);
        parent.scale(2);
        
        // the child is also moved:
        
        double[] p = child.getGlobalPosition();
        double r = child.getGlobalRotation();
        double s = child.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(3, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);        
        
        // now move the child in its new coordinate frame
        
        child.translate(1, 0);
        child.rotate(-90);
        child.scale(0.5);

        p = child.getGlobalPosition();
        r = child.getGlobalRotation();
        s = child.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(5, p[1], EPSILON);
        assertEquals(0, r, EPSILON);
        assertEquals(1, s, EPSILON);

        // the parent is not affected
        
        p = parent.getGlobalPosition();
        r = parent.getGlobalRotation();
        s = parent.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(3, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);

    }

    @Test
    public void testSetParent0() {
        GameObject obj1 = new GameObject(GameObject.ROOT);
        GameObject obj2 = new GameObject(GameObject.ROOT);
        
        assertSame(GameObject.ROOT, obj1.getParent());
        assertTrue(obj1.getChildren().isEmpty());
        
        assertSame(GameObject.ROOT, obj2.getParent());
        assertTrue(obj1.getChildren().isEmpty());
        
        obj1.translate(1, 1);
        obj1.rotate(90);
        obj1.scale(2);
        
        obj2.setParent(obj1);

        // obj2's global coordinate frame should not be changed
        
        double[] p = obj2.getGlobalPosition();
        double r = obj2.getGlobalRotation();
        double s = obj2.getGlobalScale();

        assertEquals(0, p[0], EPSILON);
        assertEquals(0, p[1], EPSILON);
        assertEquals(0, r, EPSILON);
        assertEquals(1, s, EPSILON);        

        // obj2's local coordinate frame is adjusted to suit
        
        p = obj2.getPosition();
        r = obj2.getRotation();
        s = obj2.getScale();
        
        assertEquals(-0.5, p[0], EPSILON);
        assertEquals(0.5, p[1], EPSILON);
        assertEquals(-90, r, EPSILON);
        assertEquals(0.5, s, EPSILON);        

        // obj1's local coordinate frame is not affected
        
        p = obj1.getPosition();
        r = obj1.getRotation();
        s = obj1.getScale();
        
        assertEquals(1, p[0], EPSILON);
        assertEquals(1, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);        

    }
    
}