package com.fastcode.example.domain.core.address;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.city.CityEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ADDRESS")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class AddressEntity extends AbstractEntity {

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Basic
    @Column(name = "address2", nullable = true, length = 50)
    private String address2;

    @Basic
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Basic
    @Column(name = "district", nullable = false, length = 20)
    private String district;

    @Basic
    @Column(name = "LAST_UPDATE", nullable = false)
    private LocalDateTime lastUpdate;

    @Basic
    @Column(name = "POSTAL_CODE", nullable = true, length = 10)
    private String postalCode;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ADDRESS_ID", nullable = false)
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private CityEntity city;
}
