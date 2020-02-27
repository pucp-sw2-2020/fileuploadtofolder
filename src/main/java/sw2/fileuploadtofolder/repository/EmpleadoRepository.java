package sw2.fileuploadtofolder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw2.fileuploadtofolder.entity.Empleado;

public interface EmpleadoRepository
        extends JpaRepository<Empleado, Integer> {
}

