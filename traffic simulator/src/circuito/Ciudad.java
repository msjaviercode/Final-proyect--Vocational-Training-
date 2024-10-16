package circuito;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import sockets.Client;

public class Ciudad extends JFrame {
	/**
	 * @author Javier Mena Sanchez
	 */
	
	
	final static Mapa m = new Mapa();
	public static Trafico trafico;
	Client client;
	/**
	 * Constructor de la clase.
	 */

	public Ciudad() {
		
		setTitle("Metro City");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int alto = 690;
		int ancho = 1200;

		setLocation(d.width / 2 - ancho / 2, d.height / 2 - alto / 2);
		setSize(ancho, alto);
		setVisible(true);
		
		
		m.setSize(ancho,alto);
		getContentPane().add(m);
		
		new Thread(m).start();
		
		client = new Client("localhost");
        client.start();
        
		
	}
	
}