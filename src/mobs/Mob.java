package mobs;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import entities.Entity;

public class Mob extends Entity{

	public double life, maxLife; 
	public int exp;
	public int frames, maxFrames, index, maxIndex;
	public int maskTragetx, maskTargety, maskTargetw, maskTargeth;
	public boolean rigth, left, down, up;
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3, damageFrames;
	public int dir = rightDir;
	public boolean moved, isDamage;
	public int depth;
	
	boolean dirRight;
	boolean dirLeft;
	boolean dirUp;
	boolean dirDown;
	
	public double speed;
	
	boolean ps = true;
	
	public BufferedImage [] rigthBuf;
	public BufferedImage [] leftBuf;
	public BufferedImage [] upBuf;
	public BufferedImage [] downBuf; 
	
	public BufferedImage [] rightDamage;
	public BufferedImage [] leftDamage;
	public BufferedImage [] upDamage;
	public BufferedImage [] downDamage;

	public Mob(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rigthBuf = new BufferedImage[4];
		leftBuf = new BufferedImage[4];
		upBuf = new BufferedImage[4];
		downBuf = new BufferedImage[4];
		
		rightDamage = new BufferedImage[4];
		leftDamage = new BufferedImage[4];
		upDamage = new BufferedImage[4];
		downDamage = new BufferedImage[4];
		
	}
	
	public static boolean isColiddingTarget(Mob e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskTragetx, e1.getY() + e1.maskTargety, e1.maskTargetw, e1.maskTargeth);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY()+ e2.masky, e2.mwidth, e2.mheigth);
		
		return e1Mask.intersects(e2Mask);
	}
	
}
