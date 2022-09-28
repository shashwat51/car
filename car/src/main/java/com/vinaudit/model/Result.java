package com.vinaudit.model;

import java.util.List;

public class Result {

	public List<vinmaster> vin;
	public Float estimatedPrice;

	public Result(List<vinmaster> vinlist, Float price) {
		this.vin = vinlist;
		this.estimatedPrice = price;
	}
	
	
}
