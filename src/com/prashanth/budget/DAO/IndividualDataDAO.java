package com.prashanth.budget.DAO;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.prashanth.budget.Constants.BudgetSplitConstants;
import com.prashanth.budget.Databases.BudgetDatabaseHelper;
import com.prashanth.budget.POJO.IndividualDetailsCargo;

/**
 * Database helper class realted to Individual Data
 * 
 * @author deepu
 * 
 */
public class IndividualDataDAO {

	private SQLiteDatabase database;
	private BudgetDatabaseHelper dbHelper;
	ArrayList<IndividualDetailsCargo> individualCargoArray = null;
	ArrayList<String> individuaNameslList = null;

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

	/**
	 * Inserts a new Individual Data into database
	 * 
	 * @param indivDetailCargo
	 * @return boolean
	 */
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

	/**
	 * returns set of users involved as Cursor Object
	 * 
	 * @return
	 */
	public Cursor retrieveAllUsersdataCursor() {
		Cursor cursor = database.query(
				BudgetSplitConstants.individualDataTable, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			return null;
		}

		return cursor;
	}

	/**
	 * returns set of users involved
	 * 
	 * @return ArrayList<IndividualDetailsCargo>
	 */
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

	HashMap<String, ArrayList<?>> totalUserDataWithList;
	ArrayList<String> totalUsersName;

	/**
	 * returns set of users involved along with a list of total users
	 * 
	 * @return ArrayList<IndividualDetailsCargo>
	 */
	public HashMap<String, ArrayList<?>> retrieveAllUsersListWithTotalData() {
		totalUserDataWithList = new HashMap<String, ArrayList<?>>();
		individualCargoArray = new ArrayList<IndividualDetailsCargo>();
		totalUsersName = new ArrayList<String>();
		Cursor cursor = database.query(
				BudgetSplitConstants.individualDataTable, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			return null;
		}
		while (!cursor.isAfterLast()) {
			individualCargoArray.add(cursorToIndividualCargoWithList(cursor));
			cursor.moveToNext();

		}
		totalUserDataWithList.put(BudgetSplitConstants.ONLYUSERSNAME,
				totalUsersName);
		totalUserDataWithList.put(BudgetSplitConstants.TOTALINDIVSDATA,
				individualCargoArray);

		return totalUserDataWithList;
	}

	/**
	 * Returns single user data based on userId
	 * 
	 * @param uniqueUserId
	 * @return IndividualDetailsCargo
	 */
	public IndividualDetailsCargo retrievesingleUserDetail(String uniqueUserId) {
		Cursor cursor = database.query(
				BudgetSplitConstants.individualDataTable, null,
				BudgetSplitConstants.individualUniqueUserId + "="
						+ uniqueUserId, null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			return null;
		}
		return cursorToIndividualCargo(cursor);
	}

	/**
	 * deletes an individual based on user ID
	 * 
	 * @param uniqueUserId
	 * @return boolean
	 */
	public boolean deleateIndividualData(String uniqueUserId) {
		database.delete(BudgetSplitConstants.individualDataTable,
				BudgetSplitConstants.individualUniqueUserId + "="
						+ uniqueUserId, null);

		return true;
	}

	/**
	 * Converts cursor to IndivdualDetailsCargo Object
	 * 
	 * @param cursor
	 * @return IndividualDetailsCargo
	 */
	private IndividualDetailsCargo cursorToIndividualCargo(Cursor cursor) {
		IndividualDetailsCargo individualDetailsCargo = new IndividualDetailsCargo();
		individualDetailsCargo.setUniqueUserId(cursor.getString(0));
		individualDetailsCargo.setFirstName(cursor.getString(1));
		individualDetailsCargo.setLastName(cursor.getString(2));
		individualDetailsCargo.setEmailId(cursor.getString(3));
		individualDetailsCargo.setPhoneNumber(cursor.getString(4));
		return individualDetailsCargo;
	}

	/**
	 * Converts cursor to IndivdualDetailsCargo Object along with populating a
	 * new list of user names
	 * 
	 * @param cursor
	 * @return IndividualDetailsCargo
	 */
	private IndividualDetailsCargo cursorToIndividualCargoWithList(Cursor cursor) {
		IndividualDetailsCargo individualDetailsCargo = new IndividualDetailsCargo();
		totalUsersName.add(cursor.getString(2) + " " + cursor.getString(1));
		individualDetailsCargo.setUniqueUserId(cursor.getString(0));
		individualDetailsCargo.setFirstName(cursor.getString(1));
		individualDetailsCargo.setLastName(cursor.getString(2));
		individualDetailsCargo.setEmailId(cursor.getString(3));
		individualDetailsCargo.setPhoneNumber(cursor.getString(4));
		return individualDetailsCargo;
	}

}
