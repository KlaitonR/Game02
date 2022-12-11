 package entities.mobs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.BulletShoot;
import entities.Entity;
import entities.itens.Beef;
import entities.itens.PigBeef;
import main.Game;
import main.Sound;
import util.Id;
import util.Mapa;
import util.Regiao;
import world.Camera;
import world.EntitySolid;
import world.World;

public class Pig extends Mob {
	
	public Pig(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
		maxFrames = 20; maxIndex = 3;
		maskTragetx = -73; maskTargety = -68; maskTargetw = 150; maskTargeth = 150;
		
		speed = Game.rand.nextDouble();
		ps = true;
		life = 5; maxLife = 5; exp = 200;
		
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
			
			rightDamage[i] = Game.spriteMobs.getSprite(64 + (i*16), 16, 16, 16);
			leftDamage[i] = Game.spriteMobs.getSprite(64 + (i*16), 0, 16, 16);
			upDamage[i] = Game.spriteMobs.getSprite(64 + (i*16), 48, 16, 16);
			downDamage[i] = Game.spriteMobs.getSprite(64 + (i*16), 32, 16, 16);
		}
	}
	
	public void tick() {
		
		super.tick();
		
		moved = false;
		
		if(speed<0.1) 
			speed +=0.15;
		else if(speed>0.4)
			speed -= 0.35;
	
		// MOVIMENTAÇÃO ALEATÓRIA DO INIMIGO
		
				if(ps) { // Não anda na diagonal
				
					if(dirRight == true && Game.world.isFree((int)(x+speed), this.getY(), this.z) 
							&& EntitySolid.solidCollision((int)(x+speed), this.getY())) {
						moved = true;
						x += speed;
						dir = rightDir;
						
					}else if(dirLeft == true && Game.world.isFree((int)(x-speed), this.getY(), this.z) 
							&& EntitySolid.solidCollision((int)(x-speed), this.getY())){
						moved =  true;
						x -= speed;
						dir = leftDir;
					}
					
					if(dirDown == true && Game.world.isFree(this.getX(), (int)(y+speed), this.z) 
							&& EntitySolid.solidCollision(this.getX(), (int)(y+speed))) {
						moved = true;
						y += speed;
						dir = downDir;
					}else if (dirUp == true && Game.world.isFree(this.getX(), (int)(y-speed), this.z) 
							&& EntitySolid.solidCollision(this.getX(), (int)(y-speed))) {
						moved = true;
						y -= speed;
						dir = upDir;
					}
	
				}else { //anda na diagonal
					
					if(dirRight == true && Game.world.isFree((int)(x+speed), this.getY(), this.z) 
							&& EntitySolid.solidCollision((int)(x+speed), this.getY())) {
						moved = true;
						x += speed;
						dir = rightDir;
						
					}else if(dirLeft == true && Game.world.isFree((int)(x-speed), this.getY(), this.z) 
							&& EntitySolid.solidCollision((int)(x-speed), this.getY())){
						moved =  true;
						x -= speed;
						dir = leftDir;
					} else if(dirDown == true && Game.world.isFree(this.getX(), (int)(y+speed), this.z) 
							&& EntitySolid.solidCollision(this.getX(), (int)(y+speed))) {
						moved = true;
						y += speed;
						dir = downDir;
					}else if (dirUp == true && Game.world.isFree(this.getX(), (int)(y-speed), this.z) 
							&& EntitySolid.solidCollision(this.getX(), (int)(y-speed))) {
						moved = true;
						y -= speed;
						dir = upDir;
					}
				}
				
				//Mudar de direção ao colidir
				
				//Para esquerda e para direitra
				if(dirRight == true && !Game.world.isFree((int)(x+speed), this.getY(), this.z) ||
						!EntitySolid.solidCollision((int)(x+speed), this.getY())) {
					dirRight = false;
					dirLeft = true;
					ps = false;
				}
				
				if(dirLeft == true && !Game.world.isFree((int)(x-speed), this.getY(), this.z) ||
						!EntitySolid.solidCollision((int)(x-speed), this.getY())) {
					dirRight = true;
					dirLeft = false;
					ps = true;
				}
				
				//Para baixo e para cima
				
				if(dirDown == true && !Game.world.isFree(this.getX(), (int)(y+speed), this.z) ||
						!EntitySolid.solidCollision(this.getX(), (int)(y+speed))) {
					dirDown = false;
					dirUp = true;
				}
				
				if(dirUp == true && !Game.world.isFree(this.getX(), (int)(y-speed), this.z) ||
						!EntitySolid.solidCollision(this.getX(), (int)(y-speed))) {
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
			if(this.damageFrames == 200) {
				damageFrames = 0; 
				isDamage = false;
			}
		}
		
		collidingBullet();

	}
	
	public void collidingBullet() {
			
		isDamage = false;
		
		for(int i=0; i < Game.bulletShootes.size(); i++) {
			if(Entity.isColidding(this, Game.bulletShootes.get(i))) {
				BulletShoot.collidingBullet =  true;
				BulletShoot.collidingWall = false;
				BulletShoot.collidingEnemy = true;
				World.generateParticles(25, (int)x, (int)y, this.psTiles);
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
			if (Game.player.expAttackTtl < Game.player.expMaxPossivel) {
				Game.player.expAttackTtl += exp;
				Game.player.expAttack += exp;
			}
			Game.mobs.remove(this);
			Game.entities.remove(this);
			Game.entities.add(pigBeef);
			Sound.Clips.pigDeath.play();
			Sound.Clips.pigGrunts.stop();
		}
	}
	
	public void render(Graphics g) {

//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheigth);
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskTragetx, this.getY() - Camera.y + maskTargety, maskTargetw, maskTargeth);
		
		if(isDamage) {
			
			if(dir == rightDir) {
				g.drawImage(rightDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 15; mheigth = 9; maskx = 1; masky = 7;
			}else if (dir == leftDir) {
				g.drawImage(leftDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 15; mheigth = 9; maskx = 1; masky = 7;
			}else if(dir == upDir) {
				g.drawImage(upDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 9; mheigth = 12; maskx = 3; masky = 4;
			}else if(dir == downDir) {
				g.drawImage(downDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 9; mheigth = 12; maskx = 3; masky = 4;
			}
		}else {
		
			if(dir == rightDir) {
				g.drawImage(rigthBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 15; mheigth = 9; maskx = 1; masky = 7;
			}else if (dir == leftDir) {
				g.drawImage(leftBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 15; mheigth = 9; maskx = 1; masky = 7;
			}else if(dir == upDir) {
				g.drawImage(upBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 9; mheigth = 12; maskx = 3; masky = 4;
			}else if(dir == downDir) {
				g.drawImage(downBuf[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				mwidth = 9; mheigth = 12; maskx = 3; masky = 4;
			}
		}		
	}

}
