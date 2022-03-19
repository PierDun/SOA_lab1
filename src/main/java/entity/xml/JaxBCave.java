package entity.xml;

import entity.DragonCave;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cave")
public class JaxBCave {
    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "depth")
    private Double depth;

    public DragonCave toCave () {
        DragonCave cave = new DragonCave();

        cave.setId(this.id);
        cave.setDepth(this.depth);

        return cave;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Double getDepth() {
        return depth;
    }
    public void setDepth(Double depth) {
        this.depth = depth;
    }
}