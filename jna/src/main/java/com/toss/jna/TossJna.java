package com.toss.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public class TossJna {

	public interface TossLibrary extends Library {
		TossLibrary INSTANCE = (TossLibrary) Native.loadLibrary("toss.dll", TossLibrary.class);

		int add(int x, int y);

		int referenceCase(int x, int y, IntByReference reference);
	}

	public static void main(String[] args) {
		TossLibrary library = TossLibrary.INSTANCE;
		System.out.println(library.add(1, 2));
		IntByReference reference = new IntByReference();
		int bs = library.referenceCase(3, 1, reference);
		System.out.println(bs);
		System.out.println(reference.getValue());
	}
}
