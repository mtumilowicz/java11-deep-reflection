# java11-deep-reflection

Reference: https://www.amazon.com/Java-Language-Features-Modules-Expressions/dp/1484233476

# preface
* reading/writing fields and invoking methods/constructors
rise issues of access control
* for example, if you declare a field as private it should
  be accessible only in the class
* access control rules can be suppressed when we 
are using reflection
* **deep reflection** - accessing "inaccessible" members using
reflection
* used by Hibernate and Spring

# methods
* before `JDK9`: `setAccessible(true)`  invoked on 
`Field`/`Method`/`Constructor` was sufficient
* in the projects listed in [projects section](#projects) we will show how to handle 
deep reflection on the `JDK11`
* `Field`, `Method`, and `Constructor` all inherit from a 
class `AccessibleObject`

# AccessibleObject
* `public void setAccessible(boolean flag)` -
    set the accessible flag (`true` suppresses checks for Java language access control)
    * **cannot be used to enable access to `private` / `default (package)` / `protected` access
        when the declaring class is in a different module to the caller and the package 
        containing the declaring class is not open to the caller's module**
* `public static void setAccessible(AccessibleObject[] array, boolean flag)` - 
    convenience method to set the accessible flag for an array of reflected 
    objects with a single security check (for efficiency)
    * since 9
    * same specification concerning module-access as `setAccessible(boolean)`
    * `InaccessibleObjectException` if access cannot be enabled for all
     objects in the array
    * `SecurityException`:
        * if the request is denied by the security manager
        * if an element in the array is a constructor for `java.lang.Class`
            and the flag is `true`
* `public final boolean trySetAccessible()`
    * since 9
    * set the accessible flag to `true` if possible
    * if access cannot be enabled, i.e. the checks or Java language access control cannot
        be suppressed, this method returns false (as opposed to `setAccessible(true)` throwing 
        `InaccessibleObjectException` when it fails)
    * `SecurityException` if the request is denied by the security manager
* `public final boolean canAccess(Object obj)` - 
    test if the caller can access member of `obj`
    * since 9
    * `IllegalArgumentException` -
      * if static member or constructor and the given `obj` is `non-null`
      * if an instance method or field and the given `obj` is `null` or `this`
      is not a member of `obj`
        
# project description
We will provide tests for methods mentioned above. As a exemplary member
we will take private instance method and the default module (it's only
overview - more sophisticated examples are in [projects](#projects)):
```
class X {
    private void go() {

    }
}
```
* `setAccessible(boolean flag)`
    ```
    var methodGo = X.class.getDeclaredMethod("go");
    var x = new X();
    
    assertFalse(methodGo.canAccess(x));
    
    methodGo.setAccessible(true);
    
    assertTrue(methodGo.canAccess(x));
    assertTrue((boolean) methodGo.invoke(x));
    ```
* `setAccessible(AccessibleObject[] array, boolean flag)`
* `trySetAccessible()`
* `canAccess(Object obj)`
    
# projects
* https://github.com/mtumilowicz/java11-deep-reflection-in-module
* https://github.com/mtumilowicz/java11-deep-reflection-cross-modules
* https://github.com/mtumilowicz/java11-deep-reflection-unnamed-modules
