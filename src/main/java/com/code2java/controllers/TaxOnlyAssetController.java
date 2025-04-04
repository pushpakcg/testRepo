package com.code2java.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.code2java.service.TaxOnlyAssetService;

@Controller
@RequestMapping(value="/taxOnlyAsset")
public class TaxOnlyAssetController {

	@Autowired
	TaxOnlyAssetService taxOnlyAssetService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String test()
	{
		return "toa";
	}
	
	@ResponseBody
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String fileUpload(@RequestParam CommonsMultipartFile file, Model model,HttpServletRequest request)
	{
		String statusMsg="";
		if(file.isEmpty())
		{
			return "file is empty";
		}
		else
		{
			statusMsg=taxOnlyAssetService.updateAndInsertRecords(file,request);
		}
		System.out.println(file.getOriginalFilename());
		model.addAttribute("updateStatus",statusMsg);
		System.out.println("message: "+statusMsg);
		return statusMsg;
	}
	
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delFileUpload(@RequestParam CommonsMultipartFile file, Model model)
	{
		String statusMsg="";
		if(file.isEmpty())
		{
			return "file is empty";
		}
		else
		{
			statusMsg=taxOnlyAssetService.deleteAndUpdateRecords(file);
		}
		System.out.println(file.getOriginalFilename());
		model.addAttribute("delStatus",statusMsg);
		System.out.println("message: "+statusMsg);
		return statusMsg;
	}
}
