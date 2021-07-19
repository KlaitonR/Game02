package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Game;
import world.Camera;

public class Particle extends Entity{

	public int lifeTime = 5;
	public int curLife = 0;
	public int spd = 2;
	public double dx = 0;
	public double dy = 0;
	
	public Particle(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	
	}
	
	public void tick() {
		
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(lifeTime == curLife) {
			Game.particles.remove(this);
		}
	}
	
	static public Color corParticle() {
		
		Color cor = new Color(0xFFFFFF);
		
		if(BulletShoot.collidingWall) 
			cor = new Color(0xFFFF00);
		else if (BulletShoot.collidingEnemy) 
			cor = new Color(0xFF0000);
		
		return cor;	
	}
	
	public void render(Graphics g) {
		g.setColor(corParticle());
		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, mwidth, height);
	}

}
