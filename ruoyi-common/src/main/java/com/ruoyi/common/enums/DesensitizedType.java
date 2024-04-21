package com.ruoyi.common.enums;

import java.util.function.Function;
import com.ruoyi.common.utils.DesensitizedUtil;

/**
 * Types of De-allergy
 *
 * @author ruoyi
 */
public enum DesensitizedType
{
    /**
     * Name of，The second2The Star Replacement.
     */
    USERNAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),

    /**
     * The code，All characters are used.*by replacement
     */
    PASSWORD(DesensitizedUtil::password),

    /**
     * ID card，in the middle10The Star Replacement.
     */
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1** **** ****$2")),

    /**
     * The phone number.，in the middle4The Star Replacement.
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),

    /**
     * The mailbox，Showing the first letter and@The address behind shows.，The other stars are replaced.
     */
    EMAIL(s -> s.replaceAll("(^.)[^@]*(@.*$)", "$1****$2")),

    /**
     * Number of Bank Card，Keep the last.4The place，The other stars are replaced.
     */
    BANK_CARD(s -> s.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1")),

    /**
     * Number of car.，Includes ordinary vehicles.、New Energy Vehicles
     */
    CAR_LICENSE(DesensitizedUtil::carLicense);

    private final Function<String, String> desensitizer;

    DesensitizedType(Function<String, String> desensitizer)
    {
        this.desensitizer = desensitizer;
    }

    public Function<String, String> desensitizer()
    {
        return desensitizer;
    }
}
