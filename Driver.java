package jdbc;
import java.sql.*;
import java.util.Scanner;

public class Driver {

	public static int decode(String tinyURL) {
		int convID=0;
		String code = "abcdefghijklmnopqrstuvwkyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for(int i=0; i<tinyURL.length(); i++) {
			int ind=code.indexOf(tinyURL.charAt(i));
			convID+=ind*Math.pow(62, i);
		}
		return convID;
	}


	public static String encode(int id) {
		String convURL="";
		int rem=0;
		String code = "abcdefghijklmnopqrstuvwkyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		while(id>0) {
			rem = id%62;
			convURL+=code.charAt(rem);
			id=id/62;
		}
		return convURL;
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		Scanner scan = new Scanner(System.in);
		String websiteURL="";
		String smallURL="";

		System.out.println("Would you like to encode or decode a URL?");
		String answer = scan.nextLine();

		while(!(answer.equals("encode")||answer.equals("decode"))) {
			System.out.println("Would you like to encode or decode a URL?");
			answer = scan.nextLine();
		}

		if(answer.equals("encode")) {
			System.out.println("Enter orginal URL: (choose between: https://twitter.com, https://www.amazon.com, https://www.facebook.com, "
					+ "https://www.google.com"+"https://www.hulu.com"+"https://www.microsoft.com/en-us/"+ "https://www.mysql.com"
					+ "https://www.netflix.com" + "https://www.oracle.com/index.html" + "https://www.reddit.com)");
			websiteURL = scan.nextLine();
			websiteURL = "\""+websiteURL+"\"";
		}
		
		
		else{ 
			System.out.println("Enter the tiny URL portion following the foward slash (i.e.: 'vQ3Pfb' )");
			smallURL = scan.nextLine();
		}

		
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/URLshortener?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", "student", "student");
			myStmt = myConn.createStatement();

			//statement for retrieving ID number
			if(websiteURL!="") {
				String IDstatement="Select * from URLs where URL=" + websiteURL;
				myRs = myStmt.executeQuery(IDstatement);
				myRs.next();
				int id = Integer.parseInt(myRs.getString("id"));
				System.out.println("Tiny URL produced: elai.na/"+ encode(id)); 

			}

			//statement for retrieving big URL
			else {
				String URLstatement="Select * from URLs where id=" + decode(smallURL);
				myRs = myStmt.executeQuery(URLstatement);
				myRs.next();
				System.out.println("Original website URL: " + myRs.getString("URL"));}

		}
		catch(Exception exc){
			exc.printStackTrace();
		}




	}

}
