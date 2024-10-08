package servicios;

import entidades.Habilidad;

import java.util.HashMap;
import java.util.Map;

public class ServicioHabilidades {
    private Map<String, Habilidad> habilidades;

    ServicioHabilidades(){
        this.habilidades = new HashMap<>();
    }

    public Habilidad getOrCreateHabilidad(String habilidad){
        if (habilidades.containsKey(habilidad)){
            return habilidades.get(habilidad);
        } else {
            Habilidad nuevaHabilidad = new Habilidad(habilidad);
            habilidades.put(habilidad,nuevaHabilidad);
            return nuevaHabilidad;
        }
    }
}
