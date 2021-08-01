package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.sql.DataSource;

public class StudentDbUtil {

	
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		
		dataSource = theDataSource;
	}
	
	//Create a method to list the students
	public List<Student> getStudents() throws Exception {
		
		//Create an empty arrayList called students 
		List<Student> students = new ArrayList<>();

		//Set up JDBC
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
		//get a connection 
		myConn = dataSource.getConnection();
		//create a sql statement 
		String sql = "SELECT * from student ORDER BY last_name";
		myStmt = myConn.createStatement();
		
		//execute query 
		myRs = myStmt.executeQuery(sql);

		//process result set (loop results)
		while (myRs.next()) {
			
			//Retrieve data from result set row 
			//Cast the parameters 
			int id = myRs.getInt("id");
			String firstName = myRs.getString("first_name");
			String lastName = myRs.getString("last_name");
			String email = myRs.getString("email");
			
			//create new student object  
//			Student tempStudent = new Student(myRs.getInt("id"), myRs.getString("first_name"), myRs.getString("last_name"),
//					myRs.getString("email"));
		
			Student tempStudent = new Student(id, firstName, lastName, email);
			
			//TEST: (Java makes it works like this >>)  Student test = new Student(2,"firstName","lastName","email");
			
			//Add to list of students 
			students.add(tempStudent);
		}
		
	
			return students;
			
		} finally {
			//close JDBC: (Java makes a method to close, turn each off through if statement 
					close(myConn, myStmt, myRs);
		}
			
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		
		//Try to close each object
		try {
			if(myConn != null) {
				myConn.close();
			}
			if(myStmt != null) {
				myStmt.close();
			}
			if(myRs != null) {
				myRs.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void addStudent(Student theStudent) throws Exception {
		// TODO Auto-generated method stub
	
	//Generate Connection/ prepared statement
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		
		try {
		//get DB connection (throw exception for dataSource)
			myConn = dataSource.getConnection();
			
			
			//create sql insert
			String sql = "insert into student "
					   + "(first_name, last_name, email) "
					   + "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
		//set the param values for student 
		myStmt.setString(1, theStudent.getFirstName());
		myStmt.setString(2, theStudent.getLastName());
		myStmt.setString(3, theStudent.getEmail());
			
		//execute sql insert 
		myStmt.execute();
		} finally {
		//clean JDBC objects
		
			close(myConn, myStmt, null);
		}
		
	}

	public Student getStudent(String theStudentId) throws Exception {
		Student theStudent = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try {
			//convert student id to int: parsing 
			studentId = Integer.parseInt(theStudentId);
			
			//get connection to DB
			myConn = dataSource.getConnection();
			
			//create sql string to get selected student by id 
			String sql = "SELECT * FROM student WHERE id=?";
			
			//collect prepareed statement 
			myStmt = myConn.prepareStatement(sql);
			
			//set parameters for myStmt 
			myStmt.setInt(1, studentId);
			
			//execute statement 
			myRs = myStmt.executeQuery();
			
			//Retrieve parameters from result set row except id 
			if(myRs.next()) {
				
				String firstName= myRs.getString("first_name");
				String lastName= myRs.getString("last_name");
				String email= myRs.getString("email");

				//use studentId during construction 
				theStudent = new Student(studentId, firstName, lastName, email);
			} else {
				throw new Exception("Could not find student id: " + studentId );
			}

			//return info
			return theStudent;
			
			
		} finally {
			//clean JDBC
			close(myConn, myStmt, myRs);
		}
		
		
	}

	public void updateStudent(Student theStudent) throws Exception {
		// TODO Auto-generated method stub
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
		//create a conenction 
		myConn = dataSource.getConnection();
		
		//TODO: create sql udpate statement
		String sql = "UPDATE student " +
		"set first_name=?, last_name=?, email=? " +
				"where id=?";
		
		//prepare statement
		myStmt = myConn.prepareStatement(sql);
		
		//set params
		myStmt.setString(1, theStudent.getFirstName());
		myStmt.setString(2, theStudent.getLastName());
		myStmt.setString(3, theStudent.getEmail());
		myStmt.setInt(4, theStudent.getId());

		
		//execute sql statement
		myStmt.execute();
		
		}
		 finally {
				close(myConn, myStmt, null);
			}
			
	}

	public void deleteStudent(String theStudentId) throws Exception {
		// TODO Auto-generated method stub
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			//Convert student id to int 
			int studentId = Integer.parseInt(theStudentId);
			
			//get connection to db
			myConn = dataSource.getConnection();
			
			//create sql to delete student 
			String sql = "DELETE FROM student WHERE id=?";
			
			//prepare statement 
			myStmt = myConn.prepareStatement(sql);
			
			//set statement params
			myStmt.setInt(1, studentId);
			
			//execute sql
			myStmt.execute();
		} finally {
			
			close(myConn, myStmt, null);
		}
		
		
	}

}
