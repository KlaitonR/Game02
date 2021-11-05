package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Bonfire;
import entities.Entity;
import entities.Oven;
import entities.itens.Axe;
import entities.itens.BlockWood;
import entities.itens.Bomb;
import entities.itens.Gunpowder;
import entities.itens.Pickaxe;
import entities.itens.Potion;
import entities.itens.ProcessedPotassiumNitrate;
import entities.itens.ProcessedSulfor;
import entities.itens.ProcessedWood;
import main.Game;
import main.Sound;

public class SystemCreation {
	
	public BufferedImage [] itens = new BufferedImage[5];
	public Entity slot [] = new Entity[5];
	public final int indexCraft = 4;
	public int clickSelectIndexCreation;
	
	//pega o index do Item que foi clicado na janela de criação
	public void checkClickPositionItemCreation(int index) {
		clickSelectIndexCreation = index;
	}
	
	public void addItem(Entity item) {
		
		if(isNull()) {
			int i = indexSlotNull();
			if(i<slot.length-1) {
				slot[i] = item;
				itens[i] = item.getSprite();
				Sound.Clips.dropAndGetItem.play();
			}
			
		}
	}
	
	public void removeItem(int indexRemove) {
		
		int index;
		
		if(slot[indexRemove] != null) {
			if(!Game.sysInv.checkPackInv(slot[indexRemove])) {
				index = Game.sysInv.checkPositionGetInv();
				if (index >= 0 && index <= Game.sysInv.inventario.length) {
					Game.sysInv.inventario[index] = slot[indexRemove];
					Game.sysInv.inv[index] = slot[indexRemove].getSprite();
					Game.sysInv.handItem = slot[indexRemove];
					Game.sysInv.handIndexItem = index;
					slot[indexRemove] = null;
					itens[indexRemove] = null;
					Sound.Clips.dropAndGetItem.play();
				}
			}
		}
	}
	
	//Verifica se tem algum slots vazio
	public boolean isNull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null) {
				return true;
			}
		}
		
		return false;
	}
	
	//Verifica se todos os espaços estão vazios
	public boolean isAllNull() {
		
		int cont = 0;
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null) {
				cont++;
			}
		}
		
		if(cont == slot.length-1)
			return true;
		else 
			return false;
		
	}
	
	//Verifica se o slot de craft está vazio
	public boolean isNullCraft() {
		
		if(slot[slot.length-1] == null)
			return true;
		
		return false;
	}
	
	//retorna o index do primeiro slot nulo
	public int indexSlotNull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null)
				return i;
		}
		
		return -1;
	}
	
	//retorna o index do primeiro slot NÃO nulo
	public int indexSlotNotNull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] != null)
				return i;
		}
		
		return -1;
	}
	
	public void closeCreation(int x, int y) {
		
		if(CheckSlots()){ // verifica se possui itens na aba de criação
			if(!isFullInv()) { //verifica se o inventario possui espaço ou está cheio
				
				int slotsInv = qtSlotsInv(); //verifica quantas posições estão vazias
				
				if(slotsInv > 0) {//Verifica se possuei pelo menos 1 posição vazia
					
					while(slotsInv > 0) { //enquanto possuir posições vazias
						
						int indexRemove = indexSlotNotNull(); 
						if(indexRemove >= 0)
							removeItem(indexRemove); //remove da aba de criação para o inventario (já faz a checagem de pack)
						
						slotsInv--;
					}
					
					if(CheckSlots()) { //se ainda possui objetos na aba de criação, dropa os itens
						dropItens(x, y);
					}
					
				}else { // dropa todos os itens restantes
					dropItens(x, y);
				}
			}else {
				//dropa cada item individualmente se não possue um pack imcompleto no inventario ou espaço vazio
				for(int i=0; i<slot.length; i++) {
					if(!Game.sysInv.checkPackInv(slot[i])) {
						dropItem(x, y, i);
					}
				}
			}
		}
	}
	
	public void dropItens(int x, int y) { //Dropa todos os itens da aba criação
		
		for(int i=0; i<slot.length; i++) {
			if(slot[i]!=null) {
				slot[i].setX(x);
				slot[i].setY(y);
				slot[i].show = true;
				Game.entities.add(slot[i]);
				slot[i] = null;
				itens[i] = null;
				Sound.Clips.dropAndGetItem.play();
			}
		}
		
	}
	
	public void dropItem(int x, int y, int index) { //Dropa um item especifico da aba criação
		
			if(slot[index] != null) {
				slot[index].setX(x);
				slot[index].setY(y);
				Game.entities.add(slot[index]);
				slot[index] = null;
				itens[index] = null;
				Sound.Clips.dropAndGetItem.play();
			}
		
	}
	
	public int qtSlotsInv() { //verifica quantos espaços possuem 
		
		int slots = 0;
		
		for(int i=0; i<Game.sysInv.inventario.length; i++) {
			if(Game.sysInv.inventario[i] == null) {
				slots++;
			}
		}
		
		return slots;
	}
	
	public boolean CheckSlots() { //chegar se ainda a objetos no slot
		
		for(int i=0; i<slot.length; i++) {
			if(slot[i] != null) {
				return true;
			}
		}
		return false;
	}
	
	
	//Apenas retorna se está cheio ou não
	public boolean isFullInv() {
		
		for(int i=0; i<Game.sysInv.inventario.length; i++) {
			if(Game.sysInv.inventario[i] == null) {
				return false;
			}
		}
		
		return true;
	}
	
	//tenta pegar o craft e jogar no inventario
	public void checkGetCraft(int x, int y) {
	
		if(slot[indexCraft] != null) {
			if(qtSlotsInv() > 0) { //Se tem espaço na bag, em qualquer caso será adicionado ao inventario
								  //Caso não tenha espaço, item não empilhaveis serão dropados
				int index = Game.sysInv.checkPositionGetInv();
				if(!Game.sysInv.checkPackInv(slot[indexCraft])) {
					if (index >= 0 && index <= Game.sysInv.inventario.length) {
						Game.sysInv.inventario[index] = slot[indexCraft];
						Game.sysInv.inv[index] = slot[indexCraft].getSprite();
						Game.sysInv.handItem = Game.sysInv.inventario[index];
						Game.sysInv.handIndexItem = index;
					}
				}
			}else if(!Game.sysInv.checkPackInv(slot[indexCraft])) { //Tenta adicionar em um pack imcompleto, se não adicionar, dropa
				dropItem(x, y, indexCraft);
			}
		}
	}
	
	//Verifica se tem outro item alem os dos necessarios para fazer o craft nos slots
	//Se algum slot tiver um id diferente de algum dos index passados, retorna false
	public boolean checkCraft(int index1, int index2, int index3, int index4, int numSlot) {
		for(int i=0; i<slot.length-1;i++) {
			
			if(numSlot == 1) {
				if(index1 != i) {
					if(slot[i] != null && slot[i].id != slot[index1].id)
						return false;
				}
				
			}else if (numSlot == 2) {
				if(index1 != i && index2 != i) {
					if(slot[i] != null && slot[i].id != slot[index1].id && slot[i].id != slot[index2].id)
						return false;
				}
				
			}else if (numSlot == 3) {
				if(index1 != i && index2 != i && index3 != i) {
					if(slot[i] != null && slot[i].id != slot[index1].id && slot[i].id != slot[index2].id && slot[i].id != slot[index3].id)
						return false;
				}
				
			}else if (numSlot == 4) {
				if(index1 != i && index2 != i && index3 != i && index4 != i) {
					if(slot[i] != null && slot[i].id != slot[index1].id && slot[i].id != slot[index2].id && slot[i].id != slot[index3].id && slot[i].id != slot[index4].id)
						return false;
				}
			}
			
		}
		
		return true;
	}
	
	public void craftItem(ArrayList<Id> ids,
							int index1, int index2, int index3, int index4,
							int required1, int required2, int required3, int required4,
							BufferedImage img, int numSlot, int qtItem) {
		
		Entity item = null;
		
		if(checkCraft(index1, index2, index3, index4, numSlot)) {
			if(checkQtStuff(index1, index2, index3, index4, required1, required2, required3, required4, numSlot, qtItem)) {
			
				itens[indexCraft] = img;
				
				if(Game.player.clickCraft) {
					Game.player.clickCraft = false;
					Sound.Clips.creation.play();
					item = createItem(item, ids, numSlot, qtItem);
					if(item != null) {
						item.mapa.add(Game.mapaGame);
						item.regiao.add(Game.regiaoGame);
						slot[indexCraft] = item;
						useResourseToCraft(index1, index2, index3, index4, required1, required2, required3, required4, numSlot);
						checkGetCraft(Game.player.getX(), Game.player.getY());
					}
					
					slot[indexCraft] = null;
					
				}				
			}else {
				indexCraftNull();
			}
		}else {
			indexCraftNull();
		}
	}
	
	public void indexCraftNull() {
		slot[indexCraft] = null;
		itens[indexCraft] = null;
	}
	
	public void useResourseToCraft(int index1, int index2, int index3, int index4,
									int required1, int required2, int required3, int required4,
									int numSlot) {
		
		if(numSlot == 1) {
			removeResource(index1, required1);
	
		}else if(numSlot == 2) {
			removeResource(index1, required1);
			removeResource(index2, required2);
			
		}else if(numSlot == 3) {
			removeResource(index1, required1);
			removeResource(index2, required2);
			removeResource(index3, required3);
			
		}else if(numSlot == 4) {	
			removeResource(index1, required1);
			removeResource(index2, required2);
			removeResource(index3, required3);
			removeResource(index4, required4);
		}
		
	}
	
	public void removeResource(int index, int required) {
		
		if(slot[index].itensPack.size() >= required) { //Verifica se irá usar todo o recurso ou sobrará itens na lista
			int i=0;
			while(i<required) {
				slot[index].itensPack.remove(0);
				i++;
			}
		}else { //Se a quantidade de recurso requerida for igual ao item mais os itens da lista, apenas remove tudo
			removeSlotAndItens(index);
		}
	}
	
	public void removeSlotAndItens(int index) {
		slot[index] = null;
		itens[index] = null;
	}
	
	//Retorna se o indice do objeto necessário se estiver na aba de craft
	public int checkStuff(Id id, ArrayList<Id> ids) {
		
		for(int i=0; i<slot.length-1; i++) {
			if(slot[i] != null) {
				if(slot[i].id == id) {
					if(i != Game.createItem.index1 &&
						i != Game.createItem.index2 &&
						i != Game.createItem.index3 &&
						i != Game.createItem.index4) {
							ids.add(id);
							return i;
						}
					}
			}
		}
		
		return -1;
	}
	
	//Retorna o index do slot, caso algum dos ids da lista for igual ao do slot
	public int checkStuffList(ArrayList<Id> idsOneIndex, ArrayList<Id> ids) {
		
		for(int i=0; i<slot.length-1; i++) {
			if(slot[i] != null) {
				if(idsOneIndex.contains(slot[i].id)) {
					ids.add(slot[i].id);
					return i;
				}
			}
		}
		
		return -1;
	}
	
	//retorna se a quantidade de iten é suficiente pra fazer o craft
	public boolean checkQtStuff(int index1, int index2, int index3, int index4,
								int required1, int required2, int required3, int required4,
								int numSlot, int qtItem) {

		if(numSlot == 1) {
			if(index1 >= 0) {
				if((slot[index1].itensPack.size()+1) >= required1) {
					return true;
				}
			}
			
		}else if(numSlot == 2) {
			if(index1 >= 0 && index2 >= 0) {
				if((slot[index1].itensPack.size()+1) >= required1 &&
						(slot[index2].itensPack.size()+1) >= required2) {
					return true;
				}
			}
			
		}else if(numSlot == 3) {
			if(index1 >= 0 && index2 >= 0 && index3 >= 0) {
				if((slot[index1].itensPack.size()+1) >= required1 &&
						(slot[index2].itensPack.size()+1) >= required2 &&
						(slot[index3].itensPack.size()+1) >= required3) {
					return true;
				}
			}
			
		}else if(numSlot == 4) {
			if(index1 >= 0 && index2 >= 0 && index3 >= 0 && index4 >= 0) {
				if((slot[index1].itensPack.size()+1) >= required1 &&
						(slot[index2].itensPack.size()+1) >= required2 &&
						(slot[index3].itensPack.size()+1) >= required3 &&
						(slot[index4].itensPack.size()+1) >= required4) {
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	public boolean checkPackCreation(Entity atual) {
		
		if(atual != null && atual.pack) {
			if(atual.itensPack.size() == 0) {
				for(int i=0; i<slot.length-1; i++) {
					if(slot[i] != null) {
						if(atual.id == slot[i].id &&
						slot[i].itensPack.size() < slot[i].qtPack){
							for(int k=0; k<Game.sysInv.inventario.length; k++) {
								if(Game.sysInv.inventario[k] == atual) {
									Game.sysInv.inv[k] = null;
									Game.sysInv.inventario[k] = null;
								}
							}
							
							slot[i].itensPack.add(atual);
							return true;
						}
					}
				}
			}
			else if (atual.itensPack.size() > 0 &&  
			atual.itensPack.size() < atual.qtPack){
				for(int i=0; i<slot.length-1; i++) {
					if(slot[i] != null) {
						if(atual.id == slot[i].id && slot[i].itensPack.size() < slot[i].qtPack) {
							if((slot[i].itensPack.size() + atual.itensPack.size() + 1) <= slot[i].qtPack){
								
								for(int k=0; k<Game.sysInv.inventario.length; k++) {
									if(Game.sysInv.inventario[k] == atual) {
										Game.sysInv.inv[k] = null;
										Game.sysInv.inventario[k] = null;
									}
								}
								
								if(atual.itensPack.size() > 0) {
									slot[i].itensPack.addAll(atual.itensPack);
									atual.itensPack.removeAll(atual.itensPack);
								}
								slot[i].itensPack.add(atual);
								Game.entities.remove(atual);
								return true;
								
							}else {

								if(atual.itensPack.size() > 0) {
							
									int completa = 0;

									completa = slot[i].qtPack - slot[i].itensPack.size(); 
				
									for(int j = 0; j < atual.itensPack.size(); j++) {
										if(j < completa) {
											slot[i].itensPack.add(atual.itensPack.get(j));
										}
									}
					
									int cont = 0;
	
									while (cont < completa) {
											atual.itensPack.remove(0);
											cont++;
									}	
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public Entity createItem(Entity item, ArrayList<Id> ids, int numSlot, int qtItem) {
		
		if(numSlot == 1) {
			
			if (ids.contains(Id.ID_STONE)) {
				item = new Oven(0, 0, 16, 16, Entity.FORNO_DESLIGADO_EN);
				item.tipo = "forno";
				
			}else if (ids.contains(Id.ID_BLOCK_WOOD)) {
				item = new ProcessedWood(0, 0, 16, 16, Entity.MADEIRA_PROCESSADA_EN);
				
				for(int i=0; i< qtItem-1; i++) {
					Entity e = new ProcessedWood(0, 0, 16, 16, Entity.MADEIRA_PROCESSADA_EN);
					item.itensPack.add(e);
				}
				item.tipo = "madeira processada";
				
			}else if (ids.contains(Id.ID_SULFOR)) {
				item = new ProcessedSulfor(0, 0, 16, 16, Entity.ENXOFRE_PROCESSADO_EN);
				
				for(int i=0; i< qtItem-1; i++) {
					Entity e = new ProcessedSulfor(0, 0, 16, 16, Entity.ENXOFRE_PROCESSADO_EN);
					item.itensPack.add(e);
				}
				item.tipo = "enxofre processado";
				
			}else if (ids.contains(Id.ID_POTASSIUM_NITRATE)) {
				item = new ProcessedPotassiumNitrate(0, 0, 16, 16, Entity.NITRATO_DE_POTASIO_PROCESSADO_EN);
				
				for(int i=0; i< qtItem-1; i++) {
					Entity e = new ProcessedPotassiumNitrate(0, 0, 16, 16, Entity.NITRATO_DE_POTASIO_PROCESSADO_EN);
					item.itensPack.add(e);
				}
				item.tipo = "nitrato de potásio processado";
				
			}else if(ids.contains(Id.ID_FIREWOOD_OAK)||ids.contains(Id.ID_FIREWOOD_PINE) || ids.contains(Id.ID_FIREWOOD_WILLOW)) {
				item = new BlockWood(0, 0, 16, 16, Entity.BLOCO_DE_MADEIRA_EN);
				item.tipo = "bloco de madeira";
				
			}
			
		}else if (numSlot == 2) {
			
			if((ids.contains(Id.ID_SEED_OAK) && ids.contains(Id.ID_ROOT))) {
				item = new Potion(0, 0, 16, 16, Entity.POTION_EN);
				item.tipo = "poção de regeneração";
				
			}else if((ids.contains(Id.ID_GUNPOWDER) && ids.contains(Id.ID_BAR_SILVER))) {
				item = new Bomb(0, 0, 16, 16, Entity.BOMBA_EN);
				item.tipo = "bomba";
				
			}
			
			if((ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BLOCK_WOOD)) && checkQtSlot() == 2) {
				item = new Axe(0, 0, 16, 16, Entity.AXE_WOOD_EN);
				item.tipo = "machado de madeira";
				
			}else if((ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BAR_COOPER)) && checkQtSlot() == 2) {
				item = new Axe(0, 0, 16, 16, Entity.AXE_COOPER_EN);
				item.tipo = "machado de cobre";
				
			}else if((ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BAR_SILVER)) && checkQtSlot() == 2) {
				item = new Axe(0, 0, 16, 16, Entity.AXE_SILVER_EN);
				item.tipo = "machado de prata";
				
			}else if((ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BAR_GOLD)) && checkQtSlot() == 2) {
					item = new Axe(0, 0, 16, 16, Entity.AXE_GOLD_EN);
					item.tipo = "machado de ouro";
					
			}else if((ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_DIAMOND)) && checkQtSlot() == 2) {
				item = new Axe(0, 0, 16, 16, Entity.AXE_DIAMOND_EN);
				item.tipo = "machado de diamante";
					
			}else {
				Game.player.clickCraft = true;
			}
			
		}else if(numSlot == 3) {
			
			if(((ids.contains(Id.ID_FIREWOOD_OAK) || ids.contains(Id.ID_FIREWOOD_PINE) || ids.contains(Id.ID_FIREWOOD_WILLOW)) 
					&& ids.contains(Id.ID_SULFOR) && ids.contains(Id.ID_PHOSPHOR))) {
				item = new Bonfire(0, 0, 16, 16, Entity.FOGUEIRA_EN);
				item.tipo = "fogueira";
				
			}else if(ids.contains(Id.ID_CHARCOAL) && ids.contains(Id.ID_PROCESSED_SULFOR) && ids.contains(Id.ID_PROCESSED_POTASSIUM_NITRATE)) {
				item = new Gunpowder(0, 0, 16, 16, Entity.POLVORA_EN);
				item.tipo = "pólvora";
				
			}
			
			if(ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BLOCK_WOOD) && checkQtSlot() == 3) {
				item = new Pickaxe(0, 0, 16, 16, Entity.PICARETA_MADEIRA_EN);
				item.tipo = "picareta de madeira";
				
			}else if(ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BAR_COOPER) && checkQtSlot() == 3) {
				item = new Pickaxe(0, 0, 16, 16, Entity.PICARETA_COBRE_EN);
				item.tipo = "picareta de cobre";
				
			}if(ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BAR_SILVER) && checkQtSlot() == 3) {
				item = new Pickaxe(0, 0, 16, 16, Entity.PICARETA_PRATA_EN);
				item.tipo = "picareta de prata";
				
			}if(ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_BAR_GOLD) && checkQtSlot() == 3) {
				item = new Pickaxe(0, 0, 16, 16, Entity.PICARETA_OURO_EN);
				item.tipo = "picareta de ouro";
				
			}if(ids.contains(Id.ID_PROCESSED_WOOD) && ids.contains(Id.ID_DIAMOND) && checkQtSlot() == 3) {
				item = new Pickaxe(0, 0, 16, 16, Entity.PICARETA_DIAMANTE_EN);
				item.tipo = "picareta de diamante";
				
			}else {
				Game.player.clickCraft = true;
			}
			
		}else if(numSlot == 4) {
			
			
		}
		
		return item;
		
	}
	
	public int checkQtSlot() {
		
		int qt = 0;
		
		for(int i=0; i<slot.length; i++) {
			if(slot[i] != null) {
				qt++;
			}
		}
		
		return qt;
	}

}
