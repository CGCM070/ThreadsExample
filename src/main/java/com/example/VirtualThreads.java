package com.example;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreads {
    public static void main(String[] args) throws InterruptedException {


        //newVirtualThreadPerTaskExecutor() crea un ExecutorService que crea un hilo virtual por tarea.
        //el ExecutorService es una interfaz que representa un servicio que ejecuta tareas dadas.

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        // Hilo buscar letra en el abecedario
        char character = JOptionPane.showInputDialog("Ingrese una letra").toUpperCase().charAt(0);


        AtomicBoolean found = new AtomicBoolean(false);
        
        Runnable thread1 = () -> {
            //tiempo de espera 500ms


            try {

                Thread.currentThread().setName("Thread character");
                for (char i = 'A'; i <= character; i++) {
                    Thread.sleep(450);
                    System.out.print( " " + i);
                    if (i == character) {
                        System.out.println("  Letra encontrada: " + i);
                        System.out.println("Thread: " + Thread.currentThread().getName() + " finalizado");
                        found.set(true);
                        break;
                        
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        //hilo que recorre de forma descendente hasta que el primer hilo haya encontrado la letra
        int number = Integer.parseInt(JOptionPane.showInputDialog("Ingrese un número"));
        AtomicInteger atomicInteger = new AtomicInteger(number);
        Runnable thread2 = () -> {
            try {
                Thread.currentThread().setName("Thread number");
                while (!found.get()) {
                    Thread.sleep(450);
                    System.out.println(" " + atomicInteger.get());
                    if (atomicInteger.get() == 0) {
                        System.out.println(" Thread: " + Thread.currentThread().getName() + " finalizado");
                        break;
                    }
                    atomicInteger.getAndDecrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" Running task2 in thread: " + Thread.currentThread().getName());
        };


        executor.submit(thread1);
        executor.submit(thread2);
        executor.awaitTermination(30, java.util.concurrent.TimeUnit.SECONDS);

        //Concurrencia y paralelismo
        //Concurrencia: es la capacidad de un sistema para manejar múltiples tareas al mismo tiempo.
        //Paralelismo: es la capacidad de un sistema para ejecutar múltiples tareas simultáneamente.

        //En este caso, se está utilizando concurrencia, ya que se están ejecutando dos tareas al mismo tiempo, pero no de forma simultánea.
        //Esto se debe a que el hilo 2 no se ejecuta hasta que el hilo 1 haya encontrado la letra.
        //Si se quisiera utilizar paralelismo, se debería utilizar un ForkJoinPool, que es un ExecutorService que permite la ejecución de tareas de forma paralela.




    }

}