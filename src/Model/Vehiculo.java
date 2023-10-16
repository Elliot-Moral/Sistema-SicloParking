/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DataBaseSqlite.Conexion;

/**
 *
 * @author Elliot Moral
 */
public class Vehiculo {
    
        private int id_vehiculo;
	private String placa;
	private String color;

    public Vehiculo(String placa, String color) {
	this.placa = placa;
	this.color = color;
    }
   

    public int getId_vehiculo() {
	return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
	this.id_vehiculo = id_vehiculo;
    }

    public String getPlaca() {
	return placa;
    }

    public void setPlaca(String placa) {
	this.placa = placa;
    }

    public String getColor() {
	return color;
    }

    public void setColor(String color) {
	this.color = color;
    }

    

    
	 
}
