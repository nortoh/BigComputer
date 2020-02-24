
package com.nortoh.src.utils;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class Timer {
    
    private long startTime;
    private long endTime;
    private boolean hasEnded;
    
    public Timer(){
        hasEnded = false;
    }//end Timer
    
    public void start(){
         startTime = System.nanoTime();
         hasEnded = false;
    }
    
    public void end(){
        endTime = System.nanoTime();
        hasEnded = true;
    }
    
    
    public long nanoSeconds(){
       
        long durationInNano = (endTime - startTime);      
        
        return durationInNano;
    }
    
   
    public long millSeconds(){
       
        long durationInNano = (endTime - startTime);  
        long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);  
        
        return durationInMillis;
    }
    
    public double seconds(){
       
        long durationInNano = (endTime - startTime);  
        long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano); 
        double durationInSeconds = durationInMillis/1000.0;
        
        return durationInSeconds;
    }
    
   
}
