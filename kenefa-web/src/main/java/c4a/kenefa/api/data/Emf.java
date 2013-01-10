package c4a.kenefa.api.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Emf {
private static Emf emfactory=new Emf();
private EntityManagerFactory  emf;
	public EntityManagerFactory getEmf() {
	return emf;
}
	public static Emf getEmfactory() {
	return emfactory;
}
	private Emf() {
		emf=Persistence.createEntityManagerFactory("kenefa");
	}

}
