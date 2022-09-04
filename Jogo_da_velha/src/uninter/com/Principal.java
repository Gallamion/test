package uninter.com;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

//Neste trabalho vou usar meus conhecimentos prévios de um jogo da velha simples que já criei, junto com os conceitos da POO para melhorar ele.

//Como quero que o jogo tenha uma interface visual, usei o Canvas, já o runnable, para o loop do game.
public class Principal extends Canvas implements Runnable {
	
	//variáveis para decidir largura e altura da janela do jogo
	public static int largura = 300;
	public static int altura = 300;

	
	
	// tamanho da janela
	public Principal() {
		this.setPreferredSize(new Dimension(largura, altura));
	}
	
	public static void main(String[] args) {
		Principal jogo = new Principal();
		JFrame frame = new JFrame("Jogo da velha"); //instancia a janela
		frame.setVisible(true);//visivel para o usuário
		frame.setResizable(false); //o usuário não pode mudar o tamanho da janela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //para de rodar o programa quando o usuário fechar a janela		
		frame.add(jogo);//utiliza o canvas para mostrar o jogo		
		frame.pack();//busca as dimensões do jogo para a janela
		frame.setLocationRelativeTo(null);//centraliza a janela
		
	}
	
	public void tick() {
		
	}

	//metodo para renderizar os gráficos
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, largura,altura);
		
				
		g.dispose();
		bs.show();
	}
	
	public void run() {
		while (true) {
			render();
		}
		
		
	}

}
