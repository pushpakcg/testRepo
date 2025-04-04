package com.code2java.model;

import org.springframework.stereotype.Component;

@Component
public class FMVModel {

	
	private String year5;
	private String year6;
	private String year7;
	private String year8;
	private String year9;
	private String year10;
	private String year11;
	private String year12;
	private String year15;
	private String year20;
	private String computers;
	private String untrended6year;
	private String modelyear;
	
	public FMVModel() {
		
	}
	
	public FMVModel(String year5, String year6, String year7, String year8, String year9, String year10, String year11,
			String year12, String year15, String year20, String computers, String untrended6year,String modelyear) {
		super();
		
		this.year5 = year5;
		this.year6 = year6;
		this.year7 = year7;
		this.year8 = year8;
		this.year9 = year9;
		this.year10 = year10;
		this.year11 = year11;
		this.year12 = year12;
		this.year15 = year15;
		this.year20 = year20;
		this.computers = computers;
		this.untrended6year = untrended6year;
		this.modelyear=modelyear;
	}



	public String getYear5() {
		return year5;
	}

	public void setYear5(String year5) {
		this.year5 = year5;
	}

	public String getYear6() {
		return year6;
	}

	public void setYear6(String year6) {
		this.year6 = year6;
	}

	public String getYear7() {
		return year7;
	}

	public void setYear7(String year7) {
		this.year7 = year7;
	}

	public String getYear8() {
		return year8;
	}

	public void setYear8(String year8) {
		this.year8 = year8;
	}

	public String getYear9() {
		return year9;
	}

	public void setYear9(String year9) {
		this.year9 = year9;
	}

	public String getYear10() {
		return year10;
	}

	public void setYear10(String year10) {
		this.year10 = year10;
	}

	public String getYear11() {
		return year11;
	}

	public void setYear11(String year11) {
		this.year11 = year11;
	}

	public String getYear12() {
		return year12;
	}

	public void setYear12(String year12) {
		this.year12 = year12;
	}

	public String getYear15() {
		return year15;
	}

	public void setYear15(String year15) {
		this.year15 = year15;
	}

	public String getYear20() {
		return year20;
	}

	public void setYear20(String year20) {
		this.year20 = year20;
	}

	public String getComputers() {
		return computers;
	}

	public void setComputers(String computers) {
		this.computers = computers;
	}

	public String getUntrended6year() {
		return untrended6year;
	}

	public void setUntrended6year(String untrended6year) {
		this.untrended6year = untrended6year;
	}

	public String getModelyear() {
		return modelyear;
	}

	public void setModelyear(String modelyear) {
		this.modelyear = modelyear;
	}

	@Override
	public String toString() {
		return "FMVModel [year5=" + year5 + ", year6=" + year6 + ", year7=" + year7 + ", year8=" + year8 + ", year9="
				+ year9 + ", year10=" + year10 + ", year11=" + year11 + ", year12=" + year12 + ", year15=" + year15
				+ ", year20=" + year20 + ", computers=" + computers + ", untrended6year=" + untrended6year
				+ ", modelyear=" + modelyear + "]";
	}

	

	
	
	
	
}
