package tingeso_mingeso.backendexamenesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tingeso_mingeso.backendexamenesservice.entity.ExamenesEntity;
import tingeso_mingeso.backendexamenesservice.model.EstudianteEntity;
import tingeso_mingeso.backendexamenesservice.service.ExamenesService;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/examen")
public class ExamenesController {
    @Autowired
    ExamenesService examenesService;

    @GetMapping("/byrut/{rut}")
    public ResponseEntity<ArrayList<ExamenesEntity>> getByRut(@PathVariable("rut") String rut) {
        ArrayList<ExamenesEntity> examenes = examenesService.getAllByRut(rut);
        return ResponseEntity.ok(examenes);
    }

    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException {
        examenesService.guardar(file);
        examenesService.leerCsv("Examen.csv");
    }

    @GetMapping("/Generate-report/{rut}")
    public ResponseEntity<EstudianteEntity>  generarReporte(@PathVariable String rut){
        EstudianteEntity estudiante = examenesService.generateReport(rut);
        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/Generate-spreadsheet/{rut}")
    public ResponseEntity<Void>  generarPlanilla(
            @PathVariable String rut
    ) {
        examenesService.generateSpreadsheet(rut);
        return ResponseEntity.noContent().build();
    }

}
