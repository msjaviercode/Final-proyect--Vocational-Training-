package sockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import circuito.Mapa;
import circuito.Tile;
import circuito.Tile.Tipo;
import circuito.Trafico;
import circuito.Vehiculo;
import circuito.Vehiculo.Direction;

public class Client extends Thread{

    private static BufferedReader in;
    public static PrintWriter out;
    private static String serverAddress;
    private Socket socket;
    public static boolean online = false;
    static Trafico trafico;
    
    
    public Client(String ip) {
    	serverAddress = ip;
    }


    /*
     * METODO QUE DEBE LEER EL MAPA Y REPRESENTARLO CON 0=NADA Y 1=CARRETERA O CRUCE
     */
    private String getMap() {
    	int[] map = Mapa.map;
    	String sendMap = "";
    	for (int i = 0; i < map.length; i++) {
				if(map[i]==4){
					sendMap+=0;					
				}else{
					sendMap+=1;					
				}
    	}
    	return sendMap;
    }

    /**
     * THREAD QUE MANTIENE EL SOCKET ABIERTO Y RECIBE DATOS DESDE EL SERVIDOR
     * LOS DATOS LOS RECIBE MEDIANTE in.readLine(); Y LOS VUELCA SOBRE EL STRING
     * EL ATRIBUTO BOOLEANO ONLINE VARIA SU VALOR DEPENDIENDO DE SI ESTA CONECTADO
     */
    public void run() {
        try {
			socket = new Socket(serverAddress, 9001);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			while (true) {
				if (socket.isConnected()){
					online = true;
					String line = in.readLine();
					if (line.startsWith("SUBMITMAP")) {
						out.println(getMap());
					} else if (line.startsWith("MAPACCEPTED")) {
						System.out.println("Mapa recibido");
					} else {
						recibeCoche(line);
					}
				}
			}
		} catch (Exception e) {
			online = false;
			System.out.println("Desconectado");
		}
    }
    
    public boolean recibeCoche(String coche){
    	boolean result = false;
    	if (coche.length()==3){
    		try {
				creaCoche(Character.getNumericValue(coche.charAt(0)),Character.getNumericValue(coche.charAt(1)),Character.getNumericValue(coche.charAt(2)));
				result = true;
			} catch (Exception e) {
			}
    	}
    	return result;
    }

    /**
     * METODO QUE DEBEMOS PERSONALIZAR PARA INTRODUCIR LOS VEHÃ�CULOS EN NUESTRO MAPA
     * SIENDO VEL LA VELOCIDAD, COL EL COLOR Y ENTRADA LA ENTRADA ;)
     */
    private void creaCoche(int vel, int col, int entrada) {
		System.out.println("Coche creado: V"+vel+" C"+col+" E"+entrada);
		int entra;
		if (entrada==0||entrada==1){
			entra=490;
		}else{
			entra=250;
		}
		
		Vehiculo v = new Vehiculo(0,entra,Trafico.tilesMap);
		v.setDirection(Direction.RIGHT);
		v.start();
		Trafico.listaCoches.add(v);
		
	}


//	public static void main(String[] args) throws Exception {
//        Client client = new Client("localhost",trafico);
//        client.start();
//        Scanner s = new Scanner(System.in);
//        String msg; 
//        while (online) {
//        	msg = s.next();
//        	out.println(msg);
//		}
//        s.close();
//    }
}