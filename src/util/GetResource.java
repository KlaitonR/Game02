package util;

import java.util.ArrayList;

import entities.Entity;
import entities.itens.Diamond;
import entities.itens.Emerald;
import entities.itens.Firewood;
import entities.itens.FirewoodOak;
import entities.itens.FirewoodPine;
import entities.itens.FirewoodWillow;
import entities.itens.Fish;
import entities.itens.Ore;
import entities.itens.OreCoal;
import entities.itens.OreCopper;
import entities.itens.OreGold;
import entities.itens.OreSilver;
import entities.itens.Phosphor;
import entities.itens.PotassiumNitrate;
import entities.itens.Seed;
import entities.itens.SeedOak;
import entities.itens.SeedPine;
import entities.itens.SeedWillow;
import entities.itens.Stone;
import entities.itens.Sulfor;
import entities.spots.MiningSite;
import entities.spots.MiningSiteCoal;
import entities.spots.MiningSiteCopper;
import entities.spots.MiningSiteDiamond;
import entities.spots.MiningSiteEmerald;
import entities.spots.MiningSiteGold;
import entities.spots.MiningSitePhosphor;
import entities.spots.MiningSitePotassiumNitrate;
import entities.spots.MiningSiteSilver;
import entities.spots.MiningSiteSulfor;
import entities.spots.Tree;
import entities.spots.TreeOak;
import entities.spots.TreePine;
import entities.spots.TreeWillow;
import main.Game;

public class GetResource {

public void getFirewood(Tree tr) {
		
		tr.life--;
		Firewood fireWood1 = null;
		Firewood fireWood2 = null;
		Firewood fireWood3 = null;
		Seed seed1 = null;
		Seed seed2 = null;
		ArrayList<Firewood> lenhas = new ArrayList<>();
		ArrayList<Seed> sementes = new ArrayList<>();
		
		if(tr instanceof TreeOak) {
			
			//lenhas
			fireWood1 = new FirewoodOak(0, 0, 16, 16, Firewood.FIREWOOD_CARVALHO_EN);
			fireWood2 = new FirewoodOak(0, 0, 16, 16, Firewood.FIREWOOD_CARVALHO_EN);
			fireWood3 = new FirewoodOak(0, 0, 16, 16, Firewood.FIREWOOD_CARVALHO_EN);
			fireWood1.tipo = "lenha de carvalho";
			fireWood1.id = Id.ID_FIREWOOD_OAK;
			fireWood2.tipo = "lenha de carvalho";
			fireWood2.id = Id.ID_FIREWOOD_OAK;
			fireWood3.tipo = "lenha de carvalho";
			fireWood3.id = Id.ID_FIREWOOD_OAK;
		
			//sementes
			seed1 = new SeedOak(0, 0, 16, 16, Seed.SEED_CARVALHO_EN);
			seed2 = new SeedOak(0, 0, 16, 16, Seed.SEED_CARVALHO_EN);
			seed1.tipo = "semente de carvalho";
			seed1.id = Id.ID_SEED_OAK;
			seed2.tipo = "semente de carvalho";
			seed2.id = Id.ID_SEED_OAK;
			
		}else if (tr instanceof TreePine) {
			
			fireWood1 = new FirewoodPine(0, 0, 16, 16, Firewood.FIREWOOD_PINHEIRO_EN);
			fireWood2 = new FirewoodPine(0, 0, 16, 16, Firewood.FIREWOOD_PINHEIRO_EN);
			fireWood3 = new FirewoodPine(0, 0, 16, 16, Firewood.FIREWOOD_PINHEIRO_EN);
			fireWood1.tipo = "lenha de pinheiro";
			fireWood1.id = Id.ID_FIREWOOD_PINE;
			fireWood2.tipo = "lenha de pinheiro";
			fireWood2.id = Id.ID_FIREWOOD_PINE;
			fireWood3.tipo = "lenha de pinheiro";
			fireWood3.id = Id.ID_FIREWOOD_PINE;
			
			seed1 = new SeedPine(0, 0, 16, 16, Seed.SEED_PINHEIRO_EN);
			seed2 = new SeedPine(0, 0, 16, 16, Seed.SEED_PINHEIRO_EN);
			seed1.tipo = "semente de pinheiro";
			seed1.id = Id.ID_SEED_PINE;
			seed2.tipo = "semente de pinheiro";
			seed2.id = Id.ID_SEED_PINE;
			
		}else if (tr instanceof TreeWillow) {
			
			fireWood1 = new FirewoodWillow(0, 0, 16, 16, Firewood.FIREWOOD_SALGUEIRO_EN);
			fireWood2 = new FirewoodWillow(0, 0, 16, 16, Firewood.FIREWOOD_SALGUEIRO_EN);
			fireWood3 = new FirewoodWillow(0, 0, 16, 16, Firewood.FIREWOOD_SALGUEIRO_EN);
			fireWood1.tipo = "lenha de salgueiro";
			fireWood1.id = Id.ID_FIREWOOD_WILLOW;
			fireWood2.tipo = "lenha de salgueiro";
			fireWood2.id = Id.ID_FIREWOOD_WILLOW;
			fireWood3.tipo = "lenha de salgueiro";
			fireWood3.id = Id.ID_FIREWOOD_WILLOW;
			
			seed1 = new SeedWillow(0, 0, 16, 16, Seed.SEED_SALGUEIRO_EN);
			seed2 = new SeedWillow(0, 0, 16, 16, Seed.SEED_SALGUEIRO_EN);
			seed1.tipo = "semente de salgueiro";
			seed1.id = Id.ID_SEED_WILLOW;
			seed2.tipo = "semente de salgueiro";
			seed2.id = Id.ID_SEED_WILLOW;
		}
		
		lenhas.add(fireWood1);
		lenhas.add(fireWood2);
		lenhas.add(fireWood3);
		sementes.add(seed1);
		sementes.add(seed2);
		
		int index = Game.sysInv.checkPositionGetInv();
		
		for(int i=0; i<lenhas.size();i++) {
			
			if(!Game.sysInv.checkPackInv(lenhas.get(i))) {
				if (index >= 0 && index <= Game.sysInv.inventario.length) {
					Game.sysInv.inventario[index] = lenhas.get(i);
					Game.sysInv.inv[index] = lenhas.get(i).getSprite();
				}else {
					lenhas.get(i).setX(Game.player.getX());
					lenhas.get(i).setY(Game.player.getY());
					Game.entities.add(lenhas.get(i));
				}
			}else {
				fireWood1 = null;
				fireWood2 = null;
				fireWood3 = null;
			}
		}
		
		index = Game.sysInv.checkPositionGetInv();
		
		for(int i=0; i<sementes.size();i++) {
			
			if(!Game.sysInv.checkPackInv(sementes.get(i))) {
				if (index >= 0 && index <= Game.sysInv.inventario.length) {
					Game.sysInv.inventario[index] = sementes.get(i);
					Game.sysInv.inv[index] = sementes.get(i).getSprite();
				}else {
					sementes.get(i).setX(Game.player.getX());
					sementes.get(i).setY(Game.player.getY());
					Game.entities.add(sementes.get(i));
				}
			}else {
				seed1 = null;
				seed2 = null;
			}
		}
		
		if(tr.life == 0) {
			tr.destroySelf();
			Game.player.cuttingTree = false;
		}
	}

	public void getFish() {
	
		int index = Game.sysInv.checkPositionGetInv();
		Fish fish = new Fish(0, 0, 16, 16, Entity.FISH_EN);
		fish.tipo = "peixe";
		
		if(!Game.sysInv.checkPackInv(fish)) {
			if (index >= 0 && index <= Game.sysInv.inventario.length) {
				Game.sysInv.inventario[index] = fish;
				Game.sysInv.inv[index] = fish.getSprite();
			}else {
				fish.setX(Game.player.getX());
				fish.setY(Game.player.getY());
				Game.entities.add(fish);
			}
		}else {
			fish = null;
		}
	}
	
	public void getOre(MiningSite ms) {
		
		Ore ore = null;
		Ore stone = null;
		
		if(ms instanceof MiningSiteSilver) {
			OreSilver os = new OreSilver(0, 0, 16, 16, Ore.ORE_SILVER_EN);
			os.tipo = "minério de prata";
			ore = os;
			
		}else if (ms instanceof MiningSiteGold) {
			OreGold og = new OreGold(0, 0, 16, 16, Ore.ORE_GOLD_EN);
			og.tipo = "minério de ouro";
			ore = og;
			
		}else if (ms instanceof MiningSiteDiamond) {
			Diamond d = new Diamond(0, 0, 16, 16, Ore.DIAMOND_EN);
			d.tipo = "diamante";
			ore = d;
			
		}else if (ms instanceof MiningSiteCopper) {
			OreCopper ocp = new OreCopper(0, 0, 16, 16, Ore.ORE_COPPER_EN);
			ocp.tipo = "minério de cobre";
			ore = ocp;
			
		}else if (ms instanceof MiningSiteCoal) {
			OreCoal oc = new OreCoal(0, 0, 16, 16, Ore.ORE_COAL_EN);
			oc.tipo = "carvão mineral";
			ore = oc;
			
		}else if (ms instanceof MiningSiteEmerald) {
			Emerald e = new Emerald(0, 0, 16, 16, Ore.EMERALD_EN);
			e.tipo = "esmeralda";
			ore = e;
			
		}else if (ms instanceof MiningSiteSulfor) {
			Sulfor e = new Sulfor(0, 0, 16, 16, Ore.SULFOR_EN);
			e.tipo = "enxofre";
			ore = e;
			
		}else if (ms instanceof MiningSitePotassiumNitrate) {
			PotassiumNitrate e = new PotassiumNitrate(0, 0, 16, 16, Ore.POTASSIUM_NITRATE_EN);
			e.tipo = "nitrato de potásio";
			ore = e;
			
		}else if (ms instanceof MiningSitePhosphor) {
			Phosphor e = new Phosphor(0, 0, 16, 16, Ore.PHOSPHOR_EN);
			e.tipo = "fósforo";
			ore = e;
			
		}
		
		if(Game.rand.nextInt(100) < 100) {
			stone = new Stone(0, 0, 16, 15, Ore.STONE_EN);
			stone.tipo = "pedra";
		}
		
		int index = Game.sysInv.checkPositionGetInv();
		
		if(!Game.sysInv.checkPackInv(ore)) {
			if (index >= 0 && index <= Game.sysInv.inventario.length) {
				Game.sysInv.inventario[index] = ore;
				Game.sysInv.inv[index] = ore.getSprite();
			}else {
				ore.setX(Game.player.getX());
				ore.setY(Game.player.getY());
				Game.entities.add(ore);
			}
		}else {
			ore = null;
		}
		
		index = Game.sysInv.checkPositionGetInv();
		
		if(stone != null) {
			if(!Game.sysInv.checkPackInv(stone)) {
				if (index >= 0 && index <= Game.sysInv.inventario.length) {
					Game.sysInv.inventario[index] = stone;
					Game.sysInv.inv[index] = stone.getSprite();
				}else {
					stone.setX(Game.player.getX());
					stone.setY(Game.player.getY());
					Game.entities.add(stone);
				}
			}else {
				stone = null;
			}
		}
	}
	
}
