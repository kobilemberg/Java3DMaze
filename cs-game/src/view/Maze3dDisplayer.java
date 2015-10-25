package view;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>Maze3dDisplayer</h1>
 * This class represent an instance off 3d MazeDisplayer canvas   
 * The class extends MazeDisplayer
 */



import java.util.Random;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class Maze3dDisplayer extends MazeDisplayer {

	public int characterX;
	public int characterY;
	public int exitX;
	public int exitY;
	public int exitFloor; 
	public int currentFloor;
	MazeBasicWindow mazeBasicWindow;

	private void paintCube(double[] p,double h,PaintEvent e){
        int[] f=new int[p.length];
        for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
        
        e.gc.drawPolygon(f);
        
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        

        int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
        e.gc.drawPolygon(b);
        int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
        e.gc.drawPolygon(fr);
        
        e.gc.fillPolygon(r);
		
	}
	/**
	 * {@inheritDoc}
	 */
	public Maze3dDisplayer(Composite parent, int style) {
		super(parent, style);
		
		final Color white=new Color(null, 255, 255, 255);
		final Color black=new Color(null, 0,0,0);
		//Image bGImage = new Image(getDisplay(), "Resources/wood-floor-texture.jpg");
		//setBackgroundImage(bGImage);
		Color[] colors = {
				new Color(null, 46, 204, 113),
				new Color(null, 52, 152, 219),
				new Color(null, 155, 89, 182),
				new Color(null, 230, 126, 34),
				new Color(null, 241, 196, 15)
		};
		
		Random rand = new Random();
		int r = rand.nextInt(colors.length);
		setBackground(colors[r]);
		
		addPaintListener(new PaintListener() {
		Image exitImage = new Image(getDisplay(), "Resources/Maze/Goal.png");
		Image winImage = new Image(getDisplay(), "Resources/Maze/Goal-Won.png");

			@Override
			synchronized public void paintControl(PaintEvent e) {
			   e.gc.setForeground(new Color(null,0,0,0));
			   e.gc.setBackground(new Color(null,0,0,0));

			   int width=getSize().x;
			   int height=getSize().y;
			   
			   int mx=width/2;
			   
			   if(mazeData!=null)
			   {
				   double w=(double)width/mazeData[0].length;
				   double h=(double)height/mazeData.length;

				   for(int i=0;i<mazeData.length;i++){
					   double w0=0.7*w +0.3*w*i/mazeData.length;
					   double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
					   double start=mx-w0*mazeData[i].length/2;
					   double start1=mx-w1*mazeData[i].length/2;
				       for(int j=0;j<mazeData[i].length;j++){
				          double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
				          double cheight=h/2;
				          if(mazeData[i][j]!=0)
				        	  paintCube(dpoints, cheight,e);
				          
				          
				          if(i==characterX && j==characterY && exitX==characterX &&exitY ==characterY&& exitFloor == currentFloor)
				          {
				        	  /* Draw Exit & Character at the same place. 
				        	  e.gc.setBackground(new Color(null,200,100,0));
				        	  e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
				        	  e.gc.setBackground(new Color(null,0,255,255));
				        	  e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
				        	  e.gc.setBackground(new Color(null,0,0,0)); */
				        	  e.gc.drawImage(winImage, (int)Math.round(dpoints[0]), (int)(Math.round(dpoints[1]-cheight/2-30)));

				          }
				          else
				          {
				        	  /* Draw character */ 
				        	  if(i==characterX && j==characterY){
								   e.gc.setBackground(new Color(null,200,0,0));
								   e.gc.fillOval((int)Math.round(dpoints[0]+8), (int)Math.round(dpoints[1]-cheight/2+10), (int)Math.round((w0+w1)/4), (int)Math.round(h/2));
								   e.gc.setBackground(new Color(null,255,0,0));
								   e.gc.fillOval((int)Math.round(dpoints[0]+10), (int)Math.round(dpoints[1]-cheight/2+12), (int)Math.round((w0+w1)/2/3), (int)Math.round(h/3));
								   e.gc.setBackground(new Color(null,0,0,0));
					          }
					          /* Draw Exit */ 
					          if(i==exitX && j==exitY && exitFloor == currentFloor){
					        	  e.gc.drawImage(exitImage, (int)Math.round(dpoints[0]), (int)(Math.round(dpoints[1]-cheight/2-30)));
					        	  
					        	  mazeBasicWindow.setWon(true);
					        	  
					        	  /*
					        	  e.gc.setBackground(new Color(null,0,255,255));
					        	  e.gc.setForeground(black);
					        	  e.gc.fillRectangle((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
					        	  e.gc.setBackground(new Color(null,0,100,200));
					        	  e.gc.fillRectangle((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
					        	  */
					        	  e.gc.setBackground(new Color(null,0,0,0));
					          }
				          }
				       }
				   }
			   }
			}
		});
	}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void moveCharacter(int x,int y){
		if(y>=0 && y<mazeData[0].length && x>=0 && x<mazeData.length && mazeData[x][y]==0){
			characterX=x;
			characterY=y;
			
			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					mazeBasicWindow.setMetaDataLabel(currentFloor, x, y, exitFloor, exitX, exitY);
					redraw();
				}
			});
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveForward() {
		int x=characterX;
		int y=characterY;
		x=x-1;
		moveCharacter(x, y);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveDown()
	 */
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void moveBackward() {
		int x=characterX;
		int y=characterY;
		x=x+1;
		moveCharacter(x, y);
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveLeft()
	 */
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void moveLeft() {
		int x=characterX;
		int y=characterY;
		y=y-1;
		moveCharacter(x, y);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void moveRight() {
		int x=characterX;
		int y=characterY;
		y=y+1;
		moveCharacter(x, y);
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void moveDown() {
		int x=characterX;
		int y=characterY;
		moveCharacter(x, y);
	}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void moveUp() {
		int x=characterX;
		int y=characterY;
		moveCharacter(x, y);
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setCharacterPosition(int row, int col) {
		characterX=row;
		characterY=col;
		moveCharacter(row,col);
	}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getCharacterX() {return characterX;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setCharacterX(int characterX) {this.characterX = characterX;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getCharacterY() {return characterY;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setCharacterY(int characterY) {this.characterY = characterY;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getExitX() {return exitX;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setExitX(int exitX) {this.exitX = exitX;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getExitY() {return exitY;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setExitY(int exitY) {this.exitY = exitY;}
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setExitFloor(int xPosition) {this.exitFloor = xPosition;}

	/**
	 * This method will return the current maze floor location
	 * @return int represent the current character floor position on 3dMazes
	 */
	public int getCurrentFloor() {return currentFloor;}
	
	/**
	 * This method will set the current maze floor location
	 * @param int currentFloor represent the current character floor position on 3dMazes
	 */
	public void setCurrentFloor(int currentFloor) {this.currentFloor = currentFloor;}
	/**
	 * This method will return the container widget of this canvas
	 * @return MazeBasicWindow represent the maze Basic Window represent the container widget
	 */
	public MazeBasicWindow getMazeBasicWindow() {return mazeBasicWindow;}
	/**
	 * This method will set the container widget of this canvas
	 * @param MazeBasicWindow represent the maze Basic Window represent the container widget
	 */
	public void setMazeBasicWindow(MazeBasicWindow mazeBasicWindow) {this.mazeBasicWindow = mazeBasicWindow;}
}
