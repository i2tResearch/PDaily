package co.pdaily.cloud.patients.entity;

import co.pdaily.cloud.doctors.entity.Doctor;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "patients")
public class Patient extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    @Column(name = "document")
    public String document;
    @Column(name = "name")
    public String name;
    @Column(name = "age")
    public int age;
    @Column(name = "gender")
    public PatientGender gender;
    @ElementCollection
    @CollectionTable(name =  "doctors_patients", joinColumns = @JoinColumn(name = "patient_id"))
    public Set<Doctor> doctors  = new HashSet<>();

    public Patient() {

    }

    public Patient(UUID id, String document, String name, int age, PatientGender gender){
        this.id = id;
        this.document = document;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public void updateFrom(Patient patient){
        this.name = patient.name;
        this.age = patient.age;
        this.gender = patient.gender;
    }
}
