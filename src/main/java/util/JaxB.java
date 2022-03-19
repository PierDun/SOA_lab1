package util;

import entity.Dragon;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxB {
    public static String jaxbObjectToXML(Dragon dragon) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Dragon.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(dragon, sw);

            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T fromStr(String str, Class<T> tClass) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new StringReader(str));
    }
}