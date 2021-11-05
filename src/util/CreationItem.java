package util;

import java.util.ArrayList;

import entities.Entity;
import main.Game;

public class CreationItem {
	
	int numSlots;
	int index1 = -1;
	int index2 = -1;
	int index3 = -1;
	int index4 = -1;
	boolean img;
	ArrayList<Id> ids = new ArrayList<>();
	ArrayList<Id> idsOneIndex = new ArrayList<>();
	
	public void createItem () {
		
		//ITENS FABRICADOS COM APENAS UM ITEM
		
		//verifica se em alguma posição possui esses id
		index1 = Game.sysCre.checkStuff(Id.ID_STONE, ids);
		if(index1 >= 0) {
			Game.sysCre.craftItem(ids, index1, -1, -1, -1, 1, -1, -1, -1, Entity.FORNO_DESLIGADO_EN, 1, 1); //Forno
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_SULFOR, ids);
		if(index1 >= 0) {
			Game.sysCre.craftItem(ids, index1, -1, -1, -1, 1, -1, -1, -1, Entity.ENXOFRE_PROCESSADO_EN, 1, 4); //Enxofre processado
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_POTASSIUM_NITRATE, ids);
		if(index1 >= 0) {
			Game.sysCre.craftItem(ids, index1, -1, -1, -1, 1, -1, -1, -1, Entity.NITRATO_DE_POTASIO_PROCESSADO_EN, 1, 4); //Nitrato de potásio processado
			img = true;
		}
		
		ids.clear();
		idsOneIndex.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_BLOCK_WOOD, ids);
		if(index1 >= 0) {
			Game.sysCre.craftItem(ids, index1 , -1, -1, -1 , 1, -1, -1, -1, Entity.MADEIRA_PROCESSADA_EN, 1, 4); //Madeira Processada
			img = true;
		}
		
		ids.clear();
		idsOneIndex.clear();
		clearIndex();
		idsOneIndex.add(Id.ID_FIREWOOD_OAK);
		idsOneIndex.add(Id.ID_FIREWOOD_PINE);
		idsOneIndex.add(Id.ID_FIREWOOD_WILLOW);
		index1 = Game.sysCre.checkStuffList(idsOneIndex, ids);
		if(!ids.isEmpty()) {
			Game.sysCre.craftItem(ids, index1, -1, -1 , -1, 4, -1, -1, -1, Entity.BLOCO_DE_MADEIRA_EN, 1, 1); //Bloco e Madeira
			img = true;
		}
		
		//ITENS FABRICADOS COM DOIS ITENS 
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_SEED_OAK, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_ROOT, ids);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, -1, -1 , 2, 2, -1, -1, Entity.POTION_EN, 2, 1); //Potion
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_BLOCK_WOOD, ids);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, -1, -1 , 1, 1, -1, -1, Entity.AXE_WOOD_EN, 2, 1); //Machado de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_BAR_COOPER, ids);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, -1, -1 , 1, 1, -1, -1, Entity.AXE_COOPER_EN, 2, 1); //Machado de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_BAR_SILVER, ids);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, -1, -1 , 1, 1, -1, -1, Entity.AXE_SILVER_EN, 2, 1); //Machado de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_BAR_GOLD, ids);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, -1, -1 , 1, 1, -1, -1, Entity.AXE_GOLD_EN, 2, 1); //Machado de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_DIAMOND, ids);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, -1, -1 , 1, 1, -1, -1, Entity.AXE_DIAMOND_EN, 2, 1); //Machado de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_GUNPOWDER, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_BAR_SILVER, ids);
		if(index1 >= 0 && index2 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, -1, -1 , 1, 1, -1, -1, Entity.BOMBA_EN, 2, 1); //Machado de cobre
			img = true;
		}
		
		//ITENS FABRICADOS COM TRÊS ITENS
		
		ids.clear();
		idsOneIndex.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_POTASSIUM_NITRATE, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_PROCESSED_SULFOR, ids);
		index3 = Game.sysCre.checkStuff(Id.ID_CHARCOAL, ids);
		if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
			Game.sysCre.craftItem(ids, index1 ,index2, index3, -1 , 1, 1, 1, -1, Entity.POLVORA_EN, 3, 1); //Pólvora
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index3 = Game.sysCre.checkStuff(Id.ID_BLOCK_WOOD, ids);
		if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, index3, -1 , 1, 1, 1, -1, Entity.PICARETA_MADEIRA_EN, 3, 1); //Picareta de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index3 = Game.sysCre.checkStuff(Id.ID_BAR_COOPER, ids);
		if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, index3, -1 , 1, 1, 1, -1, Entity.PICARETA_COBRE_EN, 3, 1); //Picareta de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index3 = Game.sysCre.checkStuff(Id.ID_BAR_SILVER, ids);
		if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, index3, -1 , 1, 1, 1, -1, Entity.PICARETA_PRATA_EN, 3, 1); //Picareta de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index3 = Game.sysCre.checkStuff(Id.ID_BAR_GOLD, ids);
		if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, index3, -1 , 1, 1, 1, -1, Entity.PICARETA_OURO_EN, 3, 1); //Picareta de cobre
			img = true;
		}
		
		idsOneIndex.clear();
		ids.clear();
		clearIndex();
		index1 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_PROCESSED_WOOD, ids);
		index3 = Game.sysCre.checkStuff(Id.ID_DIAMOND, ids);
		if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
			Game.sysCre.craftItem(ids , index1 ,index2, index3, -1 , 1, 1, 1, -1, Entity.PICARETA_DIAMANTE_EN, 3, 1); //Picareta de cobre
			img = true;
		}
		
		ids.clear();
		idsOneIndex.clear();
		clearIndex();
		idsOneIndex.add(Id.ID_FIREWOOD_OAK);
		idsOneIndex.add(Id.ID_FIREWOOD_PINE);
		idsOneIndex.add(Id.ID_FIREWOOD_WILLOW);
		index1 = Game.sysCre.checkStuffList(idsOneIndex, ids);
		index2 = Game.sysCre.checkStuff(Id.ID_SULFOR, ids);
		index3 = Game.sysCre.checkStuff(Id.ID_PHOSPHOR, ids);
		if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
			Game.sysCre.craftItem(ids, index1 ,index2, index3, -1 , 2, 1, 1, -1, Entity.FOGUEIRA_EN, 3, 1); //Fogueira
			img = true;
		}
		
		//ITENS FABRICADOS COM QUATRO ITENS
		
		if(!img) {
			Game.sysCre.indexCraftNull();
		}
		
		img = false;
		ids.clear();
		idsOneIndex.clear();
		clearIndex();
		Game.sysCre.slot[4] = null;
		Game.player.clickCraft = false;
	
	}
	
	public void clearIndex() {
		
		index1 = -1;
		index2 = -1;
		index3 = -1;
		index4 = -1;
		
	}
	
}
