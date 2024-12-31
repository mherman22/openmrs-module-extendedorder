/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.extendedorder.dao;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.Ignore;
import org.openmrs.Order;
import org.openmrs.api.EncounterService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.extendedorder.ExtendedOrder;
import org.openmrs.module.extendedorder.Item;
import org.openmrs.module.extendedorder.api.ExtendedorderService;
import org.openmrs.module.extendedorder.api.dao.ExtendedorderDao;
import org.openmrs.module.extendedorder.api.impl.ExtendedorderServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
@Ignore
public class ExtendedorderDaoTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	ExtendedorderDao dao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ExtendedorderServiceImpl extendedorderService;
	
	@Autowired
	EncounterService encounterService;
	
	@Ignore("Unignore if you want to make the Item class persistable, see also Item and liquibase.xml")
	public void saveItem_shouldSaveAllPropertiesInDb() {
		//Given
		ExtendedOrder order = new ExtendedOrder();
		order.setAction(Order.Action.NEW);
		order.setPatient(Context.getPatientService().getPatient(7));
		order.setConcept(Context.getConceptService().getConcept(5497));
		order.setCareSetting(extendedorderService.getCareSetting(1));
		order.setOrderer(extendedorderService.getOrder(1).getOrderer());
		order.setEncounter(encounterService.getEncounter(3));
		order.setEncounter(encounterService.getEncounter(3));
		order.setOrderType(extendedorderService.getOrderType(17));
		order.setDateActivated(new Date());
		order.setScheduledDate(DateUtils.addMonths(new Date(), 2));
		order.setUrgency(Order.Urgency.ON_SCHEDULED_DATE);
		order.setOrderBy(userService.getUser(1));
		
		//When
		dao.save(order);
		
		//Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
		Context.flushSession();
		Context.clearSession();
		
		//Then
		ExtendedOrder savedOrder = (ExtendedOrder) dao.getOrderByUuid(order.getUuid());
		
		assertThat(savedOrder, hasProperty("uuid", is(order.getUuid())));
		assertThat(savedOrder, hasProperty("orderBy", is(order.getOrderBy())));
	}
}
