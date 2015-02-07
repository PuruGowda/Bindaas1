package com.wattabyte.bindaasteam.navigationdrawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;

public class ProfileFragment extends Fragment {

	TextView userName;
	
	String user;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.profile_fragment, container , false);
		
		ParseUser pUser = ParseUser.getCurrentUser();
		user = pUser.getUsername();
		userName = (TextView) root.findViewById(R.id.userName);
		userName.setText(user);
		return root;
	}


}
