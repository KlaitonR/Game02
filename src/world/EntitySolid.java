package world;

import entities.FishingSpot;
import entities.Npc;
import entities.Tree;

public class EntitySolid {

	public static boolean solidTree(int xNext, int yNext) {
				
		int x1 = (xNext + 8) / World.TILE_SIZE;
		int y1 = (yNext + 8) / World.TILE_SIZE;
		
		int x2 = (xNext + World.TILE_SIZE - 8) / World.TILE_SIZE;
		int y2 = (yNext + 8) / World.TILE_SIZE;
		
		int x3 = (xNext + 8) / World.TILE_SIZE; 
		int y3 = (yNext + World.TILE_SIZE - 8) / World.TILE_SIZE; //direita
		
		int x4 = (xNext + World.TILE_SIZE - 8) / World.TILE_SIZE; // para baixo
		int y4 = (yNext + World.TILE_SIZE - 8) / World.TILE_SIZE;
		
		if (!((World.tiles[x1 + (y1*World.WIDTH)].en instanceof Tree) ||
				(World.tiles[x2 + (y2*World.WIDTH)].en instanceof Tree) ||
				(World.tiles[x3 + (y3*World.WIDTH)].en instanceof Tree) ||
				(World.tiles[x4 + (y4*World.WIDTH)].en instanceof Tree)) ){
			return true;
		}
		return false;
	}

	public static boolean solidSpotFishing(int xNext, int yNext) {
	
		int x1 = (xNext + 4) / World.TILE_SIZE;
		int y1 = (yNext + 2) / World.TILE_SIZE;
		
		int x2 = (xNext + World.TILE_SIZE - 5) / World.TILE_SIZE;
		int y2 = (yNext + 2)/ World.TILE_SIZE;
		
		int x3 = (xNext + 4) / World.TILE_SIZE;
		int y3 = (yNext + World.TILE_SIZE - 1) / World.TILE_SIZE;
		
		int x4 = (xNext + World.TILE_SIZE - 5) / World.TILE_SIZE;
		int y4 = (yNext + World.TILE_SIZE - 1) / World.TILE_SIZE;
	
		if (!(((World.tiles[x1 + (y1*World.WIDTH)].en instanceof FishingSpot) ||
				(World.tiles[x2 + (y2*World.WIDTH)].en instanceof FishingSpot) ||
				(World.tiles[x3 + (y3*World.WIDTH)].en instanceof FishingSpot) ||
				(World.tiles[x4 + (y4*World.WIDTH)].en instanceof FishingSpot)))){
			return true;
		} 
		return false;
			
	}
	
	public static boolean solidNpc(int xNext, int yNext) {
		
		int x1 = (xNext + 8) / World.TILE_SIZE;
		int y1 = (yNext + 8) / World.TILE_SIZE;
		
		int x2 = (xNext + World.TILE_SIZE - 8) / World.TILE_SIZE;
		int y2 = (yNext + 8) / World.TILE_SIZE;
		
		int x3 = (xNext + 8) / World.TILE_SIZE; 
		int y3 = (yNext + World.TILE_SIZE - 8) / World.TILE_SIZE; //direita
		
		int x4 = (xNext + World.TILE_SIZE - 8) / World.TILE_SIZE; // para baixo
		int y4 = (yNext + World.TILE_SIZE - 8) / World.TILE_SIZE;
		
		if (!((World.tiles[x1 + (y1*World.WIDTH)].en instanceof Npc) ||
				(World.tiles[x2 + (y2*World.WIDTH)].en instanceof Npc) ||
				(World.tiles[x3 + (y3*World.WIDTH)].en instanceof Npc) ||
				(World.tiles[x4 + (y4*World.WIDTH)].en instanceof Npc)) ){
			return true;
		}
		return false;
	}

	
}
