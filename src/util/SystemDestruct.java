package util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.construction.Construction;
import entities.construction.House;
import entities.construction.Mine;
import entities.construction.MineLand;
import entities.construction.Statue;
import main.Game;
import world.EntitySolid;
import world.Tile;

public class SystemDestruct {
	
	public int xTarget = -1, yTarget = -1, psX, psY, psXplayer, psYplayer;
	public boolean destruct;
	Construction constructDestruction;
	BufferedImage constructDestructionImage;
	
	public SystemDestruct() {
		
		constructDestructionImage = Game.spriteContruction.getSprite(192, 0, 16, 16);
		
	}
	
	public void tick() {
		
		psXplayer = (int)Game.player.getX();
		psYplayer = (int)Game.player.getY();
		
		if(Game.player.clickDestruct) {
			Game.player.clickDestruct = false;
			destruct = true;
		}
		
		if(destruct) {
			Game.ui.buttonEsc = true;
			if(Game.player.clickDestruction) {
				Game.player.clickDestruction = false;
				verificaPosicaoMapa();
				if(constructDestruction != null) {
					if(!(constructDestruction instanceof House) &&
							!(constructDestruction instanceof Statue)) {
						verifyConstruction();
						Game.entities.remove(constructDestruction);
						Game.world.tiles[constructDestruction.psTiles].en = null;
						Game.player.clickDestruction = false;
						constructDestruction = null;
						
					}else {
						Game.sysBuild.message = "Você não pode destruir isso!";
						constructDestruction = null;
					}
				}
			}
		}
	}
	
	public void verifyConstruction() {
		
		if(constructDestruction instanceof Mine) {
			Mine.disable = false;
		}else if (constructDestruction instanceof MineLand) {
			Game.world.tiles[constructDestruction.psTiles].setSprite(Tile.TILE_FLOOR);
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
	
	public void renderDestruct(Graphics g) {
		
		if(constructDestructionImage != null && destruct)
			g.drawImage(constructDestructionImage, ((int)Game.player.moveMx) - 8, ((int)Game.player.moveMy) - 8, null);
		
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
			Game.sysBuild.message = "Não! não faça isso!!!";
			
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
		
		if(Game.world.tiles[(psX/16) + ((psY/16) * Game.world.WIDTH)].en instanceof Construction) {
			constructDestruction = (Construction)Game.world.tiles[(psX/16) + ((psY/16) * Game.world.WIDTH)].en;
		} else {
			if(Game.sysBuild.message == null)
				Game.sysBuild.message = "Você não pode destruir isso!";
			constructDestruction = null;
		}
		
	}

}
