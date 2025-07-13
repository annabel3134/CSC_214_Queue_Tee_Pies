package org.example;

public class QueueTee {
    private static final int MAX_SIZE = 10;
    private Cutie[] queueArray;
    private int front;
    private int rear;
    private int size;

    public QueueTee() {
        queueArray = new Cutie[MAX_SIZE];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(Cutie cutie) {
        if (size == MAX_SIZE) {
            System.out.println("Queue is full");
            return;
        }
        rear = (rear + 1) % MAX_SIZE;
        queueArray[rear] = cutie;
        size++;
    }

    public Cutie dequeue() {
        if (size == 0) {
            return null;
        }
        Cutie cutie = queueArray[front];
        front = (front + 1) % MAX_SIZE;
        size--;
        return cutie;
    }

    public int size() {
        return size;
    }
}

// Cutie implementations
class Puppy implements Cutie {
    @Override
    public String description() {
        return "Playful puppy with wagging tail";
    }

    @Override
    public Integer cutenessRating() {
        return 10;
    }
}

class Kitty implements Cutie {
    @Override
    public String description() {
        return "Fluffy kitty with big eyes";
    }

    @Override
    public Integer cutenessRating() {
        return 9;
    }
}

class PygmyMarmoset implements Cutie {
    @Override
    public String description() {
        return "Tiny monkey with curious expression";
    }

    @Override
    public Integer cutenessRating() {
        return 8;
    }
}