package com.wattabyte.bindaasteam.util;

import java.util.ArrayList;

public class BindaasUtil {

	private static String fbName;
	private static String fbId;
	private static String playerName;
	private String teamName;
	ArrayList<String> list;
	static ArrayList<String> tName;
	

	public BindaasUtil() {

	}

	public static String getFbName() {
		return fbName;
	}

	public static void setFbName(String fbName) {
		BindaasUtil.fbName = fbName;
	}

	public static String getFbId() {
		return fbId;
	}

	public static void setFbId(String fbBirthDate) {
		BindaasUtil.fbId = fbBirthDate;
	}

	public static String getPlayerName() {
		return playerName;
	}

	public static void setPlayerName(String playerName) {
		BindaasUtil.playerName = playerName;
	}
	
	public BindaasUtil(ArrayList<String> list)
	{
		this.list = list;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}



}
