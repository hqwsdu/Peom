package com.gqq.tangpoem;

public class Common {

	public static PoemType getType(int type) {
		PoemType pType = 0 == type ? PoemType.Shi : (1 == type ? PoemType.Ci : PoemType.Qita);
		return pType;
	}
}
