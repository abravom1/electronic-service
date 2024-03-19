package ec.com.ups.electronic.repository;

import ec.com.ups.electronic.source.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, String> {
}
