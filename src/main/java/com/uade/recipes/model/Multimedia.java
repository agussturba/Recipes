package com.uade.recipes.model;

import com.uade.recipes.vo.MultimediaVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Multimedia {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Instruction instruction;
    private String typeContent;
    private String extension;
    private String urlContent;

    public MultimediaVo toVO(){
        MultimediaVo vo = new MultimediaVo();
        vo.setId(id);
        vo.setInstructionId(instruction.getId());
        vo.setTypeContent(typeContent);
        vo.setUrlContent(urlContent);
        vo.setExtension(extension);

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Multimedia that = (Multimedia) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
