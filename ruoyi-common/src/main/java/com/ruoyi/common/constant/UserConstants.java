package com.ruoyi.common.constant;

/**
 * User constant information
 * 
 * @author ruoyi
 */
public class UserConstants
{
    /**
     * The only logo of the system user within the platform
     */
    public static final String SYS_USER = "SYS_USER";

    /** in normal state. */
    public static final String NORMAL = "0";

    /** an abnormal state. */
    public static final String EXCEPTION = "1";

    /** Users are banned. */
    public static final String USER_DISABLE = "1";

    /** Status of prohibition. */
    public static final String ROLE_DISABLE = "1";

    /** Normal state of the department. */
    public static final String DEPT_NORMAL = "0";

    /** Department stops. */
    public static final String DEPT_DISABLE = "1";

    /** The dictionary is normal. */
    public static final String DICT_NORMAL = "0";

    /** Is the system default?（is） */
    public static final String YES = "Y";

    /** Is the menu out chain?（is） */
    public static final String YES_FRAME = "0";

    /** Is the menu out chain?（No） */
    public static final String NO_FRAME = "1";

    /** Type of Menu（The catalogue） */
    public static final String TYPE_DIR = "M";

    /** Type of Menu（The menu） */
    public static final String TYPE_MENU = "C";

    /** Type of Menu（The button） */
    public static final String TYPE_BUTTON = "F";

    /** LayoutIdentification of components */
    public final static String LAYOUT = "Layout";
    
    /** ParentViewIdentification of components */
    public final static String PARENT_VIEW = "ParentView";

    /** InnerLinkIdentification of components */
    public final static String INNER_LINK = "InnerLink";

    /** Testing whether the only return mark. */
    public final static boolean UNIQUE = true;
    public final static boolean NOT_UNIQUE = false;

    /**
     * User name length limit
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * The length limit.
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;
}
