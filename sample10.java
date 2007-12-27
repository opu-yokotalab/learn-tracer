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

    int tx; //�e�L�X�g�̕\���ʒu�@�w���W
    int ty; //�e�L�X�g�̕\���ʒu�@�x���W
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
		    add(lbl_ore = new Label("���̓G���["));
		}
	}


    public void loadout(){//gdb�̏o�͌��ʂ̓ǂݍ���
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
		    //Pattern pattern = Pattern.compile("^[1-9]+:.*");//�ϐ��}�b�`���O
		    //Pattern pattern = Pattern.compile(".*.c:[1-9]+$");//�ϐ��Ăяo���EBreakPoint�}�b�`���O
		    Pattern pattern = Pattern.compile("^[1-9]+?\t.*");//���̍s�̃}�b�`���O
		    Matcher matcher = pattern.matcher(line);
		    boolean b= matcher.matches();//�}�b�`���O����
		    if(b==true){//���̍s�̕\���Ƀ}�b�`��������
			endn[j]=in;
			in = 0;
			if((j!=0) | (frag != 0)){
			    j++;
			}
			//�s�ԍ��ƍs�̓��e�ɕ���
			Pattern pattern2 = Pattern.compile("\t[ \t]*");//�^�u�̃}�b�`���O
			String[] strs = pattern2.split(line);//����
			lin[j] = (Integer.valueOf(strs[0]).intValue());//�s�ԍ����
			gdbout[j][in] = strs[1];//���̍s�̓��e����

			//else{
			//    Pattern pattern3 = Pattern.compile("\t");
			//    String[] strt = pattern3.split(line);
			//    lin[j] = (Integer.valueOf(strt[0]).intValue()) - 1;//�s�ԍ����
			//
			//    gdbout[j][in] = strt[1];//���̍s�̓��e����
			//}
			in++;
			frag++;
		    }
		    else{//�ϐ��̕\���Ȃ�
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
	    add(lbl_ore = new Label("�\�[�X���̓G���["));
	}
    }



    public void init(){

	loadout();
	loads();


	myPnl = new Panel();
	myPnl.setLayout(new GridLayout(1,4));


	myBtn = new Button("�@���ց@");
	youBtn = new Button("�@�X�e�b�v�ڂց@");
	myTxt = new TextField();
	heBtn = new Button(" �߂� ");

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
	g.drawString("�S"+end+"�X�e�b�v",550,50);
	g.drawString("����"+fgs+"�X�e�b�v��",550,70);

	for(a=0;a<endn[fg];a++){
		if(a==0){
			g.setColor(Color.pink);
			g.drawString("���Ɏ��s����s�F"+lin[fg]+"�s��",tx2,ty2);
			g.setColor(Color.blue);
			ty2 = ty2 + size;
		    g.drawString(gdbout[fg][a],tx2,ty2);
		}
		else{
			g.setColor(Color.pink);
			g.drawString("�ϐ�"+a,tx2,ty2);
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
		//	}//���ǂ̗]�n����
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
