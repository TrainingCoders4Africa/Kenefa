import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import c4a.kenefa.api.data.DAOJeeImpl;
import c4a.kenefa.api.data.FacilityDao;
import c4a.kenefa.api.data.FacilityDaoImpl;
import c4a.kenefa.api.model.Facility;




public class Main {
    /*public static void main(String[] args) {
       System.out.println("DataNucleus Tutorial with JPA");
        System.out.println("=============================");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("kenefa");
        
        // Create entity manager
        EntityManager em = emf.createEntityManager();
        // if you need transactions i will not use them
        //EntityTransaction tx = em.getTransaction();
        
        System.out.println("Save entities");
        System.out.println("=============================");
        Person p = new Person();
        p.setFirstName("test-name-1");
        p.setLastName("test-lastn-1");
        p.setLevel(5);
        
        em.persist(p);
        String id = p.getId();
        System.out.println("id: " + id);
        
        p = new Person();
        p.setFirstName("test-name-2");
        p.setLastName("test-lastn-2");
        p.setLevel(4);
        
        em.persist(p);
        id = p.getId();
        System.out.println("id: " + id);
        // to save for sure in db
        em.close();
        
        System.out.println("find by id");
        System.out.println("=============================");
        em = emf.createEntityManager();
        Person p2 = em.find(Person.class, id);
        System.out.println("found: " + p2.toString());
        
        
        //em = emf.createEntityManager();
        System.out.println("query");
        System.out.println("=============================");
        String qstr = "SELECT p FROM " + Person.class.getName() +
            " p WHERE p.level >= 3 ";
        System.out.println("query: " + qstr);
        Query q = em.createQuery(qstr);
        List<Person> list = (List) q.getResultList();
        for (Person per : list) {
            System.out.println("person > " + per.toString());
        }
        
        System.out.println("Remove from db");
        System.out.println("=============================");
        for (Person per : list) {
            em.remove(per);
        }
        em.close();
    	 System.out.println("CDI with WEld!!!");
		try{
			TranslateService t = beanContainer.getBeanByType(TranslateService.class);
			 System.out.println("TranslateService :"+t.hello());
		}catch(Exception e){
			e.printStackTrace();
		}
    }*/
	 
	 public static void main(String ar[]){
		 System.out.println("---DataNucleus Tutorial with JPA");
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("kenefa");
	        String jpql="SELECT f FROM " + Facility.class.getName() + " f where upper(f.country) = '" + "Senegal".toUpperCase() + "' and f.city = '"+"DAKAR"+"'";
	        // Create entity manager
	        EntityManager em = emf.createEntityManager();
	       System.out.println("find --: "+new FacilityDaoImpl().getFacilities(0, 4));
	        /*Country c=new Country("COTE D'IVOIRE");
	        c.setCities(new ArrayList<City>());
	        City ci=new City("ABIDIAN");
	        c.getCities().add(ci);
	        ci=new City("YAMOUSSOUKORO");
	        c.getCities().add(ci);
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        em.persist(c); 
	        System.out.println("country enreg : "+c.getId());
	        tx.commit();*/
		 
		/*System.out.println("DataNucleus Tutorial with JPA");
	        System.out.println("=============================");
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("kenefa");
	        
	        // Create entity manager
	        EntityManager em = emf.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        System.out.println("Save Facility object");
	        Capacity capacity = new Capacity(120, 13, 56);
	        Service service = new Service(true, true, false, true, false);
	        Rating rating= new Rating(5, 3, 4, 5, 4, 3, 4);
	        //1st facility saved!!!
 Facility facility1 = new Facility("ROI BAUDOUIN","GUEDIAWAYE","33 823-34-34",2d,3d,"Hospital", new Date(), capacity);
	        
	        //em.persist(facility1);
	       // System.out.println("id: " + facility1.getId());
	        //2nd facility saved!!!
	         * Facility facility2=new Facility("CHU FANN", "AVENUE CHEIKH ANTA DIOP", "+221 33821-23-23", "PUBLIC",
	    			"DAKAR", "SENEGAL", "www.chu-fann.org", "11H:30 - 15H:00/ 18H:00 - 21H:15",
	    			17d, 16.7d, "HOSPITAL", new Date(),
	    			capacity, service, rating);
	    			facility2=new Facility("CLINIQUE LE PASTEUR", "RUFISQUE RUE 10", "+221 33821-93-03", "PRIVATE",
	    			"RUFISQUE", "SENEGAL", "www.clinique-paSteur.org", "11H:30 - 15H:00/ 18H:00 - 21H:15",
	    			17d, 16.7d, "CLINIC", new Date(),
	    			capacity, service, rating);
	        em.persist(facility2);
	        Facility facility2=new Facility("CENTRE DE SANTE KEUR SOEUR ", "PIKINE TALLY BOU MACK", "+221 33821-93-03", "PUBLIC",
	    			"PIKINE", "SENEGAL", "www.clinique-paSteur.org", "11H:30 - 17H:00/ 18H:00 - 21H:15",
	    			17d, 16.7d, "HEALTH CENTER", new Date(),
	    			capacity, service, rating);
	        em.persist(facility2);
	        
	        facility2=new Facility("ABASS NDAO", "MEDINA RUE 33", "+221 33821-93-03", "PUBLIC",
	    			"DAKAR", "SENEGAL", "www.clinique-paSteur.org", "11H:30 - 11H:00/ 18H:00 - 21H:15",
	    			17d, 16.7d, "CLINIC", new Date(),
	    			capacity, service, rating);
	        em.persist(facility2);
	        
	        facility2=new Facility("CLINIQUE YA SALAM", "RUFISQUE ALLE GOR MACK", "+221 33821-73-03", "PRIVATE",
	    			"RUFISQUE", "SENEGAL", "www.yaSalam.org", "11H:30 - 15H:00/ 19H:00 - 21H:15",
	    			17d, 16.7d, "CLINIC", new Date(),
	    			capacity, service, rating);
	        em.persist(facility2);
	        
	        facility2=new Facility("CLINIQUE LES MAMELLES", "MAMELLES AVIATION", "+221 33821-93-03", "PRIVATE",
	    			"DAKAR", "SENEGAL", "www.clinique-mamelle.org", "10H:30 - 14H:00/ 18H:00 - 21H:15",
	    			17d, 16.7d, "CLINIC", new Date(),
	    			capacity, service, rating);
	        em.persist(facility2);
	        System.out.println("id: " + facility2.getId());
	        
	        tx.commit();
	        em.close();*/
	 }
}