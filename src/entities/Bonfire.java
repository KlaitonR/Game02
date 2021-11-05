package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import util.Id;
import world.Camera;

public class Bonfire extends Entity{
	
	private BufferedImage animation[];
	private int frames, maxFrames = 10, index, maxIndex = 10;
	
	public Bonfire(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		id = Id.ID_BONFIRE;
		animation = new BufferedImage[11];
		
		for(int i = 0; i<11; i++) {
			animation[i] = Game.spritesheet.getSprite(48 + (i*16), 272, 16, 16);
		}
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
