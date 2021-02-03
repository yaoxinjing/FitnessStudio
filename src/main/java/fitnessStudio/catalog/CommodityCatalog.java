package fitnessStudio.catalog;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

import fitnessStudio.catalog.Commodity.CommodityType;


public interface CommodityCatalog extends Catalog<Commodity> {

	static final Sort DEFAULT_SORT = Sort.by("productIdentifier").descending();
	
	//return All
	Streamable<Commodity> findAll();
	
	//return commodity by Name
	Streamable<Commodity> findByName(String name);
	
	//Return sorted elements from database
	Iterable<Commodity> findByType(CommodityType type, Sort sort);
	
	//Find Products by Type sorted by product identifier
	default Iterable<Commodity> findByType(CommodityType type) {
		return findByType(type, DEFAULT_SORT);
	}
	
}