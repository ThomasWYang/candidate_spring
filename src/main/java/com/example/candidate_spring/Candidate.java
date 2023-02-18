package com.example.candidate_spring;

import java.util.Objects;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Candidate {
    private @Id @GeneratedValue Long id;
    private String fname;
    private String lname;
    private String email;
    private Integer score;

    Candidate() {
    }

    Candidate(String fname, String lname, String email) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.score = new Random().nextInt(100 - 60) + 60;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Candidate))
            return false;
        Candidate customer = (Candidate) o;
        return Objects.equals(this.id, customer.id) && Objects.equals(this.fname, customer.fname)
                && Objects.equals(this.lname, customer.lname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.fname, this.lname);
    }

    @Override
    public String toString() {
        return String.format("Candidate { id = %d, name = %s %s, score = %d }", this.id, this.fname, this.lname,
                this.score);

    }

}
