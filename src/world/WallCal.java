package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import util.Mapa;
import util.Regiao;

public class WallCal extends WallTile{
	
	public BufferedImage borderUpRight;
	public BufferedImage borderUpLeft;
	public BufferedImage borderDownLeft;
	public BufferedImage borderDownRight;

	public WallCal(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		mapa.add(Mapa.MAPA_CALABOUÇO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
		
		borderUpLeft = Game.spritesheet.getSprite(256, 128, 16, 16);
		borderUpRight = Game.spritesheet.getSprite(272, 128, 16, 16);
		borderDownLeft = Game.spritesheet.getSprite(288, 128, 16, 16);
		borderDownRight = Game.spritesheet.getSprite(304, 128, 16, 16);
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		if(right && left && up && down)
			g.drawImage(borderDownLeft, x - Camera.x ,y - Camera.y ,null);
		else if(right && left && !up && down)
			g.drawImage(borderDownLeft, x - Camera.x ,y - Camera.y ,null);
		else if(!right && left && up && down)
			g.drawImage(borderUpLeft, x - Camera.x ,y - Camera.y ,null);
		else if(right && !left && up && down)
			g.drawImage(borderDownRight, x - Camera.x ,y - Camera.y ,null);
		else if(right && left && up && !down)
			g.drawImage(borderUpLeft, x - Camera.x ,y - Camera.y ,null);
		else if(!right && left && !up && down)
			g.drawImage(borderUpLeft, x - Camera.x ,y - Camera.y ,null);
		else if(!right && left && up && !down)
			g.drawImage(borderDownLeft, x - Camera.x ,y - Camera.y ,null);
		else if(right && !left && up && !down)
			g.drawImage(borderDownRight, x - Camera.x ,y - Camera.y ,null);
		else if(right && !left && !up && down)
			g.drawImage(borderUpRight, x - Camera.x ,y - Camera.y ,null);
	
	}

}
