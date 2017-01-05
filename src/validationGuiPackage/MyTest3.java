package validationGuiPackage;

import org.jlab.clas.physics.GenericKinematicFitter;
import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.PhysicsEvent;
import org.jlab.clas.physics.RecEvent;
import org.jlab.groot.data.H1F;
import org.jlab.groot.ui.TCanvas;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

public class MyTest3 {

	public static void main(String[] args) {

		int pidCode = 11;
		
		H1F hgenp = new H1F("hgenp", "hgenp", 100, 0, 10);
		H1F hrecp = new H1F("hgenp", "hgenp", 100, 0, 10);

    	HipoDataSource reader = new HipoDataSource();
    	reader.open("/Users/harrison/Desktop/CLAS12softwareValidation/forward_e_DST.340k.18oct16.hipo");
    	GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);

    	while(reader.hasEvent() == true)
    	{
    		DataEvent event = reader.getNextEvent();
    		PhysicsEvent genEvent = fitter.getGeneratedEvent(event);
    		RecEvent recEvent = fitter.getRecEvent(event);
    		recEvent.doPidMatch();
    		
    		// loop over generated particles
    		for(int iGenPart = 0; iGenPart < genEvent.countByPid(pidCode); iGenPart++)
    		{
    			Particle genPart = genEvent.getParticleByPid(pidCode, iGenPart);
    			hgenp.fill(genPart.p());
    		}
    		
    		// loop over reconstructed particles
    		for(int iRecPart = 0; iRecPart < recEvent.getReconstructed().countByPid(pidCode); iRecPart++)
    		{
    			Particle recPart = recEvent.getReconstructed().getParticleByPid(pidCode, iRecPart);
    			hrecp.fill(recPart.p());
    		}
    	}
    	
    	TCanvas can = new TCanvas("can", 700, 500);
    	can.divide(2, 1);
    	can.cd(0);
    	can.draw(hgenp);
    	can.cd(1);
    	can.draw(hrecp);

	}

}
