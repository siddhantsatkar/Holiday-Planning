package com.test.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ssdi.POJO.hotelBean;
import com.ssdi.model.ServicesDao;
import com.ssdi.model.databaseFactory;
import com.ssdi.util.ConnectionUtil;
import com.ssdi.util.IConnectionData;
import com.ssdi.util.TestConnection;

public class testHotelSearch {

	private static databaseFactory factory;
	private ServicesDao serviceDao;
	
	@BeforeClass
	public static void myInitialization() throws Exception {
		factory = databaseFactory.getInstance("test");
	}
	
	@Before
	public void setUp() throws Exception {
		serviceDao = factory.createServiceDao();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws SQLException {
		
		IConnectionData connectionData = new TestConnection();
		Connection testConnection = null;
		Statement statement = null;
		String insertSql = null;
		
		insertSql = "insert into hotel values ('r1', 'h1', 'taj', 'this is a 5 star hotel', 5.0);";
		
		/* Invoke function under test */
		try {
			testConnection = ConnectionUtil.getConnection(connectionData);
			
			statement = testConnection.createStatement();
		    statement.executeUpdate(insertSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* Invoke function under test */
		hotelBean hotel= new hotelBean();
		hotel.setRegion("delhi");
		ArrayList<hotelBean> hotelList = serviceDao.searchHotels(hotel);
		
		hotel = hotelList.get(0);
		assertEquals(hotel.getHotelName(), "taj");
		assertEquals(hotel.getDescription(), "this is a 5 star hotel");
	}
}
