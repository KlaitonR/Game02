package util;

import entities.Entity;
import main.Game;

public class CreationItem {
	
	int numSlots;
	int index1 = -1;
	int index2 = -1;
	int index3 = -1;
	int index4 = -1;
	boolean img;
	
	public void createItem () {
		
		//verifica se em alguma posição possui esses idskbk
		index1 = Game.sysCre.checkStuff(Id.ID_STONE);
		if(index1 >= 0) {
			Game.sysCre.craftItem(Id.ID_STONE, null, null, null, index1, -1, -1, -1, 1, -1, -1, -1, Entity.FORNO_EN, 1); //Forno
			img = true;
		}
		
		index1 = Game.sysCre.checkStuff(Id.ID_SEED_OAK);
		index2 = Game.sysCre.checkStuff(Id.ID_ROOT);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(Id.ID_SEED_OAK, Id.ID_ROOT, null, null, index1 ,index2, -1, -1 , 2, 2, -1, -1, Entity.POTION_EN, 2); //Potion
			img = true;
		}
		
		index1 = Game.sysCre.checkStuff(Id.ID_OVEN);
		index2 = Game.sysCre.checkStuff(Id.ID_ORE_GOLD);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(Id.ID_OVEN, Id.ID_ORE_GOLD, null, null, index1, index2,-1 , -1, 1, 1, -1, -1, Entity.AXE_EN, 2); //Ouro
			img = true;
		}
		
		if(!img) {
			Game.sysCre.indexCraftNull();
		}
		
		img = false;
	
	}
	
}
