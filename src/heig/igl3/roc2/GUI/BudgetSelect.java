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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class BudgetSelect extends JDialog implements ActionListener, MouseInputListener,ListSelectionListener{
	
	private JButton btSubmit, btCancel;
    private JPanel panel;
    private JLabel lblBudgets;
    @SuppressWarnings("unused")
	private Utilisateur user;
    private ArrayList<BudgetListItem> budgetList = new ArrayList<BudgetListItem>();
    private DefaultListModel<BudgetListItem> listModel;
    private JList<BudgetListItem> guiBudgetList;
    public BudgetListItem budgetListItem;
    
    public BudgetSelect(JFrame frame, boolean modal, Utilisateur user) {
    	super(frame, modal);
    	this.user = user;

    	budgetList = Roc2DB.getBudgetList(user.id);
    	
    	listModel = new DefaultListModel<BudgetListItem>();
    	for(BudgetListItem item : budgetList) {
    		listModel.addElement(item);
    	}
    	
    	guiBudgetList = new JList<BudgetListItem>(listModel);
    	guiBudgetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane listScroller = new JScrollPane(guiBudgetList);
    	guiBudgetList.addListSelectionListener(this);
    	lblBudgets = new JLabel();
    	lblBudgets.setText("Choisir un budget: ");

    	btSubmit=new JButton("Ouvrir");
    	btCancel=new JButton ("Quitter");
    	btSubmit.setEnabled(false);
    	KeyAdapter actionClavier = new KeyAdapter(){
    		@Override
            public void keyPressed(KeyEvent e){
    			int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER)
                    btSubmit.doClick();
                else if (key == KeyEvent.VK_ESCAPE)
                    btCancel.doClick();
    		}
    	};
    	
    	
    	guiBudgetList.addMouseListener(this);
    	guiBudgetList.addKeyListener(actionClavier);
    	panel=new JPanel(new GridLayout(1,1));
    	
    	add(lblBudgets, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
        panel.add(btCancel);
        panel.add(btSubmit);
        add(panel, BorderLayout.SOUTH);
    	
        //add(panel,BorderLayout.CENTER);
        btSubmit.addActionListener(this);
        btCancel.addActionListener(this);
        setTitle("ROC2");
        
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btCancel)
			System.exit(0);
		this.budgetListItem = listModel.get(guiBudgetList.getSelectedIndex());
        setVisible(false);
        
	}

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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==guiBudgetList){
			btSubmit.setEnabled(true);
		}else{
			btSubmit.setEnabled(false);
		}
	}

}
