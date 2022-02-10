public class Iperfer {

	private static void server(int port) {
		System.out.println("Starting server on port: " + port); 	
		try {
			//Estblish socket connection
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine, outputLine;

			outputLine = "1";
			out.println(outputLine);
			//Initiate contact with client
			while((inputLine = in.readLine()) != null) {
				outputLine = "1";
				out.println(outputLine);
				if (outputLine.equals("Bye."))
					break;
			}
		} catch(Exception e) {
			System.out.println(e);

		}
	}

	private static void client(String hostName, int portNumber, int time) {
		System.out.println("Startin client on port: " + portNumber + ", with name: " + hostname + " for " + time + "sec");	
		try {
			Socket clientSocket = new Socket(hostName, portNumber);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch(Exception e) {
			System.out.println(e);

		}
		while ((fromServer = in.readLine()) != null) {
    			System.out.println("Server: " + fromServer);
    			if (fromServer.equals("Bye."))
        			break;
			out.println("iii");
		}


	}

	public static void main(String[] args) {

		if(args.length < 3) {
			System.out.println("Error: missing or additional arguments");
		} else {
			switch(args[0]) {
				case "-c":
					if(args.length == 7 && args[1].equals("-h") && args[3].equals("-p") && args[5].equals("-t")){
						if(Integer.parseInt(args[4]) >= 1024 && Integer.parseInt(args[4]) <= 65535){
							client(args[2],Integer.parseInt(args[4]),Integer.parseInt(args[6]));
							break;
						} else {
							System.out.println("Error: port number must be in the range 1024 to 65535");
							break;
						}	
					}
					break;
				case "-s":
					if(args.length == 3 && args[1].equals("-p")){
						if(Integer.parseInt(args[2]) >= 1024 && Integer.parseInt(args[2]) <= 65535){
							server(Integer.parseInt(args[2]));
							break;
						} else {
							System.out.println("Error: port number must be in the range 1024 to 65535");
							break;
						}
					}
				default:
					System.out.println("Error: missing or additional arguments");
			}
		}
	//intialize socket in constructor
	}
}
