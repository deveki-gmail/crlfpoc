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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request,response);
	}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=sample.txt");
        
        //PrintWriter out = response.getWriter();
        //File file = ResourceUtils.getFile("abc.txt");
        //InputStream inPut = new FileInputStream(file);
        //InputStream inPut = getClass().getResourceAsStream("abc.txt");
        Resource resource = new ClassPathResource("classpath:abc.txt");
        InputStream inPut = resource.getInputStream();
        ServletOutputStream outStream = response.getOutputStream();
		/*
		System.out.println("\n : ");
		for(byte b : "\n".getBytes(StandardCharsets.UTF_8)){
			System.out.println(b);
		}
		System.out.println("\r\n : ");
		for(byte b : "\r\n".getBytes(StandardCharsets.UTF_8)){
			System.out.println(b);
		}*/
		byte[] bData = new byte[100];
		int iRead = inPut.read(bData);
		bData = changeIfRequired(bData);
		/*
		if(bData!=null && bData.length > 0){
			
			for(byte b=0; b < bData.length; b++){
				if(bData[b] == 10){
					bData[b]=13;
					b++;
					bData[b]=10;
				}
			}
			
		}*/
		int i = -1;
		while(iRead != -1){
			outStream.write(bData, 0, iRead);
			iRead = inPut.read(bData);
			if(iRead != -1) {
				bData = changeIfRequired(bData);
			}
			/*
			for(byte b=0; b < bData.length; b++){
				if(bData[b] == 10){
					bData[b]=13;
					b++;
					bData[b]=10;
				}
			}*/
			
		}	
		
		inPut.close();
		outStream.flush();
		outStream.close();
		
    }
	private byte[] changeIfRequired(byte[] original){
    	final byte[] transformed = new byte[original.length * 2];
    	int len = 0;
    	for(int i=0; i < original.length; i++){
    		transformed[len] = original[i];
    		len++;
    		if(original[i] == (byte) '\n'){         
    		  if (i + 1 < original.length && original[i+1] != (byte) '\r'){   // ... and that character is not a \n ...
    			  transformed[i] = (byte) '\r';
    			  transformed[len] = (byte) '\n';    // ... insert a \n
    		      len++;                             // ... being sure to track the number of bytes written
    		  }
    		}// end outer if
    	}// enf outer for 
    	final byte[] result = new byte[len];              // prepare an exact sized array
    	System.arraycopy(transformed, 0, result, 0, len);
    	return transformed;
    }
} 
