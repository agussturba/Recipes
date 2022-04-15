package com.uade.recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private Integer code;
    @Column(nullable = false)
    private Timestamp timestamp;

    public Token(User user) {
        this.user = user;
        this.timestamp = Timestamp.from(Instant.now());
        this.code = (int) (Math.random() * (900000) + 100000);
    }
}
