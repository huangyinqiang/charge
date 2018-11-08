package net.inconnection.charge.admin.common.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {
    //excel2003版本的后缀
    public static final String SUFFIX_EXCEL_2003 = "xls";
    //excel2007版本的后缀
    public static final String SUFFIX_EXCEL_2007 = "xlsx";

    private static Workbook workBook = null;
    private static List<List<String[]>> dataList = new ArrayList<List<String[]>>();
    private static List<String[]> list = new ArrayList<String[]>(100);

    public static List<List<String[]>> getExcelData(File file,String suffix){
        return getData(file,suffix);
    }
    private static List<List<String[]>> getData(File file,String suffix){
        try {
            if(SUFFIX_EXCEL_2003.equals(suffix)){
                workBook = (Workbook)new HSSFWorkbook(new FileInputStream(file));
            }
            else if(SUFFIX_EXCEL_2007.equals(suffix)){
                workBook = (Workbook)new XSSFWorkbook(new FileInputStream(file));
            }

            Sheet sheet=null;
            //循环sheet
            for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
                sheet=workBook.getSheetAt(i);
                List<String[]> rows = new ArrayList<String[]>();
                int columnNum = 0;
                //循环每一行
                for (int j = 0; j <= sheet.getPhysicalNumberOfRows(); j++) {
                    Row row=sheet.getRow(j);
                    if(null != row){
                        //列数以excel第一行为准，
                        columnNum = sheet.getRow(0).getPhysicalNumberOfCells();
                        String[] cols = new String[columnNum];
                        //循环每一个单元格，以一行为单位，组成一个数组
                        for (int k = 0; k < columnNum; k++) {
                            //判断单元格是否为null，若为null，则置空
                            Cell cell = row.getCell(k);
                            if(null != cell) {
                                int cellType = cell.getCellType();
                                if( Cell.CELL_TYPE_NUMERIC == cellType){
                                    //判断该数字是否是日期,若是则转成字符串
                                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                                        Date d = cell.getDateCellValue();
                                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                                        cols[k] = formater.format(d);
                                    } else {
                                        cols[k] = getRightStr(cell.getNumericCellValue()+"");
                                    }
                                } else if(Cell.CELL_TYPE_STRING == cellType){
                                    cols[k] = (cell+"").trim();
                                }else  if (Cell.CELL_TYPE_BLANK == cellType) {/**空白格*/
                                    cols[k] = "";
                                } else {/** 其它的,非以上几种数据类型 */
                                    cols[i] = cell.getStringCellValue() + "";
                                }
                            } else {
                                cols[k] = "";
                            }
                        }
                        //以一行为单位，加入list
                        rows.add(cols);
                    }
                }
                dataList.add(rows);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  dataList;
    }
    /**
     * 正确地处理整数后自动加零的情况
     *
     * @param sNum
     * @return
     */
    private static String getRightStr(String sNum) {
        DecimalFormat decimalFormat = new DecimalFormat("#.000000");
        String resultStr = decimalFormat.format(new Double(sNum));
        if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
            resultStr = resultStr.substring(0, resultStr.indexOf("."));
        }
        return resultStr;
    }

    /**
     * 获取某一行数据
     *
     * @param rowIndex 计数从0开始，rowIndex为0代表header行
     * @return
     */
    public static String[] getRowData(int sheetIndex, int rowIndex) {
        if (dataList.size() > 0 && rowIndex < dataList.get(sheetIndex).size()) {
            return  dataList.get(sheetIndex).get(rowIndex);
        } else {
            return null;
        }

    }

    /**
     * 返回数据的列数
     *
     * @return
     */
    public static int getColumnNum(int sheetIndex) {
        Sheet sheet = workBook.getSheetAt(sheetIndex);
        Row row = sheet.getRow(0);
        if (row != null && row.getPhysicalNumberOfCells() > 0) {
            return row.getPhysicalNumberOfCells();
        }
        return 0;
    }

}
