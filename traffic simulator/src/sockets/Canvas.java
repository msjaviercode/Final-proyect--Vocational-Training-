package sockets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JPanel;


public class Canvas extends JPanel implements Runnable{
	
	private static ServerGui serverGui;
	private static Protocol protocol;
	private double ancho;
	private double alto;

	public Canvas(ServerGui serverGui, Protocol protocol){
		super();
		Canvas.serverGui = serverGui;
		Canvas.protocol = protocol;
		this.addMouseListener(new MouseClickHandler());
	}
	
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		serverGui.mainCanvas.setBounds(1, 1, serverGui.getWidth()-2, serverGui.getHeight()-24);
		ancho = this.getWidth();
		alto = this.getHeight();
		double unitH = ancho/(80*3);
		double unitV = alto/(46*3);
		double[] x = {0,ancho/3,ancho/3*2,0,ancho/3,ancho/3*2,0,ancho/3,ancho/3*2};
		double[] y = {0,0,0,alto/3,alto/3,alto/3,alto/3*2,alto/3*2,alto/3*2};
		
		
		//DIBUJA FONDO
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//DIBUJA CARRETERAS
		g.setColor(Color.WHITE);
		for (int i = 0; i < 9; i++) {
			if (Protocol.maps.containsKey(i)){
				String map = Protocol.maps.get(i);
				int cont = 0;
				for (int j = 0; j < 46; j++) {
					for (int j2 = 0; j2 < 80; j2++) {
						if (map.charAt(cont)=='1'){
							g2.setPaint(Color.white);
						    g2.fill(new Rectangle2D.Double(x[i]+(unitH*j2), y[i]+(unitV*j), unitH, unitV));
						}
						cont++;
					}
				}
				
//				String words = protocol.sockets.get(i).socket.getRemoteSocketAddress().toString();  
//				g2.setColor(Color.red);  
//				Font textFont = new Font("Arial", Font.BOLD, (int)unitH*10);  
//				g2.setFont(textFont);  
//				g2.drawString(words,  (int)x[i], (int)(y[i]+(alto/3))); 
				
			}
		}
		//DIBUJA LINEAS SEPARACION
		g2.draw(new Line2D.Double(ancho/3, 0, ancho/3, alto));
		g2.draw(new Line2D.Double((ancho/3)*2, 0, (ancho/3)*2, alto));
		g2.draw(new Line2D.Double(0, alto/3, ancho, alto/3));
		g2.draw(new Line2D.Double(0, (alto/3)*2, ancho, (alto/3)*2));
	}
	
	public class MouseClickHandler implements MouseListener{
		public void mouseClicked(MouseEvent e){
//			System.out.print("Click en ");
//			System.out.println(e.getX() + "," + e.getY());
//			System.out.println("ancho: "+ancho/3+" alto:"+alto/3);
//			System.out.println(e.getY()/((int)alto/3)*3+e.getX()/((int)ancho/3));
			int rem = e.getY()/((int)alto/3)*3+e.getX()/((int)ancho/3);
			try {
				if (Protocol.sockets.get(rem)!=null){
					Protocol.sockets.get(rem).socket.close();
				}
			} catch (IOException e1) {
			};
			Protocol.sockets.remove(rem);
			Protocol.maps.remove(rem);
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
	}

	@Override
	public void run() {
		while (true) {
			this.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
