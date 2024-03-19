package ec.com.ups.electronic.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.Autorizacion;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.Mensaje;
import ec.com.ups.electronic.util.wsdl.desarrollo.autorizacion.RespuestaComprobante;
import ec.com.ups.electronic.source.xml.FacturaXml;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MetodosVarios {

	public String obtenerDescripcionSinCaracteresEspeciales(String descripcion) {
		String resul = "";

		if (descripcion != null) {
			resul = descripcion.trim().toUpperCase();
			resul = resul.replace("Ñ", "N").replace("º", " ").replace("Á", "A").replace("É", "E").replace("Í", "I")
					.replace("Ó", "O").replace("Ú", "U").replace("á", "a").replace("é", "e").replace("í", "i")
					.replace("ó", "o").replace("ú", "u").replace("\n", "");
		} // end if

		return resul;
	}

	public String aplicaRellenar(String rellenar, String caracter, long longitud, String valor) {

		String retorno = valor.trim();
		long cantrelleno = longitud - retorno.length();

		for (int i = 0; i < cantrelleno; i++) {
			if (rellenar.equals("D"))
				retorno = retorno + caracter;
			else
				retorno = caracter + retorno;
		}

		return retorno;
	}

	public String obtenerFechaComoCadena(Date fecha){
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(fecha);
		String part[] = date.split("-");
		String fechaCadena = part[2]+part[1]+part[0];
		return fechaCadena;
	}

	public int generaDigitoModulo11(String cadena) {
		int baseMultiplicador = 7;
		System.out.println("CADENA-->" + cadena);
		int[] aux = new int[cadena.length()];
		int multiplicador = 2;
		int total = 0;
		int verificador = 0;
		for (int i = aux.length - 1; i >= 0; i--) {
			aux[i] = Integer.parseInt("" + cadena.charAt(i));
			aux[i] *= multiplicador;
			multiplicador++;
			if (multiplicador > baseMultiplicador) {
				multiplicador = 2;
			}
			total += aux[i];
		}
		if ((total == 0) || (total == 1)) {
			verificador = 0;
		} else {
			verificador = 11 - total % 11 == 11 ? 0 : 11 - total % 11;
		}
		if (verificador == 10) {
			verificador = 1;
		}
		return verificador;
	}

	public String formatearFecha(Date fecha){
		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
		return formateadorFecha.format(fecha);
	}

	public BigDecimal calcularFactorMultiplicadorIva(BigDecimal porcetajeIva){
		BigDecimal result =  porcetajeIva.divide(new BigDecimal("100")).add(new BigDecimal("1"));
		return result;
	}

	public File textToFile(String contenidoArchivo) {
		File archivo = null;
		try {
			archivo = File.createTempFile("temp", ".txt");
			try (FileOutputStream fos = new FileOutputStream(archivo);
				 OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8")) {
				for (int i = 0; i < contenidoArchivo.length(); i++) {
					out.write(contenidoArchivo.charAt(i));
				}
				System.out.println("Archivo generado correctamente.");
			}
		} catch (IOException e) {
			System.err.println("Error al generar el archivo: " + e.getMessage());
		}
		return archivo;
	}




	public String fileToText(String filePath) {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			Reader in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1) {
				buffer.append((char) ch);
			}
			in.close();
			return buffer.toString();
		} catch (IOException e) {
			System.err.println("Error al generar el texto: " + e.getMessage());
		}
		return null;
	}

	public byte[] readFileToBytes(File file) throws IOException {
		if (file == null || !file.exists())
			throw new IllegalArgumentException("El archivo es nulo o no existe.");
		byte[] bytesArray = new byte[(int) file.length()];
		try (FileInputStream fis = new FileInputStream(file)) {
			fis.read(bytesArray);
		}
		return bytesArray;
	}

	public String facturaXmlToText(FacturaXml factura) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FacturaXml.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(factura, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String autorizacionXmlToText(Autorizacion aut) {
		try {
			XStream xstream = getRespuestaXStream();
			Writer writer = null;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			writer = new OutputStreamWriter(outputStream, "UTF-8");
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xstream.toXML(aut, writer);
			return outputStream.toString("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public XStream getRespuestaXStream() {
		XStream xstream = new XStream(new XppDriver() {
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					protected void writeText(QuickWriter writer, String text) {
						writer.write(text);
					}
				};
			}
		});
		xstream.registerConverter(new CustomXMLGregorianCalendarConverter());
		xstream.alias("respuesta", RespuestaComprobante.class);
		xstream.alias("autorizacion", Autorizacion.class);
		xstream.alias("fechaAutorizacion", XMLGregorianCalendar.class);
		xstream.alias("mensaje", Mensaje.class);
		return xstream;
	}

}
