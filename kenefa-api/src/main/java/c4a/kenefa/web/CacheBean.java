package c4a.kenefa.web;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.model.Country;

@Named
@javax.enterprise.context.ApplicationScoped
public class CacheBean {
	private static final Logger LOGGER = Logger.getLogger(CacheBean.class);
	@Inject
	CountryDao<Country> cdao;
	private List<Country> countries = null;
	private LinkedHashMap<String,Country> mapCountries = null;
	private static CacheBean instance;
	
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
	
	public LinkedHashMap<String,Country> getMapCountries(){
		if(mapCountries==null){
			mapCountries = new LinkedHashMap<String, Country>();
			for(Country country:this.getCountries()){
				mapCountries.put(country.getName(), country);
			}
		}
		return mapCountries;
	}

	public static CacheBean getInstance() {
		return instance==null?instance=new CacheBean():instance;
	}
}
