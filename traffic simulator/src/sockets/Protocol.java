package sockets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Protocol extends Thread{

    private static final int PORT = 9001;

    public static HashMap<Integer,String> maps = new HashMap<Integer,String>();
    public static HashMap<Integer,Handler> sockets = new HashMap<Integer,Handler>();
    private final static int maxconnections = 9;

    public void run() {
    	System.out.println("The map server is running.");
    	ServerSocket listener = null;
        try {
        	listener = new ServerSocket(PORT);
            while (true) {
                new Handler(listener.accept()).start();
            }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
				listener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }


    static class Handler extends Thread {
        private String map;
        public Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private int num;

        public Handler(Socket socket) {
            this.socket = socket;
            num = first();
            if (num<maxconnections){
            	sockets.put(num,this);
            	System.out.println(sockets.size()+"/9 sockets ocupados");
            }else{
            	System.out.println("Servidor completo.");
            }
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
//                while (true) {
                    out.println("SUBMITMAP");
                    map = in.readLine();
                    if (map == null) {
                        return;
                    }
                    synchronized (maps) {
                        if (!maps.containsValue(map)) {
                            maps.put(num,map);
//                            break;
                        }
                    }
//                }
                out.println("MAPACCEPTED");
                
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    //QUE HACER CUANDO RECIBE MENSAJES (input)
                    procesa(input);
                    
                    //FIN DE MANEJO
                }
            } catch (IOException e) {
            } finally {
                if (map != null) {
                    maps.remove(num);
                }
                System.out.println("Desconecta: "+num);
                    sockets.remove(num);
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        
        public static Integer first() {
        	for (int i = 0; i < 9; i++) {
        		if (!sockets.containsKey(i)){
        			System.out.println("Conecta: "+i);
        			return i;
        		}
        	}
        	return null;
        }
        
        
        public void procesa(String input) {
        	String forward = input.substring(0, 2);
        	int[] newMap = getEntrance(num,input.charAt(2));
        	forward+=String.valueOf(newMap[1]);
        	if (sockets.get(newMap[0])!=null){
        		sockets.get(newMap[0]).out.println(forward);
        	}
        }

		private static int[] getEntrance(int currentMap, char exitGate) {
			int[] entrance = new int[2];
				switch (exitGate) {
					case '0': entrance[0]=currentMap-3;entrance[1]=2;break;
					case '1': entrance[0]=currentMap+1;entrance[1]=3;break;
					case '2': entrance[0]=currentMap+3;entrance[1]=0;break;
					case '3': entrance[0]=currentMap-1;entrance[1]=1;break;
				}
			return entrance;
		}
    }


}