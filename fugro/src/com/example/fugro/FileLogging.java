package com.example.fugro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.util.Log;
import android.os.Environment;

public class FileLogging {

	
		private final static String logFileName =  Environment.getExternalStorageDirectory().getPath()+"/Log/fglog.txt";
		private final static String backupLogFileName =  Environment.getExternalStorageDirectory().getPath()+"/Log/fglog_backup.txt";

		public final static long FileSize(String fileName) throws Exception {
		        FileInputStream fis = null;
		        try {
		            File me = new File(fileName);
		            fis = new FileInputStream(me);
		            return fis.getChannel().size();
		        } finally {
		            fis.close();
		        }
		    }
			
			private final static void CheckLogFileSize() {
		        long maxLogSize = 2048 * 1024;

		        try {
		        	if (FileSize(logFileName) > maxLogSize) {
		        		File f = new File(backupLogFileName);
		        		if (f.exists()) {
		        			f.delete();
		        		}
		        		File of = new File(logFileName);
		        		of.renameTo(f);
		        	}
				} catch (Exception e) {
		        }
			}

			public final static void Log(String tag, String msg) {
				try {
					Log.d(tag, msg);
					CreatefileIfNotExist();
					CheckLogFileSize();
				    java.io.FileWriter fw = new java.io.FileWriter(logFileName, true); //the true will append the new data
				    fw.write(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()) + " " + tag + " " + msg + System.getProperty("line.separator"));//appends the string to the file
				    fw.close();
				}catch (IOException e) {
					Log.e(tag, "***ERROR Logging string: " + msg + " error: " + e.toString());
				}
			}
			
			private static void CreatefileIfNotExist() {
				// TODO Auto-generated method stub
				File f = new File(logFileName);
        		if (!f.exists()) {
        			try {
						f.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
				
			}

			public final static void LogError(String tag, String msg) {
				try {
					Log.e(tag, msg);
					CheckLogFileSize();
				    java.io.FileWriter fw = new java.io.FileWriter(logFileName, true); //the true will append the new data
				    fw.write(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()) + " ERROR:" + msg + System.getProperty("line.separator"));//appends the string to the file
				    fw.close();
				} catch (IOException e) {
					Log.e(tag, "***ERROR Logging error string: " + msg + " error: " + e.toString());
				}
			}
	}
	

