package com.prashanth.budget.DAO;

import com.prashanth.budget.POJO.IndividualDetailsCargo;

public class IndividualDataDAO {

	public boolean insertIndividualData(IndividualDetailsCargo indivDetailCargo) {

		return true;
	}

	// public IndividualDetailsCargo[] retrieveAllUsersdata() {
	// IndividualDetailsCargo[] individualDetailsCargos;
	// return individualDetailsCargos;
	// }

	public IndividualDetailsCargo retrievesingleUserDetail() {
		IndividualDetailsCargo individualDetailsCargo = new IndividualDetailsCargo();
		return individualDetailsCargo;
	}

	public boolean deleateIndividualData(IndividualDetailsCargo indivDetailCargo) {
		return true;
	}

}
