/*
 * Add Product Window class
 */

package com.dmtware.in.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.Category;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditProductWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	// default constructor
	public EditProductWindow() {

	}

	// database class declaration
	SQLiteCon conn;

	// instance of MainWindow declaration (gives option of refreshing the table
	// in main window)
	MainWindow mainW;
	
	String currentProductName = "";

	// fields that need access
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldName;
	private JTextField textFieldType;
	private JTextField textFieldStock;
	JComboBox comboBoxCategory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditProductWindow dialog = new EditProductWindow();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditProductWindow(MainWindow mw) {

		// initialise access to the main window (and table refresh method)
		mainW = mw;

		// initialise database connection
		conn = new SQLiteCon();

		setModal(true);
		setResizable(false);
		setTitle("In - Edit Product");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				EditProductWindow.class
						.getResource("/com/dmtware/in/view/logo_2.png")));
		setBounds(100, 100, 396, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textFieldName = new JTextField();
		textFieldName.setBounds(188, 40, 162, 20);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);

		comboBoxCategory = new JComboBox(getCategoriesToCombo());
		comboBoxCategory.setBounds(188, 80, 81, 20);
		contentPanel.add(comboBoxCategory);

		textFieldType = new JTextField();
		textFieldType.setColumns(10);
		textFieldType.setBounds(188, 120, 162, 20);
		contentPanel.add(textFieldType);

		textFieldStock = new JTextField();
		textFieldStock.setColumns(10);
		textFieldStock.setBounds(188, 160, 162, 20);
		contentPanel.add(textFieldStock);

		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(116, 43, 62, 14);
		contentPanel.add(lblName);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategory.setBounds(116, 83, 62, 14);
		contentPanel.add(lblCategory);

		JLabel lblType = new JLabel("Type:");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(116, 123, 62, 14);
		contentPanel.add(lblType);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStock.setBounds(116, 163, 62, 14);
		contentPanel.add(lblStock);

		JButton btnEditProduct = new JButton("Update Product");
		btnEditProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				//update product
				updateProduct();
			}
		});
		btnEditProduct.setBounds(188, 200, 162, 23);
		contentPanel.add(btnEditProduct);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(EditProductWindow.class
				.getResource("/com/dmtware/in/view/logo_2.png")));
		label.setBounds(23, 25, 72, 72);
		contentPanel.add(label);

		JButton btnNew = new JButton("New");
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("new category");
				CategoriesWindow categoriesWindow = new CategoriesWindow();
				categoriesWindow.setVisible(true);

			}
		});
		btnNew.setBounds(279, 80, 71, 20);
		contentPanel.add(btnNew);

		setLocationRelativeTo(null);
		
		getProductData();

	}

	// get all categories to comboBox
	public String[] getCategoriesToCombo() {

		try {
			List<Category> categories = null;
			ArrayList<String> comboCategories = new ArrayList<String>();
			comboCategories.add("");
			categories = conn.getAllCategories();

			for (int i = 0; i < categories.size(); i++) {
				comboCategories.add(categories.get(i).getName());
				System.out.println(comboCategories.get(i));
			}

			return comboCategories.toArray(new String[comboCategories.size()]);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	// inserts product details into all fields
	public void getProductData() {

		int nameCol = 0;
		int catCol = 1;
		int typeCol = 2;
		int stockCol = 3;

		int selectedRow = mainW.tableProduct.getSelectedRow();

		textFieldName.setText(mainW.tableProduct.getValueAt(selectedRow, nameCol).toString());
		comboBoxCategory.setSelectedItem(mainW.tableProduct.getValueAt(selectedRow, catCol));
		textFieldType.setText(mainW.tableProduct.getValueAt(selectedRow, typeCol).toString());
		textFieldStock.setText(mainW.tableProduct.getValueAt(selectedRow, stockCol).toString());
		
		currentProductName = textFieldName.getText().toString();
	}
	
	// updates product
	public void updateProduct(){
		String newProdName = textFieldName.getText().toString();
		String catName = comboBoxCategory.getSelectedItem().toString();
		String typeName = textFieldType.getText().toString();
		String quantityName = textFieldStock.getText().toString();
		
		System.out.println(currentProductName + " ! " + newProdName);
		
		try {
			conn.updateProductQuery(currentProductName, newProdName, catName, typeName, quantityName);
			mainW.refreshTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currentProductName = textFieldName.getText().toString();
	}
}