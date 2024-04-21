package com.ruoyi.common.constant;

/**
 * Functional Conduct.
 * 
 * @author ruoyi
 */
public class ScheduleConstants
{
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /** Implementing the Targetkey */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /** presumed */
    public static final String MISFIRE_DEFAULT = "0";

    /** Immediately trigger execution. */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /** Provoking one execution. */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /** Do not trigger immediate implementation. */
    public static final String MISFIRE_DO_NOTHING = "3";

    public enum Status
    {
        /**
         * Normal
         */
        NORMAL("0"),
        /**
         * suspended
         */
        PAUSE("1");

        private String value;

        private Status(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }
}
