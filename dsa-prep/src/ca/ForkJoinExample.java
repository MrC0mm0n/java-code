package ca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ForkJoinExample {

	public static void main(String[] args) {
		System.out.println(java.time.LocalTime.now());

		List<ParallelTask> tasks = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 10; i++)
			tasks.add(new ParallelTask(i, random.nextInt(10000) + 1000));

		Collection<ParallelTask> results = ForkJoinTask.invokeAll(tasks);
		for (ParallelTask task : results)
			System.out.println("-- id: " + task.id + ", isDone: " + task.isDone());

		System.out.println(java.time.LocalTime.now());
	}

}

class ParallelTask extends RecursiveAction {

	private static final long serialVersionUID = 3144476239898050670L;

	int id;

	int sleep;

	ParallelTask(int id, int sleep) {
		this.id = id;
		this.sleep = sleep;
	}

	@Override
	protected void compute() {
		System.out.println("-- start: id - " + id + ", sleep - " + sleep + ", time - " + java.time.LocalTime.now());
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("-- end: id - " + id + ", sleep - " + sleep + ", time - " + java.time.LocalTime.now());
	}

}