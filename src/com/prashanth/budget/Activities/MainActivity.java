package com.prashanth.budget.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.prashanth.budget.R;

public class MainActivity extends Activity implements OnClickListener {

	Button reportBill;
	Button addIndv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		reportBill = (Button) findViewById(R.id.newBillButton);
		addIndv = (Button) findViewById(R.id.addIndv);
	}

	@Override
	protected void onStart() {
		super.onStart();
		reportBill.setOnClickListener(this);
		addIndv.setOnClickListener(this);

	}

	/**
	 * Fires an event on click of any button
	 * 
	 * @param View
	 */
	@Override
	public void onClick(View v) {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
