package com.ruoyi.common.utils.uuid;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Provide universally unique identifier (ID) implementation
 */
public class IdToken {

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private static final String digits = "0123456789";

    private static final String digits_19 = "123456789";

    private static final char DASH = '-';
    private static final char ZERO = '0';
    private static final int MIN_LENGTH = 7;

    private static final String alphaNum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final StringBuilder buf;

    private final int length;

    private final int mode;

    public static final int MODE_ALPHA_NUMERIC = 1;
    public static final int MODE_NUMERIC = 2;
    public static final int MODE_NUMERIC_DASH = 3;

    public IdToken(int length, Random random, int mode) {
        if (length < MIN_LENGTH) throw new IllegalArgumentException();

        this.random = Objects.requireNonNull(random);
        this.length = length;
        this.buf = new StringBuilder();
        this.mode = mode;


        switch (mode) {
            case MODE_NUMERIC:
            case MODE_NUMERIC_DASH:
                this.symbols = digits.toCharArray();
                break;
            case MODE_ALPHA_NUMERIC:
            default:
                this.symbols = alphaNum.toCharArray();
        }

    }

    /**
     * Create an alphanumeric string generator.
     */
    public static class Builder {

        private int nestedLength = 32;

        private int tokenType = MODE_ALPHA_NUMERIC;

        private Random secureGenerator = new SecureRandom();

        public Builder setLength(int length) {
            if (length < MIN_LENGTH) throw new IllegalArgumentException();
            this.nestedLength = length;
            return this;
        }

        public Builder setTokenType(int tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public String nextString() {

            IdToken randomTokenGenerator = new IdToken(nestedLength, secureGenerator, tokenType);
            return randomTokenGenerator.nextString();
        }
    }

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < length; ++idx) {
            char sym = symbols[random.nextInt(symbols.length)];

            // In case dot start & end of the string
            if (sym == DASH && (idx == 0 || idx == length -1)) {
                sym = digits_19.toCharArray()[random.nextInt(9)];
            }

            // In case Zero start of the string
            if (sym == ZERO && idx == 0) {
                sym = digits_19.toCharArray()[random.nextInt(9)];
            }

            // Append dash in the between
            if (mode == MODE_NUMERIC_DASH && idx == Math.round(length/2.0f)) {

                buf.append(DASH);
            }

            buf.append(sym);
        }

        return buf.toString();
    }
}
