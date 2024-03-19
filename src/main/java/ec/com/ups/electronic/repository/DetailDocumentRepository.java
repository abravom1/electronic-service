package ec.com.ups.electronic.repository;

import ec.com.ups.electronic.source.entity.DetailDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailDocumentRepository extends JpaRepository<DetailDocument, String> {
}
