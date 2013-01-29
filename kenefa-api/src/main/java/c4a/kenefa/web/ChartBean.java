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
  private PieChartModel pieModelTypeFacility;
  
  public ChartBean() {  
  }  
  
  @PostConstruct
  public void createCategoryModel() {  
	  List<String> countriz=new ArrayList<String>();
	  countriz.add("SN");//TODO from view UI
	 // countriz.add("ML");
	  categoryModel = new CartesianChartModel();  
	  categoryTopFacilitiz = new CartesianChartModel();  
	  for(String c:countriz){
		  categoryModel=this.prepareCartesianChart(categoryModel, c, false);
		  categoryTopFacilitiz=this.prepareCartesianChart(categoryTopFacilitiz, c, true);
		  //
		  CountryFull pay = cdao.getCountryFullById(c);
		  pieModelTypeFacility = new PieChartModel(); 
		  Map<String, Long> lcf=pay.getStatistics();
		  for(String val:lcf.keySet()){
			  pieModelTypeFacility.set(val, lcf.get(val));
		  }
	  }
  }  
  
  private CartesianChartModel prepareCartesianChart(CartesianChartModel chart, String country, boolean isTopFacilitiz){
	  CountryFull pay = cdao.getCountryFullById(country);
	  List<Facility> lizt;
	  if(isTopFacilitiz)lizt=pay.getTopFacilities();
	  else lizt=pay.getBottomFacilities();
	  for(Facility f:lizt){
		  ChartSeries series = new ChartSeries();
		  series.setLabel(country+"/"+f.getName()+"("+f.getCityName()+")");
		  series.set("Doctors",f.getCapacity().getDoctor());
		  series.set("Nurses",f.getCapacity().getNurse());
		  series.set("Beds",f.getCapacity().getBed());
		  chart.addSeries(series);
	  }
	  //
	  
	  return chart;
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
 
}  