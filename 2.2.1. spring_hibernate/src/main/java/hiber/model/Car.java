package hiber.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", nullable = false)
    private Long id;
    @Column
    private String model;
    @Column
    private int series;
    @OneToOne (fetch = FetchType.LAZY, mappedBy = "car")
    private User user;

    /* ====== Constructors ====== */

    public Car() {}

    public Car(String model, int series) {
        this.model = model;
        this.series = series;
    }

    /* ====== Getters / Setters ====== */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}

    public String getModel() { return model; }
    public void setModel (String model) { this.model = model; }

    public int getSeries() { return series; }
    public void setSeries (int series) { this.series = series; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Car -> Model: " + getModel() + ", Series: " + getSeries() + ", id: " + getId();
    }

}
