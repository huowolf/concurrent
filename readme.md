# Volatile的内存语义
volatile写与锁的释放有相同的内存语义。
volatile读与锁的获取有相同的内存语义。

volatile写的内存语义：  
> 当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量值刷新到主内存中。 

线程A写一个volatile变量，实质上是线程A向接下来要读这个volatile变量的某个线程发出了（其对共享变量所做修改的）消息。

volatile读的内存语义：  
> 当读一个volatile变量时，JMM会把该线程对应的本地变量内存置为无效。线程接下来将从主内存中读取共享变量。

线程B读一个volatile变量，实质上是线程B接收了之前某个线程发出的（在写这个volatile变量之前其对共享变量所做修改的）消息。

# 锁的内存语义
当线程释放锁时，JMM会把线程对应的本地内存中的共享变量的刷新到主内存中。  
当线程获取锁时，JMM会把线程对应的本地内存置为无效。从而使得被监视器保护的临界区代码必须从主内存中读取共享变量。 

线程A释放一个锁，实质上是线程A向接下来要获取这个锁的某个线程发出了（线程A其对共享变量所做修改的）消息。  
线程B获取一个锁，实质上是线程B接收了之前某个线程发出的（在释放这个所之前之前其对共享变量所做修改的）消息。

# CAS同时具有volatile读和volatile写的内存语义。

# final域的内存语义
1. 写final域的重排序规则禁止把final域的写重排序到构造函数之外。
2. 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能重排序。

# JMM通过happens-before关系向程序员提供跨线程的内存可见性保证。
1. 程序顺序规则：一个线程中的每个操作，happens-befor于该线程中的任意后续操作。
2. 监视器锁规则：对一个锁的解锁，happens-before于随后对这个锁的加锁。
3. volatile变量规则：对一个volatile域的写，happens-before于任意后续对这个volatile域的读。
4. 传递性：如果A happens-before B,且B happens-before C，那么A happens-before C

# Lock接口提供的synchronzied关键字所不具备的主要特性：
1. 尝试非阻塞的获取锁
2. 能被中断的获取锁
3. 超时获取锁


# AQS(队列同步器)
这是用来构造锁或者其他同步组件的基础框架，使用一个int成员变量
（```private volatile int state```）标识同步状态，通过内置的FIFO队列来完成队列的排队工作。

同步器的主要使用方式是继承，子类通过继承同步器并实现它的抽象方法来管理同步状态。  
通过同步器提供的三个方法（```getState(),setState(int newState),compareAndSetState(int expect, int update)```）对同步状态访问或修改。  

同步器既可以独占式获取同步状态，也可以共享式的获取同步状态。

# 重入锁（ReentrantLock)
Java的synchronized块也是可重入的。  

重入锁，就是支持重新进入的锁，它表示该锁能够支持一个线程对资源的重复加锁。该锁还支持获取锁时的公平和非公平性选择。默认是非公平锁。  

公平的获取锁，也就是等待时间最长的锁的线程最新获取锁。锁获取是有顺序的。反之，是非公平的。 公平的锁机制往往没有非公平的效率高。

# CopyOnWrite容器
CopyOnWrite容器即写时复制的容器。   
通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。

# ReadWriteLock 读写锁
读写锁在同一时间允许多个线程访问。但是在写线程访问时，所有的读线程和其他写线程均被阻塞。读写锁维护了一对锁，一个读锁，一个写锁。

一般情况下，读写锁的性能都会比排它锁好，因为大多数场景读是多于写的。在读多于写的情况下，读写锁能够提供比排它锁更好的并发性和吞吐量。

# 阻塞队列（BlockingQueue）
阻塞队列（BlockingQueue）是一个支持两个附加操作的队列。这两个附加的操作支持阻塞的插入和移除方法。

这两个附加的操作是：在队列为空时，获取元素的线程会等待队列变为非空。当队列满时，存储元素的线程会等待队列可用。

阻塞队列常用于生产者和消费者的场景，生产者是往队列里添加元素的线程，消费者是从队列里拿元素的线程。阻塞队列就是生产者存放元素的容器，而消费者也只从容器里拿元素。

### ArrayBlockingQueue 
一个由数组结构构成的有界阻塞队列。此队列按照先进先出方式对元素排序。

ArrayBlockingQueue内部使用可重入锁ReentrantLock+Condition来完成多线程环境的并发操作。

### LinkedBlockingQueue
一个由链表结构构成的有界阻塞队列。此队列的默认和最大长度为Integer.MAX_VALUE.此队列按照先进先出方式对元素排序。
```
public LinkedBlockingQueue() {this(Integer.MAX_VALUE);}

```
### PriorityBlockingQueue
一个支持优先级排序的无界阻塞队列。默认情况下采用自然顺序排序，当然也可以通过自定义Comparator来指定元素的排序顺序。  
PriorityBlockingQueue内部采用二叉堆的实现方式。添加操作则是不断“上冒”，而删除操作则是不断“下掉”。

### DelayQueue
DelayQueue是一个支持延时操作的无界阻塞队列。DelayQueue采用支持优先级的PriorityQueue来实现，但是队列中的元素必须要实现Delayed接口，Delayed接口用来标记那些应该在给定延迟时间之后执行的对象，该接口提供了getDelay()方法返回元素节点的剩余时间。同时，元素也必须要实现compareTo()方法，compareTo()方法需要提供与getDelay()方法一致的排序。

### SynchronousQueue
SynchronousQueue是一个神奇的队列，他是一个不存储元素的阻塞队列，也就是说他的每一个put操作都需要等待一个take操作，否则就不能继续添加元素了，类似于生产者和消费者进行交换。

### LinkedTransferQueue
LinkedTransferQueue是一个由链表组成的的无界阻塞队列。与其他BlockingQueue相比，他多实现了一个接口TransferQueue，该接口是对BlockingQueue的一种补充，多了tryTranfer()和transfer()两类方法。

### LinkedBlockingDeque
一个由链表结构组成的双向阻塞队列。与前面的阻塞队列相比它支持从两端插入和移出元素。以first结尾的表示从对头操作，以last结尾的表示从对尾操作。

# ThreadPoolExecutor 
```
ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) 
```
这几个核心参数的作用：
* corePoolSize 为线程池的基本大小。
* maximumPoolSize 为线程池最大线程大小。
* keepAliveTime 和 unit 则是线程空闲后的存活时间。
* workQueue 用于存放任务的阻塞队列。
* handler 当队列和最大线程池都满了之后的饱和策略。

### workQueue
* ArrayBlockingQueue：基于数组结构的有界阻塞队列，FIFO。
* LinkedBlockingQueue：基于链表结构的有界阻塞队列，FIFO。静态工厂方法**Executors.newFixedThreadPool，Executors.newSingleThreadExecutor**使用了这个队列。
* SynchronousQueue：不存储元素的阻塞队列，每个插入操作都必须等待一个移出操作，反之亦然。静态工厂方法**Executors.newCachedThreadPool**使用了这个队列。
* PriorityBlockingQueue：具有优先界别的阻塞队列。

# 线程池的拒绝策略（RejectedExecutionHandler）
* AbortPolicy策略：该策略会直接抛出异常，阻止系统正常工作。
* CallerRunsPolicy 策略：只要线程池未关闭，该策略直接在调用者线程中，运行当前的被丢弃的任务。
* DiscardOleddestPolicy策略：丢弃队列里面里面最近的一个任务，并执行当前任务。
* DiscardPolicy策略：该策略默默的丢弃无法处理的任务，不予任何处理。

# 五种线程池的使用场景
* newSingleThreadExecutor：一个单线程的线程池，可以用于需要保证顺序执行的场景，并且只有一个线程在执行。
* newFixedThreadPool：一个固定大小的线程池，可以用于已知并发压力的情况下，对线程数做限制。
* newCachedThreadPool：一个可以无限扩大的线程池，比较适合处理执行时间比较小的任务，或者负载比较轻的服务器。
* newScheduledThreadPool：可以延时启动，定时启动的线程池，适用于需要多个后台线程执行周期任务的场景。
* newWorkStealingPool：一个拥有多个任务队列的线程池，可以减少连接数，创建当前可用cpu数量的线程来并行执行。


