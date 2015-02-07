package com.wattabyte.bindaasteam.util;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Names")
public class Names extends ParseObject {
     
   public String Name;
   
     
    public String getName() {
	return Name;
}


public void setName(String name) {
	Name = name;
}


	public static ParseQuery<Names> getQuery() {
        return ParseQuery.getQuery(Names.class);
    }
}
