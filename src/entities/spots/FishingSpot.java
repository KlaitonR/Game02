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
	public boolean right, left, up, down;
	
	private BufferedImage borderComplete;
	private BufferedImage borderDiagonalUpRight;
	private BufferedImage borderDiagonalUpLeft;
	private BufferedImage borderDiagonalDownRight;
	private BufferedImage borderDiagonalDownLeft;
	private BufferedImage borderUpRight;
	private BufferedImage borderUpLeft;
	private BufferedImage borderDownRight;
	private BufferedImage borderDownLeft;
	public BufferedImage borderUpDown;
	public BufferedImage borderRightLeft;
	private BufferedImage borderRight;
	private BufferedImage borderLeft;
	private BufferedImage borderUp;
	private BufferedImage borderDown;

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
		
		if(right && left && up && down)
			g.drawImage(borderComplete, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(right && left && !up && down)
			g.drawImage(borderDiagonalUpLeft, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(!right && left && up && down)
			g.drawImage(borderDiagonalDownLeft, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(right && !left && up && down)
			g.drawImage(borderDiagonalDownRight, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(right && left && up && !down)
			g.drawImage(borderDiagonalUpRight, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(!right && left && !up && down)
			g.drawImage(borderUpLeft, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(!right && left && up && !down)
			g.drawImage(borderDownLeft, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(right && !left && up && !down)
			g.drawImage(borderDownRight, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(right && !left && !up && down)
			g.drawImage(borderUpRight, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(right && left && !up && !down)
			g.drawImage(borderUpDown, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(!right && !left && up && down)
			g.drawImage(borderRightLeft, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(!right && !left && up && !down)
			g.drawImage(borderDown, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(!right && !left && !up && down)
			g.drawImage(borderUp, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(!right && left && !up && !down)
			g.drawImage(borderRight, this.getX() - Camera.x , this.getY() - Camera.y ,null);
		else if(right && !left && !up && !down)
			g.drawImage(borderLeft, this.getX() - Camera.x , this.getY() - Camera.y ,null);

	}

}
