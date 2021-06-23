package com.codecool.peermentoringbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.StringNVarcharType;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ReviewEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int rating;

    @Column(columnDefinition="text")
    private String review;

    @Column
    private String reviewer;

    @ManyToOne
    @JsonIgnore
    private UserEntity reviewedUser;
}
