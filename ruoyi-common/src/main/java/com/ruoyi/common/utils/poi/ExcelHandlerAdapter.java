package com.ruoyi.common.utils.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * ExcelData Format Processing Adapter
 * 
 * @author ruoyi
 */
public interface ExcelHandlerAdapter
{
    /**
     * Formated
     * 
     * @param value Data Value of Cells
     * @param args excelNotesargsThe Parameters Group
     * @param cell The Cell Objects
     * @param wb Objects of Workbook
     *
     * @return Value after processing.
     */
    Object format(Object value, String[] args, Cell cell, Workbook wb);
}
