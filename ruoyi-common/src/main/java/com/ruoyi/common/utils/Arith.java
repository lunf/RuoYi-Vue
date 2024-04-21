package com.ruoyi.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Exact number of flow points.
 * 
 * @author ruoyi
 */
public class Arith
{

    /** Precision of the law. */
    private static final int DEF_DIV_SCALE = 10;

    /** This class cannot be illustrated. */
    private Arith()
    {
    }

    /**
     * Provide accurate adjustment.。
     * @param v1 Added to
     * @param v2 Added
     * @return Two parameters and
     */
    public static double add(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * Providing accurate reduction.。
     * @param v1 reduced number.
     * @param v2 decreased
     * @return Difference in two parameters.
     */
    public static double sub(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Provide accurate multiplication operations.。
     * @param v1 Repeated number.
     * @param v2 The number
     * @return The two parameters.
     */
    public static double mul(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * Provided（relative）Exactly calculated.，When unfinished circumstances occur.，accurately.
     * After a small number.10The place，The next number is four and five.。
     * @param v1 The number removed
     * @param v2 The number
     * @return The two parameters of traders.
     */
    public static double div(double v1, double v2)
    {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * Provided（relative）Exactly calculated.。When unfinished circumstances occur.，byscaleParameters indicate
     * the accuracy.，The next number is four and five.。
     * @param v1 The number removed
     * @param v2 The number
     * @param scale It says it needs to be accurate to a small number of points.。
     * @return The two parameters of traders.
     */
    public static double div(double v1, double v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0)
        {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Provides accurate small digital four-shore-five processing.。
     * @param v We need four and five numbers.
     * @param scale Save a few points.
     * @return Fourth and fifth results.
     */
    public static double round(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }
}
