package com.formal.sdusthelper;

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

	public static List<Info> infos1 = new ArrayList<Info>();
	static
	{
		infos1.add(new Info(36.008671, 120.135910, R.drawable.a01, "ATM机",
				"土建学院楼前", 1456));
		infos1.add(new Info(36.011901, 120.135623, R.drawable.a04, "ATM机",
				"B餐一楼", 1456));
		infos1.add(new Info(36.008938, 120.131387, R.drawable.a03, "ATM机",
				"繁星广场", 1456));

	}

	public static List<Info> infos2 = new ArrayList<Info>();
	static
	{
		infos2.add(new Info(36.011270, 120.129950, R.drawable.a02, "A餐打印",
				"A餐门口", 1456));
	}

	public static List<Info> infos3 = new ArrayList<Info>();
	static
	{
		infos3.add(new Info(36.011639, 120.128167, R.drawable.a03, "校医院",
				"砚湖前", 1456));
	}
	public static List<Info> infos4 = new ArrayList<Info>();
	static
	{
		infos4.add(new Info(36.011894, 120.136782, R.drawable.a04, "圆通快递",
				"B餐快递", 1456));
	}
	public static List<Info> infos5 = new ArrayList<Info>();
	static
	{
		infos5.add(new Info(36.015226, 120.134774, R.drawable.a01, "KTV",
				"距离12米", 1456));
	}
	public static List<Info> infos6 = new ArrayList<Info>();
	static
	{
		infos6.add(new Info(36.004343, 120.130471, R.drawable.a01, "NO1美发连锁",
				"距离12米", 1456));
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
