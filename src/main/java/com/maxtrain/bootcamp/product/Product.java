package com.maxtrain.bootcamp.product;

import javax.persistence.*;


@Entity
@Table(uniqueConstraints=@UniqueConstraint(name= "UIDX_partNb", columnNames= {"partNbr"}))

public class Product {	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=30, nullable=false)
	private String partNbr;
	@Column(length=30, nullable=false)
	private String Name; 
	@Column(columnDefinition="decimal(9,2) NOT NULL DEFAULT 0.0")
	private double price;
	
	public Product() {}
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPartNbr() {
		return partNbr;
	}
	public void setPartNbr(String partNbr) {
		this.partNbr = partNbr;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}	

}
