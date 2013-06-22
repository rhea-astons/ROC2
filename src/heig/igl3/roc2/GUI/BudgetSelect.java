package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.BudgetListItem;
import heig.igl3.roc2.Business.Utilisateur;
import heig.igl3.roc2.Data.Roc2DB;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

/**
 * Classe BudgetSelect, sélection du budget sur lequel on veut travailler
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
@SuppressWarnings("serial")
public class BudgetSelect extends JDialog implements ActionListener,
		MouseInputListener, ListSelectionListener {

	private JButton btSubmit, btCancel, btAdd, btRemove, btEdit;
	private JPanel panel;
	private JLabel lblBudgets;
	@SuppressWarnings("unused")
	private Utilisateur user;
	private ArrayList<BudgetListItem> budgetList = new ArrayList<BudgetListItem>();
	private DefaultListModel<BudgetListItem> listModel;
	private JList<BudgetListItem> guiBudgetList;
	public BudgetListItem budgetListItem;

	/**
	 * Constructeur
	 * 
	 * @param frame
	 * @param modal
	 * @param user
	 */
	public BudgetSelect(JFrame frame, boolean modal, Utilisateur user) {
		super(frame, modal);
		this.user = user;

		budgetList = Roc2DB.getBudgetList(user.id);

		listModel = new DefaultListModel<BudgetListItem>();
		for (BudgetListItem item : budgetList) {
			listModel.addElement(item);
		}

		guiBudgetList = new JList<BudgetListItem>(listModel);
		guiBudgetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller = new JScrollPane(guiBudgetList);
		guiBudgetList.addListSelectionListener(this);
		lblBudgets = new JLabel();
		lblBudgets.setText("Choisir un budget: ");

		btSubmit = new JButton("Ouvrir");
		btSubmit.setName("submit");
		btCancel = new JButton("Quitter");
		btCancel.setName("cancel");
		btAdd = new JButton("Ajouter");
		btAdd.setName("add");
		btEdit = new JButton("Editer");
		btEdit.setName("edit");
		btRemove = new JButton("Supprimer");
		btRemove.setName("remove");
		btSubmit.setEnabled(false);
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

		guiBudgetList.addMouseListener(this);
		guiBudgetList.addKeyListener(actionClavier);
		panel = new JPanel(new GridLayout(2, 1));

		add(lblBudgets, BorderLayout.NORTH);
		add(listScroller, BorderLayout.CENTER);
		
		JPanel btBudgetPanel = new JPanel();
		btBudgetPanel.add(btAdd);
		btBudgetPanel.add(btEdit);
		btBudgetPanel.add(btRemove);
		
		JPanel btAppPanel = new JPanel();
		btAppPanel.add(btCancel);
		btAppPanel.add(btSubmit);
		
		panel.add(btBudgetPanel);
		panel.add(btAppPanel);
		add(panel, BorderLayout.SOUTH);

		// add(panel,BorderLayout.CENTER);
		btSubmit.addActionListener(this);
		btCancel.addActionListener(this);
		btAdd.addActionListener(this);
		btEdit.addActionListener(this);
		btRemove.addActionListener(this);
		setTitle("ROC2");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		BudgetEditor frame;
		switch (source.getName()) {
		case "cancel":
			System.exit(0);
			break;
		case "submit":
			this.budgetListItem = listModel.get(guiBudgetList.getSelectedIndex());
			setVisible(false);
			break;
		case "add":
			frame = new BudgetEditor(null, true, budgetList, user);
			frame.setSize(300, 80);
			frame.setLocationRelativeTo(null);
			frame.setUndecorated(false);
			frame.setVisible(true);
			frame.dispose();
			if (frame.budget != null) {
				listModel.addElement(frame.budget);
			}
			break;
		case "edit":
			BudgetListItem toEdit = listModel.get(guiBudgetList.getSelectedIndex());
			frame = new BudgetEditor(null, true, budgetList, user, toEdit);
			frame.setSize(300, 80);
			frame.setLocationRelativeTo(null);
			frame.setUndecorated(false);
			frame.setVisible(true);
			frame.dispose();
			if (frame.budget != null) {
				listModel.removeElement(toEdit);
				listModel.addElement(frame.budget);
			}
			break;
		case "remove":
			if (JOptionPane.showConfirmDialog(this,
					"Voulez vous supprimer définitivement cet élément ?",
					"ROC2", JOptionPane.YES_NO_OPTION) == 0) {
				Runnable run = new Runnable() {
					public void run() {
						BudgetListItem toDelete = listModel.get(guiBudgetList.getSelectedIndex());
						if (Roc2DB.delBudget(toDelete.id)) {
							listModel.removeElement(toDelete);
						}
						btSubmit.setEnabled(false);
					}
				};
				SwingUtilities.invokeLater(run);
			}
			break;
		}
		this.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && guiBudgetList.getSelectedIndex() >= 0) {
			btSubmit.doClick();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
	 * .ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == guiBudgetList) {
			
			btSubmit.setEnabled(true);
		} else {
			btSubmit.setEnabled(false);
		}
	}

}
