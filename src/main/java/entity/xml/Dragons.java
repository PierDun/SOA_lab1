package entity.xml;

import entity.Dragon;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Dragons {
    @ElementList
    private List<Dragon> dragons = null;

    public List<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(List<Dragon> dragons) {
        this.dragons = dragons;
    }
}