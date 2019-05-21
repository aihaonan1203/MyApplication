package com.gaotai.baselib.util;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup {

	// thread pool closed
	private boolean isClosed = false;
	private LinkedList<Runnable> workQueue;

	/**
	 * created and started for work thread
	 * 
	 * @param poolSize
	 *            the thread size in pool
	 */
	public ThreadPool(String poolName, int poolSize) {
		super("GaoTaiThreadPool");
		setDaemon(true);
		workQueue = new LinkedList<Runnable>();
		for (int i = 0; i < poolSize; i++) {
			new WorkThread(poolName, i).start();
		}
	}

	/**
	 * Work queue to add a new task, the worker thread to execute the office
	 * 
	 * @param task
	 */
	public synchronized void execute(Runnable task) {
		if (isClosed) {
			throw new IllegalStateException();
		}
		if (task != null) {
			// Adding a task to the queue
			workQueue.add(task);
			// Wake one being getTask () method to be the work of the task
			// threads
			notify();
		}
	}

	/**
	 * Removed from the work queue a task, the worker thread will call this
	 * method
	 * 
	 * @param threadid
	 * @return
	 * @throws InterruptedException
	 */
	private synchronized Runnable getTask(int threadid)
			throws InterruptedException {
		while (workQueue.size() == 0) {
			if (isClosed)
				return null;
			// If no work queue task waits for the task
			wait();
		}
		// Inverse return the first element in the queue, and removed from the
		// queue
		return (Runnable) workQueue.removeFirst();
	}

	/**
	 * close thread pool
	 */
	public synchronized void closePool() {
		if (!isClosed) {
			// After waiting worker threads
			waitFinish();
			isClosed = true;
			// Empty the work queue and interrupt the thread pool thread for all
			// the work,
			// this method inherited from class ThreadGroup
			workQueue.clear();
			interrupt();
		}
	}

	/**
	 * Waiting for a worker to perform all tasks completed
	 */
	public void waitFinish() {
		synchronized (this) {
			isClosed = true;
			// Wake up all still getTask () method to wait for the task worker
			// thread
			notifyAll();
		}
		// Return of active threads in this thread group estimates.
		Thread[] threads = new Thread[activeCount()];
		// enumerate () method inherited from ThreadGroup class,
		// according to the estimated value of active threads in the thread
		// group to get
		// all currently active worker threads
		int count = enumerate(threads);
		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Inner classes, the worker thread is responsible for removing the task
	 * from the work queue and executes
	 * 
	 * @author huwf
	 * 
	 */
	private class WorkThread extends Thread {
		private int id;

		public WorkThread(String poolName, int id) {
			// Parent class constructor, the thread is added to the current
			// ThreadPool thread group
			super(ThreadPool.this, poolName + "-" + id);
			this.id = id;
		}

		public void run() {
			// isInterrupted () method inherited from the Thread class,
			// to determine whether the thread is interrupted
			while (!isInterrupted()) {
				Runnable task = null;
				try {
					task = getTask(id);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				// If getTask () returns null or thread getTask () is
				// interrupted, then the end of this thread
				if (task == null)
					return;

				try {
					// run task;
					task.run();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

}
