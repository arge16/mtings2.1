package tingeso_mingeso.backendexamenesservice.service;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tingeso_mingeso.backendexamenesservice.entity.ExamenesEntity;
import tingeso_mingeso.backendexamenesservice.model.CuotasEntity;
import tingeso_mingeso.backendexamenesservice.model.EstudianteEntity;
import org.springframework.http.HttpEntity;
import tingeso_mingeso.backendexamenesservice.repository.ExamenesRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExamenesService {
    @Autowired
    ExamenesRepository examenesRepository;

    @Autowired
    RestTemplate restTemplate;

    private final Logger logg = LoggerFactory.getLogger(ExamenesService.class);
    public ExamenesEntity save(ExamenesEntity examen){
        return examenesRepository.save(examen);
    }
    public ArrayList<ExamenesEntity> getAllByRut(String rut){
        return examenesRepository.findByRut(rut);
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

    public CuotasEntity saveCuota(CuotasEntity cuota) {
        HttpEntity<CuotasEntity> request = new HttpEntity<CuotasEntity>(cuota);
        CuotasEntity cuotaNew = restTemplate.postForObject("http://localhost:8080/cuotas", request, CuotasEntity.class);
        return cuotaNew;
    }

    public double discountByExam(int examGrade) {
        double discountToApply = 0;
        if ((examGrade >= 950) && (examGrade <= 1000)) {
            discountToApply = 0.1;
        } else if ((examGrade >= 900) && (examGrade < 950))  {
            discountToApply = 0.05;
        } else if ((examGrade >= 850) && (examGrade < 900)) {
            discountToApply = 0.02;
        }
        return discountToApply;
    }

    public double interestRate(int monthsLate) {
        double interest = 0;
        if (monthsLate == 1) {
            interest = 0.03;
        } else if (monthsLate == 2)  {
            interest = 0.06;
        } else if (monthsLate == 3) {
            interest = 0.09;
        }
        else if (monthsLate > 3) {
            interest = 0.15;
        }
        return interest;
    }
    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarDataDB(String fecha, String rut, String score){
        ExamenesEntity newData = new ExamenesEntity();
        newData.setDate_of_exam(fecha);
        newData.setRut(rut);
        newData.setScore(Integer.parseInt(score));
        save(newData);
    }

    public void setInterestRate(ArrayList<CuotasEntity> installments) {
        //calcular la cuota con mas meses de atraso
        int monthsLate = 0;
        for (CuotasEntity installment:installments) {
            if (installment.getAmount() != 70000 && installment.getStatus().equals("Unpaid") && LocalDate.now().isAfter(installment.getDue_date())) {
                monthsLate++ ;
            }
        }
        //Hasta aqui tengo la fecha mas antigua de las cuotas impagas
        double interest = interestRate(monthsLate);
        for (CuotasEntity installment1:installments) {
            if (installment1.getAmount()!=70000 && installment1.getStatus().equals("Unpaid")){
                installment1.setInterest(interest);
                installment1.setAmount((int) (installment1.getAmount() * (1 + interest)));
                saveCuota(installment1);
            }
        }
    }

    public ArrayList<CuotasEntity> getAllInstallmentsByRut(String rut) {
        ResponseEntity<ArrayList<CuotasEntity>> response = restTemplate.exchange(
                "http://localhost:8080/cuotas/bystudent/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<CuotasEntity>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // Manejar un escenario de error si es necesario
            return new ArrayList<>(); // Otra opción podría ser lanzar una excepción
        }
    }

    public void generateSpreadsheet(String rut){
        ArrayList<ExamenesEntity> exams = getAllByRut(rut);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateTest = LocalDate.parse(exams.get(0).getDate_of_exam(), formatter);
        int score = 0;

        for (ExamenesEntity examEntity : exams) {  //En este ciclo se obtiene la fecha mas reciente de los examenes
            score = score + examEntity.getScore();
            String dateAux = examEntity.getDate_of_exam();
            LocalDate date = LocalDate.parse(dateAux, formatter);
            if (date.isAfter(dateTest)) {
                dateTest = date;
            }
        }
        int  scoreAverage = score / exams.size();

        ArrayList<CuotasEntity> installments = getAllInstallmentsByRut(rut);
        for (CuotasEntity installment : installments) {

            //Descuentos por examenes
            if (installment.getDue_date().isAfter(dateTest) ) {
                double discountByExam = discountByExam(scoreAverage);
                double totalDiscount = installment.getDiscount() + discountByExam;
                totalDiscount = Math.round(totalDiscount * 100.0) / 100.0;
                double installmentAmount = (installment.getAmount() * discountByExam) + installment.getAmount();
                installment.setDiscount(totalDiscount);
                installment.setAmount( (int) Math.ceil(installmentAmount));
                saveCuota(installment);
            }
        }
        setInterestRate(installments);
    }

    public EstudianteEntity generateReport(String rut) {
        EstudianteEntity student = findStudentByRut(rut);
        ArrayList<ExamenesEntity> exams = getAllByRut(rut);
        ArrayList<CuotasEntity> installments = getAllInstallmentsByRut(rut);

        double scoreAverage = 0;
        double total = 0;
        int installmentsPaid = 0;
        double debtPaid = 0;
        LocalDate lastPayment = LocalDate.MIN;
        double debtToPay = 0;
        int installmentsLate = 0;
        for (ExamenesEntity exam : exams) {
            scoreAverage += exam.getScore();
        }
        for (CuotasEntity installment : installments) {
            if (installment.getAmount() != 70000) {
                total += installment.getAmount();
            }
            if (installment.getStatus().equals("Paid")) {
                installmentsPaid++;
                debtPaid += installment.getAmount();
                if (installment.getPayment_date() != null && installment.getPayment_date().isAfter(lastPayment)) {
                    lastPayment = installment.getPayment_date();
                }
            }
            if (installment.getStatus().equals("Unpaid")) {
                debtToPay += installment.getAmount();
                if (installment.getDue_date().isBefore(LocalDate.now())) {
                    installmentsLate++;
                }
            }
        }
        student.setInstallments(installments.size()-1);
        student.setTotalDebt(total);
        student.setTotalExams(exams.size());
        student.setScoresAverage(scoreAverage/exams.size());
        student.setInstallmentsPaid(installmentsPaid);
        student.setDebtPaid(debtPaid);
        if(lastPayment != LocalDate.MIN){
            student.setLastPayment(lastPayment);
        }
        else {
            student.setLastPayment(null);
        }
        student.setDebtToPay(debtToPay);
        student.setInstallmentsLate(installmentsLate);
        return student;
    }

}
