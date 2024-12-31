/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.extendedorder.api.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.User;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.api.db.hibernate.HibernateOrderDAO;
import org.openmrs.module.extendedorder.ExtendedOrder;
import org.openmrs.module.extendedorder.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("extendedorder.ExtendedorderDao")
public class ExtendedorderDao extends HibernateOrderDAO {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	public ExtendedorderDao() {
	}
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public ExtendedorderDao(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		super.setSessionFactory(this.sessionFactory.getHibernateSessionFactory());
	}
	
	public ExtendedOrder save(ExtendedOrder order) {
		getSession().saveOrUpdate(order);
		return order;
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtendedOrder> getAllOrdersByProvider(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		
		Criteria criteria = getSession().createCriteria(ExtendedOrder.class);
		criteria.add(Restrictions.eq("orderBy", user));
		return criteria.list();
	}
}
