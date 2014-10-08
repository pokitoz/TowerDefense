package all;

import gui.GameFrame;

public class GameInit {

	public static void main(String[] args) {
		GameFrame gf = new GameFrame();

		Thread t = new Thread(gf);
		t.start();
	}
}
