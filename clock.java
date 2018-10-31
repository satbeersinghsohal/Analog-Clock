import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


class clockframe{
	JFrame base;
	drawbg m;
	// JPanel p;
	static int hour,minute,second;
	clockframe(int x,int y,int radius){
		base = new JFrame();
		gettime();

		m = new drawbg(x,y,radius,hour,minute,second);
		m.setVisible(true);
		
		base.setVisible(true);
		base.setSize(x,y);
		base.add(m);
		base.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				
				kill();
				System.gc();
				System.exit(0);
			}
		});
	}
	void kill(){
		m.setVisible(false);
		m = null;
		base.setVisible(false);
		base = null;
		// base.close();
	}
	static void gettime(){
		Date b = new Date();
		String a= b.toString(); 
		hour   = Integer.parseInt(a.substring(11,13));
		minute = Integer.parseInt(a.substring(14,16));
		second = Integer.parseInt(a.substring(17,19));
	}
} 

class drawbg extends JPanel{
	double X,Y,x,y;
	int minute;
	int hour;
	int second;
	int rh,rm,rs;
	int x1[],y1[];
	int r;
	Thread a;
	drawbg(int X,int Y,int r,int h,int m,int s){
		this.X = X/2;
		this.Y = Y/2;
		this.r = r;
		minute = m;
		if(h >12){
			h = h-12;
		}
		hour = h;
		second = s;
		rm = r;
		rs = (int)(r*0.8);
		rh = (int)(r*0.6);
		x1 = new int[4];
		y1 = new int[4];

		a = new Thread(){
			public void run(){
				while(true){
					try{
						Thread.sleep(1000);
					}
					catch(Exception e){System.exit(0);}
					second +=1;
					minute += second/60;
					second = second%60;
					hour += minute/60; 
					minute = minute%60;
					// System.out.println("d");
					repaint();
				}
			}
		};
		a.start();


	}

	private double findxoncircle(int r,double angle){
		double x = Math.sin(angle)*r;
		return x;
	}
	private double findyoncircle(int r,double angle){
		double y = Math.cos(angle)*r;
		return y;	
	}

	private void calculatepoints(int r,double angle){
		this.x1[0] = (int)findxoncircle(r,angle)+(int)X;
		this.y1[0] = -(int)findyoncircle(r,angle)+(int)Y;
		this.x1[1] = (int)findxoncircle((int)r/5,Math.PI+angle)+(int)X;
		this.y1[1] = -(int)findyoncircle((int)r/5,Math.PI+angle)+(int)Y;
			
	}

	public void paintComponent(Graphics g){
		// g.setColor(Color.WHITE);
		g.setFont(new Font("serif",Font.BOLD,20));
		g.setColor(Color.GRAY);
		g.fillOval((int)X-r-25, (int)Y-r-25, (int)2*r+50, (int)2*r+50);
		g.setColor(Color.WHITE);
		g.fillOval((int)X-r-15, (int)Y-r-15, (int)2*r+30, (int)2*r+30);
		g.setColor(Color.BLACK);
		g.fillOval(200,200,2,2);
		for(int i=1;i<=12;i++)
		{
			double angle = Math.PI/6*i;
			x = findxoncircle(r,angle);
			y = findyoncircle(r,angle);
			if(i <10){
				g.drawString(""+i,(int)(X+x-5),(int)(Y-y+8));
			}else{
				g.drawString(""+i,(int)(X+x-10),(int)(Y-y+8));
			}
			
		}
		double sangle = Math.PI/30*second;
		double mangle = Math.PI/30*minute;
		double hangle = Math.PI/6*hour+mangle/12;
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		calculatepoints(rm,mangle);
		// g.fillPolygon(x1,y1,4);
		g2.drawLine(x1[0], y1[0],x1[1], y1[1]);

		calculatepoints(rh,hangle);
		g2.drawLine(x1[0], y1[0],x1[1], y1[1]);
		g2.setColor(Color.RED);
		calculatepoints(rs,sangle);
		g2.drawLine(x1[0], y1[0],x1[1], y1[1]);
		
		// g.fillPolygon(x1,y1,4);
	}
}


public class clock{
	public static void main(String[] args) {
	
		clockframe a = new clockframe(400,400,150);
		a = null;
	}
} 