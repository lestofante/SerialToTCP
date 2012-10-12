import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.util.prefs.Preferences;

public class MainLeggiSeriale implements WindowListener{
	
	private Preferences prefs= Preferences.userNodeForPackage(MainLeggiSeriale.class);;
	private Finestra fin;
	private SerialPort serial;
	private boolean connected;
	int baudRate=19200;
	
	public static void main(String args[]) {
		new MainLeggiSeriale();
	}

	
	
	public MainLeggiSeriale() {
		RXTXPathSetter.setPaths();
		//loadNatives();

		fin = new Finestra();
		
		//fin.setDefaultCloseOperation(JFrame.)
		
		String defaultValue = null;
		String propertyValue = prefs.get("seriale", defaultValue); // "a string"

		@SuppressWarnings("unchecked")
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();

		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			System.out.println(portIdentifier.getName() + " - "
					+ getPortTypeName(portIdentifier.getPortType()));
			fin.addSeriale(portIdentifier.getName());
		}

		if (propertyValue != null) {
			connetti(propertyValue);

			if (serial == null || !connected) {
				fin.addSeriale("");
				fin.setSeriale("");
			} else {
				fin.setSeriale(propertyValue);
			}
		} else {
			fin.addSeriale("");
			fin.setSeriale("");
		}

		fin.setVisible(true);
	}
	
	static String getPortTypeName(int portType) {
		switch (portType) {
		case CommPortIdentifier.PORT_I2C:
			return "I2C";
		case CommPortIdentifier.PORT_PARALLEL:
			return "Parallel";
		case CommPortIdentifier.PORT_RAW:
			return "Raw";
		case CommPortIdentifier.PORT_RS485:
			return "RS485";
		case CommPortIdentifier.PORT_SERIAL:
			return "Serial";
		default:
			return "unknown type";
		}
	}
	SerialReader reader;
	private void connetti(String portName) {
		try{
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Errore: La porta è in uso");
			//info.append("\nErrore: La porta è in uso");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(), baudRate);

			if (commPort instanceof SerialPort) {
				serial = (SerialPort) commPort;
				serial.setSerialPortParams(baudRate, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				connected = true;
				InputStream in = serial.getInputStream();

				// (new Thread(inputSeriale)).start();
				// OutputStream out = serialPort.getOutputStream();

				// (new Thread(new SerialWriter(out))).start();
				reader = new SerialReader(in, fin.getOutputText());
				serial.addEventListener(reader);
				fin.addWindowListener(this);
				serial.notifyOnDataAvailable(true);
				
				byte[] b = new byte[11];
				for (int i=0; i<b.length;i++){
					b[i]='a';
				}
				serial.getOutputStream().write(b);
				//info.append("\nConnesso alla porta seriale: "+portName);
			} else {
				System.out.println("Errore: non è ��  porta seriale valida.");
				//info.append("\nErrore: non è una <--?? porta seriale valida");
			}
		}
		}catch (Exception e){
			connected=false;
			//info.append("\nErrore di connessione seriale con la porta: " + portName);
			e.printStackTrace();
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}



	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		if (reader!=null){
			reader.chiudi();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serial.close();
		System.exit(0);
	}



	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
