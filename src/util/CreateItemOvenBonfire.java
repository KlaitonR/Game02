package util;

import java.util.ArrayList;

import entities.Entity;
import main.Game;

public class CreateItemOvenBonfire {
	
	int numSlots;
	int index1 = -1;
	int index2 = -1;
	boolean img;
	ArrayList<Id> ids = new ArrayList<>();
	ArrayList<Id> idsOneIndex = new ArrayList<>();
	
	public void createItem () {
			
			//verifica se em alguma posição possui esses id
			idsOneIndex.add(Id.ID_FIREWOOD_OAK);
			idsOneIndex.add(Id.ID_FIREWOOD_PINE);
			idsOneIndex.add(Id.ID_FIREWOOD_WILLOW);
			index1 = Game.sysOvenBonfire.checkStuffList(idsOneIndex, ids);
			
			if(index1 >= 0) {
				Game.sysOvenBonfire.craftItem(ids, index1, -1, 1, -1, Entity.CARVAO_VEGETAL_EN, 1); //Forno
				img = true;
			}
			
			ids.clear();
			clearIndex();
			ids.addAll(idsOneIndex);
			index2 = Game.sysOvenBonfire.checkStuff(Id.ID_ORE_SILVER, ids);
			if(index1 >= 0 && index2 >= 0) {
				Game.sysOvenBonfire.craftItem(ids, index1, index2, 4, 1, Entity.BARRA_PRATA_EN, 1); //Barra de prata
				img = true;
			}
			
			ids.clear();
			clearIndex();
			ids.addAll(idsOneIndex);
			index2 = Game.sysOvenBonfire.checkStuff(Id.ID_ORE_COPPER, ids);
			if(index1 >= 0 && index2 >= 0) {
				Game.sysOvenBonfire.craftItem(ids, index1, index2, 4, 1, Entity.BARRA_COBRE_EN, 1); //Barra de cobre
				img = true;
			}
			
			ids.clear();
			clearIndex();
			ids.addAll(idsOneIndex);
			index2 = Game.sysOvenBonfire.checkStuff(Id.ID_ORE_GOLD, ids);
			if(index1 >= 0 && index2 >= 0) {
				Game.sysOvenBonfire.craftItem(ids, index1, index2, 4, 1, Entity.BARRA_OURO_EN, 1); //Barra de ouro
				img = true;
			}
			
			if(!img) {
				Game.sysOvenBonfire.indexCraftNull();
			}
			
			img = false;
			ids.clear();
			idsOneIndex.clear();
			clearIndexAll();
			Game.sysOvenBonfire.slot[2] = null;
			Game.player.clickOvenBonfire = false;
			
	}
	
	public void clearIndexAll() {
		index1 = -1;
		index2 = -1;
	}
	
	public void clearIndex() {
		index2 = -1;
	}

}
