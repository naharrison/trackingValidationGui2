package validationGuiPackage;

import java.util.ArrayList;
import java.util.List;


import org.jlab.groot.data.H1F;
import org.jlab.groot.graphics.EmbeddedCanvas;

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
	
public class MyTest extends Application {
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

    H1F hh = new H1F("hh", "hh", 100, 0, 10);
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	pCan.divide(3, 2);
    	thCan.divide(3, 2);
    	phCan.divide(3, 2);

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
        file_TF = new TextField("/Users/harrison/tempCook2/eppippim/rec_gemcTest.1t9.hipo");
        grid.add(file_TF, 1, 0);

        fmc_CB = new CheckBox("pass through fastMC");
        fmc_CB.setSelected(true);
        grid.add(fmc_CB, 0, 1);

        cb_particles = new ComboBox<>();
        cb_particles.getItems().addAll(11, 2212, 211, -211, 13, -13, 22);
        cb_particles.setEditable(true);
        cb_particles.setValue(11);
        grid.add(new Label("PID: "), 0, 2);
        grid.add(cb_particles, 1, 2);
        
        String[] inputNames = {"No. of p bins", "p min (GeV)", "p max (GeV)", "No. of theta bins", "theta min (deg)", "theta max (deg)", "No. of phi bins", "phi min (deg)", "phi max (deg)", "Torus scale", "Solenoid scale", "No. of events", "statuses of interest"};
        String[] defaultInputs = {"2", "0.0", "12.0", "2", "0.0", "40.0", "1", "-180.0", "180.0", "-1.0", "1.0", "50000", "100, 200, 300, 400"};
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
    }

    private void clear_histo_lists(){
    }

    private void runAnalysis(){
    	accumulateStats();
    }
    
    private void accumulateStats(){
    	hh.setBinContent(13, 9);
    	hh.setBinContent(12, 16);
    	hh.setBinContent(11, 20);
    	hh.setBinContent(10, 15);
    	hh.setBinContent(9, 10);
    }
   
    private void drawPlots(){
    	pCan.cd(0);
    	pCan.draw(hh);

    	pCan.cd(1);
    	pCan.draw(hh);

    	pCan.update();
    }
    
	public static void main(String[] args) {
		launch(args);
	}

}
