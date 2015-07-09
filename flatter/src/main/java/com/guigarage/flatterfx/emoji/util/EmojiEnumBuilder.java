package com.guigarage.flatterfx.emoji.util;

import java.io.File;

public class EmojiEnumBuilder {

	public static void main(String[] args) {
		for(File f : new File("src/main/resources/com/guigarage/flatterfx/emoji/").listFiles()) {
			if(f.isFile() && f.getName().endsWith(".png") && !f.getName().contains("-") && f.getName().startsWith("1f")) {
				
				String hexCode = f.getName().replace(".png", "").substring(1).toUpperCase();
				
				System.out.println("U_" + hexCode + "((char)0x" + hexCode + "),");
				
				
			//	U_F627((char)0xF627);
			}
		
		}
	}
}
