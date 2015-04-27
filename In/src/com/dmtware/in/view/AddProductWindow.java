/*
 * Add Product Window class
 */

package com.dmtware.in.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.Category;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddProductWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	// default constructor
	public AddProductWindow() {

	}

	// database class declaration
	SQLiteCon conn;

	// instance of MainWindow declaration (gives option of refreshing the table
	// in main window)
	MainWindow mainW;

	// fields that need access
	private final JPanel contentPanel = new JPanel();
	public JTextField textFieldName;
	public JTextField textFieldType;
	public JTextField textFieldStock;
	JComboBox comboBoxCategory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddProductWindow dialog = new AddProductWindow();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddProductWindow(MainWindow mw) {

		// initialise access to the main window (and table refresh method)
		mainW = mw;

		// initialise database connection
		conn = new SQLiteCon();

		setModal(true);
		setResizable(false);
		setTitle("In - Add Product");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AddProductWindow.class
						.getResource("/com/dmtware/in/view/logo_2.png")));
		setBounds(100, 100, 396, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		//contentPanel.setBackground(new Color(163, 193, 228));

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

		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addProduct();
			}
		});
		btnAddProduct.setBounds(188, 200, 162, 23);
		contentPanel.add(btnAddProduct);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AddProductWindow.class
				.getResource("/com/dmtware/in/view/logo_2.png")));
		label.setBounds(23, 25, 72, 72);
		contentPanel.add(label);

		JButton btnNew = new JButton("New");
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				openCategories();

			}
		});
		btnNew.setBounds(279, 80, 71, 20);
		contentPanel.add(btnNew);
		getContentPane().setBackground(new Color(163, 193, 228));
		setLocationRelativeTo(null);

	}

	// get all categories to comboBox
	private String[] getCategoriesToCombo() {

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

	// adds new product
	private void addProduct() {

		if (fieldsCheck()) {
			String prodName = textFieldName.getText().toString().trim();
			String catName = comboBoxCategory.getSelectedItem().toString().trim();
			String typeName = textFieldType.getText().toString().trim();
			String quantityName = textFieldStock.getText().toString().trim();

			System.out.println(prodName + " " + catName + " " + typeName + " "
					+ quantityName);

			try {
				conn.insertProductQuery(prodName, catName, typeName,
						quantityName);
				// refresh table
				mainW.refreshTable();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			clearFields();
		}
		else{
			JOptionPane.showMessageDialog(null, "Please fill up all the fields and make sure that \"Stock\" is numeric");
		}
			
	}
	
	// checks if required fields are filled up
	private boolean fieldsCheck() {

		boolean name, category, type, stock;

		name = textFieldName.getText().trim().equalsIgnoreCase("") ? true : false;
		category = comboBoxCategory.getSelectedIndex() == 0 ? true : false;
		type = textFieldType.getText().trim().equalsIgnoreCase("") ? true : false;
		stock = textFieldStock.getText().trim().equalsIgnoreCase("") || !isNumeric(textFieldStock.getText()) ? true
				: false;

		if (name || type || category || stock) {
			return false;
		} else
			return true;
	}
	
	// checks if String is numeric
	private static boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	
	// clears fields
	private void clearFields(){
		textFieldName.setText("");
		comboBoxCategory.setSelectedIndex(0);
		textFieldType.setText("");
		textFieldStock.setText("");

	}
	
	private void openCategories(){
		System.out.println("new category");
		CategoriesWindow categoriesWindow = new CategoriesWindow();
		categoriesWindow.setVisible(true);
		
		// refreshes combobox after change
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				@SuppressWarnings("unchecked")
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						getCategoriesToCombo());
				comboBoxCategory.setModel(model);
			}
		});

	}
}
