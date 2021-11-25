package entidades;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import estructurasdedatos.Tupla;

public abstract class Mesa {
    private int codigoID;
    private static int codigoContador = 0;
    private Votante presidenteMesa;
    private HashMap<Integer,Integer> franjasHorarias; // (franja-cupo)
    
    public Mesa(Votante presidenteMesa) {
    	if(presidenteMesa == null) {
    		throw new RuntimeException();
    	}
    	this.codigoID = codigoContador++;
    	this.franjasHorarias= new HashMap<>();
    	crearFranjasHorarias();
    	asignarPresidente(presidenteMesa);
    }

    public abstract void crearFranjasHorarias();

    public void asignarPresidente(Votante votante){
        if(votante == null || votante.esPresidenteDeMesa() || hayPresidenteDeMesa()) {
        	throw new RuntimeException();
        }
 
        this.presidenteMesa=votante;
        Tupla<Integer, Integer> turno = dameTurno();
        presidenteMesa.asignarComoPresidenteDeMesa();
        presidenteMesa.asignarTurno(turno.getX(), turno.getY());
    }

    public boolean hayPresidenteDeMesa() {
        return this.presidenteMesa!=null ;
    }
    

    public int dameCodigoMesa() {
        return this.codigoID;
    }

    public void actualizarCupo(int franjaHoraria) {
        this.franjasHorarias.replace(franjaHoraria, this.franjasHorarias.get(franjaHoraria)+1);
    }
    
    public abstract int buscarFranjaHoraria();

    public Tupla<Integer,Integer> dameTurno() {
        int franjaDisponible = buscarFranjaHoraria();
        if (franjaDisponible!=-1) {
            Tupla<Integer,Integer> turno= new Tupla<>(dameCodigoMesa(),franjaDisponible);
            actualizarCupo(franjaDisponible);
            return turno;
        }
        else
            return null;
    }

	public HashMap<Integer, Integer> getFranjasHorarias() {
		return franjasHorarias;
	}
	
	public int cantidadDeVotantes() {
		int cantidadDeVotantes=0;
		Set<Integer> franjas = this.franjasHorarias.keySet();
		for (Integer franja : franjas) {
			cantidadDeVotantes += this.franjasHorarias.get(franja);
		}
		return cantidadDeVotantes;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mesa m = (Mesa) obj;
		if (codigoID != m.codigoID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-------------------------------------------\n");
		sb.append("Mesa N° ").append(this.codigoID).append("\n");
		sb.append(this.getClass().getName()).append("\n");
		sb.append("Presidente de mesa :").append(this.presidenteMesa.getNombre()).append(" DNI: ");
        sb.append(this.presidenteMesa.getDni()).append("\n");

        Set<Integer> horarios = this.franjasHorarias.keySet();
        for (Integer horario : horarios) {
            sb.append("Horario: ").append(horario).append(" Cupos asignados: ");
            sb.append(this.franjasHorarias.get(horario)).append("\n");
        }

		return sb.toString();
	}
    
    
    

}

