// designed for coatjava 3.0
package validationGuiPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jlab.clas.fastmc.Clas12FastMC;
import org.jlab.clas.physics.GenericKinematicFitter;
import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.PhysicsEvent;
import org.jlab.clas.physics.RecEvent;
import org.jlab.detector.base.DetectorType;
import org.jlab.detector.base.GeometryFactory;
import org.jlab.geom.base.Detector;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.F1D;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
	
public class MyGui extends Application {
	EmbeddedCanvas pCan = new EmbeddedCanvas();
	EmbeddedCanvas thCan = new EmbeddedCanvas();
	EmbeddedCanvas phCan = new EmbeddedCanvas();
	VBox vbLeft = null;
    ComboBox<Integer> cb_particles = null;
    List<TextField> tf = new ArrayList<>();
    TextField file_TF = null;
    CheckBox fmc_CB = null;
    boolean goHasBeenClicked = false;
    int minEntriesForFit = 100;
    int pBin = 0;
    int thBin = 0;
    int phBin = 0;
    List<List<H1F>> hpgen = new ArrayList<>();
    List<List<H1F>> hpgenSensitive = new ArrayList<>();
    List<List<H1F>> hpgenDetected = new ArrayList<>();
    List<List<H1F>> hprec = new ArrayList<>();
    List<List<H1F>> hpacc = new ArrayList<>();
    List<List<H1F>> hpaccGeometric = new ArrayList<>();
    List<List<H1F>> hpaccTracking = new ArrayList<>();
    List<List<List<H1F>>> hpres = new ArrayList<>();
    List<List<H1F>> hthgen = new ArrayList<>();
    List<List<H1F>> hthgenSensitive = new ArrayList<>();
    List<List<H1F>> hthgenDetected = new ArrayList<>();
    List<List<H1F>> hthrec = new ArrayList<>();
    List<List<H1F>> hthacc = new ArrayList<>();
    List<List<H1F>> hthaccGeometric = new ArrayList<>();
    List<List<H1F>> hthaccTracking = new ArrayList<>();
    List<List<List<H1F>>> hthres = new ArrayList<>();
    List<List<H1F>> hphgen = new ArrayList<>();
    List<List<H1F>> hphgenSensitive = new ArrayList<>();
    List<List<H1F>> hphgenDetected = new ArrayList<>();
    List<List<H1F>> hphrec = new ArrayList<>();
    List<List<H1F>> hphacc = new ArrayList<>();
    List<List<H1F>> hphaccGeometric = new ArrayList<>();
    List<List<H1F>> hphaccTracking = new ArrayList<>();
    List<List<List<H1F>>> hphres = new ArrayList<>();
    List<List<List<F1D>>> ffp = new ArrayList<>();
    List<List<List<F1D>>> ffth = new ArrayList<>();
    List<List<List<F1D>>> ffph = new ArrayList<>();
    List<List<GraphErrors>> gDpOpVp = new ArrayList<>();
    List<List<GraphErrors>> gDthVp = new ArrayList<>();
    List<List<GraphErrors>> gDphVp = new ArrayList<>();
    List<List<GraphErrors>> gDpOpVth = new ArrayList<>();
    List<List<GraphErrors>> gDthVth = new ArrayList<>();
    List<List<GraphErrors>> gDphVth = new ArrayList<>();
    List<List<GraphErrors>> gDpOpVph = new ArrayList<>();
    List<List<GraphErrors>> gDthVph = new ArrayList<>();
    List<List<GraphErrors>> gDphVph = new ArrayList<>();
    List<List<GraphErrors>> gpresMuVp = new ArrayList<>();
    List<List<GraphErrors>> gthresMuVp = new ArrayList<>();
    List<List<GraphErrors>> gphresMuVp = new ArrayList<>();
    List<List<GraphErrors>> gpresMuVth = new ArrayList<>();
    List<List<GraphErrors>> gthresMuVth = new ArrayList<>();
    List<List<GraphErrors>> gphresMuVth = new ArrayList<>();
    List<List<GraphErrors>> gpresMuVph = new ArrayList<>();
    List<List<GraphErrors>> gthresMuVph = new ArrayList<>();
    List<List<GraphErrors>> gphresMuVph = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	pCan.divide(3, 3);
    	thCan.divide(3, 3);
    	phCan.divide(3, 3);
    	
    	org.jlab.groot.base.GStyle.getAxisAttributesX().setLabelFontSize(18);
    	org.jlab.groot.base.GStyle.getAxisAttributesX().setTitleFontSize(18);
    	org.jlab.groot.base.GStyle.getAxisAttributesY().setLabelFontSize(18);
    	org.jlab.groot.base.GStyle.getAxisAttributesY().setTitleFontSize(18);

    	SwingNode pSN = new SwingNode();
    	pSN.setContent(pCan);
    	SwingNode thSN = new SwingNode();
    	thSN.setContent(thCan);
    	SwingNode phSN = new SwingNode();
    	phSN.setContent(phCan);
    	
    	BorderPane pBP = new BorderPane();
    	pBP.setCenter(pSN);
    	BorderPane thBP = new BorderPane();
    	thBP.setCenter(thSN);
    	BorderPane phBP = new BorderPane();
    	phBP.setCenter(phSN);
    	
    	vbLeft = new VBox();

    	TabPane tabPane = new TabPane();
    	Tab pTab = new Tab("p distributions and dependencies");
    	pTab.setContent(pBP);
    	Tab thTab = new Tab("th distributions and dependencies");
    	thTab.setContent(thBP);
    	Tab phTab = new Tab("ph distributions and dependencies");
    	phTab.setContent(phBP);
    	tabPane.getTabs().add(pTab);
    	tabPane.getTabs().add(thTab);
    	tabPane.getTabs().add(phTab);
    	
        SplitPane split = new SplitPane();
        split.setDividerPosition(0, 0.32);
        split.getItems().add(vbLeft);
        split.getItems().add(tabPane);

        Scene scene = new Scene(split, 1250, 850);
        primaryStage.setScene(scene);
        primaryStage.show();

        addOptionsList();
    }
    
    private void addOptionsList(){
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5)); // margins
        
        grid.add(new Label("hipo file: "), 0, 0);
        //file_TF = new TextField("/Users/harrison/tempCook2/eppippim/rec_gemcTest.1t9.hipo");
        //file_TF = new TextField("/Users/harrison/tempCook2/rec_eppippim.40k.hipo");
        //file_TF = new TextField("/Users/harrison/Desktop/CLAS12softwareValidation/forward_e_DST.340k.18oct16.hipo");
        file_TF = new TextField("/Users/harrison/realistic_e_baseline.1t43.DST.hipo");
        grid.add(file_TF, 1, 0);

        fmc_CB = new CheckBox("pass through fastMC");
        //fmc_CB.setSelected(true);
        fmc_CB.setSelected(false);
        grid.add(fmc_CB, 0, 1);

        cb_particles = new ComboBox<>();
        cb_particles.getItems().addAll(11, 2212, 211, -211, 13, -13, 22);
        cb_particles.setEditable(true);
        cb_particles.setValue(11);
        grid.add(new Label("PID: "), 0, 2);
        grid.add(cb_particles, 1, 2);
        
        String[] inputNames = {"No. of p bins", "p min (GeV)", "p max (GeV)", "No. of theta bins", "theta min (deg)", "theta max (deg)", "No. of phi bins", "phi min (deg)", "phi max (deg)", "Torus scale", "Solenoid scale", "No. of events", "statuses of interest"};
        String[] defaultInputs = {"5", "0.5", "10.5", "1", "5.0", "35.0", "1", "-180.0", "180.0", "-1.0", "1.0", "500000", "100, 200, 300, 400"};
        int NinputRows = inputNames.length;

        for(int k = 0; k < NinputRows; k++)
        {
	        tf.add(new TextField(String.format("%s", defaultInputs[k])));
	        grid.add(new Label(String.format("%s", inputNames[k])), 0, k+3);
	        grid.add(tf.get(k), 1, k+3);
        }

        Button goBtn = new Button("Go!");
        grid.add(goBtn, 1, NinputRows+3);
        goBtn.setOnAction(event -> {
        	if(goHasBeenClicked == true)
        	{
        		rm_p_th_ph_buttons();
        		clear_histo_lists();
        	}
        	draw_p_th_ph_buttons();
        	define_histos();
        	runAnalysis();
        	drawPlots();
        	goHasBeenClicked = true;
        });
        vbLeft.getChildren().add(grid);
    }

    private void rm_p_th_ph_buttons(){
    int vbLength = vbLeft.getChildren().size();	
    vbLeft.getChildren().remove(vbLength-1);
    vbLeft.getChildren().remove(vbLength-2);
    vbLeft.getChildren().remove(vbLength-3);
    vbLeft.getChildren().remove(vbLength-4);
    vbLeft.getChildren().remove(vbLength-5);
    vbLeft.getChildren().remove(vbLength-6);
    }
    
    private void draw_p_th_ph_buttons(){
    	int NpBins = Integer.parseInt(tf.get(0).getText());
    	double pMin = Double.parseDouble(tf.get(1).getText());
    	double pMax = Double.parseDouble(tf.get(2).getText());
    	int NthBins = Integer.parseInt(tf.get(3).getText());
    	double thMin = Double.parseDouble(tf.get(4).getText());
    	double thMax = Double.parseDouble(tf.get(5).getText());
    	int NphBins = Integer.parseInt(tf.get(6).getText());
    	double phMin = Double.parseDouble(tf.get(7).getText());
    	double phMax = Double.parseDouble(tf.get(8).getText());
    	
        List<Button> phBtns = new ArrayList<>();
        List<List<Button>> pthBtns = new ArrayList<>();

    	//buttons for phi bin
    	HBox hb_phi = new HBox();
    	for(int k = 0; k < NphBins; k++)
    	{
   			double phBinCenter = phMin + ((phMax - phMin)/NphBins)*k + 0.5*((phMax - phMin)/NphBins);
    		phBtns.add(new Button(String.format("%.1f\u00b0", phBinCenter))); // \u00b0 is the degree symbol
    		hb_phi.getChildren().add(phBtns.get(k));
    	}
    	vbLeft.getChildren().add(new Label(""));
    	vbLeft.getChildren().add(new Label("choose phi bin: (numbers are bin center)"));
    	vbLeft.getChildren().add(hb_phi);
    	phBtns.get(0).setStyle("-fx-base: #ff0000;");
    	for(int k = 0; k < NphBins; k++)
    	{
    		int tempk = k;
    		phBtns.get(k).setOnAction((ActionEvent e) -> {
    			for(int j = 0; j < NphBins; j++)
    			{
    				phBtns.get(j).setStyle("-fx-base: #c0c0c0;");
    			}
    			phBtns.get(tempk).setStyle("-fx-base: #ff0000;");
    			phBin = tempk;
    			drawPlots();
    		});
    	}

    	//buttons for p/theta bin
    	GridPane gp_pth = new GridPane();
    	for(int p = 0; p < NpBins; p++)
    	{
    		pthBtns.add(new ArrayList<>());
    		for(int th = 0; th < NthBins; th++)
    		{
    			double pBinCenter = pMin + ((pMax - pMin)/NpBins)*p + 0.5*((pMax - pMin)/NpBins);
    			double thBinCenter = thMin + ((thMax - thMin)/NthBins)*th + 0.5*((thMax - thMin)/NthBins);
    			pthBtns.get(p).add(new Button(String.format("%.1fG %.1f\u00b0", pBinCenter, thBinCenter)));
    			gp_pth.add(pthBtns.get(p).get(th), p, th);
    		}
    	}
    	vbLeft.getChildren().add(new Label(""));
    	vbLeft.getChildren().add(new Label("choose p/th bin: (numbers are bin center)"));
    	vbLeft.getChildren().add(gp_pth);
    	pthBtns.get(0).get(0).setStyle("-fx-base: #ff0000;");
    	for(int p = 0; p < NpBins; p++)
    	{
    		for(int th = 0; th < NthBins; th++)
    		{
    			int tempp = p;
    			int tempth = th;
    			pthBtns.get(p).get(th).setOnAction((ActionEvent e) -> {
    				for(int i = 0; i < NpBins; i++)
    				{
    					for(int j = 0; j < NthBins; j++)
    					{
    						pthBtns.get(i).get(j).setStyle("-fx-base: #c0c0c0;");
    					}
    				}
    				pthBtns.get(tempp).get(tempth).setStyle("-fx-base: #ff0000;");
    				pBin = tempp;
    				thBin = tempth;
    				drawPlots();
    			});
    		}
    	}
    }

    private void define_histos(){
    	int NpBins = Integer.parseInt(tf.get(0).getText());
    	double pMin = Double.parseDouble(tf.get(1).getText());
    	double pMax = Double.parseDouble(tf.get(2).getText());
    	int NthBins = Integer.parseInt(tf.get(3).getText());
    	double thMin = Double.parseDouble(tf.get(4).getText());
    	double thMax = Double.parseDouble(tf.get(5).getText());
    	int NphBins = Integer.parseInt(tf.get(6).getText());
    	double phMin = Double.parseDouble(tf.get(7).getText());
    	double phMax = Double.parseDouble(tf.get(8).getText());

    	// loop over th and ph
    	for(int th = 0; th < NthBins; th++)
    	{
    		hpgen.add(new ArrayList<>());
    		hpgenSensitive.add(new ArrayList<>());
    		hpgenDetected.add(new ArrayList<>());
    		hprec.add(new ArrayList<>());
    		gDpOpVp.add(new ArrayList<>());
    		gDthVp.add(new ArrayList<>());
    		gDphVp.add(new ArrayList<>());
    		gpresMuVp.add(new ArrayList<>());
    		gthresMuVp.add(new ArrayList<>());
    		gphresMuVp.add(new ArrayList<>());
    		for(int ph = 0; ph < NphBins; ph++)
    		{
    			double thBinLeftEdge = thMin + ((thMax - thMin)/NthBins)*th;
    			double thBinRightEdge = thMin + ((thMax - thMin)/NthBins)*th + 1.0*((thMax - thMin)/NthBins);
    			double phBinLeftEdge = phMin + ((phMax - phMin)/NphBins)*ph;
    			double phBinRightEdge = phMin + ((phMax - phMin)/NphBins)*ph + 1.0*((phMax - phMin)/NphBins);
    			hpgen.get(th).add(new H1F(String.format("hpgen_th%d_ph%d", th, ph), String.format("%.1f < th < %.1f, %.1f < ph < %.1f", thBinLeftEdge, thBinRightEdge, phBinLeftEdge, phBinRightEdge), 100, pMin-0.1, pMax+0.1));
    			hpgen.get(th).get(ph).setTitleX("p (GeV)");
    			hpgenSensitive.get(th).add(new H1F(String.format("hpgenSensitive_th%d_ph%d", th, ph), String.format("hpgenSensitive_th%d_ph%d", th, ph), 100, pMin-0.1, pMax+0.1));
    			hpgenDetected.get(th).add(new H1F(String.format("hpgenDetected_th%d_ph%d", th, ph), String.format("hpgenDetected_th%d_ph%d", th, ph), 100, pMin-0.1, pMax+0.1));
    			hpgenSensitive.get(th).get(ph).setLineColor(4);
    			hpgenSensitive.get(th).get(ph).setFillColor(50+4);
    			hpgenDetected.get(th).get(ph).setLineColor(5);
    			hpgenDetected.get(th).get(ph).setFillColor(50+5);
    			hprec.get(th).add(new H1F(String.format("hprec_th%d_ph%d", th, ph), String.format("hprec_th%d_ph%d", th, ph), 100, pMin-0.1, pMax+0.1));
    			hprec.get(th).get(ph).setLineColor(2);
    			hprec.get(th).get(ph).setFillColor(50+2);
    			gDpOpVp.get(th).add(new GraphErrors());
    			gDpOpVp.get(th).get(ph).setTitle("#Delta p/p vs p");
    			gDpOpVp.get(th).get(ph).setTitleX("p (GeV)");
    			gDpOpVp.get(th).get(ph).setTitleY("#sigma(#Delta p/p)");
    			gDthVp.get(th).add(new GraphErrors());
    			gDthVp.get(th).get(ph).setTitle("#Delta #theta vs p");
    			gDthVp.get(th).get(ph).setTitleX("p (GeV)");
    			gDthVp.get(th).get(ph).setTitleY("#sigma(#Delta #theta) (mrad)");
    			gDphVp.get(th).add(new GraphErrors());
    			gDphVp.get(th).get(ph).setTitle("#Delta #phi vs p");
    			gDphVp.get(th).get(ph).setTitleX("p (GeV)");
    			gDphVp.get(th).get(ph).setTitleY("#sigma(#Delta #phi) (mrad)");
    			gpresMuVp.get(th).add(new GraphErrors());
    			gpresMuVp.get(th).get(ph).setTitle("#mu(Delta p/p) vs p");
    			gpresMuVp.get(th).get(ph).setTitleX("p (GeV)");
    			gpresMuVp.get(th).get(ph).setTitleY("#mu(Delta p/p)");
    			gthresMuVp.get(th).add(new GraphErrors());
    			gthresMuVp.get(th).get(ph).setTitle("#mu(Delta #theta) vs p");
    			gthresMuVp.get(th).get(ph).setTitleX("p (GeV)");
    			gthresMuVp.get(th).get(ph).setTitleY("#mu(Delta #theta) (mrad)");
    			gphresMuVp.get(th).add(new GraphErrors());
    			gphresMuVp.get(th).get(ph).setTitle("#mu(Delta #phi) vs p");
    			gphresMuVp.get(th).get(ph).setTitleX("p (GeV)");
    			gphresMuVp.get(th).get(ph).setTitleY("#mu(Delta #phi) (mrad)");
    		}
    	}
    	
    	// loop over p and ph
    	for(int p = 0; p < NpBins; p++)
    	{
    		hthgen.add(new ArrayList<>());
    		hthgenSensitive.add(new ArrayList<>());
    		hthgenDetected.add(new ArrayList<>());
    		hthrec.add(new ArrayList<>());
    		gDpOpVth.add(new ArrayList<>());
    		gDthVth.add(new ArrayList<>());
    		gDphVth.add(new ArrayList<>());
    		gpresMuVth.add(new ArrayList<>());
    		gthresMuVth.add(new ArrayList<>());
    		gphresMuVth.add(new ArrayList<>());
    		for(int ph = 0; ph < NphBins; ph++)
    		{
    			double pBinLeftEdge = pMin + ((pMax - pMin)/NpBins)*p;
    			double pBinRightEdge = pMin + ((pMax - pMin)/NpBins)*p + 1.0*((pMax - pMin)/NpBins);
    			double phBinLeftEdge = phMin + ((phMax - phMin)/NphBins)*ph;
    			double phBinRightEdge = phMin + ((phMax - phMin)/NphBins)*ph + 1.0*((phMax - phMin)/NphBins);
    			hthgen.get(p).add(new H1F(String.format("hthgen_p%d_ph%d", p, ph), String.format("%.1f < p < %.1f, %.1f < ph < %.1f", pBinLeftEdge, pBinRightEdge, phBinLeftEdge, phBinRightEdge), 100, thMin-1.0, thMax+1.0));
    			hthgen.get(p).get(ph).setTitleX("#theta (deg)");
    			hthgenSensitive.get(p).add(new H1F(String.format("hthgenSensitive_p%d_ph%d", p, ph), String.format("hthgenSensitive_p%d_ph%d", p, ph), 100, thMin-1.0, thMax+1.0));
    			hthgenDetected.get(p).add(new H1F(String.format("hthgenDetected_p%d_ph%d", p, ph), String.format("hthgenDetected_p%d_ph%d", p, ph), 100, thMin-1.0, thMax+1.0));
    			hthgenSensitive.get(p).get(ph).setLineColor(4);
    			hthgenSensitive.get(p).get(ph).setFillColor(50+4);
    			hthgenDetected.get(p).get(ph).setLineColor(5);
    			hthgenDetected.get(p).get(ph).setFillColor(50+5);
    			hthrec.get(p).add(new H1F(String.format("hthrec_p%d_ph%d", p, ph), String.format("hthrec_p%d_ph%d", p, ph), 100, thMin-1.0, thMax+1.0));
    			hthrec.get(p).get(ph).setLineColor(2);
    			hthrec.get(p).get(ph).setFillColor(50+2);
    			gDpOpVth.get(p).add(new GraphErrors());
    			gDpOpVth.get(p).get(ph).setTitle("#Delta p/p vs #theta");
    			gDpOpVth.get(p).get(ph).setTitleX("#theta (deg)");
    			gDpOpVth.get(p).get(ph).setTitleY("#sigma(#Delta p/p)");
    			gDthVth.get(p).add(new GraphErrors());
    			gDthVth.get(p).get(ph).setTitle("#Delta #theta vs #theta");
    			gDthVth.get(p).get(ph).setTitleX("#theta (deg)");
    			gDthVth.get(p).get(ph).setTitleY("#sigma(#Delta #theta) (mrad)");
    			gDphVth.get(p).add(new GraphErrors());
    			gDphVth.get(p).get(ph).setTitle("#Delta #phi vs #theta");
    			gDphVth.get(p).get(ph).setTitleX("#theta (deg)");
    			gDphVth.get(p).get(ph).setTitleY("#sigma(#Delta #phi) (mrad)");
    			gpresMuVth.get(p).add(new GraphErrors());
    			gpresMuVth.get(p).get(ph).setTitle("#mu(#Delta p/p) vs #theta");
    			gpresMuVth.get(p).get(ph).setTitleX("#theta (deg)");
    			gpresMuVth.get(p).get(ph).setTitleY("#mu(#Delta p/p)");
    			gthresMuVth.get(p).add(new GraphErrors());
    			gthresMuVth.get(p).get(ph).setTitle("#mu(#Delta #theta) vs #theta");
    			gthresMuVth.get(p).get(ph).setTitleX("#theta (deg)");
    			gthresMuVth.get(p).get(ph).setTitleY("#mu(#Delta #theta) (mrad)");
    			gphresMuVth.get(p).add(new GraphErrors());
    			gphresMuVth.get(p).get(ph).setTitle("#mu(#Delta #phi) vs #theta");
    			gphresMuVth.get(p).get(ph).setTitleX("#theta (deg)");
    			gphresMuVth.get(p).get(ph).setTitleY("#mu(#Delta #phi) (mrad)");
    		}
    	}
    	
    	// loop over p and th
    	for(int p = 0; p < NpBins; p++)
    	{
    		hphgen.add(new ArrayList<>());
    		hphgenSensitive.add(new ArrayList<>());
    		hphgenDetected.add(new ArrayList<>());
    		hphrec.add(new ArrayList<>());
    		gDpOpVph.add(new ArrayList<>());
    		gDthVph.add(new ArrayList<>());
    		gDphVph.add(new ArrayList<>());
    		gpresMuVph.add(new ArrayList<>());
    		gthresMuVph.add(new ArrayList<>());
    		gphresMuVph.add(new ArrayList<>());
    		for(int th = 0; th < NthBins; th++)
    		{
    			double pBinLeftEdge = pMin + ((pMax - pMin)/NpBins)*p;
    			double pBinRightEdge = pMin + ((pMax - pMin)/NpBins)*p + 1.0*((pMax - pMin)/NpBins);
    			double thBinLeftEdge = thMin + ((thMax - thMin)/NthBins)*th;
    			double thBinRightEdge = thMin + ((thMax - thMin)/NthBins)*th + 1.0*((thMax - thMin)/NthBins);
    			hphgen.get(p).add(new H1F(String.format("hphgen_p%d_th%d", p, th), String.format("%.1f < p < %.1f, %.1f < th < %.1f", pBinLeftEdge, pBinRightEdge, thBinLeftEdge, thBinRightEdge), 100, phMin-1.0, phMax+1.0));
    			hphgen.get(p).get(th).setTitleX("#phi (deg)");
    			hphgenSensitive.get(p).add(new H1F(String.format("hphgenSensitive_p%d_th%d", p, th), String.format("hphgenSensitive_p%d_th%d", p, th), 100, phMin-1.0, phMax+1.0));
    			hphgenDetected.get(p).add(new H1F(String.format("hphgenDetected_p%d_th%d", p, th), String.format("hphgenDetected_p%d_th%d", p, th), 100, phMin-1.0, phMax+1.0));
    			hphgenSensitive.get(p).get(th).setLineColor(4);
    			hphgenSensitive.get(p).get(th).setFillColor(50+4);
    			hphgenDetected.get(p).get(th).setLineColor(5);
    			hphgenDetected.get(p).get(th).setFillColor(50+5);
    			hphrec.get(p).add(new H1F(String.format("hphrec_p%d_th%d", p, th), String.format("hphrec_p%d_th%d", p, th), 100, phMin-1.0, phMax+1.0));
    			hphrec.get(p).get(th).setLineColor(2);
    			hphrec.get(p).get(th).setFillColor(50+2);
    			gDpOpVph.get(p).add(new GraphErrors());
    			gDpOpVph.get(p).get(th).setTitle("Delta p/p vs #phi");
    			gDpOpVph.get(p).get(th).setTitleX("#phi (deg)");
    			gDpOpVph.get(p).get(th).setTitleY("#sigma(#Delta p/p)");
    			gDthVph.get(p).add(new GraphErrors());
    			gDthVph.get(p).get(th).setTitle("#Delta #theta vs #phi");
    			gDthVph.get(p).get(th).setTitleX("#phi (deg)");
    			gDthVph.get(p).get(th).setTitleY("#sigma(#Delta #theta) (mrad)");
    			gDphVph.get(p).add(new GraphErrors());
    			gDphVph.get(p).get(th).setTitle("#Delta #phi vs #phi");
    			gDphVph.get(p).get(th).setTitleX("#phi (deg)");
    			gDphVph.get(p).get(th).setTitleY("#sigma(#Delta #phi) (mrad)");
    			gpresMuVph.get(p).add(new GraphErrors());
    			gpresMuVph.get(p).get(th).setTitle("#mu(#Delta p/p) vs #phi");
    			gpresMuVph.get(p).get(th).setTitleX("#phi (deg)");
    			gpresMuVph.get(p).get(th).setTitleY("#mu(#Delta p/p)");
    			gthresMuVph.get(p).add(new GraphErrors());
    			gthresMuVph.get(p).get(th).setTitle("#mu(#Delta #theta) vs #phi");
    			gthresMuVph.get(p).get(th).setTitleX("#phi (deg)");
    			gthresMuVph.get(p).get(th).setTitleY("#mu(#Delta #theta) (mrad)");
    			gphresMuVph.get(p).add(new GraphErrors());
    			gphresMuVph.get(p).get(th).setTitle("#mu(#Delta #phi) vs #phi");
    			gphresMuVph.get(p).get(th).setTitleX("#phi (deg)");
    			gphresMuVph.get(p).get(th).setTitleY("#mu(#Delta #phi) (mrad)");
    		}
    	}
    	
    	// loop over p, th, and ph
    	for(int p = 0; p < NpBins; p++)
    	{
    		hpres.add(new ArrayList<>());
    		hthres.add(new ArrayList<>());
    		hphres.add(new ArrayList<>());
    		ffp.add(new ArrayList<>());
    		ffth.add(new ArrayList<>());
    		ffph.add(new ArrayList<>());
    		for(int th = 0; th < NthBins; th++)
    		{
    			hpres.get(p).add(new ArrayList<>());
    			hthres.get(p).add(new ArrayList<>());
    			hphres.get(p).add(new ArrayList<>());
    			ffp.get(p).add(new ArrayList<>());
    			ffth.get(p).add(new ArrayList<>());
    			ffph.get(p).add(new ArrayList<>());
    			for(int ph = 0; ph < NphBins; ph++)
    			{
    				hpres.get(p).get(th).add(new H1F(String.format("hpres_p%d_th%d_ph%d", p, th, ph), String.format("hpres_p%d_th%d_ph%d", p, th, ph), 100, -0.05, 0.05));
    				hpres.get(p).get(th).get(ph).setTitleX("#Delta p/p");
    				hpres.get(p).get(th).get(ph).setLineColor(p+1);
    				hpres.get(p).get(th).get(ph).setFillColor(50+p+1);
    				hthres.get(p).get(th).add(new H1F(String.format("hthres_p%d_th%d_ph%d", p, th, ph), String.format("hthres_p%d_th%d_ph%d", p, th, ph), 100, -0.4, 0.4));
    				hthres.get(p).get(th).get(ph).setTitleX("#Delta #theta (deg)");
    				hthres.get(p).get(th).get(ph).setLineColor(th+1);
    				hthres.get(p).get(th).get(ph).setFillColor(50+th+1);
    				hphres.get(p).get(th).add(new H1F(String.format("hphres_p%d_th%d_ph%d", p, th, ph), String.format("hphres_p%d_th%d_ph%d", p, th, ph), 100, -0.8, 0.8));
    				hphres.get(p).get(th).get(ph).setTitleX("#Delta #phi (deg)");
    				hphres.get(p).get(th).get(ph).setLineColor(ph+1);
    				hphres.get(p).get(th).get(ph).setFillColor(50+ph+1);
    				ffp.get(p).get(th).add(new F1D(String.format("ffp%d%d", p, th), "[amp]*gaus(x, [mu], [sig])", -0.01, 0.01));
    				ffp.get(p).get(th).get(ph).setLineColor(p+1);
    				ffth.get(p).get(th).add(new F1D(String.format("ffthp%d%d", p, th), "[amp]*gaus(x, [mu], [sig])", -0.15, 0.15));
    				ffth.get(p).get(th).get(ph).setLineColor(th+1);
    				ffph.get(p).get(th).add(new F1D(String.format("ffphp%d%d", p, th), "[amp]*gaus(x, [mu], [sig])", -0.25, 0.25));
    				ffph.get(p).get(th).get(ph).setLineColor(ph+1);
    			}
    			
    		}
    	}
    }

    private void clear_histo_lists(){
    	hpgen.clear();
    	hpgenSensitive.clear();
    	hpgenDetected.clear();
    	hprec.clear();
    	hpacc.clear();
    	hpaccGeometric.clear();
    	hpaccTracking.clear();
    	hpres.clear();
    	hthgen.clear();
    	hthgenSensitive.clear();
    	hthgenDetected.clear();
    	hthrec.clear();
    	hthacc.clear();
    	hthaccGeometric.clear();
    	hthaccTracking.clear();
    	hthres.clear();
    	hphgen.clear();
    	hphgenSensitive.clear();
    	hphgenDetected.clear();
    	hphrec.clear();
    	hphacc.clear();
    	hphaccGeometric.clear();
    	hphaccTracking.clear();
    	hphres.clear();
    	ffp.clear();
    	ffth.clear();
    	ffph.clear();
    	gDpOpVp.clear();
    	gDthVp.clear();
    	gDphVp.clear();
    	gDpOpVth.clear();
    	gDthVth.clear();
    	gDphVth.clear();
    	gDpOpVph.clear();
    	gDthVph.clear();
    	gDphVph.clear();
    	gpresMuVp.clear();
    	gpresMuVth.clear();
    	gpresMuVph.clear();
    	gthresMuVp.clear();
    	gthresMuVth.clear();
    	gthresMuVph.clear();
    	gphresMuVp.clear();
    	gphresMuVth.clear();
    	gphresMuVph.clear();
    }

    private void runAnalysis(){
    	int NpBins = Integer.parseInt(tf.get(0).getText());
    	double pMin = Double.parseDouble(tf.get(1).getText());
    	double pMax = Double.parseDouble(tf.get(2).getText());
    	int NthBins = Integer.parseInt(tf.get(3).getText());
    	double thMin = Double.parseDouble(tf.get(4).getText());
    	double thMax = Double.parseDouble(tf.get(5).getText());
    	int NphBins = Integer.parseInt(tf.get(6).getText());
    	double phMin = Double.parseDouble(tf.get(7).getText());
    	double phMax = Double.parseDouble(tf.get(8).getText());
    	
    	double pBW = (pMax - pMin)/NpBins;
    	double thBW = (thMax - thMin)/NthBins;
    	double phBW = (phMax - phMin)/NphBins;
    	
    	accumulateStats();

    	// calculate acceptances
    	for(int th = 0; th < NthBins; th++)
    	{
    		hpacc.add(new ArrayList<>());
    		hpaccGeometric.add(new ArrayList<>());
    		hpaccTracking.add(new ArrayList<>());
    		for(int ph = 0; ph < NphBins; ph++)
    		{
    			H1F hh = H1F.divide(hprec.get(th).get(ph), hpgen.get(th).get(ph));
    			H1F hhG = H1F.divide(hpgenSensitive.get(th).get(ph), hpgen.get(th).get(ph));
    			H1F hhT = H1F.divide(hpgenDetected.get(th).get(ph), hpgenSensitive.get(th).get(ph));
    			hpacc.get(th).add(hh);
    			hpacc.get(th).get(ph).setTitleX("p (GeV)");
    			hpaccGeometric.get(th).add(hhG);
    			hpaccTracking.get(th).add(hhT);
    			hpaccGeometric.get(th).get(ph).setLineColor(3);
    			hpaccGeometric.get(th).get(ph).setFillColor(50+3);
    			hpaccTracking.get(th).get(ph).setLineColor(7);
    			hpaccTracking.get(th).get(ph).setFillColor(50+7);
    		}
    	}
    	
    	for(int p = 0; p < NpBins; p++)
    	{
    		hthacc.add(new ArrayList<>());
    		hthaccGeometric.add(new ArrayList<>());
    		hthaccTracking.add(new ArrayList<>());
    		for(int ph = 0; ph < NphBins; ph++)
    		{
    			H1F hh = H1F.divide(hthrec.get(p).get(ph), hthgen.get(p).get(ph));
    			H1F hhG = H1F.divide(hthgenSensitive.get(p).get(ph), hthgen.get(p).get(ph));
    			H1F hhT = H1F.divide(hthgenDetected.get(p).get(ph), hthgenSensitive.get(p).get(ph));
    			hthacc.get(p).add(hh);
    			hthacc.get(p).get(ph).setTitleX("#theta (deg)");
    			hthaccGeometric.get(p).add(hhG);
    			hthaccTracking.get(p).add(hhT);
    			hthaccGeometric.get(p).get(ph).setLineColor(3);
    			hthaccGeometric.get(p).get(ph).setFillColor(50+3);
    			hthaccTracking.get(p).get(ph).setLineColor(7);
    			hthaccTracking.get(p).get(ph).setFillColor(50+7);
    		}
    	}
    	
    	for(int p = 0; p < NpBins; p++)
    	{
    		hphacc.add(new ArrayList<>());
    		hphaccGeometric.add(new ArrayList<>());
    		hphaccTracking.add(new ArrayList<>());
    		for(int th = 0; th < NthBins; th++)
    		{
    			H1F hh = H1F.divide(hphrec.get(p).get(th), hphgen.get(p).get(th));
    			H1F hhG = H1F.divide(hphgenSensitive.get(p).get(th), hphgen.get(p).get(th));
    			H1F hhT = H1F.divide(hphgenDetected.get(p).get(th), hphgenSensitive.get(p).get(th));
    			hphacc.get(p).add(hh);
    			hphacc.get(p).get(th).setTitleX("#phi (deg)");
    			hphaccGeometric.get(p).add(hhG);
    			hphaccTracking.get(p).add(hhT);
    			hphaccGeometric.get(p).get(th).setLineColor(3);
    			hphaccGeometric.get(p).get(th).setFillColor(50+3);
    			hphaccTracking.get(p).get(th).setLineColor(7);
    			hphaccTracking.get(p).get(th).setFillColor(50+7);
    		}
    	}
    	
    	// fit resolutions
    	for(int p = 0; p < NpBins; p++)
    	{
    		for(int th = 0; th < NthBins; th++)
    		{
    			for(int ph = 0; ph < NphBins; ph++)
    			{
    				double par0tol = 0.15; // tolerance from max bin content
    				double par1tol = 0.1; // tolerance from Mean (absolute... klunky since different units for p/angles, but close enough)
    				double par2tol = 0.99; // tolerance from RMS (RMS isn't very reliable, so big tolerance here)
    				if(hpres.get(p).get(th).get(ph).getEntries() > minEntriesForFit)
    				{
    					double maxBinContent = hpres.get(p).get(th).get(ph).getBinContent(hpres.get(p).get(th).get(ph).getMaximumBin());
    					ffp.get(p).get(th).get(ph).setParameter(0, maxBinContent);
    					ffp.get(p).get(th).get(ph).setParLimits(0, (1.0-par0tol)*maxBinContent, (1.0+par0tol)*maxBinContent);
    					ffp.get(p).get(th).get(ph).setParameter(1, hpres.get(p).get(th).get(ph).getMean());
    					ffp.get(p).get(th).get(ph).setParLimits(1, hpres.get(p).get(th).get(ph).getMean() - par1tol, hpres.get(p).get(th).get(ph).getMean() + par1tol);
    					ffp.get(p).get(th).get(ph).setParameter(2, 0.6*hpres.get(p).get(th).get(ph).getRMS()); // rms tends to be too large an estimate
    					ffp.get(p).get(th).get(ph).setParLimits(2, (1.0-par2tol)*hpres.get(p).get(th).get(ph).getRMS(), (1.0+par2tol)*hpres.get(p).get(th).get(ph).getRMS());
    					DataFitter.fit(ffp.get(p).get(th).get(ph), hpres.get(p).get(th).get(ph), "RNQ");
    					DataFitter.fit(ffp.get(p).get(th).get(ph), hpres.get(p).get(th).get(ph), "RNQ");
    					gDpOpVp.get(th).get(ph).addPoint(pMin + 0.5*pBW + p*pBW, ffp.get(p).get(th).get(ph).getParameter(2), 0.0, ffp.get(p).get(th).get(ph).parameter(2).error());
    					gDpOpVth.get(p).get(ph).addPoint(thMin + 0.5*thBW + th*thBW, ffp.get(p).get(th).get(ph).getParameter(2), 0.0, ffp.get(p).get(th).get(ph).parameter(2).error());
    					gDpOpVph.get(p).get(th).addPoint(phMin + 0.5*phBW + ph*phBW, ffp.get(p).get(th).get(ph).getParameter(2), 0.0, ffp.get(p).get(th).get(ph).parameter(2).error());
    					gpresMuVp.get(th).get(ph).addPoint(pMin + 0.5*pBW + p*pBW, ffp.get(p).get(th).get(ph).getParameter(1), 0.0, ffp.get(p).get(th).get(ph).parameter(1).error());
    					gpresMuVth.get(p).get(ph).addPoint(thMin + 0.5*thBW + th*thBW, ffp.get(p).get(th).get(ph).getParameter(1), 0.0, ffp.get(p).get(th).get(ph).parameter(1).error());
    					gpresMuVph.get(p).get(th).addPoint(phMin + 0.5*phBW + ph*phBW, ffp.get(p).get(th).get(ph).getParameter(1), 0.0, ffp.get(p).get(th).get(ph).parameter(1).error());
    				}
    				if(hthres.get(p).get(th).get(ph).getEntries() > minEntriesForFit)
    				{
    					double maxBinContent = hthres.get(p).get(th).get(ph).getBinContent(hthres.get(p).get(th).get(ph).getMaximumBin());
    					ffth.get(p).get(th).get(ph).setParameter(0, maxBinContent);
    					ffth.get(p).get(th).get(ph).setParLimits(0, (1.0-par0tol)*maxBinContent, (1.0+par0tol)*maxBinContent);
    					ffth.get(p).get(th).get(ph).setParameter(1, hthres.get(p).get(th).get(ph).getMean());
    					ffth.get(p).get(th).get(ph).setParLimits(1, hthres.get(p).get(th).get(ph).getMean() - par1tol, hthres.get(p).get(th).get(ph).getMean() + par1tol);
    					ffth.get(p).get(th).get(ph).setParameter(2, 0.6*hthres.get(p).get(th).get(ph).getRMS()); // rms tends to be too large an estimate
    					ffth.get(p).get(th).get(ph).setParLimits(2, (1.0-par2tol)*hthres.get(p).get(th).get(ph).getRMS(), (1.0+par2tol)*hthres.get(p).get(th).get(ph).getRMS());
    					DataFitter.fit(ffth.get(p).get(th).get(ph), hthres.get(p).get(th).get(ph), "RNQ");
    					DataFitter.fit(ffth.get(p).get(th).get(ph), hthres.get(p).get(th).get(ph), "RNQ");
    					gDthVp.get(th).get(ph).addPoint(pMin + 0.5*pBW + p*pBW, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).getParameter(2)), 0.0, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).parameter(2).error()));
    					gDthVth.get(p).get(ph).addPoint(thMin + 0.5*thBW + th*thBW, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).getParameter(2)), 0.0, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).parameter(2).error()));
    					gDthVph.get(p).get(th).addPoint(phMin + 0.5*phBW + ph*phBW, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).getParameter(2)), 0.0, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).parameter(2).error()));
    					gthresMuVp.get(th).get(ph).addPoint(pMin + 0.5*pBW + p*pBW, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).getParameter(1)), 0.0, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).parameter(1).error()));
    					gthresMuVth.get(p).get(ph).addPoint(thMin + 0.5*thBW + th*thBW, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).getParameter(1)), 0.0, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).parameter(1).error()));
    					gthresMuVph.get(p).get(th).addPoint(phMin + 0.5*phBW + ph*phBW, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).getParameter(1)), 0.0, 1000.0*Math.toRadians(ffth.get(p).get(th).get(ph).parameter(1).error()));
    				}
    				if(hphres.get(p).get(th).get(ph).getEntries() > minEntriesForFit)
    				{
    					double maxBinContent = hphres.get(p).get(th).get(ph).getBinContent(hphres.get(p).get(th).get(ph).getMaximumBin());
    					ffph.get(p).get(th).get(ph).setParameter(0, maxBinContent);
    					ffph.get(p).get(th).get(ph).setParLimits(0, (1.0-par0tol)*maxBinContent, (1.0+par0tol)*maxBinContent);
    					ffph.get(p).get(th).get(ph).setParameter(1, hphres.get(p).get(th).get(ph).getMean());
    					ffph.get(p).get(th).get(ph).setParLimits(1, hphres.get(p).get(th).get(ph).getMean() - par1tol, hphres.get(p).get(th).get(ph).getMean() + par2tol);
    					ffph.get(p).get(th).get(ph).setParameter(2, 0.6*hphres.get(p).get(th).get(ph).getRMS()); // rms tends to be too large an estimate
    					ffph.get(p).get(th).get(ph).setParLimits(2, (1.0-par2tol)*hphres.get(p).get(th).get(ph).getRMS(), (1.0+par2tol)*hphres.get(p).get(th).get(ph).getRMS());
    					DataFitter.fit(ffph.get(p).get(th).get(ph), hphres.get(p).get(th).get(ph), "RNQ");
    					DataFitter.fit(ffph.get(p).get(th).get(ph), hphres.get(p).get(th).get(ph), "RNQ");
    					gDphVp.get(th).get(ph).addPoint(pMin + 0.5*pBW + p*pBW, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).getParameter(2)), 0.0, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).parameter(2).error()));
    					gDphVth.get(p).get(ph).addPoint(thMin + 0.5*thBW + th*thBW, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).getParameter(2)), 0.0, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).parameter(2).error()));
    					gDphVph.get(p).get(th).addPoint(phMin + 0.5*phBW + ph*phBW, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).getParameter(2)), 0.0, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).parameter(2).error()));
    					gphresMuVp.get(th).get(ph).addPoint(pMin + 0.5*pBW + p*pBW, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).getParameter(1)), 0.0, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).parameter(1).error()));
    					gphresMuVth.get(p).get(ph).addPoint(thMin + 0.5*thBW + th*thBW, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).getParameter(1)), 0.0, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).parameter(1).error()));
    					gphresMuVph.get(p).get(th).addPoint(phMin + 0.5*phBW + ph*phBW, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).getParameter(1)), 0.0, 1000.0*Math.toRadians(ffph.get(p).get(th).get(ph).parameter(1).error()));
    				}
    			}
    		}
    	}
    }
    
    private void accumulateStats(){
     	int pidCode = cb_particles.getValue();
    	int NpBins = Integer.parseInt(tf.get(0).getText());
    	double pMin = Double.parseDouble(tf.get(1).getText());
    	double pMax = Double.parseDouble(tf.get(2).getText());
    	int NthBins = Integer.parseInt(tf.get(3).getText());
    	double thMin = Double.parseDouble(tf.get(4).getText());
    	double thMax = Double.parseDouble(tf.get(5).getText());
    	int NphBins = Integer.parseInt(tf.get(6).getText());
    	double phMin = Double.parseDouble(tf.get(7).getText());
    	double phMax = Double.parseDouble(tf.get(8).getText());
    	double Tscale = Double.parseDouble(tf.get(9).getText());
    	double Sscale = Double.parseDouble(tf.get(10).getText());
    	int Nevents = Integer.parseInt(tf.get(11).getText());
    	List<String> statuses = Arrays.asList(tf.get(12).getText().split("\\s*,\\s*"));
    	
    	// read in and analyze reconstructed GEMC file / set up fast MC
    	HipoDataSource reader = new HipoDataSource();
    	reader.open(file_TF.getText());
    	GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);

    	Clas12FastMC fastMC = new Clas12FastMC(Tscale, Sscale);
    	fastMC.setDebugMode(1);
    	Detector detDC = GeometryFactory.getDetector(DetectorType.DC);
    	Detector detEC = GeometryFactory.getDetector(DetectorType.EC);
    	//Detector detBST = GeometryFactory.getDetector(DetectorType.BST);
    	fastMC.addDetector("DC", detDC);
    	fastMC.addDetector("EC", detEC);
    	//fastMC.addDetector("BST", detBST);
    	//String[] dneg1 = {"DC", "EC"};
    	//int[] hneg1 = {36, 9};
    	String[] dneg1 = {"DC"};
    	int[] hneg1 = {36};
    	//String[] dneg2 = {"BST"};
    	//int[] hneg2 = {3};
    	//String[] dpos1 = {"DC", "EC"};
    	//int[] hpos1 = {36, 9};
    	String[] dpos1 = {"DC"};
    	int[] hpos1 = {36};
    	//String[] dpos2 = {"BST"};
    	//int[] hpos2 = {3};
    	String[] dneutral1 = {"EC"};
    	int[] hneutral1 = {9};
    	fastMC.addFilter(-1, dneg1, hneg1);
    	//fastMC.addFilter(-1, dneg2, hneg2);
    	fastMC.addFilter(1, dpos1, hpos1);
    	//fastMC.addFilter(1, dpos2, hpos2);
    	fastMC.addFilter(0, dneutral1, hneutral1);
    	
    	int eventCounter = 0;
    	while(reader.hasEvent() == true && eventCounter < Nevents)
    	{
    		eventCounter++;
    		DataEvent event = reader.getNextEvent();
    		PhysicsEvent genEvent = fitter.getGeneratedEvent(event);
    		RecEvent recEvent = fitter.getRecEvent(event);
    		recEvent.doPidMatch();
    		
    		// loop over generated particles
    		for(int iGenPart = 0; iGenPart < genEvent.countByPid(pidCode); iGenPart++)
    		{
    			Particle genPart = genEvent.getParticleByPid(pidCode, iGenPart);

    			double genp = genPart.p();
    			double genth = Math.toDegrees(genPart.theta());
    			double genph = Math.toDegrees(genPart.phi());
    			int genpBin = (int) Math.floor((NpBins*(genp - pMin))/(pMax - pMin)); // floor() rounds to -inf instead of 0
    			int genthBin = (int) Math.floor((NthBins*(genth - thMin))/(thMax - thMin));
    			int genphBin = (int) Math.floor((NphBins*(genph - phMin))/(phMax - phMin));

    			if(genpBin >= 0 && genpBin < NpBins && genthBin >= 0 && genthBin < NthBins && genphBin >= 0 && genphBin < NphBins)
    			{
    				hpgen.get(genthBin).get(genphBin).fill(genp);
    				hthgen.get(genpBin).get(genphBin).fill(genth);
    				hphgen.get(genpBin).get(genthBin).fill(genph);
    				
    				Particle fastRecPart;
    				if(fmc_CB.isSelected() == false) fastRecPart = new Particle(pidCode, 0, 0, 0, 0, 0, 0);
    				else
    				{
    					if(fastMC.checkParticle(genPart) == true) fastRecPart = genPart;
    					else fastRecPart = new Particle(pidCode, 0, 0, 0, 0, 0, 0);
    				}

    				if(fmc_CB.isSelected() == true && fastRecPart.p() > 0.000001)
    				{
    					hpgenSensitive.get(genthBin).get(genphBin).fill(genp);
    					hthgenSensitive.get(genpBin).get(genphBin).fill(genth);
    					hphgenSensitive.get(genpBin).get(genthBin).fill(genph);
    				}
    			}
    		}
    		
    		// loop over reconstructed particles
    		for(int iRecPart = 0; iRecPart < recEvent.getReconstructed().countByPid(pidCode); iRecPart++)
    		{
    			Particle recPart = recEvent.getReconstructed().getParticleByPid(pidCode, iRecPart);
    			double recp = recPart.p();
    			double recth = Math.toDegrees(recPart.theta());
    			double recph = Math.toDegrees(recPart.phi());
    			int recpBin = (int) Math.floor((NpBins*(recp - pMin))/(pMax - pMin)); // floor() rounds to -inf instead of 0
    			int recthBin = (int) Math.floor((NthBins*(recth - thMin))/(thMax - thMin));
    			int recphBin = (int) Math.floor((NphBins*(recph - phMin))/(phMax - phMin));
    			int recstatus = 100; // need actual status here !!!

    			boolean good_rec_particle = false;
    			if(recpBin >= 0 && recpBin < NpBins && recthBin >= 0 && recthBin < NthBins && recphBin >= 0 && recphBin < NphBins)
    			{
    				for(int k = 0; k < statuses.size(); k++)
    				{
    					if(recstatus == Integer.parseInt(statuses.get(k))) good_rec_particle = true;
    				}
    			}
    			
    			if(good_rec_particle == true)
    			{
    				hprec.get(recthBin).get(recphBin).fill(recp);
    				hthrec.get(recpBin).get(recphBin).fill(recth);
    				hphrec.get(recpBin).get(recthBin).fill(recph);
    			}
    			
    			// find the corresponding generated particle
    			boolean foundMatch = false; // for rare cases when there isn't a corresponding particle (mis-identified particles)
    			Particle correspondingGenPart = new Particle(pidCode, 0, 0, 0, 0, 0, 0);

    			if(genEvent.countByPid(pidCode) == 1)
    			{
    				foundMatch = true;
    				correspondingGenPart = genEvent.getParticleByPid(pidCode, 0);
    			}
    			else if(genEvent.countByPid(pidCode) > 1)
    			{
   					foundMatch = true;
    				correspondingGenPart = genEvent.getParticleByPid(pidCode, 0);
    				for(int iGenPart = 1; iGenPart < genEvent.countByPid(pidCode); iGenPart++)
    				{
    					if(recPart.euclideanDistance(genEvent.getParticleByPid(pidCode, iGenPart)) < recPart.euclideanDistance(correspondingGenPart)) correspondingGenPart = genEvent.getParticleByPid(pidCode, iGenPart);
    				}
    			}
    			
    			if(foundMatch == true)
    			{
    				double genp = correspondingGenPart.p();
    				double genth = Math.toDegrees(correspondingGenPart.theta());
    				double genph = Math.toDegrees(correspondingGenPart.phi());
    				int genpBin = (int) Math.floor((NpBins*(genp - pMin))/(pMax - pMin)); // floor() rounds to -inf instead of 0
    				int genthBin = (int) Math.floor((NthBins*(genth - thMin))/(thMax - thMin));
    				int genphBin = (int) Math.floor((NphBins*(genph - phMin))/(phMax - phMin));
    				
    				if(genpBin >= 0 && genpBin < NpBins && genthBin >= 0 && genthBin < NthBins && genphBin >= 0 && genphBin < NphBins)
    				{
    					hpres.get(genpBin).get(genthBin).get(genphBin).fill((genp - recp)/genp);
    					hthres.get(genpBin).get(genthBin).get(genphBin).fill(genth - recth);
    					hphres.get(genpBin).get(genthBin).get(genphBin).fill(genph - recph);

    					hpgenDetected.get(genthBin).get(genphBin).fill(genp);
    					hthgenDetected.get(genpBin).get(genphBin).fill(genth);
    					hphgenDetected.get(genpBin).get(genthBin).fill(genph);
    				}
    			}
    			
    		}
    		
    		if(eventCounter%1000 == 0) System.out.println("Analyzed " + eventCounter/1000 + " thousand events...");
    	}
    	
    }
   
    private void drawPlots(){
    	int NpBins = Integer.parseInt(tf.get(0).getText());
    	int NthBins = Integer.parseInt(tf.get(3).getText());
    	int NphBins = Integer.parseInt(tf.get(6).getText());

    	pCan.cd(0);
    	pCan.draw(hpgen.get(thBin).get(phBin));
    	pCan.draw(hpgenSensitive.get(thBin).get(phBin), "same");
    	pCan.draw(hpgenDetected.get(thBin).get(phBin), "same");
    	pCan.draw(hprec.get(thBin).get(phBin), "same");
    	pCan.cd(1);
    	pCan.draw(hpacc.get(thBin).get(phBin), "E");
    	pCan.draw(hpaccGeometric.get(thBin).get(phBin), "same");
    	pCan.draw(hpaccTracking.get(thBin).get(phBin), "same");
    	pCan.cd(2);
    	for(int p = 0; p < NpBins; p++)
    	{
    		if(p == 0) pCan.draw(hpres.get(p).get(thBin).get(phBin));
    		else pCan.draw(hpres.get(p).get(thBin).get(phBin), "same");
    		if(hpres.get(p).get(thBin).get(phBin).getEntries() > minEntriesForFit) pCan.draw(ffp.get(p).get(thBin).get(phBin), "same");
    	}
    	pCan.cd(3);
    	pCan.draw(gDpOpVp.get(thBin).get(phBin));
    	pCan.cd(4);
    	pCan.draw(gDthVp.get(thBin).get(phBin));
    	pCan.cd(5);
    	pCan.draw(gDphVp.get(thBin).get(phBin));
    	pCan.cd(6);
    	pCan.draw(gpresMuVp.get(thBin).get(phBin));
    	pCan.cd(7);
    	pCan.draw(gthresMuVp.get(thBin).get(phBin));
    	pCan.cd(8);
    	pCan.draw(gphresMuVp.get(thBin).get(phBin));
    	pCan.update();
    	
    	thCan.cd(0);
    	thCan.draw(hthgen.get(pBin).get(phBin));
    	thCan.draw(hthgenSensitive.get(pBin).get(phBin), "same");
    	thCan.draw(hthgenDetected.get(pBin).get(phBin), "same");
    	thCan.draw(hthrec.get(pBin).get(phBin), "same");
    	thCan.cd(1);
    	thCan.draw(hthacc.get(pBin).get(phBin));
    	thCan.draw(hthaccGeometric.get(pBin).get(phBin), "same");
    	thCan.draw(hthaccTracking.get(pBin).get(phBin), "same");
    	thCan.cd(2);
    	for(int th = 0; th < NthBins; th++)
    	{
    		if(th == 0) thCan.draw(hthres.get(pBin).get(th).get(phBin));
    		else thCan.draw(hthres.get(pBin).get(th).get(phBin), "same");
    		if(hthres.get(pBin).get(th).get(phBin).getEntries() > minEntriesForFit) thCan.draw(ffth.get(pBin).get(th).get(phBin), "same");
    	}
    	thCan.cd(3);
    	thCan.draw(gDpOpVth.get(pBin).get(phBin));
    	thCan.cd(4);
    	thCan.draw(gDthVth.get(pBin).get(phBin));
    	thCan.cd(5);
    	thCan.draw(gDphVth.get(pBin).get(phBin));
    	thCan.cd(6);
    	thCan.draw(gpresMuVth.get(pBin).get(phBin));
    	thCan.cd(7);
    	thCan.draw(gthresMuVth.get(pBin).get(phBin));
    	thCan.cd(8);
    	thCan.draw(gphresMuVth.get(pBin).get(phBin));
    	thCan.update();

    	phCan.cd(0);
    	phCan.draw(hphgen.get(pBin).get(thBin));
    	phCan.draw(hphgenSensitive.get(pBin).get(thBin), "same");
    	phCan.draw(hphgenDetected.get(pBin).get(thBin), "same");
    	phCan.draw(hphrec.get(pBin).get(thBin), "same");
    	phCan.cd(1);
    	phCan.draw(hphacc.get(pBin).get(thBin));
    	phCan.draw(hphaccGeometric.get(pBin).get(thBin), "same");
    	phCan.draw(hphaccTracking.get(pBin).get(thBin), "same");
    	phCan.cd(2);
    	for(int ph = 0; ph < NphBins; ph++)
    	{
    		if(ph == 0) phCan.draw(hphres.get(pBin).get(thBin).get(ph));
    		else phCan.draw(hphres.get(pBin).get(thBin).get(ph), "same");
    		if(hphres.get(pBin).get(thBin).get(ph).getEntries() > minEntriesForFit) phCan.draw(ffph.get(pBin).get(thBin).get(ph), "same");
    	}
    	phCan.cd(3);
    	phCan.draw(gDpOpVph.get(pBin).get(thBin));
    	phCan.cd(4);
    	phCan.draw(gDthVph.get(pBin).get(thBin));
    	phCan.cd(5);
    	phCan.draw(gDphVph.get(pBin).get(thBin));
    	phCan.cd(6);
    	phCan.draw(gpresMuVph.get(pBin).get(thBin));
    	phCan.cd(7);
    	phCan.draw(gthresMuVph.get(pBin).get(thBin));
    	phCan.cd(8);
    	phCan.draw(gphresMuVph.get(pBin).get(thBin));
    	phCan.update();
    	
    	//temp:
     	//int pidCode = cb_particles.getValue();
    	//F1D fff;
    	//if(pidCode == 11)
    	//{
    	//	fff = new F1D("fff", "sqrt([a]*x*x + [b]*((0.000511*0.000511 + x*x)/(x*x)))", 0.2, 11);
    	//	fff.setParameter(0, 0.0006*0.0006);
    	//	fff.setParameter(1, 0.002*0.002);
    	//}
    	//else
    	//{
    	//	fff = new F1D("fff", "sqrt([a]*x*x + [b]*((0.938*0.938 + x*x)/(x*x)))", 0.2, 11);
    	//	fff.setParameter(0, 0.0016*0.0016);
    	//	fff.setParameter(1, 0.0015*0.0015);
    	//}
    	//fff.setParLimits(0, -0.01, 0.01);
    	//fff.setParLimits(1, -0.01, 0.01);
		//DataFitter.fit(fff, gDpOpVp.get(0).get(0), "RE");
    	//pCan.cd(3);
    	//pCan.draw(fff, "same");
    	//pCan.update();
    	//System.out.println("sig1, sig2 = " + Math.sqrt(fff.getParameter(0)) + ", " + Math.sqrt(fff.getParameter(1)));
    }
    
	public static void main(String[] args) {
		launch(args);
	}

}
