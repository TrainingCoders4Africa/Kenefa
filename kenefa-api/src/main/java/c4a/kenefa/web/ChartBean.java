package c4a.kenefa.web;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.CountryFull;
import c4a.kenefa.api.model.Facility;

@Named
@SessionScoped
public class ChartBean implements Serializable {  
  @Inject private CountryDao<Country> cdao;
  private CartesianChartModel categoryModel;  
  private CartesianChartModel categoryTopFacilitiz;  
  private PieChartModel pieModelDoctor=new PieChartModel();
  private PieChartModel pieModelBed=new PieChartModel();
  private PieChartModel pieModelTypeFacility, pieModelScopeFacility;
  private String country="SN";
  private String libCountry="Senegal";
  private String city;
  private CartesianChartModel catTopFacAfric,catBottomFacAfric;
  
  public ChartBean() {  
  }  
  
  @PostConstruct
  public void createCategoryModel() { 
	  categoryModel= new CartesianChartModel();
	  categoryTopFacilitiz= new CartesianChartModel();
	  catTopFacAfric= new CartesianChartModel();
	  catBottomFacAfric= new CartesianChartModel();
	  this.prepareCartesianChart(categoryModel, country, false);
	  this.prepareCartesianChart(categoryTopFacilitiz, country, true);
	  //
	  this.cartesianChartFacility(catTopFacAfric, cdao.getTopBottomFacilities("ASC", 5));
	  this.cartesianChartFacility(catBottomFacAfric, cdao.getTopBottomFacilities("DESC", 5));
	  //
	  CountryFull pay = cdao.getCountryFullById(country);
	  libCountry=pay.getName();
	  pieModelTypeFacility = new PieChartModel(); 
	  Map<String, Long> lcf=pay.getStatistics();
	  for(String val:lcf.keySet()){
		  pieModelTypeFacility.set(val + " (" + lcf.get(val) + ")", lcf.get(val));
	  }
	  pieModelScopeFacility = new PieChartModel();
	  lcf=pay.getStatScope();
	  for(String val:lcf.keySet()){
		  pieModelScopeFacility.set(val + " (" + lcf.get(val) + ")", lcf.get(val));
	  }
  }  
  
  private void prepareCartesianChart(CartesianChartModel chart, String country, boolean isTopFacilitiz){
	  CountryFull pay = cdao.getCountryFullById(country);
	  List<Facility> list;
	  if(isTopFacilitiz)list=pay.getTopFacilities();
	  else list=pay.getBottomFacilities();
	  this.cartesianChartFacility(chart, list);
  }
  
  private void cartesianChartFacility(CartesianChartModel chart,List<Facility> list){
	  	  for(Facility f:list){
		  	  if(f.getCapacity()==null) continue;
			  ChartSeries series = new ChartSeries();
			  series.setLabel(f.getName()+"("+f.getCountry()+":"+f.getCityName()+")");
			  series.set("Doctors",f.getCapacity().getDoctor());
			  series.set("Nurses",f.getCapacity().getNurse());
			  series.set("Beds",f.getCapacity().getBed());
			  chart.addSeries(series);
	  }
  }

	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}

	public PieChartModel getPieModelBed() {
		return pieModelBed;
	}

	public PieChartModel getPieModelDoctor() {
		return pieModelDoctor;
	}

	public CartesianChartModel getCategoryTopFacilitiz() {
		return categoryTopFacilitiz;
	}

	public PieChartModel getPieModelTypeFacility() {
		return pieModelTypeFacility;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public CartesianChartModel getCatTopFacAfric() {
		return catTopFacAfric;
	}

	public void setCatTopFacAfric(CartesianChartModel catTopFacAfric) {
		this.catTopFacAfric = catTopFacAfric;
	}

	public CartesianChartModel getCatBottomFacAfric() {
		return catBottomFacAfric;
	}

	public void setCatBottomFacAfric(CartesianChartModel catBottomFacAfric) {
		this.catBottomFacAfric = catBottomFacAfric;
	}

	public String getLibCountry() {
		return libCountry;
	}

	public void setLibCountry(String libCountry) {
		this.libCountry = libCountry;
	}

	public PieChartModel getPieModelScopeFacility() {
		return pieModelScopeFacility;
	}

	public void setPieModelScopeFacility(PieChartModel pieModelScopeFacility) {
		this.pieModelScopeFacility = pieModelScopeFacility;
	}
 
}  