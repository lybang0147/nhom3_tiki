package com.hcmute.starter.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@RestResource(exported = false)
@Table(name = "\"discount_program\"")
@NoArgsConstructor
public class DiscountProgramEntity {
    @SequenceGenerator(
            name = "program_sequence",
            sequenceName = "program_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "program_sequence"
    )
    @Column(name = "program_id")
    private Long id;
    @Column(name = "program_name")
    private String name;
    @Column(name = "percent")
    private double percent;
    @Column(name = "\"from_date\"")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date fromDate;
    @Column(name = "\"to_date\"")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date toDate;
    @Column(name = "\"created_at\"")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "\"description\"")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "\"brand_program\"", joinColumns = @JoinColumn(name = "\"program_id\""), inverseJoinColumns = @JoinColumn(name = "\"brand_id\""))
    private Set<BrandEntity> brandEntities;

    public DiscountProgramEntity(String name, Float percent, Date fromDate, Date toDate, String description) {
        this.name = name;
        this.percent = percent;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BrandEntity> getBrandEntities() {
        return brandEntities;
    }

    public void setBrandEntities(Set<BrandEntity> brandEntities) {
        this.brandEntities = brandEntities;
    }
}

