package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.BudgetListItem;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Utilisateur;
import heig.igl3.roc2.Data.Roc2DB;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BudgetEditor extends JDialog implements ActionListener{
	
	private JPanel panel;
	private JTextField budgName;
	private JLabel lblName;
	private JButton btSubmit, btCancel;
	public BudgetListItem budget, budgToEdit = null;
	private ArrayList<BudgetListItem> budgetList;
	private boolean edit;
	private Utilisateur user;
	
	public BudgetEditor(JFrame frame, boolean modal, ArrayList<BudgetListItem> budgetList, Utilisateur user) {
		super(frame, modal);
		this.budgetList = budgetList;
		this.user = user;
		
		lblName = new JLabel();
		lblName.setText("Nom:");
		budgName = new JTextField(25);

		btSubmit = new JButton("Valider");
		btCancel = new JButton("Annuler");

		KeyAdapter actionClavier = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER)
					btSubmit.doClick();
				else if (key == KeyEvent.VK_ESCAPE)
					btCancel.doClick();
			}
		};

		budgName.addKeyListener(actionClavier);

		panel = new JPanel(new GridLayout(2, 1));
		panel.add(lblName);
		panel.add(budgName);
		panel.add(btCancel);
		panel.add(btSubmit);
		add(panel, BorderLayout.CENTER);
		btSubmit.addActionListener(this);
		btCancel.addActionListener(this);
		setTitle("ROC2");
		
	}
	
	public BudgetEditor(JFrame frame, boolean modal, ArrayList<BudgetListItem> budgetList, Utilisateur user, BudgetListItem budgToEdit) {
		this(frame, modal, budgetList, user);
		this.budgToEdit = budgToEdit;
		this.edit = true;
		this.budgName.setText(budgToEdit.libelle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean exist;
		if (e.getSource() == btCancel) {
			this.setVisible(false);
			budget = null;
		}
		if (e.getSource() == btSubmit) {;
			if (budgName.getText().length() > 3 && edit) {
				budget = Roc2DB.editBudget(budgToEdit, budgName.getText());
				setVisible(false);
			} else if (budgName.getText().length() > 3) {
				budget = Roc2DB.addBudget(budgName.getText(), user.id);
				setVisible(false);
			}
		}
		
	}

}
