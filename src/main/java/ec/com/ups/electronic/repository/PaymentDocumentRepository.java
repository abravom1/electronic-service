package ec.com.ups.electronic.repository;


import ec.com.ups.electronic.source.entity.PaymentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDocumentRepository extends JpaRepository<PaymentDocument, String> {
}
