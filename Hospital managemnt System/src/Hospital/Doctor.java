package Hospital;
import java.sql.*;

public class Doctor {

		private  Connection connection;
		
		public Doctor(Connection connection) {
			this.connection=connection;
			
		}
		
		public void viewDoctors() {
			String query="select *from doctor";
			try {

				PreparedStatement preparedStatement=connection.prepareStatement(query);
				ResultSet resultset=preparedStatement.executeQuery();
				System.out.println("Doctors");
				System.out.println("+--------+-------------+--------------+");
				System.out.println("|doctorid|Name         |Specialization|");
			    while(resultset.next()) {
			            int id =resultset.getInt("id");
			            String name=resultset.getString("name");
			            
			            String Specialization=resultset.getString("Specialization"); 
			            System.out.printf("|%-8s|%-13s|%-17s| \n",id,name,Specialization);
			            System.out.println("+----------+-------------+-------+-------+");
			    }
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		public boolean getDoctorByid (int id) {
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


