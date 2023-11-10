package tingeso_mingeso.backendestudiantesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso_mingeso.backendestudiantesservice.entity.EstudianteEntity;
import tingeso_mingeso.backendestudiantesservice.repository.EstudianteRepository;
import tingeso_mingeso.backendestudiantesservice.model.CuotasEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;


    @Autowired
    RestTemplate restTemplate;

    public List<EstudianteEntity> getStudents() {
        return (List<EstudianteEntity>) estudianteRepository.findAll();
    }

    public EstudianteEntity saveStudent(EstudianteEntity student) {
        return estudianteRepository.save(student);
    }

    public EstudianteEntity getByRut(String rut) {
        return estudianteRepository.findByRut(rut);
    }

    public EstudianteEntity setPaymentType(String rut, String payment_type) {
        EstudianteEntity student = estudianteRepository.findByRut(rut);
        student.setPaymentType(payment_type);
        return estudianteRepository.save(student);
    }

    public ArrayList<CuotasEntity> getCuotas(String rut) {
        ArrayList<CuotasEntity> cuotas = restTemplate.getForObject("http://backend-cuotas-service/cuotas/bystudent/" + rut, ArrayList.class);
        return cuotas;
    }

    public EstudianteEntity getStudentById(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

}
