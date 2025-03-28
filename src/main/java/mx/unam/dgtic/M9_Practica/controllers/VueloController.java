package mx.unam.dgtic.M9_Practica.controllers;

import mx.unam.dgtic.M9_Practica.modelos.Vuelo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/vuelo")
public class VueloController {
    private HashMap<Integer, Vuelo> vuelos;

    public VueloController(){
        vuelos = new HashMap<>();
        vuelos.put(1, new Vuelo(1, "México", "España", "15:50 HRS"));
        vuelos.put(2, new Vuelo(2, "Francia", "Bélgica", "08:50 HRS"));
        vuelos.put(3, new Vuelo(3, "Noruega", "Japón", "22:50 HRS"));
    }

    @GetMapping("/creditos")
    public String ping(){
        String html = "<html><body><h2>Participantes del equipo:</h2>" +
                "<ul>" +
                "<li>Manuel Mérida Aguilar</li>" +
                "<li>Ernesto Velasco Arciniega</li>" +
                "<li>Rodrigo Ivan Olvera Martinez</li>" +
                "<li>Omar Nieto Crisóstono</li>" +
                "</ul>" +
                "</body></html>";
        return html;
    }

    @GetMapping(value = "/", headers = {"Accept=application/json"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, Vuelo>> getAll(){
        return new ResponseEntity<>(vuelos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> getVuelo(@PathVariable Integer id){
        Vuelo vueloDB = vuelos.get(id);

        if(vueloDB != null) return ResponseEntity.ok(vueloDB);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vuelo> addbook(@RequestBody Vuelo vuelo){
        int id = 1;
        while (vuelos.containsKey(id)){id++;}

        vuelo.setId(id);
        vuelos.put(vuelo.getId(), vuelo);

        return new ResponseEntity<>(vuelo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> actualizarVuelo(@PathVariable Integer id, @RequestBody Vuelo vuelo){
        if(vuelos.containsKey(id)){
            vuelo.setId(id);
            vuelos.replace(vuelo.getId(), vuelo);
            return new ResponseEntity<>(vuelos.get(id), HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vuelo> actualizarParcial(@PathVariable Integer id, @RequestBody Vuelo vuelo){
        Vuelo vueloDB = vuelos.get(id);

        if(vueloDB == null){
            return ResponseEntity.notFound().build();        }
        if(vuelo.getDestino() != null)
            vueloDB.setDestino(vuelo.getDestino());
        if(vuelo.getOrigen() != null)
            vueloDB.setOrigen(vuelo.getOrigen());
        if(vuelo.getHoraSalida() != null)
            vueloDB.setHoraSalida(vuelo.getHoraSalida());
        vuelos.replace(id, vueloDB);

        return ResponseEntity.ok(vuelos.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vuelo> eliminarVuelo(@PathVariable Integer id){
        Vuelo vuelo = vuelos.get(id);
        if (vuelo != null) {
            vuelos.remove(id);
            return ResponseEntity.ok(vuelo);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
