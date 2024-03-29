package com.prashanth.budget.Constants;

/**
 * Contains all the Constant data related to Project
 * 
 * @author deepu
 * 
 */
public class BudgetSplitConstants {

	// Database Table Details and DatabaseName with Version
	public static final int budgetSplitDatabaseVersion = 1;
	public static final String budgetDatabase = "budgetsplit.db";
	public static final String individualDataTable = "INDIVIDUAL_DETAILS";
	public static final String transactionDataTable = "TRANSACTION_DETAILS";
	public static final String TAG = "BudgetSplit";
	public static final String DatabaseTAG = "BudgetSplitDatabase";

	// Individual Detail Database Columns
	public static final String individualFirstName = "FIRSTNAME";
	public static final String individualLastName = "LASTNAME";
	public static final String individualEmailId = "EMAILID";
	public static final String individualPhoneNumber = "PHONENUMBER";
	public static final String individualUniqueUserId = "USERID";

	// Transaction Detail Database Columns
	public static final String TransId = "TRANS_ID";
	public static final String TransAmt = "TRANS_AMT";
	public static final String TransOwner = "TRANS_OWNER";
	public static final String TransOwnerAmtShare = "TRANS_OWNERAMT_SHARE";
	public static final String TransParticipant = "TRANS_PARTICIPANT";
	public static final String TransParticipantAmtShare = "TRANS_PARTICIPANT_AMT_SHARE";
	public static final String TransDescription = "TRANS_DESCIPTION";
	public static final String TransEntryDt = "TRANS_ENTRY_DT";
	public static final String TransActualDt = "TRANS_ACTUAL_DT";
	public static final String TransPlace = "TRANS_PLACE";

	// Constants to Create HashMap which is assigned to Expandable List
	public static final String GROUP = "group";
	public static final String CHILD = "child";

	// Constants to Transfer Individual Details in a HashMap along with Users
	// String List
	public static final String TOTALINDIVSDATA = "totalindivsdata";
	public static final String ONLYUSERSNAME = "onlyusersname";

}
