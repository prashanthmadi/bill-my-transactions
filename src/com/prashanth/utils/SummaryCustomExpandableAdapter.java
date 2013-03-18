package com.prashanth.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Log.w("Budget", "in child");
		convertView = inflater.inflate(R.layout.generic_group_expandable_list,
				null);
		transactionDetailsCargo = (TransactionDetailsCargo) totalData
				.get(groupPosition).get(BudgetSplitConstants.CHILD)
				.get(childPosition);
		TextView userName = (TextView) convertView.findViewById(R.id.userName);
		userName.setText("55");
		return convertView;
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
		Log.w("Budget", "returns group count " + totalData.size());
		return totalData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		Log.w("Budget", "returns child count"
				+ totalData.get(groupPosition).get(BudgetSplitConstants.CHILD)
						.size());

		return totalData.get(groupPosition).get(BudgetSplitConstants.CHILD)
				.size();

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
