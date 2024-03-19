package ec.com.ups.electronic.source.xml.buils;


import ec.com.ups.electronic.source.classTest.Empresa;
import ec.com.ups.electronic.source.classTest.Entidad;
import ec.com.ups.electronic.source.entity.DetailDocument;
import ec.com.ups.electronic.source.entity.Document;
import ec.com.ups.electronic.source.entity.PaymentDocument;
import ec.com.ups.electronic.source.enums.TipoImpuestoEnum;
import ec.com.ups.electronic.source.enums.TipoImpuestoIvaEnum;
import ec.com.ups.electronic.source.xml.FacturaImpuestoXml;
import ec.com.ups.electronic.source.xml.FacturaXml;
import ec.com.ups.electronic.source.xml.InfoTributariaXml;
import ec.com.ups.electronic.util.DatosUtil;
import ec.com.ups.electronic.util.MetodosVarios;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class FacturaBuild {
    private final MetodosVarios metodosVarios;

    public FacturaXml generaFacturaXml(
            Document document,
            List<DetailDocument> listDetails,
            List<PaymentDocument> listPay){

        Entidad entidad = new Entidad("nombre",
                "apellido",
                "nombreCompleto",
                "0603604349",
                "prueba@hotmail.com",
                "0959639918",
                "norte-guayaquil");
        Empresa empresa = new Empresa("NOMBRE-EMPRESA-PRUEBA",
                "0930555529001",
                "RAZON-SOCIAL-PRUEBA",
                "NOMBRE-COMERCIAL",
                "GUAYQUIL",
                "0959639918",
                "prueba@prueba.com",
                "001",
                "1");

        FacturaXml facturaXml = new FacturaXml();
        facturaXml.setVersion("1.0.0");
        facturaXml.setId("comprobante");
        facturaXml.setInfoTributaria(crearInfoTributaria(empresa, document));
        facturaXml.setInfoFactura(crearInfoFacturaXml(empresa, entidad, document, listPay));
        facturaXml.setDetalles(crearDetalleXml(listDetails));
        facturaXml.setInfoAdicional(crearCampoAdicionalXml(entidad));
        return facturaXml;
    }

    public InfoTributariaXml crearInfoTributaria(Empresa empresa, Document document){
        InfoTributariaXml infoTributaria = new InfoTributariaXml();
        infoTributaria.setAmbiente(empresa.getTipoAmbiente());
        infoTributaria.setTipoEmision(DatosUtil.TIPO_EMISION);
        infoTributaria.setRazonSocial(empresa.getRazonSocial()!=null?empresa.getRazonSocial():"");
        infoTributaria.setNombreComercial(empresa.getNombreComercial()!=null?empresa.getNombreComercial():"");
        infoTributaria.setRuc(empresa.getNumeroIdentificacion());
        infoTributaria.setClaveAcceso(obtenerClaveAcceso(empresa, document.getNumeroDocumento()));
        infoTributaria.setCodDoc(DatosUtil.CODIGO_SRI_TIPO_CMP_FACTURA);
        infoTributaria.setEstab(document.getNumeroDocumento().split("-")[0]);
        infoTributaria.setPtoEmi(document.getNumeroDocumento().split("-")[1]);
        infoTributaria.setSecuencial(metodosVarios.aplicaRellenar("I", "0", 9L, document.getNumeroDocumento().split("-")[2]));
        infoTributaria.setDirMatriz(empresa.getDireccion());
        return infoTributaria;
    }

    public String obtenerClaveAcceso(Empresa empresa, String numeroDocumento){
        String claveAcceso = metodosVarios.obtenerFechaComoCadena(new Date())+
                DatosUtil.CODIGO_SRI_TIPO_CMP_FACTURA +
                empresa.getNumeroIdentificacion()+
                empresa.getTipoAmbiente()+
                numeroDocumento.split("-")[0]+
                numeroDocumento.split("-")[1]+
                metodosVarios.aplicaRellenar("I", "0", 9L, numeroDocumento.split("-")[2])+
                metodosVarios.aplicaRellenar("I", "0", 8, String.valueOf(Long.parseLong(numeroDocumento.split("-")[2])).trim()) +
                DatosUtil.TIPO_EMISION;
        return claveAcceso.concat(String.valueOf(metodosVarios.generaDigitoModulo11(claveAcceso)));
    }

    public FacturaXml.InfoFactura crearInfoFacturaXml(Empresa empresa, Entidad entidad, Document documento, List<PaymentDocument> listPay){
        FacturaXml.InfoFactura infoFacturaXml = new FacturaXml.InfoFactura();
        infoFacturaXml.setFechaEmision(metodosVarios.formatearFecha(new Date()));
        infoFacturaXml.setDirEstablecimiento(empresa.getDireccion());
        if (entidad.getIdentificacion().equals(DatosUtil.ID_FICTICIO_CONSUMIDOR_FINAL_10) ||
                entidad.getIdentificacion().equals(DatosUtil.ID_FICTICIO_CONSUMIDOR_FINAL_13)){
            infoFacturaXml.setTipoIdentificacionComprador(DatosUtil.TIPO_IDENTIFICACION_SRI_CEDULA);
            infoFacturaXml.setIdentificacionComprador(DatosUtil.ID_FICTICIO_CONSUMIDOR_FINAL_13);
            infoFacturaXml.setRazonSocialComprador(DatosUtil.NOMBRE_FICTICIO_CONSUMIDOR_FINAL);
        }else{
            infoFacturaXml.setTipoIdentificacionComprador(DatosUtil.TIPO_IDENTIFICACION_SRI_CEDULA);
            infoFacturaXml.setIdentificacionComprador(entidad.getIdentificacion());
            infoFacturaXml.setRazonSocialComprador(metodosVarios.obtenerDescripcionSinCaracteresEspeciales((entidad.getNombre()!=null?entidad.getNombre():"")));
            if (empresa.getTipoAmbiente().equals(DatosUtil.AMBIENTE_DESARROLLO))
                infoFacturaXml.setRazonSocialComprador("PRUEBAS SERVICIO DE RENTAS INTERNAS");
        }//end if
        infoFacturaXml.setTotalSinImpuestos(documento.getSubtotal().subtract(documento.getValorDescuento()));
        infoFacturaXml.setTotalDescuento(documento.getValorDescuento());
        infoFacturaXml.setPropina(BigDecimal.ZERO.setScale(2));
        infoFacturaXml.setImporteTotal(documento.getValorTotal());
        infoFacturaXml.setMoneda("DOLAR");
        infoFacturaXml.setTotalConImpuestos(crearTotalImpuestoXml(documento));
        infoFacturaXml.setPagos(crearPagoXml(listPay));
        return  infoFacturaXml;
    }

    public FacturaXml.InfoFactura.TotalConImpuestos crearTotalImpuestoXml(Document document){
        FacturaXml.InfoFactura.TotalConImpuestos totalConImpuestos =  new FacturaXml.InfoFactura.TotalConImpuestos();
        List<FacturaXml.InfoFactura.TotalConImpuestos.TotalImpuesto> listTotalImpuestoXml = new ArrayList<>();
        FacturaXml.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuestoXml = new FacturaXml.InfoFactura.TotalConImpuestos.TotalImpuesto();

        totalImpuestoXml.setCodigo(TipoImpuestoEnum.IVA.getCode());

        if (document.getValorImpuesto().equals(new BigDecimal("0")) ){
            totalImpuestoXml.setCodigoPorcentaje(TipoImpuestoIvaEnum.IVA_VENTA_0.getCode());
        }else{
            if (document.getPorcentajeImpuesto().equals(new BigDecimal("12")))
                totalImpuestoXml.setCodigoPorcentaje(TipoImpuestoIvaEnum.IVA_VENTA_12.getCode());
            if (document.getPorcentajeImpuesto().equals(new BigDecimal("14")))
                totalImpuestoXml.setCodigoPorcentaje(TipoImpuestoIvaEnum.IVA_VENTA_14.getCode());
        }
        totalImpuestoXml.setBaseImponible(document.getSubtotal().subtract(document.getValorDescuento()));
        totalImpuestoXml.setValor(document.getValorImpuesto());
        listTotalImpuestoXml.add(totalImpuestoXml);
        totalConImpuestos.setTotalImpuesto(listTotalImpuestoXml);
        return totalConImpuestos;
    }

    public FacturaXml.InfoFactura.Pagos crearPagoXml(List<PaymentDocument> listPay){
        FacturaXml.InfoFactura.Pagos pagos =  new FacturaXml.InfoFactura.Pagos();
        List<FacturaXml.InfoFactura.Pagos.Pago> listPagoXml = new ArrayList<>();
        for (PaymentDocument paymentDocument : listPay) {
            FacturaXml.InfoFactura.Pagos.Pago pagoXml = new FacturaXml.InfoFactura.Pagos.Pago();
            pagoXml.setFormaPago(DatosUtil.FORMA_PAGO_SIN_UTILIZACION_SISTEMA_FINANCIERO_SRI);
            pagoXml.setPlazo("0");
            pagoXml.setTotal(paymentDocument.getValor());
            pagoXml.setUnidadTiempo("dias");
            listPagoXml.add(pagoXml);
        }
        pagos.setPago(listPagoXml);
        return pagos;
    }

    public FacturaXml.Detalles crearDetalleXml(List<DetailDocument> listDetails){
        String codigo = "";
        String descripcion = "";
        BigDecimal precioUnitario = new BigDecimal("0");

        FacturaXml.Detalles detalles =  new FacturaXml.Detalles();
        List<FacturaXml.Detalles.Detalle> listDetalleXml = new ArrayList<>();

        for (DetailDocument det : listDetails) {
            FacturaXml.Detalles.Detalle detalleXml = new FacturaXml.Detalles.Detalle();
            if(det.getProducto()!=null){
                codigo = metodosVarios.obtenerDescripcionSinCaracteresEspeciales(det.getProducto().getCodigo());
                descripcion = metodosVarios.obtenerDescripcionSinCaracteresEspeciales(det.getProducto().getNombre());
                precioUnitario = det.getProducto().getPrecio().multiply(metodosVarios.calcularFactorMultiplicadorIva(det.getPorcentajeImpuesto()));
            }
            detalleXml.setCodigoPrincipal(codigo);
            detalleXml.setDescripcion(descripcion);
            detalleXml.setCantidad(det.getCantidad());
            detalleXml.setPrecioUnitario(precioUnitario.setScale(2, RoundingMode.HALF_UP));
            detalleXml.setDescuento(det.getValorDescuento());
            detalleXml.setPrecioTotalSinImpuesto(det.getSubtotal().subtract(det.getValorDescuento()));
            detalleXml.setImpuestos(crearImpuestoXml(det));
            listDetalleXml.add(detalleXml);
        }
        detalles.setDetalle(listDetalleXml);
        return detalles;
    }

    public FacturaXml.Detalles.Detalle.Impuestos crearImpuestoXml(DetailDocument det){
        FacturaXml.Detalles.Detalle.Impuestos impuestos = new FacturaXml.Detalles.Detalle.Impuestos();
        List<FacturaImpuestoXml> facturaImpuestoXmlList = new ArrayList<>();
        FacturaImpuestoXml facturaImpuestoXml = new FacturaImpuestoXml();
        facturaImpuestoXml.setCodigo(TipoImpuestoEnum.IVA.getCode());
        if (det.getPorcentajeImpuesto().equals(new BigDecimal("0")))
            facturaImpuestoXml.setCodigoPorcentaje(TipoImpuestoIvaEnum.IVA_VENTA_0.getCode());
        if (det.getPorcentajeImpuesto().equals(new BigDecimal("12")))
            facturaImpuestoXml.setCodigoPorcentaje(TipoImpuestoIvaEnum.IVA_VENTA_12.getCode());
        if (det.getPorcentajeImpuesto().equals(new BigDecimal("14")))
            facturaImpuestoXml.setCodigoPorcentaje(TipoImpuestoIvaEnum.IVA_VENTA_14.getCode());
        facturaImpuestoXml.setTarifa(det.getPorcentajeImpuesto());
        facturaImpuestoXml.setBaseImponible(det.getSubtotal().subtract(det.getValorDescuento()));
        facturaImpuestoXml.setValor(det.getValorImpuesto());
        facturaImpuestoXmlList.add(facturaImpuestoXml);
        impuestos.setImpuesto(facturaImpuestoXmlList);

        return impuestos;
    }

    public FacturaXml.InfoAdicional crearCampoAdicionalXml(Entidad entidad){
        FacturaXml.InfoAdicional infoAdicional = new FacturaXml.InfoAdicional();
        List<FacturaXml.InfoAdicional.CampoAdicional> listCampoAdicionalXml = new ArrayList<>();
        FacturaXml.InfoAdicional.CampoAdicional campoAdicionalXml = new FacturaXml.InfoAdicional.CampoAdicional();
        if (entidad.getDireccion()!=null && !entidad.getDireccion().isEmpty()){
            campoAdicionalXml.setNombre("DIRECCION");
            campoAdicionalXml.setValue(entidad.getDireccion());
            listCampoAdicionalXml.add(campoAdicionalXml);
        }
        if (entidad.getTelefonoCelular()!=null && !entidad.getTelefonoCelular().isEmpty()){
            campoAdicionalXml = new FacturaXml.InfoAdicional.CampoAdicional();
            campoAdicionalXml.setNombre("TELEFONO1");
            campoAdicionalXml.setValue(entidad.getTelefonoCelular());
            listCampoAdicionalXml.add(campoAdicionalXml);
        }

        if (entidad.getCorreo()!=null && !entidad.getCorreo().isEmpty()){
            campoAdicionalXml = new FacturaXml.InfoAdicional.CampoAdicional();
            campoAdicionalXml.setNombre("EMAIL");
            campoAdicionalXml.setValue(entidad.getCorreo());
            listCampoAdicionalXml.add(campoAdicionalXml);
        }
        infoAdicional.setCampoAdicional(listCampoAdicionalXml);
        return infoAdicional;
    }

}
