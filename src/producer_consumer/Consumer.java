package producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer extends Thread
{

    BlockingQueue<Long> s2;
    long result;
    long data;

    public Consumer(BlockingQueue<Long> s2)
    {
        this.s2 = s2;
    }

    @Override
    public void run()
    {
        while (!s2.isEmpty())
        {
            try
            {

                data = s2.take();

                result += data;
//                System.out.println("Det næste tal er: " + data);
//                System.out.println("Den totale sum af alle tal, so far, er: " + result);
//                System.out.println("-----------------------------------------------");

                if (s2.isEmpty())
                {
                    //Dette er et værn imod at consumer ender førend alle data er produced, 
                    //da uheldig scheduling af threads (kombineret med en lang beregning) kan give et signifikant delay på det sidste data.
                    Thread.sleep(2000);
                    if (s2.isEmpty())
                    {
                        break;
                    }
                }
            } catch (InterruptedException ex)
            {
                System.err.println("poll afbrudt!");
            }
        }
        System.out.println("result: " + result);
        System.out.println("Denne consumer tråd er done. ID: " + getName());
    }
}
