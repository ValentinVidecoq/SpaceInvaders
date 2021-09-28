package sample;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Fusee
{
    protected int posX;
    protected int posY;
    protected int size;
    protected Image img;
    protected int detruit = 0;

    public Fusee(int posX, int posY, int size, Image img)
    {
        this.posX = posX;
        this.posY = posY;
        this.img = img;
        this.size = size;
    }

    public void update(GraphicsContext gc)
    {
        gc.fillRect(0,370,500,30);
        this.affiche(gc);
    }

    public void affiche(GraphicsContext gc)
    {
        gc.drawImage(img, posX,posY, size, size);
    }

    public void moveRight()
    {
        if(this.posX < 480)
        {
            this.posX += 5;
        }
    }

    public void moveLeft()
    {
        if(this.posX > 0)
        {
            this.posX -= 5;
        }
    }
}
