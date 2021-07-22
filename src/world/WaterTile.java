package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import util.Mapa;
import util.Regiao;

public class WaterTile extends Tile{
	
	private int frames, maxFrames = 20, index, maxIndex = 4;
	private BufferedImage [] moveWater;

	public WaterTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		maskx = -22;
		masky = -22;
		mwidth = 60;
		mheigth = 60;
		
		mapa.addAll(Mapa.addAll());
		regiao.addAll(Regiao.addAll());
		
		moveWater = new BufferedImage[5];
		
		for(int i = 0; i<5; i++) {
			moveWater[i] = Game.spritesheet.getSprite(240 + (i*16), 0, 16, 16);
		}
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
		
		g.drawImage(moveWater[index], x - Camera.x ,y - Camera.y ,null);
		
	}
	
}
