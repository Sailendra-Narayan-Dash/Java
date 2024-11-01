package Hospital;
import java.sql.*;
import java.util.Scanner;
public class Hospital {

		// TODO Auto-generated method stub
	    private static final String url="jdbc:mysql://localhost:3306/hospital";
	    private static final String username="root";
	    private static final String password="Sailendra@123";
	

		public static void main(String[] args) {
			try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			Scanner sc=new Scanner(System.in);
					
			try {
				Connection connection=DriverManager.getConnection(url,username,password);
				Patient patient=new Patient(connection,sc);
				Doctor doctor=new Doctor(connection);
				while(true) {
					System.out.println("HOSPITAL SYSTEM");
					System.out.println("1.Add patient");
					System.out.println("2.view patient");
					System.out.println("3.view Doctor");
					System.out.println("4.Book Appointment");
					System.out.println("5.Exit");
					System.out.println("Enter Your Choice");
					int choice=sc.nextInt();
					switch(choice) {
					case 1:
						patient.addpatient();
						System.out.println();
					case 2:
						patient.viewpatient();
						System.out.println();
					case 3:
						doctor.viewDoctors();
						System.out.println();
					case 4:
						bookAppointment(patient,doctor,connection,sc);
						System.out.println();
					case 5:
					return;
					case 6: System.out.println("Enter a valid Choice");
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}

	}
		public static void bookAppointment(Patient patient,Doctor doctor ,Connection connection,Scanner sc) {
		System.out.println("Enter patient id:");
		int patientid=sc.nextInt();
		System.out.println("Enetr Doctor Id:");
		int doctorid=sc.nextInt();
		System.out.println("Enter appointment date (DD-MM-YYYY): ");
		String appointmentdate=sc.next();
		if(patient.getpatientByid(patientid)&& doctor.getDoctorByid(doctorid)) {
			if(checkDoctoravailability(doctorid,appointmentdate, connection)) {
				String appointquery="insert into appointment(patient_id,doctor_id,appointment_date) values(?,?,?)";
			try
			{
				PreparedStatement preparedStatement=connection.prepareStatement(appointquery);
				preparedStatement.setInt(1, patientid);
				preparedStatement.setInt(2, doctorid);
				preparedStatement.setString(3, appointmentdate);
				int rowafected=preparedStatement.executeUpdate();
				if(rowafected>0) {
					System.out.println("Apointment Booked");
				}else {
					System.out.println("failed to Book");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			}
			
		
		}else {
			System.out.println("either patient or doctor not exist");
		}
		
		}
    private static boolean checkDoctoravailability(int doctorid, String appointmentdate,Connection connection) {
			String query="select count(*) From appointment where doctor_id=? And appointment_date=?";
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(query);
				preparedStatement.setInt(1, doctorid);
				preparedStatement.setString(2,appointmentdate);

				ResultSet resultSet=preparedStatement.executeQuery();
				if(resultSet.next()) {
					int count=resultSet.getInt(1);
					if(count==0) {
						return true;
					}else {
						return false;
					}
				}

				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	

}