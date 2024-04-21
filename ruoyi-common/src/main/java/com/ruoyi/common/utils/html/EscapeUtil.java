package com.ruoyi.common.utils.html;

import com.ruoyi.common.utils.StringUtils;

/**
 * Conversion and Conversion Tools
 * 
 * @author ruoyi
 */
public class EscapeUtil
{
    public static final String RE_HTML_MARK = "(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)";

    private static final char[][] TEXT = new char[64][];

    static
    {
        for (int i = 0; i < 64; i++)
        {
            TEXT[i] = new char[] { (char) i };
        }

        // special HTML characters
        TEXT['\''] = "&#039;".toCharArray(); // A single quote.
        TEXT['"'] = "&#34;".toCharArray(); // The double quote.
        TEXT['&'] = "&#38;".toCharArray(); // &Tagged
        TEXT['<'] = "&#60;".toCharArray(); // Less than number.
        TEXT['>'] = "&#62;".toCharArray(); // greater than number.
    }

    /**
     * Conversion in the text.HTMLThe characters are secure.
     * 
     * @param text Translated text.
     * @return Text after conversion.
     */
    public static String escape(String text)
    {
        return encode(text);
    }

    /**
     * Returned to convert.HTMLSpecial characters
     * 
     * @param content Includes the conversion.HTMLThe content
     * @return Character after conversion.
     */
    public static String unescape(String content)
    {
        return decode(content);
    }

    /**
     * Cleaning all.HTMLTagged，Do not delete the content on the label.
     * 
     * @param content The text
     * @return Remove the text after the label.
     */
    public static String clean(String content)
    {
        return new HTMLFilter().filter(content);
    }

    /**
     * Escapecoded
     * 
     * @param text coded text.
     * @return Postcoding characters.
     */
    private static String encode(String text)
    {
        if (StringUtils.isEmpty(text))
        {
            return StringUtils.EMPTY;
        }

        final StringBuilder tmp = new StringBuilder(text.length() * 6);
        char c;
        for (int i = 0; i < text.length(); i++)
        {
            c = text.charAt(i);
            if (c < 256)
            {
                tmp.append("%");
                if (c < 16)
                {
                    tmp.append("0");
                }
                tmp.append(Integer.toString(c, 16));
            }
            else
            {
                tmp.append("%u");
                if (c <= 0xfff)
                {
                    // issue#I49JU8@Gitee
                    tmp.append("0");
                }
                tmp.append(Integer.toString(c, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * EscapeDecoded
     * 
     * @param content Contained content.
     * @return Posts after decoding.
     */
    public static String decode(String content)
    {
        if (StringUtils.isEmpty(content))
        {
            return content;
        }

        StringBuilder tmp = new StringBuilder(content.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < content.length())
        {
            pos = content.indexOf("%", lastPos);
            if (pos == lastPos)
            {
                if (content.charAt(pos + 1) == 'u')
                {
                    ch = (char) Integer.parseInt(content.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                }
                else
                {
                    ch = (char) Integer.parseInt(content.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            }
            else
            {
                if (pos == -1)
                {
                    tmp.append(content.substring(lastPos));
                    lastPos = content.length();
                }
                else
                {
                    tmp.append(content.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    public static void main(String[] args)
    {
        String html = "<script>alert(1);</script>";
        String escape = EscapeUtil.escape(html);
        // String html = "<scr<script>ipt>alert(\"XSS\")</scr<script>ipt>";
        // String html = "<123";
        // String html = "123>";
        System.out.println("clean: " + EscapeUtil.clean(html));
        System.out.println("escape: " + escape);
        System.out.println("unescape: " + EscapeUtil.unescape(escape));
    }
}
