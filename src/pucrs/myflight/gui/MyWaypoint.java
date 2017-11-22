
package pucrs.myflight.gui;

import java.awt.Color;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * Um waypoint que tem uma cor e um texto associados
 * @author Martin Steiger, Marcelo Cohen
 */
public class MyWaypoint extends DefaultWaypoint
{
	private Color color;
	private final String label;
	private int size;

	/**
	 * @param color a cor
	 * @param coord a localização
	 */
	public MyWaypoint(Color color, String label, GeoPosition coord, int size)
	{
		super(coord);
		this.color = color;
		this.label = label;
		this.size = size;
	}

	/**
	 * @returns a cor do waypoint
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * @returns o texto do waypoint
	 */
	public String getLabel() {
		return label;
	}
	
	public void setSize(int n){
		size = n;
	}
	
	/**
	 * 
	 * @returns o tamanho do waypoint
	 */
	public int getSize() {
		return size;
	}
	
	public void setGreen(){
		Color green;
		green = Color.GREEN;
		this.color = green;
	}
	
	public void setYellow(){
		Color green;
		green = Color.YELLOW;
		this.color = green;
	}
	
	public void setRed(){
		Color green;
		green = Color.RED;
		this.color = green;
	}
	
	public void setGray(){
		Color green;
		green = Color.DARK_GRAY;
		this.color = green;
	}
}
