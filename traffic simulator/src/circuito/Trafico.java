package circuito;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import circuito.Vehiculo.Direction;

public class Trafico extends Thread {
	/**
	 * @author Javier Mena Sanchez
	 */

	private int numCoches = 20;
	public static int posiciones[] = { 15 * 15 + 25, 31 * 15 + 25};
	public static Tile[][] tilesMap;
	public static CopyOnWriteArrayList<Vehiculo> listaCoches;
	private int nRandom = 0;
	public static Ciudad ciudad;
	
	/**
	 * Constructor de clase
	 * 
	 * @param Tile [][] tilesMap
	 * @param Ciudad ciudad
	 */
	
	public Trafico( Tile[][] tilesMap,Ciudad ciudad) {
		this.ciudad = ciudad;
		this.tilesMap = tilesMap;
		this.listaCoches = new CopyOnWriteArrayList<Vehiculo>();
		
	}
	
	/**
	 * Metodo para a�adir un coche en una coordenada cualquiera y darle una direccion
	 * 
	 * @param int y
	 */
	public void a�adirCoche(int y) {
		a�adirCoche(0, y, Direction.RIGHT);
	}
	
	/**
	 * Sobrecarga del metodo a�adirCoche para a�adirlo a la array con el propio thread
	 * 
	 * @param in x
	 * @param in y
	 * @param Direccion direction
	 */
	public void a�adirCoche(int x, int y, Direction direction) {
		Vehiculo vehiculo = new Vehiculo(x, y, tilesMap);
		vehiculo.setDirection(direction);
		vehiculo.start();

		listaCoches.add(vehiculo);
	}
	
	/**
	 * Metodo run del del thread, a�ade coches.
	 */
	@Override
	public void run() {
//		for (int j = 0; j < numCoches; j++) {
		while(true) {
			nRandom = (int) (Math.random()*2);
			a�adirCoche(posiciones[nRandom]);

			try {
				sleep(4500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @return List<Vehiculo> listaCoches
	 */
	public List<Vehiculo> getListaCoches() {
		return listaCoches;
	}
}