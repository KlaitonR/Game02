package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class WaterTile extends Tile{
	
	private int frames, maxFrames = 20, index, maxIndex = 4;
	private BufferedImage [] moveWater;

	public WaterTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		moveWater = new BufferedImage[5];
		
		for(int i = 0; i<5; i++) {
			moveWater[i] = Game.spritesheet.getSprite(240 + (i*16), 0, 16, 16);
		}
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
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
