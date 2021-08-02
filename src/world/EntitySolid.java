package world;

import entities.Staircase;
import entities.Tree;
import entities.NPC.Npc;
import entities.construction.House;
import entities.construction.Mine;
import entities.construction.Statue;
import entities.spots.FishingSpot;
import main.Game;

public class EntitySolid {
	
	public static boolean solidCollision(int xNext, int yNext) {
		
		if(solidTree(xNext, yNext) &&
				solidSpotFishing(xNext, yNext) &&
				solidNpc(xNext, yNext) &&
				solidMine(xNext, yNext) && 
				solidStaircase(xNext, yNext) &&
				solidHouse(xNext, yNext) &&
				solidStatue(xNext, yNext)) {
			return true;
		}
		
		return false;
		
	}

	public static boolean solidTree(int xNext, int yNext) {
				
		int x1 = (xNext + 8) / Game.world.TILE_SIZE;
		int y1 = (yNext + 8) / Game.world.TILE_SIZE;
		
		int x2 = (xNext + Game.world.TILE_SIZE - 8) /Game.world.TILE_SIZE;
		int y2 = (yNext + 8) / Game.world.TILE_SIZE;
		
		int x3 = (xNext + 8) /Game.world.TILE_SIZE; 
		int y3 = (yNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE; 
		
		int x4 = (xNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE; 
		int y4 = (yNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE;
		
		if (!((Game.world.tiles[x1 + (y1*Game.world.WIDTH)].en instanceof Tree) ||
				(Game.world.tiles[x2 + (y2*Game.world.WIDTH)].en instanceof Tree) ||
				(Game.world.tiles[x3 + (y3*Game.world.WIDTH)].en instanceof Tree) ||
				(Game.world.tiles[x4 + (y4*Game.world.WIDTH)].en instanceof Tree)) ){
			return true;
		}
		return false;
	}

	public static boolean solidSpotFishing(int xNext, int yNext) {
	
		int x1 = (xNext + 5) / Game.world.TILE_SIZE;
		int y1 = (yNext + 2) / Game.world.TILE_SIZE;
		
		int x2 = (xNext + Game.world.TILE_SIZE - 6) / Game.world.TILE_SIZE;
		int y2 = (yNext + 2)/ Game.world.TILE_SIZE;
		
		int x3 = (xNext + 5) / Game.world.TILE_SIZE;
		int y3 = (yNext + Game.world.TILE_SIZE - 1) / Game.world.TILE_SIZE;
		
		int x4 = (xNext + Game.world.TILE_SIZE - 6) / Game.world.TILE_SIZE;
		int y4 = (yNext + Game.world.TILE_SIZE - 1) / Game.world.TILE_SIZE;
	
		if (!(((Game.world.tiles[x1 + (y1*Game.world.WIDTH)].en instanceof FishingSpot) ||
				(Game.world.tiles[x2 + (y2*Game.world.WIDTH)].en instanceof FishingSpot) ||
				(Game.world.tiles[x3 + (y3*Game.world.WIDTH)].en instanceof FishingSpot) ||
				(Game.world.tiles[x4 + (y4*Game.world.WIDTH)].en instanceof FishingSpot)))){
			return true;
		} 
		return false;
			
	}
	
	public static boolean solidNpc(int xNext, int yNext) {
		
		int x1 = (xNext + 8) / Game.world.TILE_SIZE;
		int y1 = (yNext + 8) / Game.world.TILE_SIZE;
		
		int x2 = (xNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE;
		int y2 = (yNext + 8) / Game.world.TILE_SIZE;
		
		int x3 = (xNext + 8) / Game.world.TILE_SIZE; 
		int y3 = (yNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE; 
		
		int x4 = (xNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE; 
		int y4 = (yNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE;
		
		if (!((Game.world.tiles[x1 + (y1*Game.world.WIDTH)].en instanceof Npc) ||
				(Game.world.tiles[x2 + (y2*Game.world.WIDTH)].en instanceof Npc) ||
				(Game.world.tiles[x3 + (y3*Game.world.WIDTH)].en instanceof Npc) ||
				(Game.world.tiles[x4 + (y4*Game.world.WIDTH)].en instanceof Npc)) ){
			return true;
		}
		return false;
	}
	
	public static boolean solidMine(int xNext, int yNext) {
		
		int x1 = (xNext + 4) / Game.world.TILE_SIZE;
		int y1 = (yNext + 4) / Game.world.TILE_SIZE;
		
		int x2 = (xNext + Game.world.TILE_SIZE - 9) / Game.world.TILE_SIZE;
		int y2 = (yNext + 4) / Game.world.TILE_SIZE;
		
		int x3 = (xNext + 4) / Game.world.TILE_SIZE; 
		int y3 = (yNext + Game.world.TILE_SIZE - 12) / Game.world.TILE_SIZE; 
		
		int x4 = (xNext + Game.world.TILE_SIZE - 9) / Game.world.TILE_SIZE; 
		int y4 = (yNext + Game.world.TILE_SIZE - 12) / Game.world.TILE_SIZE;
		
//		int x5 = (xNext + (World.TILE_SIZE*2) - 9) / World.TILE_SIZE; // para baixo
//		int y5 = (yNext + (World.TILE_SIZE*2) - 14) / World.TILE_SIZE;
		
		if (!((Game.world.tiles[x1 + (y1*Game.world.WIDTH)].en instanceof Mine) ||
				(Game.world.tiles[x2 + (y2*Game.world.WIDTH)].en instanceof Mine) ||
				(Game.world.tiles[x3 + (y3*Game.world.WIDTH)].en instanceof Mine) ||
				(Game.world.tiles[x4 + (y4*Game.world.WIDTH)].en instanceof Mine)) ){
			return true;
		}
		return false;
	}
	
	public static boolean solidStaircase(int xNext, int yNext) {
		
		int x1 = (xNext + 9) / Game.world.TILE_SIZE;
		int y1 = (yNext - 0) / Game.world.TILE_SIZE;
		
		int x2 = (xNext + Game.world.TILE_SIZE - 0) / Game.world.TILE_SIZE;
		int y2 = (yNext - 0) / Game.world.TILE_SIZE;
		
		int x3 = (xNext + 9) / Game.world.TILE_SIZE; 
		int y3 = (yNext + Game.world.TILE_SIZE - 0) / Game.world.TILE_SIZE; 
		
		int x4 = (xNext + Game.world.TILE_SIZE - 0) / Game.world.TILE_SIZE; 
		int y4 = (yNext + Game.world.TILE_SIZE - 0) / Game.world.TILE_SIZE;
		
		if (!((Game.world.tiles[x1 + (y1*Game.world.WIDTH)].en instanceof Staircase) ||
				(Game.world.tiles[x2 + (y2*Game.world.WIDTH)].en instanceof Staircase) ||
				(Game.world.tiles[x3 + (y3*Game.world.WIDTH)].en instanceof Staircase) ||
				(Game.world.tiles[x4 + (y4*Game.world.WIDTH)].en instanceof Staircase)) ){
			return true;
		}
		return false;
	}
	
	public static boolean solidHouse(int xNext, int yNext) {
		
		int x1 = (xNext + 4) / Game.world.TILE_SIZE;
		int y1 = (yNext + 14) / Game.world.TILE_SIZE;
		
		int x2 = (xNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE;
		int y2 = (yNext + 14) / Game.world.TILE_SIZE;
		
		int x3 = (xNext + 4) / Game.world.TILE_SIZE; 
		int y3 = (yNext + Game.world.TILE_SIZE + 14) / Game.world.TILE_SIZE; 
		
		int x4 = (xNext + Game.world.TILE_SIZE - 8) / Game.world.TILE_SIZE; 
		int y4 = (yNext + Game.world.TILE_SIZE + 14) / Game.world.TILE_SIZE;
		
		if (!((Game.world.tiles[x1 + (y1*Game.world.WIDTH)].en instanceof House) ||
				(Game.world.tiles[x2 + (y2*Game.world.WIDTH)].en instanceof House)) ||
				(Game.world.tiles[x3 + (y3*Game.world.WIDTH)].en instanceof House) ||
				(Game.world.tiles[x4 + (y4*Game.world.WIDTH)].en instanceof House)){
			return true;
		}
		return false;
	}
	
	public static boolean solidStatue(int xNext, int yNext) {
		
		int x1 = (xNext - 20) / Game.world.TILE_SIZE;
		int y1 = (yNext - 14) / Game.world.TILE_SIZE;
		
		int x2 = (xNext - 12) / Game.world.TILE_SIZE;
		int y2 = (yNext - 14) / Game.world.TILE_SIZE;
		
		
		if (!((Game.world.tiles[x1 + (y1*Game.world.WIDTH)].en instanceof Statue) ||
				(Game.world.tiles[x2 + (y2*Game.world.WIDTH)].en instanceof Statue))){
			return true;
		}
		return false;
	}
	
}
