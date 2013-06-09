package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Mouvement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class MouvementView extends JPanel implements ActionListener {
	
	AbstractTableModel tm;
	JTable tbl;
	private Budget budget;
	
	public MouvementView(Budget budget) {
		this.budget = budget;
		this.setLayout(new BorderLayout(0,0));
		
		this.tm = new MouvModel(budget.mouvements);
		this.tbl = new JTable(tm);
		add(this.tbl);
		
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	class MouvModel extends AbstractTableModel {
		
		public ArrayList<Mouvement> list;
		
		public MouvModel(ArrayList<Mouvement> list) {
			this.list = list;
		}

		@Override
		public int getRowCount() {
			return list.size();
		}

		@Override
		public int getColumnCount() {
			return 9;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
			case 0:
				return list.get(rowIndex).id;
			case 1:
				return list.get(rowIndex).libelle;
			case 2:
				return list.get(rowIndex).montant;
			case 3:
				return list.get(rowIndex).type;
			case 4:
				return list.get(rowIndex).date;
			case 5:
				return list.get(rowIndex).periodicite;
			case 6:
				return budget.getCategorie(list.get(rowIndex).idCategorie);
			case 7:
				return budget.getSousCategorie(list.get(rowIndex).idCategorie, list.get(rowIndex).idSousCategorie);
			case 8:
				return list.get(rowIndex).idBudget;
			default:
				return list.get(rowIndex).id;
			}
		}
		
	}

}
