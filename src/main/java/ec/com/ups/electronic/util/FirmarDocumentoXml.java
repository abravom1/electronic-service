package ec.com.ups.electronic.util;

import ec.com.ups.electronic.source.xml.PassStoreKS;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import org.apache.xerces.dom.DOMOutputImpl;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class FirmarDocumentoXml {
    public String generarXmlFirmado(File archivoGenerado, String pathXmlFirmado){
        return crearFirma(archivoGenerado, pathXmlFirmado);
    }
    public String crearFirma(File archivoGenerado, String pathXmlFirmado) {
        try {

            String rutaCertificado = DatosUtil.RUTA_CERTIFICADO_FIRMA;
            String claveCertificado = DatosUtil.CLAVE_CERTIFICADO;

            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(rutaCertificado), claveCertificado.toCharArray());

            IPKStoreManager storeManager = new KSStore(ks, new PassStoreKS(claveCertificado));
            List<X509Certificate> certs = storeManager.getSignCertificates();
            if (certs == null || certs.isEmpty())
                throw new Exception("No se pudo encontrar un certificado válido para firmar el archivo");

            X509Certificate certificado = certs.get(0).getKeyUsage()[0]?certs.get(0):certs.get(1);
            PrivateKey clavePrivada = storeManager.getPrivateKey(certificado);
            Provider provider = storeManager.getProvider(certificado);
            certificado.checkValidity(new GregorianCalendar().getTime());

            if (clavePrivada == null)
                throw new Exception("No se pudo acceder a la clave privada del certificado");

            DataToSign datosAFirmar = prepararDatosAFirmar(archivoGenerado);
            FirmaXML firma = new FirmaXML();
            Document docSigned;
            try {
                Object[] res = firma.signFile(certificado, datosAFirmar, clavePrivada, provider);
                docSigned = (Document) res[0];
            } catch (Exception ex) {
                String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Error al firmar archivo.";
                return "Error al momento de realizar la firma. "+errorMessage;
            }
            FileOutputStream fos = new FileOutputStream(pathXmlFirmado);
            writeXML(docSigned, fos);
            fos.close();
            ks.load(null, null); // Limpiar el keystore después de usarlo
            return DatosUtil.PROCESO_CORRECTO;
        } catch (CertificateExpiredException ex) {
            return "El certificado con el que intenta firmar el comprobante está expirado. Favor actualizar su certificado digital con la Autoridad Certificadora";
        } catch (ParserConfigurationException | SAXException ex) {
            return "Archivo XML a firmar mal definido o estructurado";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Error al firmar archivo.";
            return "Error al firmar archivo: " + errorMessage;
        }
    }

    private DataToSign prepararDatosAFirmar(File archivoGenerado) throws ParserConfigurationException, IOException, SAXException {
        DataToSign datosAFirmar = new DataToSign();
        datosAFirmar.setXadesFormat(es.mityc.javasign.EnumFormatoFirma.XAdES_BES);
        datosAFirmar.setEsquema(XAdESSchemas.XAdES_132);
        datosAFirmar.setXMLEncoding("UTF-8");
        datosAFirmar.setEnveloped(true);
        datosAFirmar.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        datosAFirmar.setParentSignNode("comprobante");

        try (FileInputStream fis = new FileInputStream(archivoGenerado)) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setIgnoringComments(true);
            dbf.setIgnoringElementContentWhitespace(true);
            datosAFirmar.setDocument(dbf.newDocumentBuilder().parse(fis));
        }
        return datosAFirmar;
    }

    public static void writeXML(Document doc, OutputStream out) {
        OutputStreamWriter osw = new OutputStreamWriter(out);
        DOMOutputImpl domoutputimpl = new DOMOutputImpl();
        domoutputimpl.setEncoding(doc.getXmlEncoding());
        domoutputimpl.setCharacterStream(osw);
        DOMImplementationLS dils = (DOMImplementationLS)doc.getImplementation();
        LSSerializer serializer = dils.createLSSerializer();
        serializer.getDomConfig().setParameter("namespaces", false);
        serializer.getDomConfig().getParameterNames();
        serializer.write(doc, domoutputimpl);
    }

}
