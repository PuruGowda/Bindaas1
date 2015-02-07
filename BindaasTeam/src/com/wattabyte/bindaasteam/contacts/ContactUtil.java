package com.wattabyte.bindaasteam.contacts;

import java.util.ArrayList;

public class ContactUtil {
	
	public String id;
    public String name;
    public ArrayList<ContactPhone> numbers;

    public ContactUtil(String id, String name) {
        this.id = id;
        this.name = name;
        
        this.numbers = new ArrayList<ContactPhone>();
    }

   

    public void addNumber(String number, String type) {
        numbers.add(new ContactPhone(number, type));
    }

}
