Exercise 2.1) 

2. Is your solution fair towards writer threads? In other words, does your solution ensure that if a writer
thread wants to write, then it will eventually do so? 

No, the solution is not fair towards the writers - since the writers will need to wait until there are 0 readers to write, and readers on the contrary can come along as long as there are no writers.

If so, explain why. If not, modify part 1. so that
your implementation satisfies this fairness requirement, and then explain why your new solution satisfies
the requirement.

    In order to make the monitor more fair, we have ensured that writers dont nescessarily have to wait until there is 0 readers, before aquiring a write lock. We have altered a specific snippet: 

     original:
            while (readers > 0 || writer)
                condition.await();
            writer = true;

    new: 
         while(writer)
                condition.await();
            writer=true;
            while(readers > 0)
                condition.await();



Does your monitor implementation contain any condition variables?

Our implementation uses syncrhonization with a o object, so we dont use an explicit condition variable, but the o object uses has a condition variable which is then utilized to determine when a thread should wait or not: 

o.wait() <- condition variable?

Exercise 2.2) 

    1. Execute the example as is. Do you observe the "main" thread’s write to mi.value remains invisible to
    the t thread, so that it loops forever? Independently of your observation, is it possible that the program
    loops forever?

    the program will loop until the mutable integer is set to something different that 0.
    In pc's with mutliple cores variables stored in (reg,L1,L2) are not shared by the threads. 
    For this program that means that MI could potientally be stored in a cache that is not shared by the threads, 
    meaning that t1 thread will never read that main thread has set the value mi to 42. 
2. 
    By adding synchronized on the MutableIntegers methods the value of MutableInteger we can achieve visibilty between thread t and Main thread. Concretely this means that if either thread had the Mutablie Integer value stored in a non-shared memory space(L1,L2,reg) the value will be flushed to Main memory, allowing each thread to share the resource. 

3. 
    No it wont always terminate. It depends on whether or not you syncrhonize other parts of the code. 

4. 
    It does terminate yes. The volitale keyword offers a weak form of synchrhonization by flushing the variable to Main memory. Therefore main thread and thread t can share the ressource for value. 


Exercise 2.3)
Consider the small artificial program in file TestLocking0.java. In class Mystery, the
single mutable field sum is private, and all methods are synchronized, so superficially the class seems that no concurrent sequence of method calls can lead to race conditions.

1. 
    There are race conditions yes: 

    Sum is 1837489,000000 and should be 2000000,000000

2. Explain why race conditions appear when t1 and t2 use the Mystery object. Hint: Consider (a) what it
    means for an instance method to be synchronized, and (b) what it means for a static method to be synchronized.
    
    The reason why a race condition appears when using th Mystery object, is that the two threads are using different locks. Static methods are methods that belongs to the class and not an instance of the class. Since synchronized uses a lock under the hood, these two methods: addStatic and addInstance will use different locks to synchronize, the static will use the lock of the class, and add instance will use lock of the object.


3.
        for (int i = 0; i < count; i++)
                m.addInstance(1);
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < count; i++)
                m.addInstance(1);
        });

        with this change we ensure proper synchronization between the threads by using the same lock around our critical section(addMethod). Alternatively you could use addStatic in both threads, but dont mix and match.

4. 
    No it is not nescessary for this program to have a lock on the sum.