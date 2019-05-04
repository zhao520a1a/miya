package com.miya.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidongzhou1 on 2017/3/1.
 */
public class Util {
  public static void SaveJsonArraySheet_v1(HSSFWorkbook wb, String sheet_name, JSONArray r) {
    int row_offset = 0;
    for (int k = 0; k < r.size(); ++k) {
      JSONObject obj = r.getJSONObject(k);
      JSONObject d = obj.getJSONObject("tb");

      String tl = obj.get("class") + "_" + d.getOrDefault("top_left", "");

      List<String> col_names = new ArrayList<String>();
      for (Object v : d.getJSONArray("row_name")) {
        col_names.add((String) v);
      }
      List<String> row_names = new ArrayList<String>();
      for (Object v : d.getJSONArray("col_name")) {
        row_names.add((String) v);
      }
      List<List<String>> data_in_row = new ArrayList<List<String>>();
      for (int i = 0; i < d.getJSONArray("data_in_row").size(); ++i) {
        List<String> arr = new ArrayList<String>();
        JSONArray sub_arr = d.getJSONArray("data_in_row").getJSONArray(i);
        for (int j = 0; j < d.getJSONArray("data_in_row").getJSONArray(i).size(); ++j) {
          arr.add(sub_arr.getString(j));
        }
        data_in_row.add(arr);
      }
      Util.SaveAsExcelSheet(wb, sheet_name, col_names, row_names, data_in_row, tl, 0, row_offset);
      row_offset += (3 + col_names.size());
    }
  }

  public static void SaveJsonArraySheet_v1(HSSFWorkbook wb, String sheet_name, List list) {
    //int row_offset = 0;
    //for (int k = 0; k < list.size(); ++k) {
    //  LoginUserDto obj = (LoginUserDto) list.get(k);
    //  JSONObject d = obj.getJSONObject("tb");
    //
    //  String tl = obj.get("class") + "_" + d.getOrDefault("top_left", "");
    //
    //  List<String> col_names = new ArrayList<String>();
    //  for (Object v : d.getJSONArray("row_name")) {
    //    col_names.add((String) v);
    //  }
    //  List<String> row_names = new ArrayList<String>();
    //  for (Object v : d.getJSONArray("col_name")) {
    //    row_names.add((String) v);
    //  }
    //  List<List<String>> data_in_row = new ArrayList<List<String>>();
    //  for (int i = 0; i < d.getJSONArray("data_in_row").size(); ++i) {
    //    List<String> arr = new ArrayList<String>();
    //    JSONArray sub_arr = d.getJSONArray("data_in_row").getJSONArray(i);
    //    for (int j = 0; j < d.getJSONArray("data_in_row").getJSONArray(i).size(); ++j) {
    //      arr.add(sub_arr.getString(j));
    //    }
    //    data_in_row.add(arr);
    //  }
    //  Util.SaveAsExcelSheet(wb, sheet_name, col_names, row_names, data_in_row, tl, 0, row_offset);
    //  row_offset += (3 + col_names.size());
    //}
  }

  public static void SaveJsonAsSheet(HSSFWorkbook wb, String sheet_name, JSONObject d) {
    List<String> col_names = new ArrayList<String>();
    for (Object v : d.getJSONArray("row_name")) {
      col_names.add((String) v);
    }
    List<String> row_names = new ArrayList<String>();
    for (Object v : d.getJSONArray("col_name")) {
      row_names.add((String) v);
    }
    List<List<String>> data_in_row = new ArrayList<List<String>>();
    for (int i = 0; i < d.getJSONArray("data_in_row").size(); ++i) {
      List<String> arr = new ArrayList<String>();
      for (int j = 0; j < d.getJSONArray("data_in_row").getJSONArray(i).size(); ++j) {
        arr.add(String.valueOf(d.getJSONArray("data_in_row").getJSONArray(i).getDouble(j)));
      }
      data_in_row.add(arr);
    }
    Util.SaveAsExcelSheet(wb, sheet_name, col_names, row_names, data_in_row, "");
  }


  //保存为Excel
  public static void SaveAsExcelSheet(HSSFWorkbook wb,
                                      String sheet_name,
                                      List<String> col_names,
                                      List<String> row_names,
                                      List<List<String>> data_in_row,
                                      String firstColumnName) {
    SaveAsExcelSheet(wb, sheet_name, col_names, row_names, data_in_row, firstColumnName, 0, 0);
  }

  public static void SaveAsExcelSheet(HSSFWorkbook wb,
                                      String sheet_name,
                                      List<String> col_names,
                                      List<String> row_names,
                                      List<List<String>> data_in_row,
                                      String firstColumnName,
                                      int col_base,
                                      int row_base) {
    HSSFSheet sheet = wb.createSheet(sheet_name);
    int row_num = row_base;
    HSSFRow row = sheet.createRow(row_num++);
    int col_num = col_base;
    row.createCell(col_num++).setCellValue(firstColumnName);

    for (String v : col_names) {
      row.createCell(col_num++).setCellValue(v);
    }

    for (int i = 0; i < row_names.size(); ++i) {
      col_num = col_base;
      row = sheet.createRow(row_num++);
      row.createCell(col_num++).setCellValue(row_names.get(i));
      for (int j = 0; j < data_in_row.get(i).size(); ++j) {
        row.createCell(col_num++).setCellValue(data_in_row.get(i).get(j));
      }
    }
  }



}

