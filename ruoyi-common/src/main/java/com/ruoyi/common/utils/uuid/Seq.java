package com.ruoyi.common.utils.uuid;

import java.util.concurrent.atomic.AtomicInteger;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * @author ruoyi Generated categories.
 */
public class Seq
{
    // General series types
    public static final String commSeqType = "COMMON";

    // Type of series.
    public static final String uploadSeqType = "UPLOAD";

    // Number of General Interfaces
    private static AtomicInteger commSeq = new AtomicInteger(1);

    // Number of interfaces.
    private static AtomicInteger uploadSeq = new AtomicInteger(1);

    // Identification of Machine
    private static final String machineCode = "A";

    /**
     * Get a general serial number.
     * 
     * @return The sequence value
     */
    public static String getId()
    {
        return getId(commSeqType);
    }
    
    /**
     * presumed16number of series. yyMMddHHmmss + A machine identification. + 3The length of the cycle increases the string.
     * 
     * @return The sequence value
     */
    public static String getId(String type)
    {
        AtomicInteger atomicInt = commSeq;
        if (uploadSeqType.equals(type))
        {
            atomicInt = uploadSeq;
        }
        return getId(atomicInt, 3);
    }

    /**
     * General interface number. yyMMddHHmmss + A machine identification. + lengthThe length of the cycle increases the string.
     * 
     * @param atomicInt number of series.
     * @param length Number of length.
     * @return The sequence value
     */
    public static String getId(AtomicInteger atomicInt, int length)
    {
        String result = DateUtils.dateTimeNow();
        result += machineCode;
        result += getSeq(atomicInt, length);
        return result;
    }

    /**
     * Additional cycle of characters.[1, 10 of (length)The second.), use0left refined.lengthNumber of points
     * 
     * @return The sequence value
     */
    private synchronized static String getSeq(AtomicInteger atomicInt, int length)
    {
        // Take the value again.+1
        int value = atomicInt.getAndIncrement();

        // Updated after value.>=10 of (length)The second part is re-established.1
        int maxSeq = (int) Math.pow(10, length);
        if (atomicInt.get() >= maxSeq)
        {
            atomicInt.set(1);
        }
        // Turn the string.，use0left refined.
        return StringUtils.padl(value, length);
    }
}
