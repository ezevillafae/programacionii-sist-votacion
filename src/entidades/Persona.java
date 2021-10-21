package entidades;


public class Persona {
	private String nombre;
	private int dni;
	private int edad;
	private boolean enfermedad;
	
	public Persona(String nombre, int dni, int edad, boolean enfermedad) {
		if(dni <= 0 || nombre == null || edad < 0 || edad >= 120) {
			throw new RuntimeException();
		}
		this.nombre = nombre;
		this.dni = dni;
		this.edad = edad;
		this.enfermedad = enfermedad;
	}
	
	public boolean esMayorDe65() {
		return this.edad > 65;
	}
	
	public boolean tieneEnfermedadPreexistente() {
		return this.enfermedad;
	}

	public String getNombre() {
		return nombre;
	}

	public int getDni() {
		return dni;
	}

	public int getEdad() {
		return edad;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Persona : {");
		sb.append("\n");
		sb.append("nombre= ").append(nombre);
		sb.append("\n");
		sb.append("dni= ").append(dni);
		sb.append("\n");
		sb.append("edad= ").append(edad);
		sb.append("\n");
		sb.append("enfermedad= ").append(enfermedad);
		sb.append("\n}\n");
		return sb.toString();
	}
	

}
