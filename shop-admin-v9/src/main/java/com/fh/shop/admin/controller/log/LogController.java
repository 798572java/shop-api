package com.fh.shop.admin.controller.log;

import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.admin.param.LogQueryParam;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.util.DateForMat;
import com.fh.shop.util.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class LogController {
        @Resource(name = "logService")
        private ILogService logService;

    //跳转到日志页面
    @RequestMapping(value = "/log/log",method = RequestMethod.GET)
    public String  list(){
        return "/log/log";
    }


    //日志的查询功能
    @RequestMapping(value = "/log/findLog",method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findLog(LogQueryParam logQueryParam){
        return  logService.findLog(logQueryParam);
    }


    //导出word
    @RequestMapping(value = "/log/exportWord",method = RequestMethod.POST)
    @ResponseBody
    public void exportWord(LogQueryParam logQueryParam, HttpServletResponse response, HttpServletRequest request){
        //查询没有分页后的数据
        List<Log> logList=logService.findLogNoPage(logQueryParam);
        Configuration configuration = new Configuration();
        //防止中文乱码
        configuration.setDefaultEncoding("utf-8");
        //指出模板文件夹名字
        configuration.setClassForTemplateLoading(this.getClass(), "/template");
        //下载前存放到本地的一个路径
        String file="E:/"+UUID.randomUUID().toString()+".docx";
        FileOutputStream fileOutputStream =null;
        OutputStreamWriter outputStreamWriter =null;
        try {
            // 获取模板
            Template template = configuration.getTemplate("LogTemplate.xml");

            Map map= new HashMap();
            map.put("logs",logList);

             fileOutputStream = new FileOutputStream(file);
             outputStreamWriter = new OutputStreamWriter(fileOutputStream,"utf-8");
            template.process(map,outputStreamWriter);
            FileUtil.downloadFile(request,response,new File(file));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            if(outputStreamWriter!=null){
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //删除垃圾文件
        FileUtil.deleteFile(file);
    }





    //导出pdf
    @RequestMapping(value = "/log/exportPDF",method = RequestMethod.POST)
    @ResponseBody
    public  void  exportPDF(LogQueryParam logQueryParam,HttpServletResponse response){
        //查询没有分页后的数据
        List<Log> logList=logService.findLogNoPage(logQueryParam);
        Configuration configuration = new Configuration();
        //防止中文乱码
        configuration.setDefaultEncoding("utf-8");
        //指出模板文件夹名字
        configuration.setClassForTemplateLoading(this.getClass(), "/template");

        try {
            // 获取模板
            Template template = configuration.getTemplate("LogPdfTemplate.html");

            Map map= new HashMap();

            map.put("logs",logList);
            String s = DateForMat.date2str(new Date(), DateForMat.Date_Str);
            map.put("data", s);

            StringWriter stringWriter=new StringWriter();

            template.process(map,stringWriter);

            String htmlContent = stringWriter.toString();

            FileUtil.pdfDownloadFile(response, htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }


    //导出excel
    @RequestMapping(value = "/log/exportExcel",method = RequestMethod.POST)
    @ResponseBody
    public  void exportExcel(LogQueryParam logQueryParam,HttpServletResponse response){
        //查询没有分页后的数据
        List<Log> logList=logService.findLogNoPage(logQueryParam);
        //获取workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //获取sheet页
        XSSFSheet sheet = workbook.createSheet();
        //标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);
        //获取日期样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        short format = workbook.createDataFormat().getFormat(DateForMat.Date_Str);
        cellStyle.setDataFormat(format);
        String [] headRowArr={"编号","用户名称","真实名称","操作","时间"};
        int  rowIndex=0;
        int  cellIndex=0;
        //创建大标题
        XSSFRow titleRow = sheet.createRow(rowIndex++);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("日志管理");
        titleRow.setHeightInPoints(30);
        titleCell.setCellStyle(titleStyle);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, headRowArr.length-1);
        sheet.addMergedRegion(cellRangeAddress);
        //创建表头
        XSSFRow headerRow = sheet.createRow(rowIndex++);
        for (int i = 0; i < headRowArr.length; i++) {
            String s = headRowArr[i];
            headerRow.createCell(i).setCellValue(s);
            sheet.setColumnWidth(i,20*256);
        }
        //遍历集合，放入excel
        for (Log log : logList) {
            XSSFRow row = sheet.createRow(rowIndex++);
            row.createCell(cellIndex++).setCellValue(log.getId());
            row.createCell(cellIndex++).setCellValue(log.getUserName());
            row.createCell(cellIndex++).setCellValue(log.getRealName());
            row.createCell(cellIndex++).setCellValue(log.getInfo());
            XSSFCell cell = row.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(log.getInsertTime());
            cellIndex=0;
        }
        FileUtil.xlsDownloadFile(response,workbook);

    }


}
