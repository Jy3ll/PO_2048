package game.ui;

import game.engine.Board;
import game.engine.MoveDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements ActionListener, KeyListener {
    private Board board;
    private JPanel contentPane;
    private final JPanel boardPanel;
    private final JPanel sidePanel;
    private final JButton restartButton;
    private final JLabel scoreField;

    public MainFrame(){
        this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.height/2,5*screenSize.height/8);
        setResizable(false);
        setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);

        this.contentPane=new JPanel();
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(new Color(187,173,160));
        JPanel layoutPanel=new JPanel();
        layoutPanel.setBounds(0,0,screenSize.height/2,5*screenSize.height/8);
        layoutPanel.setLayout(new BoxLayout(layoutPanel,BoxLayout.Y_AXIS));
        layoutPanel.setBackground(new Color(187,173,160));
        this.contentPane.add(layoutPanel);

        this.sidePanel=new JPanel();
        this.sidePanel.setMaximumSize(new Dimension(screenSize.height/2,screenSize.height/8));
        this.sidePanel.setLayout(new GridLayout(1,2));
        this.sidePanel.setBackground(new Color(187,173,160));


        JPanel scorePanel=new JPanel();
        this.scoreField= new JLabel();
        scorePanel.add(this.scoreField);
        scorePanel.setBackground(new Color(187,173,160));
        JPanel restartPanel=new JPanel();
        this.restartButton=new JButton("New Game");
        this.restartButton.addActionListener(this);
        restartPanel.add(restartButton);
        restartPanel.setBackground(new Color(187,173,160));
        this.sidePanel.add(scorePanel);
        this.sidePanel.add(restartPanel);
        this.boardPanel=new JPanel();
        this.boardPanel.setMaximumSize(new Dimension(screenSize.height/3,screenSize.height/3));
        this.boardPanel.setLayout(new GridLayout(4,4));

        for(int i=0;i<16;i++) {
            JPanel added=new JPanel();
            added.setLayout(new GridBagLayout());
            added.setMaximumSize(new Dimension(screenSize.height/8,screenSize.height/8));
            added.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            added.add(new JLabel(Integer.toString(i)));
            this.boardPanel.add(added);
        }

        layoutPanel.add(this.sidePanel);
        layoutPanel.add(this.boardPanel);
        this.setContentPane(this.contentPane);
        this.initGame();
    }

    private void initGame(){
        this.board=new Board();
        this.updateBoard();
    }

    private void updateBoard(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                String fieldValue="";
                if(this.board.getElement(i,j)!=0) fieldValue=Integer.toString(this.board.getElement(i,j));
                ((JLabel)(((JPanel) (this.boardPanel.getComponent(4*i+j))).getComponent(0))).setText(fieldValue);
            }
        }
        this.scoreField.setText("Score: "+board.getScore());
        if(this.board.getWinCondition()){
            JOptionPane.showMessageDialog(this,"Gratulacje, Wygrałeś! Twój wynik to: "+this.board.getScore());
            this.initGame();
        }
        if(this.board.getLossCondition()){
            JOptionPane.showMessageDialog(this,"Przegrałeś! Twój wynik to: "+this.board.getScore());
            this.initGame();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e){
        Object source=e.getSource();
        if(source==this.restartButton){
            this.board=new Board();
            this.updateBoard();
        }
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyPressed(KeyEvent e){

    }
    @Override
    public void keyReleased(KeyEvent e){
        switch(e.getKeyCode()){
            case 65: //Left
            case 37:
                this.board.moveInDirection(MoveDirection.LEFT);
                this.updateBoard();
                break;
            case 87: //Up
            case 38:
                this.board.moveInDirection(MoveDirection.UP);
                this.updateBoard();
                break;
            case 68: //Right
            case 39:
                this.board.moveInDirection(MoveDirection.RIGHT);
                this.updateBoard();
                break;
            case 83: //Down
            case 40:
                this.board.moveInDirection(MoveDirection.DOWN);
                this.updateBoard();
                break;
            default:
                break;
        }
    }

}
