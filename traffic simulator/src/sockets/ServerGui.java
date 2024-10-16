package sockets;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ServerGui extends JFrame {

	private JPanel contentPane;
	public static Protocol protocol;
	Canvas mainCanvas;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui frame = new ServerGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public ServerGui() throws Exception {
		protocol = new Protocol();
		protocol.start();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 80*6, 46*6);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mainCanvas = new Canvas(this,protocol);
		mainCanvas.setBounds(1, 1, 1, 1);
		contentPane.add(mainCanvas);
		
		(new Thread(mainCanvas)).start();
		

		
	}
}
