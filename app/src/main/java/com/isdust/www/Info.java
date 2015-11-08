package com.isdust.www;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable
{
	private static final long serialVersionUID = -1010711775392052966L;
	private double latitude;
	private double longitude;
	private int imgId;
	private String name;
	private String distance;
	private int zan;

	//ATM
	public static List<Info> infos1 = new ArrayList<Info>();
	static
	{
		infos1.add(new Info(36.008682, 120.135915, R.drawable.atm01, "ATM机",
				"土建学院楼前", 0));
		infos1.add(new Info(36.011901, 120.135659, R.drawable.atm02, "ATM机",
				"B餐一楼西门口", 0));
		infos1.add(new Info(36.012022, 120.129905, R.drawable.atm03, "ATM机",
				"繁星广场", 0));
		infos1.add(new Info(36.008022, 120.12721, R.drawable.atm04, "ATM机",
				"J14 ATM机", 0));
		infos1.add(new Info(36.010755, 120.125692, R.drawable.atm05, "ATM机",
				"维克超市西门口", 0));

	}
//library
	public static List<Info> infos2 = new ArrayList<Info>();
	static
	{
		infos2.add(new Info(36.007996, 120.127026, R.drawable.alib, "图书馆",
				"J14西楼的2、3层", 0));
	}
//hospital
	public static List<Info> infos3 = new ArrayList<Info>();
	static
	{
		infos3.add(new Info(36.011686, 120.128185,R.drawable.ahos, "校医院",
				"繁星广场西侧", 0));
	}
	//express
	public static List<Info> infos4 = new ArrayList<Info>();
	static
	{
		infos4.add(new Info(36.011978,120.137155, R.drawable.aexp1, "圆通快递",
				"B餐继续向东走", 0));
		infos4.add(new Info(36.004301, 120.13022, R.drawable.aexp2, "中通快递",
				"南门过桥后向西走100米", 0));
		infos4.add(new Info(36.010894,120.126069, R.drawable.aexp3, "韵达快递",
				"维克超市内", 0));
		infos4.add(new Info(36.011233,120.129878, R.drawable.aexp4, "申通快递",
				"A餐三楼菜鸟驿站", 0));
	}
	//print
	public static List<Info> infos5 = new ArrayList<Info>();
	static
	{
		infos5.add(new Info(36.011978, 120.137155, R.drawable.apri1, "B区文印店",
				"B餐继续向东走", 0));
		infos5.add(new Info(36.011314, 120.129856, R.drawable.aexp4, "A区文印店",
				"A餐正门口", 0));
		infos5.add(new Info(36.010894, 120.126069, R.drawable.apri2, "C区文印店",
				"维克超市东门口", 0));
		infos5.add(new Info(36.00758,120.131898, R.drawable.apri5, "J3文印店",
				"J3号楼一层东北角", 0));
		infos5.add(new Info(36.007712, 120.135926, R.drawable.apri6, "J13文印店",
				"J13号楼一层右转", 0));
	}
	//bookshop
	public static List<Info> infos6 = new ArrayList<Info>();
	static
	{
		infos6.add(new Info(36.011314, 120.129856, R.drawable.aexp4, "A区旧书店",
				"A餐3楼西北角", 0));
		infos6.add(new Info(36.011978,120.137155, R.drawable.aexp1, "B区旧书店",
				"B餐向东走", 0));
	}



	public Info(double latitude, double longitude, int imgId, String name,
			String distance, int zan)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.name = name;
		this.distance = distance;
		this.zan = zan;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public int getImgId()
	{
		return imgId;
	}

	public void setImgId(int imgId)
	{
		this.imgId = imgId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public int getZan()
	{
		return zan;
	}

	public void setZan(int zan)
	{
		this.zan = zan;
	}

}
