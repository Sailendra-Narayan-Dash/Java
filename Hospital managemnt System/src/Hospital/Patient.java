package Hospital;
import java.util.*;
import java.lang.invoke.MethodHandles.Lookup.ClassOption;
import java.sql.*;
public class Patient {
	private  Connection connection;
	private Scanner scanner;
	public Patient(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	public void addpatient() {
		System.out.println("Enter Patient name:");
		String name=scanner.next();
		System.out.println("Enter patinetage:");
		int age=scanner.nextInt();
		System.out.println("Enter patient gender:");
		String gender=scanner.next();
	
	try {
		String query="iNSERT INTO patient(name,age,gender) VALUES(?,?,?)";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		
		preparedStatement.setString(1,name);
		preparedStatement.setInt(2,age);
		preparedStatement.setString(3,gender);
		int affectedrows=preparedStatement.executeUpdate();
		if(affectedrows>0) {
			System.out.println("patient Added Succesfully");
		}else {
			System.out.println("failed to add patient ");
		}
		
		
	}catch(SQLException e) {
		e.printStackTrace();
	}

	}
	public void viewpatient() {
		String query="select *from patient";
		try {

			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultset=preparedStatement.executeQuery();
			System.out.println("Patient");
			System.out.println("+----------+-------------+-------+-------+");
			System.out.println("| patientid|Name         | Age   | gender|");
			System.out.println("+----------+-------------+-------+-------+");
		    while(resultset.next()) {
		            int id=resultset.getInt("id");
		            String name=resultset.getString("name");
		            int age=resultset.getInt("age");
		            String gender=resultset.getString("gender"); 
		            System.out.printf("|%-12s|%-13s|%-7s|%-7s \n",id,name,age,gender);
		            System.out.println("+----------+-------------+-------+-------+");
		    }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean getpatientByid (int id) {
		String query="select *from Patient where id=?";
		try {

			PreparedStatement preparedStatement=connection.prepareStatement(query);
			 preparedStatement.setInt(1, id);
			 ResultSet resultset=preparedStatement.executeQuery();
			 if(resultset.next()) {
				 return true;
			 }else {
				 return false;
			 }
			 }
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

}
