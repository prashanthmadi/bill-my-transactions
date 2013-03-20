package com.prashanth.budget.Activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.prashanth.budget.R;
import com.prashanth.budget.Constants.BudgetSplitConstants;
import com.prashanth.budget.DAO.IndividualDataDAO;
import com.prashanth.budget.DAO.TransactionDataDAO;
import com.prashanth.budget.POJO.IndividualDetailsCargo;
import com.prashanth.budget.POJO.TransactionDetailsCargo;
import com.prashanth.utils.ArrayAdapterClickHelper;
import com.prashanth.utils.MultipleCheckBoxAdapter;

/**
 * Activity class to add new transaction
 * 
 * @author deepu
 * 
 */
public class AddTransaction extends Activity implements ArrayAdapterClickHelper {

	private Button selectParticipants;
	private ToggleButton splitType;
	private TextView amount;
	private TextView place;
	private TextView description;
	private IndividualDataDAO individualDataDAO;
	private HashMap<String, ArrayList<?>> totalIndividualsDetail;
	private ArrayList<Integer> selectedData;
	private ListView listview;
	private ArrayList<String> usersList;
	private ArrayList<IndividualDetailsCargo> usersData;
	private Button transactioDate;
	private Button transactionTime;
	private Button transactionSubmit;
	private int totalParticipants;
	private int otherParticipants;
	private TransactionDataDAO transactionDataDAO;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewtransaction);

		selectParticipants = (Button) findViewById(R.id.selectParticipants);
		splitType = (ToggleButton) findViewById(R.id.splitType);
		amount = (TextView) findViewById(R.id.amount);
		place = (TextView) findViewById(R.id.place);
		description = (TextView) findViewById(R.id.desc);
		transactioDate = (Button) findViewById(R.id.transactionDate);
		transactionTime = (Button) findViewById(R.id.transactionTime);
		transactionSubmit = (Button) findViewById(R.id.transactionSubmit);

		individualDataDAO = new IndividualDataDAO(this);

		individualDataDAO.open();
		totalIndividualsDetail = individualDataDAO
				.retrieveAllUsersListWithTotalData();
		individualDataDAO.close();

		transactioDate.setText(new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		transactionTime.setText(new SimpleDateFormat("HH : mm")
				.format(new Date()));

		if (totalIndividualsDetail != null) {
			usersList = (ArrayList<String>) totalIndividualsDetail
					.get(BudgetSplitConstants.ONLYUSERSNAME);
			usersData = (ArrayList<IndividualDetailsCargo>) totalIndividualsDetail
					.get(BudgetSplitConstants.TOTALINDIVSDATA);
		}

		selectParticipants.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				if (totalIndividualsDetail != null) {
					popupMultipleCheckBoxDialog((ArrayList<String>) totalIndividualsDetail
							.get(BudgetSplitConstants.ONLYUSERSNAME));
				} else {
					// show alert of no individuals
				}
			}
		});

		transactionSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onSubmitTransaction();
			}
		});

	}

	/**
	 * This would popup dialog with list of input data
	 */
	protected void popupMultipleCheckBoxDialog(ArrayList<String> totalData) {

		selectedData = new ArrayList<Integer>();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = inflater.inflate(R.layout.list_item, null);
		listview = (ListView) view.findViewById(R.id.listItems);
		MultipleCheckBoxAdapter adapter = new MultipleCheckBoxAdapter(this,
				R.layout.listdata, totalData, this);
		listview.setAdapter(adapter);

		Dialog dialog = builder
				.setView(view)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						onPositiveButtonClick(selectedData);
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
							}
						}).create();
		dialog.show();
	}

	@Override
	public void clickDelegate(int position, boolean checked) {
		if (checked) {
			selectedData.add(position);
		} else {
			selectedData.remove(Integer.valueOf(position));
		}
	}

	@Override
	public void onPositiveButtonClick(ArrayList<Integer> selectedData) {

	}

	/**
	 * Fires an event on click of sumbit button while adding a new transaction
	 * 
	 * @param View
	 */
	public void onSubmitTransaction() {

		otherParticipants = selectedData.size();
		totalParticipants = otherParticipants + 1;
		Double amt = Double.parseDouble(amount.getText().toString());
		transactionDataDAO = new TransactionDataDAO(this);

		if (otherParticipants == 0) {
			return;
		}

		transactionDataDAO.open();
		TransactionDetailsCargo transactionDetailsCargo = new TransactionDetailsCargo();
		transactionDetailsCargo.setActutalTransDate(new Date().toString());
		transactionDetailsCargo.setTransAmt(amount.getText().toString());
		transactionDetailsCargo.setTransDescription(description.getText()
				.toString());
		transactionDetailsCargo.setTransEntryDate(new Date().toString());
		transactionDetailsCargo.setTransPlace(place.getText().toString());
		transactionDetailsCargo.setTransOwner("1234");
		Double equalSplitAmt = amt / totalParticipants;
		if (!splitType.isActivated()) {
			transactionDetailsCargo.setTransownerAmtShare(equalSplitAmt
					.toString());
			transactionDetailsCargo.setTransParticipantAmtShare(equalSplitAmt
					.toString());
		}
		for (int currentParticipant : selectedData) {

			transactionDetailsCargo.setTransParticipant(usersData.get(
					currentParticipant).getUniqueUserId());
			transactionDataDAO.insertNewTransaction(transactionDetailsCargo);
		}

		transactionDataDAO.close();
		finish();
	}
}
