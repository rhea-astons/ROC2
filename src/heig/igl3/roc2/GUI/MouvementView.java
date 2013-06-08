package heig.igl3.roc2.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MouvementView extends JPanel implements ActionListener {
	
	DefaultListModel<String> ml;
	JList<String> list;
	
	public MouvementView() {
		this.setLayout(new BorderLayout(0,0));
		this.ml = new DefaultListModel<String>();
		this.ml.addElement("test1");
		this.ml.addElement("test2");
		this.ml.addElement("test3");
		this.list = new JList<String>(ml);
		
		add(this.list);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
