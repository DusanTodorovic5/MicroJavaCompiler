package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;

public class TabExtension {
	public static final Struct boolType = new Struct(Struct.Bool);
	
	public static boolean assignableTo(Struct src, Struct dst) {
		if(src.assignableTo(dst)) {
			return true;
		}
		
		if(src.getKind() == Struct.Class && dst.getKind() == Struct.Class) {
			Struct curr = src;
            while (curr != null) {
            	if (curr.equals(dst)) {
                    return true;
                }

                curr = curr.getElemType();
            }
		}
		
		return false;
	}
}
