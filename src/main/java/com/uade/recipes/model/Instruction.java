package com.uade.recipes.model;

import com.uade.recipes.vo.InstructionVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@SecondaryTable(name = "instruction_addition", pkJoinColumns = @PrimaryKeyJoinColumn(name = "instruction_id"))
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    private Recipe recipe;
    @Column(table = "instruction_addition")
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer numberOfStep;

    public InstructionVo toVO(){
        InstructionVo vo = new InstructionVo();
        vo.setId(id);
        vo.setTitle(title);
        vo.setRecipeId(recipe.getId());
        vo.setDescription(description);
        vo.setNumberOfStep(numberOfStep);

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Instruction that = (Instruction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
