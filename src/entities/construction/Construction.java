package entities.construction;

import java.awt.image.BufferedImage;
import entities.Entity;
import main.Game;

public class Construction extends Entity{
	
	public static BufferedImage MINE_EN = Game.spriteContruction.getSprite(0, 0, 32, 32);
	public static BufferedImage HOUSE_EN = Game.spriteContruction.getSprite(32, 0, 32, 32);
	public static BufferedImage STATUE_EN = Game.spriteContruction.getSprite(0, 32, 64, 64);
	
	public Construction (double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

}
