package com.fraud.springprac.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.beans.ConstructorProperties;
import java.util.Date;

@Entity
@Table(name = "active_tokens", schema = "egitim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,  unique = true, length = 1024)
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @Column(nullable = false)
    private Date slidingExpiration;
    @Column(nullable = false)
    private Date absoluteExpiration;
    @Column(nullable = false)
    private Date issuedAt;

    public ActiveToken(String accessToken, UserEntity user, Date slidingExp, Date absoluteExp, Date now) {
        this.token = accessToken;
        this.user = user;
        this.slidingExpiration = slidingExp;
        this.absoluteExpiration = absoluteExp;
        this.issuedAt = now;
    }
}
