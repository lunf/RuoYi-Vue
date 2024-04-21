package com.ruoyi.common.core.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import com.ruoyi.common.utils.StringUtils;

/**
 * Types of tools.
 * 
 * @author ruoyi
 */
public class CharsetKit
{
    /** ISO-8859-1 */
    public static final String ISO_8859_1 = "ISO-8859-1";
    /** UTF-8 */
    public static final String UTF_8 = "UTF-8";
    /** GBK */
    public static final String GBK = "GBK";

    /** ISO-8859-1 */
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
    /** UTF-8 */
    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
    /** GBK */
    public static final Charset CHARSET_GBK = Charset.forName(GBK);

    /**
     * Convert toCharsetObjects
     * 
     * @param charset Collection of characters，Return the default set of characters to the empty.
     * @return Charset
     */
    public static Charset charset(String charset)
    {
        return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }

    /**
     * Convert a string of characters.
     * 
     * @param source The characters.
     * @param srcCharset Source of characters，presumedISO-8859-1
     * @param destCharset The target character.，presumedUTF-8
     * @return Collections after conversion.
     */
    public static String convert(String source, String srcCharset, String destCharset)
    {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * Convert a string of characters.
     * 
     * @param source The characters.
     * @param srcCharset Source of characters，presumedISO-8859-1
     * @param destCharset The target character.，presumedUTF-8
     * @return Collections after conversion.
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset)
    {
        if (null == srcCharset)
        {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset)
        {
            destCharset = StandardCharsets.UTF_8;
        }

        if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset))
        {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

    /**
     * @return Code of system characters.
     */
    public static String systemCharset()
    {
        return Charset.defaultCharset().name();
    }
}
