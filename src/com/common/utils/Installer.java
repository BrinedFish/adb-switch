package com.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class Installer {
	Context context;
	public static String appPath;
	public static final String tag="Installer";
	public Installer(Context context) {
		super();
		
		// TODO Auto-generated constructor stub
		this.context= context;
	}
	
	public static void makeDir(String path) {
		String root[] = path.split("/");
		for (int i = 0; i < root.length-1; i++) {
			String p = "";
			for (int j = 0; j <= i; j++) {
				p = p+root[j]+"/";
			}
			CmdUtils.rootCmd("mkdir "+ p);
			/*
			try {
				//Log.d(tag, p);
				Runtime.getRuntime().exec("mkdir "+ p);
				
			} catch (IOException e) {
				 TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
	}

	private String copyFile(String filename, int ressource) {
		
		makeDir(filename);
		if (ressource>0) {
			File outFile = new File(filename);
	        //Log.d(MSG_TAG, "Copying file '"+filename+"' ...");
	        InputStream is = this.context.getResources().openRawResource(ressource);
	        byte buf[] = new byte[1024];
	        int len;
	        try {
	            OutputStream out = new FileOutputStream(outFile);
	            while((len = is.read(buf))>0) {
	                out.write(buf,0,len);
	            }
	            out.close();
	            is.close();
	        } catch (IOException e) {
	            return "Couldn't install file - "+filename+"!";
	        }
	        try {
	            Runtime.getRuntime().exec("chmod 0775 "+ filename);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		}
        
        return null;
    }
	
	public boolean isFirstRun() {
		//SharedPreferences sp = this.context.getSharedPreferences("com.lzdd.portforwarding_preferences", 0);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);
		boolean isFirstRun = sp.getBoolean("isFirstRun", true);
		Editor editor =  sp.edit();
		editor.putBoolean("isFirstRun", false);
		editor.commit();
		return isFirstRun;
	}

    public void install(HashMap<String, Integer > files) {
    	this.appPath = this.context.getApplicationContext().getFilesDir().getParent();
    	if (isFirstRun()) {
    		Iterator iter = files.entrySet().iterator();
    		while (iter.hasNext()) {
    		    Map.Entry entry = (Map.Entry) iter.next();
    		    Object key = entry.getKey();
    		    Object val = entry.getValue();
    		    this.copyFile(this.appPath+"/"+key,(Integer)val);
    		    
 
    		} 
            
		}
        
    }
    
    public static List<FileInfo> getFiles(String path,String fliter) {
		File[] files = new File(path).listFiles(); 
		List<FileInfo> list = new ArrayList<FileInfo>();
		   for (File file : files) { 
		     if ( (fliter == null)
		    		 ||(file.getName().indexOf(fliter) >= 0) ){ 
		    	 FileInfo fileInfo = new FileInfo(file.toURI());
		    	 list.add(fileInfo);
		    	 //Log.d("aaa", file.getName());
		     } 
		   } 
		 return list;
	}
    
    
}
