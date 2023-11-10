package tingeso_mingeso.backendexamenesservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "examenes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamenesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String rut;
    private int score;
    private String date_of_exam;
}
