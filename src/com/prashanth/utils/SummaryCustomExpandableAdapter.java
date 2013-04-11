package com.prashanth.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.prashanth.budget.R;
import com.prashanth.budget.Constants.BudgetSplitConstants;
import com.prashanth.budget.POJO.IndividualDetailsCargo;
import com.prashanth.budget.POJO.TransactionDetailsCargo;

public class SummaryCustomExpandableAdapter extends BaseExpandableListAdapter {

	List<HashMap<String, ArrayList<?>>> totalData;
	LayoutInflater inflater;
	Context context;
	IndividualDetailsCargo individualDetailsCargo;
	TransactionDetailsCargo transactionDetailsCargo;
	boolean[] arrBgcolor;
	private int activeHex, inactiveHex;

	/**
	 * @param context
	 * @param totalData
	 */
	public SummaryCustomExpandableAdapter(Context context,
			List<HashMap<String, ArrayList<?>>> totalData) {
		this.totalData = totalData;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		activeHex = Color.parseColor("#FCD5B5");
		inactiveHex = Color.parseColor("#EEEEEE");
		arrBgcolor = new boolean[10];
        resetArrbg();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		convertView = inflater.inflate(R.layout.generic_group_expandable_list,
				null);
		individualDetailsCargo = (IndividualDetailsCargo) totalData
				.get(groupPosition).get(BudgetSplitConstants.GROUP).get(0);
		TextView userName = (TextView) convertView.findViewById(R.id.userName);
		userName.setText(individualDetailsCargo.getLastName());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.generic_group_expandable_list,
				null);
		transactionDetailsCargo = (TransactionDetailsCargo) totalData
				.get(groupPosition).get(BudgetSplitConstants.CHILD)
				.get(childPosition);
		TextView userName = (TextView) convertView.findViewById(R.id.userName);
		userName.setText(transactionDetailsCargo.getTransParticipantAmtShare());

		if (arrBgcolor[childPosition]) {
			convertView.setBackgroundColor(activeHex);
		} else {
			convertView.setBackgroundColor(inactiveHex);
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				resetArrbg();
				arrBgcolor[childPosition] = true;
				notifyDataSetChanged();

			}
		});

		return convertView;
	}

	private void resetArrbg() {
		for (int i = 0; i < arrBgcolor.length; i++) {
			arrBgcolor[i] = false;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		return totalData.get(groupPosition).get(BudgetSplitConstants.GROUP)
				.get(0);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return totalData.get(groupPosition).get(BudgetSplitConstants.CHILD)
				.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getGroupCount() {
		if (totalData != null) {
			return totalData.size();
		} else {
			return 0;
		}
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (totalData.get(groupPosition)
				.containsKey(BudgetSplitConstants.CHILD)) {
			return totalData.get(groupPosition).get(BudgetSplitConstants.CHILD)
					.size();
		} else {
			return 0;
		}
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
