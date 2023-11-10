package tingeso_mingeso.backendexamenesservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotasEntity {


    private String rut;
    private int amount;
    private double discount;
    private double interest;
    private double total;
    private String status;
    private LocalDate due_date;
    private LocalDate payment_date;
    private int studentId;
}
