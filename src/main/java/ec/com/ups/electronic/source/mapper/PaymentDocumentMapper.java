package ec.com.ups.electronic.source.mapper;



import ec.com.ups.electronic.source.entity.PaymentDocument;
import ec.com.ups.electronic.source.model.PaymentDocumentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentDocumentMapper {

    @Mapping(source = "documentPaymentId", target = "id")
    @Mapping(source = "paymentMethod", target = "formaPago")
    @Mapping(source = "value", target = "valor")
    PaymentDocument mapPaymentDocumentModelToPaymentDocument(PaymentDocumentModel paymentDocumentModel);



}
