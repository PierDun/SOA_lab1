package entity;

import entity.xml.JaxBDragon;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity(name = "dragon")
@Table
@Root(name = "dragon")
public class Dragon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Element
    private long id;

    @Column(name = "name", nullable = false, length = -1)
    @Element
    private String name;

    @Column(name = "age", nullable = false)
    @Element
    private Long age;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Element(name = "dragonColor")
    private Color color;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Element(name = "dragonType")
    private DragonType type;

    @Column(name = "birth", nullable = false)
    @Element(name = "creationDate")
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Column(name = "description", nullable = false, length = -1)
    @Element
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate")
    @Element
    private Coordinates coordinates;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cave")
    @Element
    private DragonCave cave;


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
    public void setCreationDate(ZonedDateTime birth) {
        this.creationDate = birth;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates (Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public DragonCave getCave() {
        return cave;
    }
    public void setCave(DragonCave cave) {
        this.cave = cave;
    }

    public void update(JaxBDragon data) {
        this.name = data.getName();
        this.age = data.getAge();
        this.color = data.getColor();
        this.type = data.getType();
        this.description = data.getDescription();
        this.coordinates.update(data.getCoordinates());
        this.cave.update(data.getCave());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragons = (Dragon) o;
        return id == dragons.id && Objects.equals(name, dragons.name) && Objects.equals(age, dragons.age) && Objects.equals(color, dragons.color) && Objects.equals(type, dragons.type) && Objects.equals(creationDate, dragons.creationDate) && Objects.equals(description, dragons.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, color, type, creationDate, description);
    }
}