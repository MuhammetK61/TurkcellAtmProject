package com.example.TurkcellAtmProject.atm.sql.controller;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.TurkcellAtmProject.atm.sql.DatabaseConnection;

public interface IControllerBank<T> {
	
	// CRUD
	public void create(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
	public ArrayList<T> list();
	
	// Gövdeli metot interface
	default Connection getInterfaceConnection() {
		return DatabaseConnection.getInstance().getConnection();
	}
	
}