package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<PowerOutage> getEvents(Nerc n) {
	
		String sql = "SELECT nerc_id, customers_affected, date_event_began, date_event_finished "+
				"FROM poweroutages AS po, nerc AS n "+
				"WHERE po.nerc_id= n.id AND n.id= ? "+
				"ORDER BY date_event_began";
		
		List<PowerOutage> events= new ArrayList<>();
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
			Integer nid= res.getInt("nerc_id");
			Integer ncust= res.getInt("customers_affected");
			LocalDateTime start =  res.getTimestamp("date_event_began").toLocalDateTime();
			LocalDateTime end =  res.getTimestamp("date_event_finished").toLocalDateTime();
			

			PowerOutage po= new PowerOutage(nid,ncust,start,end);
			events.add(po);

			conn.close();
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return events;
	
	
		
		
	}
	

}
