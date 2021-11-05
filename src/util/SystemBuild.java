package util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.Entity;
import entities.construction.Construction;
import entities.construction.Mine;
import entities.construction.MineLand;
import entities.construction.Thorn;
import main.Game;
import world.EntitySolid;
import world.Tile;

public class SystemBuild {
	
	public BufferedImage buildImage[] = new BufferedImage[18];
	public int index, xTarget, yTarget;
	public boolean building;
	public String message;
	public Construction construction;
	public BufferedImage constructionImage;
	private int frames, maxFrames = 180;
	public int psX=0, psY=0, psXplayer, psYplayer;
	
	public SystemBuild() {
		
		index = -1;
		buildImage[0] = Game.spriteContruction.getSprite(224, 0, 16, 16);
		buildImage[1] = Construction.THORN_LOCKED_EN;
		
	}
	
	public void tick() {
		
		psXplayer = (int)Game.player.getX();
		psYplayer = (int)Game.player.getY();
		
		checkIndex();
		checkConstruction();
		
		if(message != null) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				message = null;
			}
		}
		
		if(Mine.unlocked)
			buildImage[0] = Game.spriteContruction.getSprite(208, 0, 16, 16);
		else
			buildImage[0] = Game.spriteContruction.getSprite(224, 0, 16, 16);
		
		if(Thorn.unlocked)
			buildImage[1] = Construction.THORN_EN;
		else
			buildImage[1] = Construction.THORN_LOCKED_EN;
		
		if(MineLand.unlocked)
			buildImage[2] = Construction.MINE_LAND_EN;
		else
			buildImage[2] = Construction.MINE_LAND_LOCKED_EN;
		
	}
	
	public void checkIndex() {
		if(index >= 0) {
			building = true;
			Game.player.build = false;
			Game.ui.buttonEsc = true;
		}
	}
	
	public void checkConstruction() {
		
		if(Game.player.clickConstruction) {
			if(building) {
				if(checkMoney()) {
					verificaPosicaoMapa();
					if(Game.mapaGame.equals(Mapa.MAPA_FLORESTA)){
						if(index == 0) {
							if(!Mine.disable) { //verifica se já possui o recurso
								construction = new Mine(psX, psY, 32, 32, Construction.MINE_EN);
								if(build()) {
									construction.tipo = "mina";
									Game.player.money -= Mine.price;
									Mine.disable = true;
									Game.world.confirmTilesConstruction32x32(construction);
									index = -1;
									building = false;
									Game.ui.buttonEsc = false;
								}
							}else {
								message = "Você já possui esse recurso!";
								construction = null;
							}
						}else if(index == 1) {
							construction = new Thorn(psX, psY, 16, 16, Construction.THORN_EN);
							if(build()) {
								construction.tipo = "espinho";
								Game.player.money -= Thorn.price;								
							}
							
						}else if(index == 2) {
							construction = new MineLand(psX, psY, 16, 16, Construction.MINE_LAND_PLANTED_EN);
							if(build()) {
								construction.tipo = "mina terrestre";
								Game.player.money -= MineLand.price;								
							}
						}
						
					}else {
						message = "Você não pode construir isso aqui!";
						construction = null;
					}
					
				}else {
					message = "Você não tem dinheiro suficiente!";
					construction = null;
				}
				
				Game.player.clickConstruction = false;
				Game.player.clickBuildDescription = false;
			}
		}
		
	}
	
	private void verificaPosicaoMapa() {
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer - 112;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer - 96;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer - 80;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer - 64;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer - 48;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer - 32;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer - 16;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer + 16;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer + 32;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer + 48;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer + 64;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer + 80;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer + 96;
			psY = psYplayer - 80;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 0 && yTarget < 16) {
			psX = psXplayer + 112;
			psY = psYplayer - 80;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer - 112;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer - 96;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer - 80;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer - 64;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer - 48;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer - 32;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer - 16;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer + 16;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer + 32;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer + 48;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer + 64;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer + 80;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer + 96;
			psY = psYplayer - 64;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 16 && yTarget < 32) {
			psX = psXplayer + 112;
			psY = psYplayer - 64;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer - 112;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer - 96;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer - 80;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer - 64;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer - 48;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer - 32;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer - 16;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer + 16;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer + 32;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer + 48;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer + 64;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer + 80;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer + 96;
			psY = psYplayer - 48;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 32 && yTarget < 48) {
			psX = psXplayer + 112;
			psY = psYplayer - 48;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer - 112;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer - 96;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer - 80;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer - 64;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer - 48;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer - 32;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer - 16;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer + 16;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer + 32;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer + 48;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer + 64;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer + 80;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer + 96;
			psY = psYplayer - 32;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 48 && yTarget < 64) {
			psX = psXplayer + 112;
			psY = psYplayer - 32;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer - 112;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer - 96;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer - 80;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer - 64;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer - 48;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer - 32;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer - 16;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer + 16;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer + 32;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer + 48;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer + 64;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer + 80;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer + 96;
			psY = psYplayer - 16;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 64 && yTarget < 80) {
			psX = psXplayer + 112;
			psY = psYplayer - 16;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer - 112;
			psY = psYplayer;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer - 96;
			psY = psYplayer;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer - 80;
			psY = psYplayer;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer - 64;
			psY = psYplayer;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer - 48;
			psY = psYplayer;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer - 32;
			psY = psYplayer;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer - 16;
			psY = psYplayer;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer;
			psY = psYplayer;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer + 16;
			psY = psYplayer ;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer + 32;
			psY = psYplayer;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer + 48;
			psY = psYplayer;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer + 64;
			psY = psYplayer;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer + 80;
			psY = psYplayer;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer + 96;
			psY = psYplayer;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 80 && yTarget < 96) {
			psX = psXplayer + 112;
			psY = psYplayer;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer - 112;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer - 96;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer - 80;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer - 64;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer - 48;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer - 32;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer - 16;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer + 16;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer + 32;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer + 48;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer + 64;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer + 80;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer + 96;
			psY = psYplayer + 16;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 96 && yTarget < 112) {
			psX = psXplayer + 112;
			psY = psYplayer + 16;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer - 112;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer - 96;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer - 80;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer - 64;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer - 48;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer - 32;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer - 16;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer + 16;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer + 32;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer + 48;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer + 64;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer + 80;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer + 96;
			psY = psYplayer + 32;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 112 && yTarget < 128) {
			psX = psXplayer + 112;
			psY = psYplayer + 32;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer - 112;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer - 96;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer - 80;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer - 64;
			psY = psYplayer + 48;
				
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer - 48;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer - 32;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer - 16;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer + 16;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer + 32;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer + 48;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer + 64;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer + 80;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer + 96;
			psY = psYplayer + 48;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 128 && yTarget < 144) {
			psX = psXplayer + 112;
			psY = psYplayer + 48;
			
		}
		
		if(xTarget >= 0 && xTarget < 16 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer - 112;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 16 && xTarget < 32 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer - 96;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 32 && xTarget < 48 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer - 80;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 48 && xTarget < 64 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer - 64;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 64 && xTarget < 80 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer - 48;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 80 && xTarget < 96 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer - 32;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 96 && xTarget < 112 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer - 16;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 112 && xTarget < 128 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 128 && xTarget < 144 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer + 16;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 144 && xTarget < 160 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer + 32;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 160 && xTarget < 176 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer + 48;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 176 && xTarget < 192 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer + 64;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 192 && xTarget < 208 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer + 80;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 208 && xTarget < 224 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer + 96;
			psY = psYplayer + 64;
			
		}else if(xTarget >= 224 && xTarget < 240 && yTarget >= 144 && yTarget < 160) {
			psX = psXplayer + 112;
			psY = psYplayer + 64;
			
		}
		
	}
	
	public void ajustaPosicaoPlayerMapa() {
		
		psXplayer = (int)Game.player.getX();
		psYplayer = (int)Game.player.getY();
		
		while (psXplayer%16 != 0) {
			psXplayer++;
		}

		while (psYplayer%16 != 0) {
			psYplayer++;
		}
		
		Game.player.setX(psXplayer);
		Game.player.setY(psYplayer);
		
		while(!EntitySolid.solidCollision(Game.player.getX(), Game.player.getY())) {
			Game.player.setX(Game.player.getX() - 16);
		}
		
		Game.player.psTiles = (Game.player.getX()/16) + ((Game.player.getY()/16)*Game.world.WIDTH);
		
		while(Tile.isCollidingAll()) {
			Game.player.setX(Game.player.getX() - 16);
			Game.player.psTiles = (Game.player.getX()/16) + ((Game.player.getY()/16)*Game.world.WIDTH);
		}
		
	}

	public boolean build() {
			
			if(!Entity.isColidding(construction, Game.player)) {
				if(!checkCollidingEntities()) {
					psX = (int)(psX/16);
					psY = (int)(psY/16);
					construction.psTiles = psX + (psY*Game.world.WIDTH);
					construction.xTile = psX;
					construction.yTile = psY;
					Game.world.tiles[construction.psTiles].en = construction;
					Game.entities.add(construction);
					Game.player.clickBuildDescription = false;
					Game.player.indexDescription = -1;
					Game.player.clickConstruction = false;
					return true;
				}else {
					message = "Há alguma coisa sobre o local da construção!";
					construction = null;
					return false;
				}
			}else {
				message = "Você está em cima do local da construção!";
				construction = null;
				return false;
			}
		
	}
	
	public boolean checkCollidingEntities() {
		
		for(int i=0; i<Game.entities.size(); i++) {
			if(Entity.isColidding(Game.entities.get(i), construction)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkMoney() {
		
		if(index == 0) {
			if(Game.player.money < Mine.price)
				return false;
		}else if(index == 1)
			if(Game.player.money < Thorn.price) {
			return false;
		}
		
		return true;
	}
	
	public void renderBuilding(Graphics g) {
		
		if(index == 0)
			constructionImage = Construction.MINE_EN;
		else if (index == 1) 
			constructionImage = Construction.THORN_EN;
		else if (index == 2) 
				constructionImage = Construction.MINE_LAND_EN;
		else 
			constructionImage = null;
		
		
		if(constructionImage != null) {
			if(index == 0)
				g.drawImage(constructionImage, ((int)Game.player.moveMx) - 16, ((int)Game.player.moveMy) - 16, null);
			else
				g.drawImage(constructionImage, ((int)Game.player.moveMx) - 8, ((int)Game.player.moveMy) - 8, null);
		}
		
	}
	
}
