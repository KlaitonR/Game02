package util;

import java.awt.image.BufferedImage;
import entities.Entity;
import entities.Oven;
import entities.itens.Axe;
import entities.itens.Potion;
import main.Game;
import main.Sound;

public class SystemCreation {
	
	public BufferedImage [] itens = new BufferedImage[5];
	public Entity slot [] = new Entity[5];
	public final int indexCraft = 4;
	public int clickSelectIndexCreation;
	
	//pega o index do Item que foi clicado na janela de cria��o
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
	
	//Verifica se todos os espa�os est�o vazios
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
	
	//Verifica se o slot de craft est� vazio
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
	
	//retorna o index do primeiro slot N�O nulo
	public int indexSlotNotNull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] != null)
				return i;
		}
		
		return -1;
	}
	
	public void closeCreation(int x, int y) {
		
		if(CheckSlots()){ // verifica se possui itens na aba de cria��o
			if(!isFullInv()) { //verifica se o inventario possui espa�o ou est� cheio
				
				int slotsInv = qtSlotsInv(); //verifica quantas posi��es est�o vazias
				
				if(slotsInv > 0) {//Verifica se possuei pelo menos 1 posi��o vazia
					
					while(slotsInv > 0) { //enquanto possuir posi��es vazias
						
						int indexRemove = indexSlotNotNull(); 
						if(indexRemove >= 0)
							removeItem(indexRemove); //remove da aba de cria��o para o inventario (j� faz a checagem de pack)
						
						slotsInv--;
					}
					
					if(CheckSlots()) { //se ainda possui objetos na aba de cria��o, dropa os itens
						dropItens(x, y);
					}
					
				}else { // dropa todos os itens restantes
					dropItens(x, y);
				}
			}else {
				//dropa cada item individualmente se n�o possue um pack imcompleto no inventario ou espa�o vazio
				for(int i=0; i<slot.length; i++) {
					if(!Game.sysInv.checkPackInv(slot[i])) {
						dropItem(x, y, i);
					}
				}
			}
		}
	}
	
	public void dropItens(int x, int y) { //Dropa todos os itens da aba cria��o
		
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
	
	public void dropItem(int x, int y, int index) { //Dropa um item especifico da aba cria��o
		
			if(slot[index] != null) {
				slot[index].setX(x);
				slot[index].setY(y);
				Game.entities.add(slot[index]);
				slot[index] = null;
				itens[index] = null;
				Sound.Clips.dropAndGetItem.play();
			}
		
	}
	
	public int qtSlotsInv() { //verifica quantos espa�os possuem 
		
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
	
	
	//Apenas retorna se est� cheio ou n�o
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
			if(qtSlotsInv() > 0) { //Se tem espa�o na bag, em qualquer caso ser� adicionado ao inventario
								  //Caso n�o tenha espa�o, item n�o empilhaveis ser�o dropados
				int index = Game.sysInv.checkPositionGetInv();
				if(!Game.sysInv.checkPackInv(slot[indexCraft])) {
					if (index >= 0 && index <= Game.sysInv.inventario.length) {
						Game.sysInv.inventario[index] = slot[indexCraft];
						Game.sysInv.inv[index] = slot[indexCraft].getSprite();
						Game.sysInv.handItem = Game.sysInv.inventario[index];
						Game.sysInv.handIndexItem = index;
					}
				}
			}else if(!Game.sysInv.checkPackInv(slot[indexCraft])) { //Tenta adicionar em um pack imcompleto, se n�o adicionar, dropa
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
	
	public void craftItem(Id id1, Id id2, Id id3, Id id4,
							int index1, int index2, int index3, int index4,
							int required1, int required2, int required3, int required4,
							BufferedImage img, int numSlot) {
		
		Entity item = null;
		
		if(checkCraft(index1, index2, index3, index4, numSlot)) {
			if(checkQtStuff(index1, index2, index3, index4, required1, required2, required3, required4, numSlot)) {
			
				itens[indexCraft] = img;
				
				if(Game.player.clickCraft) {
					Game.player.clickCraft = false;
					Sound.Clips.creation.play();
					item = createItem(item, id1, id2, id3, id4, numSlot);
					slot[indexCraft] = item;
					useResourseTocraft(index1, index2, index3, index4, required1, required2, required3, required4, numSlot);
					checkGetCraft(Game.player.getX(), Game.player.getY());
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
	
	public void useResourseTocraft(int index1, int index2, int index3, int index4,
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
		
		if(slot[index].itensPack.size() >= required) { //Verifica se ir� usar todo o recurso ou sobrar� itens na lista
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
	
	//Retorna se o indice do objeto necess�rio se estiver na aba de craft
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
	
	//retorna se a quantidade de iten � suficiente pra fazer o craft
	public boolean checkQtStuff(int index1, int index2, int index3, int index4,
								int required1, int required2, int required3, int required4,
								int numSlot) {

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
	
	public Entity createItem(Entity item, Id id1, Id id2, Id id3, Id id4, int numSlot) {
		
		if(numSlot == 1) {
			
			if (id1==Id.ID_STONE) {
				item = new Oven(0, 0, 16, 16, Entity.FORNO_EN);
				item.tipo = "forno";
			}
			
		}else if (numSlot == 2) {
			
			if((id1==Id.ID_SEED_OAK && id2==Id.ID_ROOT) || (id1==Id.ID_ROOT && id2==Id.ID_SEED_OAK)) {
				item = new Potion(0, 0, 16, 16, Entity.POTION_EN);
				item.tipo = "po��o de regenera��o";
				
			}else if ((id1==Id.ID_OVEN && id2==Id.ID_ORE_GOLD) || (id1==Id.ID_ORE_GOLD && id2==Id.ID_OVEN)) {
				item = new Axe(0, 0, 16, 16, Entity.AXE_EN);
				item.tipo = "machado";
			}
			
		}else if(numSlot == 3) {
			
		}else if(numSlot == 4) {
			
		}
		
		return item;
		
	}

}
