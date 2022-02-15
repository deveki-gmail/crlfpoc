package com.concretepage.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        InputStream inPut = getClass().getResourceAsStream("abc.txt");
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
		
		
		if(bData!=null && bData.length > 0){
			
			for(byte b=0; b < bData.length; b++){
				if(bData[b] == 10){
					bData[b]=13;
					b++;
					bData[b]=10;
				}
			}
			
		}
		int i = -1;
		while(iRead != -1){
			outStream.write(bData, 0, iRead);
			iRead = inPut.read(bData);
			
			for(byte b=0; b < bData.length; b++){
				if(bData[b] == 10){
					bData[b]=13;
					b++;
					bData[b]=10;
				}
			}
			
		}	
		
		inPut.close();
		outStream.flush();
		outStream.close();
		
    }
} 