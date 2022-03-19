package entity;

import entity.xml.JaxBCave;
import entity.xml.JaxBDragon;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity(name = "dragonCave")
@Table
@XmlRootElement
public class DragonCave {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlElement
    private int id;

    @Column(name = "depth")
    @XmlElement
    private Double depth;


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

    public void update(JaxBCave data) {
        this.depth = data.getDepth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DragonCave cave = (DragonCave) o;
        return id == cave.id && Objects.equals(depth, cave.depth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depth);
    }
}