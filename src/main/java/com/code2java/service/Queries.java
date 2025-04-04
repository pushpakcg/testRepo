package com.code2java.service;

public class Queries {
	
	//Income Tax Asset Register Query
	public static String query1="INSERT INTO inc_tax_asset_reg_report\r\n" +
			 "(inc_uniqueasset,inc_companycode,inc_assetnumber,inc_subnumber,inc_legunique,inc_assetdescription,inc_assetclasss,inc_assetclassdescription,INC_LEGACYCATEGORYCODE,inc_entereddate,inc_installdate,inc_assetlife,inc_remaininglife,inc_taxapc,inc_accumulateddepreciation,inc_yeartodatedeprec,inc_taxnbv,inc_bonusdepreciation,inc_taxytdbonusdepr,inc_firstyeardepreciated,inc_lastyeardepreciated,inc_lastmonthdepreciated,inc_neworused,INC_TAXDEPRMETHOD,INC_TAXDEPRCONVENTION,INC_PPTAX,inc_currentaccountingyear,inc_corpinstalldate,inc_corplife,inc_corpbookapc,inc_corpbookaccumreserve,inc_corpnbv,INC_EVGRP5,INC_BUILDING,INC_PL_DESCRIPTION,INC_PROJECT_NUMBER,INC_LEGACY_PROJECT_NO, INC_ASSETNOTE)\r\n" + 
			"SELECT ara01_com || ara01_ast# || ara01_sub#,ara01_com,ara01_ast#,ara01_sub#,ara01_legunique,ara01_desc,ara01_class,ara01_clsdesc,LEGACY_CATEGORY_CODE,ara01_crtdate,ara10_oprread,ara10_ul,ara10_reml,ara10_apc,ara10_ad,ara10_ytd,ara10_nbv, ara10_bonus + ara10_ytdbonus,ara10_ytdbonus,ara10_ordprstdat,substr(ara10_lstdpryrpd,4,4),substr(ara10_lstdpryrpd,1,2),ara01_nu,TAX_DEPRECIATION_METHOD,TAX_DEP_CONVENTION,ara01_pptax,ara01_curyrpd,ara01_captdate,ara01_ul,ara01_apc,ara01_ad,ara01_nbv,ARA01_EVGRP5,ara01_buil,ara01_pldesc,ara01_prj#,ara01_legprnum,ara01_assetnote \r\n" + 
			"FROM   interim_astregact_01 tab1 LEFT OUTER JOIN interim_astregact_10 tab2\r\n" +  
			"ON     tab1.ara01_com = tab2.ara10_com\r\n" +
			"AND    tab1.ara01_ast# = tab2.ara10_ast#\r\n" +
			"AND    tab1.ara01_sub# = tab2.ara10_sub#\r\n" +
			"LEFT OUTER JOIN asset_class_guidelines_report tab3\r\n" +
			"ON    tab1.ara01_class = tab3.asset_class\r\n" +
			"WHERE ARA01_DEACT ='00000000' OR ARA01_DEACT IS NULL\r\n"+
			"AND ARA10_DEACT ='00000000' OR ARA10_DEACT IS NULL";
	
	//Income Tax Asset Retirement Query 
	public static String query2="INSERT INTO inc_tax_asset_retire_report\r\n" +
			"(uniqueasset,companycode,assetnumber,subnumber,legunique,assetdescription,assetclasss,assetclassdescription,legacycategorycode,entereddate,installdate,assetlife,remaininglife,taxapc,bonusdepreciation,taxytdbonusdepr,firstyeardepreciated,lastyeardepreciated,lastmonthdepreciated,neworused,taxdeprmethod,taxdeprconvention,pptax,currentaccountingyear,corpinstalldate,corplife,corpbookapc,corpbookaccumreserve,corpnbv,retirementdate,assignment,retirementdescription,retirementcost,retirementad,retirementytd,retirementnbv,gain,loss,bonus,EVGRP5,BUILDING,PL_DESCRIPTION,PROJECT_NUMBER,LEGACY_PROJECT_NO,EVGRP3,EVGRP4,ASSETNOTE)\r\n" +
			"SELECT ret10_com || ret10_ast# || ret10_sub#,ret10_com,ret10_ast#,ret10_sub#,ara01_legunique,ara01_desc,ara01_class,ara01_clsdesc,legacy_category_code,ara01_crtdate,ara10_oprread,ara10_ul,ara10_reml,ara10_apc,ara10_bonus + ara10_ytdbonus,ara10_ytdbonus,ara10_ordprstdat,Substr(ara10_lstdpryrpd, 4, 4),Substr(ara10_lstdpryrpd, 1, 2),ara01_nu,tax_depreciation_method,tax_dep_convention,ara01_pptax,ara01_curyrpd,ara01_captdate,ara01_ul,ara01_apc,ara01_ad,ara01_nbv,ret10_retdat,ret10_assignment,ret10_rettext,ret10_retapc,ret10_retad,ret10_ytd,ret10_retnbv,ret10_gain,ret10_loss,ret10_bonus, ARA01_EVGRP5,ara01_buil,ara01_pldesc,ara01_prj#,ara01_legprnum,ARA01_EVGRP3, ARA01_EVGRP4,ara01_assetnote \r\n" +
			"FROM   interim_astret_10 tab1 LEFT OUTER JOIN interim_astregact_01 tab2 \r\n" +
			"ON tab1.ret10_com = tab2.ara01_com \r\n" +
			"AND tab1.ret10_ast# = tab2.ara01_ast#\r\n" +
			                       "AND tab1.ret10_sub# = tab2.ara01_sub#\r\n" +
			       "LEFT OUTER JOIN interim_astregact_10 tab3\r\n" +
			                    "ON tab1.ret10_com = tab3.ara10_com\r\n" +
			                       "AND tab1.ret10_ast# = tab3.ara10_ast#\r\n" +
			                       "AND tab1.ret10_sub# = tab3.ara10_sub#\r\n" +
			       "LEFT OUTER JOIN asset_class_guidelines_report tab4\r\n" +
			                    "ON tab2.ara01_class = tab4.asset_class";
			 
	
	//ADS Asset Register Query
	public static String query3="INSERT INTO ads_tax_asset_register_report\r\n" +
			 "(ads_uniqueasset,ads_companycode,ads_assetnumber,ads_subnumber,ads_legunique,ads_assetdescription,ads_assetclasss,ads_assetclassdescription,ADS_LEGACYCATEGORYCODE,ads_entereddate,ads_installdate,ads_assetlife,ads_remaininglife,ads_taxapc,ads_accumulatedreserve,ads_yeartodatedeprec,ads_taxnbv,ads_firstyeardepreciated,ads_lastyeardepreciated,ads_lastmonthdepreciated,ads_neworused,ADS_TAXDEPRMETHOD,ADS_TAXDEPRCONVENTION,ads_currentaccountingyear,ads_corpinstalldate,ads_corplife,ads_corpbookapc,ads_corpbookaccumreserve,ads_corpnbv)\r\n" +
			"SELECT ara01_com || ara01_ast# || ara01_sub#,ara01_com,ara01_ast#,ara01_sub#,ara01_legunique,ara01_desc,ara01_class,ara01_clsdesc,LEGACY_CATEGORY_CODE,ara01_crtdate,ara20_oprread,ara20_ul,ara20_reml,ara20_apc,ara20_ad,ara20_ytd,ara20_nbv,ara20_ordprstdat,substr(ara20_lstdpryrpd,4,4),substr(ara20_lstdpryrpd,1,2),ara01_nu,TAX_DEPRECIATION_METHOD,TAX_DEP_CONVENTION,ara20_curyrpd,ara01_captdate,ara01_ul,ara01_apc,ara01_ad,ara01_nbv \r\n" +
			"FROM   interim_astregact_01 TAB1 LEFT OUTER JOIN interim_astregact_20 TAB2\r\n" +
			"ON  TAB1.ara01_com = TAB2.ara20_com\r\n" +
			"AND TAB1.ara01_ast# = TAB2.ara20_ast#\r\n" +
			"AND TAB1.ara01_sub# = TAB2.ara20_sub#\r\n" +
			"LEFT OUTER JOIN asset_class_guidelines_report tab3\r\n" +
			"ON   tab1.ara01_class = tab3.asset_class\r\n" +
			"WHERE ARA01_DEACT ='00000000' OR ARA01_DEACT IS NULL";
			
	
	        
	
	
	//Tax Projection Report Query
	
	  public static String query4="INSERT INTO inc_tax_proj_report\r\n"+
			  "(for_uniqueasset,for_companycode,for_assetnumber ,for_subnumber,for_legunique,for_assetdescription,for_assetclasss,for_assetclassdescription,FOR_LEGACYCATEGORYCODE,for_entereddate,for_installdate ,For_assetlife,For_remaininglife,for_taxapc ,for_accumulatedreserve ,for_yeartodatedeprec ,for_taxnbv , for_bonusdepreciation ,FOR_TAXYTDBONUSDEPR,for_projection_year1 ,for_projection_year2 ,for_projection_year3 ,for_projection_year4 ,for_projection_year5 ,for_firstyeardepreciated,for_lastyeardepreciated,for_lastmonthdepreciated,for_neworused,FOR_TAXDEPRMETHOD,FOR_TAXDEPRCONVENTION,for_currentaccountingyear,for_corpinstalldate,for_corplife,for_corpbookapc ,for_corpbookaccumreserve ,for_corpnbv)\r\n" +
              "SELECT ara01_com||ara01_ast#||ara01_sub#,ara01_com,ara01_ast#,ara01_sub#,ara01_legunique, ara01_desc, ara01_class, ara01_clsdesc, LEGACY_CATEGORY_CODE, ara01_crtdate, ara10_oprread, ara10_ul, ara10_reml, ara10_apc, ara10_ad, ara10_ytd, ara10_nbv,ara10_bonus + ara10_ytdbonus,ara10_ytdbonus,prj10_yr01,prj10_yr02,prj10_yr03,prj10_yr04,prj10_yr05,ara10_ordprstdat,substr(ara10_lstdpryrpd,4,4),substr(ara10_lstdpryrpd,1,2),ara01_nu,TAX_DEPRECIATION_METHOD,TAX_DEP_CONVENTION, ara01_curyrpd,ara01_captdate,ara01_ul,ara01_apc,ara01_ad,ara01_nbv \r\n" +
              "FROM   interim_astregact_01 tab1 LEFT OUTER JOIN interim_astregact_10 tab2 \r\n" +
               "ON  tab1.ara01_com = tab2.ara10_com \r\n" +
              "AND    tab1.ara01_ast# = tab2.ara10_ast# \r\n" +
              "AND    tab1.ara01_sub# = tab2.ara10_sub# \r\n" +
              "LEFT OUTER JOIN  interim_astprj_10 tab3 \r\n" +
              "ON tab1.ara01_com = tab3.prj10_com \r\n" +
              "AND tab1.ara01_ast# = tab3.prj10_ast# \r\n" +
               "AND tab1.ara01_sub# = tab3.prj10_sub# \r\n" +
              "LEFT OUTER JOIN asset_class_guidelines_report tab4 \r\n" +
               "ON tab1.ara01_class = tab4.asset_class \r\n" +
               "WHERE ARA01_DEACT ='00000000' OR ARA01_DEACT IS NULL \r\n" +
               "AND ARA10_DEACT='00000000' OR ARA10_DEACT IS NULL";
	 
	
	//Property Tax Asset Listing report Query
	  public static String query5="INSERT INTO prop_tax_asset_list_report\r\n" +	

				
				"(prop_entity,prop_unique_asstno,prop_asset_number,prop_asset_sub_number,prop_internal_asst_no,PROP_LEGACY_ASSET_NUMBER,PROP_LEGACY_ASSET_SUBNUMBER,prop_asset_description,prop_country,prop_state,prop_county,prop_city,prop_street,prop_zip_code,prop_building,prop_pl_description,prop_entered_date,prop_sap_asset_class,prop_sap_asset_class_des,prop_gl_category_description,prop_corporate_life,prop_install_date,prop_corp_book_cost_basis,prop_corp_book_accum_rsrv,prop_net_book_value,prop_accounting_year,prop_tax_install_date,prop_tax_cost_basis,prop_tax_ntv,prop_pptax,prop_invoice_number,prop_project_number,prop_purchase_order,prop_wbse,prop_profit_center,prop_cost_center,prop_manufacture,prop_vendor,prop_attraction_id,prop_geo_location,prop_department,prop_company_name,prop_serial_number,prop_inventory_number,prop_quantity,prop_physical_location_co_name,prop_type_name,prop_legacy_project_no,prop_tracking_number,prop_evgrp5,prop_evgrp3, prop_evgrp4, prop_assetnote)\r\n" +	
				"SELECT ara01_com,ara01_com || ara01_ast# || ara01_sub#,ara01_ast#,ara01_sub#,ara01_legintast#,ARA01_LEGAST,ARA01_LEGASTSUB,ara01_desc,ara01_cont,ara01_stat,ara01_coun,ara01_city,ara01_strt,ara01_zip,ara01_buil,ara01_pldesc,ara01_crtdate,ara01_class,ara01_clsdesc,ara01_glcatdesc,ara01_ul,ara01_captdate,ara01_apc,ara01_ad,ara01_nbv,ara01_curyrpd,ara10_oprread,ara10_apc,ara10_nbv,ara01_pptax,ara01_invoice,ara01_prj#,ara01_po,ara01_wbse,ara01_pc,ara01_cc,ara01_manufact,ara01_vendor,ara01_attrid,ara01_geoloc,ara01_dept,ara01_comnam,ara01_serial#,ara01_inventory#,ara01_quantity,ara01_plcnam,ara01_typnam,ara01_legprnum,ara01_tracking#,ARA01_EVGRP5,ARA01_EVGRP3, ARA01_EVGRP4, ara01_assetnote \r\n" +	
				"FROM   interim_astregact_01 TAB1 LEFT OUTER JOIN interim_astregact_10 TAB2\r\n" +	
				"ON  TAB1.ara01_com = TAB2.ara10_com\r\n" +	
				"AND TAB1.ara01_ast# = TAB2.ara10_ast#\r\n" +	
				"AND TAB1.ara01_sub# = TAB2.ara10_sub#\r\n" +	
				"WHERE ARA01_DEACT ='00000000' OR ARA01_DEACT IS NULL\r\n"+	
				"AND ARA10_DEACT ='00000000' OR ARA10_DEACT IS NULL";  
	
	
	//Property Tax Asset Retirement Report Query
	public static String query6="INSERT INTO prop_tax_asset_retirement_report\r\n" +
			"(pret_statestate,pret_entity_name,pret_entity,pret_room_and_rack,pret_country,pret_state,pret_county,pret_city,pret_street,pret_zip_code,pret_building,pret_unique_asset,PRet_LEGACY_ASSET_NUMBER,PRet_LEGACY_ASSET_SUBNUMBER,pret_asset_description,pret_assetclass,pret_class_description,pret_gl_category_description,pret_entered_date,pret_install_date,pret_disposal_date,pret_assignment,pret_first_cost,pret_dsiposal_cost,pret_tax_install_date,pret_tax_cost_basis,pret_EVGRP5,pret_PL_DESCRIPTION,pret_PROJECT_NUMBER,pret_LEGACY_PROJECT_NO,pret_EVGRP3,pret_EVGRP4,pret_ASSETNOTE)\r\n" +
			"SELECT ara01_stat,ara01_comnam,ara01_com,ara01_rmrc,ara01_cont,ara01_stat,ara01_coun,ara01_city,ara01_strt,ara01_zip,ara01_buil,ara01_com || ara01_ast# || ara01_sub#,ARA01_LEGAST,ARA01_LEGASTSUB, ara01_desc,ara01_class,ara01_clsdesc,ara01_glcatdesc,ara01_crtdate,ara01_captdate,ret01_retdat,ret01_assignment,ara01_apc,ret01_retapc,ara10_oprread,ara10_apc,ARA01_EVGRP5,ara01_pldesc,ara01_prj#,ara01_legprnum,ARA01_EVGRP3, ARA01_EVGRP4,ara01_assetnote \r\n" +
			"FROM  interim_astret_01 TAB1 LEFT OUTER JOIN interim_astregact_01 TAB2\r\n" + 
			"ON TAB2.ara01_com = TAB1.ret01_com\r\n" +
			       "AND TAB2.ara01_ast# = TAB1.ret01_ast#\r\n" +
			       "AND TAB2.ara01_sub# = TAB1.ret01_sub#\r\n" +
			"LEFT OUTER JOIN interim_astregact_10 TAB3\r\n" +
			       "ON  TAB2.ara01_com = TAB3.ara10_com\r\n" +
			       "AND TAB2.ara01_ast# = TAB3.ara10_ast#\r\n" +
			       "AND TAB2.ara01_sub# = TAB3.ara10_sub#";
			 
			        
	
	//State tax Asset Listing Query
	public static String query7="INSERT INTO state_tax_asset_listing_report\r\n" +
			"(st_businessunit,st_uniqueassetkey,st_estimateddateofasset,st_assetdescription,st_geocodestate,st_geocodecountry,st_bookcost,st_accumulatedbookdepr)\r\n" +
			"SELECT ara01_com,ara01_com || ara01_ast# || ara01_sub#,ara01_captdate,ara01_desc,ara01_stat,ara01_cont,ara01_apc,ara01_ad \r\n" +
			"FROM   interim_astregact_01\r\n"+ 
	        "WHERE ARA01_DEACT ='00000000'";
	
	public static String query8="insert into FAWEB.ASSET_TRANSFER_REPORT_PHYSICAL_LOCATION \r\n"
			+ "(COMPANY,	ASSET_NUMBER,	SUB_NUMBER,	CHANGE_DATE,	\r\n"
			+ "TYPE,	FROM_LOCATION,	FROM_PHYSICALLOCATIONDESCRIPTION,	FROM_COUNTRY,	FROM_STATEREGION,	\r\n"
			+ "FROM_POSTAL_CODE,	FROM_COUNTY,	FROM_CITY,	FROM_STREET,	FROM_BUILDING,	FROM_FLOOR,	\r\n"
			+ "FROM_ROOM,	FROM_RACK,	TO_LOCATION,	TO_PHYSICALLOCATIONDESCRIPTION,	TO_COUNTRY,	TO_STATEREGION,	TO_POSTAL_CODE,\r\n"
			+ "TO_COUNTY,	TO_CITY,	TO_STREET,	TO_BUILDING,	TO_FLOOR,	TO_ROOM,	TO_RACK)   \r\n"
			+ "select \r\n"
			+ "substr(INTERIM_ASSET_TRANSFER_PL.object_value,1,4) AS company ,\r\n"
			+ "substr(INTERIM_ASSET_TRANSFER_PL.object_value,9,8) AS asset_number,\r\n"
			+ "substr(INTERIM_ASSET_TRANSFER_PL.object_value,17,4) AS sub_number,\r\n"
			+ "INTERIM_ASSET_TRANSFER_PL.\"DATE\" as change_date,\r\n"
			+ "INTERIM_ASSET_TRANSFER_PL.field_name as type,\r\n"
			+ "INTERIM_ASSET_TRANSFER_PL.old_value as from_Location,\r\n"
			+ "            t2_old.physicallocationdescription as from_physicallocationdescription, \r\n"
			+ "			t2_old.COUNTRY as from_COUNTRY,\r\n"
			+ "            t2_old.STATEREGION as from_STATEREGION,\r\n"
			+ "            t2_old.postal as from_postal_code, \r\n"
			+ "        t2_old.COUNTYDISTRICT as from_COUNTY,\r\n"
			+ "        t2_old.CITY as from_CITY,\r\n"
			+ "        t2_old.STREET as from_STREET,\r\n"
			+ "        t2_old.BUILDING as from_BUILDING,\r\n"
			+ "			t2_old.FLOOR as from_FLOOR,\r\n"
			+ "            t2_old.ROOM as from_ROOM,\r\n"
			+ "            t2_old.ROOMRACK as from_rack,\r\n"
			+ "        INTERIM_ASSET_TRANSFER_PL.new_value as to_Location, \r\n"
			+ "			t2_new.physicallocationdescription as to_physicallocationdescription,\r\n"
			+ "            t2_new.COUNTRY as to_COUNTRY,t2_new.STATEREGION as to_STATEREGION,\r\n"
			+ "        t2_new.postal as to_postal_code, \r\n"
			+ "        t2_new.COUNTYDISTRICT as to_COUNTY,\r\n"
			+ "        t2_new.CITY as to_CITY,\r\n"
			+ "        t2_new.STREET as to_STREET,\r\n"
			+ "        t2_new.BUILDING as to_BUILDING,\r\n"
			+ "        t2_new.FLOOR as to_FLOOR,\r\n"
			+ "        t2_new.ROOM as to_ROOM,\r\n"
			+ "        t2_new.ROOMRACK as to_rack\r\n"
			+ "			from INTERIM_ASSET_TRANSFER_PL \r\n"
			+ "        left join physical_location t2_old on INTERIM_ASSET_TRANSFER_PL.old_value=t2_old.physicallocation\r\n"
			+ "        left join physical_location t2_new on INTERIM_ASSET_TRANSFER_PL.new_value=t2_new.physicallocation";
			
	public static String query9="INSERT INTO ASSET_TRANSFER_ALL_TRANSACTION_REPORT (\r\n"
			+ "    AT_COMPANY_CODE, AT_ASSET, AT_SUB_NUM, AT_PARTNER_COMP_CODE, AT_ACQ_OR_RETR_ASSET_NO, \r\n"
			+ "    AT_ACQ_OR_RETR_ASSET_SUB_NO, AT_TRANSACTION_TYPE, AT_TR_TRANS_TYPE_DESC, AT_POSTING_DATE, \r\n"
			+ "    AT_DESCRIPTION, AT_ASSET_CLASS, AT_ASSET_CLASS_DESCRIPTION, AT_GL_ACCOUNT_CATEGORY, \r\n"
			+ "    AT_BAL_SH_ACCT_APC, AT_CAPITALIZED_ON, AT_COST_CENTER, AT_LOCATION, AT_COUNTRY, \r\n"
			+ "    AT_STATEREGION, AT_POSTAL, AT_COUNTYDISTRICT, AT_CITY, AT_STREET, AT_BUILDING, \r\n"
			+ "    AT_PROJECT_DEFINITION, AT_PROJ_DEF, AT_DOC_NUM, AT_ASSET_VAL_DATE, AT_DEPRECIATION_AREA, \r\n"
			+ "    AT_AMOUNT_POSTED, AT_ORD_DEPRECIATION_ON_TRANS, AT_SPEC_DEP_ON_TRANS, AT_COST_CENT\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "    AT_COMPANY_CODE, AT_ASSET, AT_SUB_NUM, AT_PARTNER_COMP_CODE, AT_ACQ_OR_RETR_ASSET_NO, \r\n"
			+ "    AT_ACQ_OR_RETR_ASSET_SUB_NO, AT_TRANSACTION_TYPE, TR_TRANS_TYPE_DESC, AT_POSTING_DATE, \r\n"
			+ "    AT_DESCRIPTION, AT_ASSET_CLASS, ASSET_CLASS_DESCRIPTION, GL_ACCOUNT_CATEGORY, AT_BAL_SH_ACCT_APC, \r\n"
			+ "    AT_CAPITALIZED_ON, AT_COST_CENTER, AT_LOCATION, COUNTRY, STATEREGION, POSTAL, COUNTYDISTRICT, \r\n"
			+ "    CITY, STREET, BUILDING, AT_PROJECT_DEFINITION, AT_PROJ_DEF, AT_DOC_NUM, AT_ASSET_VAL_DATE, \r\n"
			+ "    AT_DEPRECIATION_AREA, AT_AMOUNT_POSTED, AT_ORD_DEPRECIATION_ON_TRANS, AT_SPEC_DEP_ON_TRANS, AT_COST_CENT\r\n"
			+ "FROM \r\n"
			+ "    INTERIM_ASSET_TRANSFER_ALL_TXN\r\n"
			+ "INNER JOIN \r\n"
			+ "    TRANSFER_TRANSACTION_TYPE ON INTERIM_ASSET_TRANSFER_ALL_TXN.AT_TRANSACTION_TYPE = TRANSFER_TRANSACTION_TYPE.TR_TRANS_TYPE\r\n"
			+ "INNER JOIN \r\n"
			+ "    asset_class_guidelines_report ON INTERIM_ASSET_TRANSFER_ALL_TXN.AT_ASSET_CLASS = asset_class_guidelines_report.ASSET_CLASS\r\n"
			+ "INNER JOIN \r\n"
			+ "    physical_location ON INTERIM_ASSET_TRANSFER_ALL_TXN.AT_LOCATION = physical_location.PHYSICALLOCATION\r\n";
			
	public static String query10="insert into state_income_tax_report (SIT_ASSET_ID,SIT_Department,SIT_LEGALFEIN,SIT_LEGALNAME,SIT_TAXFEIN,SIT_TAXNAME,SIT_IN_SERVICE_YEAR,\r\n"
			+ "			SIT_acq_date,SIT_special_note,SIT_IN_SERVICE_MONTH,SIT_BONUS_TAG,SIT_ASSET_TYPE,SIT_FEDERAL_TAX_LIFE,SIT_FEDERAL_TAX_METHOD,SIT_FEDERAL_TAX_CONVENTION,\"SIT_COST\",SIT_FEDERAL_TAX_ACCUM,SIT_ENTERED_DATE,SIT_YEARTODATEDEPREC,SIT_TAXYTDBONUSDEPR,SIT_BONUSDEPRECIATION,SIT_LEGACYCATEGORYCODE) \r\n"
			+ "			select INC_UNIQUEASSET,INC_COMPANYCODE,FEDERAL_ID,LEGAL_ENTITY_NAME,tax_fein,tax_name,(select CASE when INC_INSTALLDATE <> '00000000' then EXTRACT(YEAR FROM TO_DATE(INC_INSTALLDATE,'MM-DD-YYYY' )) else 0 end FROM dual) \r\n"
			+ "		,inc_installdate,inc_assetdescription,(select CASE when INC_INSTALLDATE <> '00000000' then EXTRACT(MONTH FROM TO_DATE(INC_INSTALLDATE,'MM-DD-YYYY' )) else 0 end FROM dual),(select case when (inc_bonusdepreciation) <> 0  AND (inc_taxapc) <> 0  then ROUND(ABS(inc_bonusdepreciation/inc_taxapc)*100) else 0  end from dual) \r\n"
			+ "        ,inc_assetlife||'YR'||' '||TAX_DEPRECIATION_METHOD_ABBR||' '||TAX_DEP_CONVENTION_ABBR,\r\n"
			+ "\r\n"
			+ "        (select case when inc_assetclasss ='5001' or  inc_assetclasss='5801' then\r\n"
			+ "        (select tax_asset_life from asset_class_guidelines_report where inc_assetclasss=asset_class and \r\n"
			+ "        asset_class in ('5001','5801'))\r\n"
			+ "        else inc_assetlife end from dual )as inc_assetlife,\r\n"
			+ "\r\n"
			+ "        TAX_DEPRECIATION_METHOD_ABBR,TAX_DEP_CONVENTION_ABBR,\r\n"
			+ "        INC_TAXAPC,\r\n"
			+ "        INC_ACCUMULATEDDEPRECIATION, INC_ENTEREDDATE,INC_YEARTODATEDEPREC,\r\n"
			+ "                   INC_TAXYTDBONUSDEPR,\r\n"
			+ "                   INC_BONUSDEPRECIATION,INC_LEGACYCATEGORYCODE\r\n"
			+ "			from inc_tax_asset_reg_report \r\n"
			+ "		left outer join clrs_fein_lookup_table on comp_id=inc_companycode  \r\n"
			+ "		left outer join tax_legal_name on bu=inc_companycode\r\n"
			+ "		left outer join asset_class_guidelines_report on inc_assetclasss=ASSET_CLASS where COMCAST_PWC='0'\r\n"
			+ "";
			

}
