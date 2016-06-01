package com.winnie.view.mainViewPart;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.winnie.interfaces.Controller;
import com.winnie.interfaces.MainBody;
import com.winnie.interfaces.MainPanel;
import com.winnie.interfaces.TablePanel;
import com.winnie.model.Node;
import com.winnie.model.Source;
import com.winnie.util.GetMessageUtil;
import com.winnie.view.MainBodyImpl;
import com.winnie.view.lineUtil.DrawArrowLine;
import com.winnie.view.menuutil.ItemPerform;
import com.winnie.view.menuutil.MyPopmenu;
/**
 * 主面板类,用于绘制图像
 * @author YuFeng
 * @date 2015年9月17日 下午2:40:30
 */
public class MainPanelImpl extends JScrollPane implements MainPanel{
	/**
	 * 连线
	 * @author YuFeng
	 * @date 2015年9月21日 下午9:51:15
	 */
	public class Line{
		public int x0,y0,x,y;//端点坐标
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 2373834079951800213L;
	/**
	 * 难点 1.获取容器大小
	 * 2.网格状布局
	 * 3.添加组件
	 * 4.记录组件
	 * 5.画线、记录线
	 * 6.拖动
	 */
	protected final int itemWidth=50;//组件宽度
	protected final int itemHeight=50;//组件高度
	protected int width;
	protected int height;
	protected GNode [][] used;
	protected Node current = null;
	protected boolean settled =true;
	protected TablePanel tablePanel = null;
	protected MainBody mainBody = null;
	protected GNode curItem = null; //当前元素，用于添加元素
	protected GNode from = null;//起点，用于连线
	
	private boolean flag = false; 
	protected int sx ,sy ,ex,ey;//用来记录带箭头的直线
	
	protected int initCpontCount = 0;
	/**
	 * 单独处理画线部分的类，用于代码分割，便于管理
	 */
	private LinePart linePart = new LinePart();
	/**
	 * 记录节点信息
	 */
	protected ArrayList<GNode> items =new ArrayList<GNode>();
	/**
	 * 记录邻接边信息
	 */
	protected ArrayList<Edge> edges = new ArrayList<Edge>();
	/**
	 * 右键菜单
	 */
	protected MyPopmenu menu;
	/**
	 * 
	 * @param width 容器的宽
	 * @param height 容器的高
	 */
	public MainPanelImpl(int width,int height,TablePanel table,MainBody mainBody) {
		this.tablePanel = table;
		this.mainBody = mainBody;
		setLayout(null);
		addPopmenu();
		setPreferredSize(new Dimension(width, height));
		used=new GNode[width/itemWidth][height/itemHeight];
		this.width=width;
		this.height=height;
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(from!=null){
					doRelease(e);
				}
				if(settled&&flag){
					curItem = null;
					current = null;
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(getComponentCount());
				if(e.getButton() == MouseEvent.BUTTON1){
					Point temp = calLocation(e.getPoint());
					from=used[(int) temp.getX()][(int) temp.getY()];
				}else{
					Point temp = calLocation(e.getPoint());
					curItem=used[(int) temp.getX()][(int) temp.getY()];
					flag = true;
				}
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(current!=null){
					System.out.println("as"+getComponentCount());
					parentRemove(getComponentCount()-1);
					System.out.println("as"+getComponentCount());
					update(getGraphics());
				}
				current = null;
				curItem = null;
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				doMouseEnter();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				switch (e.getButton()) {
					case MouseEvent.BUTTON1:
						doAdd(e.getPoint());
					break;
					
					case MouseEvent.BUTTON3:
						if(!settled){
							doAdd(e.getPoint());
							parentRemove(getComponentCount()-2);
							return;
						}
						flag = true; 
						doRightButton(e);
					default:
						System.out.println(getComponentCount());//其他情况输出
					break;
				}
				
//				if(e.getButton()==e.BUTTON3){
//					测试获得数据，测试成功
//					int[][] result = getGraph();
//					for (int i = 0; i < result.length; i++) {
//						for (int j = 0; j < result.length; j++) {
//							System.out.print(result[i][j]+",");
//						}
//						System.out.println();
//					}
//					测试移除Item
//					GNode g = getNodeUnderMouse(e.getPoint());
//					System.out.println(g);
//					if(g!=null){
//						remove(g.getComponent());
//					}
//				}
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {			
				doMove(e.getPoint());
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if(curItem==null){
					if(from != null){
						doDrawLine(e.getPoint());
					}
				}else{
//					System.out.println("asdf");
					if(flag){
						doRightDrag(e.getPoint());
					}
				}
			}
		});
		initCpontCount = getComponentCount();
//		this.add
	}
	public void doDrawLine(Point e){
		repaint();
		if(from == null){
			return ;
		}
		if(e==null){
			return ;
		}
		drawLine(from, e);
	}
	/**
	 * 添加组件
	 * @param label
	 * @param i
	 * @param j
	 * @author YuFeng   
	 * @date 2015年9月25日 下午8:45:04
	 */
	public void addLabel(GNode label,int i , int j,float alpha){
		if(used[i][j]==null){
//			add(label.getComponent());//默认已经添加
			label.paint(i*itemWidth, j*itemHeight, alpha);
			repaint();
		}
	}
	/**
	 * 
	 * @param src 鼠标点击位置
	 * @author YuFeng   
	 * @date 2015年9月25日 下午9:46:32
	 */
	public void doMove(Point src){
		if(current!=null){
			Point aim = calLocation(src);
			addLabel(curItem, (int)aim.getX(), (int)aim.getY(), 0.5f);
		}
	}
	public void doRightDrag(Point src){
		
		if(curItem!=null&&from==null){
			
			Point aim = calLocation(src);
			if(used[(int)aim.getX()][ (int)aim.getY()]==null){
				try{
//					System.out.println(aim);
					Point bf = search(curItem);
					addLabel(curItem, (int)aim.getX(), (int)aim.getY(), 1f);
					used[(int)aim.getX()][ (int)aim.getY()]=curItem;
					used[(int)bf.getX()][ (int)bf.getY()] = null;
					update(getGraphics());
				}catch(Exception e){
					
				}
			}
			
		}
	}
	/**
	 * 添加节点时做的动作
	 * @param src
	 * @author YuFeng   
	 * @date 2015年9月25日 下午10:03:53
	 */
	public void doAdd(Point src){
		Point aim = calLocation(src);
		if(used[(int) aim.getX()][(int) aim.getY()]!=null){
			return ;
		}
		if(current!=null){
			if(curItem==null){
				curItem = new GNode(current);
				add(curItem.getComponent());
			}
			addLabel(curItem, (int)aim.getX(), (int)aim.getY(), 1f);
			//添加到当前节点队列
//			GNode g = new GNode(current);
			items.add(curItem);
			curItem.newIndex = items.size()-1;
			used[(int) aim.getX()][(int) aim.getY()]=curItem;
			System.out.println(search(curItem));
			//收尾
//			tablePanel.setSettled(current);
			current = null;
			curItem = null;
			tablePanel.setSettled();
			settled = true;
		}
	}
	/**
	 * 右键删除，
	 * 
	 * @author YuFeng   
	 * @date 2015年9月25日 下午9:42:08
	 */
	public void delete(){
		
	}
	/**
	 * 计算一个点的位置
	 * @param aim
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月21日 下午9:54:08
	 */
	public Point calLocation(Point aim){
		int x =(int) (aim.getX()/itemWidth);
		int y =(int) (aim.getY()/itemHeight);
		Point result =new Point(x,y);
		return result;
	}
	public static void main(String[] args) {
		JFrame f =new JFrame();
		f.add(new MainPanelImpl(500, 500,null,new MainBodyImpl(null)));
//		f.setLayout(null);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Button());
		f.add(new Button());
		f.add(new Button());
		f.add(new Button());
		f.setVisible(true);
	}
	@Override
	public int[][] getGraph() {
		Map<GNode, Integer> index = new HashMap<GNode, Integer>();
		for (int i =0;i<items.size();i++) {
			index.put(items.get(i),i);
		}
		int[][] result = new int[items.size()][items.size()];
		for (int i = 0; i < result.length; i++) {
			List<Edge> edges = items.get(i).edges;
			for(Edge e:edges){
				result[i][index.get(e.to)]=e.num;
			}
		}
		return result;
	}
	@Override
	public List<Node> getList() {
		List<Node> result = new ArrayList<Node>();
		for(GNode item : items){
			result.add(item.node);
		}
		return result;
	}
	@Override
	public void showDialog(String message, int type) {
		
	}
	@Override
	public boolean isSettled() {
		return settled;
	}
	public TablePanel getTablePanel() {
		return tablePanel;
	}
	public void setTablePanel(TablePanel tablePanel) {
		this.tablePanel = tablePanel;
	}
	/**
	 * 查找GNode在界面中的位置
	 * @param g
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月26日 下午3:26:58
	 */
	public Point search(GNode g){
		for (int i = 0; i < used.length; i++) {
			for (int j = 0; j < used[i].length; j++) {
				if(used[i][j]==g){
					return new Point(i,j);
				}
			}
		}
		return null;
	}
	/**
	 * 画带有箭头的直线
	 * @param from 起点
	 * @param to
	 * @author YuFeng   
	 * @date 2015年9月26日 下午4:15:39
	 */
	public void drawLine(GNode from,Point to){
		Point src = search(from);
		if(src==null){
			return ;
		}
		ex = (int) to.getX();
		ey = (int) to.getY();
		src.setLocation(src.getX()*itemWidth, src.getY()*itemHeight);
		sy = (int) (src.getY()+itemHeight/2);
		if(src.getX()<to.getX()){
//			DrawArrowLine.drawAL(src.getX()+itemWidth, src.getY()+itemHeight/2, to.getX(), to.getY(), (Graphics2D)getGraphics());
			sx = (int) (src.getX()+itemWidth);
		}else{
//			DrawArrowLine.drawAL(src.getX(), src.getY()+itemHeight/2, to.getX(), to.getY(), (Graphics2D)getGraphics());
			sx = (int) src.getX();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		DrawArrowLine.drawAL(sx, sy, ex, ey, (Graphics2D)g);
		for(GNode from :items){
			for(Edge to : from.edges){
				linePart.drawEdge(from, to.to ,g,to.num);
			}	
		}
	}
	/**
	 * 鼠标释放后执行的函数，用于添加边
	 * @param e
	 * @author YuFeng   
	 * @date 2015年10月4日 下午11:10:16
	 */
	private void doRelease(MouseEvent e) {
		linePart.addEdge(e);
	}
	public class LinePart{
		/**
		 * 添加边
		 * @param e
		 * @author YuFeng   
		 * @date 2015年10月4日 下午11:10:55
		 */
		public  void addEdge(MouseEvent e) {
			Point temp = calLocation(e.getPoint());
			GNode to=used[(int) temp.getX()][(int) temp.getY()];
			int num;
//			System.out.println("_____________"+curItem);
			if(curItem!=null){
				clearArrawLine();
				return ;
			}
			try {
				if(to==null){
					clearArrawLine();
					return ;
				}else{
					if(from.node.getType() == to.node.getType()||from==to||to.contains(from)){
						clearArrawLine();
						return ;
					}else{
						if(from.node.getType()==1){
							num = applyForResource(to);
							if(num==-1){
								num = 0;
								return ;
							}
						}else{
							num = delieveResource(to);
							if(num==-1){
								num = 0;
								return ;
							}
						}
						items.get(((GNode)from).newIndex).add(new Edge(to,num));
						
//						System.out.println(items.get(((GNode)from).newIndex).edges.size());
					}
				}
			} catch (Exception e1) {
//				e1.printStackTrace();
				System.out.println("添加边出现错误");
			}
			clearArrawLine();
		}
		protected int delieveResource(GNode to) throws Exception {
			int num;
			Source s;
			num = GetMessageUtil.getInteger("请输入分配资源数目：");
			s = (Source)from.node;
			int  all = s.getRemains();
			if(num>all){
				JOptionPane.showMessageDialog(null, "__分配数目超过资源剩余数目上限!");
				clearArrawLine();
				return -1;
			}
			if(!from.contains(to)){
				if(num<=0){
					JOptionPane.showMessageDialog(null, "请输入正数!");
					clearArrawLine();
					return -1;
				}
			}else{
				if((-num)>from.getNum(to)){
					JOptionPane.showMessageDialog(null, "删除数目过多！");
					clearArrawLine();
					return -1;
				}else{
					from.update(to, num);
					s.setUsed(s.getUsed()+num);
					tablePanel.setUsed(s, num);
					clearArrawLine();
					return -1;
				}
			}
			s.setUsed(num);
			tablePanel.setUsed(s, num);
			return num;
		}
		protected int applyForResource(GNode to) throws Exception {
			int num;
			Source s;
			num = GetMessageUtil.getInteger("请输入申请资源数目：");
			s = (Source)to.node;
			int  all = s.getNums();
			
			if(from.contains(to)){
				if(num<=0){
					if((-num)>from.getNum(to)){
						JOptionPane.showMessageDialog(null, "删除数量过多！");
						clearArrawLine();
						return -1;
					}else{
						from.update(to, num);
						clearArrawLine();
						return -1;
					}
				}else{
					if(num+from.getNum(to)>all){
						JOptionPane.showMessageDialog(null, "申请数目超过资源数目上限!");
						clearArrawLine();
						return -1;
					}else{
						from.update(to, num);
						clearArrawLine();
						return -1;
					}
				}
			}else{
				if(num<=0){
					if(!from.contains(to)){//第一次申请不能为负数
						JOptionPane.showMessageDialog(null, "请输入正数!");
						clearArrawLine();
						return -1;
					}else{
					}
				}
				if(num>all){
					JOptionPane.showMessageDialog(null, "申请数目超过资源数目上限!");
					clearArrawLine();
					return -1;
				}else{
				}
			}
			return num;
		}
		/**
		 * 清除划线
		 * 
		 * @author YuFeng   
		 * @date 2015年10月11日 下午12:37:58
		 */
		private void clearArrawLine(){
			from =null;
			sx = 0 ; 
			sy = 0 ;
			ex = 0 ;
			ey = 0 ;
			repaint();
		}
		/**
		 * 画带有箭头的边
		 * @param from
		 * @param to
		 * @param g
		 * @author YuFeng   
		 * @date 2015年10月4日 下午11:15:19
		 */
		public void drawEdge(GNode from , GNode to ,Graphics g){
			Point fromLoc = search(from);
			Point toLoc = search(to);
			int sx=0,sy=0,ex=0,ey=0;
			sy = (int) fromLoc.getY();
			ey = (int) toLoc.getY();
			/**
			 * 分情况画线
			 */
			if(fromLoc.getX()!=toLoc.getX()){
				if(fromLoc.getX()>toLoc.getX()){
					sx = (int) (fromLoc.getX());
					ex = (int) toLoc.getX()+1;
				}else{
					
					sx = (int) (fromLoc.getX()+1);
					ex = (int) toLoc.getX();
				}
				sx = sx*itemWidth;
				sy =(int) (((double)sy+0.5)*itemHeight);
				ex =ex*itemWidth;
				ey =(int) (((double)ey + 0.5)*itemHeight);
				
			}else{
				if(sy > ey){
					ey++;
				}else{
					sy++;
				}
				sx=(int) ((fromLoc.getX()+0.5)*itemWidth);
				sy=(int) (((double)sy)*itemHeight);
				ex= (int) ((toLoc.getX()+0.5)*itemWidth);
				ey= (int) (((double)ey)*itemHeight);
			}
			DrawArrowLine.drawAL(sx, sy , ex , ey , (Graphics2D)g);
//			System.out.println(sx +"|"+ sy+" "+ex+"|"+ey);
//			g.drawString(, x, y);
			
		}
		public void drawEdge(GNode from , GNode to ,Graphics g,int num){
			Point fromLoc = search(from);
			Point toLoc = search(to);
			if(fromLoc == null || toLoc == null){
				return ;
			}
			int sx=0,sy=0,ex=0,ey=0;
			sy = (int) fromLoc.getY();
			ey = (int) toLoc.getY();
			/**
			 * 分情况画线
			 */
			if(fromLoc.getX()!=toLoc.getX()){
				if(Math.abs(fromLoc.getX() - toLoc.getX()) == 1){
					if(fromLoc.getY()>toLoc.getY()){
						sy = (int) fromLoc.getY();
						ey = (int) toLoc.getY()+1;
					}else{
						sy = (int) fromLoc.getY()+1;
						ey = (int) toLoc.getY();
					}
					sx = (int) (((double)fromLoc.getX()+0.5)*itemWidth);
					sy = sy * itemHeight;
					ex = (int) (((double)toLoc.getX()+0.5)*itemWidth);
					ey = ey * itemHeight;
				}else{
					if(fromLoc.getX()>toLoc.getX()){
						sx = (int) (fromLoc.getX());
						ex = (int) toLoc.getX()+1;
					}else{
					
						sx = (int) (fromLoc.getX()+1);
						ex = (int) toLoc.getX();
					}
					sx = sx*itemWidth;
					sy =(int) (((double)sy+0.5)*itemHeight);
					ex =ex*itemWidth;
					ey =(int) (((double)ey + 0.5)*itemHeight);
				}
			}else{
				if(sy > ey){
					ey++;
				}else{
					sy++;
				}
				sx=(int) ((fromLoc.getX()+0.5)*itemWidth);
				sy=(int) (((double)sy)*itemHeight);
				ex= (int) ((toLoc.getX()+0.5)*itemWidth);
				ey= (int) (((double)ey)*itemHeight);
			}
//			System.out.println("("+sx+","+sy+").("+ex+","+ey+")");
			DrawArrowLine.drawAL(sx, sy , ex , ey , (Graphics2D)g);
//			System.out.println(sx +"|"+ sy+" "+ex+"|"+ey);
			g.drawString(num+"", (sx+ex)/2, (sy+ey)/2);
			
		}
//		public void drawEdge(GNode from , GNode to ,Graphics g,int num){
//			if(from==null||to == null){
//				return ;
//			}
//			Point fromLoc = search(from);
//			Point toLoc = search(to);
//			if(fromLoc==null||toLoc==null){
//				return;
//			}
//			int sx=0,sy=0,ex=0,ey=0;
//			sy = (int) fromLoc.getY();
//			ey = (int) toLoc.getY();
//			/**
//			 * 分情况画线
//			 */
//			if(fromLoc.getX()!=toLoc.getX()){
//				if(fromLoc.getX()>toLoc.getX()){
//					sx = (int) (fromLoc.getX());
//					ex = (int) toLoc.getX()+1;
//				}else{
//					sx = (int) (fromLoc.getX()+1);
//					ex = (int) toLoc.getX();
//				}
//				sx = sx*itemWidth;
//				sy =(int) (((double)sy+0.5)*itemHeight);
//				ex =ex*itemWidth;
//				ey =(int) (((double)ey + 0.5)*itemHeight);
//				
//			}else{
//				if(sy > ey){
//					ey++;
//				}else{
//					sy++;
//				}
//				sx=(int) ((fromLoc.getX()+0.5)*itemWidth);
//				sy=(int) (((double)sy)*itemHeight);
//				ex= (int) ((toLoc.getX()+0.5)*itemWidth);
//				ey= (int) (((double)ey)*itemHeight);
//			}
//			DrawArrowLine.drawAL(sx, sy , ex , ey , (Graphics2D)g);
////			System.out.println(sx +"|"+ sy+" "+ex+"|"+ey);
//			g.drawString(num+"", (sx+ex)/2, (sy+ey)/2);
//			
//		}
	}
	
	public List<Node> getNodes(){
		List<Node> result= new ArrayList<Node>();
		for(GNode node:items){
			result.add(node.node);
		}
		return result;
	}
	/**
	 * 获得该位置下的节点
	 * @param e
	 * @return
	 * @author YuFeng   
	 * @date 2015年10月10日 下午3:40:58
	 */
	public GNode getNodeUnderMouse(Point e){
		Point aim = calLocation(e);
		return used[(int) aim.getX()][(int) aim.getY()];
	}
	@Override
	public void remove(int index){
		if(index<0||index>items.size()){return ;}
		GNode node = items.get(index);
		Point e = search(node);
		removeNodeFromItems(node);
		used[(int) e.getX()][(int) e.getY()] =null;
		items.remove(index);
		super.remove(3+index);//为什么是3？
		update(getGraphics());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(getComponentCount());
	}
	public void parentRemove(int index){
		super.remove(index);
		update(getGraphics());
	}
	public void removeNodeFromItems(GNode to){
		for (GNode from : items) {
			remove(from,to);
		}
		while(to.edges.size()>0){
			remove(to, to.edges.get(0).to);
		}
	}
	protected void remove(GNode from , GNode to){
		if(from.contains(to)){
			if(from.node.getType()==2){
				tablePanel.setUsed(from.node,-from.getNum(to));
			}
			from.remove(to);
		}
	}
	public void restart(){
		items.clear();
		for (int i = 0; i < used.length; i++) {
			for (int j = 0; j < used[i].length; j++) {
				used[i][j] = null;
			}
		}
		mainBody.renew();
	}
	/**
	 * 右键事件
	 * 
	 * @author YuFeng   
	 * @date 2015年10月10日 下午9:55:32
	 */
	private void doRightButton(MouseEvent e) {
		menu.action(e);
//		try{
//			while(getComponentCount()>initCpontCount){
//				remove(0);
////				update(getGraphics());
////				Thread.sleep(500);
//			}
//			System.out.println(getComponentCount());
//		}catch(Exception e1){
//			System.out.println(e1.getMessage());
//		}
		
	}
	/**
	 * 鼠标进入事件
	 * @author YuFeng   
	 * @date 2015年10月10日 下午9:55:51
	 */
	private void doMouseEnter() {
		if(tablePanel == null){
//			System.out.println("asdf");
			return ;
		}
		current = tablePanel.getCurrentItem();
		if(current!=null&&curItem==null){
			curItem = new GNode(current);
			add(curItem.getComponent());
			settled = false;
		}
//		if(current==null){
//			current = new Node(1,"asfd");
//		}
	}
	
	protected void addPopmenu(){
		menu = new MyPopmenu(this);
		menu.addItem("约简", new ItemPerform() {
			@Override
			public void perform(ActionEvent e) {
				mainBody.showMessage("开始约简", Controller.C_NORMAL);
				mainBody.doIt();
				mainBody.showDialog("约简结束", Controller.INFO);
//				mainBody.renew();
			}
		});
		menu.addItem("复原", new ItemPerform() {
			@Override
			public void perform(ActionEvent e) {
				while(getComponentCount()>3){
					try{
						remove(0);
					}catch(Exception e1){
						parentRemove(3);
					}
				}
				while(getComponentCount()>3){
					parentRemove(3);
				}
				tablePanel.renew();
				restart();
				flag = false;
			}
		});
		this.add(menu.getMenu());
	}
	public void deleteNode(Node node){
		if(node == null){
			return ;
		}
		int aim = -1;
		for(int i=0;i<items.size();i++){
			GNode g = items.get(i);
			if(g.node.getType()==node.getType()&&g.node.getIndex() == node.getIndex()){
				aim = i;
				break;
			}
		}
		if(aim>=0){
			System.out.println("qwer");
			remove(aim);
		}
	}
	/**
	 * 更新节点信息
	 * @param node
	 * @author YuFeng   
	 * @date 2015年10月12日 下午4:08:59
	 */
	public void updateNode(Node node){
		if(node == null){
			return ;
		}
		for(int i=0;i<items.size();i++){
			GNode g = items.get(i);
			if(g.node.getType()==node.getType()&&g.node.getIndex() == node.getIndex()){
				g.setContent(node.getName());
				if(node.getType()==2){
					Source s = (Source) g.node;
					s.setNums(((Source)node).getNums());
				}
				break;
			}
		}
	}
	@Override
	public Component getItem() {
		return this;
	}
}


