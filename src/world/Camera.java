package world;

//import main.Game;

public class Camera {

	public static int x = 0;
	public static int y = 0;
	
	public static int clamp(int atual,int min, int max) {
		
//		System.out.println(Game.player.getX() - x);
//		System.out.println(Game.player.getY() - y);

		if(atual < min) {
			atual = min;
		}
		
		if (atual > max) {
			atual = max;
		}
	
		return atual;
	}
	
}
