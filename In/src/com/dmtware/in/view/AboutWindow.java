/*
 * About Window class
 */
package com.dmtware.in.view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JDialog;

import java.awt.Toolkit;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;

public class AboutWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutWindow dialog = new AboutWindow();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
	public AboutWindow() {
		setModal(true);
		setResizable(false);
		setUndecorated(true);
		setTitle("In - About");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutWindow.class.getResource("/com/dmtware/in/view/logo_new.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setBackground(new Color(56, 56, 56));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.setFocusPainted(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(181, 266, 89, 23);
		btnOk.setBackground(new Color(204, 204, 204));
		getContentPane().add(btnOk);
		
		JLabel lblInInventory = new JLabel("In v1.0");
		lblInInventory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInInventory.setHorizontalAlignment(SwingConstants.CENTER);
		lblInInventory.setForeground(Color.LIGHT_GRAY);
		lblInInventory.setBounds(10, 55, 430, 38);
		getContentPane().add(lblInInventory);
		
		JLabel lblFreeOpenSource = new JLabel("Free, open source, cross platform inventory management software.");
		lblFreeOpenSource.setHorizontalAlignment(SwingConstants.CENTER);
		lblFreeOpenSource.setForeground(Color.LIGHT_GRAY);
		lblFreeOpenSource.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFreeOpenSource.setBounds(10, 103, 430, 38);
		getContentPane().add(lblFreeOpenSource);
		
		JEditorPane dtrpnLink = new JEditorPane();
		dtrpnLink.setSelectionColor(Color.WHITE);
		dtrpnLink.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dtrpnLink.setForeground(Color.LIGHT_GRAY);
		dtrpnLink.setOpaque(false);
		dtrpnLink.setContentType("text/html");
		dtrpnLink.setText("<a href='http://dmtware.com/'>www.dmtware.com</a>");
		dtrpnLink.setBackground(Color.LIGHT_GRAY);
		dtrpnLink.setEditable(false);
		
		dtrpnLink.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent hle) {
	               if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                       System.out.println(hle.getURL());
                       Desktop desktop = Desktop.getDesktop();
                       try {
                           desktop.browse(hle.getURL().toURI());
                       } catch (Exception ex) {
                           ex.printStackTrace();
                       }
                   }
   
			}
		});
		dtrpnLink.setForeground(Color.RED);
		dtrpnLink.setBounds(164, 155, 134, 32);
		getContentPane().add(dtrpnLink);
		
		JButton button = new JButton("");
		button.setBackground(new Color(204, 204, 204));
		button.setEnabled(false);
		button.setBounds(154, 157, 143, 23);
		getContentPane().add(button);

	}
}
