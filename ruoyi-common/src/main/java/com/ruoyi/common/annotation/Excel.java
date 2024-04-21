package com.ruoyi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import com.ruoyi.common.utils.poi.ExcelHandlerAdapter;

/**
 * Customized ExportExcelData Note
 * 
 * @author ruoyi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    /**
     * when exporting.excelThe middle order
     */
    public int sort() default Integer.MAX_VALUE;

    /**
     * to export.ExcelName in.
     */
    public String name() default "";

    /**
     * Format of date, as: yyyy-MM-dd
     */
    public String dateFormat() default "";

    /**
     * If the dictionary type，Please set the dictionary.typeValue (as: sys_user_sex)
     */
    public String dictType() default "";

    /**
     * Read the content translation. (as: 0=The Man,1=The Woman,2=Unknown)
     */
    public String readConverterExp() default "";

    /**
     * Separation，Read the character group content.
     */
    public String separator() default ",";

    /**
     * BigDecimal accuracy presumed:-1(Not opened.BigDecimalFormated)
     */
    public int scale() default -1;

    /**
     * BigDecimal Submit to the rules. presumed:BigDecimal.ROUND_HALF_EVEN
     */
    public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

    /**
     * when exporting.excelThe height of each column.
     */
    public double height() default 14;

    /**
     * when exporting.excelThe width of each column.
     */
    public double width() default 16;

    /**
     * After the text.,as% 90 becoming90%
     */
    public String suffix() default "";

    /**
     * When it is worth time.,Value of the field.
     */
    public String defaultValue() default "";

    /**
     * suggested information
     */
    public String prompt() default "";

    /**
     * Settings can only select column content that cannot be entered..
     */
    public String[] combo() default {};

    /**
     * Do you need a vertical fusion cell?,Responding to demand:containedlistCollecting the cell.)
     */
    public boolean needMerge() default false;

    /**
     * Exporting data.,Responding to demand:Sometimes we need to export a template.,This is the title required but the content needs to be manually filled by the user..
     */
    public boolean isExport() default true;

    /**
     * Name of properties in another class,Support for multi-level access,Separate in small numbers.
     */
    public String targetAttr() default "";

    /**
     * Automatic statistics.,At the end, add a line of statistics.
     */
    public boolean isStatistics() default false;

    /**
     * Type of Export（0The numbers 1The characters. 2The picture）
     */
    public ColumnType cellType() default ColumnType.STRING;

    /**
     * Export the background color.
     */
    public IndexedColors headerBackgroundColor() default IndexedColors.GREY_50_PERCENT;

    /**
     * Export the color of the font.
     */
    public IndexedColors headerColor() default IndexedColors.WHITE;

    /**
     * Export the cell background color.
     */
    public IndexedColors backgroundColor() default IndexedColors.WHITE;

    /**
     * Exporting cell font color.
     */
    public IndexedColors color() default IndexedColors.BLACK;

    /**
     * Method of exporting fields
     */
    public HorizontalAlignment align() default HorizontalAlignment.CENTER;

    /**
     * Custom Data Processor
     */
    public Class<?> handler() default ExcelHandlerAdapter.class;

    /**
     * Customized data processor parameters
     */
    public String[] args() default {};

    /**
     * Type of field（0：Export of import.；1：Export only.；2：Only imported.）
     */
    Type type() default Type.ALL;

    public enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    public enum ColumnType
    {
        NUMERIC(0), STRING(1), IMAGE(2), TEXT(3);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}