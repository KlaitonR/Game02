package entities;

import java.awt.image.BufferedImage;

public class Pine extends Tree{
	
	public Pine(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		
		if(life <= 0) {
			destroySelf(Entity.FIREWOOD_PINHEIRO_EN, Entity.SEED_PINHEIRO_EN );
		}
			
	}
	
}
