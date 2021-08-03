package entities.spots;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entities.Entity;
import main.Game;
import util.Mapa;
import util.Regiao;
import world.Camera;

public class FishingSpot extends Entity{
	
	public static int fishingTime, maxFishingfTime = Game.rand.nextInt(600) + 400;
	private int frames, maxFrames = 20, index, maxIndex = 4;
	private BufferedImage [] moveFishing;
	private BufferedImage [] moveFishingSwamp;
	public boolean swamp;

	public FishingSpot(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		maskx = -1;
		masky = -2;
		mwidth = 18;
		mheigth = 20;
		
		maskx2 = -22;
		masky2 = -22;
		mwidth2 = 60;
		mheigth2 = 60;
		
		moveFishing = new BufferedImage[5];
		moveFishingSwamp = new BufferedImage[5];
		
		for(int i = 0; i<5; i++) {
			moveFishing[i] = Game.spritesheet.getSprite(240 + (i*16), 16, 16, 16);
			moveFishingSwamp[i] = Game.spritesheet.getSprite(240 + (i*16), 48, 16, 16);
		}
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}
	
	public static boolean isColidding(Entity e1, Entity player) {
	
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx2, e1.getY() + e1.masky2, e1.mwidth2, e1.mheigth2);
		Rectangle e2Mask = new Rectangle(player.getX() + player.maskx, player.getY() + player.masky, player.mwidth, player.mheigth);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx2, this.getY() - Camera.y + masky2, mwidth2, mheigth2);
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
		
		if(swamp) {
			g.drawImage(moveFishingSwamp[index] ,this.getX() - Camera.x, this.getY() - Camera.y , null);
			if(moveFishing[0] != null) {
				moveFishing[0] = null;
				moveFishing[1] = null;
				moveFishing[2] = null;
				moveFishing[3] = null;
				moveFishing[4] = null;
			}
		}else {
			g.drawImage(moveFishing[index] ,this.getX() - Camera.x, this.getY() - Camera.y , null);
		}

	}

}
