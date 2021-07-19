package entities;

import java.awt.image.BufferedImage;

import main.Game;

public class Stump extends Entity{

	public Stump(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void destroySelf() {
		
		Root root = new Root(x+5, y-7, 16, 16, Entity.RAIZ_EN);
		root.show = true;
		root.tipo = "raiz";
		root.clear = true;
		Game.entities.add(root);
		
		Game.entities.remove(this);
		
	}


}
