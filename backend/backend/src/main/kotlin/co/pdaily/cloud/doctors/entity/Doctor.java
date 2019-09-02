package co.pdaily.cloud.doctors.entity;

import co.pdaily.cloud.patients.entity.Patient;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "doctors")
public class Doctor extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    @Column(name = "name")
    public String name;
    @Column(name = "mail")
    public String mail;
    @Column(name = "role")
    public DoctorRole role;
    @ElementCollection
    @CollectionTable(name =  "doctors_patients", joinColumns = @JoinColumn(name = "doctor_id"))
    public Set<Patient> patients = new HashSet<>();

    public Doctor () {

    }

    public Doctor(UUID id, String name, String mail, DoctorRole role){
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.role = role;
    }

    public void updateFrom(Doctor doctor){
        this.name = doctor.name;
        this.mail = doctor.mail;
        this.role = doctor.role;
    }

    public void addPatient(Patient patient){
        patients.add(patient);
    }
}
