package com.inkly.inkly_backend.post.entity;

import com.inkly.inkly_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Getter
@Entity
@Check(
        name = "check_status_and_published_at",
        constraints = """
                (status = 'DRAFT' and published_at IS NULL)
                OR 
                (status = 'PUBLISHED' and published_at IS NOT NULL)
                """
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(
            name = "title",
            length = 200
    )
    private String title;


    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Column(
            name = "published_at"
    )
    private LocalDateTime publishedAt;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;



    @PrePersist
    protected void setTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
