package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.spots.Tree;
import entities.spots.TreeOak;
import entities.spots.TreePine;
import main.Game;
import util.Mapa;
import util.Regiao;
import world.FloorTile;
import world.Tile;

public class Ground extends Entity{
	
	public int time;
	public int f1 = 1*60, f2 = 2*60, f3 = 3*60;
	public boolean plant;
	public int life = 100;
	public int cont;
	public String tipo;

	public Ground(double x, double y, int width, int height, BufferedImage sprite, int ps) {
		super(x, y, width, height, sprite);
		this.psTiles = ps;
		depth = 10;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
	
	public void plant() {
		
		if(plant && time < f3) {
			Tree tr = null;
			time++;
			depth = 1;
			if(time < f1 && time > 0) {
				this.setSprite(Entity.GROUND_F1_EN);
			}else if(time < f2) {
				this.setSprite(Entity.GROUND_F2_EN);
			}else if(time < f3) {
				this.setSprite(Entity.GROUND_F3_EN);
			}
			
			if (time == f3 && !Game.collision.checkColisionGroundToTree(this)) {
				
				if(tipo.equals("terreno de carvalho")) {
					 tr = new TreeOak(this.x, this.y, 16, 16, Tree.CARVALHO_EN);
					 tr.tipo = "carvalho";
					 
				}else if (tipo.equals("terreno de pinheiro")){
					tr = new TreePine(this.x, this.y, 16, 16, Tree.PINHEIRO_EN);
					tr.tipo = "pinheiro";
				}
				
				Game.entities.add(tr);
				Game.world.tiles[this.psTiles].en = tr;
				tr.show = true;
				tr.psTiles = this.psTiles;
				plant = false;
				Game.entities.remove(this);
				
			}else if(time == f3){
				time = f2;
			}
		}
		
	}
	
	public void generateFloor() {
		
		cont++;
		
		if(!plant && cont >= 900){
			life --;
		}
		
		if(life == 0) {
			Game.world.tiles[psTiles] = new FloorTile((int)x, (int)y, Tile.TILE_FLOOR);
			Game.world.tiles[psTiles].show = true;
			Game.entities.remove(this);
		}
		
	}
	
	public void tick() {
		
		depth = 0;
		plant();
		generateFloor();
		
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
