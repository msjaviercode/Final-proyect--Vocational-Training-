package circuito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tile {
	/**
	 * 
	 * @author Javier Mena Sanchez
	 *
	 */
	
	/**
	 * 
	 * enum de tipos
	 *
	 */
	public enum Tipo {
		FONDO(4), CALLEH(2),CALLEV(1), ERROR(-1), CRUCE1(3),SALIDANR(5),SALIDAES(6),SALIDASR(7),SALIDAOS(8);

		private int id;

		private Tipo(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
		/**
		 * Metodo que mira la id y devuelve el tipo de tile a la que pertenece segun la enum de tipos
		 * 
		 * @param int id
		 * @return Tipo tipo
		 */
		public static Tipo parseTipo(int id) {
			for(Tipo each : Tipo.values()) {
				if (each.getId() == id)
					return each;
			}
			return ERROR;
		}
	}

	public final static int H = 15;
	public final static int W = 15;

	public int x;
	public int y;
	
	private List<Vehiculo> vehiculos;
	
	private List<Semaforo> semaforos;

	private Vehiculo.Direction direction;
	
	private Tipo tipo;
	

	/**
	 * Constructor de la clase
	 * 
	 * @param Tipo tipo
	 */
	public Tile(Tipo tipo) {
		this.tipo = tipo;
		this.semaforos = new ArrayList<Semaforo>();
		vehiculos = new CopyOnWriteArrayList<Vehiculo>();
	}
	/**
	 * 
	 * @return List<Vehiculo> 
	 */
	
	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}
	/**
	 * 
	 * @return Boolean
	 */
	
	public boolean hasVehiculo() {
		return vehiculos != null || vehiculos.isEmpty();
	}
	
	/**
	 * 
	 * @return Tipo tipo
	 */
	public Tipo getTipo() {
		return tipo;
	}
	/**
	 * 
	 * @param Direction direction
	 */
	
	public void setDirection(Vehiculo.Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * 
	 * @return Direction direction
	 */
	public Vehiculo.Direction getDirection() {
		return direction;
	}
	/**
	 * 
	 * @return List<Semaforo> 
	 */
	public synchronized List<Semaforo> getSemaforos() {
		return new CopyOnWriteArrayList<Semaforo>(semaforos);
	}
	/**
	 * 
	 * @param Semaforo semaforo
	 */

	public void addSemaforo(Semaforo semaforo) {
		this.semaforos.add(semaforo);
	}
	/**
	 * 
	 * @return boolean
	 */
	public boolean hasSemaforo() {
		return semaforos != null || semaforos.isEmpty();
	}

	/**
	 * 
	 * @param int x
	 * @param int y
	 */
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	/**
	 * Metodo equals para comparar tiles
	 */
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Tile) {
			Tile tile = (Tile) obj;
			
			return tile.x == this.x && tile.y == this.y;
		}
		else {
			return false;
		}
	}

	/**
	 * 
	 * @param Vehiculo vehiculo
	 */
	public void añadirVehiculo(Vehiculo vehiculo) {
		vehiculos.add(vehiculo);
	}
	/**
	 * 
	 * @param Vehiculo vehiculo
	 */
	public void quitarVehiculo(Vehiculo vehiculo) {
		vehiculos.remove(vehiculo);
	}



}