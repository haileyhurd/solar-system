
// Hailey Hurd
import javafx.scene.paint.*; // colors
import javafx.scene.canvas.*; // canvas
import javafx.scene.text.*; // fonts
import javafx.scene.layout.*; // grid

// Planet Class
// This class is for a planet, and contains the method for displaying the planet on the canvas. This is a class instead of part of the main
// application so that the planets could be manipulated if other aspects are added to the project.

public class Planet {
	// Fields of Planet
	private double distance;
	private Color color;
	private double diameter;
	private String name;

	// Two options for constructors. The second is only used for unusual planets, such as gas giants, which I would like to appear at a different
	// size than the rest.
	public Planet (String name, double distance, Color color) {
		this.distance = distance;
		this.color = color;
		this.diameter = 0.5;
		this.name = name;
	}
	public Planet (String name, double distance, double diameter, Color color) {
		this.distance = distance;
		this.color = color;
		this.diameter = diameter;
		this.name = name;
	}

	// Getters
	public Color getColor() {
		return this.color;
	}

	public double getDistance() {
		return this.distance;
	}

	public double getDiameter() {
		return this.diameter;
	}

	public double getName() {
		return this.diameter;
	}

	// Manipulators 
	public void setColor(Color color) {
		this.color = color;
	}

	public void setDistance(double r) {
		this.distance = r;
	}

	public void setDiameter(double d) {
		this.diameter = d;
	}

	public void setName(String n) {
		this.name = n;
	}

	// Method for label line because in the child class DwarfPlanet, the line will be dotted, and otherwise I would have to copy more code over.
	public void labelLine (GridPane grid, GraphicsContext g, double start_x, double start_y, double length) {
		g.fillRect(start_x, start_y, 2, length);
	}

	// Method for adding planet to display
	public void addPlanet (GridPane grid, GraphicsContext g, int is_up) {
		// Calculating year:
		// Convert r to Astronomical Units, which makes calculations far easier
		double year_r = this.distance * 0.0000000066846;
		// Kepler's 3rd Law
		double year_squared = year_r*year_r*year_r;
		double year =  Math.sqrt(year_squared);
		// Rounding year to the nearest hundredth:
		year = Math.round(year*100.0) / 100.0;

		// Scale calcultions:
			// Length of canvas is 8192px.
			// If we leave 5px of buffer space on either side, the length is 8182px.
			// The largest distance from the far side of the sun to a planet is 6,001,400,000km.
			// (6,001,400,000km/8182px) = 733,488km/px.
			// On this scale, the sun's diameter must be 2px.
			// Planets will be 0.5 pixels wide except for Jupiter, which will be 1px wide. This won't be to scale, but the width is limited by pixels.

		// Graphics:
		// Determining distance from left side of canvas in px for this scale
		double translated_x = (this.distance / 733488) + 7;
		// Planet itself
		g.setFill(this.color);
		g.fillOval(translated_x, 323.5, this.diameter, this.diameter);
		// Label line and label
		// is_up = 1 means label is above planet, = 0 means it's below, = 2 means it's above and higher than = 1, = 3 means it's below and lower than = 0.
		if (is_up == 1) {
			labelLine(grid, g, translated_x - 0.5, 180, 140);
			g.setFont(Font.font("sans-serif", 30));
			g.fillText(this.name, translated_x + 13, 202);
			g.setFont(Font.font("sans-serif", 15));
			String label_text = "1 " + this.name + " year is equal to ";
			g.fillText(label_text, translated_x + 13, 230);
			String label_text2 = year + " Earth years.";
			g.fillText(label_text2, translated_x + 13, 250);
		} else if (is_up == 0) {
			labelLine(grid, g, translated_x - 1, 324.5, 140);
			g.setFont(Font.font("sans-serif", 30));
			g.fillText(this.name, translated_x + 13, 410);
			g.setFont(Font.font("sans-serif", 15));
			String label_text = "1 " + this.name + " year is equal to ";
			g.fillText(label_text, translated_x + 13, 438);
			String label_text2 = year + " Earth years.";
			g.fillText(label_text2, translated_x + 13, 458);
		} else if (is_up == 2) {
			labelLine(grid, g, translated_x, 180-150, 140+150);
			g.setFont(Font.font("sans-serif", 30));
			g.fillText(this.name, translated_x + 13, 202-150);
			g.setFont(Font.font("sans-serif", 15));
			String label_text = "1 " + this.name + " year is equal to ";
			g.fillText(label_text, translated_x + 13, 230-150);
			String label_text2 = year + " Earth years.";
			g.fillText(label_text2, translated_x + 13, 250-150);
		} else if (is_up == 3) {
			labelLine(grid, g, translated_x-1, 324.5, 140+155);
			g.setFont(Font.font("sans-serif", 30));
			g.fillText(this.name, translated_x + 13, 410+150);
			g.setFont(Font.font("sans-serif", 15));
			String label_text = "1 " + this.name + " year is equal to ";
			g.fillText(label_text, translated_x + 13, 438+150);
			String label_text2 = year + " Earth years.";
			g.fillText(label_text2, translated_x + 13, 458+150);
		}
	}
	public static void main (String[] args){
	}
}