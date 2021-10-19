package entidades;

public class Main {
	public static void main(String[] args) throws Exception {
		Votante pres;
		try {
			pres = new Votante("Nicolas", 3434535, 29, false, true);
			MesaMayores mesaMayores = new MesaMayores(20, pres);
			
			System.out.println(mesaMayores);
			
		} catch (Exception e) {
			System.out.println("Error al crear la mesa");
		}
		
		
		
	}
}
