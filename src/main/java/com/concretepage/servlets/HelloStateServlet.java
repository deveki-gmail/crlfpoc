package com.concretepage.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;
import java.nio.charset.*;
public class HelloStateServlet extends HttpServlet   {
	private static final long serialVersionUID = 1L;
	
	boolean flag = false;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request,response);
	}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	System.out.println("User detail : "+request.getHeader("User-Agent"));
    	
    	
    	
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=sample.txt");
        //PrintWriter out = response.getWriter();
        //File file = ResourceUtils.getFile("abc.txt");
        //InputStream inPut = new FileInputStream(file);
        //InputStream inPut = getClass().getResourceAsStream("abc.txt");
        String val = request.getParameter("size");
        Resource resource  = null;
        
        if("10mb".equalsIgnoreCase(val)){
        	resource = new ClassPathResource("classpath:10mb.txt");
        }
        if("20mb".equalsIgnoreCase(val)){
        	resource = new ClassPathResource("classpath:20mb.txt");
        }
        if("25mb".equalsIgnoreCase(val)){
        	resource = new ClassPathResource("classpath:25mb.txt");
        }
        
        if(resource == null){
        	resource = new ClassPathResource("classpath:10mb.txt");
        }
        
        
        InputStream inPut = resource.getInputStream();
        ServletOutputStream outStream = response.getOutputStream();
        long time_start = new Date().getTime();
		
        byte[] bData = new byte[1024];
        byte[] tempByteArray = null;
		int iRead = inPut.read(bData);
		if(flag){
			tempByteArray = new byte[iRead];
			System.arraycopy(bData, 0, tempByteArray, 0, iRead);
			bData = tempByteArray;
			bData = changeIfRequired(bData, iRead);
		}
		while(iRead != -1){
			
			if(flag){
				outStream.write(bData, 0, bData.length);
			}else{
				outStream.write(bData, 0, iRead);
			}
			
			iRead = inPut.read(bData);
			if(iRead != -1 && flag) {
				tempByteArray = new byte[iRead];
				System.arraycopy(bData, 0, tempByteArray, 0, iRead);
				bData = tempByteArray;
				bData = changeIfRequired(bData, iRead);
			}
		}	
		
		long time_end = new Date().getTime();
		System.out.println("Total taken time for size "+val+"   : "+(time_end - time_start) +" millies ");
		inPut.close();
		outStream.flush();
		outStream.close();
		
    }
    private static byte[] changeIfRequired(byte[] original, int read){
		final byte[] transformed = new byte[read * 2];
    	int len = 0;
    	for(int i=0; i < read; i++){
    		byte val = original[i];
    		if(val == (byte) '\n'){   
    			transformed[len] = (byte) '\r';
    			len++;
    			transformed[len] = (byte) '\n';    // ... insert a \n
    		    len++;                             // ... being sure to track the number of bytes written
    		}else{
    			transformed[len] = val;
    			len++;
    		}
    	} 
    	len = len - 1;
    	final byte[] result = new byte[len];              // prepare an exact sized array
    	System.arraycopy(transformed, 0, result, 0, len);
    	return result;
    }
} 
