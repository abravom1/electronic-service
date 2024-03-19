package ec.com.ups.electronic.repository;


import ec.com.ups.electronic.source.entity.MensajeSriResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeSriResponseRepository extends JpaRepository<MensajeSriResponse, String> {
}
