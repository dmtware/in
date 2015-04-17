/*
 * Categories Window class
 */
package com.dmtware.in.view;

import java.awt.EventQueue;

import javax.swing.JDialog;

import java.awt.Toolkit;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.Category;
import com.dmtware.in.model.CategoryTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoriesWindow extends JDialog {
	
	// database connection declaration
	SQLiteCon conn;
	
	// table
	private JTable tableCategories;
	private JTextField textFieldNewCategory;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CategoriesWindow dialog = new CategoriesWindow();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public CategoriesWindow() {
		
		// connect to database
		conn = new SQLiteCon();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(CategoriesWindow.class.getResource("/com/dmtware/in/view/logo_2.png")));
		setTitle("In - Categories");
		setModal(true);
		setBounds(100, 100, 378, 252);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 11, 237, 160);
		getContentPane().add(scrollPane);
		
		tableCategories = new JTable();
		scrollPane.setViewportView(tableCategories);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(CategoriesWindow.class.getResource("/com/dmtware/in/view/logo_2.png")));
		label.setBounds(20, 11, 72, 72);
		getContentPane().add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Add category
				
			}
		});
		btnAdd.setBounds(272, 176, 80, 23);
		getContentPane().add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				removeCategory();
			}
		});
		btnRemove.setBounds(12, 149, 89, 23);
		getContentPane().add(btnRemove);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(12, 104, 89, 23);
		getContentPane().add(btnEdit);
		
		textFieldNewCategory = new JTextField();
		textFieldNewCategory.setBounds(115, 177, 147, 20);
		getContentPane().add(textFieldNewCategory);
		textFieldNewCategory.setColumns(10);
		
		setLocationRelativeTo(null);
		
		getCategoriesToTable();

	}
	
	// get all products to the table (join table query)
	public void getCategoriesToTable() {

		try {

			List<Category> categories = null;

			categories = conn.getAllCategories();

			CategoryTableModel model = new CategoryTableModel(
					categories);

			tableCategories.setModel(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// remove category
	public void removeCategory(){
		int idCol = 0;
		int nameCol = 1;

		// if row selected
		if (!(tableCategories.getSelectedRow() == -1)) {

			int selectedRow = tableCategories.getSelectedRow();

			String catId = tableCategories.getValueAt(selectedRow, idCol)
					.toString();

			String catName = tableCategories.getValueAt(selectedRow, nameCol)
					.toString();

			System.out.println(catId + " " + catName);

			int reply = JOptionPane.showConfirmDialog(null,
					"Do you really want to remove this category?", "Remove?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeCategoryQuery(catId, catName);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				//refreshTable();

				JOptionPane.showMessageDialog(null, "Category removed");

			} else {
				// do nothing
			}

		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"In order to remove category please select category row first");
		}
	}
}
