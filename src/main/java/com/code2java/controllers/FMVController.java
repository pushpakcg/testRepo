package com.code2java.controllers;



import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.code2java.model.FMVModel;
import com.code2java.service.FMVService;
import com.google.gson.Gson;

@Controller
public class FMVController {

	@Autowired
	FMVService fmvService;
	
	@RequestMapping(value="FMVDashboard", method=RequestMethod.GET)
	public String loadFMVDashboard(ModelMap model,@ModelAttribute("stat") String stat)
	{
		FMVModel fmvModel=new FMVModel();
		model.put("fmvModel", fmvModel);
		model.addAttribute("yearList",fmvService.getYears());
		return "fmv";
	}
	

	@ResponseBody
	@RequestMapping(value="modelData", method=RequestMethod.POST)
	public String modelData(@RequestParam String year,ModelMap model)
	{
		FMVModel fmvModel=new FMVModel();
		fmvModel=fmvService.getModelValues(year);
		Gson gson=new Gson();
		String jj=gson.toJson(fmvModel);
		String sanitizeJJ=jj.replaceAll("[^a-z0-9:{}\",]", "");
		return sanitizeJJ;
	}
	
	
	@RequestMapping(value="FMVDashboard",method=RequestMethod.POST)
	public String updateModelData(FMVModel fmvModel,ModelMap model,RedirectAttributes redirectAttr)
	{
		List<String> years=fmvService.getYears();
		if(years.contains(fmvModel.getModelyear()))
		{
			String stat=fmvService.updateModel(fmvModel);
			redirectAttr.addFlashAttribute("stat", stat);
		}
		else
		{
			String stat=fmvService.insertModel(fmvModel);
			redirectAttr.addFlashAttribute("stat", stat);
		}
		return "redirect:FMVDashboard";
	}
	
	@RequestMapping(value="excelExport", method=RequestMethod.GET)
	public void excelExport(HttpServletResponse response) throws IOException
	{
		SXSSFWorkbook wb=fmvService.excelExport();
		response.setHeader("Content-Disposition",
				"attachment; filename=" + "OC_TAX_Data.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		ServletOutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		wb.dispose();
	}
	
}
