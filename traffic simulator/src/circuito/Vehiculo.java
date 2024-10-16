package circuito;

import java.awt.Color;
import java.awt.Graphics;

import sockets.Client;

public class Vehiculo extends Thread {
	
	
	
	/**
	 * @author Javier Mena Sanchez
	 */
	long id;
	
	/**
	 * enum de direcciones
	 */
	public enum Direction {
		UP,RIGHT, DOWN, LEFT;
	}

	
	
	private int i = 2;
	private boolean elected = false;
	private int x = 0;
	private int y = 0;
	private int ancho = 8;
	private int alto = 4;
	private boolean parado = false;
	private boolean eliminado = false;
	private int velocidades[]={0, 15, 30, 45, 60, 75};

	private Tile[][] tilesMap;
	private Tile tileActual;
	
	private Direction direction;
	
	/**
	 * constructor de la clase 
	 * 
	 * @param int x
	 * @param int y
	 * @param Tile[][] tilesMap
	 */
	public Vehiculo(int x, int y, Tile[][] tilesMap) {
		this.x = x;
		this.y = y;
		this.tilesMap = tilesMap;
		this.tileActual = getTile();
	}

	/**
	 * run del thread
	 * 
	 * en cada ejecucion se mira si hay un vehiculo en el mismo tile y se mueve
	 */
	@Override
	public void run() {
		id = Thread.currentThread().getId();

		while (true) {
			try {
				if (tileActual == null || !tileActual.equals(getTile())) {

					tileActual.quitarVehiculo(this);
					tileActual = getTile();
					tileActual.añadirVehiculo(this);
				}

				moverse();

				sleep(28);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Metodo de pintar, compara la velocidad y el deltatime de pintado del canvas
	 * 
	 * @param Graphics g 
	 * @param float delta
	 */
	public void pintar(Graphics g, float delta) {
		g.setColor(Color.blue);

		
		switch (direction) {
		case UP:
			g.fillRect((int) x, (int) y - (int) (((double) (velocidades[i]) / Math.pow(10, 9)) * (delta)), alto, ancho);
			break;
		case RIGHT:
			g.fillRect((int) x + (int) (((double) (velocidades[i]) / Math.pow(10, 9)) * (delta)), (int) y, ancho, alto);
			break;
		case DOWN:
			g.fillRect((int) x, (int) y + (int) (((double) (velocidades[i]) / Math.pow(10, 9)) * (delta)), alto, ancho);
			break;
		case LEFT:
			g.fillRect((int) x - (int) (((double) (velocidades[i]) / Math.pow(10, 9)) * (delta)), (int) y, ancho, alto);
			break;
		default:
			break;
		}
	}
	/**
	 * Metodo que permite al vehiculo decidir la direccion segun el tile que recorre
	 */
	private void moverse() {
		if(!parado)
		toDirection();	

		detectarVehiculos();


		try {
			setDirection(getTile().getTipo());
		} catch(NullPointerException e) {

		}
	}
	
	/**
	 * 
	 * Metodo que permite mover el vehiculo segun la direccion.
	 */
	private void toDirection() {
		switch (direction) {
		case UP:
			y-=velocidad();
			x = ((int) x / 15) * 15 + 9;
			break;
		case RIGHT:
			x+=velocidad();
			y = ((int) y / 15) * 15 + 9;
			break;
		case DOWN:
			y+=velocidad();
			x = ((int) x / 15) * 15 + 2;
			break;
		case LEFT:
			x-=velocidad();
			y = ((int) y / 15) * 15 + 2;
			break;

		default:
			break;
		}
	}
	
	/**
	 * Metodo que devuelve una velocidad interpolada
	 * @return int i (index de velocidad)
	 */
	private int velocidad() {
		return (int) ((double) velocidades[i] / 15.0);
	}

	/**
	 * Metodo de aceleracion, cambio de velocidades
	 */
	private void acelerar() {
		parado=false;
		}

	/**
	 * Metodo de frenado
	 */
	private void frenar() {
		parado=true;

	}
	/**
	 * Metodo que devuelve el tile por el que pasa el vehiculo
	 * @return Tile result
	 */
	public Tile getTile() {
		Tile result = null;

		try {
			result = tilesMap[x/15][y/15];
		} catch (ArrayIndexOutOfBoundsException e) {

		}

		return result;
	}

	/**
	 * Metodo que devuelve el tile según el numero de tiles por delante introducidos por parametro
	 * 
	 * @param num
	 * @return Tile result
	 */
	private Tile[] getNAheadTiles(int num){
		int iX = x/15; 
		int iY = y/15; 
		Tile[] result = new Tile[num];
		for (int i = 1; i < result.length; i++)
			try {
				switch (direction) {
				case DOWN:
					iY = iY + i;
					break;
				case LEFT:
					iX = iX - i;
					break;
				case RIGHT:
					iX = iX + i;
					break;
				case UP:
					iY = iY - i;
					break;
				}
				result[i] = tilesMap[iX][iY];
			} catch (ArrayIndexOutOfBoundsException e) {
			}

		return result;
	}

	/**
	 * get direction
	 * @return Direction direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * set direction
	 * @param Direction direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	/**
	 *  sobrecarca del metodo setDirection segun el tile pasado por parametro.
	 * 
	 * @param Tipo tipo
	 */

	public void setDirection(Tile.Tipo tipo) {
		switch (tipo) {
		case CALLEH:
			elected = false;
			break;
		case CALLEV:
			elected = false;
			break;
		case CRUCE1:
			direction = getRandomDirection();
			elected = true;
			break;
		case ERROR:
			break;
		case FONDO:
			break;
		case SALIDANR:
		if(!eliminado){
			Client.out.println("530");
			Trafico.listaCoches.remove(this);
			}
			eliminado =true;
			break;
		case SALIDAES:
			if(!eliminado){
			Client.out.println("531");
			Trafico.listaCoches.remove(this);
			}
			eliminado =true;	
			break;
		case SALIDASR:
			if(!eliminado){
			Client.out.println("532");
			Trafico.listaCoches.remove(this);
			}
			eliminado =true;
			break;
		case SALIDAOS:
			break;
		default:
			break;
		}
	}
	/**
	 * Metodo para elegir la direccion en el cruce de forma aleatoria
	 * @return Direction direction
	 */
	private Direction getRandomDirection() {
		int aleatorio = (int) (Math.random() * 4);
		if (elected == false) {
			switch (aleatorio) {
			case 0:
				return direction == Direction.DOWN ? getRandomDirection() : Direction.UP;
			case 1:
				return direction == Direction.UP ? getRandomDirection() : Direction.DOWN;
			case 2:
				return direction == Direction.LEFT ? getRandomDirection() : Direction.RIGHT;
			case 3:
				return direction == Direction.RIGHT ? getRandomDirection() : Direction.LEFT;
			}
		} 

		return direction;
	}

	/**
	 * Metodo que permite detectar si hay un vehiculo en los tiles dados por el metodo getNAheadTiles
	 */
	private void detectarVehiculos() {
		Tile[] aheadTiles = getNAheadTiles(2);

		for (Tile tile : aheadTiles) {
			if (tile != null && tile.hasVehiculo()) {
				for (Vehiculo each : tile.getVehiculos()) {
					comprobarVehiculo(each);
				}
			}
			else {
				acelerar();
				detectarSemaforos();
			}
		}
	}

	/**
	 * permite mirar si el tile continuo en la misma direccion tiene un semaforo
	 */
	private void detectarSemaforos(){
		Tile[] aheadTiles = getNAheadTiles(2);

//		int aux = 1;
		for (Tile tile : aheadTiles) {
			if (tile != null && tile.hasSemaforo()) {
				for (Semaforo sem : tile.getSemaforos()) {
					if (sem.getDirection() == this.direction)
						comprobarSemaforo(sem);
				}
			}

			
		}
	}
	/**
	 * Comprueba si hay un vehiculo con la misma id y direccion
	 * 
	 * @param Vehiculo ve
	 */

	private void comprobarVehiculo(Vehiculo ve) {
		if (ve.id != id && ve.direction != direction) {
			acelerar();
		}
		else {
			frenar();
		}
		
	}
	/**
	 * Mira el estado del semaforo
	 * 
	 * @param Semaforo sem
	 */
	
	private void comprobarSemaforo(Semaforo sem) {

		switch (sem.getEstado()) {
		case ROJO:
			frenar();
			parado = true;
			break;
		case VERDE:
			acelerar();
			parado = false;
			break;
		default:
			break;
		}
	}
	
	
}
