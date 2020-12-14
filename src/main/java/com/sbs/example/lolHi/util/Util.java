package com.sbs.example.lolHi.util;

import java.math.BigInteger;

public class Util {

	public static int getAsInt(Object object) {
		// TODO Auto-generated method stub
		return getAsInt(object, -1);
	}

	public static int getAsInt(Object object, int defaultValue) {
		// TODO Auto-generated method stub
		
		if(object instanceof BigInteger) {
			return ((BigInteger)object).intValue();
		}
		else if(object instanceof Long) {
			return (int)((long)object);
		}
		else if(object instanceof Integer) {
			return (int)object;
		}
		else if(object instanceof String) {
			return Integer.parseInt((String)object);
		}	
		
		return defaultValue;
	}

	public static String getAsStr(Object object, String string) {
		if(object instanceof String ) {
			return (String)(object);
		}
		return object.toString();
	}

}
