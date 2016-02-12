package producer_consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends Thread
{

    BlockingQueue<Long> s1;
    BlockingQueue<Long> s2;
    Consumer cons;

    public Controller()
    {
        s1 = new ArrayBlockingQueue(11);
        s2 = new ArrayBlockingQueue(11);
    }

    public void populateArray()
    {
        s1.add(new Long(4));
        s1.add(new Long(5));
        s1.add(new Long(8));
        s1.add(new Long(12));
        s1.add(new Long(21));
        s1.add(new Long(22));
        s1.add(new Long(34));
        s1.add(new Long(35));
        s1.add(new Long(36));
        s1.add(new Long(37));
        s1.add(new Long(42));
    }

    private void threader(int noOfThreads) throws InterruptedException
    {
        for (int i = 0; i < noOfThreads; i++)
        {
            Thread bob = new Thread(new Producer(s1, s2));
            bob.start();
            bob.join();            
        }
    }

    @Override
    public void run()
    {
        populateArray();
        double startTime = System.currentTimeMillis();
        try
        {
            threader(4);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        double endTime = System.currentTimeMillis();
        
        double time = (endTime - startTime)/1000;
        System.out.println("Tiden er: " + time);
        new Thread(new Consumer(s2)).start();        
    }

}
