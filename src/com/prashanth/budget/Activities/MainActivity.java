package com.prashanth.budget.Activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.prashanth.budget.R;
import com.prashanth.budget.Constants.BudgetSplitConstants;
import com.prashanth.budget.DAO.IndividualDataDAO;
import com.prashanth.budget.DAO.TransactionDataDAO;
import com.prashanth.budget.POJO.IndividualDetailsCargo;
import com.prashanth.budget.POJO.TransactionDetailsCargo;
import com.prashanth.utils.SummaryCustomExpandableAdapter;

public class MainActivity extends Activity {

	ExpandableListView expandableList;
	SummaryCustomExpandableAdapter summaryCustomExpandableAdapter;
	ArrayList<HashMap<String, ArrayList<?>>> totalData;
	HashMap<String, ArrayList<?>> singleData;
	Context context;
	IndividualDataDAO individualDataDAO;
	TransactionDataDAO transactionDataDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		expandableList = (ExpandableListView) findViewById(R.id.billSummaryExpandableList);
		context = this;

		totalData = new ArrayList<HashMap<String, ArrayList<?>>>();

		summaryCustomExpandableAdapter = new SummaryCustomExpandableAdapter(
				context, totalData);
		expandableList.setAdapter(summaryCustomExpandableAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUserData();
		summaryCustomExpandableAdapter.notifyDataSetChanged();

	}

	public void onViewClicked(View v) {
		switch (v.getId()) {
		case R.id.addIndv:
			Intent addIndividualIntent = new Intent(this, AddIndividual.class);
			startActivity(addIndividualIntent);
			break;

		case R.id.newBillButton:
			Intent newBillButtonIntent = new Intent(this, AddTransaction.class);
			startActivity(newBillButtonIntent);
			break;
		}
	}

	public void setUserData() {
		individualDataDAO = new IndividualDataDAO(context);
		transactionDataDAO = new TransactionDataDAO(context);
		individualDataDAO.open();
		transactionDataDAO.open();
		ArrayList<IndividualDetailsCargo> currentindividulDetails;
		ArrayList<IndividualDetailsCargo> individualDetailsCargo = individualDataDAO
				.retrieveAllUsersdata();

		for (IndividualDetailsCargo currentIndividualDetail : individualDetailsCargo) {
			singleData = new HashMap<String, ArrayList<?>>();
			currentindividulDetails = new ArrayList<IndividualDetailsCargo>();
			currentindividulDetails.add(currentIndividualDetail);
			if (individualDetailsCargo != null) {
				singleData.put(BudgetSplitConstants.GROUP,
						currentindividulDetails);
			}
			ArrayList<TransactionDetailsCargo> transactionDetailsCargo = transactionDataDAO
					.retrieveAllTransactionWithUser(currentIndividualDetail
							.getUniqueUserId());
			if (transactionDetailsCargo != null) {
				singleData.put(BudgetSplitConstants.CHILD,
						transactionDetailsCargo);
			}
			totalData.add(singleData);
		}
		transactionDataDAO.close();
		individualDataDAO.close();

	}
}