package entities;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;
import main.Sound;
import util.Mapa;
import util.Regiao;
import world.Camera;
import world.World;
//import java.util.Random;
//import world.AStar;
//import world.Vector2i;
//import main.Sound;
//import java.awt.Color;

	public class Enemy extends Entity{
	
		private double speed = Game.rand.nextDouble();
		
		private int maskTragetx = -40, maskTargety = -40, maskTargetw = 80, maskTargeth = 80;
		
		private BufferedImage [] rightEnemy;
		private BufferedImage [] leftEnemy;
		private BufferedImage [] upEnemy;
		private BufferedImage [] downEnemy; 
		private BufferedImage [] rightEnemyDamage;
		private BufferedImage [] leftEnemyDamage;
		private BufferedImage [] upEnemyDamage;
		private BufferedImage [] downEnemyDamage;
		
		public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
		public int dir = rightDir;
		private boolean moved = false;
		private int frames = 0, maxFrames = 20, index = 0, maxIndex = 3;
		
		public double life = 10, maxLife = 10;
		boolean isDamage;
		private int damageFrames;
		public boolean target = false;
		
		private double exp;
		
		boolean dirRight = true;
		boolean dirLeft = false;
		boolean dirUp = false;
		boolean dirDown = true;
		
		boolean ps = true;

	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		maskx = 4; masky = 0; mwidth = 9; mheigth = 16;
		
		rightEnemy = new BufferedImage[4];
		leftEnemy = new BufferedImage[4];
		upEnemy = new BufferedImage[4];
		downEnemy = new BufferedImage[4];
		
		rightEnemyDamage = new BufferedImage[4];
		leftEnemyDamage = new BufferedImage[4];
		upEnemyDamage = new BufferedImage[4];
		downEnemyDamage = new BufferedImage[4];
		
		for(int i = 0; i<4; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 0, 16, 16);
			leftEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 16, 16, 16);
			upEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 32, 16, 16);
			downEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 48, 16, 16);
			
			rightEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 64, 16, 16);
			leftEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 80, 16, 16);
			upEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 96, 16, 16);
			downEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 112, 16, 16);
			
		}
		
		depth = 0;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
	
	public boolean isColiddingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheigth);
		Rectangle player = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY()+ Game.player.masky, Game.player.mwidth, Game.player.mheigth);

		return enemyCurrent.intersects(player);
	}
	
	public boolean isTargetPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskTragetx, this.getY() + maskTargety, maskTargetw, maskTargeth);
		Rectangle player = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY()+ Game.player.masky, Game.player.mwidth, Game.player.mheigth);
		
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		
		moved = false;
		
		if(speed<0.3) 
			speed +=0.25;
		else if(speed>0.8)
			speed -= 0.25;
		
//		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 200) { // se a distancia for menor que 200, persegue o player
//			
//		}
		
		// MOVIMENTAÇÃO ALEATÓRIA DO INIMIGO
		if(!isColiddingWithPlayer()) {
			
			if(!isTargetPlayer() && !target) { //se o player não entra na área do target, ou nçao causar dano (ANDA ALEATORIAMENTE)
				
				if(ps) { // Não anda na diagonal
				
					if(dirRight == true && Game.world.isFree((int)(x+speed), this.getY(), this.z) && !isColiddingEnemy((int)(x+speed), this.getY())) {
						moved = true;
						x += speed;
						dir = rightDir;
						
					}else if(dirLeft == true && Game.world.isFree((int)(x-speed), this.getY(), this.z) && !isColiddingEnemy((int)(x-speed), this.getY())){
						moved =  true;
						x -= speed;
						dir = leftDir;
					}
					
					if(dirDown == true && Game.world.isFree(this.getX(), (int)(y+speed), this.z) && !isColiddingEnemy(this.getX(), (int)(y+speed))) {
						moved = true;
						y += speed;
						dir = downDir;
					}else if (dirUp == true && Game.world.isFree(this.getX(), (int)(y-speed), this.z) && !isColiddingEnemy(this.getX(), (int)(y-speed))) {
						moved = true;
						y -= speed;
						dir = upDir;
					}

				}else { //anda na diagonal
					
					if(dirRight == true && Game.world.isFree((int)(x+speed), this.getY(), this.z) && !isColiddingEnemy((int)(x+speed), this.getY())) {
						moved = true;
						x += speed;
						dir = rightDir;
						
					}else if(dirLeft == true && Game.world.isFree((int)(x-speed), this.getY(), this.z) && !isColiddingEnemy((int)(x-speed), this.getY())){
						moved =  true;
						x -= speed;
						dir = leftDir;
					} else if(dirDown == true && Game.world.isFree(this.getX(), (int)(y+speed), this.z) && !isColiddingEnemy(this.getX(), (int)(y+speed))) {
						moved = true;
						y += speed;
						dir = downDir;
					}else if (dirUp == true && Game.world.isFree(this.getX(), (int)(y-speed), this.z) && !isColiddingEnemy(this.getX(), (int)(y-speed))) {
						moved = true;
						y -= speed;
						dir = upDir;
					}
				}
				
				//Mudar de direção ao colidir
				
				//Para esquerda e para direitra
				if(dirRight == true && !Game.world.isFree((int)(x+speed), this.getY(), this.z)) {
					dirRight = false;
					dirLeft = true;
					ps = false;
				}
				
				if(dirLeft == true && !Game.world.isFree((int)(x-speed), this.getY(), this.z)) {
					dirRight = true;
					dirLeft = false;
					ps = true;
				}
				
				if(isColiddingEnemy((int)(x+speed), this.getY())) {
					dirRight = false;
					dirLeft = true;
					ps = false;
				}
				
				if(isColiddingEnemy((int)(x-speed), this.getY())) {
					dirRight = true;
					dirLeft = false;
					ps = true;
				}
				
				//Para baixo e para cima
				
				if(dirDown == true && !Game.world.isFree(this.getX(), (int)(y+speed), this.z)) {
					dirDown = false;
					dirUp = true;
				}
				
				if(dirUp == true && !Game.world.isFree(this.getX(), (int)(y-speed), this.z)) {
					dirDown = true;
					dirUp = false;
				}
				
				if(isColiddingEnemy(this.getX(), (int)(y+speed))) {
					dirDown = false;
					dirUp = true;
				}
				
				if(isColiddingEnemy(this.getX(), (int)(y-speed))) {
					dirDown = true;
					dirUp = false;
				}
				
			}else { //Se entrar na área de target ou causar dano (SEGUE O PLAYER)
			
				if((int)x < Game.player.getX() && Game.world.isFree((int)(x+speed), this.getY(), this.z) && !isColiddingEnemy((int)(x+speed), this.getY())) {
					moved = true;
					x += speed;
					dir =  rightDir;
				}else if ((int)x > Game.player.getX() && Game.world.isFree((int)(x-speed), this.getY(), this.z) && !isColiddingEnemy((int)(x-speed), this.getY())){
					moved =  true;
					x -= speed;
					dir = leftDir;
				}
				
				if((int)y < Game.player.getY() && Game.world.isFree(this.getX(), (int)(y+speed), this.z) && !isColiddingEnemy(this.getX(), (int)(y+speed))) {
					moved = true;
					y += speed;
					dir = downDir;
				}else if ((int)y > Game.player.getY() && Game.world.isFree(this.getX(), (int)(y-speed), this.z) && !isColiddingEnemy(this.getX(), (int)(y-speed))){
					moved = true;
					y -= speed;
					dir = upDir;
				}
			}
		}else { // Se colidir com o player (CAUSA DANO)
			
			if(Game.rand.nextInt(100) < 10) {
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamage = true;
				Sound.Clips.hurt.play();
			}
		}
		
		//MOVIMENTAÇÃO DE PERSEGUIÇÃO COM ALGORITMO A*
//		if(!isColiddingWithPlayer()) {
//			if(path == null || path.size() == 0) {
//				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
//				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
//				path = AStar.findPath(Game.world, start, end);
//			}
//		}else {
//			if(new Random().nextInt(100) < 5) {
//				Sound.hurtEffect.play();
//				Game.player.life-=Game.rand.nextInt(3);
//				Game.player.isDamage = true;
//			}
//		}
//			if(new Random().nextInt(100) < 50)
//				followPath(path);
//			
//			if(x % 16 == 0 && y % 16 == 0) {
//				if(new Random().nextInt(100) < 10) {
//					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
//					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
//					path = AStar.findPath(Game.world, start, end);
//				}
//			}
//			
//			if(this.x < Game.player.getX()) {
//				dir = rightDir;
//			}else if (this.x > Game.player.getX()){
//				dir = leftDir;
//			}
//			
//			if(this.y < Game.player.getY()) {
//				dir = downDir;
//			}else if (this.y > Game.player.getY()){
//				dir = upDir;
//			}
			
		
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
		
		collidingBullet();
		
		if(life <= 0)
			destroySelf();
		
		if(isDamage) {	
			//Se o player causar dano no enemy, pega target
			target = true;
			damageFrames++;
			if(this.damageFrames == 100) {
				damageFrames = 0; 
				isDamage = false;
			}
		}
		
	}
	
	public void destroySelf() {
		Game.entities.remove(this);
		Game.enemies.remove(this);
		this.exp = 999;

		double dif;
		dif = Game.player.maxExp[Game.player.maxLevel] - Game.player.exp;
		
		if(dif >= this.exp && dif > 0) {
			Game.player.exp += this.exp;
		}else {
			Game.player.exp += dif;
		}
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
				life --;
				isDamage = true;
				return;
			}
		}
	}

	public void render(Graphics g) {
		super.render(g);
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskTragetx, this.getY() - Camera.y + maskTargety, maskTargetw, maskTargeth);
		
		if(isDamage) {
			
			if(dir == rightDir)
				g.drawImage(rightEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}else {
		
			if(dir == rightDir)
				g.drawImage(rightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}		
		
		g.setColor(Color.black); 
		g.fillRect((int)x + 2 - Camera.x, (int)y - 5 - Camera.y, 12, 3);
		g.setColor(Color.red);
		g.fillRect((int)x + 3 - Camera.x, (int)y - 4 - Camera.y, 10, 1);
		g.setColor(Color.green);
		g.fillRect((int)x + 3 - Camera.x, (int)y - 4 - Camera.y, (int)((life/maxLife)*10), 1);
		
//		g.setColor(Color.black);
//		g.fillRect(Game.player.getX() - Camera.x + Game.player.maskx, Game.player.getY() - Camera.y + Game.player.masky, Game.player.maskw, Game.player.maskh);
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
