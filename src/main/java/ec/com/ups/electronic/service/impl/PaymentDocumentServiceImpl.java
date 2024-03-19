package ec.com.ups.electronic.service.impl;


import ec.com.ups.electronic.repository.PaymentDocumentRepository;
import ec.com.ups.electronic.source.entity.Document;
import ec.com.ups.electronic.source.entity.PaymentDocument;
import ec.com.ups.electronic.source.mapper.PaymentDocumentMapper;
import ec.com.ups.electronic.source.model.request.DocumentModelRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PaymentDocumentServiceImpl {
	private final PaymentDocumentMapper paymentDocumentMappere;
	private final PaymentDocumentRepository paymentDocumentRepository;

	@Transactional
	public List<PaymentDocument> saveDocumentPayment(Document document, DocumentModelRequest request) {
		List<PaymentDocument> listPago = request.documentPayment().stream()
				.map(det -> {
					PaymentDocument detalle = paymentDocumentMappere.mapPaymentDocumentModelToPaymentDocument(det);
					detalle.setDocumentoId(document.getId());
					return detalle;
				})
				.collect(Collectors.toList());
		return paymentDocumentRepository.saveAll(listPago);
	}






}
