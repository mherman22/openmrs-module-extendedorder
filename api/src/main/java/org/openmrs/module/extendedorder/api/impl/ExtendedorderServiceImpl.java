/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.extendedorder.api.impl;

import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.api.impl.OrderServiceImpl;
import org.openmrs.module.extendedorder.ExtendedOrder;
import org.openmrs.module.extendedorder.Item;
import org.openmrs.module.extendedorder.api.ExtendedorderService;
import org.openmrs.module.extendedorder.api.dao.ExtendedorderDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SuppressWarnings("unused")
public class ExtendedorderServiceImpl extends OrderServiceImpl implements ExtendedorderService {
	
	private ExtendedorderDao dao;
	
	public void setDao(ExtendedorderDao dao) {
		this.dao = dao;
		super.setOrderDAO(this.dao);
	}
	
	@Override
	public ExtendedOrder save(ExtendedOrder order) {
		return dao.save(order);
	}
	
	@Override
	public List<ExtendedOrder> getAllOrdersByProvider(User user) {
		return dao.getAllOrdersByProvider(user);
	}
}
