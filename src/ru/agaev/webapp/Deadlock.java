package ru.agaev.webapp;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
    public static void main(String[] args) throws InterruptedException {

        Runner runner = new Runner();
        Thread thread1 = new Thread(runner::firstThread);
        Thread thread2 = new Thread(runner::secondThread);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        runner.finished();
    }
}

class Runner {
    private Account account1 = new Account();
    private Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public void lockUnlockThreads(Account accountA, Account accountB, Lock lockA, Lock lockB, String threadNumber) {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            System.out.println("Запуск " + lockA + " в " + threadNumber + " потоке");
            lockA.lock();
            System.out.println("Запущен " + lockA + " в " + threadNumber + " потоке");
            System.out.println("Запуск " + lockB + " в " + threadNumber + " потоке");
            lockB.lock();
            System.out.println("Запущен " + lockB + " в " + threadNumber + " потоке");
            try {
                Account.transfer(accountA, accountB, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }

        }
    }

    public void firstThread() {
        lockUnlockThreads(account1, account2, lock1, lock2, "1");
    }


    public void secondThread() {
        lockUnlockThreads(account2, account1, lock2, lock1, "2");
    }

    public void finished() {
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
        System.out.println("Total balance " + (account1.getBalance() + account2.getBalance()));
    }
}

class Account {
    private int balance = 10000;

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}