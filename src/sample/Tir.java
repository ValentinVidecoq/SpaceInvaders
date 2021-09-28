package sample;

import javafx.scene.canvas.GraphicsContext;

public class Tir
{
    protected int posX;
    protected int posY;
    protected int speed = 10;
    protected int size = 6;

    public Tir (int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }

    public void update (int x)
    {
        if (x == 0)
        {
            this.posY -= speed;
        }
        else if (x == 1)
        {
            this.posY += speed;
        }

    }

    public void affiche (GraphicsContext gc)
    {
        gc.fillOval(posX + size + 1, posY - size, size, size);
    }

    /*
    Colision avec un alien
     */

    public boolean collision (Alien alien)
    {
        if(this.posX >= alien.posX && this.posX <= alien.posX + alien.size && this.posY >= alien.posY && this.posY <= alien.posY + alien.size)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    Colision avec le joueur
     */

    public boolean collision_player (Fusee player)
    {
        if(this.posX >= player.posX && this.posX <= player.posX + player.size && this.posY >= player.posY && this.posY <= player.posY + player.size)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
