package com.ruoyi.common.core.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Set;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Type of Converter
 *
 * @author ruoyi
 */
public class Convert
{
    /**
     * Convert to string.<br>
     * If the value is givennull，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static String toStr(Object value, String defaultValue)
    {
        if (null == value)
        {
            return defaultValue;
        }
        if (value instanceof String)
        {
            return (String) value;
        }
        return value.toString();
    }

    /**
     * Convert to string.<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static String toStr(Object value)
    {
        return toStr(value, null);
    }

    /**
     * Convert to characters.<br>
     * If the value is givennull，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Character toChar(Object value, Character defaultValue)
    {
        if (null == value)
        {
            return defaultValue;
        }
        if (value instanceof Character)
        {
            return (Character) value;
        }

        final String valueStr = toStr(value, null);
        return StringUtils.isEmpty(valueStr) ? defaultValue : valueStr.charAt(0);
    }

    /**
     * Convert to characters.<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Character toChar(Object value)
    {
        return toChar(value, null);
    }

    /**
     * Convert tobyte<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Byte toByte(Object value, Byte defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Byte)
        {
            return (Byte) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).byteValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Byte.parseByte(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert tobyte<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Byte toByte(Object value)
    {
        return toByte(value, null);
    }

    /**
     * Convert toShort<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Short toShort(Object value, Short defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Short)
        {
            return (Short) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).shortValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Short.parseShort(valueStr.trim());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert toShort<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Short toShort(Object value)
    {
        return toShort(value, null);
    }

    /**
     * Convert toNumber<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Number toNumber(Object value, Number defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Number)
        {
            return (Number) value;
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return NumberFormat.getInstance().parse(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert toNumber<br>
     * If the value is empty.，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Number toNumber(Object value)
    {
        return toNumber(value, null);
    }

    /**
     * Convert toint<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Integer toInt(Object value, Integer defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Integer)
        {
            return (Integer) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).intValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(valueStr.trim());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert toint<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Integer toInt(Object value)
    {
        return toInt(value, null);
    }

    /**
     * Convert toIntegerNumber of groups<br>
     *
     * @param str The value converted.
     * @return Results
     */
    public static Integer[] toIntArray(String str)
    {
        return toIntArray(",", str);
    }

    /**
     * Convert toLongNumber of groups<br>
     *
     * @param str The value converted.
     * @return Results
     */
    public static Long[] toLongArray(String str)
    {
        return toLongArray(",", str);
    }

    /**
     * Convert toIntegerNumber of groups<br>
     *
     * @param split Separation
     * @param split The value converted.
     * @return Results
     */
    public static Integer[] toIntArray(String split, String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return new Integer[] {};
        }
        String[] arr = str.split(split);
        final Integer[] ints = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            final Integer v = toInt(arr[i], 0);
            ints[i] = v;
        }
        return ints;
    }

    /**
     * Convert toLongNumber of groups<br>
     *
     * @param split Separation
     * @param str The value converted.
     * @return Results
     */
    public static Long[] toLongArray(String split, String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return new Long[] {};
        }
        String[] arr = str.split(split);
        final Long[] longs = new Long[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            final Long v = toLong(arr[i], null);
            longs[i] = v;
        }
        return longs;
    }

    /**
     * Convert toStringNumber of groups<br>
     *
     * @param str The value converted.
     * @return Results
     */
    public static String[] toStrArray(String str)
    {
        return toStrArray(",", str);
    }

    /**
     * Convert toStringNumber of groups<br>
     *
     * @param split Separation
     * @param split The value converted.
     * @return Results
     */
    public static String[] toStrArray(String split, String str)
    {
        return str.split(split);
    }

    /**
     * Convert tolong<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Long toLong(Object value, Long defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Long)
        {
            return (Long) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).longValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            // Supporting the Scientific Computing Law
            return new BigDecimal(valueStr.trim()).longValue();
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert tolong<br>
     * If the value is given<code>null</code>，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Long toLong(Object value)
    {
        return toLong(value, null);
    }

    /**
     * Convert todouble<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Double toDouble(Object value, Double defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Double)
        {
            return (Double) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).doubleValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            // Supporting the Scientific Computing Law
            return new BigDecimal(valueStr.trim()).doubleValue();
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert todouble<br>
     * If the value is empty.，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Double toDouble(Object value)
    {
        return toDouble(value, null);
    }

    /**
     * Convert toFloat<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Float toFloat(Object value, Float defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Float)
        {
            return (Float) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).floatValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Float.parseFloat(valueStr.trim());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert toFloat<br>
     * If the value is empty.，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Float toFloat(Object value)
    {
        return toFloat(value, null);
    }

    /**
     * Convert toboolean<br>
     * StringThe value of support is：true、false、yes、ok、no，1,0 If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static Boolean toBool(Object value, Boolean defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Boolean)
        {
            return (Boolean) value;
        }
        String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        valueStr = valueStr.trim().toLowerCase();
        switch (valueStr)
        {
            case "true":
            case "yes":
            case "ok":
            case "1":
                return true;
            case "false":
            case "no":
            case "0":
                return false;
            default:
                return defaultValue;
        }
    }

    /**
     * Convert toboolean<br>
     * If the value is empty.，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static Boolean toBool(Object value)
    {
        return toBool(value, null);
    }

    /**
     * Convert toEnumObjects<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     *
     * @param clazz EnumofClass
     * @param value Value
     * @param defaultValue The default value
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value, E defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (clazz.isAssignableFrom(value.getClass()))
        {
            @SuppressWarnings("unchecked")
            E myE = (E) value;
            return myE;
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Enum.valueOf(clazz, valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert toEnumObjects<br>
     * If the value is empty.，or conversion failure.，Return the default value.<code>null</code><br>
     *
     * @param clazz EnumofClass
     * @param value Value
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value)
    {
        return toEnum(clazz, value, null);
    }

    /**
     * Convert toBigInteger<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static BigInteger toBigInteger(Object value, BigInteger defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof BigInteger)
        {
            return (BigInteger) value;
        }
        if (value instanceof Long)
        {
            return BigInteger.valueOf((Long) value);
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return new BigInteger(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert toBigInteger<br>
     * If the value is empty.，or conversion failure.，Return the default value.<code>null</code><br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static BigInteger toBigInteger(Object value)
    {
        return toBigInteger(value, null);
    }

    /**
     * Convert toBigDecimal<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @param defaultValue The default value when converting errors
     * @return Results
     */
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof BigDecimal)
        {
            return (BigDecimal) value;
        }
        if (value instanceof Long)
        {
            return new BigDecimal((Long) value);
        }
        if (value instanceof Double)
        {
            return BigDecimal.valueOf((Double) value);
        }
        if (value instanceof Integer)
        {
            return new BigDecimal((Integer) value);
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return new BigDecimal(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * Convert toBigDecimal<br>
     * If the value is empty.，or conversion failure.，Return the default value.<br>
     * Conversion failure will not be mistaken.
     *
     * @param value The value converted.
     * @return Results
     */
    public static BigDecimal toBigDecimal(Object value)
    {
        return toBigDecimal(value, null);
    }

    /**
     * Convert the object into a string.<br>
     * 1、ByteThe number andByteBufferThe number will be converted into the corresponding string. 2、The number of objects will be called.Arrays.toStringMethod
     *
     * @param obj Objects
     * @return The characters.
     */
    public static String utf8Str(Object obj)
    {
        return str(obj, CharsetKit.CHARSET_UTF_8);
    }

    /**
     * Convert the object into a string.<br>
     * 1、ByteThe number andByteBufferThe number will be converted into the corresponding string. 2、The number of objects will be called.Arrays.toStringMethod
     *
     * @param obj Objects
     * @param charsetName Collection of characters
     * @return The characters.
     */
    public static String str(Object obj, String charsetName)
    {
        return str(obj, Charset.forName(charsetName));
    }

    /**
     * Convert the object into a string.<br>
     * 1、ByteThe number andByteBufferThe number will be converted into the corresponding string. 2、The number of objects will be called.Arrays.toStringMethod
     *
     * @param obj Objects
     * @param charset Collection of characters
     * @return The characters.
     */
    public static String str(Object obj, Charset charset)
    {
        if (null == obj)
        {
            return null;
        }

        if (obj instanceof String)
        {
            return (String) obj;
        }
        else if (obj instanceof byte[])
        {
            return str((byte[]) obj, charset);
        }
        else if (obj instanceof Byte[])
        {
            byte[] bytes = ArrayUtils.toPrimitive((Byte[]) obj);
            return str(bytes, charset);
        }
        else if (obj instanceof ByteBuffer)
        {
            return str((ByteBuffer) obj, charset);
        }
        return obj.toString();
    }

    /**
     * willbyteConvert numbers into string.
     *
     * @param bytes byteNumber of groups
     * @param charset Collection of characters
     * @return The characters.
     */
    public static String str(byte[] bytes, String charset)
    {
        return str(bytes, StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * Decoding the code.
     *
     * @param data The characters.
     * @param charset Collection of characters，If this field is empty.，The result depends on the platform.
     * @return Posts after decoding.
     */
    public static String str(byte[] data, Charset charset)
    {
        if (data == null)
        {
            return null;
        }

        if (null == charset)
        {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * will be coded.byteBufferConvert data into string.
     *
     * @param data The data
     * @param charset Collection of characters，If you use the current system character set for free
     * @return The characters.
     */
    public static String str(ByteBuffer data, String charset)
    {
        if (data == null)
        {
            return null;
        }

        return str(data, Charset.forName(charset));
    }

    /**
     * will be coded.byteBufferConvert data into string.
     *
     * @param data The data
     * @param charset Collection of characters，If you use the current system character set for free
     * @return The characters.
     */
    public static String str(ByteBuffer data, Charset charset)
    {
        if (null == charset)
        {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    // ----------------------------------------------------------------------- Transition of the whole corner.
    /**
     * Half the angle.
     *
     * @param input String.
     * @return The whole corner..
     */
    public static String toSBC(String input)
    {
        return toSBC(input, null);
    }

    /**
     * Half the angle.
     *
     * @param input String
     * @param notConvertSet Unchanged collection of characters.
     * @return The whole corner..
     */
    public static String toSBC(String input, Set<Character> notConvertSet)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (null != notConvertSet && notConvertSet.contains(c[i]))
            {
                // The unchanged characters.
                continue;
            }

            if (c[i] == ' ')
            {
                c[i] = '\u3000';
            }
            else if (c[i] < '\177')
            {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * Around half a corner.
     *
     * @param input String.
     * @return Half corner string.
     */
    public static String toDBC(String input)
    {
        return toDBC(input, null);
    }

    /**
     * Replace the whole corner to half corner.
     *
     * @param text The text
     * @param notConvertSet Unchanged collection of characters.
     * @return Character after replacement.
     */
    public static String toDBC(String text, Set<Character> notConvertSet)
    {
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (null != notConvertSet && notConvertSet.contains(c[i]))
            {
                // The unchanged characters.
                continue;
            }

            if (c[i] == '\u3000')
            {
                c[i] = ' ';
            }
            else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
            {
                c[i] = (char) (c[i] - 65248);
            }
        }
        String returnString = new String(c);

        return returnString;
    }

    /**
     * Conversion of digital amounts Write a full one first and then replace it as zero.
     *
     * @param n The numbers
     * @return A Chinese number.
     */
    public static String digitUppercase(double n)
    {
        String[] fraction = { "The corner", "Parts" };
        String[] digit = { "0 is", "The", "", "", "and the", "Woo", "Land", "", "", "" };
        String[][] unit = { { "by", "Thousand", "Millions" }, { "", "picked up", "", "" } };

        String head = n < 0 ? "The negative" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++)
        {
            // optimizeddoubleLost accuracy problem.
            BigDecimal nNum = new BigDecimal(n);
            BigDecimal decimal = new BigDecimal(10);
            BigDecimal scale = nNum.multiply(decimal).setScale(2, RoundingMode.HALF_EVEN);
            double d = scale.doubleValue();
            s += (digit[(int) (Math.floor(d * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(0 is.)+", "");
        }
        if (s.length() < 1)
        {
            s = "The whole";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++)
        {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++)
            {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(0 is.)*0 is$", "").replaceAll("^$", "0 is") + unit[0][i] + s;
        }
        return head + s.replaceAll("(0 is.)*by zero.", "by").replaceFirst("(0 is.)+", "").replaceAll("(0 is.)+", "0 is").replaceAll("^The whole$", "The zero.");
    }
}
