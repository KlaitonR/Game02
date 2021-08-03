package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import util.Mapa;
import util.Regiao;

public class WaterTile extends Tile{
	
	private int frames, maxFrames = 20, index, maxIndex = 4;
	private BufferedImage [] moveWater;
	private BufferedImage [] moveWaterSwamp;
	public boolean swamp;

	public WaterTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		maskx = -22;
		masky = -22;
		mwidth = 60;
		mheigth = 60;
		
		mapa.addAll(Mapa.addAll());
		regiao.addAll(Regiao.addAll());
		
		moveWater = new BufferedImage[5];
		moveWaterSwamp = new BufferedImage[5];
		
		for(int i = 0; i<5; i++) {
			moveWater[i] = Game.spritesheet.getSprite(240 + (i*16), 0, 16, 16);
			moveWaterSwamp[i] = Game.spritesheet.getSprite(240 + (i*16), 32, 16, 16);
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
		
		if(swamp) {
			g.drawImage(moveWaterSwamp[index], x - Camera.x ,y - Camera.y ,null);
			if(moveWater[0] != null) {
				moveWater[0] = null;
				moveWater[1] = null;
				moveWater[2] = null;
				moveWater[3] = null;
				moveWater[4] = null;
			}
		}else {
			g.drawImage(moveWater[index], x - Camera.x ,y - Camera.y ,null);
		}
		
	}
	
}
