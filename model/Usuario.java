package com.utsem.app.model;

import java.util.UUID;

import com.utsem.app.enums.Perfiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(unique = true)
	String username;
	@Column
	String nombre;
	@Column
	String paterno;
	@Column
	String materno;
	@Column
	Perfiles perfil;
	
	@Column(unique = true)
	UUID uuid; //id unico universal

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	
	
}
