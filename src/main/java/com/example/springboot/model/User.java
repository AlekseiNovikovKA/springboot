package com.example.springboot.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotEmpty(message = "Введите имя")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "Введите фамилию")
    @Size(min = 2, max = 30, message = "Фамилия должно содержать от 2 до 30 символов")
    private String lastName;
    @Column
    @NotNull(message = "Введите возраст")
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Max(value = 127, message = "Возраст превышает максимальный предел")
    private Byte age;

    @Column
    @NotNull(message = "Введите email")
    private String email;
    @Column
    @NotNull(message = "Введите пароль")
    private String password;
    @Column
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() { }

    public User(String name, String lastName, Byte age, String email, String password, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
