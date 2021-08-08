package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import util.Mapa;
import util.Regiao;

public class EarthTile extends Tile{

	public BufferedImage borderComplete;
	public BufferedImage borderDiagonalUpRight;
	public BufferedImage borderDiagonalUpLeft;
	public BufferedImage borderDiagonalDownRight;
	public BufferedImage borderDiagonalDownLeft;
	public BufferedImage borderUpRight;
	public BufferedImage borderUpLeft;
	public BufferedImage borderUpDown;
	public BufferedImage borderRightLeft;
	public BufferedImage borderDownRight;
	public BufferedImage borderDownLeft;
	public BufferedImage borderRight;
	public BufferedImage borderLeft;
	public BufferedImage borderUp;
	public BufferedImage borderDown;
	
	public EarthTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		mapa.addAll(Mapa.addAll());
		regiao.addAll(Regiao.addAll());
		
		borderUpLeft = Game.spritesheet.getSprite(256, 64, 16, 16);
		borderUpRight = Game.spritesheet.getSprite(272, 64, 16, 16);
		borderDownLeft = Game.spritesheet.getSprite(288, 64, 16, 16);
		borderDownRight = Game.spritesheet.getSprite(304, 64, 16, 16);
		
		borderLeft = Game.spritesheet.getSprite(272, 80, 16, 16);
		borderRight = Game.spritesheet.getSprite(256, 80, 16, 16);
		borderUp = Game.spritesheet.getSprite(288, 80, 16, 16);
		borderDown = Game.spritesheet.getSprite(304, 80, 16, 16);
		
		borderDiagonalUpLeft = Game.spritesheet.getSprite(256, 96, 16, 16);
		borderDiagonalUpRight = Game.spritesheet.getSprite(272, 96, 16, 16);
		borderDiagonalDownLeft = Game.spritesheet.getSprite(288, 96, 16, 16);
		borderDiagonalDownRight = Game.spritesheet.getSprite(304, 96, 16, 16);
		
		borderUpDown = Game.spritesheet.getSprite(272, 112, 16, 16);
		borderRightLeft = Game.spritesheet.getSprite(288, 112, 16, 16);
		
		borderComplete = Game.spritesheet.getSprite(304, 112, 16, 16);
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		if(right && left && up && down)
			g.drawImage(borderComplete, x - Camera.x ,y - Camera.y ,null);
		else if(right && left && !up && down)
			g.drawImage(borderDiagonalUpLeft, x - Camera.x ,y - Camera.y ,null);
		else if(!right && left && up && down)
			g.drawImage(borderDiagonalDownLeft, x - Camera.x ,y - Camera.y ,null);
		else if(right && !left && up && down)
			g.drawImage(borderDiagonalDownRight, x - Camera.x ,y - Camera.y ,null);
		else if(right && left && up && !down)
			g.drawImage(borderDiagonalUpRight, x - Camera.x ,y - Camera.y ,null);
		else if(!right && left && !up && down)
			g.drawImage(borderUpLeft, x - Camera.x ,y - Camera.y ,null);
		else if(!right && left && up && !down)
			g.drawImage(borderDownLeft, x - Camera.x ,y - Camera.y ,null);
		else if(right && !left && up && !down)
			g.drawImage(borderDownRight, x - Camera.x ,y - Camera.y ,null);
		else if(right && !left && !up && down)
			g.drawImage(borderUpRight, x - Camera.x ,y - Camera.y ,null);
		else if(right && left && !up && !down)
			g.drawImage(borderUpDown, x - Camera.x ,y - Camera.y ,null);
		else if(!right && !left && up && down)
			g.drawImage(borderRightLeft, x - Camera.x ,y - Camera.y ,null);
		else if(!right && !left && up && !down)
			g.drawImage(borderDown, x - Camera.x ,y - Camera.y ,null);
		else if(!right && !left && !up && down)
			g.drawImage(borderUp, x - Camera.x ,y - Camera.y ,null);
		else if(!right && left && !up && !down)
			g.drawImage(borderRight, x - Camera.x ,y - Camera.y ,null);
		else if(right && !left && !up && !down)
			g.drawImage(borderLeft, x - Camera.x ,y - Camera.y ,null);
		
	}
	
}
