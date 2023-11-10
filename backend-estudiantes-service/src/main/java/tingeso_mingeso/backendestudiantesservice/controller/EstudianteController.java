package tingeso_mingeso.backendestudiantesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso_mingeso.backendestudiantesservice.entity.EstudianteEntity;
import tingeso_mingeso.backendestudiantesservice.service.EstudianteService;
import tingeso_mingeso.backendestudiantesservice.model.CuotasEntity;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;

    @GetMapping()
    public ResponseEntity<List<EstudianteEntity>> list() {
        List<EstudianteEntity> students = estudianteService.getStudents();
        if(students.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(students);
    }

    @PostMapping()
    public ResponseEntity<EstudianteEntity> save(@RequestBody EstudianteEntity student) {
        EstudianteEntity studentNew = estudianteService.saveStudent(student);
        return ResponseEntity.ok(studentNew);
    }

    @GetMapping("/paymenttype/{rut}/{paymentType}")
    public ResponseEntity<EstudianteEntity> setPaymentType(@PathVariable("rut") String rut, @PathVariable("paymentType") String paymentType) {
        EstudianteEntity student = estudianteService.setPaymentType(rut, paymentType);
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/byRut/{rut}")
    public ResponseEntity<EstudianteEntity> getByRut(@PathVariable("rut") String rut) {
        EstudianteEntity student = estudianteService.getByRut(rut);
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/cuotas/{rut}")
    public ResponseEntity<ArrayList<CuotasEntity>> getCuotas(@PathVariable("rut") String rut) {
        ArrayList<CuotasEntity> cuotas = estudianteService.getCuotas(rut);
        return ResponseEntity.ok(cuotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteEntity> getById(@PathVariable("id") Long id) {
        EstudianteEntity student = estudianteService.getStudentById(id);
        if(student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

}