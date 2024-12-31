package org.openmrs.module.extendedorder;

import org.openmrs.Order;
import org.openmrs.User;

@SuppressWarnings("unused")
public class ExtendedOrder extends Order {
	
	private User orderBy;
	
	public ExtendedOrder() {
		super();
	}
	
	public ExtendedOrder(Integer orderId, User orderBy) {
		super(orderId);
		this.orderBy = orderBy;
	}
	
	public User getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(User orderBy) {
		this.orderBy = orderBy;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", orderBy=" + (orderBy != null ? orderBy.toString() : "null");
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!super.equals(o))
			return false;
		if (!(o instanceof ExtendedOrder))
			return false;
		ExtendedOrder that = (ExtendedOrder) o;
		return orderBy != null ? orderBy.equals(that.orderBy) : that.orderBy == null;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
		return result;
	}
}
