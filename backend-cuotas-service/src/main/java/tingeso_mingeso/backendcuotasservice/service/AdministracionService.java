package tingeso_mingeso.backendcuotasservice.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Data
@Service
public class AdministracionService {

    public double discountBySchoolType(String typeSchool) {
        double discount = 0;
        if (Objects.equals(typeSchool, "Municipal")) {
            discount = 0.2;
        } else if (Objects.equals(typeSchool, "Subvencionado")) {
            discount = 0.1;

        }
        return discount;
    }
    public double discountByGraduationYear(int graduationYear) {
        int yearsGraduated = 2023 - graduationYear;
        double discountToApply = 0;
        if (yearsGraduated < 1) {
            discountToApply = 0.15;
        } else if (yearsGraduated == 1 ||  yearsGraduated == 2)  {
            discountToApply = 0.08;
        } else if (yearsGraduated == 3 ||  yearsGraduated == 4) {
            discountToApply = 0.04;}
        return discountToApply;
    }

    public Integer maxInstallments(String typeSchool) {
        Integer max = 0;
        if (Objects.equals(typeSchool, "Municipal")) {
            max = 10;
        } else if (Objects.equals(typeSchool, "Subvencionado"))  {
            max = 7;
        } else if (Objects.equals(typeSchool, "Privado")) {
            max = 4;
        }
        return max;
    }






}
