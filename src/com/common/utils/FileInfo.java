package com.common.utils;

import java.io.File;
import java.net.URI;

public class FileInfo extends File {
	
	public boolean selected;

	public FileInfo(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
		selected = false;
	}

}
