import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import javax.swing.JTextArea;


public class Finestra extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	JTextArea textArea = new JTextArea();
	JComboBox<String> comboSerial = new JComboBox<String>();
	
	public Finestra() {
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		springLayout.putConstraint(SpringLayout.NORTH, comboSerial, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboSerial, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comboSerial, -476, SpringLayout.EAST, getContentPane());
		getContentPane().add(comboSerial);
		
		JComboBox<Integer> comboBaud = new JComboBox<Integer>();
		springLayout.putConstraint(SpringLayout.NORTH, comboBaud, 1, SpringLayout.NORTH, comboSerial);
		springLayout.putConstraint(SpringLayout.WEST, comboBaud, 6, SpringLayout.EAST, comboSerial);
		springLayout.putConstraint(SpringLayout.EAST, comboBaud, -319, SpringLayout.EAST, getContentPane());
		comboBaud.setEditable(true);
		getContentPane().add(comboBaud);
		
		JComboBox<String> comboTipo = new JComboBox<String>();
		springLayout.putConstraint(SpringLayout.NORTH, comboTipo, 0, SpringLayout.NORTH, comboSerial);
		springLayout.putConstraint(SpringLayout.WEST, comboTipo, 6, SpringLayout.EAST, comboBaud);
		springLayout.putConstraint(SpringLayout.EAST, comboTipo, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(comboTipo);
		
		textArea.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 5, SpringLayout.SOUTH, comboBaud);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 0, SpringLayout.WEST, comboSerial);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, 325, SpringLayout.SOUTH, comboSerial);
		springLayout.putConstraint(SpringLayout.EAST, textArea, 0, SpringLayout.EAST, comboTipo);
		getContentPane().add(textArea);
		
		setSize(800, 200);
	}

	public void addSeriale(String name) {
		comboSerial.addItem(name);
	}

	public void setSeriale(String string) {
		comboSerial.setSelectedItem(string);
	}

	public JTextArea getOutputText() {
		return textArea;
	}
}
