package c4a.kenefa.api.test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import c4a.kenefa.api.data.FacilityDao;
import c4a.kenefa.api.model.Facility;

public /*abstract*/ class FacilityTest {
	private static final Logger LOGGER = Logger.getLogger(FacilityTest.class);
	@Inject
	FacilityDao<Facility> fdao;//TODO
	
	@Before
    public void doBefore() {
        LOGGER.debug("do Before Test");
        
    }
	
	/**
	 * Teste the number of facilities  nb=5
	 */
	@Test
	public void getNbFacilities(){
		LOGGER.debug("Test number of facilities");
		// Arrange
        final int nbFacilities = 0;
        // Act
        final List<Facility> facilities = new ArrayList<Facility>(0);  //fdao.getFacilities(0, 5);
        // Assert
        Assert.assertEquals(nbFacilities, facilities.size());
	}
	/**
	 * Teste the first result result=Hopital Roi Baudouin
	 */
	@Test
	public void getFirstFacility(){
		LOGGER.debug("Test first Facility");
		LOGGER.debug("Hopital Roi Baudouin");
	}
	
}
