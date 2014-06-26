package userinterface.technischeSysteme;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import userinterface.ToolTip;
import userinterface.ToolTip.TOOL_TIPS;
import userinterface.language.Language;
import userinterface.Utils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import data.Constants;
import data.technischeSysteme.Data_Nutzung;
import data.technischeSysteme.TimeProfile;

public class JP_Nutzung extends JPanel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629739283222114176L;

	private JTextField tf_hauptnutzung = null;
	private JTextField tf_nutzFlaeche = null;
	private SuperComboBox cb_nutzung = null;
	private SuperComboBox cb_profil = null;
	private SuperComboBox cb_persBelegung = null;
	private JLabel l_persBelegung = null;
	private SuperComboBox cb_elektrischegerate = null;
	private JLabel l_elektrischegerate = null;
	private SuperComboBox cb_beleuchtung = null;
	private JLabel l_beleuchtung = null;

	private Diag_TimeProfile timeProfile = null;
	private Diag_TimeProfile timeProfileAnzeige = null;
	private JFrame jf_graphAnzeige = null;
	private TimeProfile l_tp = null;
	
	private JD_TechnischeSysteme jfts = null;
	private Data_Nutzung nutzungData = null;
	
	
	public JP_Nutzung(JD_TechnischeSysteme ts){
		jfts = ts;
		nutzungData = ts.getTechnischeSysteme().getNutzungData();
		l_tp = new TimeProfile(jfts.getNutzung());
		init_gui();
	}
	
	private void init_gui() {

			setBorder(Utils.createSuperBorder(Language.getText("Nutzung"), Utils.gross));
			
			JLabel info = ToolTip.getToolTip(TOOL_TIPS.NUTZUNG);
			
			JLabel l_hauptnutzung = new JLabel(Language.getText("Hauptnutzung") + ":");
			l_hauptnutzung.setFont(Utils.normal);
			JLabel l_nutzflaeche = new JLabel(Language.getText("Nutzflaeche") + ":");
			l_nutzflaeche.setFont(Utils.normal);
			JLabel l_qm = new JLabel("m²");
			l_qm.setFont(Utils.normal);
			JLabel l_profil = new JLabel(Language.getText("Profil") + ":");
			l_profil.setFont(Utils.normal);
			JLabel l_tage = new JLabel(Language.getText("Nutzung") + ":");
			l_tage.setFont(Utils.normal);
			
			JLabel l_personenbelegung = new JLabel(Language.getText("Personenbelegung"));
			l_personenbelegung.setFont(Utils.normal);
//			getCb_personen()
			JLabel l_qmPerson = new JLabel("m²/P.");
			l_qmPerson.setFont(Utils.normal);
			
			JLabel l_elGeraete = new JLabel(Language.getText("ElektrischeGeraete"));
			l_elGeraete.setFont(Utils.normal);
//			getCb_elektrischegerate()
			JLabel l_wGeraete = new JLabel("W/m²");
			l_wGeraete.setFont(Utils.normal);
			
			JLabel l_beleuchtung = new JLabel(Language.getText("Beleuchtung"));
			l_beleuchtung.setFont(Utils.normal);
//			getCb_beleuchtung()
			JLabel l_wBeleuchtung = new JLabel("W/m²");
			l_wBeleuchtung.setFont(Utils.normal);

			
			FormLayout fl = new FormLayout(	"left:pref:grow, 50dlu, fill:20dlu:grow, 4dlu, right:pref, 2dlu, pref",
											"9dlu, center:pref, 9dlu, center:pref, 15dlu, " +
											"center:pref, 9dlu, center:pref, 9dlu, center:pref, 9dlu, center:pref, 15dlu, " +
											"center:pref, 9dlu, center:pref, 9dlu, center:pref, 9dlu, center:pref");
			setLayout(fl);
			
			PanelBuilder pb = new PanelBuilder(fl, this);
			CellConstraints cc = new CellConstraints();

			
			pb.add(l_hauptnutzung, cc.xy(1, 2));
			pb.add(getTf_hauptnutzung(), cc.xyw(3, 2, 5));
			pb.add(l_nutzflaeche, cc.xy(1, 4));
			pb.add(getTf_nutzFlaeche(), cc.xyw(3, 4, 4, CellConstraints.FILL, CellConstraints.CENTER));
			pb.add(l_qm, cc.xy(7, 4, CellConstraints.RIGHT, CellConstraints.CENTER));
			
			pb.addSeparator(Language.getText("AnzeigeProfil"), cc.xyw(1, 6, 6));
			pb.add(info, cc.xy(7, 6, CellConstraints.RIGHT, CellConstraints.CENTER));
			pb.add(l_profil, cc.xy(1, 8));
			pb.add(getCb_profil(), cc.xyw(3, 8, 5));
			pb.add(l_tage, cc.xy(1, 10));
			pb.add(getCb_nutzung(), cc.xyw(3, 10, 5));
			pb.add(getTimeProfile(), cc.xyw(1, 12, 7));
			
			pb.addSeparator(Language.getText("AuswahlNutzungsintensitaet"), cc.xyw(1, 14, 7));
			pb.add(l_personenbelegung, cc.xy(1, 16));
			pb.add(getCb_persBelegung(), cc.xy(3, 16));
			pb.add(getL_persBelegung(), cc.xy(5, 16));
			pb.add(l_qmPerson, cc.xy(7, 16));
			
			pb.add(l_elGeraete, cc.xy(1, 18));
			pb.add(getCb_elektrischegerate(), cc.xy(3, 18));
			pb.add(getL_elektrischegerate(), cc.xy(5, 18));
			pb.add(l_wGeraete, cc.xy(7, 18));
			
			pb.add(l_beleuchtung, cc.xy(1, 20));
			pb.add(getCb_beleuchtung(), cc.xy(3, 20));
			pb.add(getL_beleuchtung(), cc.xy(5, 20));
			pb.add(l_wBeleuchtung, cc.xy(7, 20));

	}
	
	public void loadData(){
		//nutzung
		getCb_profil().setSelectedIndex(nutzungData.getProfil());
		getCb_nutzung().setSelectedIndex(nutzungData.getNutzungIndex());
		getCb_persBelegung().setSelectedIndex(nutzungData.getPersonenbelegung());
		getCb_elektrischegerate().setSelectedIndex(nutzungData.getElekGeraeteIndex());
		getCb_beleuchtung().setSelectedIndex(nutzungData.getBeleuchtungIndex());
		
		jfts.getJp_Anzeige().getL_profil().setText(Constants.cb_profil[nutzungData.getProfil()]);
		jfts.getJp_Anzeige().getL_nutzung().setText(Constants.cb_nutzung[nutzungData.getNutzungIndex()]);
		jfts.getJp_Anzeige().getL_persbeleg().setText(Constants.cb_auswahlNutzungsintensitaet[nutzungData.getPersonenbelegung()]  + jfts.getNutzung().getPersonenBelegung(nutzungData.getPersonenbelegung()));
		jfts.getJp_Anzeige().getL_elekger().setText(Constants.cb_auswahlNutzungsintensitaet[nutzungData.getElekGeraeteIndex()] + jfts.getNutzung().getGeraeteBelegung(nutzungData.getElekGeraeteIndex()));
		jfts.getJp_Anzeige().getL_beleuchtung().setText(Constants.cb_auswahlNutzungsintensitaet[nutzungData.getBeleuchtungIndex()] + jfts.getNutzung().getBeleuchtungBelegung(nutzungData.getBeleuchtungIndex()));
		
		updateGraphTp();
	}
	
	public void saveData(){
		// nutzung
		nutzungData.setProfil(getCb_profil().getSelectedIndex());
		nutzungData.setNutzungIndex(getCb_nutzung().getSelectedIndex());
		nutzungData.setPersonenbelegung(getCb_persBelegung().getSelectedIndex());
		nutzungData.setElekGeraeteIndex(getCb_elektrischegerate().getSelectedIndex());
		nutzungData.setBeleuchtungIndex(getCb_beleuchtung().getSelectedIndex());
	}
	
	public JTextField getTf_hauptnutzung() {
		if (tf_hauptnutzung == null) {
			tf_hauptnutzung = new JTextField();
			tf_hauptnutzung.setEditable(false);
			tf_hauptnutzung.setFocusable(false);
			tf_hauptnutzung.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		}
		return tf_hauptnutzung;
	}

	public JTextField getTf_nutzFlaeche() {
		if (tf_nutzFlaeche == null) {
			tf_nutzFlaeche = new JTextField();
			tf_nutzFlaeche.setEditable(false);
			tf_nutzFlaeche.setFocusable(false);
			tf_nutzFlaeche.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		}
		return tf_nutzFlaeche;
	}

	public SuperComboBox getCb_nutzung() {
		if (cb_nutzung == null) {
			cb_nutzung = new SuperComboBox(Constants.cb_nutzung);
			cb_nutzung.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					updateGraphTp();
				}
			});
		}
		return cb_nutzung;
	}

	public SuperComboBox getCb_profil() {
		if (cb_profil == null) {
			cb_profil = new SuperComboBox(Constants.cb_profil);
			cb_profil.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					updateGraphTp();
				}
			});
		}
		return cb_profil;
	}
	
	public void aktualisiereAuswahlNutzungsint(){
		getL_persBelegung().setText(""+jfts.getNutzung().getPersonenBelegung(getCb_persBelegung().getSelectedIndex()));
		getL_elektrischegerate().setText(""+jfts.getNutzung().getGeraeteBelegung(getCb_elektrischegerate().getSelectedIndex()));
		getL_beleuchtung().setText(""+jfts.getNutzung().getBeleuchtungBelegung(getCb_beleuchtung().getSelectedIndex()));
	}
	
	public JLabel getL_persBelegung() {
		if (l_persBelegung==null) {
			l_persBelegung = new JLabel();
		}
		return l_persBelegung;
	}

	public JLabel getL_elektrischegerate() {
		if (l_elektrischegerate==null) {
			l_elektrischegerate = new JLabel();
		}
		return l_elektrischegerate;
	}

	public JLabel getL_beleuchtung() {
		if (l_beleuchtung==null) {
			l_beleuchtung = new JLabel();
		}
		return l_beleuchtung;
	}

	public SuperComboBox getCb_persBelegung() {
		if (cb_persBelegung == null) {
			String[] persBeleg  = {Language.getText("gering"), Language.getText("mittel"), Language.getText("hoch")};
			cb_persBelegung = new SuperComboBox(persBeleg);
			cb_persBelegung.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					getL_persBelegung().setText(""+jfts.getNutzung().getPersonenBelegung(cb_persBelegung.getSelectedIndex()));
					jfts.getJp_mechLueftung().setStandardInTfWechselInn();
					
				}
			});
		}
		return cb_persBelegung;
	}

	public SuperComboBox getCb_elektrischegerate() {
		if (cb_elektrischegerate == null) {

			String[] elg  = {Language.getText("gering"), Language.getText("mittel"), Language.getText("hoch")};
			cb_elektrischegerate = new SuperComboBox(elg);
			cb_elektrischegerate.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					getL_elektrischegerate().setText(""+jfts.getNutzung().getGeraeteBelegung(cb_elektrischegerate.getSelectedIndex()));
					
				}
			});
		}
		return cb_elektrischegerate;
	}

	public SuperComboBox getCb_beleuchtung() {
		if (cb_beleuchtung == null) {
			String[] bel  = {Language.getText("gering"), Language.getText("mittel"), Language.getText("hoch")};
			cb_beleuchtung = new SuperComboBox(bel);
			cb_beleuchtung.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					getL_beleuchtung().setText(""+jfts.getNutzung().getBeleuchtungBelegung(cb_beleuchtung.getSelectedIndex()));
					
				}
			});
		}
		return cb_beleuchtung;
	}
	
	public Data_Nutzung getNutzungData() {
		return nutzungData;
	}
	
	public void updateGraphTp() {
//		System.out.println("nutz "+getCb_nutzung().getSelectedIndex()+" profil "+ getCb_profil().getSelectedIndex()+" haupt "+ Project.getInstance().getHauptnutzungsNummer());
		l_tp.setDataList(getCb_nutzung().getSelectedIndex(), getCb_profil().getSelectedIndex());
		
		getTimeProfile().setData(l_tp.getDataList());
		jfts.getJp_Anzeige().getTimeProfile().setData(l_tp.getDataList());
		getTimeProfileAnzeige().setData(l_tp.getDataList());
		this.repaint();
	}
	
	public Diag_TimeProfile getTimeProfile() {
		if (timeProfile == null) {
			timeProfile = new Diag_TimeProfile();
			timeProfile.setBorder(BorderFactory.createLineBorder(Color.black));
			timeProfile.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
			timeProfile.setChartSize(this.getWidth(), 100);
			timeProfile.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mousePressed(MouseEvent e) {
					getJf_graphAnzeige().setLocation(e.getXOnScreen()-getJf_graphAnzeige().getWidth()/2, e.getYOnScreen()-getJf_graphAnzeige().getHeight()/2);
					getJf_graphAnzeige().setVisible(true);
					super.mousePressed(e);
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					getJf_graphAnzeige().setVisible(false);
					super.mouseReleased(e);
				}
			});
		}
		return timeProfile;
	}

	public Diag_TimeProfile getTimeProfileAnzeige() {
		if (timeProfileAnzeige==null) {
			timeProfileAnzeige = new Diag_TimeProfile();
//			timeProfileAnzeige.seta
			timeProfileAnzeige.setAxisVisible(true);
			timeProfileAnzeige.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		return timeProfileAnzeige;
	}
	
	public JFrame getJf_graphAnzeige() {
		if (jf_graphAnzeige == null) {
			jf_graphAnzeige = new JFrame();
			jf_graphAnzeige.setAlwaysOnTop(true);
			jf_graphAnzeige.setUndecorated(true);
			jf_graphAnzeige.setSize(getTimeProfileAnzeige().getPreferredSize());			
			jf_graphAnzeige.add(getTimeProfileAnzeige());	
		}
		return jf_graphAnzeige;
	}
	
}
