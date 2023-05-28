package sk.dudek.post_app.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Table(name = "post")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer externalId;
    private Integer userId;

    @Column()
    private String title;

    @Column(length = 500)
    private String body;
}
