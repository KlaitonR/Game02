package util;

import java.awt.image.BufferedImage;
import entities.Entity;
import entities.itens.Potion;
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
				Game.world.tiles[slot[i].xTile + (slot[i].yTile*Game.world.WIDTH)].en = slot[i];
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
				slot[index].show = true;
				Game.entities.add(slot[index]);
				Game.world.tiles[slot[index].xTile + (slot[index].yTile*Game.world.WIDTH)].en = slot[index];
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
	
	public boolean checkCraft(int index1, int index2) {
		
		for(int i=0; i<slot.length-1;i++) {
			if(index1 != i && index2 != i) {
				if(slot[i] != null && slot[i].id != slot[index1].id && slot[i].id != slot[index2].id)
					return false;
			}
		}
		
		return true;
	}
	
	public void craftItem(Id id1, Id id2, int required1, int required2) {
		
		int index1 = checkStuff(id1);
		int index2 = checkStuff(id2);
		
		if(index1 >= 0 && index2 >= 0) {
			if(checkCraft(index1, index2)) {
				if(checkQtStuff(index1, required1) && checkQtStuff(index2, required2)) {
					
					Entity item = null;
					
					if((id1==Id.ID_SEED_OAK && id2==Id.ID_ROOT) || (id1==Id.ID_ROOT && id2==Id.ID_SEED_OAK)) {
						item = new Potion(0, 0, 16, 16, Entity.POTION_EN);
						item.tipo = "Poção de regeneração";
					}
					
					slot[indexCraft] = item;
					itens[indexCraft] = item.getSprite();
					
					if(Game.player.clickCraft) {
						
						Game.player.clickCraft = false;
						Sound.Clips.creation.play();
						
						if(slot[index1].itensPack.size()>=required1) {
							int i=0;
							while(i<required1) {
								slot[index1].itensPack.remove(0);
								i++;
							}
						}else {
							slot[index1] = null;
							itens[index1] = null;
						}
						
						if(slot[index2].itensPack.size()>=required2) {
							int i=0;
							while(i<required2) {
								slot[index2].itensPack.remove(0);
								i++;
							}
						}else {
							slot[index2] = null;
							itens[index2] = null;
						}
						
						checkGetCraft(Game.player.getX(), Game.player.getY());
					}
				}else {
					indexCraftNull();
				}
			}else {
				indexCraftNull();
			}
		}else {
			indexCraftNull();
		}
	}
	
	private void indexCraftNull() {
		slot[indexCraft] = null;
		itens[indexCraft] = null;
	}
	
	//Retorna se o indice do objeto necessário se estiver na aba de craft
	public int checkStuff(Id id) {
		
		for(int i=0; i<slot.length-1; i++) {
			if(slot[i] != null) {
				if(slot[i].id == id) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	//retorna se a quantidade de iten é suficiente pra fazer o craft
	public boolean checkQtStuff(int index, int required ) {

		if(index >= 0) {
			if((slot[index].itensPack.size()+1) >= required)
				return true;
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

}
