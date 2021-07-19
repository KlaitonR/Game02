package mobs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.Beef;
import entities.BulletShoot;
import entities.Entity;
import entities.PigBeef;
import main.Game;
import main.Sound;
import util.Id;
import world.Camera;
import world.EntitySolid;
import world.World;

public class Pig extends Mob {
	
	public Pig(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		maxFrames = 20; maxIndex = 3;
		maskTragetx = -73; maskTargety = -68; maskTargetw = 150; maskTargeth = 150;
		speed = Game.rand.nextDouble();
		ps = true;
		life = 5; maxLife = 5; exp = 100;
		
		if(Game.rand.nextInt(100)>50) {
			dirRight = true;
		}else {
			dirLeft = true;
		}
		
		if(Game.rand.nextInt(100)>50) {
			dirUp = true;
		}else {
			dirDown = true;
		} 
		
		for(int i = 0; i<4; i++) {
			rigthBuf[i] = Game.spriteMobs.getSprite(0 + (i*16), 16, 16, 16);
			leftBuf[i] = Game.spriteMobs.getSprite(0 + (i*16), 0, 16, 16);
			upBuf[i] = Game.spriteMobs.getSprite(0 + (i*16), 48, 16, 16);
			downBuf[i] = Game.spriteMobs.getSprite(0 + (i*16), 32, 16, 16);
			
			//Arrumar
			rightDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 64, 16, 16);
			leftDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 80, 16, 16);
			upDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 96, 16, 16);
			downDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 112, 16, 16);
		}
	}
	
	public void tick() {
		
		moved = false;
		
		if(speed<0.3) 
			speed +=0.15;
		else if(speed>0.5)
			speed -= 0.45;

		// MOVIMENTAÇÃO ALEATÓRIA DO INIMIGO
		
				if(ps) { // Não anda na diagonal
				
					if(dirRight == true && World.isFree((int)(x+speed), this.getY(), this.z) 
							&& !isColiddingMob((int)(x+speed), this.getY())
							&& EntitySolid.solidTree((int)(x+speed), this.getY())) {
						moved = true;
						x += speed;
						dir = rightDir;
						
					}else if(dirLeft == true && World.isFree((int)(x-speed), this.getY(), this.z) 
							&& !isColiddingMob((int)(x-speed), this.getY())
							&& EntitySolid.solidTree((int)(x-speed), this.getY())){
						moved =  true;
						x -= speed;
						dir = leftDir;
					}
					
					if(dirDown == true && World.isFree(this.getX(), (int)(y+speed), this.z) 
							&& !isColiddingMob(this.getX(), (int)(y+speed))
							&& EntitySolid.solidTree(this.getX(), (int)(y+speed))) {
						moved = true;
						y += speed;
						dir = downDir;
					}else if (dirUp == true && World.isFree(this.getX(), (int)(y-speed), this.z) 
							&& !isColiddingMob(this.getX(), (int)(y-speed))
							&& EntitySolid.solidTree(this.getX(), (int)(y-speed))) {
						moved = true;
						y -= speed;
						dir = upDir;
					}

				}else { //anda na diagonal
					
					if(dirRight == true && World.isFree((int)(x+speed), this.getY(), this.z) 
							&& !isColiddingMob((int)(x+speed), this.getY())
							&& EntitySolid.solidTree((int)(x+speed), this.getY())) {
						moved = true;
						x += speed;
						dir = rightDir;
						
					}else if(dirLeft == true && World.isFree((int)(x-speed), this.getY(), this.z) 
							&& !isColiddingMob((int)(x-speed), this.getY())
							&& EntitySolid.solidTree((int)(x-speed), this.getY())){
						moved =  true;
						x -= speed;
						dir = leftDir;
					} else if(dirDown == true && World.isFree(this.getX(), (int)(y+speed), this.z) 
							&& !isColiddingMob(this.getX(), (int)(y+speed))
							&& EntitySolid.solidTree(this.getX(), (int)(y+speed))) {
						moved = true;
						y += speed;
						dir = downDir;
					}else if (dirUp == true && World.isFree(this.getX(), (int)(y-speed), this.z) 
							&& !isColiddingMob(this.getX(), (int)(y-speed))
							&& EntitySolid.solidTree(this.getX(), (int)(y-speed))) {
						moved = true;
						y -= speed;
						dir = upDir;
					}
				}
				
				//Mudar de direção ao colidir
				
				//Para esquerda e para direitra
				if(dirRight == true && !World.isFree((int)(x+speed), this.getY(), this.z) ||
										isColiddingMob((int)(x+speed), this.getY()) ||
										isColiddingEntity((int)(x+speed), this.getY()) ||
										isColiddingEnemy((int)(x+speed), this.getY())) {
					dirRight = false;
					dirLeft = true;
					ps = false;
				}
				
				if(dirLeft == true && !World.isFree((int)(x-speed), this.getY(), this.z) ||
									isColiddingMob((int)(x-speed), this.getY()) ||
									isColiddingEntity((int)(x-speed), this.getY()) ||
									isColiddingEnemy((int)(x-speed), this.getY())) {
					dirRight = true;
					dirLeft = false;
					ps = true;
				}
				
				//Para baixo e para cima
				
				if(dirDown == true && !World.isFree(this.getX(), (int)(y+speed), this.z) ||
									isColiddingMob(this.getX(), (int)(y+speed)) ||
									isColiddingEntity(this.getX(), (int)(y+speed)) ||
									isColiddingEnemy(this.getX(), (int)(y+speed))) {
					dirDown = false;
					dirUp = true;
				}
				
				if(dirUp == true && !World.isFree(this.getX(), (int)(y-speed), this.z) ||
									isColiddingMob(this.getX(), (int)(y-speed)) ||
									isColiddingEntity(this.getX(), (int)(y-speed)) ||
									isColiddingEnemy(this.getX(), (int)(y-speed))) {
					dirDown = true;
					dirUp = false;
				}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}else{
			index = 0;
			frames = 0;
		}
		
		if(life <= 0)
			destroySelf();
		
		if(isDamage) {
		
			damageFrames++;
			if(this.damageFrames == 100) {
				damageFrames = 0; 
				isDamage = false;
			}
		}
		
		collidingBullet();
		
		if(isTargetPlayer()) {
			Sound.Clips.pigGrunts.loop();
		}else {
			Sound.Clips.pigGrunts.stop();
		}
		
	}
	
	public boolean isTargetPlayer() {
			
		Mob m = null;
		
		for(int i=0; i<Game.mobs.size();i++) {
			m = Game.mobs.get(i);
			if(Mob.isColiddingTarget(m, Game.player)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void collidingBullet() {
			
		isDamage = false;
		
		for(int i=0; i < Game.bulletShootes.size(); i++) {
			if(Entity.isColidding(this, Game.bulletShootes.get(i))) {
				BulletShoot.collidingBullet =  true;
				BulletShoot.collidingWall = false;
				BulletShoot.collidingEnemy = true;
				World.generateParticles(50, (int)x, (int)y, this.psTiles);
				Game.bulletShootes.remove(i);
				life--;
				isDamage = true;
				return;
			}
		}
	}
	
	public void destroySelf() {
		if(life <= 0) {
			Beef pigBeef = new PigBeef((int)x + 3, (int)y -7, 16, 16, Entity.PIG_BEEF_EN);
			pigBeef.tipo = "carne de porco";
			pigBeef.id = Id.ID_PIG_BEEF;
			pigBeef.clear = true;
			Game.mobs.remove(this);
			Game.entities.add(pigBeef);
			Sound.Clips.pigDeath.play();
			Sound.Clips.pigGrunts.stop();
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskTragetx, this.getY() - Camera.y + maskTargety, maskTargetw, maskTargeth);
		
		if(isDamage) {
			
			if(dir == rightDir)
				g.drawImage(rightDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}else {
		
			if(dir == rightDir)
				g.drawImage(rigthBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}		
		
		g.setColor(Color.black); 
		g.fillRect((int)x + 2 - Camera.x, (int)y - 5 - Camera.y, 12, 3);
		g.setColor(Color.red);
		g.fillRect((int)x + 3 - Camera.x, (int)y - 4 - Camera.y, 10, 1);
		g.setColor(Color.green);
		g.fillRect((int)x + 3 - Camera.x, (int)y - 4 - Camera.y, (int)((life/maxLife)*10), 1);
		
	}

}
