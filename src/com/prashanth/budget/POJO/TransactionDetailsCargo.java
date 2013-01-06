package com.prashanth.budget.POJO;

public class TransactionDetailsCargo {

	private String transId;
	private String transAmt;
	private String transOwner;
	private String transownerAmtShare;
	private String transParticipant;
	private String transParticipantAmtShare;
	private String transDescription;
	private String transEntryDate;
	private String transActualDate;
	private String transPlace;

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransownerAmtShare() {
		return transownerAmtShare;
	}

	public void setTransownerAmtShare(String transownerAmtShare) {
		this.transownerAmtShare = transownerAmtShare;
	}

	public String getTransDescription() {
		return transDescription;
	}

	public void setTransDescription(String transDescription) {
		this.transDescription = transDescription;
	}

	public String getTransEntryDate() {
		return transEntryDate;
	}

	public void setTransEntryDate(String transEntryDate) {
		this.transEntryDate = transEntryDate;
	}

	public String getActutalTransDate() {
		return transActualDate;
	}

	public void setActutalTransDate(String transActualDate) {
		this.transActualDate = transActualDate;
	}

	public String getTransPlace() {
		return transPlace;
	}

	public void setTransPlace(String transPlace) {
		this.transPlace = transPlace;
	}

	public String getTransOwner() {
		return transOwner;
	}

	public void setTransOwner(String transOwner) {
		this.transOwner = transOwner;
	}

	public String getTransParticipant() {
		return transParticipant;
	}

	public void setTransParticipant(String transParticipant) {
		this.transParticipant = transParticipant;
	}

	public String getTransParticipantAmtShare() {
		return transParticipantAmtShare;
	}

	public void setTransParticipantAmtShare(String transParticipantAmtShare) {
		this.transParticipantAmtShare = transParticipantAmtShare;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

}
