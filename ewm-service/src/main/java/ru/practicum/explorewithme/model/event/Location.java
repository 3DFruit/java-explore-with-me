package ru.practicum.explorewithme.model.event;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    Long id;
    @Column(name = "latitude", nullable = false)
    private Double lat;
    @Column(name = "longitude", nullable = false)
    private Double lon;
}
