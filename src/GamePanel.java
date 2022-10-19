import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=20;
    static final int GAME_UNIT=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNIT];
    final int y[] = new int[GAME_UNIT];
    int bodyParts = 6;
    int applesEaten;
    int applex;
    int appley;
    char Direction = 'R';
    boolean running = false;
    Timer timer = new Timer(DELAY,this);
    Random random;

    GamePanel()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame()
    {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(running) {
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }*/
            g.setColor(Color.RED);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman",Font.BOLD,39));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Stuffed_in:"+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Stuffed_in:"+applesEaten))/2,g.getFont().getSize());
        }
        else
        {
            gameover(g);
        }
    }
    public void newApple()
    {
        applex = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move()
    {
        for(int i=bodyParts;i>0;i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (Direction)
        {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
         }

    }
    public void checkapple()
    {
        if((x[0] == applex) && (y[0] == appley))
        {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkcollisions()
    {
        for(int i=bodyParts;i>0;i--)
        {
            //head to body
            if((x[0] == x[i]) && (y[0] == y[i]))
            {
                running = false;
            }
            //head to left border
            if(x[0]<0)
            {
                running = false;
            }
            //head to right border
            if(x[0]>SCREEN_WIDTH)
            {
                running = false;
            }
            //head to top border
            if(y[0]<0)
            {
                running = false;
            }
            //head to bottom border
            if(y[0] > SCREEN_HEIGHT)
            {
                running = false;
            }
            //stop the timer
            if(!running)
            {
                timer.stop();
            }

        }


    }
    public void gameover(Graphics g)
    {
        //game score entha ani
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman",Font.BOLD,39));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Stuffed_in:"+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Stuffed_in:"+applesEaten))/2,g.getFont().getSize());
        //game ayyipoyindhi ra
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman",Font.BOLD,69));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("NOOB_You_LOST", (SCREEN_WIDTH - metrics2.stringWidth("NOOB_You_LOST"))/2,SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running)
        {
            move();
            checkapple();
            checkcollisions();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:;
                if(Direction != 'R')
                {
                    Direction = 'L';
                }
                break;
                case KeyEvent.VK_RIGHT:;
                    if(Direction != 'L')
                    {
                        Direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:;
                    if(Direction != 'D')
                    {
                        Direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:;
                    if(Direction != 'U')
                    {
                        Direction = 'D';
                    }
                    break;

            }

        }
    }

}
