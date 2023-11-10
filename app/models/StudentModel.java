package models;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.ebean.Model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
public class StudentModel extends Model{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "age")
	public int age;

	@OneToOne(cascade = CascadeType.ALL,mappedBy = "studentmodel")
	@JsonManagedReference
	private CitizenIDModel citizen;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	public StudentModel(Long id, String name, int age, CitizenIDModel citizen) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.citizen = citizen;
	}

	public CitizenIDModel getCitizen() {
		return citizen;
	}

	public void setCitizen(CitizenIDModel citizen) {
		this.citizen = citizen;
	}

	public StudentModel() {
		super();
	}


	
}
