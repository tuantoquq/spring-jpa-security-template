package com.openway.springtemplateapollo.entity;

import com.openway.springtemplateapollo.constants.RoleEnum;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    /**
     * auto generate id of DB
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * role name
     */
    @Column(name = "role_name", unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    /**
     * creation time of record
     */
    @Column(name="create_at", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp createAt;

    /**
     * update time of record
     */
    @Column(name="update_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updateAt;
    public int getId() {
        return id;
    }

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<User> listUser;

    public void setId(int id) {
        this.id = id;
    }

    public RoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleEnum roleName) {
        this.roleName = roleName;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}
