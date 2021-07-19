package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.Tile;
import world.WallTile;
import world.World;

public class BulletShoot extends Entity {
	
	private double dx;
	private double dy;
	private double speed = 4;
	private int life = 60, curLife = 0;
	static public boolean collidingWall;
	static public boolean collidingEnemy;
	static public boolean collidingBullet;
	
	public BulletShoot(double x, double y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void collidingTile() {
		
		for(int i=0; i < World.tiles.length; i++) {
			Tile t =  World.tiles[i];
			if(t instanceof WallTile) {
				if(Entity.isColiddingTile(this, t)) {
					BulletShoot.collidingBullet = false;
					collidingWall = true;
					collidingEnemy = false;
					World.generateParticles(50, (int)x, (int)y, World.tiles[i].psTiles);
					Game.bulletShootes.remove(this);
					collidingWall = true;
					collidingEnemy = false;
					return;	
				}
			}
		}
	}
	
	public void checkCollisionTree(){
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Tree) {
				if(Entity.isColidding(this, atual)) {
					atual.depth = depthBullet(atual.getY());
				}
			}
		}
	}
	
	public int depthBullet(int yAtual) {
		
		if(y > yAtual) { 
			depth = 2;
			return  1;
		
		}else {
			depth = 1;
			return 2;
		}
	}

	public void tick() {
		
		x += dx * speed;
		y += dy * speed;
		curLife++;
		if(curLife == life) {
			BulletShoot.collidingBullet = false;
			Game.bulletShootes.remove(this);
			return;	
		}
		
		collidingTile();
		checkCollisionTree();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
	}

}
