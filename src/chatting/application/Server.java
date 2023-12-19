package chatting.application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener{
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server(){
        f.setLayout(null);


        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,75);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i2 = i1.getImage().getScaledInstance(30,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,30,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/boy.png"));
        Image i5 = i4.getImage().getScaledInstance(55,55,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(38,10,55,55);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,25,25,25);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25,20,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,26,25,20);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/more.jpg"));
        Image i14 = i13.getImage().getScaledInstance(30,40,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(400,16,30,42);
        p1.add(more);

        JLabel name = new JLabel("Daksh");
        name.setBounds(110,20,100,40);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        p1.add(name);

        JLabel status = new JLabel("online");
        status.setBounds(110,45,90,30);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
        p1.add(status);


        a1  = new JPanel();
       // a1.setBackground(new Color(210,210,210));
        a1.setBounds(4,79,442,578);
       f.add(a1);

        text = new JTextField();
        text.setBounds(4,661,325,38);
       f.add(text);

        JButton send = new JButton("SEND");
        send.setBounds(334,664,100,30);
        send.setBackground(new Color(7,120,70));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.BOLD,13));
        f.add(send);

      f.setSize(450,700);
      f.setLocation(200,10);
      f.getContentPane().setBackground(Color.white);

       f.setUndecorated(true);
       f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));


            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();

        } catch (Exception ae){
            ae.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma",Font.PLAIN,12));
        output.setBackground(new Color(54,69,79));
        output.setForeground(Color.white);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(8,8,8,30));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args){
        new Server();

        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
               Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }

            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
