package com.openway.springtemplateapollo.entity;

import com.openway.springtemplateapollo.constants.GenderEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {
    /**
     * auto generate id of DB
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * user's email
     */
    @Column(name = "email")
    private String email;

    /**
     * user's password
     */
    @Column(name = "password")
    private String password;

    /**
     * user's gender
     */
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    /**
     * user's refresh token
     */
    @Column(name = "refresh_token")
    private String refreshToken;

    /**
     * user's address
     */
    @Column(name = "address")
    private String address;

    /**
     * foreign key to role
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Role role;

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

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
