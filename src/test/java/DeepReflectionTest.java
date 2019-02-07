import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2019-02-07.
 */
public class DeepReflectionTest {

    @Test
    public void setAccessible() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var methodGo = X.class.getDeclaredMethod("go");
        var x = new X();

        assertFalse(methodGo.canAccess(x));

        methodGo.setAccessible(true);

        assertTrue(methodGo.canAccess(x));
        assertTrue((boolean) methodGo.invoke(x));
    }
    
}
