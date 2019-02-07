import org.junit.Test;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2019-02-07.
 */
public class DeepReflectionTest {

    @Test
    public void setAccessible_single() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        var methodGo = X.class.getDeclaredMethod("go");
        var x = new X();
        
        // then
        assertFalse(methodGo.canAccess(x));
        
        // when
        methodGo.setAccessible(true);
        
        // then
        assertTrue(methodGo.canAccess(x));
        assertTrue((boolean) methodGo.invoke(x));
    }

    @Test
    public void setAccessible_objects() throws NoSuchMethodException {
        // given
        var methodGo = X.class.getDeclaredMethod("go");
        var methodGo2 = X.class.getDeclaredMethod("go2");
        var x = new X();
        Predicate<AccessibleObject> canAccess = obj -> obj.canAccess(x);
        
        // and
        AccessibleObject[] methods = {methodGo, methodGo2};
        
        // when
        AccessibleObject.setAccessible(methods, true);
        
        // then
        assertTrue(Arrays.stream(methods).allMatch(canAccess));
    }
    
    @Test
    public void trySetAccessible() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        var methodGo = X.class.getDeclaredMethod("go");
        var x = new X();

        // then
        assertFalse(methodGo.canAccess(x));

        // when
        assertTrue(methodGo.trySetAccessible());

        // then
        assertTrue(methodGo.canAccess(x));
        assertTrue((boolean) methodGo.invoke(x));
    }
    
    @Test
    public void canAccess() throws NoSuchMethodException {
        // given
        var methodGo = X.class.getDeclaredMethod("go");
        var x = new X();
        
        // expect
        assertFalse(methodGo.canAccess(x));
    }

    @Test(expected = IllegalArgumentException.class)
    public void canAccess_methodGo_isNotMemberOf() throws NoSuchMethodException {
        // given
        var methodGo = X.class.getDeclaredMethod("go");

        // expect
        assertFalse(methodGo.canAccess(new Y()));
    }
}
