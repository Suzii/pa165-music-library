
package cz.muni.fi.pa165.musiclib.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Inspired by http://solutiondesign.com/blog/-/blogs/protecting-your-hibernate-i-1/
 *
 * @author Zuzana Dankovcikova
 * @version 15/10/28
 */
public class SetIdHelper {
  
    public static void setId(Object persistentObject, Long id) {
        try {
            Method setIdMethod = findMethod(persistentObject.getClass(),"setId");
            setIdMethod.setAccessible(true);
            setIdMethod.invoke(persistentObject, id);
        }
        catch (IllegalAccessException|InvocationTargetException e) {
            System.err.println(e);
        }
    }
  
    private static Method findMethod(Class entityClass, String methodName) {
  
        Method method = null;
        while (entityClass != null && method == null) {
            try {
                method = entityClass.getDeclaredMethod(methodName,Long.class);
            }
            catch (NoSuchMethodException e) {
                System.err.println(e);
            }finally{
                entityClass = entityClass.getSuperclass();
            }
        }
  
        return method;
    }
}