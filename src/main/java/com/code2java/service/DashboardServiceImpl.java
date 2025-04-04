package com.code2java.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code2java.dao.DataTableDAO;
@Service("dashboardservice")
public class DashboardServiceImpl implements DashboardService{
	
	@Autowired
	private DataTableDAO dataTableDao;

	@Override
	public List<String[]> getDashboardDesAndValues() {
		List<String[]> resultList = new ArrayList<String[]>();
		Connection conn;
		PreparedStatement ps;
		try {
			String query = "SELECT SUM(PROP_CORP_BOOK_COST_BASIS) as PROP_CORP_BOOK_COST_BASIS, prop_gl_category_description FROM PROP_TAX_ASSET_LIST_REPORT GROUP BY prop_gl_category_description ORDER BY PROP_CORP_BOOK_COST_BASIS desc";
			conn = dataTableDao.getConnection();
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String[] arr = new String[2];
				arr[0] = rs.getString("PROP_CORP_BOOK_COST_BASIS");
				arr[1] = rs.getString("prop_gl_category_description");
				//System.out.println(arr[0]+"-----"+arr[1]);
				resultList.add(arr);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultList;
	}
	

}
