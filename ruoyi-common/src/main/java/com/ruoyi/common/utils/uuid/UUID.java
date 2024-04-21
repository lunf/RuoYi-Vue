package com.ruoyi.common.utils.uuid;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.ruoyi.common.exception.UtilException;

/**
 * Providing the Unique Identification Code（universally unique identifier）（UUID）realized
 *
 * @author ruoyi
 */
public final class UUID implements java.io.Serializable, Comparable<UUID>
{
    private static final long serialVersionUID = -1185015143654744140L;

    /**
     * SecureRandom The single example.
     *
     */
    private static class Holder
    {
        static final SecureRandom numberGenerator = getSecureRandom();
    }

    /** This isUUIDThe highest64Effective */
    private final long mostSigBits;

    /** This isUUIDThe Minimum64Effective */
    private final long leastSigBits;

    /**
     * Private structures.
     * 
     * @param data The data
     */
    private UUID(byte[] data)
    {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++)
        {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for (int i = 8; i < 16; i++)
        {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * Use the specified data to build new UUID。
     *
     * @param mostSigBits used {@code UUID} The Most Effective 64 The place
     * @param leastSigBits used {@code UUID} The Minimum Effective 64 The place
     */
    public UUID(long mostSigBits, long leastSigBits)
    {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * Obtaining Types 4（Incredibly created.）UUID The static factory.。
     * 
     * @return created randomly. {@code UUID}
     */
    public static UUID fastUUID()
    {
        return randomUUID(false);
    }

    /**
     * Obtaining Types 4（Incredibly created.）UUID The static factory.。 Use the encrypted fake random number generator to generate that UUID。
     * 
     * @return created randomly. {@code UUID}
     */
    public static UUID randomUUID()
    {
        return randomUUID(true);
    }

    /**
     * Obtaining Types 4（Incredibly created.）UUID The static factory.。 Use the encrypted fake random number generator to generate that UUID。
     * 
     * @param isSecure Is used{@link SecureRandom}If you can get a safer random code.，Otherwise you can get better performance.
     * @return created randomly. {@code UUID}
     */
    public static UUID randomUUID(boolean isSecure)
    {
        final Random ng = isSecure ? Holder.numberGenerator : getRandom();

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f; /* clear version */
        randomBytes[6] |= 0x40; /* set to version 4 */
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(randomBytes);
    }

    /**
     * Type obtained according to the specified byte number group 3（Based on the name）UUID The static factory.。
     *
     * @param name used for construction. UUID The number of bits.。
     *
     * @return Created according to specific numbers. {@code UUID}
     */
    public static UUID nameUUIDFromBytes(byte[] name)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae)
        {
            throw new InternalError("MD5 not supported");
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] &= 0x0f; /* clear version */
        md5Bytes[6] |= 0x30; /* set to version 3 */
        md5Bytes[8] &= 0x3f; /* clear variant */
        md5Bytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(md5Bytes);
    }

    /**
     * based on {@link #toString()} The string standard described in the method represents the form created{@code UUID}。
     *
     * @param name designated {@code UUID} The characters.
     * @return has a specified value. {@code UUID}
     * @throws IllegalArgumentException If name and {@link #toString} The string described in indicates that the form does not match this abnormal
     *
     */
    public static UUID fromString(String name)
    {
        String[] components = name.split("-");
        if (components.length != 5)
        {
            throw new IllegalArgumentException("Invalid UUID string: " + name);
        }
        for (int i = 0; i < 5; i++)
        {
            components[i] = "0x" + components[i];
        }

        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }

    /**
     * Back here. UUID of 128 The Minimum Effective Value 64 The place。
     *
     * @return This is UUID of 128 The Minimum Effective Value 64 The place。
     */
    public long getLeastSignificantBits()
    {
        return leastSigBits;
    }

    /**
     * Back here. UUID of 128 The highest level of effectiveness. 64 The place。
     *
     * @return This is UUID of 128 The highest effectiveness. 64 The place。
     */
    public long getMostSignificantBits()
    {
        return mostSigBits;
    }

    /**
     * with this {@code UUID} Related version number.. The version describes this. {@code UUID} How was created.。
     * <p>
     * The version has the following meaning::
     * <ul>
     * <li>1 based on time. UUID
     * <li>2 DCE Safety UUID
     * <li>3 Based on the name UUID
     * <li>4 created randomly. UUID
     * </ul>
     *
     * @return This is {@code UUID} The version number.
     */
    public int version()
    {
        // Version is bits masked by 0x000000000000F000 in MS long
        return (int) ((mostSigBits >> 12) & 0x0f);
    }

    /**
     * with this {@code UUID} Related Variants。Variable description {@code UUID} The layout.。
     * <p>
     * The variable has the following meaning:：
     * <ul>
     * <li>0 for NCS Backward compatibility.
     * <li>2 <a href="http://www.ietf.org/rfc/rfc4122.txt">IETF&nbsp;RFC&nbsp;4122</a>(Leach-Salz), Used for such
     * <li>6 to retain，Microsoft is backward compatible.
     * <li>7 Conserved for later defined use.
     * </ul>
     *
     * @return This is {@code UUID} Related Variants
     */
    public int variant()
    {
        // This field is composed of a varying number of bits.
        // 0 - - Reserved for NCS backward compatibility
        // 1 0 - The IETF aka Leach-Salz variant (used by this class)
        // 1 1 0 Reserved, Microsoft backward compatibility
        // 1 1 1 Reserved for future definition.
        return (int) ((leastSigBits >>> (64 - (leastSigBits >>> 62))) & (leastSigBits >> 63));
    }

    /**
     * with this UUID Related time value.。
     *
     * <p>
     * 60 The time is based on this. {@code UUID} of time_low、time_mid and time_hi Field Building。<br>
     * The time received. 100 Minecraft is a unit.，from UTC（General coordination time.） 1582 Year 10 The Moon 15 Day starts at 0:00.。
     *
     * <p>
     * The timeline is based only on time. UUID（The version Type is 1）Within is meaningful.。<br>
     * If this {@code UUID} Not based on time. UUID，This method is thrown out. UnsupportedOperationException。
     *
     * @throws UnsupportedOperationException If this {@code UUID} not version for 1 of UUID。
     */
    public long timestamp() throws UnsupportedOperationException
    {
        checkTimeBase();
        return (mostSigBits & 0x0FFFL) << 48//
                | ((mostSigBits >> 16) & 0x0FFFFL) << 32//
                | mostSigBits >>> 32;
    }

    /**
     * with this UUID Related clock sequence values。
     *
     * <p>
     * 14 The watch series is based on this. UUID of clock_seq Field Building。clock_seq Fields are used to guarantee on the basis of time. UUID The time is unique.。
     * <p>
     * {@code clockSequence} Value is based on time. UUID（The version Type is 1）Within is meaningful.。 If this UUID Not based on time. UUID，This method is thrown out.
     * UnsupportedOperationException。
     *
     * @return This is {@code UUID} The clock series.
     *
     * @throws UnsupportedOperationException If this UUID of version Not for 1
     */
    public int clockSequence() throws UnsupportedOperationException
    {
        checkTimeBase();
        return (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
    }

    /**
     * with this UUID Related Node Value。
     *
     * <p>
     * 48 The point value is based on this. UUID of node Field Building。This field is designed to save the machine. IEEE 802 Addressed，This address is used to create this. UUID To ensure unique space.。
     * <p>
     * Node values are based only on time. UUID（The version Type is 1）Within is meaningful.。<br>
     * If this UUID Not based on time. UUID，This method is thrown out. UnsupportedOperationException。
     *
     * @return This is {@code UUID} The Node Value.
     *
     * @throws UnsupportedOperationException If this UUID of version Not for 1
     */
    public long node() throws UnsupportedOperationException
    {
        checkTimeBase();
        return leastSigBits & 0x0000FFFFFFFFFFFFL;
    }

    /**
     * Back here.{@code UUID} The form of character.。
     *
     * <p>
     * UUID The string represents the form of this. BNF described：
     * 
     * <pre>
     * {@code
     * UUID                   = <time_low>-<time_mid>-<time_high_and_version>-<variant_and_sequence>-<node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               = [0-9a-fA-F]
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @return This is{@code UUID} The form of character.
     * @see #toString(boolean)
     */
    @Override
    public String toString()
    {
        return toString(false);
    }

    /**
     * Back here.{@code UUID} The form of character.。
     *
     * <p>
     * UUID The string represents the form of this. BNF described：
     * 
     * <pre>
     * {@code
     * UUID                   = <time_low>-<time_mid>-<time_high_and_version>-<variant_and_sequence>-<node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               = [0-9a-fA-F]
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param isSimple A simple model.，Simple modes do not carry.'-'ofUUIDThe characters.
     * @return This is{@code UUID} The form of character.
     */
    public String toString(boolean isSimple)
    {
        final StringBuilder builder = new StringBuilder(isSimple ? 32 : 36);
        // time_low
        builder.append(digits(mostSigBits >> 32, 8));
        if (!isSimple)
        {
            builder.append('-');
        }
        // time_mid
        builder.append(digits(mostSigBits >> 16, 4));
        if (!isSimple)
        {
            builder.append('-');
        }
        // time_high_and_version
        builder.append(digits(mostSigBits, 4));
        if (!isSimple)
        {
            builder.append('-');
        }
        // variant_and_sequence
        builder.append(digits(leastSigBits >> 48, 4));
        if (!isSimple)
        {
            builder.append('-');
        }
        // node
        builder.append(digits(leastSigBits, 12));

        return builder.toString();
    }

    /**
     * Back here. UUID The hash code.。
     *
     * @return UUID The hash value.。
     */
    @Override
    public int hashCode()
    {
        long hilo = mostSigBits ^ leastSigBits;
        return ((int) (hilo >> 32)) ^ (int) hilo;
    }

    /**
     * Compare this object with the specified object.。
     * <p>
     * Only when the parameters do not {@code null}、And one UUID Objects、have with this. UUID the same varriant、Includes the same value.（Everyone is the same.）The time，The result is for {@code true}。
     *
     * @param obj Compared with objects.
     *
     * @return If the object is the same.，then back. {@code true}；Otherwise return. {@code false}
     */
    @Override
    public boolean equals(Object obj)
    {
        if ((null == obj) || (obj.getClass() != UUID.class))
        {
            return false;
        }
        UUID id = (UUID) obj;
        return (mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits);
    }

    // Comparison Operations

    /**
     * Take this. UUID with the designated UUID compared。
     *
     * <p>
     * If two UUID differently，and the first UUID The highest effective field is greater than the second. UUID The matching field.，The first UUID greater than the second. UUID。
     *
     * @param val with this UUID compared UUID
     *
     * @return here UUID less than、equal or greater. val The time，Return separately. -1、0 or 1。
     *
     */
    @Override
    public int compareTo(UUID val)
    {
        // The ordering is intentionally set up so that the UUIDs
        // can simply be numerically compared as two numbers
        return (this.mostSigBits < val.mostSigBits ? -1 : //
                (this.mostSigBits > val.mostSigBits ? 1 : //
                        (this.leastSigBits < val.leastSigBits ? -1 : //
                                (this.leastSigBits > val.leastSigBits ? 1 : //
                                        0))));
    }

    // -------------------------------------------------------------------------------------------------------------------
    // Private method start
    /**
     * Returns the number corresponding.hexValue
     * 
     * @param val Value
     * @param digits The place
     * @return Value
     */
    private static String digits(long val, int digits)
    {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    /**
     * Check if it istime-basedThe versionUUID
     */
    private void checkTimeBase()
    {
        if (version() != 1)
        {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
    }

    /**
     * obtained{@link SecureRandom}，Classes provide a strong random number generator encrypted (RNG)
     * 
     * @return {@link SecureRandom}
     */
    public static SecureRandom getSecureRandom()
    {
        try
        {
            return SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new UtilException(e);
        }
    }

    /**
     * Obtain random number generator objects<br>
     * ThreadLocalRandomisJDK 7Then it produces a random number.，To resolve competition on multiple lines.。
     * 
     * @return {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom getRandom()
    {
        return ThreadLocalRandom.current();
    }
}
