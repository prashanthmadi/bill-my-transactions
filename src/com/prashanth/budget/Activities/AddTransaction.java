package com.prashanth.budget.Activities;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.prashanth.budget.R;
import com.prashanth.budget.DAO.TransactionDataDAO;
import com.prashanth.budget.POJO.TransactionDetailsCargo;

/**
 * Activity class to add new transaction
 * 
 * @author deepu
 *
 */
public class AddTransaction extends Activity {

	/**
	 * Field splitType
	 */
	Spinner splitType;
	TextView amount;
	TextView place;
	TextView description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewtransaction);
		splitType = (Spinner) findViewById(R.id.splitType);
		amount = (TextView) findViewById(R.id.amount);
		place = (TextView) findViewById(R.id.place);
		description = (TextView) findViewById(R.id.description);

	}

	/**
	 * Fires an event on click of sumbit button while adding a new transaction
	 * 
	 * @param View
	 */
	public void onSubmitTransaction(View v) {

		TransactionDataDAO transactionDataDAO = new TransactionDataDAO(this);

		TransactionDetailsCargo transactionDetailsCargo = new TransactionDetailsCargo();
		// transactionDetailsCargo.setActutalTransDate(transActualDate);
		Double amt = Double.parseDouble(amount.getText().toString());
		transactionDetailsCargo.setTransAmt(amt.toString());
		transactionDetailsCargo.setTransDescription(description.getText()
				.toString());
		transactionDetailsCargo.setTransEntryDate(new Date().toString());
		transactionDetailsCargo.setTransOwner("aaaa");
		transactionDetailsCargo.setTransParticipant("id");
		transactionDetailsCargo.setTransPlace(place.getText().toString());

		if (splitType.getSelectedItem().toString().equalsIgnoreCase("Equal")) {
			Double equalSplitAmt = amt / 2;
			transactionDetailsCargo.setTransownerAmtShare(equalSplitAmt
					.toString());
			transactionDetailsCargo.setTransParticipantAmtShare(equalSplitAmt
					.toString());
		} else {

		}

		Log.w("new Transaction Details", transactionDetailsCargo.toString());

		transactionDataDAO.insertNewTransaction(transactionDetailsCargo);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

}
