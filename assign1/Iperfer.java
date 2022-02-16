import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Iperfer {

	private static void server(int port) {
		long total = 0;
		long start, end;

		try {
			//Estblish socket connection
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			InputStream clientInputStream = clientSocket.getInputStream();
		
			byte[] buffer = new byte[1000];

			long temp;
			start = System.currentTimeMillis();
			while ((temp = clientInputStream.read(buffer)) != -1) {
				total += temp;
			}
			end = System.currentTimeMillis();
			serverSocket.close();
			System.out.println("received=" + (double)(total/1000) + " KB rate=" + (double)(((total * 8)/((end - start)/1000))/1000000) + " Mbps");
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	private static void client(String hostName, int portNumber, int time) {	
		try {
			Socket socket = new Socket(hostName, portNumber);
			OutputStream socketOutputStream = socket.getOutputStream();

			long total = 0;
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < time * 1000) {
				byte[] message = new byte[1000];
				socketOutputStream.write(message);
				socketOutputStream.flush();
				total += message.length;
			}
			long end = System.currentTimeMillis();
			socket.close();
			System.out.println("sent=" + (double)(total/1000) + " KB rate=" + (double)(((total * 8)/((end - start)/1000))/1000000) + " Mbps");
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Error: missing or additional arguments");
		} else {
			switch(args[0]) {
				case "-c":
					if(args.length == 7 && args[1].equals("-h") && args[3].equals("-p") && args[5].equals("-t")){
						if(Integer.parseInt(args[4]) >= 1024 && Integer.parseInt(args[4]) <= 65535){
							client(args[2], Integer.parseInt(args[4]), Integer.parseInt(args[6]));
							break;
						} else {
							System.out.println("Error: port number must be in the range 1024 to 65535");
							break;
						}	
					}
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
