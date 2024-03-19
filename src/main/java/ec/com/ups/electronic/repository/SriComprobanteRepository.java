package ec.com.ups.electronic.repository;

import ec.com.ups.electronic.source.entity.SriComprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SriComprobanteRepository extends JpaRepository<SriComprobante, String> {

    @Query(value="from SriComprobante s where s.documento.id = :documentId")
    SriComprobante findByDocumentoId(@Param("documentId") String documentId);
}
