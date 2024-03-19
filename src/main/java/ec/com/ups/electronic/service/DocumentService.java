package ec.com.ups.electronic.service;

import ec.com.ups.electronic.source.model.request.DocumentModelRequest;
import ec.com.ups.electronic.source.model.response.DocumentDataResponse;
import ec.com.ups.electronic.source.model.response.SriReceiptDataResponse;

public interface DocumentService {
    DocumentDataResponse createDocument(DocumentModelRequest request);
    SriReceiptDataResponse consultDocument(String documentId);
}
