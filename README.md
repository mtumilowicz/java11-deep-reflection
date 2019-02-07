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
* before `JDK9`: call `setAccessible(true)` on 
`Field`/`Method`/`Constructor` was sufficient
* in projects listed in (projects section) we will show how to handle 
deep reflection on the `JDK11`
* `Field`, `Method`, and `Constructor` all inherit from a 
class `AccessibleObject`:
    * `public void setAccessible(boolean flag)` -
        Set the accessible flag for this reflected object to the indicated boolean 
        value. A value of true indicates that the reflected object should suppress 
        checks for Java language access control when it is used.
        * modules - method may be used by a caller in class `C` to enable access 
        to a member of declaring class `D`:
            * `C` and `D` are in the same module.
            * The member is public and `D` is public in a package that the module 
                containing `D` exports to at least the module containing `C`.
            * The member is protected static, `D` is public in a package that the 
                module containing `D` exports to at least the module containing `C`, 
                and `C` is a subclass of `D`.
            * `D` is in a package that the module containing `D` opens to at least the 
                module containing `C`. All packages in unnamed and open modules are open 
                to all modules and so this method always succeeds when `D` is in an 
                unnamed or open module.
    * `public static void setAccessible(AccessibleObject[] array, boolean flag)` - 
    Convenience method to set the accessible flag for an array of reflected 
    objects with a single security check (for efficiency)
        * since 9
        * access to each reflected object can be enabled as specified by `setAccessible(boolean)`.
        * `array` the array of `AccessibleObjects`
        * `flag` the new value for the accessible flag
              in each object
        * exceptions:
            * `InaccessibleObjectException` if access cannot be enabled for all
             objects in the array
            * `SecurityException` if the request is denied by the security manager
             or an element in the array is a constructor for `java.lang.Class`
             and the flag is `true`
    * `public boolean trySetAccessible()`
        * since 9
        * set the accessible flag for this reflected object to true
            if possible
        * if access cannot be enabled, i.e. the checks or Java language access control cannot
            be suppressed, this method returns false (as opposed to `setAccessible(true)` throwing 
            `InaccessibleObjectException` when it fails
        * exceptions:
            * `SecurityException` if the request is denied by the security manager
    * `public boolean canAccess(Object obj)`
# projects
* https://github.com/mtumilowicz/java11-deep-reflection-in-module
* https://github.com/mtumilowicz/java11-deep-reflection-cross-modules
* https://github.com/mtumilowicz/java11-deep-reflection-unnamed-modules
