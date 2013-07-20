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
import com.biermacht.brews.recipe.RecipeReccomendedValues;
import android.content.*;
import android.widget.*;
import java.util.*;
import com.biermacht.brews.frontend.*;
import com.biermacht.brews.frontend.adapters.*;

public class DetailsViewFragment extends Fragment {

	private int resource;
	private Recipe r;
	private OnItemClickListener mClickListener;
	private RecipeReccomendedValues reccomendedValues;
	View pageView;
	Context c;
	
	// List stuff
	private DetailArrayAdapter mAdapter;
	private ArrayList<Detail> detailList;
	private ListView listView;
	
	// Details to show
	Detail beerType;
	Detail originalGravity;
	Detail finalGravity;
	Detail abv;
	Detail color;
	Detail bitterness;
	
	public DetailsViewFragment(Context c, Recipe r)
	{
		this.resource = R.layout.details_view;
		this.r = r;
		this.c = c;
		this.reccomendedValues = r.getStyle().getReccomendedValues();
	}
	
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		pageView = inflater.inflate(resource, container, false);
		listView = (ListView) pageView.findViewById(R.id.details_list);
		this.detailList = new ArrayList<Detail>();
		
		setHasOptionsMenu(true);
			
		// Configure details
		beerType = new Detail();
		beerType.setTitle("Beer Style: ");
		beerType.setType(Detail.TYPE_TEXT);
		beerType.setFormat("%s");
		beerType.setContent(r.getStyle().getName());
		detailList.add(beerType);
			
		originalGravity = new Detail();
		originalGravity.setTitle("Original Gravity: ");
		originalGravity.setValue(r.getOG());
		originalGravity.setFormat("%2.3f");
		originalGravity.setMin(reccomendedValues.getMinOG());
		originalGravity.setMax(reccomendedValues.getMaxOG());
		detailList.add(originalGravity);
			
		finalGravity = new Detail();
		finalGravity.setTitle("Final Gravity: ");
		finalGravity.setValue(r.getFG());
		finalGravity.setFormat("%2.3f");
		finalGravity.setMin(reccomendedValues.getMinFG());
		finalGravity.setMax(reccomendedValues.getMaxFG());
		detailList.add(finalGravity);
			
		bitterness = new Detail();
		bitterness.setTitle("Bitterness, IBU: ");
		bitterness.setValue(r.getBitterness());
		bitterness.setFormat("%2.1f");
		bitterness.setMin(reccomendedValues.getMinBitter());
		bitterness.setMax(reccomendedValues.getMaxBitter());
		detailList.add(bitterness);
		  
		color = new Detail();
		color.setTitle("Color, SRM: ");
		color.setValue(r.getColor());
		color.setFormat("%2.1f");
		color.setMin(reccomendedValues.getMinColor());
		color.setMax(reccomendedValues.getMaxColor());
		detailList.add(color);
			
		abv = new Detail();
		abv.setTitle("ABV: ");
		abv.setValue(r.getABV());
		abv.setFormat("%2.1f");
		abv.setMin(reccomendedValues.getMinAbv());
		abv.setMax(reccomendedValues.getMaxAbv());
		detailList.add(abv);
			
		// Adapter stuff
		mAdapter = new DetailArrayAdapter(c, detailList);
		listView.setAdapter(mAdapter);
		  
		return pageView;
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.details_menu, menu);
	}
	
}
