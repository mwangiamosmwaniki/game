import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class snakeGame extends JPanel implements ActionListener, KeyListener{
    private class tile {
        int x;
        int y;
        
        tile(int x, int y){
            this.x = x;
            this.y =y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    int velocityX;
    int velocityY;
    boolean gameOver = false;

    tile snakeHead;

    ArrayList<tile>snakeBody;

    tile food;
    Random random;

    Timer gameLoop;

    snakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        snakeHead = new tile(5, 5);

        snakeBody = new ArrayList<tile>();

        addKeyListener(this);
        setFocusable(true);

        food = new tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;
        

        gameLoop = new Timer(100, this);
        gameLoop.start();
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //for(int i = 0; i<boardWidth/tileSize; i++){
         //   g.drawLine(i*tileSize,0, i*tileSize, boardHeight);
          //  g.drawLine(0, i*tileSize, boardWidth, i*tileSize);

       // }
        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);

        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize, true);
        
        for(int i = 0; i < snakeBody.size(); i++){
            tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: "+ String.valueOf(snakeBody.size()), tileSize = 16, tileSize);
        }
        else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize = 16, tileSize);
        }
    }
    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }
    public boolean collision(tile Tile1, tile Tile2){
        return Tile1.x == Tile2.x && Tile1.y == Tile2.y;
    }

    public void move(){
        if (collision(snakeHead, food)) {
            snakeBody.add(new tile(food.x, food.y));
            placeFood();
        }

        for (int i = snakeBody.size()-1; i >=0; i--){
            tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for(int i = 0; i < snakeBody.size(); i++){
            tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();;
        }
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || 
        snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
        gameOver = true;
        }
    }

        @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
