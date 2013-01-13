package c4a.kenefa.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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

@Named
@SessionScoped
public class HomeBean implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(HomeBean.class);
    private MindmapNode root;
    private MindmapNode selectedNode;
    private TreeNode selectedTreeNode;
    @Inject private CountryDao<Country> cdao;
    @Inject private FacilityDao<Facility> fdao;
    private Facility currentFacility = null;
	private PieChartModel pieDoctorFacility = new PieChartModel();
	
	private List<Country> countries;
    private boolean showMapMind=true;

    public boolean isShowMapMind(){
        return this.showMapMind;
    }

    public void setShowMapMind(boolean showMapMind){
        this.showMapMind=showMapMind;
    }
    
    public HomeBean() {
    	
    }
    
    @PostConstruct
    public void init(){
	    if(root==null){
	    	root = new DefaultMindmapNode("Kenefa", "Kenefa API", "FFCC00", false);
	    	for(Country c:cdao.getCountries()){
	    		MindmapNode map = new DefaultMindmapNode(c.getName(), c, "6e9ebf", true);
	    		root.addNode(map);
	    	}
	    	this.prepareTree();
	    }
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
        return currentFacility;
    }

    public void setCurrentFacility(Facility currentFacility){
        this.currentFacility=currentFacility;
    }
    public List<Facility> getFacilities(){
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
    	LOGGER.info("selected facility : " + event.getTreeNode().toString());
    	if(event.getTreeNode().getData() instanceof Facility)this.currentFacility=(Facility) event.getTreeNode().getData();
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
            	 String country = ((Country) ((MindmapNode) node.getParent()).getData()).getName();
            	 List<Facility> list = cdao.getFacilitiesByCountryAndCity(country, city.getName());
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
                node.addNode(new DefaultMindmapNode("Inconnu", "Inconnu", "290cab",false));
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
  
    private void createPieDoctorFacility(List<Facility> l) {  
        for(Facility f:l){
        	if(f.getCapacity()!=null&&f.getCapacity().getDoctor()>0)
        	pieDoctorFacility.set(f.getName(), f.getCapacity().getDoctor());  
        }
    }

	public List<Country> getCountries() {
		if(countries==null){
			countries=cdao.getCountries();
		}
		return countries;
	}  
	
	private TreeNode treeRoot=null;
	private Doc doc;  
	  
	 public void prepareTree() {  
		 if(treeRoot==null){
	    	treeRoot = new DefaultTreeNode("root", null);  
	    	for(Country country:this.getCountries()){
	    		TreeNode doc1 = new DefaultTreeNode("country",new Doc(country.getName()), treeRoot);
	    		for(City city:country.getCities()){
	    			TreeNode doc2 = new DefaultTreeNode("city",new Doc(city.getName()), doc1);
	    			List<Facility> list = cdao.getFacilitiesByCountryAndCity(country.getName(), city.getName());
	            	 for(Facility f: list){
	            		 LOGGER.debug("FACILITY : " +country.getName()+ " " + f.getName());
	            		 TreeNode doc3 = new DefaultTreeNode("facility", f, doc2); 
	            	 }
	    		}
	    	}
		 }

	}
	 
	public void saveFacility(){
		if(currentFacility.getId()!=null)
			fdao.updateFacility(currentFacility.getId(), currentFacility);
		else fdao.getDao().persist(currentFacility);
		treeRoot=null;
		this.prepareTree();
		LOGGER.info("Facility updated gone!!!!!!!!!");
	}

    public void displaySelectedFacility(){
    	this.showMapMind=false;
    }
    
    public void activMindMap(){
    	this.showMapMind=true;
    }

    public void deleteFacility(){

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
	 
	 
		
}