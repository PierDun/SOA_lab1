package validator;

import entity.Color;
import entity.DragonType;

public class XMLvalidator {
    public boolean checkForTags(String xml) {
        boolean a = xml.contains("<id>");
        a = a && xml.contains("</id>");

        a = a && xml.contains("<dragon>");
        a = a && xml.contains("</dragon>");

        a = a && xml.contains("<name>");
        a = a && xml.contains("</name>");

        a = a && xml.contains("<age>");
        a = a && xml.contains("</age>");

        a = a && xml.contains("<color>");
        a = a && xml.contains("</color>");

        a = a && xml.contains("<type>");
        a = a && xml.contains("</type>");

        a = a && xml.contains("<description>");
        a = a && xml.contains("</description>");

        a = a && xml.contains("<coordinates>");
        a = a && xml.contains("</coordinates>");

        a = a && xml.contains("<x>");
        a = a && xml.contains("</x>");

        a = a && xml.contains("<y>");
        a = a && xml.contains("</y>");

        a = a && xml.contains("<cave>");
        a = a && xml.contains("</cave>");

        a = a && xml.contains("<depth>");
        a = a && xml.contains("</depth>");
        return a;
    }

    public boolean checkForCorrectValues (String xml) {
        boolean a = true;
        try {
            long id = Long.parseLong(xml.substring(xml.indexOf("<id>") + 4, xml.indexOf("</id>")));
            Long age = Long.parseLong(xml.substring(xml.indexOf("<age>") + 5, xml.indexOf("</age>")));
            int x = Integer.parseInt(xml.substring(xml.indexOf("<x>") + 3, xml.indexOf("</x>")));
            Integer y = Integer.parseInt(xml.substring(xml.indexOf("<y>") + 3, xml.indexOf("</y>")));
            Double depth = Double.parseDouble(xml.substring(xml.indexOf("<depth>") + 7, xml.indexOf("</depth>")));
        } catch (NumberFormatException e) {
            return false;
        }

        String color = xml.substring(xml.indexOf("<color>") + 7, xml.indexOf("</color>"));
        for (Color c : Color.values()) {
            a = a || (c.name().equals(color));
        }

        String type = xml.substring(xml.indexOf("<type>") + 6, xml.indexOf("</type>"));
        for (DragonType t : DragonType.values()) {
            a = a || (t.name().equals(type));
        }
        return a;
    }
}
