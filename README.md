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
    * `static void setAccessible(AccessibleObject[] array, boolean flag)`
    - Convenience method to set the accessible flag for an array of reflected 
    objects with a single security check (for efficiency)
        * `array` the array of `AccessibleObjects`
        * `flag` the new value for the accessible flag
              in each object
        * exceptions:
            * `InaccessibleObjectException` if access cannot be enabled for all
             objects in the array
            * `SecurityException` if the request is denied by the security manager
             or an element in the array is a constructor for `java.lang.Class`
             and the flag is `true`
    * void setAccessible(boolean flag)
    * boolean trySetAccessible()
    * boolean canAccess(Object obj)

# projects
* https://github.com/mtumilowicz/java11-deep-reflection-in-module
* https://github.com/mtumilowicz/java11-deep-reflection-cross-modules
* https://github.com/mtumilowicz/java11-deep-reflection-unnamed-modules
