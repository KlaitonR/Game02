package entities.construction;

import java.awt.image.BufferedImage;
import entities.Entity;
import main.Game;

public class Construction extends Entity {
	
	public static BufferedImage MINE_EN = Game.spriteContruction.getSprite(0, 0, 32, 32);
	public static BufferedImage HOUSE_EN = Game.spriteContruction.getSprite(32, 0, 32, 32);
	public static BufferedImage STATUE_EN = Game.spriteContruction.getSprite(0, 32, 64, 64);
	public static BufferedImage THORN_EN = Game.spriteContruction.getSprite(240, 0, 16, 16);
	public static BufferedImage THORN_LOCKED_EN = Game.spriteContruction.getSprite(256, 0, 16, 16);
	public static BufferedImage MINE_LAND_EN = Game.spriteContruction.getSprite(272, 0, 16, 16);
	public static BufferedImage MINE_LAND_LOCKED_EN = Game.spriteContruction.getSprite(288, 0, 16, 16);
	public static BufferedImage MINE_LAND_PLANTED_EN = Game.spriteContruction.getSprite(64, 32, 16, 16);
	
	
	public static BufferedImage DRAWER_EN = Game.spriteContruction.getSprite(96, 0, 16, 16);
	public static BufferedImage CABINET_EN = Game.spriteContruction.getSprite(112, 0, 16, 32);
	public static BufferedImage CABINET_02_EN = Game.spriteContruction.getSprite(64, 0, 16, 32);
	public static BufferedImage BEDSIDE_LAMP_EN = Game.spriteContruction.getSprite(96, 16, 16, 16);
	public static BufferedImage CHAIR_EN = Game.spriteContruction.getSprite(80, 16, 16, 16);
	public static BufferedImage TABLE_EN = Game.spriteContruction.getSprite(128, 0, 32, 16);
	public static BufferedImage BED_EN = Game.spriteContruction.getSprite(128, 16, 32, 16);
	public static BufferedImage WATCH_EN = Game.spriteContruction.getSprite(80, 0, 16, 16);
	public static BufferedImage FLOWER_VASE_EN = Game.spriteContruction.getSprite(160, 0, 16, 16);
	public static BufferedImage MAT_EN = Game.spriteContruction.getSprite(160, 16, 16, 16);
	
	public int masckConstructionX;
	public int masckConstructionY;
	public int masckConstructionW;
	public int masckConstructionH;
	
	public Construction (double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
	}

}
