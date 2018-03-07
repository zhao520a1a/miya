package com.miya.controller;

import com.alibaba.fastjson.JSONObject;
import com.miya.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 * 下载文件为Excel文件
 * @author 赵金鑫
 * @date 2017年11月09日
 */
@Slf4j
@Controller
@RequestMapping("/download")
public class DownloadController {

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public void download(HttpServletResponse response,  HttpSession session) {

        JSONObject downloadJSONObject =(JSONObject) session.getAttribute("userList");
        log.info("userList获得Session属性对象：{}" ,downloadJSONObject);
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            if (downloadJSONObject != null) {
                String fileName = "测试1.xls";
                fileName = URLEncoder.encode(fileName, "UTF-8");
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
                // 解决中文文件名乱码关键行
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
                Util.SaveAsExcelSheet(wb,fileName,(List<String>)downloadJSONObject.get("columns"),(List<String>)downloadJSONObject.get("rows"),
                        (List<List<String>>) downloadJSONObject.get("data"),"");
                log.info("保存为Excel文件");
                wb.write(response.getOutputStream());
                log.info("发送成功");
            } else {
                response.getOutputStream().write(new String("had no data").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
