package com.sengkim.study.sample.domain;

/**
 * @author sengkim
 * @Date Sep 8, 2015
 * @Email sengkim.it@gmail.com
 */
public class User extends AbstractLongDomainEntity {

    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstname=" + firstname + ", lastname="
                + lastname + "]";
    }

}