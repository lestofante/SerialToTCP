import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerTCP implements Runnable{
	OutputStream os;
	ServerSocket ss;
	
	ArrayList<Socket> fedeliAscoltatori = new ArrayList<>();
	boolean ascolta = true; 
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(2345);
			while(ascolta){
				Socket s = ss.accept();
				synchronized (fedeliAscoltatori) {
					fedeliAscoltatori.add(s);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		ascolta=false;
		try {
			ss.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Socket l:fedeliAscoltatori){
			try {
				l.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void write(String data) {
		ArrayList<Socket> toRemove = new ArrayList<>();
		for (Socket l:fedeliAscoltatori){
			try {
				l.getOutputStream().write(data.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				toRemove.add(l);
				e.printStackTrace();
			}
		}
		fedeliAscoltatori.removeAll(toRemove);
	}
}
