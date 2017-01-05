package validationGuiPackage;

import org.jlab.geom.prim.Line3D;
import org.jlab.geom.prim.Path3D;
import org.jlab.geom.prim.Point3D;
import org.jlab.geom.prim.Vector3D;

public class MyTest2 {

	public static void main(String[] args) {
		double x = 0.0;
		double y = 0.0;
		double z = 0.0;
		double ux = 113.39691809523171;
		double uy = -391.6069057931654;
		double uz = -59.13757667298579;

   		Line3D lineX = new Line3D(new Point3D(24.594874198842717, 10.187530458020763, -46.0), new Point3D(24.594874198842717, 10.187530458020763, 46.0));
   		Path3D path = new Path3D();
   		path.generate(new Point3D(x, y, z), new Vector3D(ux, uy, uz), 1500.0, 2);
   		
   		Line3D intersect = path.distance(lineX);
   		Point3D intP = intersect.end();
   		
   		System.out.println(intP.toString());
	}

}
