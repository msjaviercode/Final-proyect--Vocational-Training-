package circuito;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import circuito.Vehiculo.Direction;

public class Semaforo extends Thread {
	/**
	 * 
	 * @author Javier Mena Sanchez
	 *
	 */
	/**
	 * 
	 * enum de estados del semaforo
	 *
	 */
	enum Estado {
		VERDE, ROJO;
	}
	
	
	private static final int ANCHO = 5;
	private static final int ALTO = 5;
	
	private Direction direction;
		
	
	private Tile tile;

	private Estado estado;
	
	/**
	 * constructor de la clase, implanta el estado inicial aleatoriamente
	 * 
	 * @param Tile tile
	 * @param Direction direction
	 */
	
	public Semaforo(Tile tile, Direction direction) {
		this.direction = direction;
		this.tile = tile;
		this.tile.addSemaforo(this);
		int aleatorio = (int) (Math.random() * 2);
		switch(aleatorio){
		case 0:
			setEstado(Estado.ROJO);
			break;
		case 1:
			setEstado(Estado.VERDE);
		}
		
		
	}
	
	/**
	 * Run del thread, cambia de estado.
	 */
	
	@Override
	public void run() {
		while(true){
			if (puedeCambiar() || estado == Estado.VERDE)
				cambio();

			try {
				sleep(1400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Metodo que decide si un semaforo puede cambiar a verde segun si hay otro en el mismo tile con el mismo estado.
	 * 
	 * @return boolean 
	 */
	
	private boolean puedeCambiar() {
		for (Semaforo each : tile.getSemaforos()) {
			if (each.getEstado() == Estado.VERDE) {
				return false;
			}
		}

		return true;
	}

/**
 * Metodo que cambia el estado, siempre contrario.
 */
	public void cambio(){
		
		
		switch (estado) {
		case ROJO:
			setEstado(Estado.VERDE);
			break;
		case VERDE:
			setEstado(Estado.ROJO);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Metodo para pintar el semaforo
	 * 
	 * @param Graphics g
	 */
	public void pintar(Graphics g) {
		if (estado == Estado.VERDE)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);
		
		Rectangle rectangle = calculeRectangle();
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	/**
	 * Metodo que devuelve la direccion a la que afecta el semaforo
	 * 
	 * @return Direction direction
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Metodo que calcula y devuelve un rectangulo segun la direccion a la que representa.
	 * 
	 * @return Rectangle
	 */
	
	private Rectangle calculeRectangle() {
		switch (direction) {
		case DOWN:
			return new Rectangle(tile.x, tile.y, ANCHO, ALTO);
		case LEFT:
			return new Rectangle(tile.x + tile.W - ANCHO , tile.y, ANCHO, ALTO);
		case RIGHT:
			return new Rectangle(tile.x, tile.y + tile.H - ALTO, ANCHO, ALTO);
		case UP:
			return new Rectangle(tile.x + tile.W - ANCHO, tile.y + tile.H - ALTO, ANCHO, ALTO);
		default:
			return new Rectangle(tile.x, tile.y, ANCHO, ALTO);
		}
	}
	
	/**
	 * 
	 * @return Estado estado
	 */
	public Estado getEstado(){
		return estado;
	}
	/**
	 * 
	 * @param Estado estado
	 */
	
	/**
	 * 
	 * @param estado
	 */
	public void setEstado(Estado estado){
		this.estado = estado;
	}
	/**
	 * 
	 * @return tile
	 */
	public Tile getTile() {
		return tile;
	}
	/**
	 * 
	 * @param tile
	 */
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
