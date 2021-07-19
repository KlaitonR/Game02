package util;

import java.awt.image.BufferedImage;
import entities.Entity;
import main.Game;
import main.Sound;

public class SystemBag {

	public Entity bagpack[][] = new Entity[4][6]; //4x6
	public BufferedImage [][] bag = new BufferedImage[4][6];
	public int[] clickSelectIndexBag;
	
	//pega os indexs do Item que foi clicado na mochila
		public void checkClickPositionItemBag(int[] index) {
			clickSelectIndexBag = index;
		}
	
	//verifica a primeira posição vazia na mochila
		public int[] checkPositionPutBag() {
			
			int [] index = {-1, -1};
			
				for(int j=0; j<6; j++) {
					for(int i=0; i<4; i++) {
					if(bag[i][j] == null) {
						index[0] = i;
						index[1] = j;
						break;
					}	
				}
					
				if(index[0] != -1 && index[1] != -1) 
					break;
			}
				
			return index;
		}
		
		public void getItemBag() {
			
			int i, j = -1;
			int [] index = clickSelectIndexBag; // armazena a posição do intem selecionado
			
//			System.out.println(index[0] + "  " + index[1]);
			
			i = index[0]; // retorna as posições da matriz em um vetor
			j = index[1];
			
			if(bagpack[j][i] != null) {
				
				int indexInv = Game.sysInv.checkPositionGetInv();
				
				if(!Game.sysInv.checkPackInv(bagpack[j][i])) {
				
					//verifica se o inventario não está cheio e o player estiver com o espaço do inventario selecionado para a mão
					if((indexInv >= 0 && indexInv < Game.sysInv.inventario.length)) {
						Game.sysInv.inventario[indexInv] = bagpack[j][i];
						Game.sysInv.handItem = bagpack[j][i];
						Game.sysInv.inv[indexInv] = bag[j][i];
						Game.sysInv.handIndexItem = indexInv;
						bagpack[j][i] = null;
						bag[j][i] = null;
						Sound.Clips.dropAndGetItem.play();
					}
				}
			}
		}
		
		public void checkItemBag() {
			
			boolean lighterInv = false;
			boolean lighterBug =  false;
			
			for(int i=0; i<4; i++) {
				for(int j=0; j<6; j++) {
					if(bagpack[i][j] != null && bagpack[i][j].tipo.equals("isqueiro"))
						lighterBug = true;
				}
			}
			
			for(int i=0;i<5;i++) {
				if(Game.sysInv.inventario[i] != null && Game.sysInv.inventario[i].tipo.equals("isqueiro"))
					lighterInv = true;
			}
			
			if(lighterBug && !lighterInv)
				Game.player.useLighter = false;
		}
	
	public boolean checkPackBag(Entity atual) {
		
		if(atual.pack) {
			if(atual.itensPack.size() == 0) {
				for(int i=0; i<4; i++) {
					for(int j=0; j<6; j++) {
						if(bagpack[i][j] != null) {
							if(atual.id == bagpack[i][j].id &&
								bagpack[i][j].itensPack.size() < bagpack[i][j].qtPack){
								
								for(int k=0; k<Game.sysInv.inventario.length; k++) {
									if(Game.sysInv.inventario[k] == atual) {
										Game.sysInv.inventario[k] = null;
										Game.sysInv.inv[k] = null;
									}
								}
								
								bagpack[i][j].itensPack.add(atual);
								
								return true;
							}
						}
					}
				}
			}
			else if (atual.itensPack.size() > 0 &&   // Se o pack tiver  lista maior q zero e menor que o qtPack do inventario
			atual.itensPack.size() < atual.qtPack){
				for(int i=0; i<4; i++) {
					for(int j=0; j<6; j++) {
						if(bagpack[i][j] != null) {
							//Verifica se o item do inventario e da bag possuem mesmo id e se o pack da bag não está completo
							if(atual.id == bagpack[i][j].id && bagpack[i][j].itensPack.size() < bagpack[i][j].qtPack) {
								//verifica se a soma do pack do chão e do pack do inventario +1 (o próprio pack do chão)
								//supera a qtPack do inventario
								//Se não superar, apenas adiciona os items da lista do pack do chão ao pack do inventario
								if((bagpack[i][j].itensPack.size() + atual.itensPack.size() + 1) <= bagpack[i][j].qtPack){
									
									for(int k=0; k<Game.sysInv.inventario.length; k++) {
										if(Game.sysInv.inventario[k] == atual) {
											Game.sysInv.inventario[k] = null;
											Game.sysInv.inv[k] = null;
										}
									}
									
									if(atual.itensPack.size() > 0) {
										bagpack[i][j].itensPack.addAll(atual.itensPack);
										atual.itensPack.removeAll(atual.itensPack);
									}
									
									bagpack[i][j].itensPack.add(atual);
								
									return true;
									
								}else { // Se superar, calcula os itens que sobrarão
									
									//verifica se o pack é apenas o item, ou se tem algo na lista
									//Se não tiver nada na lista, apenas ignora e retorna false
									if(atual.itensPack.size() > 0) {
										
										int completa = 0;
										//Calcula quantos item completaram o pack do inventario
										completa = bagpack[i][j].qtPack - bagpack[i][j].itensPack.size(); 
										
										//adicionando os itens do pack da bag ao pack do inventario
										for(int k = 0; k < atual.itensPack.size(); k++) {
											if(k < completa) {
												bagpack[i][j].itensPack.add(atual.itensPack.get(k));
											}
										}
										
										//removendo os itens do pack do bag que foram pegos do pack do bag para o pack do inventario
										int cont = 0;
										
										//removendo os itens do pack do bag que foram pegos do pack do bag para o pack do inventario
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
		}
		return false;
	}
	
}
