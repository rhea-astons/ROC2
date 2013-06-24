package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Utilisateur;
import heig.igl3.roc2.Data.Roc2DB;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Classe ROC2 main class
 * 
 * @author artphi
 * 
 */
public class ROC2 extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private ROC2StatusBar statusBar;
	private ROC2MenuBar menuBar;

	private JPanel view;

	@SuppressWarnings("unused")
	private Utilisateur connectedUser;
	private static Budget budget;

	/**
	 * Constructeur
	 */
	public ROC2() {
		super();

		connectedUser = Login.user;

		menuBar = new ROC2MenuBar();
		setJMenuBar(menuBar);
		menuBar.menuItemQuitter.addActionListener(this);
		menuBar.menuiItemBudget.addActionListener(this);
		menuBar.menuItemCategories.addActionListener(this);
		menuBar.menuItemMouvements.addActionListener(this);
		menuBar.menuItemApropos.addActionListener(this);
		// menuBar.menuItemGraphe.addActionListener(this);

		statusBar = new ROC2StatusBar();

		setTitle("ROC2");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setResizable(true);

		add(statusBar, BorderLayout.SOUTH);
		statusBar.setMessage("Statut");

		// view = new CategorieView(budget);
		view = new BudgetView(budget);
		add(view, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Utilisateur user;
			Login frame = new Login(null, true, false);
			frame.setSize(300, 100);
			frame.setLocationRelativeTo(null);
			frame.setUndecorated(true);
			frame.setVisible(true);
			user = Login.user;
			frame.dispose();

			while (user == null) {
				Login framebis = new Login(null, true, true);
				framebis.setSize(300, 133);
				framebis.setLocationRelativeTo(null);
				framebis.setUndecorated(true);
				framebis.setVisible(true);
				user = Login.user;
				framebis.dispose();
			}

			BudgetSelect frame2 = new BudgetSelect(null, true, user);
			frame2.setSize(300, 150);
			frame2.setLocationRelativeTo(null);
			frame2.setUndecorated(true);
			frame2.setVisible(true);
			frame2.dispose();

			JDialog loader = new JDialog();
			JProgressBar progressBar = new JProgressBar();
			JLabel lblLoading = new JLabel();
			lblLoading.setText("Chargement du budget...");
			progressBar.setIndeterminate(true);
			loader.add(lblLoading, BorderLayout.NORTH);
			loader.add(progressBar, BorderLayout.CENTER);
			loader.setSize(300, 40);
			loader.setLocationRelativeTo(null);
			loader.setUndecorated(true);
			loader.setVisible(true);

			budget = Roc2DB.getBudget(frame2.budgetListItem.id);
			loader.dispose();

			ROC2 gui = new ROC2();
			gui.setVisible(true);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Le programme a rencontrée une erreur importante. Veuillez contacter le support. "
									+ "Message éventuel : "
									+ e.getMessage()
									+ " " + e.getLocalizedMessage());
			System.exit(-1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		switch (actionCommand) {
		case "Quitter":

			System.exit(0);
			break;
		case "Budget":
			this.getContentPane().removeAll();
			view = new BudgetView(budget);
			add(view, BorderLayout.CENTER);
			this.revalidate();
			break;
		case "Mouvements":
			this.getContentPane().removeAll();
			view = new MouvementView(budget);
			add(view, BorderLayout.CENTER);
			this.revalidate();
			break;
		case "Catégories":
			this.getContentPane().removeAll();
			view = new CategorieView(budget);
			add(view, BorderLayout.CENTER);
			this.revalidate();
			break;
		case "A propos":
			String about = "ROC2 v0.9 RC\n" +
						   "\n" +
						   "Calculateur de budget\n" +
						   "\n" +
						   "Par:\n" +
						   "- Raphaël Santos\n" +
						   "- Olivier Francillon\n" +
						   "- Chris Paccaud\n" +
						   "- Cédric Bugnon";
			JOptionPane.showMessageDialog(this, about);
			//JOptionPane.showInternalMessageDialog(this,  , title, messageType, icon)
			break;
		case "Graphe":
			/*
			 * System.out.println("Graphe"); ROC2PieChart graph = new
			 * ROC2PieChart(null, true, budget); graph.setSize(800,600);
			 * graph.setLocationRelativeTo(null); graph.setUndecorated(false);
			 * graph.setVisible(true); graph.dispose();
			 */
			break;
		}
	}

}
