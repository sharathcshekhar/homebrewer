package com.biermacht.brews.frontend.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import com.biermacht.brews.R;
import com.biermacht.brews.recipe.Recipe;
import android.content.*;
import android.widget.*;
import java.util.*;
import com.biermacht.brews.frontend.*;
import com.biermacht.brews.frontend.adapters.*;
import com.biermacht.brews.utils.*;
import com.biermacht.brews.recipe.*;

public class ProfileViewFragment extends Fragment {

	private int resource;
	private Recipe r;
	private OnItemClickListener mClickListener;
	View pageView;
	Context c;

	// List stuff
	private DetailArrayAdapter detailArrayAdapter;
	private DetailArrayAdapter mashStepArrayAdapter;
	private ArrayList<Detail> detailList;
	private ArrayList<Detail> mashStepList;
	private ListView detailsListView;
	private ListView stepsListView;

	// Details to show
	Detail beerType;
	Detail originalGravity;
	Detail finalGravity;
	Detail abv;
	Detail color;
	Detail bitterness;

	public ProfileViewFragment(Context c, Recipe r)
	{
		this.resource = R.layout.profile_view;
		this.r = r;
		this.c = c;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		pageView = inflater.inflate(resource, container, false);
		detailsListView = (ListView) pageView.findViewById(R.id.details_list);
		stepsListView = (ListView) pageView.findViewById(R.id.steps_list);
		
		this.detailList = new ArrayList<Detail>();
		this.mashStepList = new ArrayList<Detail>();

		setHasOptionsMenu(true);

		// Configure details
		beerType = new Detail();
		beerType.setTitle("Profile Name: ");
		beerType.setType(Detail.TYPE_TEXT);
		beerType.setFormat("%s");
		beerType.setContent(r.getMashProfile().getName());
		detailList.add(beerType);
		
		beerType = new Detail();
		String t = String.format("%2.0f", r.getMashProfile().getDisplayGrainTemp());
		t += " " + Units.FARENHEIT;
		beerType.setTitle("Grain Temp: ");
		beerType.setType(Detail.TYPE_TEXT);
		beerType.setFormat("%s");
		beerType.setContent(t);
		detailList.add(beerType);
		
		beerType = new Detail();
		t = String.format("%2.0f", r.getMashProfile().getDisplayTunTemp());
		t += " " + Units.FARENHEIT;
		beerType.setTitle("Tun Temp: ");
		beerType.setType(Detail.TYPE_TEXT);
		beerType.setFormat("%s");
		beerType.setContent(t);
		detailList.add(beerType);
		
		// Adapter stuff
		detailArrayAdapter = new DetailArrayAdapter(c, detailList);
		detailsListView.setAdapter(detailArrayAdapter);
		
		Detail detail = new Detail();
		detail.setType(Detail.TYPE_BLANK);
		mashStepList.add(detail);
		for (MashStep s : r.getMashProfile().getMashStepList())
		{
			detail = new Detail();
			detail.setTitle(s.getName()+":");
			detail.setType(Detail.TYPE_TEXT);
			detail.setFormat("%s");
			detail.setContent("Hold at " + String.format("%2.0f", s.getDisplayStepTemp()) + " F");
			detail.setSubText("Ramp to temp in " + s.getRampTime() + " mins");
			mashStepList.add(detail);
		}

		// MashSteps list here
		mashStepArrayAdapter = new DetailArrayAdapter(c, mashStepList);
		stepsListView.setAdapter(mashStepArrayAdapter);
		
		
		return pageView;
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.mash_profile_menu, menu);
	}

}
