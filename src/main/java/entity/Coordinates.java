package entity;

import entity.xml.JaxBCoordinates;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@Table
@XmlRootElement
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlElement
    private int id;

    @Column(name = "x", nullable = false)
    @XmlElement
    private int x;

    @Column(name = "y", nullable = false)
    @XmlElement
    private Integer y;


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

    public void update(JaxBCoordinates data) {
        this.x = data.getX();
        this.y = data.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return id == that.id && x == that.x && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }
}