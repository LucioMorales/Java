package servicios;

import entidades.Habilidad;
import entidades.Mascota;
import jakarta.persistence.EntityManager;
import repositorios.RepositorioMascotas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ServicioMascotas {
    private List<Mascota> mascotas;
    private ServicioHabilidades servicioHabilidades;
    private RepositorioMascotas repositorio;

    public ServicioMascotas(EntityManager em){
        this.mascotas = new ArrayList<>();
        this.servicioHabilidades = new ServicioHabilidades();
        this.repositorio = new RepositorioMascotas(em);
    }

    public void guardarMascotasEnBd(){
        repositorio.guardarTodo(mascotas);
    }

    public void guardarVivas(){
        List<Mascota> vivas = mascotas
                .stream()
                .filter(Mascota::estaViva).toList();
        repositorio.guardarTodo(vivas);
    }

    public void cargarMascotas(){
        String archivoCsv = "mascotas.csv";
        
        try(BufferedReader br = new BufferedReader(new FileReader(archivoCsv));) {
            String linea = br.readLine();
            while((linea = br.readLine()) != null){
                String[] partes = linea.split(",");
                String nombre = partes[0];
                int energia = Integer.parseInt(partes[1]);
                int humor = Integer.parseInt(partes[2]);
                String[] habilidades = partes[3].split(";");
                List<Habilidad> listaHabilidades = Arrays.stream(habilidades)
                        .map(h -> servicioHabilidades.getOrCreateHabilidad(h))
                        .toList();

                Mascota m = new Mascota();
                m.setNombre(nombre);
                m.setNivelEnergia(energia);
                m.setNivelHumor(humor);
                m.setHabilidades(new HashSet<>(listaHabilidades));

                mascotas.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarMascotas(){
        this.mascotas.forEach(System.out::println);
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public List<Mascota> getFelices(){
        return repositorio.getFelices();
    }
}

