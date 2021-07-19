package util;

import java.util.ArrayList;
import entities.Axe;
import entities.Beef;
import entities.Bullet;
import entities.Door;
import entities.Entity;
import entities.Firewood;
import entities.Fish;
import entities.FishingRod;
import entities.FishingSpot;
import entities.Ground;
import entities.Hoe;
import entities.LifePack;
import entities.Lighter;
import entities.Npc;
import entities.Potion;
import entities.Root;
import entities.Seed;
import entities.Stump;
import entities.Tree;
import entities.Wapon;
import main.Game;
import main.Sound;
import world.FloorTile;
import world.Tile;
import world.World;

public class CollisonPlayer {
	
	public ArrayList<Entity> allCollission = new ArrayList<>();
	
	public void checkCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Axe ||
				atual instanceof Beef ||
				atual instanceof Firewood ||
				atual instanceof Fish ||
				atual instanceof FishingRod ||
				atual instanceof Hoe ||
				atual instanceof LifePack ||
				atual instanceof Lighter ||
				atual instanceof Potion ||
				atual instanceof Root ||
				atual instanceof Seed ||
				atual instanceof Wapon) {
				confirmCollision(atual, Game.sysInv.checkPositionGetInv());
			}
		}
	}
	
	public void checkCollisionNpc() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Npc) {
				atual.depth = Game.player.depthPlayer((int)atual.y);
			}
		}
	}
	
	public void checkCollisionAmmo() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Bullet) {
				if(Entity.isColidding(Game.player, atual)) {
					
					atual.depth = Game.player.depthPlayer((int)atual.y);
					
					Game.player.ammo += 10;
					Game.entities.remove(atual);
					Sound.Clips.missAmo.play();
				}else {
					allCollission.remove(atual);
				}
			}
		}
	}
	
	public void confirmCollision(Entity atual, int index) {
		if(Entity.isColidding(Game.player, atual)) {
		
			atual.depth = Game.player.depthPlayer((int)atual.y);
				
			if(Game.player.getItem) {
				if(!Game.sysInv.checkPackInv(atual)) {
					if (index >= 0 && index <= Game.sysInv.inventario.length) {
						atual.clear = false;
						atual.timeClear = 0;
						Game.sysInv.inventario[index] = atual;
						Game.sysInv.inv[index] = atual.getSprite();
						Game.sysInv.handItem = Game.sysInv.inventario[index];
						Game.sysInv.handIndexItem = index;
						Game.entities.remove(atual);
						Sound.Clips.dropAndGetItem.play();
					}
				}
			}
		}
	}
	
	//Colisões com objetos que não irão para o inventario
	public void checkCollisionTree(){
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Tree) {
				if(Entity.isColidding(Game.player, atual)) {

					atual.depth = Game.player.depthPlayer((int)atual.y);
					
					if(Game.player.useItem && Game.player.hasAxe) {
						((Tree) atual).life--;
						Sound.Clips.cuttingTree.play();
					}
				}
			}
		}
	}
	
	public boolean checkCollisionFishingSpot() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof FishingSpot) {
				if(Entity.isColidding(Game.player, atual)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void getFish() {
		
		int index = Game.sysInv.checkPositionGetInv();
		
		Fish fish =  new Fish(0, 0, 16, 16, Entity.FISH_EN);
		fish.tipo = "peixe";
		
		if(!Game.sysInv.checkPackInv(fish)) {
			if (index >= 0 && index <= Game.sysInv.inventario.length) {
				Game.sysInv.inventario[index] = fish;
				Game.sysInv.inv[index] = fish.getSprite();
			}
		}else {
			fish = null;
		}
	}
	
	public void checkCollisionDoor() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Door) {
				if(Entity.isColidding(Game.player, atual)) {
					if(Game.player.useItem) {
						Game.player.openDoor =  true;
						Game.player.doorCollision = (Door) atual;
					}
				}
			}
		}
		
	}
	
	public void openDoor() {
		Game.player.openDoor = false;
		double menor = 99999999;
		double dist;
		int xTile = 0;
		int yTile = 0; 
		 
		for(int i=0; i<Game.entities.size(); i++) {
			if(Game.entities.get(i) instanceof Door && Game.entities.get(i) != Game.player.doorCollision ) {
					dist = Entity.calculateDistance((int)Game.player.getX(), (int)Game.player.getY(), (int)Game.entities.get(i).getX(), (int)Game.entities.get(i).getY());
					if(dist < menor) {
					 menor = dist;
						xTile = (int)Game.entities.get(i).getX();
						yTile = (int)Game.entities.get(i).getY();
						 
					}
				}
			}
	 
		Game.player.setX(xTile);
		Game.player.setY(yTile);
		Game.player.updateCamera();
	}
	
	public void createGround() {
		
		for(int i = 0; i < World.tiles.length; i++) {
				
			Tile t = World.tiles[i];
				
			if(World.isColiddingFloorTileToGround(Game.player, t) 
					&& Game.player.hasHoe 
					&& Game.player.useItem 
					&& t instanceof FloorTile
					&& !(t.en instanceof Ground)){
				Ground gd = new Ground(t.getX(), t.getY(), 16, 16, Entity.GROUND_EN, t.psTiles);
				gd.tipo = "terreno";
				gd.show = true;
				gd.psTiles = t.psTiles;
				World.tiles[t.psTiles].en = gd;
				Game.entities.add(gd);
				Sound.Clips.digging.play();
			}
		}
	}
	
	public void checkColisionGround() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Ground) {
				if(Entity.isColidding(Game.player, atual)) {
					
					Game.player.depthPlayer((int)atual.y);
					
					if(Game.sysInv.inventario[Game.sysInv.handIndexItem] != null) {
						if(Game.player.useItem && Game.sysInv.inventario[Game.sysInv.handIndexItem] instanceof Seed) {
							if(Game.sysInv.inventario[Game.sysInv.handIndexItem].tipo.equals("semente de carvalho")) {
								((Ground) atual).tipo = "terreno de carvalho";
							}else if (Game.sysInv.inventario[Game.sysInv.handIndexItem].tipo.equals("semente de pinheiro")) {
								((Ground) atual).tipo = "terreno de pinheiro";
							}
							
							((Ground) atual).plant = true;
							Game.sysInv.inventario[Game.sysInv.handIndexItem] = null;
							Game.sysInv.inv[Game.sysInv.handIndexItem] = null;
						
						}
					}
				}
			}
		}
	}
	
	public boolean checkColisionGroundToTree(Ground gd) {
		
		if(Entity.isColidding(Game.player, gd)) {
			return true;
		}
		
		return false;

	}
	
	public void checkCollisionStump() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Stump) {
				
				if(Entity.isColidding(Game.player, atual)) {
	
					atual.depth = Game.player.depthPlayer((int)atual.y);
					
					if(Game.player.useItem && Game.player.hasHoe) {
						((Stump) atual).destroySelf();
					}
				}
			}
		}
	}

}
