/*
 * SQLite class
 */

package com.dmtware.in.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dmtware.in.model.Category;
import com.dmtware.in.model.Product;
import com.dmtware.in.model.ProductJoin;
import com.dmtware.in.model.User;
import com.dmtware.in.view.MainWindow;

public class SQLiteCon {

	public static Connection myConn;
	public static boolean isConnected;

	// constructor that connects to database
	public SQLiteCon() {

		try {
			// gets name of the database from txt file
			Properties props = new Properties();
			props.load(new FileInputStream("settings.txt"));

			String db = props.getProperty("db");

			Class.forName("org.sqlite.JDBC");
			myConn = DriverManager.getConnection("jdbc:sqlite:" + db);
			System.out.println("Connected");

			// pragma on, and deal with no resultset
			PreparedStatement pst = myConn
					.prepareStatement("PRAGMA foreign_keys = ON;");
			boolean result = pst.execute();

			while (true) {
				if (result) {
					//ResultSet rs = pst.getResultSet();
					// Do something with resultset ...
				} else {
					int updateCount = pst.getUpdateCount();
					if (updateCount == -1) {
						// no more results
						break;
					}
					// Do something with update count ...
				}
				result = pst.getMoreResults();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);

		}
	}

	/*
	 * User Table methods
	 */

	// login method that verifies user
	@SuppressWarnings("deprecation")
	public void login(Connection conn, JTextField user, JPasswordField pswd) {
		try {
			// declare query that checks if user exists
			String query = "SELECT  * FROM User where UserName=? AND Password=?";
			// put into prepared statement
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, user.getText());
			pst.setString(2, pswd.getText());

			// get result
			ResultSet rs = pst.executeQuery();

			int count = 0;

			// display result
			while (rs.next()) {
				count++;
			}

			// if match
			if (count == 1) {
				// JOptionPane.showMessageDialog(null, "User and Pass OK");
				// close login window
				isConnected = true;
				// frmInLogin.dispose();
				// open main window
				MainWindow mainWindow = new MainWindow();
				mainWindow.setVisible(true);
			}
			// if don't match
			else {
				JOptionPane.showMessageDialog(null, "Wrong User or Pass");
			}

			rs.close();
			pst.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {

			try {

			} catch (Exception e2) {

			}
		}
	}

	// gets all users to the list
	public List<User> getAllUsers() throws Exception {

		List<User> list = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM User");

			while (myRs.next()) {
				User tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// search users
	public List<User> searchUsers(String userName) throws Exception {
		List<User> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			userName += "%";
			myStmt = myConn
					.prepareStatement("select * from User where UserName like ?");

			myStmt.setString(1, userName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				User tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to user
	private User convertRowToUser(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("Id");
		String userName = myRs.getString("UserName");
		String password = myRs.getString("Password");
		String firstName = myRs.getString("FirstName");
		String surname = myRs.getString("Surname");

		User tempUser = new User(id, userName, password, firstName, surname);

		return tempUser;
	}

	// insert user
	public void insertUserQuery(String userName, String password,
			String firstName, String surname) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn
					.prepareStatement("INSERT INTO User (UserName, Password, FirstName, Surname)"
							+ "VALUES (?, ?, ?, ?)");

			myStmt.setString(1, userName);
			myStmt.setString(2, password);
			myStmt.setString(3, firstName);
			myStmt.setString(4, surname);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// remove category query
	public void removeUserQuery(String userId, String userName)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("DELETE FROM User WHERE Id = ? AND UserName = ?");

			myStmt.setString(1, userId);
			myStmt.setString(2, userName);
			myStmt.execute();

		} catch (Exception e) {

		} finally {
			close(myStmt, null);

		}
	}

	/*
	 * Category Table Methods
	 */

	// gets the list of categories
	public List<Category> getAllCategories() throws Exception {

		List<Category> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt
					.executeQuery("SELECT * FROM Category ORDER BY Category.Name COLLATE NOCASE");

			while (myRs.next()) {
				Category tempCategory = convertRowToCategory(myRs);
				list.add(tempCategory);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to category
	private Category convertRowToCategory(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("Id");
		String name = myRs.getString("Name");

		Category tempCategory = new Category(id, name);

		return tempCategory;
	}

	// gets ID of catName
	public int getCategoryId(String category) throws SQLException {

		List<Category> list = new ArrayList<>();
		Category tempCategory = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Category");

			while (myRs.next()) {
				tempCategory = convertRowToCategory(myRs);
				list.add(tempCategory);

				if (tempCategory.getName().equalsIgnoreCase(category)) {
					break;
				}
			}

			return tempCategory.getId();

		} finally {
			close(myStmt, myRs);
		}
	}

	// insert category
	public void insertCategoryQuery(String catName) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("INSERT INTO Category (Name)"
					+ "VALUES (?)");

			myStmt.setString(1, catName);
			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// remove category query
	public void removeCategoryQuery(String catId, String catName)
			throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("DELETE FROM Category WHERE Id = ? AND Name = ?");

			myStmt.setString(1, catId);
			myStmt.setString(2, catName);
			myStmt.execute();
			// JOptionPane.showMessageDialog(null, "Category removed");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"This category has products assigned so can't be removed");
		} finally {
			close(myStmt, null);

		}
	}

	// update category
	public void updateCategoryQuery(String currentCategory, String newCategory,
			String id) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Category SET Name = ? WHERE Id = ?");

			myStmt.setString(1, newCategory);
			myStmt.setString(2, id);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	/*
	 * Product Table methods
	 */

	// get all products - join table (category id- name)
	public List<ProductJoin> getProductsJoin() throws Exception {

		List<ProductJoin> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt
					.executeQuery("SELECT Product.Name, Category.Name as CatName, Product.Type, Product.Stock FROM Product INNER JOIN Category ON Product.Category=Category.Id ORDER BY Product.Name COLLATE NOCASE");

			while (myRs.next()) {
				ProductJoin tempProductJoin = convertRowToProductJoin(myRs);
				list.add(tempProductJoin);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to join product
	private ProductJoin convertRowToProductJoin(ResultSet myRs)
			throws SQLException {

		String name = myRs.getString("Name");
		String category = myRs.getString("CatName");
		String type = myRs.getString("Type");
		int stock = myRs.getInt("Stock");

		ProductJoin tempProductJoin = new ProductJoin(name, category, type,
				stock);

		return tempProductJoin;
	}

	// filters products by category, used in combobox
	public List<ProductJoin> filterProductsByCat(String catName)
			throws Exception {
		List<ProductJoin> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// catName = "";
			myStmt = myConn
					.prepareStatement("SELECT Product.Name, Category.Name "
							+ "as CatName, Product.Type, Product.Stock "
							+ "FROM Product INNER JOIN Category ON "
							+ "Product.Category=Category.Id WHERE Category.Name = ? ORDER BY Product.Name COLLATE NOCASE");

			myStmt.setString(1, catName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				ProductJoin tempProductJoin = convertRowToProductJoin(myRs);
				list.add(tempProductJoin);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	// search method used for search button
	public List<ProductJoin> searchProductsJoinCat(String prodName, String cat)
			throws Exception {
		List<ProductJoin> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			if (cat.equalsIgnoreCase("All")) {
				System.out.println("If ALL");
				prodName += "%";
				myStmt = myConn
						.prepareStatement("SELECT Product.Name, Category.Name "
								+ "as CatName, Product.Type, Product.Stock "
								+ "FROM Product INNER JOIN Category ON "
								+ "Product.Category=Category.Id WHERE Product.Name LIKE ? ORDER BY Product.Name COLLATE NOCASE");

				myStmt.setString(1, prodName);

			} else {
				prodName += "%";
				myStmt = myConn
						.prepareStatement("SELECT Product.Name, Category.Name "
								+ "as CatName, Product.Type, Product.Stock "
								+ "FROM Product INNER JOIN Category ON "
								+ "Product.Category=Category.Id WHERE Category.Name = ? AND Product.Name LIKE ? ORDER BY Product.Name COLLATE NOCASE");

				myStmt.setString(1, cat);
				myStmt.setString(2, prodName);

			}

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				ProductJoin tempProductJoin = convertRowToProductJoin(myRs);
				list.add(tempProductJoin);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	// insert product
	public void insertProductQuery(String prodName, String catName,
			String typeName, String quantityName) throws Exception {

		PreparedStatement myStmt = null;

		// get ID of catName
		int catId = getCategoryId(catName);

		try {

			myStmt = myConn
					.prepareStatement("INSERT INTO Product (Name, Category, Type, Stock)"
							+ "VALUES (?, ?, ?, ?)");

			myStmt.setString(1, prodName);
			myStmt.setString(2, "" + catId);
			myStmt.setString(3, typeName);
			myStmt.setString(4, quantityName);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}

	}

	// remove product
	public void removeProductQuery(String prodName, String typeName,
			String stockName) throws Exception {

		PreparedStatement myStmt = null;

		try {

			myStmt = myConn
					.prepareStatement("DELETE FROM Product WHERE Name = ? AND Type = ? AND Stock = ?");

			myStmt.setString(1, prodName);
			myStmt.setString(2, typeName);
			myStmt.setString(3, stockName);

			myStmt.execute();
		} finally {
			close(myStmt, null);
		}
	}

	// remove product
	public void removeAllQuery(String tableName) throws Exception {

		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("DELETE FROM " + tableName);
			myStmt.execute();
		} finally {
			close(myStmt, null);
		}
	}

	// update product
	public void updateProductQuery(String currentProductName, String prodName,
			String catName, String typeName, String quantityName)
			throws Exception {

		PreparedStatement myStmt = null;

		// get ID of prod and catName
		int catId = getCategoryId(catName);
		int prodId = getProductId(currentProductName);

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Product SET Name = ?, Category = ?, Type = ?, Stock = ?"
							+ "WHERE Id = ?");

			myStmt.setString(1, prodName);
			myStmt.setString(2, "" + catId);
			myStmt.setString(3, typeName);
			myStmt.setString(4, quantityName);
			myStmt.setString(5, "" + prodId);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}
	}

	// gets ID of catName
	public int getProductId(String product) throws SQLException {

		List<Product> list = new ArrayList<>();
		Product tempProduct = null;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Product");

			while (myRs.next()) {
				tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);

				if (tempProduct.getName().equalsIgnoreCase(product)) {
					break;
				}
			}

			return tempProduct.getId();

		} finally {
			close(myStmt, myRs);
		}
	}

	// add stock
	public void addStockQuery(String prodName, String typeName, int quantity)
			throws Exception {

		PreparedStatement myStmt = null;

		String qString = "" + quantity;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Product SET Stock = (Product.Stock + ?) "
							+ "WHERE Name = ? AND Type = ?");

			myStmt.setString(1, qString);
			myStmt.setString(2, prodName);
			myStmt.setString(3, typeName);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}

	}

	// remove stock
	public void removeStockQuery(String prodName, String typeName, int quantity)
			throws Exception {

		PreparedStatement myStmt = null;

		String qString = "" + quantity;

		try {

			myStmt = myConn
					.prepareStatement("UPDATE Product SET Stock = (Product.Stock - ?) "
							+ "WHERE Name = ? AND Type = ?");

			myStmt.setString(1, qString);
			myStmt.setString(2, prodName);
			myStmt.setString(3, typeName);

			myStmt.executeUpdate();
		} finally {
			close(myStmt, null);
		}

	}

	/*
	 * Other methods
	 */

	// close connection
	private static void close(Connection myConn, Statement myStmt,
			ResultSet myRs) throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {

		}

		if (myConn != null) {
			myConn.close();
		}
	}

	// close when st and rs
	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);
	}

	// //////////////////////
	// /DEPRECATED METHODS///
	// //////////////////////

	// get all products
	public List<Product> getAllProducts() throws Exception {

		List<Product> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Product");

			while (myRs.next()) {
				Product tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);
			}

			return list;

		} finally {
			close(myStmt, myRs);
		}
	}

	// convert row to product
	private Product convertRowToProduct(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("Id");
		String name = myRs.getString("Name");
		int category = myRs.getInt("Category");
		String type = myRs.getString("Type");
		int stock = myRs.getInt("Stock");

		Product tempProduct = new Product(id, name, category, type, stock);

		return tempProduct;
	}

	// search products
	public List<Product> searcshProducts(String prodName) throws Exception {
		List<Product> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			prodName += "%";
			myStmt = myConn
					.prepareStatement("select * from Product where Name like ?");

			myStmt.setString(1, prodName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				Product tempProduct = convertRowToProduct(myRs);
				list.add(tempProduct);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}
}