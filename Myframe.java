import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.util.*;
import java.sql.*;


public class Myframe{
	public static void main(String[] args){
		JFrame frame = new JFrame("Cosmetic Ingredient Checker");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		
		JLabel label1 = new JLabel("Enter Ingredient Name");
		
		JTextField searchField=new JTextField();
		searchField.setPreferredSize(new Dimension(200,25));
		searchField.setBackground(Color.LIGHT_GRAY);
		JButton    searchButton=new JButton("search");
		
		
		JTextArea resultArea = new JTextArea();
		resultArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(resultArea);
		scrollPane.setPreferredSize(new Dimension(300,400));
		
		searchButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String text=searchField.getText();
			if(!text.isEmpty()){
				resultArea.setText("Searching...");
				searchInDatabase(text,resultArea);
				
			}
			else{
				resultArea.setText("please enter sone text");
			}
		}
		});
		
	panel1.add(label1);
	panel1.add(searchField);
	panel1.add(searchButton);
	panel1.add(scrollPane);
	frame.add(panel1);
	frame.setVisible(true);
	}
	
	public static void searchInDatabase(String text,JTextArea resultArea){
	try{
	
		//Class.forName("org.postgresql.Driver");
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost/cosmetic_ingredients","username","pass");
		
		String query="Select * from ingredients where ingredient_name ILIKE ?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1,"%"+text.trim().toLowerCase()+"%");
		ResultSet rs=ps.executeQuery();
		
		resultArea.setText("");
		while(rs.next()){
			StringBuilder row = new StringBuilder(); 
			
			row.append("ingredient_name : ").append(rs.getString(1)).append("\n")
	.append("inci_name : ").append(rs.getString(2)).append("\n")
	.append("function : ").append(rs.getString(3)).append("\n")
	.append("safety_rating : ").append(rs.getString(4)).append("\n")
	.append("allergen : ").append(rs.getString(5)).append("\n")
	.append("common_products : ").append(rs.getString(6)).append("\n")
	.append("eco_friendly : ").append(rs.getString(7)).append("\n")
	.append("research_link : ").append(rs.getString(8)).append("\n");
	
	resultArea.append(row.toString());
		
		}
		if(resultArea.getText().isEmpty()){
			resultArea.setText("No result Found");
		}	
		rs.close();
		ps.close();
		con.close();
		}catch(SQLException e){
			System.out.println("Error"+e.getMessage());
		}
	
	}
	

}
