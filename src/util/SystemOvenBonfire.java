package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Entity;
import entities.itens.Charcoal;
import entities.itens.CooperBar;
import entities.itens.GoldBar;
import entities.itens.SilverBar;
import main.Game;
import main.Sound;

public class SystemOvenBonfire {

	public BufferedImage imageSlot[] = new BufferedImage[3];
	public Entity slot [] = new Entity[3];
	public final int indexCraft = 2;
	public int clickSelectIndexOvenBonfire;
	
	//pega o index do Item que foi clicado na janela de criação
		public void checkClickPositionOvenBonfire(int index) {
			clickSelectIndexOvenBonfire = index;
		}
		
		public void addItem(Entity item) {
			
			if(isNull()) {
				int i = indexSlotNull();
				if(i<slot.length-1) {
					slot[i] = item;
					imageSlot[i] = item.getSprite();
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
						imageSlot[indexRemove] = null;
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
		
		public void closeOvenBonfire(int x, int y) {
			
			if(CheckSlots()){ // verifica se possui itens na aba do forno/fogueira
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
			
			slot[2] = null;
			imageSlot[2] = null;

		}
		
		public void dropItens(int x, int y) { //Dropa todos os itens da aba do forno/fogueira
			
			for(int i=0; i<slot.length; i++) {
				if(slot[i]!=null) {
					slot[i].setX(x);
					slot[i].setY(y);
					slot[i].show = true;
					Game.entities.add(slot[i]);
					slot[i] = null;
					imageSlot[i] = null;
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
					imageSlot[index] = null;
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
		public boolean checkCraft(int index1, int index2) {
			
			for(int i=0; i<slot.length-1;i++) {
				if(index1 >=0 && index2 >=0) {
					if(index1 != i && index2 != i) {
						if(slot[i] != null && slot[i].id != slot[index1].id && slot[i].id != slot[index2].id)
							return false;
					}
				}else if(index1 >=0) {
					if(index1 != i) {
						if(slot[i] != null && slot[i].id != slot[index1].id)
							return false;
					}
				}
			}
			
			return true;
		}
		
		public void craftItem(ArrayList<Id> ids, int index1, int index2, int required1, int required2, BufferedImage img, int qtItem) {
			
			Entity item = null;
			
			if(checkCraft(index1, index2)) {
				if(checkQtStuff(index1, index2, required1, required2, qtItem)) {
				
					imageSlot[indexCraft] = img;
					
					if(Game.player.clickCraftOvenBonfire) {
						Game.player.clickCraftOvenBonfire = false;
						item = createItem(item, ids, qtItem);
						item.mapa.add(Game.mapaGame);
						item.regiao.add(Game.regiaoGame);
						slot[indexCraft] = item;
						useResourseToCraft(index1, index2, required1, required2);
						checkGetCraft(Game.player.getX(), Game.player.getY());
						slot[indexCraft] = null;
						Sound.Clips.creation.play();
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
			imageSlot[indexCraft] = null;
		}
		
		public void useResourseToCraft(int index1, int index2, int required1, int required2) {
			
			removeResource(index1, required1);
			removeResource(index2, required2);
		}
		
		public void removeResource(int index, int required) {
			
			if(index >=0) {
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
		}
		
		public void removeSlotAndItens(int index) {
			slot[index] = null;
			imageSlot[index] = null;
		}
		
		//Retorna se o indice do objeto necessário se estiver na aba de craft
		public int checkStuff(Id id, ArrayList<Id> ids) {
			
			for(int i=0; i<slot.length-1; i++) {
				if(slot[i] != null) {
					if(slot[i].id == id) {
						ids.add(id);
						return i;
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
		public boolean checkQtStuff(int index1, int index2, int required1, int required2, int qtItem) {

			if(index1 >= 0 && index2 >= 0) {
				if((slot[index1].itensPack.size()+1) >= required1 &&
						(slot[index2].itensPack.size()+1) >= required2) {
					return true;
				}
			}else if(index1 >= 0) {
				if((slot[index1].itensPack.size()+1) >= required1) {
					return true;
				}
			}
			
			return false;
		}
		
		public boolean checkPackOvenBonfire(Entity atual) {
			
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
		
		public Entity createItem(Entity item, ArrayList<Id> ids, int qtItem) {
			
			if(((ids.contains(Id.ID_FIREWOOD_OAK) || ids.contains(Id.ID_FIREWOOD_PINE) || ids.contains(Id.ID_FIREWOOD_WILLOW)))) {
				item = new Charcoal(0, 0, 16, 16, Entity.CARVAO_VEGETAL_EN);
				item.tipo = "cavão vegetal";
				
			}
			
			if(((ids.contains(Id.ID_FIREWOOD_OAK) || ids.contains(Id.ID_FIREWOOD_PINE) || ids.contains(Id.ID_FIREWOOD_WILLOW))) &&
					ids.contains(Id.ID_ORE_SILVER)) {
				item = new SilverBar(0, 0, 16, 16, Entity.BARRA_PRATA_EN);
				item.tipo = "barra de prata";
				
			}
			
			if(((ids.contains(Id.ID_FIREWOOD_OAK) || ids.contains(Id.ID_FIREWOOD_PINE) || ids.contains(Id.ID_FIREWOOD_WILLOW))) &&
					ids.contains(Id.ID_ORE_COPPER)) {
				item = new CooperBar(0, 0, 16, 16, Entity.BARRA_COBRE_EN);
				item.tipo = "barra de cobre";
				
			}
			
			if(((ids.contains(Id.ID_FIREWOOD_OAK) || ids.contains(Id.ID_FIREWOOD_PINE) || ids.contains(Id.ID_FIREWOOD_WILLOW))) &&
					ids.contains(Id.ID_ORE_GOLD)) {
				item = new GoldBar(0, 0, 16, 16, Entity.BARRA_OURO_EN);
				item.tipo = "barra de ouro";
				
			}
			
			return item;
			
		}

	
}
