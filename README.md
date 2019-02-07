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
class `AccessibleObject`:
    * `public void setAccessible(boolean flag)` -
        set the accessible flag (`true` suppresses checks for Java language access control)
        * cannot be used to enable access to private / default (package) / protected access
            when the declaring class is in a different module to the caller and the package 
            containing the declaring class is not open to the caller's module
    * `public static void setAccessible(AccessibleObject[] array, boolean flag)` - 
        convenience method to set the accessible flag for an array of reflected 
        objects with a single security check (for efficiency)
        * since 9
        * specified by `setAccessible(boolean)`
        * exceptions:
            * `InaccessibleObjectException` if access cannot be enabled for all
             objects in the array
            * `SecurityException`:
                * if the request is denied by the security manager
                * if an element in the array is a constructor for `java.lang.Class`
                    and the flag is `true`
    * `public final boolean trySetAccessible()`
        * since 9
        * set the accessible flag to true if possible
        * if access cannot be enabled, i.e. the checks or Java language access control cannot
            be suppressed, this method returns false (as opposed to `setAccessible(true)` throwing 
            `InaccessibleObjectException` when it fails)
        * exceptions:
            * `SecurityException` if the request is denied by the security manager
    * `public final boolean canAccess(Object obj)` - 
        test if the caller can access reflected object
        * since 9
        * `obj` - ????????
        * `IllegalArgumentException` -
          * if reflected object is a static member or constructor and the given `obj` is `non-null`
          * if reflected object is an instance method or field and the given `obj` is `null` 
          or of type that is not a subclass of ???????????????????????.
            
    
# projects
* https://github.com/mtumilowicz/java11-deep-reflection-in-module
* https://github.com/mtumilowicz/java11-deep-reflection-cross-modules
* https://github.com/mtumilowicz/java11-deep-reflection-unnamed-modules
