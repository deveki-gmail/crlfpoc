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
    	
    	System.out.println("User detail : "+request.getHeader("User-Agent"));
    	
    	
    	
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=sample.txt");
        //PrintWriter out = response.getWriter();
        //File file = ResourceUtils.getFile("abc.txt");
        //InputStream inPut = new FileInputStream(file);
        //InputStream inPut = getClass().getResourceAsStream("abc.txt");
        Resource resource = new ClassPathResource("classpath:abc1.txt");
        InputStream inPut = resource.getInputStream();
        ServletOutputStream outStream = response.getOutputStream();
		
		byte[] bData = new byte[1024];
		int iRead = inPut.read(bData);
		bData = changeIfRequired(bData);
		
		while(iRead != -1){
			outStream.write(bData, 0, iRead);
			iRead = inPut.read(bData);
			if(iRead != -1) {
				bData = changeIfRequired(bData);
			}
		}	
		
		inPut.close();
		outStream.flush();
		outStream.close();
		
    }
	private static byte[] changeIfRequired(byte[] original){
		final byte[] transformed = new byte[original.length * 2];
    	int len = 0;
    	for(int i=0; i < original.length; i++){
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
    	final byte[] result = new byte[len];              // prepare an exact sized array
    	System.arraycopy(transformed, 0, result, 0, len);
    	//for(int i=0; i < result.length; i++){
    	//	result[i] = original[i];
    	//}
    	return result;
    }
} 
