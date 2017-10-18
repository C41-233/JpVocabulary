package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="national_values")
public class NationalWordValue {

	@Column(name="national_id")
	private long refId;
	
	@Column(name="value")
	private String value;
	
	
	
}
