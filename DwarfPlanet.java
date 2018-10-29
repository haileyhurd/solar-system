
// Hailey Hurd
import javafx.scene.paint.*; // colors
import javafx.scene.canvas.*; // canvas
import javafx.scene.text.*; // fonts
import javafx.scene.layout.*; // grid

// DwarfPlanet Class
// DwarfPlanet is a child of the Planet class. It is exactly the same but the constructor only makes small planets and the label line is 
// dotted instead of solid.
public class DwarfPlanet extends Planet {
	public DwarfPlanet (String name, double distance, Color color) {
		super(name, distance, 0.5, color);

	}
	public void labelLine (GridPane grid, GraphicsContext g, double start_x, double start_y, double length) {
		double total_length = 0;
		for(int i = 0; total_length < length; i++){
			g.fillRect(start_x, start_y+(10*i), 2, 10);
			start_y += 5;
			total_length += 15;
		}
	}
	public static void main (String[] args){
	}
}