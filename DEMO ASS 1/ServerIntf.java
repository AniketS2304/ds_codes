import java.rmi.*;


interface ServerIntf extends Remote{
	// Syntax for method declaration: access_specifier return_type method_name(arguments...){ return value}
	public String stringJoin(String str1, String str2) throws RemoteException;
	
}



//ServerIntf.java (Interface)
//➤ This is the common contract shared between the client and the server.
//➤ Both the server (ServerImpl) and the client (Client) use it to know what methods are available for remote calling.
//✅ Used by both Client and Server.

