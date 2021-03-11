package ca.sheridancollege.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ca.sheridancollege.databases.Databases;
import cs.sheridancollege.beans.Matrix;

import org.springframework.beans.factory.annotation.Autowired;

import cs.sheridancollege.beans.Users;
import ca.sheridancollege.databases.DatabaseAccess;

@Controller
public class MatrixControllers {
	
	private static String Alphabet = "abcdefghijklmnopqrstuvwxyz";
	private static int[] x = {-1,-1,-1,0,0,1,1,1}; 
    private static int[] y = {-1,0,1,-1,1,-1,0,1};
	
    @Autowired
	private DatabaseAccess da;
	
	@GetMapping("/")
	public String goHome(Model model, @ModelAttribute Users user) {
		model.addAttribute("user", new Users());
		da.addUser(user);
		return "home.html";
	}
	
	@GetMapping("/add")
	public String createMatrix(Model model) {
		model.addAttribute("matrix", new Matrix());
		return "matrix.html";
	}
	
	@GetMapping("/matrix")
	public String saveMatrix(Model model, @ModelAttribute Matrix matrix) {
		Databases.A.clear();
		Databases.key = "";
		Databases.matrixs.clear();
		Databases.Result.clear();
		char[][] a = new char[500][500];
		int length = matrix.getN();
		Databases.key = matrix.getKey();
		for (int i=0;i<length;i++) {
			Databases.A.add(new ArrayList<Character>());
			for (int j=0;j<length;j++) {
				int index = (int)(Math.random()*25);
				a[i][j] = Alphabet.charAt(index);
				Databases.A.get(i).add(j,a[i][j]);
			}
		}
		Databases.matrixs.add(matrix);
		return "matrix.html";
	}
	
	@GetMapping("/search")
	public String searchMatrix(Model model) throws IOException {
		PrintWriter output = new PrintWriter(new File("src/main/resources/static/output.txt"));
		char[][] a = new char[500][500];
		String keyWord = Databases.key;
		int keyLength = keyWord.length();
		int length = Databases.A.size();
		for (int i=0;i<length;i++) {
			output.println();
			for (int j=0;j<length;j++) {
				a[i][j] = Databases.A.get(i).get(j);
				output.printf("%3s", a[i][j]);
			}
		}
		
		boolean result = false;
		for (int i=0;i<length;i++) { 
			for (int j=0;j<length;j++) {
				if (a[i][j] == keyWord.charAt(0)) {
					for (int dir=0;dir<8;dir++) { 
						int k;
						int rd = i + x[dir];
						int cd = j + y[dir];
						for (k = 1; k < keyLength; k++) { 
							if ((rd>=length)||(rd<0)||(cd>=length)||(cd<0)) 
								break; 
							if (a[rd][cd] != keyWord.charAt(k)) 
								break; 
							rd += x[dir];
							cd += y[dir];
						} 
						if (k == keyLength) {
							Databases.Result.add("Found at (" + i + ", " + j + ") to (" + (rd-x[dir]) + ", " + (cd-y[dir]) + ")");
							result = true;
						}
					}
				}
			}
		}
		if (result == false) Databases.Result.add("Not found");
		output.close();
		return "result.html";
	}
	
	@GetMapping("/view")
	public String displayMatrix(Model model) {
		model.addAttribute("matrixs", Databases.A);
		model.addAttribute("result", Databases.Result);
		return "displayMatrix.html";
	}
}
