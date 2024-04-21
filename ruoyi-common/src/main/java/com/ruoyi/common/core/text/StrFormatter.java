package com.ruoyi.common.core.text;

import com.ruoyi.common.utils.StringUtils;

/**
 * Formatting of characters.
 * 
 * @author ruoyi
 */
public class StrFormatter
{
    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';

    /**
     * Formatting of characters<br>
     * This method is simple to take place. {} Replacement of parameters according to order.<br>
     * If you want out. {} Use of \\transition { can be，If you want out. {} The previous \ Use of Double Conversion \\\\ can be<br>
     * Examples：<br>
     * Usually used：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * transition{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * transition\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     * 
     * @param strPattern Character series template
     * @param argArray The Parameters List
     * @return Results
     */
    public static String format(final String strPattern, final Object... argArray)
    {
        if (StringUtils.isEmpty(strPattern) || StringUtils.isEmpty(argArray))
        {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        // Initialization defines good lengths for better performance.
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;
        int delimIndex;// The location of the place.
        for (int argIndex = 0; argIndex < argArray.length; argIndex++)
        {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1)
            {
                if (handledPosition == 0)
                {
                    return strPattern;
                }
                else
                { // The remaining part of the string template no longer contains the positions.，Join the rest after returning the result.
                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf.toString();
                }
            }
            else
            {
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH)
                {
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH)
                    {
                        // There is a switch before the switch.，The position remains effective.
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(Convert.utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    }
                    else
                    {
                        // Locations are converted.
                        argIndex--;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(C_DELIM_START);
                        handledPosition = delimIndex + 1;
                    }
                }
                else
                {
                    // Normal positioning.
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(Convert.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        // Add all the characters after the last place.
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf.toString();
    }
}
