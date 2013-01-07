package com.prashanth.budget.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.prashanth.budget.Constants.BudgetSplitConstants;

public class BudgetDatabaseHelper extends SQLiteOpenHelper {

	private static final String indivividualDatabaseCreate = "CREATE TABLE "
			+ BudgetSplitConstants.individualDataTable + "("
			+ BudgetSplitConstants.individualUniqueUserId
			+ " integer primary key,"
			+ BudgetSplitConstants.individualFirstName + " text,"
			+ BudgetSplitConstants.individualLastName + " text,"
			+ BudgetSplitConstants.individualEmailId + " text,"
			+ BudgetSplitConstants.individualPhoneNumber + " text)";

	private static final String transcationDatabaseCreate = "CREATE TABLE "
			+ BudgetSplitConstants.transactionDataTable + "("
			+ BudgetSplitConstants.TransId + " integer primary key,"
			+ BudgetSplitConstants.TransAmt + " text,"
			+ BudgetSplitConstants.TransDescription + " text,"
			+ BudgetSplitConstants.TransActualDt + " text,"
			+ BudgetSplitConstants.TransEntryDt + " text,"
			+ BudgetSplitConstants.TransOwner + " text,"
			+ BudgetSplitConstants.TransOwnerAmtShare + " text,"
			+ BudgetSplitConstants.TransParticipant + " text,"
			+ BudgetSplitConstants.TransParticipantAmtShare + " text,"
			+ BudgetSplitConstants.TransPlace + " text)";

	public BudgetDatabaseHelper(Context context) {
		super(context, BudgetSplitConstants.budgetDatabase, null,
				BudgetSplitConstants.budgetSplitDatabaseVersion);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(indivividualDatabaseCreate);
		db.execSQL(transcationDatabaseCreate);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(BudgetSplitConstants.DatabaseTAG,
				"Going to Recreate Tables. All the previous data would be lost");
		db.execSQL("DROP TABLE IF EXISTS " + indivividualDatabaseCreate);
		db.execSQL("DROP TABLE IF EXISTS " + transcationDatabaseCreate);
		onCreate(db);

	}

}
