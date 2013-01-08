package com.prashanth.budget.DAO;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.prashanth.budget.Constants.BudgetSplitConstants;
import com.prashanth.budget.Databases.BudgetDatabaseHelper;
import com.prashanth.budget.POJO.TransactionDetailsCargo;

/**
 * Database helper class for Transaction related events
 * 
 * @author deepu
 * 
 */
public class TransactionDataDAO {

	private SQLiteDatabase database;
	private BudgetDatabaseHelper dbHelper;
	private ArrayList<TransactionDetailsCargo> transactoinDetailsArray = null;

	/**
	 * @param context
	 */
	public TransactionDataDAO(Context context) {
		dbHelper = new BudgetDatabaseHelper(context);
	}

	/**
	 * opens a database connection
	 */
	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * closes a database connection
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * Inserts a new transaction into database
	 * 
	 * @param transactionDetailsCargo
	 * @return boolean
	 */
	public boolean insertNewTransaction(
			TransactionDetailsCargo transactionDetailsCargo) {

		ContentValues values = new ContentValues();
		values.put(BudgetSplitConstants.TransActualDt,
				transactionDetailsCargo.getActutalTransDate());
		values.put(BudgetSplitConstants.TransAmt,
				transactionDetailsCargo.getTransAmt());
		values.put(BudgetSplitConstants.TransDescription,
				transactionDetailsCargo.getTransDescription());
		values.put(BudgetSplitConstants.TransEntryDt,
				transactionDetailsCargo.getTransEntryDate());
		values.put(BudgetSplitConstants.TransOwner,
				transactionDetailsCargo.getTransOwner());
		values.put(BudgetSplitConstants.TransOwnerAmtShare,
				transactionDetailsCargo.getTransownerAmtShare());
		values.put(BudgetSplitConstants.TransParticipant,
				transactionDetailsCargo.getTransParticipant());
		values.put(BudgetSplitConstants.TransParticipantAmtShare,
				transactionDetailsCargo.getTransParticipantAmtShare());
		values.put(BudgetSplitConstants.TransPlace,
				transactionDetailsCargo.getTransPlace());

		long insertResult = database.insert(
				BudgetSplitConstants.transactionDataTable, null, values);

		if (insertResult < 0) {
			Log.w(BudgetSplitConstants.DatabaseTAG,
					"unable to enter transaction"
							+ transactionDetailsCargo.getTransDescription()
							+ " at " + transactionDetailsCargo.getTransPlace());
			return false;
		}

		return true;
	}

	/**
	 * Retrieves a transaction from database using transaction id
	 * @param transId
	 * @return TransactionDetailsCargo
	 */
	public TransactionDetailsCargo retrieveSingleTransaction(String transId) {
		Cursor cursor = database.query(
				BudgetSplitConstants.transactionDataTable, null,
				BudgetSplitConstants.TransId + "=" + transId, null, null, null,
				null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			return null;
		}
		return cusorToTransDetailsCargo(cursor);
	}

	/**
	 * retrieves all the transactions of a user
	 * 
	 * @return ArrayList<TransactionDetailsCargo>
	 */
	public ArrayList<TransactionDetailsCargo> retrieveAllTransaction() {
		transactoinDetailsArray = new ArrayList<TransactionDetailsCargo>();

		Cursor cursor = database.query(
				BudgetSplitConstants.transactionDataTable, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			return null;
		}
		while (cursor.isAfterLast()) {
			transactoinDetailsArray.add(cusorToTransDetailsCargo(cursor));
			cursor.moveToNext();
		}

		return transactoinDetailsArray;
	}

	/**
	 * retrieves transactions of user with associated transaction participant
	 * 
	 * @param transParticipant
	 * @return ArrayList<TransactionDetailsCargo>
	 */
	public ArrayList<TransactionDetailsCargo> retrieveAllTransactionWithUser(
			String transParticipant) {
		transactoinDetailsArray = new ArrayList<TransactionDetailsCargo>();

		Cursor cursor = database.query(
				BudgetSplitConstants.transactionDataTable, null,
				BudgetSplitConstants.TransParticipant + "=" + transParticipant,
				null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			return null;
		}
		while (cursor.isAfterLast()) {
			transactoinDetailsArray.add(cusorToTransDetailsCargo(cursor));
			cursor.moveToNext();
		}

		return transactoinDetailsArray;
	}

	/**
	 * converts cursor into POJO
	 * 
	 * @param cursor
	 * @return
	 */
	public TransactionDetailsCargo cusorToTransDetailsCargo(Cursor cursor) {
		TransactionDetailsCargo transactionDetailsCargo = new TransactionDetailsCargo();

		transactionDetailsCargo.setTransId(cursor.getString(0));
		transactionDetailsCargo.setTransAmt(cursor.getString(1));
		transactionDetailsCargo.setTransDescription(cursor.getString(2));
		transactionDetailsCargo.setActutalTransDate(cursor.getString(3));
		transactionDetailsCargo.setTransEntryDate(cursor.getString(4));
		transactionDetailsCargo.setTransOwner(cursor.getString(5));
		transactionDetailsCargo.setTransownerAmtShare(cursor.getString(6));
		transactionDetailsCargo.setTransParticipant(cursor.getString(7));
		transactionDetailsCargo
				.setTransParticipantAmtShare(cursor.getString(8));
		transactionDetailsCargo.setTransPlace(cursor.getString(9));

		return transactionDetailsCargo;
	}

	/**
	 * 
	 * deletes a particular transaction using transaction id
	 * 
	 * @param transId
	 * @return boolean
	 */
	public boolean deleteTransaction(String transId) {

		long deleteTransResult = database.delete(
				BudgetSplitConstants.transactionDataTable,
				BudgetSplitConstants.TransId + "=" + transId, null);
		if (deleteTransResult < 0) {
			Log.w(BudgetSplitConstants.DatabaseTAG,
					"unable to delete transaction" + transId);
			return false;
		}

		return true;
	}
}
