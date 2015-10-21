package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class WelcomeDisplayer extends Canvas {
	WelcomeDisplayer(Composite parent, int style) {
		 
		super(parent, style);

		Image bGImage = new Image(getDisplay(), "Resources/welcome.png");

		addPaintListener(new PaintListener() 
		{
			@Override
			public void paintControl(PaintEvent e) 
			{
				e.gc.drawImage(bGImage, 0, 0,bGImage.getBounds().width,bGImage.getBounds().height,0,0, (int) (e.width), (int) (e.height));
			}
		});
					  
		getDisplay().syncExec(new Runnable() 
		{
			@Override
			public void run() {
				redraw();
			}
		});		   				
	}	
}
