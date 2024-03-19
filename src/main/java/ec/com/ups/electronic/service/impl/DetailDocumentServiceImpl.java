package ec.com.ups.electronic.service.impl;



import ec.com.ups.electronic.repository.DetailDocumentRepository;
import ec.com.ups.electronic.source.entity.DetailDocument;
import ec.com.ups.electronic.source.entity.Document;
import ec.com.ups.electronic.source.mapper.DetailDocumentMapper;
import ec.com.ups.electronic.source.model.request.DocumentModelRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class DetailDocumentServiceImpl {
	private final DetailDocumentMapper detailDocumentMapper;
	private final DetailDocumentRepository detailDocumentRepository;

	@Transactional
	public List<DetailDocument> saveDocumentDetail(Document document, DocumentModelRequest request) {
		List<DetailDocument> listDetalle = request.documentDetail().stream()
				.map(det -> {
					DetailDocument detalle = detailDocumentMapper.mapDetailDocumentModelToDetailDocument(det);
					detalle.setDocumentoId(document.getId());
					return detalle;
				})
				.collect(Collectors.toList());
		return detailDocumentRepository.saveAll(listDetalle);
	}






}
