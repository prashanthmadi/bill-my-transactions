package com.prashanth.budget.Activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

	/**
	 * Field splitType
	 */
	Button selectParticipants;
	ToggleButton splitType;
	TextView amount;
	TextView place;
	TextView description;
	IndividualDataDAO individualDataDAO;
	HashMap<String, ArrayList<?>> totalIndividualsDetail;
	private ArrayList<Integer> selectedData;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewtransaction);
		selectParticipants = (Button) findViewById(R.id.selectParticipants);
		splitType = (ToggleButton) findViewById(R.id.splitType);
		amount = (TextView) findViewById(R.id.amount);
		place = (TextView) findViewById(R.id.place);
		description = (TextView) findViewById(R.id.desc);
		individualDataDAO = new IndividualDataDAO(this);
		individualDataDAO.open();
		totalIndividualsDetail = individualDataDAO
				.retrieveAllUsersListWithTotalData();
		individualDataDAO.close();

		selectParticipants.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				popupMultipleCheckBoxDialog((ArrayList<String>) totalIndividualsDetail
						.get(BudgetSplitConstants.ONLYUSERSNAME));
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
		// TODO Auto-generated method stub

	}

	/**
	 * Fires an event on click of sumbit button while adding a new transaction
	 * 
	 * @param View
	 */
	public void onSubmitTransaction(View v) {

		TransactionDataDAO transactionDataDAO = new TransactionDataDAO(this);
		transactionDataDAO.open();

		TransactionDetailsCargo transactionDetailsCargo = new TransactionDetailsCargo();
		// transactionDetailsCargo.setActutalTransDate(transActualDate);
		Double amt = Double.parseDouble(amount.getText().toString());
		transactionDetailsCargo.setTransAmt(amount.getText().toString());
		transactionDetailsCargo.setTransDescription(description.getText()
				.toString());
		transactionDetailsCargo.setTransEntryDate(new Date().toString());
		transactionDetailsCargo.setTransOwner("1234");
		transactionDetailsCargo.setTransParticipant("id");
		transactionDetailsCargo.setTransPlace(place.getText().toString());

		if (splitType.isActivated()) {
			Double equalSplitAmt = amt / 2;
			transactionDetailsCargo.setTransownerAmtShare(equalSplitAmt
					.toString());
			transactionDetailsCargo.setTransParticipantAmtShare(equalSplitAmt
					.toString());
		} else {

		}

		Log.w("new Transaction Details", transactionDetailsCargo.toString());

		transactionDataDAO.insertNewTransaction(transactionDetailsCargo);
		transactionDataDAO.close();
		finish();
	}

}
