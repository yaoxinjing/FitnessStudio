package fitnessStudio.user;

import javax.persistence.*;

@Entity
public class Address {

    private @Id @GeneratedValue long id;


    private String street;
    private Integer houseNumber;
    private String additionalAddress;
    private String city;
    private String zipcode;

    //default constructor
    public Address(){}

    public Address(String street, Integer houseNumber, String additionalAddress, String City, String zipcode) {

        this.street = street;
        this.houseNumber = houseNumber;
        this.additionalAddress = additionalAddress;
        this.city = City;
        this.zipcode = zipcode;
}   

    public Address changeAddress(String street, Integer houseNumber, String additionalAddress, 
        String City, String zipcode) {

        this.street = street;
        this.houseNumber = houseNumber;
        this.additionalAddress = additionalAddress;
        this.city = City;
        this.zipcode = zipcode;

        return this;
    }

    public String toString() {
        return this.street + " " + houseNumber.toString() + " " + additionalAddress + ", " + zipcode + " " + city;
    }

    public String getStreet() {
        return this.street;
    }
    public Integer getHouseNumber() {
        return this.houseNumber;
    }
    public String getAdditionalAddress() {
        return this.additionalAddress;
    }
    public String getCity() {
        return this.city;
    }
    public String getZipcode() {
        return this.zipcode;
    }
    
}