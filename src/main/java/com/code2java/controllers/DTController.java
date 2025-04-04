package com.code2java.controllers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.code2java.service.IDTService;
import com.code2java.service.StateIncomeTaxService;
import com.google.gson.Gson;
import com.onelogin.saml2.Auth;
import com.onelogin.saml2.servlet.ServletUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Controller
public class DTController {

	@Autowired
	private IDTService idtService;
	
	@Autowired
	public StateIncomeTaxService stateIncomeTaxService;;

	private static final Logger logger = (Logger) LogManager.getLogger(DTController.class);

	@RequestMapping(value = "/acs")
	public String loadacs(Model model) {
		return "acs";
	}

	@RequestMapping(value = "/acs.jsp")
	public String loadacs2(Model model) {
		return "acs";
	}

	@RequestMapping(value = "/attrs")
	public String loadattrs(Model model) {
		return "attrs";
	}

	@RequestMapping(value = "/dologin")
	public String loaddologin(Model model) {
		return "dologin";
	}

	@RequestMapping(value = "/dologout")
	public String loaddologout(Model model) {
		return "dologout";
	}

	@RequestMapping(value = "/metadata")
	public String loadmetadata(Model model) {
		return "metadata";
	}

	@RequestMapping(value = "/samlindex")
	public String loadsamlindex(Model model) {
		return "samlindex";
	}

	@RequestMapping(value = "/sls")
	public String loadsls(Model model) {
		return "sls";
	}

	@RequestMapping(value = "/")
	public String loadHome(Model model, HttpServletResponse response, HttpServletRequest request) {
		logger.info("loadHome starts");
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		HttpSession session = request.getSession();
		String uri = request.getScheme() + "://" +   // "http" + "://
	             request.getServerName() +       // "myhost"
	             ":" +                           // ":"
	             request.getServerPort() +       // "8080"
	             request.getRequestURI() +       // "/people"
	             "?" +                           // "?"
	             request.getQueryString();
		logger.info("url: " +uri);
		//String ssoid=(null!=session &&
		 //null!=session.getAttribute("uid"))?session.getAttribute("uid").toString():"no-ssoid";
		//String ssoid="206638207";
		// sso in local

		/*
		 * Cookie userIdCookie = new Cookie("userId", ssoid); userIdCookie.setPath("/");
		 * userIdCookie.setMaxAge(-1); response.addCookie(userIdCookie);
		 */

		try {
			String samlResponse = request.getParameter("SAMLResponse");
		  //samlResponse= "PHNhbWxwOlJlc3BvbnNlIFZlcnNpb249IjIuMCIgSUQ9ImJMNFJHd0RaZTNqYW9xZmx6RVhPLjFZcW1heCIgSXNzdWVJbnN0YW50PSIyMDIzLTA0LTI3VDA5OjQzOjQ3Ljc2NloiIEluUmVzcG9uc2VUbz0iT05FTE9HSU5fZDI1YjAzMTktYjM2Mi00NWZhLWE2OWQtYmEzZmJhOTA2MGY5IiB4bWxuczpzYW1scD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOnByb3RvY29sIj48c2FtbDpJc3N1ZXIgeG1sbnM6c2FtbD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFzc2VydGlvbiI+bmJjdWZzc3ByZDwvc2FtbDpJc3N1ZXI+PHNhbWxwOlN0YXR1cz48c2FtbHA6U3RhdHVzQ29kZSBWYWx1ZT0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOnN0YXR1czpTdWNjZXNzIi8+PC9zYW1scDpTdGF0dXM+PHNhbWw6QXNzZXJ0aW9uIElEPSJHbjUyVzY5cjM5bXR3ZzJickFTRVZZUUt6bVgiIElzc3VlSW5zdGFudD0iMjAyMy0wNC0yN1QwOTo0Mzo0Ny43NzNaIiBWZXJzaW9uPSIyLjAiIHhtbG5zOnNhbWw9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphc3NlcnRpb24iPjxzYW1sOklzc3Vlcj5uYmN1ZnNzcHJkPC9zYW1sOklzc3Vlcj48ZHM6U2lnbmF0dXJlIHhtbG5zOmRzPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjIj48ZHM6U2lnbmVkSW5mbz48ZHM6Q2Fub25pY2FsaXphdGlvbk1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMTAveG1sLWV4Yy1jMTRuIyIvPjxkczpTaWduYXR1cmVNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNyc2Etc2hhMjU2Ii8+PGRzOlJlZmVyZW5jZSBVUkk9IiNHbjUyVzY5cjM5bXR3ZzJickFTRVZZUUt6bVgiPjxkczpUcmFuc2Zvcm1zPjxkczpUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIvPjxkczpUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiLz48L2RzOlRyYW5zZm9ybXM+PGRzOkRpZ2VzdE1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMDQveG1sZW5jI3NoYTI1NiIvPjxkczpEaWdlc3RWYWx1ZT5QemtyR0k4ZCtGdWlpVCtWN2lDdmx6dEJUVDRpdXlpWEpWWnNLMUVxVk9NPTwvZHM6RGlnZXN0VmFsdWU+PC9kczpSZWZlcmVuY2U+PC9kczpTaWduZWRJbmZvPjxkczpTaWduYXR1cmVWYWx1ZT5rTWs3OEx6RkdmUGxONTZ3U3BDT0ZRTjMrQVhubUsybUl6SU03QmVTNzNxSHpaSndGZFgwTnBXVGtzS3ZUdUJXYXNOd2kxd2ppOENzM0F1RnI3eFNPVnRhSTQzMFhOTkU0eUxUWUVRUDhVZUhPOEhwZ3AyaXAyYXhFV2MxMmQ0SDFpK3VOSHhnQUZOWi9GMklZYklLNWx4ZFllU3dmTzByZjJZenVLZEs0ckJxQmxuS2V6dFdBbko0eVAxcXB1MHp1S0FXK2IvaU1hc1IxNFJBYkx0RVloWGpiSHNpaWtoOE5xMnNKaXBkK0UxcmdMR21sZTk0ck9zTlJYMFlQbHZJZ085QW94aHRCc25ZdkJUeGFVZUFOYXFFdFlBUVhoL1JiSDFNem5aVmc5WUdSMWZSa1BUQlVlR05iQkpwU3ROcWhzK2toRSt2aWRIclJvMGdXVi9IbVE9PTwvZHM6U2lnbmF0dXJlVmFsdWU+PGRzOktleUluZm8+PGRzOlg1MDlEYXRhPjxkczpYNTA5Q2VydGlmaWNhdGU+TUlJR3ZUQ0NCYVdnQXdJQkFnSVFlZW11aXdlWUlPSUlrc3FGQmlsQ0JUQU5CZ2txaGtpRzl3MEJBUXNGQURDQnVqRUxNQWtHQTFVRUJoTUNWVk14RmpBVUJnTlZCQW9URFVWdWRISjFjM1FzSUVsdVl5NHhLREFtQmdOVkJBc1RIMU5sWlNCM2QzY3VaVzUwY25WemRDNXVaWFF2YkdWbllXd3RkR1Z5YlhNeE9UQTNCZ05WQkFzVE1DaGpLU0F5TURFeUlFVnVkSEoxYzNRc0lFbHVZeTRnTFNCbWIzSWdZWFYwYUc5eWFYcGxaQ0IxYzJVZ2IyNXNlVEV1TUN3R0ExVUVBeE1sUlc1MGNuVnpkQ0JEWlhKMGFXWnBZMkYwYVc5dUlFRjFkR2h2Y21sMGVTQXRJRXd4U3pBZUZ3MHlNekF6TWpBeE16TXhOVEphRncweU5EQXpNVGN4TXpNeE5USmFNRzB4Q3pBSkJnTlZCQVlUQWxWVE1SRXdEd1lEVlFRSUV3aE9aWGNnV1c5eWF6RVJNQThHQTFVRUJ4TUlUbVYzSUZsdmNtc3hJREFlQmdOVkJBb1RGMDVDUTFWdWFYWmxjbk5oYkNCTlpXUnBZU3dnVEV4RE1SWXdGQVlEVlFRREV3MW1jM011YVc1aVkzVXVZMjl0TUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUE2SnZDWG1ZN0FhQlpZeU1jRlk3TlI1T2NyZ09PZG9FankrWWFPU1FNNUkzZjBrQTFNSWtIQVlMT3BxUVpqSkpUVXVsSGxDbVpqc0xTMGdZd0dHRGtINTJLRUtscFZ0OGhBNjRkS1d5MW52dTAzc2diK2ZueTBsU2Y0VXlWbEliRldMNmRtaEhjenZOWUZnVXhEN3ZYQlBGT2JWZXgrZ29IY3NMaG5tWFFaMjdiU1dOV1JMNFllZ09yMDJrY1pPYUpMRVczL3pZVFdNa3BkNG5lYzN5QVlNMzc3NTVFNHRFZ3l6Y1Y2S3lDcG93UVZQU1ZuZ1RJQnkya3dmR000TzQ5NFB1QmNmeGxTdmNkd04xRHFaNTFtQnh2Q0JBUXBBZWZLYllhUHlhVlc0MUpwWUJUeTNRN0J1N1h3MDVTcTFCUUp6TktSV1RsM2dGUHJuM1hkU2ZZVXdJREFRQUJvNElEQ1RDQ0F3VXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVW1KdU0wSk9wZ21jZUl2ajl3Z3dZZXl0TGdua3dId1lEVlIwakJCZ3dGb0FVZ3FKd2ROMjhVei9QZTlUM3pYK25ZTVlLVEw4d2FBWUlLd1lCQlFVSEFRRUVYREJhTUNNR0NDc0dBUVVGQnpBQmhoZG9kSFJ3T2k4dmIyTnpjQzVsYm5SeWRYTjBMbTVsZERBekJnZ3JCZ0VGQlFjd0FvWW5hSFIwY0RvdkwyRnBZUzVsYm5SeWRYTjBMbTVsZEM5c01Xc3RZMmhoYVc0eU5UWXVZMlZ5TURNR0ExVWRId1FzTUNvd0tLQW1vQ1NHSW1oMGRIQTZMeTlqY213dVpXNTBjblZ6ZEM1dVpYUXZiR1YyWld3eGF5NWpjbXd3R0FZRFZSMFJCQkV3RDRJTlpuTnpMbWx1WW1OMUxtTnZiVEFPQmdOVkhROEJBZjhFQkFNQ0JhQXdIUVlEVlIwbEJCWXdGQVlJS3dZQkJRVUhBd0VHQ0NzR0FRVUZCd01DTUV3R0ExVWRJQVJGTUVNd053WUtZSVpJQVliNmJBb0JCVEFwTUNjR0NDc0dBUVVGQndJQkZodG9kSFJ3Y3pvdkwzZDNkeTVsYm5SeWRYTjBMbTVsZEM5eWNHRXdDQVlHWjRFTUFRSUNNSUlCZlFZS0t3WUJCQUhXZVFJRUFnU0NBVzBFZ2dGcEFXY0FkUUE3VTNkMVBpMjVnRTZMTUZzRy9rQTdaOWhQdy9USHZRQU5MWEp2NGZyVUZ3QUFBWWIvT0IzSEFBQUVBd0JHTUVRQ0lESlprYXNwNVBjemtZeGtBQ05GdkVUQk8wVjQ1WVRKbEdpQ0EwN1A0QWI4QWlBY2J6c05UcmFjK2o1bEhFWm91dldFQjdyQmdGWVZIbjNFaGlBMHBQU3lGZ0IzQUVpdzQydmFwa2MwRCtWcUF2cWRNT3NjVWdITFZ0MHNnZG03djZzNTJJUnpBQUFCaHY4NEhrd0FBQVFEQUVnd1JnSWhBTTgwKzdnallpSklOYWZCMXkwVFJoVGtYcXVyeDY2VkF2dUJIU3BZM29qVUFpRUFtSDZXWG9vdjhoMFhpQ1VSTzBBd2cvTW1Zc2xNNndTeFllY0lWS1R2VEVJQWRRRHV6ZEJrMWRzYXpzVmN0NTIwelJPaU1vZEdmTHpzM3NOUlNGbEdjUisxbXdBQUFZYi9PQjQrQUFBRUF3QkdNRVFDSUcvV0tUQjUxR0ZTcTIrTUFoeVl0c2ZSYUJSQ3pFVjV4QklxOUJFcE8wcGVBaUJiUHBzK1VCZlExYSsyN0QvOW9ZdFVBL3pGOUNPRy8zUGFiaHNKTEdxa1JEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFIc0JTU2NOTlNyc2p2M1Z1SkU0OERBWmxnb2FNSFlOOXplM2JiNUJZVWNnNFQvZ1lOcm9YY2x6R0hnMDJicThNWFlhRW42OUwwMXVhQXhEOEliOU04d09xandzZWlhSU51M2tFYWJWMEhxRnRxdFk5Zkk2bDJTMzlqV21idm9EUVRxaEo0MzZLRmNnTjA0bElrNTdEMnZGcyttS1pmTTNIYWxsZ3lxbUpLZDVnWWFvSkZ1K3ZEelZVeDhlSGpJVkpiLy83YWdCdDlzK3RKZGR4RStySnlGTzlYWDYrZlJaejZJQlMyWHNlVlAwTkY1Z0FDNEwvQ3dBWWE3N01FSWdMcDM3bTJoc2ViSTZBMWZ2dUFPR0hnb1hFd040Mk1tTFhDQVpabTJVQXZhRm5FT0dGV1lRcGNodFJFYmhIZ1hkM3NzcHJnZHBkZDVBd3JzYlVFNURUQUE9PTwvZHM6WDUwOUNlcnRpZmljYXRlPjwvZHM6WDUwOURhdGE+PGRzOktleVZhbHVlPjxkczpSU0FLZXlWYWx1ZT48ZHM6TW9kdWx1cz42SnZDWG1ZN0FhQlpZeU1jRlk3TlI1T2NyZ09PZG9FankrWWFPU1FNNUkzZjBrQTFNSWtIQVlMT3BxUVpqSkpUVXVsSGxDbVpqc0xTMGdZd0dHRGtINTJLRUtscFZ0OGhBNjRkS1d5MW52dTAzc2diK2ZueTBsU2Y0VXlWbEliRldMNmRtaEhjenZOWUZnVXhEN3ZYQlBGT2JWZXgrZ29IY3NMaG5tWFFaMjdiU1dOV1JMNFllZ09yMDJrY1pPYUpMRVczL3pZVFdNa3BkNG5lYzN5QVlNMzc3NTVFNHRFZ3l6Y1Y2S3lDcG93UVZQU1ZuZ1RJQnkya3dmR000TzQ5NFB1QmNmeGxTdmNkd04xRHFaNTFtQnh2Q0JBUXBBZWZLYllhUHlhVlc0MUpwWUJUeTNRN0J1N1h3MDVTcTFCUUp6TktSV1RsM2dGUHJuM1hkU2ZZVXc9PTwvZHM6TW9kdWx1cz48ZHM6RXhwb25lbnQ+QVFBQjwvZHM6RXhwb25lbnQ+PC9kczpSU0FLZXlWYWx1ZT48L2RzOktleVZhbHVlPjwvZHM6S2V5SW5mbz48L2RzOlNpZ25hdHVyZT48c2FtbDpTdWJqZWN0PjxzYW1sOk5hbWVJRCBGb3JtYXQ9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjEuMTpuYW1laWQtZm9ybWF0OnVuc3BlY2lmaWVkIj4yMDY2MzgyMDc8L3NhbWw6TmFtZUlEPjxzYW1sOlN1YmplY3RDb25maXJtYXRpb24gTWV0aG9kPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6Y206YmVhcmVyIj48c2FtbDpTdWJqZWN0Q29uZmlybWF0aW9uRGF0YSBSZWNpcGllbnQ9Imh0dHBzOi8vZmFycy1kci5pbmJjdS5jb20vRkFXZWJBcHAvIiBOb3RPbk9yQWZ0ZXI9IjIwMjMtMDQtMjdUMDk6NDg6NDcuNzczWiIgSW5SZXNwb25zZVRvPSJPTkVMT0dJTl9kMjViMDMxOS1iMzYyLTQ1ZmEtYTY5ZC1iYTNmYmE5MDYwZjkiLz48L3NhbWw6U3ViamVjdENvbmZpcm1hdGlvbj48L3NhbWw6U3ViamVjdD48c2FtbDpDb25kaXRpb25zIE5vdEJlZm9yZT0iMjAyMy0wNC0yN1QwOTozODo0Ny43NzNaIiBOb3RPbk9yQWZ0ZXI9IjIwMjMtMDQtMjdUMDk6NDg6NDcuNzczWiI+PHNhbWw6QXVkaWVuY2VSZXN0cmljdGlvbj48c2FtbDpBdWRpZW5jZT5GYXJzRFI8L3NhbWw6QXVkaWVuY2U+PC9zYW1sOkF1ZGllbmNlUmVzdHJpY3Rpb24+PC9zYW1sOkNvbmRpdGlvbnM+PHNhbWw6QXV0aG5TdGF0ZW1lbnQgU2Vzc2lvbkluZGV4PSJHbjUyVzY5cjM5bXR3ZzJickFTRVZZUUt6bVgiIEF1dGhuSW5zdGFudD0iMjAyMy0wNC0yN1QwOTo0Mzo0Ny43NjlaIj48c2FtbDpBdXRobkNvbnRleHQ+PHNhbWw6QXV0aG5Db250ZXh0Q2xhc3NSZWY+dXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFjOmNsYXNzZXM6dW5zcGVjaWZpZWQ8L3NhbWw6QXV0aG5Db250ZXh0Q2xhc3NSZWY+PC9zYW1sOkF1dGhuQ29udGV4dD48L3NhbWw6QXV0aG5TdGF0ZW1lbnQ+PHNhbWw6QXR0cmlidXRlU3RhdGVtZW50PjxzYW1sOkF0dHJpYnV0ZSBOYW1lPSJ1aWQiIE5hbWVGb3JtYXQ9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphdHRybmFtZS1mb3JtYXQ6YmFzaWMiPjxzYW1sOkF0dHJpYnV0ZVZhbHVlIHhzaTp0eXBlPSJ4czpzdHJpbmciIHhtbG5zOnhzPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYSIgeG1sbnM6eHNpPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYS1pbnN0YW5jZSI+MjA2NjM4MjA3PC9zYW1sOkF0dHJpYnV0ZVZhbHVlPjwvc2FtbDpBdHRyaWJ1dGU+PHNhbWw6QXR0cmlidXRlIE5hbWU9IkZpcnN0TmFtZSIgTmFtZUZvcm1hdD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmF0dHJuYW1lLWZvcm1hdDpiYXNpYyI+PHNhbWw6QXR0cmlidXRlVmFsdWUgeHNpOnR5cGU9InhzOnN0cmluZyIgeG1sbnM6eHM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hIiB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIj5DaGl0dGFiYXRoaW5hPC9zYW1sOkF0dHJpYnV0ZVZhbHVlPjwvc2FtbDpBdHRyaWJ1dGU+PHNhbWw6QXR0cmlidXRlIE5hbWU9Ikxhc3ROYW1lIiBOYW1lRm9ybWF0PSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YXR0cm5hbWUtZm9ybWF0OmJhc2ljIj48c2FtbDpBdHRyaWJ1dGVWYWx1ZSB4c2k6dHlwZT0ieHM6c3RyaW5nIiB4bWxuczp4cz0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEiIHhtbG5zOnhzaT0iaHR0cDovL3d3dy53My5vcmcvMjAwMS9YTUxTY2hlbWEtaW5zdGFuY2UiPktvbmRhcGFOYWlkdTwvc2FtbDpBdHRyaWJ1dGVWYWx1ZT48L3NhbWw6QXR0cmlidXRlPjxzYW1sOkF0dHJpYnV0ZSBOYW1lPSJlbWFpbCIgTmFtZUZvcm1hdD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmF0dHJuYW1lLWZvcm1hdDpiYXNpYyI+PHNhbWw6QXR0cmlidXRlVmFsdWUgeHNpOnR5cGU9InhzOnN0cmluZyIgeG1sbnM6eHM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hIiB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIj5DaGl0dGFiYXRoaW5hLktvbmRhcGFOYWlkdUBuYmN1bmkuY29tPC9zYW1sOkF0dHJpYnV0ZVZhbHVlPjwvc2FtbDpBdHRyaWJ1dGU+PC9zYW1sOkF0dHJpYnV0ZVN0YXRlbWVudD48L3NhbWw6QXNzZXJ0aW9uPjwvc2FtbHA6UmVzcG9uc2U+";
			if (null != samlResponse && samlResponse.length() > 0) {
				logger.info("samlResponse present");
				// saml response is present, process the auth request

				Auth auth = new Auth(request, response);
				 	logger.info("request: "+ request);
					logger.info("request: "+ request.toString());
					logger.info("response: "+ response);
					logger.info("response: "+ response.toString());
				// result.put("auth", auth);
				auth.processResponse();
				result.put("authprocess", "completed");
				logger.info("auth processResponse method completed");
				if (!auth.isAuthenticated()) {
					// out.println("<div class=\"alert alert-danger\" role=\"alert\">Not
					// authenticated</div>");
					result.put("isAuthenticated", "false");
					logger.info("isAuthenticated false");
				}

				List<String> errors = auth.getErrors();
				result.put("errors", errors);
				logger.info("errors : " + errors);

				if (!errors.isEmpty()) {
					logger.info("errors prestn in authentication");
					// out.println("<p>" + StringUtils.join(errors, ", ") + "</p>");
					if (auth.isDebugActive()) {
						String errorReason = auth.getLastErrorReason();
						result.put("errorReason", errorReason);
						logger.info("errorReason : " + errorReason);
						if (errorReason != null && !errorReason.isEmpty()) {
							// out.println("<p>" + auth.getLastErrorReason() + "</p>");
						}
					}
					// out.println("<a href=\"dologin\" class=\"btn btn-primary\">Login</a>");
				} else {
					logger.info("authentication success, adding session attributes");
					Map<String, List<String>> attributes = auth.getAttributes();

					for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
						session.setAttribute(entry.getKey(), entry.getValue().get(0));
						result.put(entry.getKey(), entry.getValue().get(0));
					}

					String nameId = auth.getNameId();
					String nameIdFormat = auth.getNameIdFormat();
					String sessionIndex = auth.getSessionIndex();
					String nameidNameQualifier = auth.getNameIdNameQualifier();
					String nameidSPNameQualifier = auth.getNameIdSPNameQualifier();
					result.put("nameId", auth.getNameId());
					// logger.info("nameId : "+auth.getNameId());
					result.put("nameIdFormat", nameIdFormat);
					result.put("sessionIndex", sessionIndex);
					result.put("nameidNameQualifier", nameidNameQualifier);
					result.put("nameidSPNameQualifier", nameidSPNameQualifier);

					session.setAttribute("attributes", attributes);
					session.setAttribute("nameId", nameId);
					session.setAttribute("nameIdFormat", nameIdFormat);
					session.setAttribute("sessionIndex", sessionIndex);
					session.setAttribute("nameidNameQualifier", nameidNameQualifier);
					session.setAttribute("nameidSPNameQualifier", nameidSPNameQualifier);

					String ssoid = (null != session && null != session.getAttribute("uid"))
							? session.getAttribute("uid").toString()
							: "no-ssoid";
					String email = (null != session && null != session.getAttribute("email"))
							? session.getAttribute("email").toString()
							: "no-email";
					logger.info("ssoid :" + ssoid);
					logger.info("email :" + email);

					// Adding cookies
					Cookie firstNameCookie = new Cookie("firstName", session.getAttribute("FirstName").toString());
					firstNameCookie.setPath("/");
					firstNameCookie.setMaxAge(-1);
					response.addCookie(firstNameCookie);

					Cookie lastNameCookie = new Cookie("lastName", session.getAttribute("LastName").toString());
					lastNameCookie.setPath("/");
					lastNameCookie.setMaxAge(-1);
					response.addCookie(lastNameCookie);

					Cookie userIdCookie = new Cookie("userId", ssoid);
					userIdCookie.setPath("/");
					userIdCookie.setMaxAge(-1);
					response.addCookie(userIdCookie);

					String relayState = request.getParameter("RelayState");

					if (relayState != null && !relayState.isEmpty()
							&& !relayState.equals(ServletUtils.getSelfRoutedURLNoQuery(request))
							&& !relayState.contains("/dologin")) { // We don't want to be redirected to login.jsp
																	// neither
						// response.sendRedirect(request.getParameter("RelayState"));
						return relayState;
					}
				}

			} else if (null != session && null != session.getAttribute("email")
					&& StringUtils.isNotBlank(session.getAttribute("email").toString())) {
				// session exist, allow to redirect to home page
				logger.info("session already eexist, allow to home page");
				return "homePage";
			} else {
				// no session, no" saml response, redirect to login
				logger.info("no session, no saml response");
				// return "dologin";
			}

		} catch (Exception e) {
			logger.info("Exception : " + e);
			logger.info("Exception message : " + e.getMessage());
			result.put("error", e.getMessage());
		}
		logger.info("loadHome ends");
		return "homePage";
	}
	/*
	 * public String getUserDetails(String samplInput, HttpServletResponse response)
	 * { try {
	 * 
	 * byte[] samlByteArray = Base64.decodeBase64(samplInput); ByteArrayInputStream
	 * samlByteStreamy = new ByteArrayInputStream(samlByteArray); JAXBContext
	 * jaxbContext = JAXBContext.newInstance(Response.class); Unmarshaller
	 * jaxbUnmarshaller = jaxbContext.createUnmarshaller(); Response samlResponse =
	 * (Response) jaxbUnmarshaller.unmarshal(samlByteStreamy);
	 * 
	 * String uid =
	 * samlResponse.getAssertion().getAttributeStatement().getAttribute().get(0).
	 * getAttributeValue() .get(0); String firstName =
	 * samlResponse.getAssertion().getAttributeStatement().getAttribute().get(1)
	 * .getAttributeValue().get(0); String lastName =
	 * samlResponse.getAssertion().getAttributeStatement().getAttribute().get(2)
	 * .getAttributeValue().get(0); String email =
	 * samlResponse.getAssertion().getAttributeStatement().getAttribute().get(3).
	 * getAttributeValue() .get(0);
	 * 
	 * 
	 * 
	 * System.out.println(uid); logger.info("uid :"+uid);
	 * System.out.println(firstName); System.out.println(lastName);
	 * System.out.println(email);
	 * 
	 * Cookie firstNameCookie = new Cookie("firstName", firstName);
	 * firstNameCookie.setPath("/"); firstNameCookie.setMaxAge(-1);
	 * response.addCookie(firstNameCookie);
	 * 
	 * Cookie lastNameCookie = new Cookie("lastName", lastName);
	 * lastNameCookie.setPath("/"); lastNameCookie.setMaxAge(-1);
	 * response.addCookie(lastNameCookie);
	 * 
	 * } catch (Exception e) { System.out.println("Exception:" + e); } return null;
	 * }
	 */

	/**
	 * @author code2java This method will be called when user clicks on link showed
	 *         on welcome.jsp page The request is mapped using @RequestMapping
	 *         annotation.
	 * @return
	 */
	@RequestMapping(value = "/taxAdditionReport")
	public String loadDatatable(Model model) {
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		model.addAttribute("instanceMap", getInstances());
		model.addAttribute("yearMap", getYears());
		return "taxReg";

	}

	@RequestMapping(value = "/dashboardReport")
	public String loadDatatableDash(Model model) {
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		return "dbReport";

	}

	@RequestMapping(value = "/dashboardReport1")
	public String loadDatatableDash1(Model model) {
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		return "taxRegisterReport";

	}

	/*
	 * @RequestMapping(value = "/taxRetirementReport") public String
	 * loadDatatableRetirement(Model model) { model.addAttribute("companyGroupsMap",
	 * getCompanyGroups()); model.addAttribute("instanceMap", getInstances());
	 * model.addAttribute("yearMap", getYears()); return "taxRetirement";
	 * 
	 * }
	 */

	@RequestMapping(value = "/stateTax")
	public String loadDatatableState(Model model) {
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		model.addAttribute("instanceMap", getInstances());
		model.addAttribute("yearMap", getYears());
		return "StateTax";

	}

	@RequestMapping(value = "/menu")
	public ModelAndView loadMenu(Model model) {
		/* This will load the myDatatable.jsp page */
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		return new ModelAndView("Menu");

	}

	@RequestMapping(value = "/sapfahistorical")
	public ModelAndView loadSAPFAHistorical(Model model) {
		/* This will load the myDatatable.jsp page */
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		return new ModelAndView("SAPFAHistorical");

	}

	@RequestMapping(value = "/menudash")
	public ModelAndView loadMenuDash(Model model) {
		/* This will load the myDatatable.jsp page */
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		return new ModelAndView("MenuDash");

	}

	@RequestMapping(value = "/menutax")
	public ModelAndView loadMenuTax(Model model) {
		/* This will load the myDatatable.jsp page */
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		return new ModelAndView("MenuTax");

	}

	@RequestMapping(value = "/statetax")
	public ModelAndView loadStatetax(Model model) {
		/* This will load the myDatatable.jsp page */
		model.addAttribute("companyGroupsMap", getCompanyGroups());
		return new ModelAndView("StateMenuTax");

	}

	@RequestMapping(value = "/report")
	public ModelAndView loadreport() {
		/* This will load the myDatatable.jsp page */
		return new ModelAndView("report");

	}

	/**
	 * This method will be called from AJAX callback of Data Tables
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadServerSideData")
	public String loadServerSideData(HttpServletResponse response, HttpServletRequest request) {
		logger.info("Federal Tax Asset Register loadServerSideData POST service call");
		/* getting the JSON response to load in data table */
		String jsonResponse = idtService.getDataTableResponse(request);

		/* Setting the response type as JSON */
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		logger.info("Federal Tax Asset Register loadServerSideData POST service call status : 200");
		return jsonResponse;
	}

	@RequestMapping("/downloadExcelData")
	public void downloadExcelData(HttpServletResponse response, HttpServletRequest request) throws IOException {
		logger.info("Federal Tax Asset Register downloadExcelData GET service call");
		logger.info("Temp path:" + System.getProperty("java.io.tmpdir"));

		SXSSFWorkbook workBook = idtService.getExcelData(request);
		// Workbook workBook = null;
		//SXSSFWorkbook workBook = null;
		//InfiniumReportsHandler infiniumReportsHandler = new InfiniumReportsHandler();
		//workBook = infiniumReportsHandler.writeFile(result);
		response.setHeader("Content-Disposition", "attachment; filename=" + "Tax_Register_Report.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addCookie(new Cookie("downloadStarted", "1"));
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		workBook.dispose();

		logger.info("Federal Tax Asset Register downloadExcelData GET service call status : 200");
		logger.info("excel file downloaded");

	}

	public Map<String, String> getCompanyGroups() {
		Map<String, String> groupsMap = new LinkedHashMap<String, String>();
		List<String[]> list = idtService.getCompanyGroups();
		for (String[] s : list) {
			groupsMap.put(s[0], s[1]);
		}
		return groupsMap;
	}

	// Instance
	public Map<String, String> getInstances() {
		Map<String, String> instanceMap = new LinkedHashMap<String, String>();
		List<String[]> list = idtService.getInstances();
		for (String[] s : list) {
			instanceMap.put(s[0], s[1]);
		}
		return instanceMap;
	}

	// YEAR
	public Map<String, String> getYears() {
		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		List<String[]> list = idtService.getYears();
		for (String[] s : list) {
			yearMap.put(s[0], s[1]);
		}
		return yearMap;
	}

	@RequestMapping(value = "getCompanyNames", method = RequestMethod.GET)
	public @ResponseBody String getCompanyNames(@RequestParam(value = "groupName", required = true) String groupName) {
		logger.info("Federal Tax Asset Register getCompanyNames GET service call");
		Map<String, String> companyNamesMap = new TreeMap<String, String>();
		List<String[]> list = idtService.getCompanyNames(groupName);
		for (String[] s : list) {
			companyNamesMap.put(StringEscapeUtils.escapeHtml4(s[0]), StringEscapeUtils.escapeHtml4(s[0]) + " - " + StringEscapeUtils.escapeHtml4(s[1]));
		}
		// JSONObject json = new JSONObject(companyNamesMap);
		// return json.toString();
		Gson gson = new Gson();
		String json = gson.toJson(companyNamesMap, TreeMap.class);
		// return json.toString();
		logger.info("Federal Tax Asset Register getCompanyNames GET service call status : 200");
		return json;
	}

	@RequestMapping(value = "decryptfiles", method = RequestMethod.GET)
	public @ResponseBody String decryptfiles(Model model) {
		System.out.println("decryptfiles starts");
		Map<String, Object> result = processDecrypt("/opt/app/input/");
		// Map<String,Object> result=processDecrypt("C:\\Users\\206699335\\OneDrive -
		// NBCUniversal\\My Documents\\Desktop\\experi\\Input");
		JSONObject json = new JSONObject(result);
		return json.toString();
	}

	private Map<String, Object> processDecrypt(String basepath) {
		System.out.println("processDecrypt starts");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// String basepath="/opt/app/input/";
			logger.info("File System Access Process starts for decrypt for : " + basepath);
			File dir = new File(basepath);
			logger.info("File System Access Process end for : " + basepath);
			logger.info("Getting all .pgp files in " + dir.getCanonicalPath());
			logger.info("All .PGP files provided by Sterling team ");
			File[] files = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".pgp");
				}
			});
			Integer i = 0;
			for (File file : files) {
				logger.info("file " + (++i) + ":" + file.getName() + " : File Size : " + file.length());
				// filelist.add(file.getName());
				String pgpfilename = basepath + file.getName();
				String txtfilename = pgpfilename.substring(0, pgpfilename.lastIndexOf("."));
				String command = "echo D@maTPhr5YcL#yk?| sudo -i gpg --output " + txtfilename + " --decrypt "
						+ pgpfilename;

				// String command="echo D@maTPhr5YcL#yk?| sudo -i gpg --output \"+txtfilename+\"
				// --decrypt \"+pgpfilename;

				ProcessBuilder processBuilder = new ProcessBuilder();
				processBuilder.command("bash", "-c", command);
				logger.info("File System Access Server authentication starts for : " + command);
				Process process = processBuilder.start();
				logger.info("File System Access Server authentication success for : " + command);
				// System.out.println("log:"+command);
				logger.info(file.getName() + " decrypted");
				logger.info("File Created : " + txtfilename);
			}
			result.put("status", "success");
			result.put("totalfiles", i);
			// result.compute("log", command);
			// result.put("result", filelist);
			System.out.println("Total files:" + i);
		} catch (Exception e) {
			result.put("status", "error:" + e.getMessage());
		}
		System.out.println("processDecrypt ends");
		return result;
	}

	// CSV file read starts
	@RequestMapping(value = "execbatchjob", method = RequestMethod.GET)
	public @ResponseBody String fileread(Model model, HttpServletRequest request) {

		logger.info("execbatchjob starts");
		HttpSession session = request.getSession();
		String ssoid = (null != session && null != session.getAttribute("")) ? session.getAttribute("uid").toString()
				: "no-ssoid";

		Map<String, Object> resultMap = csvFilesProcess(ssoid);

		for (Map.Entry<String, Object> map : resultMap.entrySet()) {
			logger.info(map.getKey() + " : " + map.getValue());
		}
		//logic for updating stateincometax report after job Run
		stateIncomeTaxService.updateStateIncomeTax();
		JSONObject json = new JSONObject(resultMap);
		logger.info("execbatchjob ends");
		return StringEscapeUtils.escapeHtml4(json.toString());
		// return json;
	}
	public Map<String, Object> csvFilesProcess(String ssoid) {
		logger.info("csvFilesProcess starts");
		Map<String, Object> resultmap = new LinkedHashMap<String, Object>();
		String basePath = "/opt/app/input/";
		String resultFilesPath = "/opt/app/";
		processDecrypt(basePath);
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e1) {
			logger.error("Exception in sleep", e1);
		}

		resultmap.put("decrypt", "success");
		resultmap.put("basepath", basePath);
		// TimeUnit.MINUTES.sleep(1);
		// Read the directory first

		File directoryPath = null;
		boolean allFilesProcessFlag = true;
		try {
			logger.info("File System Access Process starts for txt file read for : " + basePath);
			directoryPath = new File(basePath);
			logger.info("File System Access Process ends for txt file read for : " + basePath);
		} catch (Exception e) {
			logger.error("Exception in directoryPath check", e);
		}

		if (null == directoryPath) {
			logger.info("Issue with folder access or Folder not found");
			allFilesProcessFlag = false;
			resultmap.put("directoryfound", "no");
		} else {
			resultmap.put("directoryfound", "yes");
			logger.info("directory found");
			// List of all files and directories
			// File[] availableFilesList = directoryPath.listFiles();

			File[] availableFilesList = directoryPath.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".txt");
				}
			});

			if (null != availableFilesList && availableFilesList.length > 0) {
				Map<String, String> availableFilesAbsoluteNamesMap = new LinkedHashMap<>();
				Integer runCount = idtService.getRunCount();
				logger.info("existing run count : " + runCount);
				if (null == runCount) {
					runCount = 0;
				}
				runCount = runCount + 1;
				for (File file : availableFilesList) {
					String fileName = file.getName();
					availableFilesAbsoluteNamesMap.put(fileName, file.getAbsolutePath());
				}

				List<Map<String, Object>> targetTableList = null;
				try {
					targetTableList = idtService.getInterfaceFilesControl();
				} catch (SQLException e1) {
					logger.error("Exception in getInterfaceFilesControl", e1);
				}
				Map<String, String> targetTableMap = new LinkedHashMap<>();

				for (Map<String, Object> map : targetTableList) {
					targetTableMap.put(map.get("INTERFACE_FILE_TYPE").toString(),
							map.get("TARGET_INTERIM_TABLE").toString());
				}

				logger.info("targetTableMap:" + targetTableMap);

				// Clear all tables data before processing
				List<String> tableNamesToClear = new ArrayList<>();
				for (Map.Entry<String, String> entry : targetTableMap.entrySet()) {
					if (!tableNamesToClear.contains(entry.getValue())) {
						tableNamesToClear.add(entry.getValue());
					}
				}

				for (Map.Entry<String, String> entry : availableFilesAbsoluteNamesMap.entrySet()) {
					String interfaceFileName = entry.getKey().substring(0, entry.getKey().lastIndexOf("."));
					int index = StringUtils.ordinalIndexOf(interfaceFileName, "_", 2);
					if (index > 0) {
						interfaceFileName = interfaceFileName.substring(0, index);
					}

					String fileName = entry.getKey();
					String filePath = entry.getValue();
					logger.info("File name :" + fileName);
					String targetInterimTable = targetTableMap.get(interfaceFileName);
					Runtime.getRuntime().gc();
					Map<String, String> responseMap = processFile(targetInterimTable, fileName, filePath);
					resultmap.put("processFile: " + fileName, responseMap);
					if (responseMap.get("status").equals("SUCCESS")) {
						// Move file to Success folder
						String fileArchived = moveFiles(filePath, resultFilesPath + "/archive/", fileName, resultmap,
								ssoid);
						// String fileArchived = moveFiles(filePath, basePath + "\\success\\",
						// fileName,resultmap, ssoid);
						saveStatusControlTable(interfaceFileName, fileName, "Done", "X", "X", fileArchived, " ", " ",
								runCount);
					} else {
						// Issue with save the file data
						allFilesProcessFlag = false;
						logger.info("Error while saving the data for the file : " + fileName);
						// Move file to reprocess folder
						String fileArchived = moveFiles(filePath, resultFilesPath + "/reprocess/", fileName, resultmap,
								ssoid);
						// String fileArchived = moveFiles(filePath, basePath + "\\reprocess\\",
						// fileName,resultmap, ssoid);
						saveStatusControlTable(interfaceFileName, fileName, "Failed", "X", " ", fileArchived,
								responseMap.get("error"), "X", runCount);
					}
				}

			} else {
				logger.info("No files present in the diretory");
				allFilesProcessFlag = false;
				resultmap.put("filesfound", "no");
			}

		}
		logger.info("Processing completed for all files");
		if (allFilesProcessFlag) {
			int count2 = 0;
			try {
				// clear the reports table data before save
				logger.info("clear Register Tables starts");
				int count5 = idtService.clearReportsTables(ssoid);
				logger.info("clear Register Tables ends");
				logger.info("total number of records cleared : " + count5);
				resultmap.put("clearRegisterTables", "success : " + count5);
				// save to register tables
				count2 = idtService.saveToRegisterTables();
				logger.info("save to Register Tables success" + count2);
				resultmap.put("saveToRegisterTables", "success: " + count2);
			} catch (Exception e) {
				resultmap.put("saveToRegisterTables", "Error:" + e.getMessage());
				logger.error("Exception in saveToRegisterTables", e);
			}

			logger.info("Total records moved to Reports table:" + count2);
		} else {
			// Issue with some files processing, nothing to do with reports tables here
			resultmap.put("saveToRegisterTables", "Failure : failures in Interim table save");
			logger.info("records not moved to Reports table as there are failures to Interim table save");
		}
		logger.info("csvFilesProcess ends");
		return resultmap;
	}

	private String moveFiles(String filePath, String targetfolder, String fileName, Map<String, Object> resultmap,
			String ssoid) {
		String fileArchived = " ";
		Path source = null;
		Path target = null;
		try {
			source = Paths.get(filePath);
			target = Paths.get(targetfolder + fileName);
			resultmap.put("movedetails", source + ":To:" + target);
			logger.info("copyfile : " + source + " : To : " + target);
			Files.move(source, target);
			resultmap.put("movetxtfile:" + fileName, "success");
			// logger.info(ssoid+" : movetxtfile : "+fileName+" : success");
		} catch (Exception e) {
			logger.error("Exception in moving file : " + fileName + " : with exception : " + e);
			resultmap.put("moveError:" + fileName, e);
		}
		try {
			// move pgp file
			source = Paths.get(filePath + ".pgp");
			target = Paths.get(targetfolder + fileName + ".pgp");
			resultmap.put("movedetails", source + ":To:" + target);
			logger.info("copyfile : " + source + " : To : " + target);
			Files.move(source, target);
			resultmap.put("movepgpfile:" + fileName, "success");
			logger.info("archivefile : " + fileName + " : success");
		} catch (Exception e) {
			logger.error("Exception in moving file : " + fileName + " : with exception : " + e);
			resultmap.put("moveError:" + fileName, e);
		}
		fileArchived = "X";
		return fileArchived;
	}

	private void saveStatusControlTable(String interfaceFileName, String fileName, String status, String fileFound,
			String tablePopulated, String fileArchived, String errorMsg, String reprocess, int runCount) {
		try {
			idtService.saveStatusControlTable(
					new Object[] { new Timestamp(new java.util.Date().getTime()), interfaceFileName, fileName, status,
							fileFound, tablePopulated, fileArchived, errorMsg, reprocess, runCount });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateEscapeCharInFile(String filePath) {
		try {
			Scanner sc = new Scanner(new File(filePath));
			StringBuffer buffer = new StringBuffer();
			while (sc.hasNextLine()) {
				buffer.append(sc.nextLine() + System.lineSeparator());
			}
			String fileContents = buffer.toString();
			sc.close();
			fileContents = fileContents.replaceAll("\\|","|");
			// instantiating the FileWriter class
			FileWriter writer = new FileWriter(filePath);
			writer.append(fileContents);
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			System.out.println("error in updateEscapeCharInFile:" + e.getMessage());
		}

	}

	private Map<String, String> processFile(String targetInterimTable, String fileName, String filePath) {
		FileReader filereader = null;
		CSVParser parser = null;
		CSVReader csvReader = null;
		
		Map<String, String> resultMap = new HashMap<>();
		//updateEscapeCharInFile(filePath);
		try {
			filereader = new FileReader(filePath);
			parser = new CSVParserBuilder().withSeparator('|').build();
			
			csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
			// Read all data at once
			
			
			
	
			//List<String[]> allData = csvReader.readAll();
			int counter=0;
			String[] data;
			List<Object[]> objectList=new ArrayList<>();
			Map<String, String> savedata=null;
			Runtime.getRuntime().gc();	
		while((data=csvReader.readNext())!=null)
		{
			counter++;
			
			
			objectList.add(data);
			if(counter%50000==0)
			{
				int totalrecords = objectList.size();
				 savedata=saveCSVData(totalrecords, targetInterimTable, objectList);
				
				objectList.clear();
				
				//System.gc();
				Runtime.getRuntime().gc();
				objectList=new ArrayList<>(1);
			}
		}
		
		int totalrecords = objectList.size();
		System.out.println(totalrecords);
		 savedata=saveCSVData(totalrecords, targetInterimTable, objectList);
		 objectList.clear();//
		 //System.gc();
		 Runtime.getRuntime().gc();
		 objectList=new ArrayList<>(1);
		  
			//List<Object[]> objectList = new ArrayList<>(allData);

			
			
			System.out.println("Total records to be updated:" + (objectList.size() ));

			/*
			 * // Clear table data before insert int deletedRecords =
			 * searchDao.deleteRecords(targetInterimTable); System.out.println(
			 * "Number of records deleted from the table :" + targetInterimTable + " is :" +
			 * deletedRecords);
			 */

			return savedata;

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "FAILURE");
			resultMap.put("error", e.getMessage());
		} finally {
			try {
				if (null != filereader) {
					filereader.close();
				}
				if (null != csvReader) {
					csvReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	private Map<String, String> saveCSVData(int totalrecords, String targetInterimTable, List<Object[]> objectList) {
		Map<String, String> resultMap = new HashMap<>();
		try {
			/*
			 * int count = 0; for (int i = 1; i <= totalrecords; i = i + 10000) { int
			 * lastindex = totalrecords - i > 10000 ? i + 10000 : totalrecords;
			 * System.out.println("update batch from : " + i + " - " + (lastindex)); int[]
			 * updated = idtService.saveBatchRecords(targetInterimTable,
			 * objectList.subList(i, lastindex)); count = count + updated.length; }
			 */
			int count = idtService.saveBatchRecords(totalrecords, targetInterimTable, objectList);
			System.out.println("Total records updated : " + (count));
			resultMap.put("status", "SUCCESS");
		} catch (Exception e) {
			resultMap.put("status", "FAILURE");
			int errorLength = e.getMessage().length() >= 1000 ? 998 : e.getMessage().length();
			resultMap.put("error", e.getMessage().substring(0, errorLength - 1));
			System.out.println("Error in save csv data:" + e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping("/getIncomeTaxAssetRegHeadings")
	public @ResponseBody String getHeadingNames()
	{
		List<String> headings=new ArrayList<>();
		
		List<String[]> headingList=idtService.getTableHeadings();
		for(String[] h: headingList)
		{
			
			headings.add(h[0]);
		}
		Gson gson=new Gson();
		String jsonH=gson.toJson(headings, ArrayList.class);
		return jsonH;
	}

}

