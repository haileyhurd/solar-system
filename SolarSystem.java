
// Hailey Hurd

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*; 
import javafx.scene.canvas.*; // canvas for graphics
import javafx.geometry.*; 
import javafx.scene.control.*; // general controls
import javafx.scene.layout.*; // grid
import javafx.scene.text.*; // fonts and labels
import javafx.scene.paint.*; // colors
import javafx.scene.control.Slider; // distance slider
import javafx.scene.control.ScrollPane.*; // scrolling display for canvas

// Solar System
//
// This program displays our solar system to scale, other than some of the planets which are too small to be displayed otherwise. These planets
// are displayed at the very smallest diameter possible, but we are limited by pixel size and the length limit of the canvas.
// 
// Users can also create an imaginary planet, which the program will display along with how long a year would be on that planet (based
// on Kepler's 3rd Law of Planetary Motion).
//
// The planets are split up into two classes: Planet and DwarfPlanet. This allows for the two to be distinguished, in this case by a dotted label line 
// for dwarf planets and a solid line for planets.
//
// The stars are generated randomly and displayed on a seperate canvas than the solar system. When the solar system is scrolled by, the stars do not 
// move, creating an illusion of depth. 


public class SolarSystem extends Application {
	public void start(Stage the_stage) {
		the_stage.setTitle("Solar System");
		the_stage.setFullScreen(true);

		GridPane grid = new GridPane();

		// Modified from the HelloWorld on RomanNet. Set setGridLinesVisible() to true to help work on content positioning.
        grid.setVgap(15);
        grid.setHgap(30);
        //grid.setGridLinesVisible(true);
        //

        // Second page content:

        // Controls at bottom:

        // Add a planet / is it a dwarf planet?
        Text instructions = new Text ("Add a planet:");
        grid.add(instructions, 1, 2);
        instructions.getStyleClass().add("add_a_planet");

        CheckBox is_dwarf = new CheckBox("Dwarf Planet");
        grid.add(is_dwarf, 1, 3);
        is_dwarf.getStyleClass().add("label");

        // Divider
        Separator divider2 = new Separator();
		divider2.setOrientation(Orientation.VERTICAL);
		grid.add(divider2, 2, 2);
   		grid.setRowSpan(divider2, 2);

   		// Distance
        Text color_label = new Text ("  Distance from surface of Sun (1000s of km):  ");
        grid.add(color_label, 3, 2);
        color_label.getStyleClass().add("controls_label");

        Slider r_slider = new Slider(1, 6000000, 1);
        r_slider.setMajorTickUnit(100000);
        r_slider.setShowTickMarks(true);
        r_slider.setShowTickLabels(true);
        grid.add(r_slider, 3, 3);

        // Divider
        Separator divider3 = new Separator();
		divider3.setOrientation(Orientation.VERTICAL);
		grid.add(divider3, 5, 2);
   		grid.setRowSpan(divider3, 2);

   		// Name
   		Text name_label = new Text ("Name:");
        grid.add(name_label, 6, 2);
        name_label.getStyleClass().add("controls_label");

        TextField name = new TextField("Planet");
        grid.add(name, 6, 3);

        // Divider
   		Separator divider4 = new Separator();
		divider4.setOrientation(Orientation.VERTICAL);
		grid.add(divider4, 7, 2);
   		grid.setRowSpan(divider4, 2);

   		// Enter Button
   		Button enter = new Button(" Enter ");
        grid.add(enter, 8, 2);
        grid.setRowSpan(enter, 2);
        enter.getStyleClass().add("enter");

        // Main Divider
   		Separator divider1 = new Separator();
   		grid.add(divider1, 0, 1);
   		grid.setColumnSpan(divider1, GridPane.REMAINING);

   		// Create canvas for stars:
       	final Canvas c_stars = new Canvas(1280, 650);
        GraphicsContext g_stars = c_stars.getGraphicsContext2D();
        grid.add(c_stars, 0, 0);
        grid.setColumnSpan(c_stars, GridPane.REMAINING);

        // Add stars to star canvas:
		drawStars(grid, g_stars, 350);

        // Create canvas for rest of graphics:
        final Canvas canvas = new Canvas(8192, 650);
        GraphicsContext g = canvas.getGraphicsContext2D();

		// Place canvas on a ScrollPane
		ScrollPane scroll = new ScrollPane(canvas);
		grid.add(scroll, 0, 0);
		grid.setColumnSpan(scroll, GridPane.REMAINING);
		scroll.getStyleClass().add("scroll");
		scroll.setVbarPolicy(ScrollBarPolicy.NEVER);

		// Add sun to canvas:
		// Calculations for dimensions of sun are down below in the addPlanet method. The sun is 2px in diameter on this scale.
		g.setFill(Color.LEMONCHIFFON);
		g.fillOval(5, 324, 2, 2);

		// Label for sun:
		g.setFill(Color.LEMONCHIFFON);
		g.fillRect(5, 180-150, 2, 140+150);
		g.setFont(Font.font("sans-serif", 30));
		g.fillText("The Sun", 20, 202-150);
		g.setFont(Font.font("sans-serif", 15));
		g.fillText("Mass: about 2 x 10^30 kg", 20, 230-150);

		// Adding planets from our solar system using Planet and Dwarf Planet classes:
		// (Out of order because of drawing order overlap)
		Planet earth = new Planet("Earth", 149600000, Color.TEAL);
			earth.addPlanet(grid, g, 3);
		Planet mars = new Planet("Mars", 223000000, Color.BROWN);
			mars.addPlanet(grid, g, 2);
		Planet mercury = new Planet("Mercury", 58000000, Color.POWDERBLUE);
			mercury.addPlanet(grid, g, 0);
		Planet venus = new Planet("Venus", 108000000, Color.ORANGE);
		    venus.addPlanet(grid, g, 1);
		Planet jupiter = new Planet("Jupiter", 778500000, 1, Color.SALMON);
		    jupiter.addPlanet(grid, g, 1);
		Planet saturn = new Planet("Saturn", 1433000000, 1, Color.GOLD);
		    saturn.addPlanet(grid, g, 1);
		Planet uranus = new Planet("Uranus", 2871000000.0, 1, Color.LIGHTSKYBLUE);
		    uranus.addPlanet(grid, g, 1);
		Planet neptune = new Planet("Neptune", 4495000000.0, 1, Color.ROYALBLUE);
		    neptune.addPlanet(grid, g, 1);
		DwarfPlanet pluto = new DwarfPlanet("Pluto", 5870000000.0, Color.PLUM);
		    pluto.addPlanet(grid, g, 1);

		// Modified from https://code.makery.ch/blog/javafx-8-event-handling-examples/
        // When enter button is clicked:
		// Generic planets made so that a new planet is not defined under the same name each time the button is clicked:
        Planet newPlanet = new Planet("Planette", 12000, Color.LIGHTPINK);
        DwarfPlanet newDwarfPlanet = new DwarfPlanet("Planette", 12000, Color.LIGHTPINK);
        enter.setOnAction((event) -> {
        	// Get values from other controls
       		double r = r_slider.getValue();
       		r = r * 1000;
       		boolean dwarf = is_dwarf.isSelected();
        	String n = name.getText();
        	// Add planet to display
       		if(dwarf = true){
    			newDwarfPlanet.setDistance(r);
    			newDwarfPlanet.setName(n);
    			newDwarfPlanet.addPlanet(grid, g, 2);
    		} else {
    			newPlanet.setDistance(r);
    			newPlanet.setName(n);
    			newPlanet.addPlanet(grid, g, 2);
    		}
		});
    	//

        // Starting second page scene with css:
		Scene scene1 = new Scene(grid);
		scene1.getStylesheets().add("solar_system_css.css");
		grid.getStyleClass().add("first_page");

		the_stage.setScene(scene1);

		the_stage.show();

	}
	public static void main(String[] args) {
		launch(args);
	}

	// Method to randomly draw a certain number of stars
	public static void drawStars(GridPane grid, GraphicsContext g, int numStars) {
		int count = 0;
		while (count<=numStars) {
			double x = Math.random() * 1280;
			double y = Math.random() * 650;
			g.setFill(Color.rgb(255, 255, 255, 0.3));
			g.fillOval(x, y, 2, 2);
			count ++;
		}
	}

}