package it.alelegrand.spring.domain;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class UserDto {
	
	@NotEmpty(message = "The name is required")
	private String name;
	
	@NotEmpty(message = "The surname is required")
	private String surname;
	
	private Double height;
	
	private MultipartFile imageFile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
}