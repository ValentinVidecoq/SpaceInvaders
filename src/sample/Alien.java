package sample;

import javafx.scene.image.Image;

public class Alien extends Fusee
{
    protected static int vitesse = 5;
    protected static int direction = 0;

    public Alien(int posX, int posY, int size, Image img)
    {
        super(posX,posY,size,img);
    }

    public void update()
    {
        if(direction == 1)
        {
            if(this.posX <= 470)
            {
                this.posX += vitesse;
            }
            else
            {
                this.posX += vitesse;
                direction = 0;
            }
        }
        else
        {
            if(this.posX > 5)
            {
                this.posX -= vitesse;
            }
            else
            {
                this.posX += vitesse;
                direction = 1;
            }
        }
    }
}