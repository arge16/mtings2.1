package tingeso_mingeso.backendexamenesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tingeso_mingeso.backendexamenesservice.entity.ExamenesEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ExamenesRepository extends JpaRepository<ExamenesEntity, Long> {
    public ArrayList<ExamenesEntity> findByRut(String rut);
}
