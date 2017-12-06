package com.desire3d.auth.exceptions;

public class DataNotFoundException extends BaseException {
	private String message;

	public DataNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	/*public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor c = Class.forName("com.desire3d.auth.exceptions.DataNotFoundException").getConstructor(String.class);
		BaseException e = (BaseException)c.newInstance("Data not found exception");
		System.out.println(e.getMessage());
	}*/
}
