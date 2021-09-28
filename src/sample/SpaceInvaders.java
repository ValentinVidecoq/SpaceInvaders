package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.text.Font;

public class SpaceInvaders extends Application {

    private Canvas canvas;
    private Pane root;
    private Scene scene;
    private GraphicsContext gc;
    private Color color_bg = Color.BLACK;
    private Color color_tir = Color.GREEN;
    private Color color_tirAlien = Color.BLUE;
    private Fusee player;
    private Timeline timeline;
    private Random rand = new Random();
    private InputStream input = this.getClass().getResourceAsStream("./images/boom.png");
    private Image BOOM_IMG = new Image(input);
    private int TAILLE_JOUEUR = 20;
    private int CPT_ALIEN = 0;
    private int ecart_alien = 40;
    private int CD_JOUEUR = 0;
    private int i;
    private int j;
    private int h = 25;
    private int score = 0;
    private int GAME_OVER = 0;
    private int rand_int;
    private int COOLDOWN = 0;
    private int vies = 3;
    List <Alien> alien = new ArrayList<Alien>();
    List <Tir> tir = new ArrayList<Tir>();
    List <Tir> tirAlien = new ArrayList<Tir>();

    /*
    Variables importantes
     */
    private int WIDTH = 500; // Largeur fenêtre
    private int HEIGHT = 400; // Hauteur fenêtre
    private int NB_ALIEN = 8; // Nombre d'aliens par ligne
    private int NB_LIGNES = 3; // Nombre de lignes


    private void creerContenu()
    {
        input = this.getClass().getResourceAsStream("./images/joueur.png");
        Image JOUEUR_IMG = new Image(input);
        input = this.getClass().getResourceAsStream("./images/alien.png");
        Image ALIEN_IMG = new Image(input);
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Pane(canvas);
        root.setPrefSize(WIDTH,HEIGHT);
        scene = new Scene(root);
        gc.setFill(color_bg);
        gc.fillRect(0,0,WIDTH, HEIGHT);
        player = new Fusee(WIDTH/2, HEIGHT - TAILLE_JOUEUR - 10 , TAILLE_JOUEUR, JOUEUR_IMG);
        player.affiche(gc);

        for (j = 0; j < NB_LIGNES; j++)
        {
            for (i = 0; i < NB_ALIEN; i++)
            {
                alien.add (new Alien(CPT_ALIEN * ecart_alien, h, TAILLE_JOUEUR, ALIEN_IMG));
                alien.get(i).affiche(gc);
                CPT_ALIEN++;
            }
            CPT_ALIEN = 0;
            h += 25;
        }

        /*
        Gestion des input clavier
         */

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    player.moveLeft();
                    gc.setFill(color_bg);
                    if ( player.detruit == 0)
                    {
                        player.update(gc);
                    }
                    break;

                case RIGHT:
                    player.moveRight();
                    gc.setFill(color_bg);
                    if ( player.detruit == 0)
                    {
                        player.update(gc);
                    }
                    break;
                case SPACE:
                    if (CD_JOUEUR % 10 == 0)
                    {
                        tir.add(new Tir(player.posX, player.posY));
                        CD_JOUEUR++;
                        break;
                    }
                }
            });

        timeline = new Timeline(new KeyFrame(Duration.millis(100), e ->
                run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /*
    Affichage du score aux coordonnées données
     */

    void afficher_score(int x, int y)
    {
        gc.setFont(new Font("Candara", 20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score : " + score, x, y);
    }

    /*
    Affichage des vies aux coordonées données
     */

    void afficher_vies()
    {
        gc.setFont(new Font("Candara", 20));
        gc.setFill(Color.WHITE);
        gc.fillText("Lives : " + vies, WIDTH - 70, 20);
    }

    /*
    Affcihage de l'écran de fin
    Il affiche la victoire ou la défaite ainsi que le score
     */

    void ecran_fin (String end)
    {
        gc.setFill(color_bg);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.WHITE);
        gc.fillRect(40, 40, 420, 320);
        gc.setFill(Color.BLACK);
        gc.fillRect(50, 50, 400, 300);
        if (end == "win")
        {

            gc.setFont(new Font("Impact", 42));
            gc.setFill(Color.GREEN);
            gc.fillText("WIN", (int) WIDTH / 2 - 30, HEIGHT / 2 - 100);

        }
        else if (end == "lose")
        {
            gc.setFont(new Font("Impact", 42));
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER", WIDTH / 2 - 95, HEIGHT / 2 - 100);
        }
        gc.setFill(color_bg);
        gc.fillRect(0, 0, 100, 25);
        afficher_score(WIDTH / 2 - 40,HEIGHT / 2 - 20);

    }

    public void run () {
        try {
            int direction1;
            int direction2;

            direction1 = Alien.direction;

            /*
            Initialisation du jeu
             */

            gc.setFill(color_bg);
            gc.fillRect(0, 0, WIDTH, HEIGHT - 30);

            afficher_score(0,20);
            afficher_vies();

            gc.setFill(color_bg);
            player.update(gc);

            /*
            Tir alien
             */

            if (COOLDOWN % 10 == 0)
            {
                rand_int = rand.nextInt(alien.size());
                tirAlien.add(new Tir(alien.get(rand_int).posX, alien.get(rand_int).posY));
            }

            /*
            Placement et déplacement des aliens
             */

            for (i = 0; i < alien.size(); i++)
            {
                gc.setFill(color_bg);
                alien.get(i).update();
                alien.get(i).affiche(gc);
                if (alien.get(i).posY >= HEIGHT - 50)
                {
                    ecran_fin("lose");
                    GAME_OVER = 1;
                    player.detruit = 1;
                }
            }

            /*
            Changement de direction des aliens quand ils touchent un coté
             */

            direction2 = Alien.direction;

            if (direction1 != direction2)
            {
                if (direction2 == 0)
                {
                    alien.get(0).posX -= (Alien.vitesse * 2);
                }

                gc.setFill(color_bg);
                gc.fillRect(0, 0, WIDTH, HEIGHT - TAILLE_JOUEUR - 10);
                afficher_score(0,20);
                afficher_vies();

                for (i = 0; i < alien.size(); i++)
                {
                    gc.setFill(color_bg);
                    alien.get(i).posY += alien.get(i).size;
                    gc.drawImage(alien.get(i).img, alien.get(i).posX, alien.get(i).posY, alien.get(i).size, alien.get(i).size);
                }
                Collections.reverse(alien);
            }

            /*
            Déplacement et colision du tir du joueur
             */

            for (i = 0; i < tir.size(); i++)
            {
                gc.setFill(color_tir);
                tir.get(i).update(0);
                tir.get(i).affiche(gc);

                for (j = 0; j < alien.size(); j++)
                {
                    if (tir.get(i).collision(alien.get(j)))
                    {
                        gc.setFill(color_bg);
                        gc.drawImage(BOOM_IMG, alien.get(j).posX, alien.get(j).posY, alien.get(j).size, alien.get(j).size);
                        alien.remove(j);
                        tir.remove(i);
                        score += 10;

                    }
                    else if (tir.get(i).posY <= 0)
                    {
                        tir.remove(i);
                    }
                }
            }

            /*
            Déplacement et colision du tir alien
             */

            for (i = 0; i < tirAlien.size(); i++)
            {
                gc.setFill(color_tirAlien);
                tirAlien.get(i).update(1);
                tirAlien.get(i).affiche(gc);
                if (tirAlien.get(i).collision_player(player))
                {
                    tirAlien.remove(i);
                    if (vies > 0)
                    {
                        gc.setFill(color_bg);
                        gc.drawImage(BOOM_IMG, player.posX, player.posY, player.size, player.size);
                        vies--;
                    }
                }
                else if (tirAlien.get(i).posY >= HEIGHT)
                {
                    tirAlien.remove(i);
                }
                if (vies == 0)
                {
                    gc.drawImage(BOOM_IMG, player.posX, player.posY, player.size, player.size);
                    gc.setFill(color_bg);
                    gc.fillRect(WIDTH - 70, 0, 70, 20);
                    afficher_vies();
                    ecran_fin("lose");
                    GAME_OVER = 1;
                    player.detruit = 1;
                }
            }

        /*
        Partie gagnée si tous les aliens sont détruits
        */

        if (alien.size() == 0)
        {
            score += 50;
            ecran_fin("win");
            GAME_OVER = 1;
            player.detruit = 1;
        }

        /*
        Arret de la timeline quand la partie est finie
         */

        if (GAME_OVER == 1)
        {
            timeline.stop();
        }

        /*
        Incrémentation du cooldown pour le tir alien et le tir joueur
         */

        COOLDOWN++;

        if (CD_JOUEUR % 10 != 0)
        {
            CD_JOUEUR++;
        }

        } catch (IndexOutOfBoundsException ignore) {}
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        creerContenu();
        stage.setTitle("Space Invaders");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
