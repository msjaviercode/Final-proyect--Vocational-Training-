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
		a�adirSemaforo(12, 16, Direction.RIGHT);
		a�adirSemaforo(12, 16, Direction.LEFT);
		
		a�adirSemaforo(28, 16, Direction.UP);
		a�adirSemaforo(28, 16, Direction.DOWN);
		a�adirSemaforo(28, 16, Direction.RIGHT);
		a�adirSemaforo(28, 16, Direction.LEFT);

		
		a�adirSemaforo(28, 32, Direction.UP);   
		a�adirSemaforo(28, 32, Direction.DOWN); 
		a�adirSemaforo(28, 32, Direction.RIGHT);
		a�adirSemaforo(28, 32, Direction.LEFT); 
		
		a�adirSemaforo(12, 32, Direction.RIGHT);
		a�adirSemaforo(12, 32, Direction.LEFT);

		a�adirSemaforo(45, 16, Direction.UP);   
		a�adirSemaforo(45, 16, Direction.DOWN); 
		a�adirSemaforo(45, 16, Direction.RIGHT);
		a�adirSemaforo(45, 16, Direction.LEFT); 

		a�adirSemaforo(45, 32, Direction.UP);   
		a�adirSemaforo(45, 32, Direction.DOWN); 
		a�adirSemaforo(45, 32, Direction.RIGHT);
		a�adirSemaforo(45, 32, Direction.LEFT); 
		
	}
	/**
	 * Metodo para a�adir los semaforos dandoles unas coordenadas, iniciandolos y a�adiendolos a la array de semaforos
	 * 
	 * @param int iX
	 * @param int iY
	 * @param Direction direction
	 */
	public void a�adirSemaforo(int iX, int iY, Direction direction) {
		Semaforo semaforo = new Semaforo(map.getTilesMap()[iX][iY], direction);
		semaforo.start();

		listaSemaforos.add(semaforo);
	}
}
