package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import main.Sound;
import util.Mapa;
import world.Camera;
import world.EntitySolid;

public class Player extends Entity{
	
	public boolean rigth, left, down, up;
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
	public int dir = rightDir;
	
	public double speed, moveMx, moveMy, mx, my;
	
	public boolean jump;
	public boolean isJumping, jumpUp, jumpDown;
	public int jumpFrames = 25, jumpCur, jumpSp = 1, z;
	
	public int frames, maxFrames = 5, index, maxIndex = 3;
	public boolean moved;
	public boolean isDamage;
	private int damageFrames;
	
	public boolean hasGun, hasAxe, hasFishingRod, hasHoe;
	public boolean shoot, mouseShoot, openLvls, offLvls = true, openMap, offMap = true;
	public boolean useLighter, useBag, openDoor, enter, enterRoom, creation, getFish, fishing, cuttingTree;
	public boolean clickInv, clickBag, clickCreation, clickCraft;
	public boolean dropItem, getItem, useItem;

	public double life = 100, maxLife = 100, exp;
	public double [] maxExp = {100, 500, 1000, 5000, 10000};
	public int ammo = 1000, levelPlayer, maxLevel = 4;
	
	public String levelRoom;
	
	public Door doorCollision;
	
	private BufferedImage [] rightPlayer;
	private BufferedImage [] leftPlayer;
	private BufferedImage [] upPlayer;
	private BufferedImage [] downPlayer; 
	
	private BufferedImage [] rightPlayerDamage;
	private BufferedImage [] leftPlayerDamage;
	private BufferedImage [] upPlayerDamage;
	private BufferedImage [] downPlayerDamage;
	
	private BufferedImage [] rightPlayerWithGun;
	private BufferedImage [] leftPlayerWithGun;
	private BufferedImage [] upPlayerWithGun;
	private BufferedImage [] downPlayerWithGun; 
	
	private BufferedImage [] rightPlayerDamageWithGun;
	private BufferedImage [] leftPlayerDamageWithGun;
	private BufferedImage [] upPlayerDamageWithGun;
	private BufferedImage [] downPlayerDamageWithGun;
	
	private BufferedImage [] rightPlayerWithAxe;
	private BufferedImage [] leftPlayerWithAxe;
	private BufferedImage [] upPlayerWithAxe;
	private BufferedImage [] downPlayerWithAxe; 
	
	private BufferedImage [] rightPlayerDamageWithAxe;
	private BufferedImage [] leftPlayerDamageWithAxe;
	private BufferedImage [] upPlayerDamageWithAxe;
	private BufferedImage [] downPlayerDamageWithAxe;
	
	private BufferedImage [] rightPlayerDamageWithFishingRod;
	private BufferedImage [] leftPlayerDamageWithFishingRod;
	private BufferedImage [] upPlayerDamageWithFishingRod;
	private BufferedImage [] downPlayerDamageWithFishingRod;
	
	private BufferedImage [] rightPlayerWithFishingRod;
	private BufferedImage [] leftPlayerWithFishingRod;
	private BufferedImage [] upPlayerWithFishingRod;
	private BufferedImage [] downPlayerWithFishingRod;
	
	private BufferedImage [] rightPlayerWithHoe;
	private BufferedImage [] leftPlayerWithHoe;
	private BufferedImage [] upPlayerWithHoe;
	private BufferedImage [] downPlayerWithHoe; 
	
	private BufferedImage [] rightPlayerDamageWithHoe;
	private BufferedImage [] leftPlayerDamageWithHoe;
	private BufferedImage [] upPlayerDamageWithHoe;
	private BufferedImage [] downPlayerDamageWithHoe;
	
	private BufferedImage rightPlayerJumping;
	private BufferedImage leftPlayerJumping;
	private BufferedImage upPlayerJumping;
	private BufferedImage downPlayerJumping; 
	
	private BufferedImage rightPlayerDamageJumping;
	private BufferedImage leftPlayerDamageJumping;
	private BufferedImage upPlayerDamageJumping;
	private BufferedImage downPlayerDamageJumping;
	
	private BufferedImage rightPlayerGunJumping;
	private BufferedImage leftPlayerGunJumping;
	private BufferedImage upPlayerGunJumping;
	private BufferedImage downPlayerGunJumping; 
	
	private BufferedImage rightPlayerGunDamageJumping;
	private BufferedImage leftPlayerGunDamageJumping;
	private BufferedImage upPlayerGunDamageJumping;
	private BufferedImage downPlayerGunDamageJumping;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		maskx = 4; masky = 0; mwidth = 8; mheigth = 16;
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		rightPlayerDamage = new BufferedImage[4];
		leftPlayerDamage = new BufferedImage[4];
		upPlayerDamage = new BufferedImage[4];
		downPlayerDamage = new BufferedImage[4]; 
		
		rightPlayerWithGun = new BufferedImage[4];
		leftPlayerWithGun = new BufferedImage[4];
		upPlayerWithGun = new BufferedImage[4];
		downPlayerWithGun = new BufferedImage[4];
		
		rightPlayerDamageWithGun = new BufferedImage[4];
		leftPlayerDamageWithGun = new BufferedImage[4];
		upPlayerDamageWithGun = new BufferedImage[4];
		downPlayerDamageWithGun = new BufferedImage[4];
		
		rightPlayerWithAxe = new BufferedImage[4];
		leftPlayerWithAxe = new BufferedImage[4];
		upPlayerWithAxe = new BufferedImage[4];
		downPlayerWithAxe = new BufferedImage[4];
		
		rightPlayerDamageWithAxe = new BufferedImage[4];
		leftPlayerDamageWithAxe = new BufferedImage[4];
		upPlayerDamageWithAxe = new BufferedImage[4];
		downPlayerDamageWithAxe = new BufferedImage[4];
		
		rightPlayerWithFishingRod = new BufferedImage[4];
		leftPlayerWithFishingRod = new BufferedImage[4];
		upPlayerWithFishingRod = new BufferedImage[4];
		downPlayerWithFishingRod = new BufferedImage[4];
		
		rightPlayerDamageWithFishingRod = new BufferedImage[4];
		leftPlayerDamageWithFishingRod = new BufferedImage[4];
		upPlayerDamageWithFishingRod = new BufferedImage[4];
		downPlayerDamageWithFishingRod = new BufferedImage[4];
		
		rightPlayerWithHoe = new BufferedImage[4];
		leftPlayerWithHoe = new BufferedImage[4];
		upPlayerWithHoe = new BufferedImage[4];
		downPlayerWithHoe = new BufferedImage[4];
		
		rightPlayerDamageWithHoe = new BufferedImage[4];
		leftPlayerDamageWithHoe = new BufferedImage[4];
		upPlayerDamageWithHoe = new BufferedImage[4];
		downPlayerDamageWithHoe = new BufferedImage[4];
		
		for(int i = 0; i<4; i++) {
			rightPlayer[i] = Game.spritePlayer.getSprite(0 + (i*16), 0, 16, 16);
			leftPlayer[i] = Game.spritePlayer.getSprite(0 + (i*16), 16, 16, 16);
			upPlayer[i] = Game.spritePlayer.getSprite(0 + (i*16), 32, 16, 16);
			downPlayer[i] = Game.spritePlayer.getSprite(0 + (i*16), 48, 16, 16);
			
			rightPlayerDamage[i] = Game.spritePlayer.getSprite(0 + (i*16), 64, 16, 16);
			leftPlayerDamage[i] = Game.spritePlayer.getSprite(0 + (i*16), 80, 16, 16);
			upPlayerDamage[i] = Game.spritePlayer.getSprite(0 + (i*16), 96, 16, 16);
			downPlayerDamage[i] = Game.spritePlayer.getSprite(0 + (i*16), 112, 16, 16);
			
			rightPlayerWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 128, 16, 16);
			leftPlayerWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 144, 16, 16);
			upPlayerWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 160, 16, 16);
			downPlayerWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 176, 16, 16);
			
			rightPlayerDamageWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 192, 16, 16);
			leftPlayerDamageWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 208, 16, 16);
			upPlayerDamageWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 224, 16, 16);
			downPlayerDamageWithGun[i] = Game.spritePlayer.getSprite(0 + (i*16), 240, 16, 16);
			
			rightPlayerWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 0, 16, 16);
			leftPlayerWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 16, 16, 16);
			upPlayerWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 32, 16, 16);
			downPlayerWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 48, 16, 16);
			
			rightPlayerDamageWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 64, 16, 16);
			leftPlayerDamageWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 80, 16, 16);
			upPlayerDamageWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 96, 16, 16);
			downPlayerDamageWithAxe[i] = Game.spritePlayer.getSprite(64 + (i*16), 112, 16, 16);
			
			rightPlayerWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 128, 16, 16);
			leftPlayerWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 144, 16, 16);
			upPlayerWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 160, 16, 16);
			downPlayerWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 176, 16, 16);
			
			rightPlayerDamageWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 192, 16, 16);
			leftPlayerDamageWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 208, 16, 16);
			upPlayerDamageWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 224, 16, 16);
			downPlayerDamageWithFishingRod[i] = Game.spritePlayer.getSprite(64 + (i*16), 240, 16, 16);
		
			rightPlayerWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 0, 16, 16);
			leftPlayerWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 16, 16, 16);
			upPlayerWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 32, 16, 16);
			downPlayerWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 48, 16, 16);
			
			rightPlayerDamageWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 64, 16, 16);
			leftPlayerDamageWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 80, 16, 16);
			upPlayerDamageWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 96, 16, 16);
			downPlayerDamageWithHoe[i] = Game.spritePlayer.getSprite(128 + (i*16), 112, 16, 16);
			
		}
		
		rightPlayerJumping = Game.spritePlayer.getSprite(0, 256, 16, 16);
		leftPlayerJumping  = Game.spritePlayer.getSprite(16, 256, 16, 16);
		upPlayerJumping = Game.spritePlayer.getSprite(32, 256, 16, 16);
		downPlayerJumping = Game.spritePlayer.getSprite(48, 256, 16, 16);
		
		rightPlayerDamageJumping = Game.spritePlayer.getSprite(0, 272, 16, 16);
		leftPlayerDamageJumping  = Game.spritePlayer.getSprite(16, 272, 16, 16);
		upPlayerDamageJumping = Game.spritePlayer.getSprite(32, 272, 16, 16);
		downPlayerDamageJumping = Game.spritePlayer.getSprite(48, 272, 16, 16);
		
		rightPlayerGunJumping = Game.spritePlayer.getSprite(48, 128, 16, 16);
		leftPlayerGunJumping  = Game.spritePlayer.getSprite(48,  144, 16, 16);
		upPlayerGunJumping = Game.spritePlayer.getSprite(48,  160, 16, 16);
		downPlayerGunJumping = Game.spritePlayer.getSprite(48, 176, 16, 16);
		
		rightPlayerGunDamageJumping = Game.spritePlayer.getSprite(48, 192, 16, 16);
		leftPlayerGunDamageJumping  = Game.spritePlayer.getSprite(48, 208, 16, 16);
		upPlayerGunDamageJumping = Game.spritePlayer.getSprite(48, 224, 16, 16);
		downPlayerGunDamageJumping = Game.spritePlayer.getSprite(48, 240, 16, 16);
	}
	
	public void checkKillEnemy() {
		
		for(int i=0; i<maxExp.length; i++) {
			if(exp <= maxExp[i]) {
				levelPlayer = i;
				break;
			}
		}
	}
	
	public int depthPlayer(Entity e) {
		
		int yPlayer = (int)this.y;
		int yEntity = (int)e.getY();
		
		if(yPlayer > yEntity - 2) { // colocar o player atras dos objetos e dar noção de profundidade
			depth = e.depth+1;
			return  1;
		
		}else {
			depth = 1;
			return e.depth+1;
		}
	}
	
	public void revealMap() {
		
		int xx = (int) (x/16);
		int yy = (int) (y/16);
		
		int ps1, ps2,ps3,ps4;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				
				ps1 = (int)xx+(i)+(yy+j)*Game.world.WIDTH;
				ps2 = (int)xx-(i)+(yy+j)*Game.world.WIDTH;
				ps3 = (int)xx+(i)+(yy-j)*Game.world.WIDTH;
				ps4 = (int)xx-(i)+(yy-j)*Game.world.WIDTH;
				
				if(ps1 < Game.world.tiles.length && ps2 < Game.world.tiles.length && ps3 < Game.world.tiles.length && ps4 < Game.world.tiles.length
						&& ps1 > 0 && ps2 > 0 && ps3 > 0 && ps4 > 0) {
					
					revealEntity(ps1, ps2, ps3, ps4);
					revealEnemy(ps1, ps2, ps3, ps4);
					revealBulletShot(ps1, ps2, ps3, ps4);
					revealParticle(ps1, ps2, ps3, ps4);
					revealMobs(ps1, ps2, ps3, ps4);
					
					Game.world.tiles[ps1].show = true;
					Game.world.tiles[ps2].show = true;
					Game.world.tiles[ps3].show = true;
					Game.world.tiles[ps4].show = true;
				}
			}
		}
	}
	
	public void revealEntity(int ps1, int ps2, int ps3, int ps4) {
		
		for(int i=0; i < Game.entities.size(); i++) {
			if(Game.entities.get(i).psTiles == ps1 || Game.entities.get(i).psTiles == ps2 ||
				Game.entities.get(i).psTiles == ps3 || Game.entities.get(i).psTiles == ps4 ) {
					Game.entities.get(i).show = true;
			}
		}
		
	}
	
	public void revealEnemy(int ps1, int ps2, int ps3, int ps4) {
		
		for(int i=0; i < Game.enemies.size(); i++) {
			int xx = (int) (Game.enemies.get(i).x/16);
			int yy = (int) (Game.enemies.get(i).y/16);
			
			int ps = (int)xx+(yy)*Game.world.WIDTH;
			
			if(Game.world.tiles[ps].show) {
				Game.enemies.get(i).show = true;
			}else {
				Game.enemies.get(i).show = false;
			}
			
		}
	}
	
	public void revealMobs(int ps1, int ps2, int ps3, int ps4) {
		
		for(int i=0; i < Game.mobs.size(); i++) {
			int xx = (int) (Game.mobs.get(i).x/16);
			int yy = (int) (Game.mobs.get(i).y/16);
			
			int ps = (int)xx+(yy)*Game.world.WIDTH;
			
			if(Game.world.tiles[ps].show) {
				Game.mobs.get(i).show = true;
			}else {
				Game.mobs.get(i).show = false;
			}
			
		}
	}
	
	public void revealBulletShot(int ps1, int ps2, int ps3, int ps4) {
		
		for(int i=0; i < Game.bulletShootes.size(); i++) {
			int xx = (int) (Game.bulletShootes.get(i).x/16);
			int yy = (int) (Game.bulletShootes.get(i).y/16);
			
			int ps = (int)xx+(yy)*Game.world.WIDTH;
			
			if(Game.world.tiles[ps].show) {
				Game.bulletShootes.get(i).show = true;
			}else {
				Game.bulletShootes.get(i).show = false;
			}
		}
	}
	
	public void revealParticle(int ps1, int ps2, int ps3, int ps4) {
		
		if(Game.particles != null) {
			for(int i=0; i < Game.particles.size(); i++) {
				int xx = (int) (Game.particles.get(i).x/16);
				int yy = (int) (Game.particles.get(i).y/16);
				
				int ps = (int)xx+(yy)*Game.world.WIDTH;
				
				if(Game.world.tiles[ps].show) {
					Game.particles.get(i).show = true;
				}else {
					Game.particles.get(i).show = false;
				}
			}
		}
	}
	
	public void checkUseItem() {
		
		if(useItem) {
			
			int hi = Game.sysInv.handIndexItem;
			Entity h = Game.sysInv.handItem;
			Game.player.useItem = false;
				
			if(Game.sysInv.inventario[hi] instanceof LifePack && h instanceof LifePack) {
				useLifePack(hi);
			}else {	
				while (Game.sysInv.handItem instanceof LifePack) {
					Game.sysInv.checkScrollItem(Game.sysInv.handIndexItem);
				}
						
				hi = Game.sysInv.handIndexItem;
				h = Game.sysInv.handItem;
						
				if(Game.sysInv.inventario[hi] instanceof LifePack && h instanceof LifePack) {
					useLifePack(hi);
				}	
			}
			
			if(Game.sysInv.inventario[hi] instanceof Lighter && h instanceof Lighter  && !Game.player.useLighter && (Game.hour >= 18 || (Game.hour <= 7 && Game.hour >=0))) {
				Game.player.useLighter = true;
				Sound.Clips.lighter.play();
			}else  if(Game.sysInv.inventario[hi] instanceof Lighter  && Game.player.useLighter){
				Game.player.useLighter = false;
				Sound.Clips.lighter.play();
			}
			
		}
	}
	
	public void useLifePack(int index) {

		double dif;
		
		if(Game.player.life <= 90) {
			Game.sysInv.checkScrollItem(index);
			Game.player.life += 10;
			Game.sysInv.inventario[index] = null;
			Game.sysInv.inv[index] = null;
		}else if (Game.player.life < 100){
			Game.sysInv.checkScrollItem(index);
			dif = 100 - Game.player.life;
			Game.player.life += dif;
			Game.sysInv.inventario[index] = null;
			Game.sysInv.inv[index] = null;
		}
			
	}
	
	private void collidingNoInteratorAndSound() {
		
		if(!Game.mapaGame.equals(Mapa.MAPA_CALABOUÇO)) {
		
			if(Game.collision.checkCollisionFishingSpotMask()) {
				Sound.Clips.waterRunning.loop();
			}else {
				Sound.Clips.waterRunning.stop();
			}
			
			if(Game.collision.checkCollisionWaterTile()) {
				Sound.Clips.waterRunning.loop();
			}else {
				Sound.Clips.waterRunning.stop();
			}
		}
		
	}
	
	public void tick() {
		
		depth = 5;
		
//		revealMap();
		
		if(creation) {
			getItem = false;
			useBag = false;
			useItem = false;
			dropItem = false;
			Game.createItem.createItem();
		}else {
			Game.sysCre.closeCreation((int)x, (int)y);
		}
		
		Game.sysInv.checkInventoryItemMap();
		Game.sysBag.checkBagpackItemMap();
		
		checkKillEnemy();

		collidingNoInteratorAndSound();
	
		Game.collision.checkCollision();
		Game.collision.checkCollisionNpc();
		Game.collision.checkCollisionPig();
		
		if(Game.collision.checkCollisionTree()) 
			Sound.Clips.cuttingTree.play();
		
		Game.collision.checkCollisionAmmo();
		Game.collision.checkCollisionDoor();
		Game.collision.checkColisionGround();
		Game.collision.checkCollisionStump();
		Game.collision.checkCollisionMine();
		Game.collision.checkCollisionStaircase();
		
		if(Game.collision.createGround())
			Sound.Clips.digging.play();
		
		
		Game.sysInv.checkDropItem();
		Game.sysInv.checkScrollItem();
		Game.sysInv.checkHasItem();
		Game.sysBag.checkItemBag();
		
		if(Game.collision.isTargetPlayer()) {
			Sound.Clips.pigGrunts.loop();
		}else {
			Sound.Clips.pigGrunts.stop();
		}
		
		if(clickInv && useBag) {
			clickInv = false;
			Game.sysInv.putItemBag();
		}else if (clickInv && creation) {
			clickInv = false;
			Game.sysInv.addItemCreation();
		}else if (clickCreation && creation) {
			clickCreation = false;
			Game.sysCre.removeItem(Game.sysCre.clickSelectIndexCreation);
		}
		
		if(clickBag) {
			clickBag = false;
			Game.sysBag.getItemBag();
		}
		
		if(Game.collision.checkCollisionFishingSpot() && hasFishingRod && useItem) {
			fishing = true;
		}
		
		if(fishing && hasFishingRod) {
			
			if(!Game.collision.checkCollisionFishingSpot()) {
				fishing = false;
				Sound.Clips.fishing.stop();
			}else {
				if(FishingSpot.fishingTime == FishingSpot.maxFishingfTime) {
					FishingSpot.fishingTime = 0;
					getFish = true;
					Sound.Clips.waterDrop.play();
				}else {
					getFish = false;
					FishingSpot.fishingTime++;
				}
				Sound.Clips.fishing.loop();
			}
			
			if(getFish) {
				Game.collision.getFish();
			}
		}
		
		if(openDoor)
			Game.collision.openDoor();	
		
		checkUseItem(); //Precisa ficar por últomo pois deixa a variavel useItem false
		
		if(jump) {
			if(isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		
		if(isJumping == true) {
			if(jumpUp) {
				jumpCur+= jumpSp;
			}else if(jumpDown) {
				jumpCur-= jumpSp;
				if(jumpCur<=0) {
					isJumping = false;
					jumpUp = false;
					jumpDown = false;
				}
			}
				z = jumpCur;
				if(jumpCur >= jumpFrames) {
					jumpUp = false;
					jumpDown = true;
				}
		}
		
		moved = false;
		
		if(!creation && !useBag) {
		
			if(rigth && Game.world.isFree((int)(x+speed), this.getY(), this.z)
					&& EntitySolid.solidCollision((int)(x+speed), this.getY())) {
				moved =  true;
				dir = rightDir; //Rotação de sprites com teclado
				x+=speed;
				
				
			}else if (left && Game.world.isFree((int)(x-speed), this.getY(), this.z) 
					&& EntitySolid.solidCollision((int)(x-speed), this.getY())) {
				moved =  true;
				dir = leftDir; //Rotação de sprites com teclado
				x-=speed;
			
			}
				
			if(up && Game.world.isFree(this.getX(),(int)(y-speed), this.z) 
					&& EntitySolid.solidCollision(this.getX(),(int)(y-speed))) {
				moved =  true;
				dir = upDir; //Rotação de sprites com teclado 
				y-=speed;
	
			}else if (down && Game.world.isFree(this.getX(), (int)(y+speed), this.z) 
					&& EntitySolid.solidCollision(this.getX(), (int)(y+speed))) {
				moved =  true;
				dir = downDir; //Rotação de sprites com teclado
				y+=speed;
			}
		}
		
		if(moved) {
			
			Sound.Clips.passos.loop();
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
			Sound.Clips.passos.stop();
		}
		
		if(life <= 0) { // Game over
			life = 0;
			Game.gameState = "GAME OVER";
		}
		
		if(isDamage) {
			damageFrames++;
			if(this.damageFrames == 8) {
				damageFrames = 0; 
				isDamage = false;
			}
		}
		
//		Atirar com o teclado e rotacionar sprite com teclado 
//		if(shoot) {
//			
//			shoot = false;
//			
//			if(hasGun && ammo > 0) {
//				ammo--;
//				int dx = 0;
//				int dy = 0;
//				int px = 0;
//				int py = 0;
//				
//				if(dir == rightDir) {
//					dx = 1;
//					px = 12;
//					py = 7;
//				}else if (dir == leftDir){
//					dx = -1;
//					px = 0;
//					py = 7;
//				}
//				
//				if (dir == downDir) {
//					dy = 1;
//					px = 6;
//					py = 6;
//				}else if (dir == upDir){
//					dy = -1;
//					px = 5;
//					py = 0;
//				}
//				
//				if(dir == rightDir || dir == leftDir) {
//					BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
//					Game.bulletShootes.add(bulletShoot);
//				}
//				
//				if(dir == downDir || dir == upDir) {
//					BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, 0, dy);
//					Game.bulletShootes.add(bulletShoot);
//				}
//			}
//		}
		
//		Atirar com o mouse e rotacionar sprite com teclado
		if(mouseShoot) {
			
			mouseShoot = false;
			
			if(hasGun && ammo > 0) {
				Sound.Clips.shoot.play();
				ammo--;
				int dx = 0;
				int dy = 0;
				int px = 0;
				int py = 0;
				
				if(dir == rightDir) {
					dx = 1;
					px = 12;
					py = 7;
				}else if (dir == leftDir){
					dx = -1;
					px = 0;
					py = 7;
				}
				
				if (dir == downDir) {
					dy = 1;
					px = 6;
					py = 6;
				}else if (dir == upDir){
					dy = -1;
					px = 5;
					py = 0;
				}
				
				if(dir == rightDir || dir == leftDir) {
					BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
					Game.bulletShootes.add(bulletShoot);
				}
				
				if(dir == downDir || dir == upDir) {
					BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, 0, dy);
					Game.bulletShootes.add(bulletShoot);
				}
			}else if (hasGun && ammo <= 0) {
				Sound.Clips.missAmo.play();
			}
		}
		
		//Rotacionar sprite
//		double angle = Math.atan2(moveMy - (this.getY()+8 - Camera.y), moveMx - (this.getX()+8 - Camera.x));
//		double direction = Math.toDegrees(angle);
//		System.out.println(direction);
//		
//		if(direction <= 35 && direction > -35)  //direita
//			dir = rightDir;
//		else if(direction <= -35 && direction > -145) //cima
//			dir = upDir;
//		else if (direction <= -145 || direction > 145)  //esquerda
//			dir = leftDir;
//		else if (direction <= 145 && direction > 50)  //baixo
//			dir = downDir;
//		
////Atirar e rotacionar sprite com o mouse
//		if(mouseShoot) {
//			
//			mouseShoot = false;
//			//Sound.missAmo.play();
//			
//			if(hasGun && ammo > 0) {
//				//Sound.missAmo.stop();
//				//Sound.shootRifle.play();
//				ammo--;
//				
//				double dx = Math.cos(angle);
//				double dy = Math.sin(angle);
//				int px = 0;
//				int py = 0;
//				
//				if(dir == rightDir) {
//					px = 12;
//					py = 7;
//				}else if (dir == leftDir){
//					px = 0;
//					py = 7;
//				}
//				
//				if (dir == downDir) {
//					px = 6;
//					py = 6;
//				}else if (dir == upDir){
//					px = 5;
//					py = 0;
//				}
//		
//				BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
//				Game.bulletShootes.add(bulletShoot);
//			}
//		}
		
	updateCamera();
		
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, Game.world.WIDTH*16 - Game.WIDTH);
		Camera.y =  Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, Game.world.HEIGHT*16 - Game.HEIGHT);
	}
	
	@Override
	public void render(Graphics g) {
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
	
		if(!isJumping) {
			
			if(!isDamage) {
					
				if(dir == rightDir) {
					
					if(hasGun) {
						g.drawImage(rightPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(rightPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(rightPlayerWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(rightPlayerWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) {
						
					if(hasGun) {
						g.drawImage(leftPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(leftPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(leftPlayerWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(leftPlayerWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
						
					if(hasGun) {
						g.drawImage(upPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(upPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(upPlayerWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(upPlayerWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
					
				}else if(dir == downDir) {
						
					if(hasGun) {
						g.drawImage(downPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(downPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(downPlayerWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(downPlayerWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
					
			}else {
					
				if(dir == rightDir) {
						
					if(hasGun) {
						g.drawImage(rightPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(rightPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(rightPlayerDamageWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(rightPlayerDamageWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) { 
						
					if(hasGun) {
						g.drawImage(leftPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(rightPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(leftPlayerDamageWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(leftPlayerDamageWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
				
					if(hasGun) {
						g.drawImage(upPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(upPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(upPlayerDamageWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(upPlayerDamageWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(upPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
					
				}else if(dir == downDir) {
						
					if(hasGun) {
						g.drawImage(downPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(downPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasFishingRod){
						g.drawImage(downPlayerDamageWithFishingRod[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if (hasHoe){
						g.drawImage(downPlayerDamageWithHoe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
			}
			
		}else { // Se estiver pulando
			
			if(!isDamage) {
				
				if(dir == rightDir) {
					
					if(hasGun) {
						g.drawImage(rightPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) {
						
					if(hasGun) {
						g.drawImage(leftPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
						
					if(hasGun) {
						g.drawImage(upPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(upPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
					
				}else if(dir == downDir) {
						
					if(hasGun) {
						g.drawImage(downPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
					
			}else {
					
				if(dir == rightDir) {
						
					if(hasGun) {
						g.drawImage(rightPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) { 
						
					if(hasGun) {
						g.drawImage(leftPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
					g.drawImage(upPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					if(hasGun) {
						g.drawImage(upPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}else if(dir == downDir) {
					
					if(hasGun) {
						g.drawImage(downPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
			}
		}
		
		if(isJumping) {
			g.setColor(Color.black);
			g.fillOval(this.getX() - Camera.x + 4, this.getY() - Camera.y + 12, 8, 8);
		}
		
	}	
}
