package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import main.Sound;
import util.Id;

public class Tree extends Entity{
	
	public int life = 5;

	public Tree(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		height += 10;
		width -= 9;
		x += 4;
		y-=7;
		life = 5;
	}
	
	public void destroySelf(BufferedImage sprite, BufferedImage seed) {
		
		//lenhas
		Firewood fireWood1 = new Firewood(this.x, this.y, 16, 16, sprite);
		fireWood1.show = true;
		fireWood1.clear = true;
		Game.entities.add(fireWood1);
		Firewood fireWood2 = new Firewood(this.x - 10, this.y - 5, 16, 16, sprite);
		fireWood2.show = true;
		fireWood2.clear = true;
		Game.entities.add(fireWood2);
		Firewood fireWood3 = new Firewood(this.x + 10, this.y + 5, 16, 16, sprite);
		fireWood3.show = true;
		fireWood3.clear = true;
		Game.entities.add(fireWood3);
		
		//Renderizar cepo
		Stump sp = new Stump(this.getX(), this.getY(), 16, 16, Entity.STUMP_EN);
		sp.tipo = "cepo";
		sp.show = true;
		sp.psTiles = psTiles;
		Game.entities.add(sp);
		
		//sementes
		Seed sd1 = new Seed(this.x - 6, this.y - 3, 16, 16, seed);
		sd1.show = true;
		sd1.clear = true;
		Game.entities.add(sd1);
		
		Seed sd2 = new Seed(this.x + 5, this.y + 5, 16, 16, seed);
		sd2.show = true;
		sd2.clear = true;
		Game.entities.add(sd2);
		
		if(this instanceof Oak) {
			fireWood1.tipo = "lenha de carvalho";
			fireWood1.id = Id.ID_FIREWOOD_OAK;
			fireWood2.tipo = "lenha de carvalho";
			fireWood2.id = Id.ID_FIREWOOD_OAK;
			fireWood3.tipo = "lenha de carvalho";
			fireWood3.id = Id.ID_FIREWOOD_OAK;
			sd1.tipo = "semente de carvalho";
			sd1.id = Id.ID_SEED_OAK;
			sd2.tipo = "semente de carvalho";
			sd2.id = Id.ID_SEED_OAK;
		}else if (this instanceof Pine) {
			fireWood1.tipo = "lenha de pinheiro";
			fireWood1.id = Id.ID_FIREWOOD_PINE;
			fireWood2.tipo = "lenha de pinheiro";
			fireWood2.id = Id.ID_FIREWOOD_PINE;
			fireWood3.tipo = "lenha de pinheiro";
			fireWood3.id = Id.ID_FIREWOOD_PINE;
			sd1.tipo = "semente de pinheiro";
			sd1.id = Id.ID_SEED_PINE;
			sd2.tipo = "semente de pinheiro";
			sd2.id = Id.ID_SEED_PINE;
		}
		
		Game.world.tiles[psTiles].en = null;
		Game.entities.remove(this);
		
		Sound.Clips.cuttingTree.stop();
		Sound.Clips.fallingTree.play();
		
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
