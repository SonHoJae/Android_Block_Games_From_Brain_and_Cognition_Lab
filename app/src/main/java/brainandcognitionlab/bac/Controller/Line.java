package brainandcognitionlab.bac.Controller;

public class Line {
	private Position p1;
	private Position p2;
	
	public Position getP1() {
		return p1;
	}

	public Position getP2() {
		return p2;
	}

	public void setP1(Position p1) {
		this.p1 = p1;
	}

	public void setP2(Position p2) {
		this.p2 = p2;
	}

	public void setP1andP2(Position posi1 , Position posi2) {
		if(posi1.getX() + posi1.getY() < posi2.getX() + posi2.getY()){
			p2 = posi2;
			p1 = posi1;
		}else{
			p2 = posi1;
			p1 = posi2;
		}
	}
	public boolean isHorizontal() {
		return p1.getY() == p2.getY() ? true : false;
	}

}
