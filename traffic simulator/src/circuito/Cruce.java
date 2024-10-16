package circuito;

import java.util.concurrent.CopyOnWriteArrayList;

import circuito.Vehiculo.Direction;


public class Cruce extends Thread{
	/**
	 * @author Javier Mena Sanchez
	 */
	private Mapa map;

	private CopyOnWriteArrayList<Semaforo> listaSemaforos;
	
	/**
	 * Constructor de la clase
	 * 
	 * @param Mapa map
	 */
	public Cruce(Mapa map) {
		this.listaSemaforos = new CopyOnWriteArrayList<Semaforo>();
		this.map = map;
	}

	public CopyOnWriteArrayList<Semaforo> getListaSemaforos() {
		return listaSemaforos;
	}

	@Override
	/**
	 * run del thread, coloca los semaforos segun la direccion
	 */
	public void run() {
		añadirSemaforo(12, 16, Direction.RIGHT);
		añadirSemaforo(12, 16, Direction.LEFT);
		
		añadirSemaforo(28, 16, Direction.UP);
		añadirSemaforo(28, 16, Direction.DOWN);
		añadirSemaforo(28, 16, Direction.RIGHT);
		añadirSemaforo(28, 16, Direction.LEFT);

		
		añadirSemaforo(28, 32, Direction.UP);   
		añadirSemaforo(28, 32, Direction.DOWN); 
		añadirSemaforo(28, 32, Direction.RIGHT);
		añadirSemaforo(28, 32, Direction.LEFT); 
		
		añadirSemaforo(12, 32, Direction.RIGHT);
		añadirSemaforo(12, 32, Direction.LEFT);

		añadirSemaforo(45, 16, Direction.UP);   
		añadirSemaforo(45, 16, Direction.DOWN); 
		añadirSemaforo(45, 16, Direction.RIGHT);
		añadirSemaforo(45, 16, Direction.LEFT); 

		añadirSemaforo(45, 32, Direction.UP);   
		añadirSemaforo(45, 32, Direction.DOWN); 
		añadirSemaforo(45, 32, Direction.RIGHT);
		añadirSemaforo(45, 32, Direction.LEFT); 
		
	}
	/**
	 * Metodo para añadir los semaforos dandoles unas coordenadas, iniciandolos y añadiendolos a la array de semaforos
	 * 
	 * @param int iX
	 * @param int iY
	 * @param Direction direction
	 */
	public void añadirSemaforo(int iX, int iY, Direction direction) {
		Semaforo semaforo = new Semaforo(map.getTilesMap()[iX][iY], direction);
		semaforo.start();

		listaSemaforos.add(semaforo);
	}
}
