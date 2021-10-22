package estructurasdedatos;

public class Tupla<A,B> {
	private final A primerElemento;
	private final B segundoElemento;
	
	public Tupla(A e1, B e2) {
		this.primerElemento = e1;
		this.segundoElemento = e2;
	}

	public A getX() {
		return primerElemento;
	}


	public B getY() {
		return segundoElemento;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(this.primerElemento).append(", ").append(this.segundoElemento).append("]").append("\n");
		return sb.toString();
	}
	
}
