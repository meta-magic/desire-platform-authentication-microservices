package com.desire3d.auth.utils;

/**
 * Enum to represent available product types for subscription
 * 
 * @author Mahesh Pardeshi
 */
public enum SubscriptionType {
	
	DEFAULT_SUBSCRIPTION(0),
	AMEXIO_CANVAS_LITE(1),
	AMEXIO_CANVAS_SE(2),
	AMEXIO_CANVAS_EE(3),
	DROIT_STUDIO_SE(4),
	DROIT_STUDIO_EE(5),
	AMEXIO_CODE_PIPELINE(6);
	
	private Integer value; 
	
	private SubscriptionType(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return super.toString().replaceAll("_", " ");
	}
	
	/**
	 * method to get the {@link SubscriptionType} corresponding to the value
	 *
	 * @param value real value
	 * @return SubscriptionType corresponding to the value
	 *
	 * @throws IllegalArgumentException
	 */
	public static SubscriptionType fromValue(Integer value) {
		if (value == null) {
			throw new IllegalArgumentException("Value cannot be null or empty!");
		}

		for (SubscriptionType enumEntry : SubscriptionType.values()) {
			if (enumEntry.getValue().equals(value)) {
				return enumEntry;
			}
		}

		throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
	}
	
}