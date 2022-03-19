package entity.xml;

import entity.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.ZonedDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dragon")
public class JaxBDragon implements Serializable {
    @XmlElement(name = "id")
    private long id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "age")
    private Long age;
    @XmlElement(name = "color")
    private Color color;
    @XmlElement(name = "type")
    private DragonType type;
    @XmlElement(name = "creationDate")
    private ZonedDateTime creationDate = ZonedDateTime.now();
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "coordinates")
    private JaxBCoordinates coordinates;
    @XmlElement(name = "cave")
    private JaxBCave cave;

    public Dragon toDragon () {
        Dragon dragon = new Dragon();

        dragon.setName(this.name);
        dragon.setAge(this.age);
        dragon.setColor(this.color);
        dragon.setType(this.type);
        dragon.setDescription(this.description);
        dragon.setCoordinates(this.coordinates.toCoordinates());
        dragon.setCave(this.cave.toCave());

        return dragon;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public DragonType getType() {
        return type;
    }
    public void setType(DragonType type) {
        this.type = type;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public JaxBCoordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates (JaxBCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public JaxBCave getCave() {
        return cave;
    }
    public void setCave(JaxBCave cave) {
        this.cave = cave;
    }
}