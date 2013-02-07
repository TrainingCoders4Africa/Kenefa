package c4a.kenefa.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

import c4a.kenefa.api.data.CountryDao;
import c4a.kenefa.api.data.FacilityDao;
import c4a.kenefa.api.model.Country;
import c4a.kenefa.api.model.Facility;
import c4a.kenefa.api.model.embedded.Capacity;
import c4a.kenefa.api.model.embedded.City;
import c4a.kenefa.api.model.embedded.Rating;
import c4a.kenefa.api.model.embedded.Service;

@ManagedBean
@ViewScoped
public class HomeBean implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(HomeBean.class);
    private MindmapNode root;
    private MindmapNode selectedNode;
    private TreeNode selectedTreeNode=new DefaultTreeNode("root", null) ;
    @Inject private CountryDao<Country> cdao;
    @Inject private FacilityDao<Facility> fdao;
    private Facility currentFacility = null;
	private PieChartModel pieDoctorFacility = new PieChartModel();
	
	//private List<Country> countries;
    //private boolean showMapMind=true;
	private Collection<City> cities;
	private List<Country> countries;
	private LinkedHashMap<String, Country> mapCountries;
	private Integer currentCity;
	private String currentCountry="SN";
	
    private List<Facility>  filteredFacilites;

   
    
    public HomeBean() {
    	
    }
    
    @PostConstruct
    public void init(){
	    if(root==null){
	    	root = new DefaultMindmapNode("Kenefa", "Kenefa API", "FFCC00", false);
	    	for(Country c:cdao.getCountriesThatHaveFacility()){
	    		MindmapNode map = new DefaultMindmapNode(c.getName(), c, "6e9ebf", true);
	    		root.addNode(map);
	    	}
	    }
	    this.prepareTree();
	    currentFacility = new Facility(new Capacity(), new Service(), new Rating());
    }

    public MindmapNode getRoot() {
        return root;
    }

    public MindmapNode getSelectedNode() {
        return selectedNode;
    }
    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Facility  getCurrentFacility(){
    	if(this.currentFacility==null)currentFacility = new Facility(new Capacity(), new Service(), new Rating());
        return currentFacility;
    }

    public void setCurrentFacility(Facility currentFacility){
        this.currentFacility=currentFacility;
    }
    public List<Facility> getFacilities(){
    	if(currentCountry!=null&&currentCity!=null)
    		return cdao.getFacilitiesByCountryAndCity(currentCountry, currentCity);
    	return fdao.getFacilities(0, 50);//TODO have to be filtered by country and city
    }
    public void onEdit(RowEditEvent event) {  
        //((Facility)event.getObject());  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Facility updated!!!!"));  
    }  
      
    public void onCancel(RowEditEvent event) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelled!!!"));  
    } 

    public TreeNode getSelectedTreeNode() {  
        return selectedTreeNode;  
    }  
  
    public void setSelectedTreeNode(TreeNode selectedTreeNode) {  
        this.selectedTreeNode = selectedTreeNode;  
    } 

    public void onFacilitySelect(NodeSelectEvent event) {  
    	//LOGGER.info("selected facility : " + event.getTreeNode().toString());
    	if(event.getTreeNode().getType().equals("city")){
    		 this.currentCity=((City)event.getTreeNode().getData()).getId();
    	}else if(event.getTreeNode().getType().equals("country")){
      		 this.currentCountry=((Country)event.getTreeNode().getData()).getId();
       	}else if(event.getTreeNode().getData() instanceof Facility){
    		this.currentFacility=(Facility) event.getTreeNode().getData();
    		if(this.currentFacility.getCapacity()==null)this.currentFacility.setCapacity(new Capacity());
    		if(this.currentFacility.getService()==null)this.currentFacility.setService(new Service());
    		if(this.currentFacility.getRating()==null)this.currentFacility.setRating(new Rating());
    	}
    }

    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
        if(node.getChildren().isEmpty()) {
            Object data = node.getData();
            if(data instanceof Country){
            	for(City c:((Country)data).getCities())
            		node.addNode(new DefaultMindmapNode(c.getName(), c , "82c542"));
            }
            if(data instanceof City){
            	 City city = (City) data;
            	 String country = ((Country) ((MindmapNode) node.getParent()).getData()).getId();
            	 List<Facility> list = cdao.getFacilitiesByCountryAndCity(country, city.getId());
            	 for(Facility f: list){
            		 node.addNode(new DefaultMindmapNode(f.getName(), f , "FFCC00",true));
            	 }
            	 //this.createPieDoctorFacility(list);
            }
            if(data instanceof Facility){
                 //do nothing 
            }
            else {
            	LOGGER.debug("Unkown type : " + data);
                //node.addNode(new DefaultMindmapNode("Inconnu", "Inconnu", "290cab",false));
            }
        }
        
    }
    
    public void onNodeDblselect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
        this.selectedNode = (MindmapNode) event.getObject();  
        Object data = node.getData();
        LOGGER.debug("facility : " + data);
        if(data instanceof Facility){
            currentFacility = (Facility) data;
        }      
    }
    
    public PieChartModel getPieDoctorFacility() {  
        return pieDoctorFacility;  
    }  
  
    private void createPieDoctorFacility(List<Facility> l) { //TODO 
        for(Facility f:l){
        	if(f.getCapacity()!=null&&f.getCapacity().getDoctor()>0)
        	pieDoctorFacility.set(f.getName(), f.getCapacity().getDoctor());  
        }
    }
    
    public void syncTree(ActionEvent x){
    	this.treeRoot=null;
    	this.prepareTree();
    }
    
    private List<Country> getCountries() {
		if(countries==null){
			countries=cdao.getCountries();
		}
		return countries;
	}  
	
    public LinkedHashMap<String,Country> getMapCountries(){
		if(mapCountries==null){
			mapCountries = new LinkedHashMap<String, Country>();
			for(Country country:this.getCountries()){
				mapCountries.put(country.getId(), country);
			}
		}
		return mapCountries;
	}

	public void findCitiesOfCountry(AjaxBehaviorEvent e){
		Country cc = new Country();
			if(currentFacility.getCountry()!=null&&currentFacility.getCountry().length()>0){
				cc.setId(this.getMapCountries().get(currentFacility.getCountry()).getId());
				int index=cdao.getCountries().indexOf(cc);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Current country : "+index+" " + currentFacility.getCountry()));
				Country c = cdao.getCountries().get(index<0?0:index);
				if(c!=null){
					cities = c.getCities();
				}
			}	
	}
	
	private TreeNode treeRoot=null;
	private Doc doc;  
	  
	 public void prepareTree() {  
		 if(treeRoot==null){
	    	treeRoot = new DefaultTreeNode("root", null);  
	    	for(Country country:cdao.getCountries()){
	    		//TreeNode doc1 = new DefaultTreeNode("country",new Doc(country.getName()), treeRoot);
	    		int i=0;
	    		TreeNode doc1=null;
	    		for(City city:country.getCities()){
	    			//TreeNode doc2 = new DefaultTreeNode("city",new Doc(city.getName()), doc1);
	    			List<Facility> list = cdao.getFacilitiesByCountryAndCity(country.getId(), city.getId());
	    			int j=0;
	    			TreeNode doc2=null;
	            	 for(Facility f: list){
	            		 if(i==0){
	 	    				doc1 = new DefaultTreeNode("country",country, treeRoot);
	 	    				i++;
	 	    			}
	            		 if(j==0){
	            			 doc2 = new DefaultTreeNode("city",city, doc1);
	            			 j++;
	            		 }
	            		 LOGGER.debug("FACILITY : " +country.getName()+ " " + f.getName());
	            		 TreeNode doc3 = new DefaultTreeNode("facility", f, doc2); 
	            	 }
	    		}
	    	}
		 }

	}
	 
	 public void cancelFacility(ActionEvent e){
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operation cancelled !!!!"));
	     currentFacility = new Facility(new Capacity(), new Service(), new Rating());
	 }
	 
	 public void removeFacility(ActionEvent e){
		 fdao.removeFacility(currentFacility.getId());
		 this.treeRoot=null;
		 this.prepareTree();
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Facility removed in success !!!!"));
	 }
	 
	public void saveFacility(ActionEvent e){
		Country c=this.getMapCountries().get(currentFacility.getCountry());
		if(c!=null){
			currentFacility.setCountry(c.getId());
			Collection<City> cc = c.getCities();
			City city=new City(currentFacility.getCity());
			for(City ci:cc){
				if(ci.equals(city)){
					city=ci;
					break;
				}
			}
			
			currentFacility.setCityName(city.getName());
		}
		LOGGER.info("FACILITY : " +currentFacility.getId()+"--------" + currentFacility.getName()+" country :" + currentFacility.getCountry());
		if(currentFacility.getId()!=null)
			fdao.updateFacility(currentFacility.getId(), currentFacility);
		else fdao.getDao().persist(currentFacility);
		this.treeRoot=null;
		this.prepareTree();
		currentFacility = new Facility(new Capacity(), new Service(), new Rating());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operation done in success !!!!"));
	}

    public void displaySelectedFacility(){
    	this.findCitiesOfCountry(null); 
    }
    
	public TreeNode getTreeRoot() {
			return treeRoot;
	}
	 
	 public Doc getDoc(){
		 return doc;
	 }

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Collection<City> getCities() {
		return cities;
	}

	public Integer getCurrentCity() {
		return currentCity;
	}

	public String getCurrentCountry() {
		return currentCountry;
	}

	public List<Facility> getFilteredFacilites() {
		return filteredFacilites;
	}

	public void setFilteredFacilites(List<Facility> filteredFacilites) {
		this.filteredFacilites = filteredFacilites;
	}
	 
	 
		
}