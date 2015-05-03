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
import com.dmtware.in.model.ProductJoin;
import com.dmtware.in.model.Unit;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditProductWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	// database class declaration
	SQLiteCon conn;

	List<ProductJoin> products;

	String currentProductName = "";
	String currentTypeName = "";

	// fields that need access
	private final JPanel contentPanel = new JPanel();
	JTextField textFieldName;
	JTextField textFieldType;
	JTextField textFieldStock;
	JComboBox<String> comboBoxCategory;
	JComboBox<String> comboBoxUnits;
	JTextField textFieldStockAlarm;

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EditProductWindow() {

		// initialise database connection
		conn = new SQLiteCon();

		setModal(true);
		setResizable(false);
		setTitle("In - Edit Product");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				EditProductWindow.class
						.getResource("/com/dmtware/in/view/logo_2.png")));
		setBounds(100, 100, 396, 356);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		// contentPanel.setBackground(new Color(163, 193, 228));

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

				// update product
				updateProduct();
			}
		});
		btnEditProduct.setBounds(188, 274, 162, 23);
		contentPanel.add(btnEditProduct);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(EditProductWindow.class
				.getResource("/com/dmtware/in/view/logo_2.png")));
		label.setBounds(23, 25, 72, 72);
		contentPanel.add(label);

		JButton btnNewCat = new JButton("New");
		btnNewCat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				openCategories();

			}
		});
		btnNewCat.setBounds(279, 80, 71, 20);
		contentPanel.add(btnNewCat);

		comboBoxUnits = new JComboBox(getUnitsToCombo());
		comboBoxUnits.setBounds(188, 198, 81, 20);
		contentPanel.add(comboBoxUnits);

		JButton btnNewUnit = new JButton("New");
		btnNewUnit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewUnit.setBounds(279, 198, 71, 20);
		contentPanel.add(btnNewUnit);

		JLabel label_1 = new JLabel("Category:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(116, 201, 62, 14);
		contentPanel.add(label_1);

		textFieldStockAlarm = new JTextField();
		textFieldStockAlarm.setColumns(10);
		textFieldStockAlarm.setBounds(188, 237, 162, 20);
		contentPanel.add(textFieldStockAlarm);

		JLabel lblStockAlarm = new JLabel("Stock Alarm:");
		lblStockAlarm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStockAlarm.setBounds(79, 240, 99, 14);
		contentPanel.add(lblStockAlarm);

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

	// gets all units to combo
	private String[] getUnitsToCombo() {

		try {
			List<Unit> units = null;
			ArrayList<String> comboUnits = new ArrayList<String>();
			comboUnits.add("");
			units = conn.getAllUnits();

			for (int i = 0; i < units.size(); i++) {
				comboUnits.add(units.get(i).getName());
				System.out.println(comboUnits.get(i));
			}

			return comboUnits.toArray(new String[comboUnits.size()]);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	// updates product
	private void updateProduct() {

		if (fieldsCheck()) {

			int reply = JOptionPane.showConfirmDialog(null,
					"Do you really want to update this product?", "Update?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				String newProdName = textFieldName.getText().toString().trim();
				String catName = comboBoxCategory.getSelectedItem().toString()
						.trim();
				String typeName = textFieldType.getText().toString().trim();
				String quantityName = textFieldStock.getText().toString()
						.trim();
				String unitName = comboBoxUnits.getSelectedItem().toString()
						.trim();

				String stockAlarm = textFieldStockAlarm.getText().toString()
						.trim();

				if (stockAlarm.equalsIgnoreCase("")) {
					stockAlarm = "0";
				}

				System.out.println("current: " + currentProductName
						+ " ! new: " + newProdName);

				try {
					products = conn.getProductsJoin();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// check if exists
				boolean productExists = false;
				@SuppressWarnings("unused")
				boolean noChange = false;

				if (newProdName.equalsIgnoreCase(currentProductName)
						&& typeName.equalsIgnoreCase(currentTypeName)) {
					//do nothing
				} else {

					for (int i = 0; i < products.size(); i++) {
						if (products.get(i).getName()
								.equalsIgnoreCase(newProdName)
								&& products.get(i).getType()
										.equalsIgnoreCase(typeName)) {
							productExists = true;
							break;

						}
					}
				}
				// if product with the same type exists
				if (!productExists) {
					try {
						conn.updateProductQuery(currentProductName,
								newProdName, catName, typeName, quantityName,
								unitName, stockAlarm);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					currentProductName = textFieldName.getText().toString()
							.trim();

					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Product with the same name and type exists");
				}
			} else {
				// do nothing
			}

		} else {

			JOptionPane
					.showMessageDialog(
							null,
							"Please fill up all the fields and make sure that \"Stock\" & \"Stock Alarm\" are numeric");
		}
	}

	// checks if required fields are filled up
	private boolean fieldsCheck() {

		boolean name, category, type, stock, unit, stockAlarm;

		name = textFieldName.getText().trim().equalsIgnoreCase("") ? true
				: false;
		category = comboBoxCategory.getSelectedIndex() == 0 ? true : false;
		type = textFieldType.getText().trim().equalsIgnoreCase("") ? true
				: false;
		stock = textFieldStock.getText().trim().equalsIgnoreCase("")
				|| !isNumeric(textFieldStock.getText().trim()) ? true : false;
		unit = comboBoxUnits.getSelectedIndex() == 0 ? true : false;

		stockAlarm = !isNumeric(textFieldStockAlarm.getText()) ? true : false;

		if (name || type || category || stock || unit || stockAlarm) {
			return false;
		} else
			return true;
	}

	// checks if String is numeric
	private static boolean isNumeric(String str) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		if (str.length() == pos.getIndex()) {

			if (Integer.parseInt(str) < 0) {
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}

	}

	// opens categories window
	private void openCategories() {
		System.out.println("new category");
		CategoriesWindow categoriesWindow = new CategoriesWindow();
		categoriesWindow.setVisible(true);

		// refreshes combobox after change
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
}