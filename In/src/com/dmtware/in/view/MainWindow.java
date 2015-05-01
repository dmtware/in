/*
 * In - MainWindow Class 
 */

package com.dmtware.in.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Toolkit;

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.Category;
import com.dmtware.in.model.Product;
import com.dmtware.in.model.ProductJoin;
import com.dmtware.in.model.ProductJoinTableModel;
import com.dmtware.in.model.ProductTableModel;

import java.awt.event.KeyAdapter;
import java.awt.Font;

import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JSeparator;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// first category in combo box
	String firstCatStr = "All";
	
	// selected product
	String currentProductName;
	
	// current product search string
	String currentProductSearch;

	// current product search result
	List<ProductJoin> currentListProductJoin;

	// AddProduct Window declaration
	AddProductWindow addProductWindow;

	// EditProduct Window declaration
	EditProductWindow editProductWindow;

	// Categories Window declaration
	CategoriesWindow categoriesWindow;

	// database class declaration
	SQLiteCon conn;

	// fields, buttons, tables that need access
	JTable tableProduct;
	private JComboBox<String> comboBoxCategory;
	private JTextField textFieldSearch;
	private JButton buttonPlus;
	private JButton buttonMinus;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public MainWindow() {

		// initialise connection
		conn = new SQLiteCon();

		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						MainWindow.class
								.getResource("/com/dmtware/in/view/logo_2.png")));

		createMenuBar();
		setResizable(false);

		setTitle("Inventory - Main | User: " + conn.currentUser);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 540);
		contentPane = new JPanel();
		// contentPane.setBackground(new Color(163, 193, 228));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(163, 193, 228));
		scrollPane.setBounds(10, 42, 654, 383);
		contentPane.add(scrollPane);

		tableProduct = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void changeSelection(int rowIndex, int columnIndex,
					boolean toggle, boolean extend) {
				// Always toggle on single selection
				super.changeSelection(rowIndex, columnIndex, !extend, extend);
			}
		};
		tableProduct.setFillsViewportHeight(true);
		tableProduct.setBackground(SystemColor.window);
		tableProduct.setSelectionBackground(new Color(163, 193, 228));
		tableProduct.setRequestFocusEnabled(false);

		tableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(tableProduct);

		comboBoxCategory = new JComboBox(getCategoriesToCombo());

		comboBoxCategory.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent evt) {

				categoryFilter(evt);
			}
		});
		comboBoxCategory.setMaximumRowCount(20);
		comboBoxCategory.setBounds(10, 11, 125, 24);
		contentPane.add(comboBoxCategory);

		JButton btnCategories = new JButton("Categories");
		btnCategories.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCategories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openCategories();
			}
		});
		btnCategories.setFocusPainted(false);
		btnCategories.setBounds(139, 11, 82, 24);
		contentPane.add(btnCategories);

		JPanel panel = new JPanel();
		// panel.setBackground(new Color(163, 193, 228));
		panel.setBounds(10, 450, 244, 30);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnAddProduct = new JButton("Add");
		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				addProduct();
			}
		});
		btnAddProduct.setBounds(0, 0, 80, 30);
		panel.add(btnAddProduct);
		btnAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 10));

		btnAddProduct.setFocusPainted(false);

		JButton btnRemoveProduct = new JButton("Remove");
		btnRemoveProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				removeProduct();
			}
		});
		btnRemoveProduct.setBounds(82, 0, 80, 30);
		panel.add(btnRemoveProduct);
		btnRemoveProduct.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemoveProduct.setFocusPainted(false);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// edit product
				editProduct();

			}
		});
		btnEdit.setBounds(164, 0, 80, 30);
		panel.add(btnEdit);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEdit.setFocusPainted(false);

		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					searchBtn();
				}
			}
		});

		JPanel panel_1 = new JPanel();
		// panel_1.setBackground(new Color(163, 193, 228));
		panel_1.setBounds(411, 450, 252, 30);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		buttonPlus = new JButton("+");
		buttonPlus.setBounds(127, 0, 125, 30);
		panel_1.add(buttonPlus);
		buttonPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addStock();
			}
		});
		buttonPlus.setFocusPainted(false);

		buttonMinus = new JButton("-");
		buttonMinus.setBounds(0, 0, 125, 30);
		panel_1.add(buttonMinus);
		buttonMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeStock();
			}
		});
		buttonMinus.setFocusPainted(false);
		textFieldSearch.setToolTipText("Product Name");
		textFieldSearch.setBounds(380, 11, 118, 25);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);

		// request focus
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textFieldSearch.requestFocusInWindow();
			}
		});

		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				searchBtn();
			}
		});
		btnSearch.setFocusPainted(false);
		btnSearch.setBounds(501, 11, 80, 24);
		contentPane.add(btnSearch);

		JLabel lblNewLabel = new JLabel("Product Name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(278, 15, 97, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				showwAll();
			}
		});
		btnShowAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnShowAll.setFocusPainted(false);
		btnShowAll.setBounds(582, 11, 80, 24);
		contentPane.add(btnShowAll);
		
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setBounds(96, 430, 75, 14);
		contentPane.add(lblProducts);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setHorizontalAlignment(SwingConstants.CENTER);
		lblStock.setBounds(501, 430, 75, 14);
		contentPane.add(lblStock);
		setLocationRelativeTo(null);

		getProductsJoin();
		//refreshTable();
	}

	// menu bar
	private void createMenuBar() {

		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				printTable();
			}
		});
		file.add(mntmPrint);

		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openSettings();
			}
		});

		file.add(mntmSettings);

		JSeparator separator_1 = new JSeparator();
		file.add(separator_1);

		file.add(eMenuItem);
		menubar.add(file);

		setJMenuBar(menubar);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic(KeyEvent.VK_E);
		menubar.add(mnEdit);

		JMenuItem mntmCategories = new JMenuItem("Edit Categories");
		mntmCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openCategories();
			}
		});
		mnEdit.add(mntmCategories);

		JMenuItem mntmAddProduct = new JMenuItem("Add Product");
		mntmAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});

		JSeparator separator = new JSeparator();
		mnEdit.add(separator);
		mnEdit.add(mntmAddProduct);

		JMenuItem mntmRemoveProduct = new JMenuItem("Remove Product");
		mntmRemoveProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeProduct();
			}
		});
		mnEdit.add(mntmRemoveProduct);

		JMenuItem mntmEditProduct = new JMenuItem("Edit Product");
		mntmEditProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editProduct();
			}
		});
		mnEdit.add(mntmEditProduct);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic(KeyEvent.VK_H);
		menubar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setEnabled(false);
		mnHelp.add(mntmAbout);
	}

	// opens Categories Window
	private void openCategories() {
		categoriesWindow = new CategoriesWindow();
		categoriesWindow.setVisible(true);
		while (categoriesWindow.isShowing()) {
			//
		}
		refreshComboBox();
		getProductsJoin();
	}
	
	// opens Settings window
	private void openSettings(){
		SettingsWindow settingsWindow = new SettingsWindow();
		settingsWindow.setVisible(true);
		while(settingsWindow.isVisible()){
			
		}
		refreshComboBox();
		refreshTable();
	}

	/*
	 * Get data to the table and combobox
	 */

	// get all products to the table (join table query)
	private void getProductsJoin() {

		try {
			List<ProductJoin> productsJoin = null;
			productsJoin = conn.getProductsJoin();
			ProductJoinTableModel model = new ProductJoinTableModel(
					productsJoin);
			tableProduct.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// get all categories to comboBox
	private String[] getCategoriesToCombo() {

		try {
			List<Category> categories = null;
			ArrayList<String> comboCategories = new ArrayList<String>();
			comboCategories.add("All");
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
	
	// refreshes combobox after change
	public void refreshComboBox(){
	
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				@SuppressWarnings({ "rawtypes" })
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						getCategoriesToCombo());
				comboBoxCategory.setModel(model);
			}
		});

	}

	/*
	 * Product search and category filter
	 */

	// search button method
	private void searchBtn() {

		try {
			String product = textFieldSearch.getText();
			currentProductSearch = product;
			List<ProductJoin> productsJoin = null;

			if (product != null && product.trim().length() > 0) {
				productsJoin = conn.searchProductsJoinCat(product, firstCatStr);
			} else {
				productsJoin = conn.searchProductsJoinCat("", firstCatStr);
			}

			for (ProductJoin temp : productsJoin) {
				System.out.println(temp);
			}

			ProductJoinTableModel model = new ProductJoinTableModel(
					productsJoin);

			tableProduct.setModel(model);
			currentListProductJoin = productsJoin;

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	// show all method
	private void showwAll(){
		textFieldSearch.setText("");
		searchBtn();
	}

	// filters category depending on item in combobox
	private void categoryFilter(ItemEvent evt) {
		Object item = evt.getItem();

		if (evt.getStateChange() == ItemEvent.SELECTED) {
			// Item was just selected
			try {

				firstCatStr = item.toString();
				System.out.println(firstCatStr);

				List<ProductJoin> productsJoin = null;

				if (firstCatStr.equalsIgnoreCase("All")) {
					productsJoin = conn.getProductsJoin();
				} else {
					productsJoin = conn.filterProductsByCat(firstCatStr);
				}

				for (ProductJoin temp : productsJoin) {
					System.out.println(temp);
				}

				ProductJoinTableModel model = new ProductJoinTableModel(
						productsJoin);
				tableProduct.setModel(model);

				currentListProductJoin = productsJoin;
				textFieldSearch.setText("");

			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
			// Item is no longer selected
		}
	}

	/*
	 * Add and remove stock
	 */

	// add stock method
	@SuppressWarnings("static-access")
	private void addStock() {
		int prodCol = 0;
		int typeCol = 2;

		// if row selected
		if (!(tableProduct.getSelectedRow() == -1)) {
			int selectedRow = tableProduct.getSelectedRow();

			String prodName = tableProduct.getValueAt(selectedRow, prodCol)
					.toString().trim();
			String typeName = tableProduct.getValueAt(selectedRow, typeCol)
					.toString().trim();

			boolean numeric = false;
			int quantity = 0;

			JOptionPane inpOption = new JOptionPane();
			String strDialogResponse = "";

				do {
					// Shows a inputdialog
					strDialogResponse = inpOption.showInputDialog("How much do you want to add: ");
					 //if OK is pushed then (if not strDialogResponse is null)
				    if (strDialogResponse != null){

						try {
						quantity = Integer.parseInt(strDialogResponse.trim());
						numeric = true;
						} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null,
								"Please enter numeric value");
						numeric = false;
						}
				    }// If cancel button is pressed
					else {
						break;
					}
				} while (!numeric);

			System.out.println(prodName + " " + typeName + " " + quantity);

			try {
				conn.addStockQuery(prodName, typeName, quantity);

			} catch (Exception e) {
				e.printStackTrace();
			}

			// refresh view here
			refreshTable();
		} else {
			System.out.println("Nothing selected");
			JOptionPane.showMessageDialog(null,
					"In order to change the stock please select product first");
		}
	}

	// remove stock method
	@SuppressWarnings("static-access")
	private void removeStock() {
		int prodCol = 0;
		int typeCol = 2;

		// if row selected
		if (!(tableProduct.getSelectedRow() == -1)) {

			int selectedRow = tableProduct.getSelectedRow();

			String prodName = tableProduct.getValueAt(selectedRow, prodCol)
					.toString().trim();
			String typeName = tableProduct.getValueAt(selectedRow, typeCol)
					.toString().trim();

			boolean numeric = false;
			int quantity = 0;
			JOptionPane inpOption = new JOptionPane();
			String strDialogResponse = "";

				do {
					// Shows a inputdialog
					strDialogResponse = inpOption.showInputDialog("How much do you want to remove: ");
					 //if OK is pushed then (if not strDialogResponse is null)
				    if (strDialogResponse != null){

						try {
						quantity = Integer.parseInt(strDialogResponse);
						numeric = true;
						} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null,
								"Please enter numeric value");
						numeric = false;
						}
				    }// If cancel button is pressed
					else {
						break;
					}
				} while (!numeric);

			System.out.println(prodName + " " + typeName + " " + quantity);

			try {
				conn.removeStockQuery(prodName, typeName, quantity);

			} catch (Exception e) {
				e.printStackTrace();
			}
			// refresh view
			refreshTable();
		} else {
			System.out.println("Nothing selected");
			JOptionPane.showMessageDialog(null,
					"In order to change the stock please select product first");
		}
	}

	/*
	 * Add and Remove product
	 */

	// add product
	private void addProduct() {
		
		// initialise AddProductWindow
		addProductWindow = new AddProductWindow();

		addProductWindow.setVisible(true);
		addProductWindow.textFieldName.setText("");
		addProductWindow.textFieldType.setText("");
		addProductWindow.textFieldStock.setText("");
		while(addProductWindow.isVisible()){
	
		}
		refreshComboBox();
		refreshTable();
	}

	// remove product
	private void removeProduct() {
		int prodCol = 0;
		int typeCol = 2;
		int stockCol = 3;

		// if row selected
		if (!(tableProduct.getSelectedRow() == -1)) {

			int selectedRow = tableProduct.getSelectedRow();

			String prodName = tableProduct.getValueAt(selectedRow, prodCol)
					.toString().trim();

			String typeName = tableProduct.getValueAt(selectedRow, typeCol)
					.toString().trim();

			String stockName = tableProduct.getValueAt(selectedRow, stockCol)
					.toString().trim();

			System.out.println(prodName + " " + typeName + " " + stockCol);

			int reply = JOptionPane.showConfirmDialog(null,
					"Do you really want to remove this product?", "Remove?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeProductQuery(prodName, typeName, stockName);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				refreshTable();

				JOptionPane.showMessageDialog(null, "Product removed");

			} else {
				// do nothing
			}

		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"In order to remove product please select product row first");
		}
		
	}

	// edit product
	private void editProduct() {
		if (!(tableProduct.getSelectedRow() == -1)) {
			editProductWindow = new EditProductWindow();
			
			int nameCol = 0;
			int catCol = 1;
			int typeCol = 2;
			int stockCol = 3;
			int unitCol = 4;

			int selectedRow = tableProduct.getSelectedRow();

			editProductWindow.textFieldName.setText(tableProduct.getValueAt(selectedRow,
					nameCol).toString().trim());
			editProductWindow.comboBoxCategory.setSelectedItem(tableProduct.getValueAt(
					selectedRow, catCol));
			editProductWindow.textFieldType.setText(tableProduct.getValueAt(selectedRow,
					typeCol).toString().trim());
			editProductWindow.textFieldStock.setText(tableProduct.getValueAt(selectedRow,
					stockCol).toString().trim());
			editProductWindow.comboBoxUnits.setSelectedItem(tableProduct.getValueAt(
					selectedRow, unitCol));

			currentProductName = editProductWindow.textFieldName.getText().toString().trim();
			
			editProductWindow.currentProductName = currentProductName;
		
			System.out.println("current: " + currentProductName);
			
			editProductWindow.setVisible(true);
			while(editProductWindow.isVisible()){
				
			}
			refreshComboBox();
			refreshTable();
		} else {
			JOptionPane.showMessageDialog(null,
					"In order to edit product please select product row first");
		}
	}

	/*
	 * Other methods
	 */

	// method that refreshes table after changing stock
	public void refreshTable() {

		try {
			if (firstCatStr.equalsIgnoreCase("All")) {
				currentListProductJoin = conn.getProductsJoin();
			} else {
				currentListProductJoin = conn.filterProductsByCat(firstCatStr);
			}

			if (currentProductSearch != null
					&& currentProductSearch.trim().length() > 0) {
				currentListProductJoin = conn.searchProductsJoinCat(
						currentProductSearch, firstCatStr);
			} else {

				currentListProductJoin = conn.searchProductsJoinCat("",
						firstCatStr);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ProductJoinTableModel model = new ProductJoinTableModel(
				currentListProductJoin);

		tableProduct.setModel(model);
	}
	
	private void printTable(){
		
		MessageFormat header = new MessageFormat("Inventory - Report");
		MessageFormat footer = new MessageFormat("Page {0, number, integer}");
		
		try {
			tableProduct.print(JTable.PrintMode.FIT_WIDTH, header, footer);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// //////////////////////
	// /DEPRECATED METHODS///
	// //////////////////////

	// get all products to table
	public void getProducts() {

		try {

			List<Product> products = null;

			products = conn.getAllProducts();

			ProductTableModel model = new ProductTableModel(products);
			tableProduct.setModel(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
