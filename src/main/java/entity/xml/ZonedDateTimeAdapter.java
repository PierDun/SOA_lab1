package entity.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime unmarshal(String s) {
        return ZonedDateTime.parse(s);
    }

    @Override
    public String marshal(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toString();
    }
}