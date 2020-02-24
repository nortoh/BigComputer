/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nortoh.src.utils;

import java.io.File;

/**
 *
 * @author Christian
 */
public class FileUtils {
    
    
    public static String getExtension(File file) {
        if(file.exists()) {
            String fileName = file.getName();
            String[] fileNameSplit = fileName.split(".");
            if(fileNameSplit.length <= 1) {
                return fileNameSplit[1];
            }
        }
        
        return null;
    }
    
    
    
}
