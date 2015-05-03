/*
 * Categories Window class
 */
package com.dmtware.in.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JDialog;

import java.awt.Toolkit;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.dmtware.in.dao.SQLiteCon;
import com.dmtware.in.model.Unit;
import com.dmtware.in.model.UnitTableModel;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UnitsWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database connection declaration
	SQLiteCon conn;

	List<Unit> units;

	// table
	private JTable tableUnits;

	String newUnit = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UnitsWindow dialog = new UnitsWindow();
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
	public UnitsWindow() {
		getContentPane().setFocusTraversalKeysEnabled(false);

		// connect to database
		conn = new SQLiteCon();

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				UnitsWindow.class
						.getResource("/com/dmtware/in/view/logo_2.png")));
		setTitle("In - Units");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 254, 242);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 11, 120, 192);
		getContentPane().add(scrollPane);

		tableUnits = new JTable() {
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
		tableUnits.setFocusable(false);

		scrollPane.setViewportView(tableUnits);

		tableUnits.setFillsViewportHeight(true);
		tableUnits.setBackground(SystemColor.window);
		tableUnits.setSelectionBackground(new Color(163, 193, 228));
		tableUnits.setRequestFocusEnabled(false);
		tableUnits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UnitsWindow.class
				.getResource("/com/dmtware/in/view/logo_2.png")));
		label.setBounds(22, 11, 72, 72);
		getContentPane().add(label);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFocusPainted(false);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// Add category
				addUnit();
			}
		});
		btnAdd.setBounds(14, 119, 88, 23);
		getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove.setFocusPainted(false);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				removeUnit();
			}
		});
		btnRemove.setBounds(14, 149, 88, 23);
		getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				updateUnit();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEdit.setFocusPainted(false);
		btnEdit.setBounds(14, 179, 88, 23);
		getContentPane().add(btnEdit);

		setLocationRelativeTo(null);

		getUnitsToTable();

	}

	// get all products to the table (join table query)
	private void getUnitsToTable() {

		try {

			units = conn.getAllUnits();

			UnitTableModel model = new UnitTableModel(units);

			tableUnits.setModel(model);

			// remove/hide Id table
			TableColumn myTableColumn0 = tableUnits.getColumnModel()
					.getColumn(0);
			// tableCategories.getColumnModel().removeColumn(myTableColumn0);
			myTableColumn0.setMaxWidth(0);
			myTableColumn0.setMinWidth(0);
			myTableColumn0.setPreferredWidth(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// add category
	private void addUnit() {

		newUnit = JOptionPane.showInputDialog("New Unit name").trim();

		// if not empty
		if (!newUnit.equalsIgnoreCase("")) {

			// check if exists
			boolean unitExists = false;
			for (int i = 0; i < units.size(); i++) {
				if (units.get(i).getName().equalsIgnoreCase(newUnit)) {

					unitExists = true;
					break;
				}
			}

			// if doesn't exist
			if (!unitExists) {
				try {
					conn.insertUnitQuery(newUnit);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getUnitsToTable();
			} else {
				JOptionPane.showMessageDialog(null, "This unit already exists");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Name of unit can't be empty");
		}
	}

	// remove category
	private void removeUnit() {
		int idCol = 0;
		int nameCol = 1;

		// if row selected
		if (!(tableUnits.getSelectedRow() == -1)) {

			int selectedRow = tableUnits.getSelectedRow();

			String catId = tableUnits.getValueAt(selectedRow, idCol)
					.toString();

			String catName = tableUnits.getValueAt(selectedRow, nameCol)
					.toString();

			int reply = JOptionPane.showConfirmDialog(null,
					"Do you really want to remove this unit?", "Remove?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				try {
					conn.removeUnitQuery(catId, catName);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// refresh view here
				getUnitsToTable();

			} else {
				// do nothing
			}

		} else {
			System.out.println("Nothing selected");
			JOptionPane
					.showMessageDialog(null,
							"In order to remove unit please select category row first");
		}
	}

	// edit category
	private void updateUnit() {

		// if row selected
		if (!(tableUnits.getSelectedRow() == -1)) {

			int idCol = 0;
			int nameCol = 1;
			int selectedRow = tableUnits.getSelectedRow();

			String id = tableUnits.getValueAt(selectedRow, idCol)
					.toString();
			String currentUnit = tableUnits.getValueAt(selectedRow,
					nameCol).toString();

			newUnit = JOptionPane.showInputDialog(
					"Please enter new name of this unit", currentUnit);
			// if not empty
			if (!newUnit.equalsIgnoreCase("")) {

				// check if exists
				boolean unitExists = false;
				for (int i = 0; i < units.size(); i++) {
					if (units.get(i).getName()
							.equalsIgnoreCase(newUnit)) {
						unitExists = true;
						break;
					}
				}

				// if doesn't exist
				if (!unitExists
						|| (newUnit.equalsIgnoreCase(currentUnit))) {
					try {
						conn.updateUnitQuery(currentUnit, newUnit,
								id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getUnitsToTable();
				} else {
					JOptionPane.showMessageDialog(null,
							"This unit already exists");
				}

			} else {
				JOptionPane.showMessageDialog(null,
						"Name of unit can't be empty");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Please select unit first");
		}
	}
}
