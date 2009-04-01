package it.polimi.elet.vplab.idswrapper.testing;

import java.awt.*;
import javax.swing.*;

import org.jfree.data.xy.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.renderer.xy.*;


/**
 * 
 * This class plots the ROC curve.<br />
 * You just have to provide any number of series (double[2][*]), it will display 
 * a line for every series. Different parameters can be set to change the 
 * appearance pf the chart.
 * 
 * @author Claudio Magni
 * @version 1.0
 * 
 ******************************************************************************/

public class Chart extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Variables needed to create the plot
	 */
	private String title;
	private ChartPanel cpanel;
	private JFreeChart chart;
	private DefaultXYDataset data;
	private Container panel;
	private Font font1;
	private Font font2;
	private Font font3;
	private NumberAxis domain;
	private NumberAxis range;
	private XYLineAndShapeRenderer render;
	private XYPlot plot;
	private int num;	// counter used to change curves appearance
	float dash1[] = {7, 5};
	float dash2[] = {5, 5};
	Stroke stroke1 = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, dash1, 1);
	Stroke stroke2 = new BasicStroke(3);
	Stroke stroke3 = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, dash2, 1);
	private Color color4 = new Color(60,60,60);
	
	/**
	 * Creates a new chart with the supplied title.
	 * 
	 * @param title The title for the plot (null permitted).
	 */
	public Chart (String title)
	{
		// TODO exception
		super("JIDS Test - Risultati");
		this.title = title;
		num = 0;
		
		panel = this.getContentPane();
		
		data = new DefaultXYDataset();
		font1 = new Font("SansSerif", Font.BOLD, 18);
		font2 = new Font("SansSerif", Font.BOLD, 14);
		font3 = new Font("SansSerif", Font.PLAIN, 12);
		
		NumberTickUnit unitX = new NumberTickUnit(0.1);
		NumberTickUnit unitY = new NumberTickUnit(5);
		domain = new NumberAxis("FPR");
		domain.setTickUnit(unitX);
		domain.setRange(-0.01, 3);
		domain.setLabelFont(font2);
		domain.setTickLabelFont(font3);
		range = new NumberAxis("TPR");
		range.setTickUnit(unitY);
		range.setRange(0, 100);
		range.setLabelFont(font2);
		range.setTickLabelFont(font3);
		
		render = new XYLineAndShapeRenderer(true, false);
		
		setVisible(false);
	}
	
	/**
	 * Adds a series (= a curve) to the plot.
	 * 
	 * @param label The name of the series for the legend.
	 * @param points The series itself. Length must be 2 (X and Y)!
	 * 					So points[2][*] only is permitted.
	 */
	public void addSeries (String label, double[][] points)
	{
		num++;
		data.addSeries(label, points);
		
		// Modify curve appearance
		for (int i = 0; i < num; i++) {
			// Let's use 4 types of strokes for now
			int mod = i % 4;
			switch (mod) {
			case 1: render.setSeriesStroke(i, stroke1, true); break;
			case 2: render.setSeriesStroke(i, stroke2, true); break;
			case 3: render.setSeriesStroke(i, stroke3, true); render.setSeriesPaint(i, color4); break;
			default: break;
			}
		}
	}
	
	/**
	 * This outputs the chart throw a <abbr title="Graphical User Interface">GUI</abbr>
	 */
	public void draw ()
	{	
		
		plot = new XYPlot(data, domain, range, render);
		
		chart = new JFreeChart(title, font1, plot, true);
		
		chart.getLegend().setItemFont(font2);
		
		cpanel = new ChartPanel(chart);
		
		panel.add(cpanel);
		
		setSize(700,600);
		setVisible(true);
	}
	
}
