import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class DatesAndExcel {

    private XSSFWorkbook workbook;
    private ArrayList<String> names = new ArrayList<String>();

    public DatesAndExcel() {
        try {
            this.workbook = new XSSFWorkbook("recource/employeeBirthdays.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getNamesList () {
        return this.names;
    }

    // Работа с Excel файлом
    public void excelReader() {
        XSSFSheet sheet = this.workbook.getSheetAt(0);
        DateTime birthdayDay = null;

        Iterator<Row> ri = sheet.rowIterator();

        while (ri.hasNext()) {
            XSSFRow row = (XSSFRow)ri.next();
            XSSFCell cellDate = row.getCell(1);
            XSSFCell cellName = row.getCell(0);
            if (DateUtil.isCellDateFormatted(cellDate)) {
                birthdayDay = new DateTime(cellDate.getDateCellValue());
                if (compare(birthdayDay) == 1) {
                    names.add(cellName.getStringCellValue());
                }
                else if (compare(birthdayDay) == -1) { /* Тут будет всплывающее окно */ }
            }
        }
    }

    private int compare(DateTime bDate) {
        if (bDate == null) {
            return -1;
            // Придумать реализацию всплывающего окна Windows
        }

        DateTime currentDate = DateTime.now();

        int days = Days.daysBetween(currentDate, bDate.withYear(currentDate.getYear())).getDays() + 1;

        if (days == 10 || days == 2) {
            return 1;
        }

        return 0;
    }

    /*public static void main(String[] args) {
        DatesAndExcel test = new DatesAndExcel();

        test.excelReader();

        ArrayList<String> result = test.getNamesList();

        for (String name : result) {
            System.out.println(name);
        }
    }*/
}
