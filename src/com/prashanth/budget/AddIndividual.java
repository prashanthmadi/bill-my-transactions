package com.prashanth.budget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prashanth.budget.DAO.IndividualDataDAO;
import com.prashanth.budget.POJO.IndividualDetailsCargo;

public class AddIndividual extends Activity {

	TextView firstName;
	TextView lastName;
	TextView emailId;
	TextView phoneNumber;
	Button submitButton;
	IndividualDetailsCargo indivDetailCargo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addnewhuman);
		indivDetailCargo = new IndividualDetailsCargo();
		firstName = (TextView) findViewById(R.id.firstName);
		lastName = (TextView) findViewById(R.id.lastName);
		emailId = (TextView) findViewById(R.id.emailId);
		phoneNumber = (TextView) findViewById(R.id.phoneNumber);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	public void onDataSubmit(View v) {
		IndividualDataDAO individualDataDao =  new IndividualDataDAO(this);
		indivDetailCargo.setFirstName(firstName.getText().toString());
		indivDetailCargo.setLastName(lastName.getText().toString());
		indivDetailCargo.setEmailId(emailId.getText().toString());
		indivDetailCargo.setPhoneNumber(phoneNumber.getText().toString());
		Log.w("new Individual Details", indivDetailCargo.toString());
		
		individualDataDao.insertIndividualData(indivDetailCargo);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
