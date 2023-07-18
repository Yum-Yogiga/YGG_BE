package com.yogiga.yogiga.keyword.entity;

import com.yogiga.yogiga.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Keyword keyword;

    public UserKeyword(User user, Keyword keyword) {
        this.user = user;
        this.keyword = keyword;
    }
}
