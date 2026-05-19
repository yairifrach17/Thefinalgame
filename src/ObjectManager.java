import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager {
    // 1. שינוי ברשימה עצמה
    private final ArrayList<FallingObject1> objectsList;
    private final int panelWidth;
    private final int panelHeight;
    private final Random random;
    private BufferedImage goodImage;
    private BufferedImage badImage;

    public ObjectManager(int panelWidth, int panelHeight) {
        this.objectsList = new ArrayList<>();
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.random = new Random();
        loadImages();
    }

    private void loadImages() {
        try {
            goodImage = ImageIO.read(new File("assets/good_ingredient.png"));
            badImage = ImageIO.read(new File("assets/bad_item.png"));
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    public void spawnObject(int currentScore) {
        int imgWidth = (goodImage != null) ? goodImage.getWidth() : 50;
        int xPos = random.nextInt(Math.max(1, panelWidth - imgWidth));

        int speed = 5;
        int baseDelay = 30;
        int difficultyDelayReduction = currentScore / 5;
        int finalDelay = Math.max(8, baseDelay - difficultyDelayReduction);

        int badItemChance = 15 + Math.min(55, currentScore / 3);
        int chanceResult = random.nextInt(100);

        // 2. שינוי פה ביצירת האובייקט
        FallingObject1 newObject;
        if (chanceResult < badItemChance) {
            newObject = new BadItem(xPos, speed, finalDelay, panelHeight, badImage);
        } else {
            newObject = new GoodIngredient(xPos, speed, finalDelay, panelHeight, goodImage);
        }

        synchronized (objectsList) {
            objectsList.add(newObject);
        }

        Thread objectThread = new Thread(newObject);
        objectThread.start();
    }

    public void removeFinishedObjects() {
        synchronized (objectsList) {
            // כאן הורדתי את המילה FallingObject, ה-Java יודע לזהות לבד את הסוג!
            objectsList.removeIf(obj -> obj.isRunning() == false);
        }
    }

    public void stopAllObjects() {
        synchronized (objectsList) {
            // 3. שינוי כאן בלולאה!
            for (FallingObject1 obj : objectsList) {
                obj.stopFalling();
            }
            objectsList.clear();
        }
    }

    // 4. שינוי כאן בפונקציה שמחזירה את הרשימה!
    public ArrayList<FallingObject1> getObjectsList() {
        return objectsList;
    }
}