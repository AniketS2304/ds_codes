import java.rmi.*;

public class Server{
	
	public static void main(String[] args){
	
		try{
			ServerImpl serverImpl = new ServerImpl();
			Naming.rebind("Server", serverImpl);
			
			System.out.println("Server Started....");
		
		}catch(Exception e){
			System.out.println("Exception Occurred At Server!" + e.getMessage());
		}
	}
	
	
}


//Server.java (Server Starter)
// This is the main program for the server.
// It creates an object of ServerImpl and registers it with the RMI registry using the name "Server".
//Uses ServerImpl. Starts the server.


