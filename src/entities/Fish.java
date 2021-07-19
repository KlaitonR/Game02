package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Id;
import world.Camera;

public class Fish extends Entity {

	public Fish(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		mwidth = 14;
		mheigth = 12;
		maskx = + 2;
		masky = + 2;
		
		pack = true;
		qtPack = 63;
		id = Id.ID_FISH;

	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.black);
		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheigth);
		super.render(g);
		
	}
		

}
