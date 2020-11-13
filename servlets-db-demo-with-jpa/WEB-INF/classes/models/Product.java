package models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="product" , uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode"})})
public class Product {
	
	@Id
	@Column(name="barcode", length=100)
	private String barcode;
	
	@Column(name="name")
	private String name;
	
	@Column(name="color")
	private String color;
	
	@Column(name="description")
	private String description;
	
	public Product() {
		super();
		this.barcode = "";
		this.name = "";
		this.color = "";
		this.description="";
	}
	
	public Product(String barcode, String name, String color, String description)	{
		this.barcode = barcode;
		this.name = name;
		this.color = color;
		this.description = description;
	}
	
	
	public void setName(String name)	{
		this.name = name;
	}
	
	public void setColor(String color)	{
		this.color = color;
	}
	
	public void setDescription(String description)	{
		this.description = description;
	}
	
	public void setBarcode(String barcode)	{
		this.barcode = barcode;
	}
	
	public String getName()	{
		return this.name;
	}
	
	public String getColor()	{
		return this.color;
	}
	
	public String getDescription()	{
		return this.description;
	}
	
	public String getBarcode()	{
		return this.barcode;
	}
}
