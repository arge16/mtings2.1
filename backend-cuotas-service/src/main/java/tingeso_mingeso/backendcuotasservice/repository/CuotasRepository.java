package tingeso_mingeso.backendcuotasservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tingeso_mingeso.backendcuotasservice.entity.CuotasEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CuotasRepository extends JpaRepository<CuotasEntity, Long> {

    public ArrayList<CuotasEntity> findByRut(String rut);
    public boolean existsByRut(String rut);

}
