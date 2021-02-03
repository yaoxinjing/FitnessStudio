package fitnessStudio.user;
import fitnessStudio.request.*;

import javax.money.MonetaryAmount;
import javax.persistence.*;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
public class Contract {

    private @Id @GeneratedValue long id;


    private String description;
    private MonetaryAmount price;
    private LocalDate startTime;
    private LocalDate endTime;
    @ManyToOne(cascade = {CascadeType.ALL})
    private StudioUser partner = null;
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Request> requests = new ArrayList<Request>();

    //default constructor
    public Contract(){}


    public Contract(String description, MonetaryAmount price, LocalDate startTime, LocalDate endTime) {
        this.description = description;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
}   

public void updateData(String description, MonetaryAmount price, LocalDate startTime, LocalDate endTime) {
    this.description = description;
    this.price = price;
    this.startTime = startTime;
    this.endTime = endTime;
}   

public String getDescription() {
    return description;
}

public MonetaryAmount getPrice() {
    return price;
}

public LocalDate startTime() {
    return startTime;
}

public String startTimeString() {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	return startTime.format(formatter);
}

public LocalDate endTime() {
    return endTime;
}

public String endTimeString() {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	return endTime.format(formatter);
}

public String toString() {
    return description;
}
 
public void addRequest(Request req) {
    this.requests.add(req);
}

public void removeRequest(Request req) {
	this.requests.remove(req);
}

public void removeRequests(Iterable<Request> iter) {
	for (Request request : iter) {
		this.requests.remove(request);
	}
	
}

public List<Request> getRequests() {
    return this.requests;
}

public void setPartner(StudioUser partner) {
    this.partner = partner;
}

public StudioUser getPartner() {
    return this.partner;
}

}