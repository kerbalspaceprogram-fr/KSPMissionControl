/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.kerbalspaceprogram.kspmissioncontrol;

import java.awt.Color;
import fr.kerbalspaceprogram.kspmissioncontrol.Telemetry.DataTelemetry;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Bruno
 */
public class KspChart {
    
    final private XYSeries series;
    final private ChartPanel chartPanel;
    final private DataTelemetry x, y;
    final private String title;
    final private String uid;
    
    
    KspChart(String uid, String title, DataTelemetry x, DataTelemetry y) throws Exception {
        if (!Telemetry.typeOf(x).equals(float.class)) throw new Exception("X is not a numeric value.");
        if (!Telemetry.typeOf(y).equals(float.class)) throw new Exception("Y is not a numeric value.");
        
        this.uid = uid;
        this.title = title;
        this.x = x;
        this.y = y;
        this.series = new XYSeries(this.title);
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(this.series);
        
        final JFreeChart chart = ChartFactory.createXYLineChart(
            title,      // chart title
            Telemetry.getDataLabel(this.x), // x axis label
            Telemetry.getDataLabel(this.y), // y axis label
            dataset,    // data
            PlotOrientation.VERTICAL,
            false,  // include legend
            true,   // tooltips
            false   // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.BLACK);
            
        //final StandardLegend legend = (StandardLegend) chart.getLegend();
        //legend.setDisplaySeriesShapes(true);
        //chart.getLegend().setVisible(false);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.BLACK);
        //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelPaint(Color.LIGHT_GRAY);
        rangeAxis.setAxisLinePaint(Color.LIGHT_GRAY);
        rangeAxis.setTickLabelPaint(Color.LIGHT_GRAY);
        
        final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        domainAxis.setLabelPaint(Color.LIGHT_GRAY);
        domainAxis.setAxisLinePaint(Color.LIGHT_GRAY);
        domainAxis.setTickLabelPaint(Color.LIGHT_GRAY);
        // OPTIONAL CUSTOMISATION COMPLETED.
                
       this.chartPanel = new ChartPanel(chart);
    }
    
    public ChartPanel getChartPanel() {
        return this.chartPanel;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void addData(Telemetry t) {
        if (this.uid.equals(t.getData(DataTelemetry.uid)))
            this.series.add(Float.valueOf(t.getData(this.x)),Float.valueOf(t.getData(this.y)));
    }
}
