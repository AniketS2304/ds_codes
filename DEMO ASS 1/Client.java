import java.rmi.*;
import java.util.Scanner;



public class Client{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		try{
			String serverURL = "rmi://localhost/Server";
			ServerIntf serverIntf = (ServerIntf) Naming.lookup(serverURL);
			
			System.out.print("Enter First String: ");
			String str1 = sc.nextLine();
			
			System.out.print("Enter Second String: ");
			String str2 = sc.nextLine();
			
			
		
			System.out.println("--------------- Results ---------------");
			System.out.println("Strings After Joining Is: " + serverIntf.stringJoin(str1, str2));
			
			
		}catch(Exception e){
			System.out.println("Exception Occurred At Client!" + e.getMessage());
		}
		
	}

}






//Client.java (Client Program)
//This is the main program for the client.
// It connects to the server using the name "Server" and calls the stringJoin() method remotely.
// It then prints the result returned from the server.