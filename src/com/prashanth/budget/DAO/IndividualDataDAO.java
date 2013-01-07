package com.prashanth.budget.DAO;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.prashanth.budget.Constants.BudgetSplitConstants;
import com.prashanth.budget.Databases.BudgetDatabaseHelper;
import com.prashanth.budget.POJO.IndividualDetailsCargo;

/**
 * @author deepu
 * 
 */
public class IndividualDataDAO {

	private SQLiteDatabase database;
	private BudgetDatabaseHelper dbHelper;
	ArrayList<IndividualDetailsCargo> individualCargoArray = null;

	public IndividualDataDAO(Context context) {
		dbHelper = new BudgetDatabaseHelper(context);
	}

	/**
	 * Opening database connection
	 * 
	 */
	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * closing database connection
	 */
	public void close() {
		dbHelper.close();
	}

	public boolean insertIndividualData(IndividualDetailsCargo indivDetailCargo) {
		ContentValues values = new ContentValues();
		values.put(BudgetSplitConstants.individualFirstName,
				indivDetailCargo.getFirstName());
		values.put(BudgetSplitConstants.individualLastName,
				indivDetailCargo.getLastName());
		values.put(BudgetSplitConstants.individualEmailId,
				indivDetailCargo.getEmailId());
		values.put(BudgetSplitConstants.individualPhoneNumber,
				indivDetailCargo.getPhoneNumber());

		long insertResult = database.insert(
				BudgetSplitConstants.individualDataTable, null, values);

		if (insertResult < 0) {
			Log.w(BudgetSplitConstants.DatabaseTAG, "Unable To Insert Record "
					+ indivDetailCargo.getFirstName());
			return false;
		}

		return true;
	}

	public ArrayList<IndividualDetailsCargo> retrieveAllUsersdata() {
		individualCargoArray = new ArrayList<IndividualDetailsCargo>();
		Cursor cursor = database.query(
				BudgetSplitConstants.individualDataTable, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			return null;
		}
		while (!cursor.isAfterLast()) {
			individualCargoArray.add(cursorToIndividualCargo(cursor));
			cursor.moveToNext();

		}

		return individualCargoArray;
	}

	public IndividualDetailsCargo retrievesingleUserDetail(String uniqueUserId) {
		Cursor cursor = database.query(
				BudgetSplitConstants.individualDataTable, null,
				BudgetSplitConstants.individualUniqueUserId + "="
						+ uniqueUserId, null, null, null, null);
		return cursorToIndividualCargo(cursor);
	}

	public boolean deleateIndividualData(IndividualDetailsCargo indivDetailCargo) {
		return true;
	}

	private IndividualDetailsCargo cursorToIndividualCargo(Cursor cursor) {
		IndividualDetailsCargo individualDetailsCargo = new IndividualDetailsCargo();
		individualDetailsCargo.setUniqueUserId(cursor.getString(0));
		individualDetailsCargo.setFirstName(cursor.getString(1));
		individualDetailsCargo.setLastName(cursor.getString(2));
		individualDetailsCargo.setEmailId(cursor.getString(3));
		individualDetailsCargo.setPhoneNumber(cursor.getString(4));
		return individualDetailsCargo;
	}

}
