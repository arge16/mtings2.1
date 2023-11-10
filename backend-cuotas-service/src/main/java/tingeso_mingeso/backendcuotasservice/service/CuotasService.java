package tingeso_mingeso.backendcuotasservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso_mingeso.backendcuotasservice.entity.CuotasEntity;
import tingeso_mingeso.backendcuotasservice.model.EstudianteEntity;
import tingeso_mingeso.backendcuotasservice.repository.CuotasRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class CuotasService {
    @Autowired
    CuotasRepository cuotasRepository;

    @Autowired
    AdministracionService administracionService;

    @Autowired
    RestTemplate restTemplate;

    public CuotasEntity saveInstallment(CuotasEntity installment){
        return cuotasRepository.save(installment);
    }

    public boolean existsByRut(String rut){
        return cuotasRepository.existsByRut(rut);
    }


    public Optional<CuotasEntity> getInstallmentById(Long id) {
        return cuotasRepository.findById(id);
    }

    public ArrayList<CuotasEntity> getAllByRut(String rut){
        return cuotasRepository.findByRut(rut);
    }


    public EstudianteEntity findStudentByRut(String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                "http://localhost:8080/estudiante/byRut/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EstudianteEntity>() {}
        );
        return response.getBody();
    }

    public EstudianteEntity setPaymentType(String paymentType, String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                "http://localhost:8080/estudiante/paymenttype/"+rut+ "/"+paymentType,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EstudianteEntity>() {}
        );
        return response.getBody();
    }

    public Integer maxInstallments(String rut){
        EstudianteEntity student = findStudentByRut(rut);
        return administracionService.maxInstallments(student.getSchool_type());
    }

    public void generarPagoContado(String rut) {
        if(!existsByRut(rut)){
            EstudianteEntity student = findStudentByRut(rut);
            setPaymentType("Contado", rut);
            LocalDate date = LocalDate.now();
            CuotasEntity matricula = new CuotasEntity();

            matricula.setDue_date(date);
            matricula.setRut(rut);
            matricula.setAmount(70000);
            matricula.setDiscount(0);
            matricula.setInterest(0);
            matricula.setTotal(70000+( 1500000 * 0.5));
            matricula.setStatus("Unpaid");
            cuotasRepository.save(matricula);

            CuotasEntity arancel = new CuotasEntity();
            arancel.setDue_date(date);
            arancel.setRut(rut);
            arancel.setAmount(750000);
            arancel.setDiscount(0.5);
            arancel.setInterest(0);
            arancel.setTotal(70000+( 1500000 * 0.5));
            arancel.setStatus("Unpaid");
            cuotasRepository.save(arancel);
        }
    }

    public void generarCuotas(String rut, int cantidadCuotas) {
        if(!existsByRut(rut)){
            EstudianteEntity student =  findStudentByRut(rut);
            setPaymentType("Cuotas", rut);
            LocalDate date = LocalDate.now();
            int dayOfMonth = date.getDayOfMonth();
            if (dayOfMonth < 10) {
                // Si el día actual es anterior al 10 del mes, ajusta a día 10 del mes actual
                date = date.withDayOfMonth(10);
            } else {
                // Si el día actual es igual o posterior al 10 del mes, ajusta a día 10 del mes siguiente
                date = date.plusMonths(1).withDayOfMonth(10);
            }

            double discountbygraduationyear = administracionService.discountByGraduationYear(student.getGraduation_year());
            double discountbyschooltype = administracionService.discountBySchoolType(student.getSchool_type());
            double totalamount =  1500000 - ((discountbygraduationyear + discountbyschooltype) * 1500000) +70000;
            double installmentAmount = totalamount / cantidadCuotas;
            int roundedInstallmentAmount = (int) Math.ceil(installmentAmount); // Redondear al entero mayor
            CuotasEntity matricula = new CuotasEntity();
            matricula.setDue_date(date);
            matricula.setRut(rut);
            matricula.setAmount(70000);
            matricula.setDiscount(0);
            matricula.setInterest(0);
            matricula.setTotal(70000);
            matricula.setStatus("Unpaid");
            cuotasRepository.save(matricula);
            for (int i = 1; i <= cantidadCuotas; i++) {
                CuotasEntity installment = new CuotasEntity();
                installment.setDue_date(date);
                installment.setRut(rut);
                installment.setAmount(roundedInstallmentAmount);
                installment.setDiscount(discountbygraduationyear + discountbyschooltype);
                installment.setInterest(0);
                installment.setTotal(totalamount);
                installment.setStatus("Unpaid");
                date = date.plusMonths(1);
                cuotasRepository.save(installment);
            }
        }
    }

    public CuotasEntity markPaid(Long id){
        Optional<CuotasEntity> installment = cuotasRepository.findById(id);
        installment.get().setStatus("Paid");
        LocalDate paymentDay = LocalDate.now();
        installment.get().setPayment_date(paymentDay);
        return cuotasRepository.save(installment.get());
    }

}
