package com.uade.recipes.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Multimedia {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Instruction instruction;
    private String typeContent;
    private String extension;
    private String urlContent;

}
