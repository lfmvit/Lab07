package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	int maxAffectedPeople;
	
	
	List<PowerOutage> events;
	List<PowerOutage> partial;
	List<PowerOutage> the_worst;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getEvents(Nerc nerc){
		return podao.getEvents(nerc);
	}


public List<PowerOutage> doWorstCase(Nerc nerc, Integer max_minutes, Integer max_years){
	
	
	
	events= this.getEvents(nerc);
	
    partial= new ArrayList<PowerOutage>();
	
	System.out.println(events.size()+" outages found for the selected nerc="+nerc.getValue());
	
	the_worst= null;
	
	maxAffectedPeople=0;
	
	return search(partial, max_minutes, max_years);
}


public List<PowerOutage> search (List<PowerOutage> partial, int maxminutes, int maxyear){    // ad ogni livello mi devo chiedere se metto o non metto l ' evento
																							//LIVELLO????
	if (sumAffectedPeople(partial) > maxAffectedPeople) {  // non ho un caso veramente "terminale" però aggiorno la soluzione peggiore ogni volta che ne trovo una valida peggiore
		maxAffectedPeople = sumAffectedPeople(partial);
		the_worst = new ArrayList<PowerOutage>(partial);
		System.out.println(maxAffectedPeople);
		
	}

		for(PowerOutage po: events) {
			if(!partial.contains(po)) {
				
				partial.add(po);
				
				if(checkMaxMinutesOfOutage(partial, maxminutes) && checkMaxYearsDifference(partial, maxyear)) {	
					return search(partial, maxminutes, maxyear);
				}
				
				partial.remove(po);
			}
		}
		
							// generare le soluzioni parziali
	
	
	return the_worst;

}

// mi serve un check sugli anni, scelto il primo gli altri devono essere entro 2 anni

public boolean checkMaxYearsDifference(List<PowerOutage> partial, int maxYearsDifference) {
	
	if(partial.size()>1) {
		int mainyear= partial.get(0).getYear();
		int lastyear= partial.get(partial.size()-1).getYear();
		
		if((lastyear-mainyear)> maxYearsDifference) {
			return false;
		}
	}
	
	return true;
	
}


//mi serve qualcosa che conta le persone sfigate fino a questo momento della ricorsione

public int sumAffectedPeople(List<PowerOutage> partial) {
	int sum = 0;
	for (PowerOutage event : partial) {
		sum += event.getCustomer_affected();
	}
	return sum;
}


//quindi???


//mi serve un livello?? mh


// mi serve il check del massimo numero di ore, sommo tutte le ore della sol parziale e checko
public int sumOutageMinutes(List<PowerOutage> partial) {
	int sum = 0;
	for (PowerOutage event : partial) {
		sum += event.getDuration();
	}
	return sum;
}

private boolean checkMaxMinutesOfOutage(List<PowerOutage> partial, int maxMinutesOfOutage) {
	int sum = sumOutageMinutes(partial);
	if (sum > maxMinutesOfOutage) {
		return false;
	}
	return true;
}




}
