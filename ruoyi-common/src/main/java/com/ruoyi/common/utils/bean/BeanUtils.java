package com.ruoyi.common.utils.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean Class of tools
 * 
 * @author ruoyi
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /** BeanIn the method name the character name begins. */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * matchedgetterExpression of the method. */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * matchedsetterExpression of the method. */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * BeanProperity Copy Tool Method。
     * 
     * @param dest The target objects.
     * @param src Source Objects
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * obtaining objects.setterMethod。
     * 
     * @param obj Objects
     * @return the object.setterMethod List
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // setterMethod List
        List<Method> setterMethods = new ArrayList<Method>();

        // Get all methods.
        Method[] methods = obj.getClass().getMethods();

        // Find outsetterMethod

        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }
        // Return tosetterMethod List
        return setterMethods;
    }

    /**
     * obtaining objects.getterMethod。
     * 
     * @param obj Objects
     * @return the object.getterMethod List
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // getterMethod List
        List<Method> getterMethods = new ArrayList<Method>();
        // Get all methods.
        Method[] methods = obj.getClass().getMethods();
        // Find outgetterMethod
        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }
        // Return togetterMethod List
        return getterMethods;
    }

    /**
     * checkedBeanIs the character name in the method name equal?。<br>
     * asgetName()andsetName()Like the name.，getName()andsetAge()The name is different.。
     * 
     * @param m1 Method Name1
     * @param m2 Method Name2
     * @return Return as the name.true，Otherwise return.false
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
}
