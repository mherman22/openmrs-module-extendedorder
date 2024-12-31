/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.extendedorder;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Order;
import org.openmrs.User;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.module.extendedorder.api.dao.ExtendedorderDao;
import org.openmrs.module.extendedorder.api.impl.ExtendedorderServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * This is a unit test, which verifies logic in ExtendedorderService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class ExtendedorderServiceTest {
	
	@InjectMocks
	ExtendedorderServiceImpl basicModuleService;
	
	@Mock
	ExtendedorderDao dao;
	
	@Mock
	UserService userService;
	
	@Mock
	EncounterService encounterService;
	
	@Mock
	PatientService patientService;
	
	@Mock
	ConceptService conceptService;
	
	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Ignore("Unignore if you want to make the Item class persistable, see also Item and liquibase.xml")
	public void saveItem_shouldSetOwnerIfNotSet() {
		//Given
		ExtendedOrder order = new ExtendedOrder();
		order.setAction(Order.Action.NEW);
		order.setPatient(patientService.getPatient(7));
		order.setConcept(conceptService.getConcept(5497));
		order.setCareSetting(basicModuleService.getCareSetting(1));
		order.setOrderer(basicModuleService.getOrder(1).getOrderer());
		order.setEncounter(encounterService.getEncounter(3));
		order.setEncounter(encounterService.getEncounter(3));
		order.setOrderType(basicModuleService.getOrderType(17));
		order.setDateActivated(new Date());
		order.setScheduledDate(DateUtils.addMonths(new Date(), 2));
		order.setUrgency(Order.Urgency.ON_SCHEDULED_DATE);
		order.setOrderBy(userService.getUser(1));
		
		when(dao.save(order)).thenReturn(order);
		
		User user = new User();
		when(userService.getUser(1)).thenReturn(user);
		
		//When
		basicModuleService.save(order);
		
		//Then
		assertThat(order, hasProperty("orderBy", is(user)));
	}
}
