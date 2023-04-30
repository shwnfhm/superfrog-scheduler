package edu.tcu.cs.superfrogscheduler.appearance.export;

import java.io.IOException;
import java.util.List;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Appearance> listAppearances;

    public ExcelExporter(List<Appearance> listAppearances) {
        this.listAppearances = listAppearances;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("CompletedAppearanceReport");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Request ID", style);
        createCell(row, 1, "Appearance Title", style);
        createCell(row, 2, "Appearance Type", style);
        createCell(row, 3, "Request E-mail", style);
        createCell(row, 4, "Superfrog Full Name", style);
        createCell(row, 5, "Superfrog Email", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Appearance appearance : listAppearances) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, appearance.getRequestId().toString(), style);
            createCell(row, columnCount++, appearance.getTitle(), style);
            createCell(row, columnCount++, appearance.getAppearanceType().toString(), style);
            createCell(row, columnCount++, appearance.getReqEmail(), style);
            createCell(row, columnCount++, appearance.getAssignedSuperFrog().getFirstName() + " " + appearance.getAssignedSuperFrog().getLastName(), style);
            createCell(row, columnCount++, appearance.getAssignedSuperFrog().getEmail(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}