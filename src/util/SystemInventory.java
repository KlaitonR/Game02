package util;

import java.awt.image.BufferedImage;

import entities.Entity;
import entities.itens.Axe;
import entities.itens.FishingRod;
import entities.itens.Hoe;
import entities.itens.Lighter;
import entities.itens.Pickaxe;
import entities.itens.Wapon;
import main.Game;
import main.Sound;

public class SystemInventory {
	
	public Entity inventario[] = new Entity[5];
	public BufferedImage [] inv = new BufferedImage[5];
	public int clickSelectIndexInv;
	public Entity handItem;
	public int handIndexItem;
	public boolean scrollItemLef;
	public boolean scrollItemDir;
	
	//pega o index do Item que foi clicado no inventario
	public void checkClickPositionItemInv(int index) {
		clickSelectIndexInv = index;
	}
	
	//verificar posição vazia do inventario
		public int checkPositionGetInv() {
				
			int index = -1;
				
			for(int i=0; i<inv.length; i++) {
				if(inv[i] == null) {
					index = i;
					break;
				}
			}

			return index;
		}
		
		public void checkScrollItem(int handIndexItem) {
			
			if(handIndexItem - 1 >= 0) {

				int index = handIndexItem-1;
				while(index > 0 && inventario[index] == null){
					index--;
				}
					
				if(index<0)
					index++;
			
				if(inventario[index] != null) {
					handItem = inventario[index];
					handIndexItem = index;
				}
			
			}else {
				
				int index = inventario.length-1;
				do {
					index--;
				}while(index > 0 && inventario[index] == null); 
				
				if(inventario[index] != null) {
					handItem = inventario[index];	
					this.handIndexItem = index;
				}else {
					handItem = null;
					handIndexItem = 0;
				}
			}
		}
		
		public void checkDropItem() {
			
			if(Game.player.dropItem) {
				
				Game.player.dropItem = false;
				int hi = handIndexItem; //Estas variaveis locais evitam que os valores sejam alocados nas posiõs erradas do vetor
				
				if(handItem != null){
					checkScrollItem(hi); // Método que modificará os atributos do Player
					if(inventario[hi] != null) {
						
						//largar o objeto no chão
						inventario[hi].setX(Game.player.x);
						inventario[hi].setY(Game.player.y);
						inventario[hi].show =  true;
						Game.entities.add(inventario[hi]);	
						inventario[hi].clear = true;
//						Game.world.tiles[inventario[hi].xTile + (inventario[hi].yTile*Game.world.WIDTH)].en = inventario[hi];
						
						checkInventoryItemDropMap(inventario[hi]);
						
						//verificações de itens 
						if(inventario[hi] instanceof Lighter) {
							Game.player.useLighter = false;
						}
						
						//retira do inventario e da mão do player
						inventario[hi] = null;
						inv[hi] = null;
						handIndexItem = hi;
						handItem = inventario[hi];
						Sound.Clips.dropAndGetItem.play();
					}
				}
			}
		}
		
		public void checkInventoryItemDropMap(Entity e) {
			
			e.mapa.clear();
			e.regiao.clear();
			e.mapa.add(Game.mapaGame);
			e.regiao.add(Game.regiaoGame);
			
			if(!e.itensPack.isEmpty()) {
				for(int i=0; i<e.itensPack.size();i++) {
					e.itensPack.get(i).mapa.clear();
					e.itensPack.get(i).regiao.clear();
					e.itensPack.get(i).mapa.add(Game.mapaGame);
					e.itensPack.get(i).regiao.add(Game.regiaoGame);
				}
			}
			
		}
		
		public void checkScrollItem() {
			
			if(scrollItemLef) {
				scrollItemLef = false;
				Sound.Clips.selectedInventory.play();
				
				if(handIndexItem - 1 >=0) {
					handIndexItem--;
					handItem = inventario[handIndexItem];
					
				}else {
					handIndexItem = inventario.length - 1;
					handItem = inventario[handIndexItem];
				}
				
			}else if(scrollItemDir) {
				scrollItemDir =  false;
				Sound.Clips.selectedInventory.play();
				
				if(handIndexItem < inventario.length - 1) {
					handIndexItem++;
					handItem = inventario[handIndexItem];
					
				}else {
					handIndexItem = 0;
					handItem = inventario[handIndexItem];
				}
			}
		}
		
		public void checkHasItem() {
			
			if(handItem instanceof Wapon && inventario[handIndexItem] instanceof Wapon) {
				Game.player.hasGun = true;
			}else {
				Game.player.hasGun = false;
			}
			
			if(handItem instanceof Axe && inventario[handIndexItem] instanceof Axe) {
				Game.player.hasAxe = true;
			}else {
				Game.player.hasAxe = false;
			}
			
			if(handItem instanceof FishingRod && inventario[handIndexItem] instanceof FishingRod) {
				Game.player.hasFishingRod = true;
			}else {
				Game.player.hasFishingRod = false;
			}
			
			if(handItem instanceof Hoe && inventario[handIndexItem] instanceof Hoe) {
				Game.player.hasHoe = true;
			}else {
				Game.player.hasHoe = false;
			}
			
			if(handItem instanceof Pickaxe && inventario[handIndexItem] instanceof Pickaxe) {
				Game.player.hasPickaxe = true;
			}else {
				Game.player.hasPickaxe = false;
			}
			
		}
		
		public void addItemCreation() {
			
			if(inventario[clickSelectIndexInv] != null) {
				if(!Game.sysCre.checkPackCreation(inventario[clickSelectIndexInv])) {
					
					int indexCreation = Game.sysCre.indexSlotNull();
					
					if(indexCreation >= 0 && indexCreation < Game.sysCre.slot.length) {
						Game.sysCre.addItem(inventario[clickSelectIndexInv]);
						handItem = null;
						inv[clickSelectIndexInv] = null;
						inventario[clickSelectIndexInv] = null;
					}
				}
			}
		}
	
		//chegar se já existe um pack no inventario do item que estiver pegando
		public boolean checkPackInv(Entity atual) {
		
		if(atual != null && atual.pack) {
			if(atual.itensPack.size() == 0) {
				for(int i=0; i<inventario.length; i++) {
					if(inventario[i] != null) {
						if(atual.id == inventario[i].id &&
							inventario[i].itensPack.size() < inventario[i].qtPack){
							
							//verifica se o item não está na mochila
							for(int k=0; k<4; k++) {
								for(int l=0; l<6; l++) {
									if(Game.sysBag.bagpack[k][l] == atual) {
										Game.sysBag.bagpack[k][l] = null;
										Game.sysBag.bag[k][l] = null;
									}
								}
							}
							
							//verifica se o item não está no creation
							for(int m=0; m<Game.sysCre.slot.length; m++) {
								if(Game.sysCre.slot[m] == atual) {
									Game.sysCre.slot[m] = null;
									Game.sysCre.itens[m] = null;
								}
							}
							
							inventario[i].itensPack.add(atual);
							Game.entities.remove(atual);
							return true;
						}
					}
				}
			}
			else if (atual.itensPack.size() > 0 &&   // Se o pack tiver  lista maior q zero e menor que o qtPack do inventario
			atual.itensPack.size() < atual.qtPack){
				for(int i=0; i<inventario.length; i++) {
					if(inventario[i] != null) {
						//verifica se o id do pack do chão é o mesmo id do pack do inventario
						// e se o pack do inventario já está cheio
						if(atual.id == inventario[i].id && inventario[i].itensPack.size() < inventario[i].qtPack) {
							//verifica se a soma do pack do chão e do pack do inventario +1 (o próprio pack do chão)
							//supera a qtPack do inventario
							//Se não superar, apenas adiciona os items da lista do pack do chão ao pack do inventario
							if((inventario[i].itensPack.size() + atual.itensPack.size() + 1) <= inventario[i].qtPack){
								
								//verifica se o item não está na mochila
								for(int k=0; k<4; k++) {
									for(int l=0; l<6; l++) {
										if(Game.sysBag.bagpack[k][l] == atual) {
											Game.sysBag.bagpack[k][l] = null;
											Game.sysBag.bag[k][l] = null;
										}
									}
								}
								
								//verifica se o item não está no creation
								for(int m=0; m<Game.sysCre.slot.length; m++) {
									if(Game.sysCre.slot[m] == atual) {
										Game.sysCre.slot[m] = null;
										Game.sysCre.itens[m] = null;
									}
								}
								
								if(atual.itensPack.size() > 0) {
									inventario[i].itensPack.addAll(atual.itensPack);
									atual.itensPack.removeAll(atual.itensPack);
								}
								inventario[i].itensPack.add(atual);
								Game.entities.remove(atual);
								return true;
								
							}else { // Se superar, calcula os itens que sobraram
								
								//verifica se o pack é apenas o item, ou se tem algo na lista
								//Se não tiver nada na lista, apenas ignora e retorna false
								if(atual.itensPack.size() > 0) {
							
									int completa = 0;
									//Calcula quantos item completaram o pack do inventario
									completa = inventario[i].qtPack - inventario[i].itensPack.size(); 
					
									//adicionando os itens do pack no chão ao pack do inventario
									for(int j = 0; j < atual.itensPack.size(); j++) {
										if(j < completa) {
											inventario[i].itensPack.add(atual.itensPack.get(j));
										}
									}
					
									int cont = 0;
	
									//removendo os itens do pack do chão que foram pegos do pack do chão para o pack do inventario
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

	public void putItemBag() {
		
		int i, j = -1;
		int [] index = Game.sysBag.checkPositionPutBag(); // chegar a posição do primeiro espaço vazio que tiver na mochila
	
		i = index[0]; // retorna as posições da matriz em um vetor
		j = index[1];
		
		//verifica se na posição selecionada do inv não está vazia, e se a mochila está cheia
		if(inventario[clickSelectIndexInv] != null && i < 4 && j < 6 && i >= 0 && j >= 0) {
			
			if(!Game.sysBag.checkPackBag(inventario[clickSelectIndexInv])) {
				//Joga o item para a mochila
				Game.sysBag.bagpack[i][j] = inventario[clickSelectIndexInv];
				Game.sysBag.bag[i][j] = inv[clickSelectIndexInv];
				Sound.Clips.dropAndGetItem.play();
				
				//Se o Item que foi selecionado com o click estiver na mão, retira da mão e do inventario
				if(inventario[clickSelectIndexInv].equals(handItem) && clickSelectIndexInv == handIndexItem) {
					handItem = null;
					inventario[clickSelectIndexInv] = null;
					inv[clickSelectIndexInv] = null;
				}else { // se não, apenas retira do inventario
					inventario[clickSelectIndexInv] = null;
					inv[clickSelectIndexInv] = null;
				}
			}
		}
	}
	
	public void checkInventoryItemMap() {
		
		if(inventario != null) {
			for(int i=0; i<inventario.length;i++) {
				if(inventario[i] != null) {
					if(!inventario[i].mapa.contains(Game.mapaGame)) {
						inventario[i].mapa.add(Game.mapaGame);
						inventario[i].regiao.add(Game.regiaoGame);
						if(!inventario[i].itensPack.isEmpty()) {
							for(int y=0; y<inventario[i].itensPack.size();y++) {
								inventario[i].itensPack.get(y).mapa.add(Game.mapaGame);
								inventario[i].itensPack.get(y).regiao.add(Game.regiaoGame);
							}
						}
					}
				}
			}
		}
		
	}
	
}
