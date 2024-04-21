package com.ruoyi.common.utils;

/**
 * De-allergy tools
 *
 * @author ruoyi
 */
public class DesensitizedUtil
{
    /**
     * All the characters of the password are used.*by replacement，for example：******
     *
     * @param password The code
     * @return Password after disinfection.
     */
    public static String password(String password)
    {
        if (StringUtils.isBlank(password))
        {
            return StringUtils.EMPTY;
        }
        return StringUtils.repeat('*', password.length());
    }

    /**
     * In the middle of the car.*by replacement，If the car is wrong.，not processed.
     *
     * @param carLicense Complete car number.
     * @return Car card after detection.
     */
    public static String carLicense(String carLicense)
    {
        if (StringUtils.isBlank(carLicense))
        {
            return StringUtils.EMPTY;
        }
        // Ordinary car cards.
        if (carLicense.length() == 7)
        {
            carLicense = StringUtils.hide(carLicense, 3, 6);
        }
        else if (carLicense.length() == 8)
        {
            // New Energy Car Cards
            carLicense = StringUtils.hide(carLicense, 3, 7);
        }
        return carLicense;
    }
}
