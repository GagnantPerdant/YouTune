package classes;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class FenetrePrincipale extends JFrame
{
    JPanel panel = new JPanel();
    JTextArea zoneTexte = new JTextArea();
    JList<Object> jlistJobBootPageUrlList = new JList<Object>();

    String[] colsVideo = { "Title", "Length", "Url" };
    DefaultTableModel listDataVideo = new DefaultTableModel(colsVideo, 0);
    JTable tblMessVideo = new JTable(listDataVideo);

    public FenetrePrincipale()
    {
	super();
	build();
    }

    public void build()
    {
	/* fenetre principale */
	setTitle("YouTune"); // On donne un titre à l'application
	setSize(400, 600); // On donne une taille à notre fenêtre
	setLocation(1000, 30);
	setVisible(true);
	// setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
	setResizable(true); // On interdit la redimensionnement de la fenêtre
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à
							// l'application de se
							// fermer lors du clic
							// sur la croix
	setContentPane(buildContentPane());
    }

    private JPanel buildContentPane()
    {
	/* gestionnaire de placement fenetre principale */
	panel.setLayout(new BorderLayout());

	// JScrollPane panTableSite = new JScrollPane(tblMessSite);
	// panel.add(panTableSite, BorderLayout.WEST);

	// JScrollPane panTableOffre = new JScrollPane(tblMessOffre);
	// panel.add(panTableOffre, BorderLayout.CENTER);

	// tblMessDetail.setAutoCreateRowSorter(true);
	JScrollPane panTableDetail = new JScrollPane(tblMessVideo);
	panel.add(panTableDetail, BorderLayout.CENTER);
	panel.setVisible(true);

	zoneTexte.setLineWrap(true);
	panel.add(zoneTexte, BorderLayout.SOUTH);

	Font fonte = new Font("2.TimesRoman ",Font.LAYOUT_LEFT_TO_RIGHT,9);
	tblMessVideo.setFont(fonte);
	tblMessVideo.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
	tblMessVideo.getColumnModel().getColumn(0).setPreferredWidth(345);
	tblMessVideo.getColumnModel().getColumn(1).setPreferredWidth(45);
	tblMessVideo.getColumnModel().getColumn(2).setPreferredWidth(10);
	tblMessVideo.addMouseListener(new MouseAdapter()
	{
	    @Override
	    public void mouseClicked(MouseEvent e)
	    {
		int x = tblMessVideo.rowAtPoint(new Point(e.getX(), e.getY()));
		int y = tblMessVideo.columnAtPoint(new Point(e.getX(), e.getY()));
		// System.out.println(x + " " + y);
		// System.out.println(tblMessVideo.getComponentAt(x, y) );

		URI f = null;
		try
		{
		    f = new URI(tblMessVideo.getModel().getValueAt(x, y)
			    .toString());
		} catch (URISyntaxException e2)
		{
		    e2.getMessage();
		}
		zoneTexte.setText(listDataVideo.getRowCount() + " résultats - "
			+ f);
		try
		{
		    System.out.println(f.toURL().toString());
		} catch (MalformedURLException e2)
		{
		    e2.getMessage();
		}
		String cmd = "xterm -e ./yv " + f;
		System.out.println(cmd);
		System.out.println(executeCommand(cmd));
		/*
		 * // Start browser if (Desktop.isDesktopSupported()) { Desktop
		 * dt = Desktop.getDesktop(); if
		 * (dt.isSupported(Desktop.Action.BROWSE)) { //File f = new
		 * File(filePath); try { dt.browse(f); } catch (IOException e1)
		 * { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * } } // TODO Auto-generated method stub
		 */
		super.mouseClicked(e);
	    }
	});

	return panel;
    }

    public void addToListVideo(Video video) throws MalformedURLException
    {
	/* String[] colsVideo = {"Title", "Length", "Url"}; */
	Object[] rowData = { video.getTitle(), video.getLength(),
		video.getUrl() };
	listDataVideo.addRow(rowData);
	zoneTexte.setText(listDataVideo.getRowCount() + " résultats");
    }

    @SuppressWarnings("deprecation")
    public static String string2date(String datecrawl)
    {
	String[] composants;
	String day, month, year;
	composants = datecrawl.split("/");
	day = composants[0];
	month = composants[1];
	year = composants[2];
	datecrawl = year + "/" + month + "/" + day;
	Date realdatecrawl = new Date();
	realdatecrawl.setDate((int) Integer.parseInt(day));
	realdatecrawl.setMonth((int) Integer.parseInt(month) - 1);
	realdatecrawl.setYear((int) Integer.parseInt(year) - 1900);
	return datecrawl;
    }

    public void addToLog(String text)
    {
	zoneTexte.setText(zoneTexte.getText() + text + "<br>");
    }

    private String executeCommand(String command)
    {

	StringBuffer output = new StringBuffer();

	Process p;
	try
	{
	    p = Runtime.getRuntime().exec(command);
	    p.waitFor();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    p.getInputStream()));

	    String line = "";
	    while ((line = reader.readLine()) != null)
	    {
		output.append(line + "\n");
	    }

	} catch (Exception e)
	{
	    e.printStackTrace();
	}

	return output.toString();

    }

}
