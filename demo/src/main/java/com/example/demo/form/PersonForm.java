package com.example.demo.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonForm {
	@NotNull
	@Min(value = 1, message = "{validation.id.min}")
	private Integer id;

	@NotNull
	@Size(min=2, max=30)
	private String name;

	@NotNull
	@Min(value = 18, message = "{validation.age.min}")
	private Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String toString() {
		return "Person(Id: " + this.id + ", Name: " + this.name + ", Age: " + this.age + ")";
	}
}
