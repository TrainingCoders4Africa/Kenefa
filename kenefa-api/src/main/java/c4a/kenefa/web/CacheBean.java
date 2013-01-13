package c4a.kenefa.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.model.Country;

@Named
@ApplicationScoped
public class CacheBean {
	private static final Logger LOGGER = Logger.getLogger(CacheBean.class);
	@Inject
	CountryDao<Country> cdao;
	private List<Country> countries = null;
	private LinkedHashMap<String,String> mapCountries = null;
	
	@PostConstruct
	public void init(){
		LOGGER.debug("Initialisation de "+CacheBean.class.getName());
	}
	
	public List<Country> getCountries() {
		if(countries==null){
			countries=cdao.getCountries();
		}
		return countries;
	}  
	
	public LinkedHashMap<String,String> getMapCountries(){
		if(mapCountries==null){
			mapCountries = new LinkedHashMap<String, String>();
			for(Country country:this.getCountries()){
				mapCountries.put(country.getName(), country.getName());
			}
		}
		return mapCountries;
	}
}
