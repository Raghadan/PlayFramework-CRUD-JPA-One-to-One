package models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.ebean.Model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CitizenIDModel extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private int age;
	
	private String address;
	
	private Long mobileno;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonBackReference
	@JoinColumn(name = "studentmodel")
	private StudentModel studentmodel;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMobileno() {
		return mobileno;
	}

	public void setMobileno(Long mobileno) {
		this.mobileno = mobileno;
	}





	public CitizenIDModel(Long id, String name, int age, String address, Long mobileno, StudentModel studentmodel) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.mobileno = mobileno;
		this.studentmodel = studentmodel;
	}

	public StudentModel getStudentmodel() {
		return studentmodel;
	}

	public void setStudentmodel(StudentModel studentmodel) {
		this.studentmodel = studentmodel;
	}

	public CitizenIDModel() {
		super();
	}
	
}
