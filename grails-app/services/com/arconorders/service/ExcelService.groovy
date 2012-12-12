package com.arconorders.service

import jxl.CellType
import jxl.Workbook
import jxl.WorkbookSettings

class ExcelService {

    def transactional = false

    def extractSheetFromFile(File file, sheetNumber, encoding = "ISO-8859-1") {
        toWorkbook(file, encoding).getSheet(sheetNumber)
    }

    Workbook toWorkbook(File file, encoding = "ISO-8859-1") {
        WorkbookSettings settings = new WorkbookSettings()
        settings.encoding = encoding  // appears default excel we'll get is ISO-8859-1 (western), not MacRoman or UTF-8
        Workbook.getWorkbook(file, settings)
    }

    def extractRowColumnValue(sheet, int row, int column, cellToValueClosure = cellToValue) {
        try {
            def cell = sheet.getCell(column, row)
            return cellToValueClosure(cell)
        } catch (ArrayIndexOutOfBoundsException e) {
            return null
        }
    }

    static cellToValue = {cell ->
        if (cell.getType() == CellType.DATE) return cell.getDate()
        def cellContents = cell.getContents()?.trim()
        cellContents ? cellContents : null
    }

}
