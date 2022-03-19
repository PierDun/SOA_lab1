package entity.xml;

import entity.Coordinates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "coordinates")
public class JaxBCoordinates {
    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "x")
    private int x;
    @XmlElement(name = "y")
    private Integer y;

    public Coordinates toCoordinates () {
        Coordinates coordinates = new Coordinates();

        coordinates.setId(this.id);
        coordinates.setX(this.x);
        coordinates.setY(this.y);

        return coordinates;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }
}