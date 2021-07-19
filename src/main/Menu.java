package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import world.World;

public class Menu {
	
	public String[] options = {"Novo jogo", "Carregar jogo","Controles", "Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length-1;
	
	public boolean up, down, enter;
	public static boolean pause;
	public static boolean controles;
	
	public static boolean saveExixts, saveGame;
	
	public static void saveGame(String[] val1, int[]val2, int encode) {
		BufferedWriter write = null;
		
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i <val1.length; i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n=0; n < value.length; n++) {
				value[n] += encode;
				current += value[n]; 
			}
			try {
				write.write(current);
				if(i < val1.length - 1) {
					write.newLine();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			write.flush();
			write.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = ""; 
						for(int i=0; i < val.length; i++) {
							val[i] -= encode;
							trans[1]+=val[i];
						}
						
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch (IOException e) {
					// TODO: handle exception
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return line;
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i=0; i<spl.length;i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]){
				
				case "levelRoom":
					World.restarGame("level" + spl2[1] + ".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
				
				case "vida":
					Game.player.life = Integer.parseInt(spl2[1]);
					break;
					
				case "levelPlayer":
					Game.player.levelPlayer = Integer.parseInt(spl2[1]);
					break;
				
				case "exp":
					Game.player.exp = Integer.parseInt(spl2[1]);
			}
		}
	}
	
	public void tick() {
		
		File file = new File("save.txt");
		if(file.exists()) {
			saveExixts = true;
		}else {
			saveExixts = false;
		}
		
		if(controles) {
			currentOption = 3;
		}else {
			if(up) {
				up = false;
				currentOption--;
				if(currentOption < 0)
					currentOption = maxOption;
			}
			
			if(down) {
				down = false;
				currentOption++;
				if(currentOption > maxOption)
					currentOption = 0;
			}
		}
		
		if(enter) {
			enter = false;
			if(options[currentOption].equals("Novo jogo")) {
				Game.gameState = "NORMAL";
				pause = false;
				file = new File("save.txt");
				file.delete();
				
			}else if (options[currentOption].equals("Continuar")) {
				Game.gameState = "NORMAL";
				pause = false;
			
			}else if (options[currentOption].equals("Carregar jogo")) {
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
				}
				
			}else if (options[currentOption].equals("Controles")) {
				Game.gameState = "CONTROLES";
				controles = true;
					
			}else if (options[currentOption].equals("Sair") && controles) { //Voltar
				Game.gameState = "MENU";
				controles = false;
				currentOption = 0;
					
			}else if (options[currentOption].equals("Sair")) {
				System.exit(1);
			}
		}
		
	}
	
	public void render(Graphics g) {
		
		if(pause) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			g2.setColor(Color.RED);
			g2.setFont(new Font("arial", Font.BOLD, 70));
			g2.drawString("Game Clone", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 200, (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 250);
			
			//opções de menu
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 24));
			g2.drawString("Continuar", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 50, 340);
			g2.drawString("Carregar Jogo", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 75, 380);
			g2.drawString("Controles", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 50, 420);
			g2.drawString("Sair", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 20, 460);
			
			if(options[currentOption].equals("Novo jogo")) 
				g2.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 70, 340);
			else if(options[currentOption].equals("Carregar jogo"))
				g2.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 95, 380);
			else if(options[currentOption].equals("Controles"))
				g2.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 70, 420);
			else if(options[currentOption].equals("Sair"))
				g2.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 40, 460);
			
		}else {
			
			g.setColor(Color.black);
			g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.BOLD, 70));
			g.drawString("Game Clone", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 200, (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 250);
			
			//opções de menu
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 24));
			g.drawString("Novo jogo", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 50, 340);
			g.drawString("Carregar Jogo", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 75, 380);
			g.drawString("Controles", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 50, 420);
			g.drawString("Sair", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 15, 460);
			
			if(options[currentOption].equals("Novo jogo")) 
				g.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 70, 340);
			else if(options[currentOption].equals("Carregar jogo"))
				g.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 95, 380);
			else if(options[currentOption].equals("Controles"))
				g.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 70, 420);
			else if(options[currentOption].equals("Sair"))
				g.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 35, 460);
		}
		
		if(controles) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,255));
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			g2.setColor(Color.RED);
			g2.setFont(new Font("arial", Font.BOLD, 70));
			g2.drawString("Controles", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 170, (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 250);
			
			//instruções de controle
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 24));
			g2.drawString("A, W, S, D: Movimentar o jogador", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 260);
			g2.drawString("E / Q: Mudar o item que o jogador está segurando", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 300);
			g2.drawString("G: Pegar itens", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 340);
			g2.drawString("F: Largar itens", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 380);
			g2.drawString("R: Utilizar itens", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 420);
			g2.drawString("B: Abrir / Fechar mochila", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 460);
			g2.drawString("LEFT CLICK: Atirar / Mover itens do inventário e mochila", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 500);
			g2.drawString("ENTER: Selecionar opções do MENU", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 250, 540);
			
			g2.drawString("Voltar", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 20, 650);
			g2.drawString(">", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 40, 650);
		}
		
	}
}
