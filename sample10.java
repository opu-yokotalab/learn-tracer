import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.awt.Graphics;
import java.util.regex.*;


public class sample10 extends Applet{
    Button myBtn,youBtn,heBtn;
    Label lbl_ore;
    TextField myTxt;
    Panel myPnl;

    String[] source = new String[300];
    String line,str;

    int[] lin = new int[300];
    int[] endn = new int [300];
    String[][] gdbout = new String[300][20];

    int fg=0;
    int h,l,end;
    int i=0,j=0,k=0;
    int[] sp = new int[100];

    int tx; //テキストの表示位置　Ｘ座標
    int ty; //テキストの表示位置　Ｙ座標
    int tx2;
    int ty2;

    int size =20;

        public void loads(){
	    j=0;
		try {

		    InputStream is = new URL(getCodeBase(), "sort.c").openStream();
		    InputStreamReader myin = new InputStreamReader( is );
		    BufferedReader br = new BufferedReader(myin);
		    while((line = br.readLine()) != null){

			//if(line.length() == 0){
			//    sp[k]=j;
			//    k++;
			//}
			//else {
			    source[j] = line;
			    j++;
			//}
		    }
		    br.close();
		    myin.close();
		    is.close();
		} catch (IOException e){
		    add(lbl_ore = new Label("入力エラー"));
		}
	}


    public void loadout(){//gdbの出力結果の読み込み
	int frag =0;
	int in = 0;

	try {

	    InputStream is = new URL(getCodeBase(), "sample6.txt").openStream();
	    InputStreamReader myin = new InputStreamReader( is );
	    BufferedReader br = new BufferedReader(myin);
	    while((line = br.readLine()) != null){

		if(line.length() == 0){
		    sp[k]=j;
		    k++;
		}
		else {
		    //Pattern pattern = Pattern.compile("^[1-9]+:.*");//変数マッチング
		    //Pattern pattern = Pattern.compile(".*.c:[1-9]+$");//変数呼び出し・BreakPointマッチング
		    Pattern pattern = Pattern.compile("^[1-9]+?\t.*");//次の行のマッチング
		    Matcher matcher = pattern.matcher(line);
		    boolean b= matcher.matches();//マッチング判定
		    if(b==true){//次の行の表示にマッチしたもの
			endn[j]=in;
			in = 0;
			if((j!=0) | (frag != 0)){
			    j++;
			}
			//行番号と行の内容に分割
			Pattern pattern2 = Pattern.compile("\t[ \t]*");//タブのマッチング
			String[] strs = pattern2.split(line);//分割
			lin[j] = (Integer.valueOf(strs[0]).intValue());//行番号代入
			gdbout[j][in] = strs[1];//次の行の内容を代入

			//else{
			//    Pattern pattern3 = Pattern.compile("\t");
			//    String[] strt = pattern3.split(line);
			//    lin[j] = (Integer.valueOf(strt[0]).intValue()) - 1;//行番号代入
			//
			//    gdbout[j][in] = strt[1];//次の行の内容を代入
			//}
			in++;
			frag++;
		    }
		    else{//変数の表示など
			gdbout[j][in] = line;
			in++;
		    }
		}
	    }
	    end = j+1;
	    br.close();
	    myin.close();
	    is.close();
	} catch (IOException e){
	    add(lbl_ore = new Label("ソース入力エラー"));
	}
    }



    public void init(){

	loadout();
	loads();


	myPnl = new Panel();
	myPnl.setLayout(new GridLayout(1,4));


	myBtn = new Button("　次へ　");
	youBtn = new Button("　ステップ目へ　");
	myTxt = new TextField();
	heBtn = new Button(" 戻る ");

	myPnl.add(myTxt);
	myPnl.add(youBtn);
	myPnl.add(myBtn);
	myPnl.add(heBtn);
	add ("Nouth",myPnl);

	youBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    str = myTxt.getText();
		    fg = Integer.valueOf(str).intValue() -1;

		    repaint();
		}
	    });


	myBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    fg++;
		    fg = fg % end;

		    repaint();
		}
	    });

	heBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
		    fg--;
			if(fg<0){
			    fg = end-1;
			}

		    repaint();
		}
	    });

    }
    public void paint(Graphics g){
	int a;
	int fgs;

	fgs = fg+1;
	g.drawLine(0,30,0,900);
	g.drawLine(0,30,1150,30);
	g.drawLine(530, 30, 530, 900);
	g.drawLine(530,75,1150,75);
	g.drawLine(1150,30,1150,900);
	g.drawLine(0,900,1150,900);
	tx = 5;
	ty = 30;
	tx2 = 550;
	ty2 = 100;

	g.setColor(Color.green);
	g.drawString("全"+end+"ステップ",550,50);
	g.drawString("現在"+fgs+"ステップ目",550,70);

	for(a=0;a<endn[fg];a++){
		if(a==0){
			g.setColor(Color.pink);
			g.drawString("次に実行する行："+lin[fg]+"行目",tx2,ty2);
			g.setColor(Color.blue);
			ty2 = ty2 + size;
		    g.drawString(gdbout[fg][a],tx2,ty2);
		}
		else{
			g.setColor(Color.pink);
			g.drawString("変数"+a,tx2,ty2);
			g.setColor(Color.blue);
			ty2 = ty2 + size;
			g.drawString(gdbout[fg][a],tx2,ty2);
		}
		ty2 = ty2 + size;
	}

	for(i=0;i<j;i++){

	    ty = ty + size;

	    //for(h=1;h<k;h++){
		//if(sp[h]==i){
		//    ty = ty + size;
		//	}//改良の余地あり
	    //}

	    g.setColor(Color.black);
	    g.drawString((i+1)+":"+source[i],tx,ty);

	    if((fg-1)<0){

		if(i==(lin[fg]-2)){
		    g.setColor(Color.red);
		    g.drawLine(tx,ty+1,tx+300,ty+1);
		}
	    }
	    else{
		if(i==(lin[fg-1]-1)){
		    g.setColor(Color.red);
		    g.drawLine(tx,ty+1,tx+300,ty+1);
		}
	    }

	}

    }
}

