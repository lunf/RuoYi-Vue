package com.ruoyi.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.AntPathMatcher;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.text.StrFormatter;

/**
 * Type of string tools.
 * 
 * @author ruoyi
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils
{
    /** The empty string. */
    private static final String NULLSTR = "";

    /** The line down. */
    private static final char SEPARATOR = '_';

    /** The Star */
    private static final char ASTERISK = '*';

    /**
     * Parameters are not empty.
     * 
     * @param value defaultValue to judge.value
     * @return value Return of value.
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * * Judge one.Collectionis empty.， IncludedList，Set，Queue
     * 
     * @param coll to judge.Collection
     * @return true：for empty false：not empty
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * Judge one.Collectionis not empty.，IncludedList，Set，Queue
     * 
     * @param coll to judge.Collection
     * @return true：not empty false：empty
     */
    public static boolean isNotEmpty(Collection<?> coll)
    {
        return !isEmpty(coll);
    }

    /**
     * * To determine whether an object group is empty.
     * 
     * @param objects The number of objects to be judged
     ** @return true：for empty false：not empty
     */
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * To determine whether an object group is not empty.
     * 
     * @param objects The number of objects to be judged
     * @return true：not empty false：empty
     */
    public static boolean isNotEmpty(Object[] objects)
    {
        return !isEmpty(objects);
    }

    /**
     * * Judge one.Mapis empty.
     * 
     * @param map to judge.Map
     * @return true：for empty false：not empty
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * Judge one.Mapis empty.
     * 
     * @param map to judge.Map
     * @return true：not empty false：empty
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    /**
     * * Decide whether a string is empty.
     * 
     * @param str String
     * @return true：for empty false：not empty
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * Determine whether a string is not empty.
     * 
     * @param str String
     * @return true：Not empty. false：The empty
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    /**
     * * To determine whether an object is empty.
     * 
     * @param object Object
     * @return true：for empty false：not empty
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * To determine whether an object is empty.
     * 
     * @param object Object
     * @return true：not empty false：empty
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * * To determine whether an object is a group type.（JavaBasic types of groups.）
     * 
     * @param object Objects
     * @return true：It is a group. false：Not a group.
     */
    public static boolean isArray(Object object)
    {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * Go to space.
     */
    public static String trim(String str)
    {
        return (str == null ? "" : str.trim());
    }

    /**
     * Replace the character within the specified range of the specified string to"*"
     *
     * @param str The characters.
     * @param startInclude Start the location.（Included）
     * @param endExclude End of location.（Not included）
     * @return Replacement of characters.
     */
    public static String hide(CharSequence str, int startInclude, int endExclude)
    {
        if (isEmpty(str))
        {
            return NULLSTR;
        }
        final int strLength = str.length();
        if (startInclude > strLength)
        {
            return NULLSTR;
        }
        if (endExclude > strLength)
        {
            endExclude = strLength;
        }
        if (startInclude > endExclude)
        {
            // The starting position is greater than the end position.，not replaced.
            return NULLSTR;
        }
        final char[] chars = new char[strLength];
        for (int i = 0; i < strLength; i++)
        {
            if (i >= startInclude && i < endExclude)
            {
                chars[i] = ASTERISK;
            }
            else
            {
                chars[i] = str.charAt(i);
            }
        }
        return new String(chars);
    }

    /**
     * Cut the string.
     * 
     * @param str The characters.
     * @param start Started
     * @return Results
     */
    public static String substring(final String str, int start)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = str.length() + start;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length())
        {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * Cut the string.
     * 
     * @param str The characters.
     * @param start Started
     * @param end ended
     * @return Results
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * To determine whether it is empty.，Not empty characters.
     * 
     * @param str to judge.value
     * @return Results
     */
    public static boolean hasText(String str)
    {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str)
    {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Formated text, {} indicate the position.<br>
     * This method is simple to take place. {} Replacement of parameters according to order.<br>
     * If you want out. {} Use of \\transition { can be，If you want out. {} The previous \ Use of Double Conversion \\\\ can be<br>
     * Examples：<br>
     * Usually used：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * transition{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * transition\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     * 
     * @param template Text template，Replaced parts. {} stated
     * @param params The parameter value
     * @return Text after formatting.
     */
    public static String format(String template, Object... params)
    {
        if (isEmpty(params) || isEmpty(template))
        {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * Is it forhttp(s)://Beginning
     * 
     * @param link linked
     * @return Results
     */
    public static boolean ishttp(String link)
    {
        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
    }

    /**
     * The characters turn.set
     * 
     * @param str The characters.
     * @param sep Separation
     * @return setThe gathering
     */
    public static final Set<String> str2Set(String str, String sep)
    {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * The characters turn.list
     * 
     * @param str The characters.
     * @param sep Separation
     * @param filterBlank Filtering pure empty.
     * @param trim Remove the empty end.
     * @return listThe gathering
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim)
    {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str))
        {
            return list;
        }

        // Filter the empty string.
        if (filterBlank && StringUtils.isBlank(str))
        {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split)
        {
            if (filterBlank && StringUtils.isBlank(string))
            {
                continue;
            }
            if (trim)
            {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * judgment given.collectionDoes the list contain numbers?array Assess the number given.arrayIt contains a given element.value
     *
     * @param collection The given collection.
     * @param array The given number.
     * @return boolean Results
     */
    public static boolean containsAny(Collection<String> collection, String... array)
    {
        if (isEmpty(collection) || isEmpty(array))
        {
            return false;
        }
        else
        {
            for (String str : array)
            {
                if (collection.contains(str))
                {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Find out whether a specified string contains any string in a specified string list while a string ignores the size of the letter.
     *
     * @param cs Identifying the string.
     * @param searchCharSequences Number of characters to be checked.
     * @return It contains an arbitrary string.
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences)
    {
        if (isEmpty(cs) || isEmpty(searchCharSequences))
        {
            return false;
        }
        for (CharSequence testStr : searchCharSequences)
        {
            if (containsIgnoreCase(cs, testStr))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Turn down the line name.
     */
    public static String toUnderScoreCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // Are the front characters written?
        boolean preCharIsUpperCase = true;
        // Current characters are written.
        boolean curreCharIsUpperCase = true;
        // The next character is written.
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * It contains a string.
     * 
     * @param str Verify the string.
     * @param strs group of characters.
     * @return Included in Returntrue
     */
    public static boolean inStringIgnoreCase(String str, String... strs)
    {
        if (str != null && strs != null)
        {
            for (String s : strs)
            {
                if (str.equalsIgnoreCase(trim(s)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Convert the line of characters named the line-down way of writing to the hole.。If the string named in the form of translation before conversion is empty.，Returning the empty string.。 for example：HELLO_WORLD->HelloWorld
     * 
     * @param name A string of characters named in the way of writing before conversion.
     * @return Character names after conversion.
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();
        // Fast inspection
        if (name == null || name.isEmpty())
        {
            // No need to convert.
            return "";
        }
        else if (!name.contains("_"))
        {
            // Not underline.，Just write the first letter.
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // Use the line to divide the original string.
        String[] camels = name.split("_");
        for (String camel : camels)
        {
            // Starting from the original string.、End of the downline or double downline.
            if (camel.isEmpty())
            {
                continue;
            }
            // The first letter is written.
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * The top name law.
     * for example：user_name->userName
     */
    public static String toCamelCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        if (s.indexOf(SEPARATOR) == -1)
        {
            return s;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == SEPARATOR)
            {
                upperCase = true;
            }
            else if (upperCase)
            {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Find out if the specified string matches any string in the specified string list.
     * 
     * @param str Identifying the string.
     * @param strs Number of characters to be checked.
     * @return is matching.
     */
    public static boolean matches(String str, List<String> strs)
    {
        if (isEmpty(str) || isEmpty(strs))
        {
            return false;
        }
        for (String pattern : strs)
        {
            if (isMatch(pattern, str))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * judgmenturlConfiguration with the rules.: 
     * ? Showing individual characters.; 
     * * Show an arbitrary string within a layer of route.，Not to cross levels.; 
     * ** indicate the arbitrary path.;
     * 
     * @param pattern matching the rules.
     * @param url Need to match.url
     * @return
     */
    public static boolean isMatch(String pattern, String url)
    {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }

    /**
     * The number left.0，It reaches the specified length.。Attention，If the number is converted into a string.，The length is greater thansize，Only retain. The LastsizeA character.。
     * 
     * @param num The Digital Objects
     * @param size Character set length.
     * @return Return the number string format.，The string is specified for length.。
     */
    public static final String padl(final Number num, final int size)
    {
        return padl(num.toString(), size, '0');
    }

    /**
     * The left line filled.。If the original stringsThe length is greater thansize，Keep only the last.sizeA character.。
     * 
     * @param s The original string.
     * @param size Character set length.
     * @param c Use of filled characters.
     * @return Return the specified length string.，from the original string to the left or to the。
     */
    public static final String padl(final String s, final int size, final char c)
    {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null)
        {
            final int len = s.length();
            if (s.length() <= size)
            {
                for (int i = size - len; i > 0; i--)
                {
                    sb.append(c);
                }
                sb.append(s);
            }
            else
            {
                return s.substring(len - size, len);
            }
        }
        else
        {
            for (int i = size; i > 0; i--)
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}