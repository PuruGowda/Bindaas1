package com.wattabyte.bindaasteam.util;

public class CheckName {
	
	public boolean checkNameGroup(String name){
		if(name.substring(0,4).equals("Group")){
			return false;
		}
		else{
			return true;
		}
		
	}
	
	public boolean checkNameTeam(String name){
		if(name.substring(0,3).equals("Team")){
			return false;
		}
		else{
			return true;
		}
	
	}

}
