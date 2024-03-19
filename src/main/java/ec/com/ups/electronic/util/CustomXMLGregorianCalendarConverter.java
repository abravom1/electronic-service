package ec.com.ups.electronic.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;

public class CustomXMLGregorianCalendarConverter implements Converter {

    @Override
    public boolean canConvert(Class type) {
        return XMLGregorianCalendar.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        XMLGregorianCalendar xmlGregorianCalendar = (XMLGregorianCalendar) o;
        String formattedDate = formatXMLGregorianCalendar(xmlGregorianCalendar);
        writer.setValue(formattedDate);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;
    }

    private String formatXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        return formatter.format(xmlGregorianCalendar.toGregorianCalendar().getTime());
    }
}