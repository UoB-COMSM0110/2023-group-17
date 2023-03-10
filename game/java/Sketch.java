import processing.core.PApplet;

import java.awt.geom.Arc2D;

public class Sketch extends PApplet {
	void doddleDraw(){
		printBoard();
		if (testGrid.dir == direction.up){
			testGrid.doddleY--;
			testGrid.curstep++;
			if (testGrid.curstep == testGrid.maxstep){
				testGrid.dir = direction.down;
			}
		}else if (testGrid.dir == direction.down){
			if (testGrid.doddleY - testGrid.pivt >= 39 || testGrid.doddleY >= 159){
				exit();
			}
			if (testGrid.doddleY + 1 < 160 && testGrid.board[testGrid.doddleY + 1][testGrid.doddleX].type != Type.EMPTY){
				testGrid.dir = direction.up;
				if (testGrid.curstep > 0) {
					testGrid.pivt -= testGrid.curstep;
				}
				testGrid.curstep = 0;
				if (testGrid.board[testGrid.doddleY + 1][testGrid.doddleX].type == Type.ONETIME){
					int left = testGrid.doddleX;
					int right = testGrid.doddleX + 1;
					while (left >= 0 && testGrid.board[testGrid.doddleY + 1][left].type == Type.ONETIME) {
						testGrid.board[testGrid.doddleY + 1][left--].type = Type.EMPTY;
					}
					while (right < 40 && testGrid.board[testGrid.doddleY + 1][right].type == Type.ONETIME) {
						testGrid.board[testGrid.doddleY + 1][right++].type = Type.EMPTY;
					}
				}
				return;
			}
			testGrid.doddleY++;
			testGrid.curstep--;
			}
		delay(60);
//		delay(50 + (int) Math.pow(2, testGrid.curstep) / 2);
	}

	void printBoard(){
		for (int j = testGrid.pivt; j < testGrid.pivt + 40; j++){
			int mark = testGrid.pivt;
			for (int i = 0; i < 40; i++){
				int optic = 150;
				if (j == testGrid.doddleY && i == testGrid.doddleX){
					fill(0, 0, 0);
				}else{

					if (testGrid.board[j][i].type == Type.EMPTY) {
						noStroke();
						fill(255,255,255, optic);
					}else{

						stroke(0);
						if (testGrid.board[j][i].type == Type.CONSTANT){
							fill(124,252,0, optic);
						}else if (testGrid.board[j][i].type == Type.ONETIME){
							fill(218,165,32, optic);
						}else if (testGrid.board[j][i].type == Type.FLOAT){
							fill(127,255,212, optic);
						}
					}
				}
				rect(i * 10, (j - mark) * 10, 10, 10);
			}
		}
		floatCellMove();
	}
	static int floatcnt = 0;

	private void floatCellMove() {
		for (int j = 0; j < testGrid.board.length; j++){
			for (int i = 0; i < testGrid.board[0].length; i++){
				if (testGrid.board[j][i].type == Type.FLOAT){
					if (((FloatCell) testGrid.board[j][i]).dir == direction.LEFT){
						if (i - 1 >= 0 && i + 3 < 40){
							testGrid.board[j][i - 1] = new FloatCell(direction.LEFT);
							testGrid.board[j][i + 3] = new Cell(Type.EMPTY);

						}
					}else if (((FloatCell) testGrid.board[j][i]).dir == direction.RIGHT){
						if (i + 4 < 40 && i - 3 >= 0){
							testGrid.board[j][i + 1] = new FloatCell(direction.RIGHT);
							testGrid.board[j][i + 4] = new FloatCell();
							testGrid.board[j][i] = new Cell(Type.EMPTY);
						}
					}
					i = 40;
				}
			}
		}
		floatcnt++;
		if (floatcnt >= 6){
			testGrid.changeDir();
			floatcnt = 0;
		}
		println(floatcnt);
	}

	@Override
	public void keyPressed(){
		if (keyCode == LEFT){

			testGrid.doddleX--;
			if (testGrid.doddleX == 0){
				testGrid.doddleX = 39;
			}

		}else if (keyCode == RIGHT){
			testGrid.doddleX++;
			if (testGrid.doddleX == 40){
				testGrid.doddleX = 0;
			}

		}
	}


	Grid testGrid = new Grid(160, 40);
	public void settings() {
		size(400, 400); // Set the size of the sketch window
	}

	public void setup(){
		size(400, 400);
		frameRate(60);
		noStroke();
	}

	public void draw() {
		background(255);
		doddleDraw();
	}

	public static void main(String[] args) {
		PApplet.main("Sketch"); // Replace with the name of your sketch class
	}
}
