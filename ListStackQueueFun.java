import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.print.DocFlavor.INPUT_STREAM;

public class ListStackQueueFun {

	public static void main(String[] args) throws InterruptedException {

		// Day 1 reading: Sections 20.1 - 20.4

		// Writing good software involves good data structure choices.
		// If there is no standard implementation (or if the standard
		// implementation doesn't suit) --> DIY (CS 216)
		// Otherwise, there can be more than one implementation of data
		// structure (e.g. list, map) and the best choice is made by one
		// who understands their differences (again, CS 216).
		// In this chapter, we gain a surface-level understanding of
		// Java's offerings.

		// Java's Collections framework has two broad classifications of
		// data structures (i.e. "containers"):
		// - a collection of elements (this chapter)
		// - an associative mapping of elements from keys to values
		// (next chapter)

		// Collections:
		// Sets store a group of nonduplicate elements.
		// Lists store an ordered collection of elements.
		// Stacks store objects that are processed in a last-in,
		// first-out (LIFO) fashion.
		// Queues store objects that are processed in a first-in,
		// first-out (FIFO) fashion.
		// PriorityQueues store objects that are processed in the order
		// of their priorities.

		// Collection operation - similar common set operation
		// addAll - set union
		// removeAll - set difference
		// retainAll - set intersection

		// ArrayLists are Collections that are not sets (can have 
		//   duplicates)
		Collection<Integer> c1 = new ArrayList<>(
				Arrays.asList(new Integer[] { 2, 2, 1 }));
		Collection<Integer> c2 = new ArrayList<>(
				Arrays.asList(new Integer[] { 2, 3 }));
		// set union? yes if no duplicates. no otherwise.
		Collection<Integer> c3 = new ArrayList<>(c1);
		c3.addAll(c2);
		String className = c1.getClass().getSimpleName();
		System.out.printf("%s %s addAll %s = %s\n", className, c1, c2,
				c3);
		// set difference? yes if no duplicates. no otherwise.
		c3.clear();
		c3.addAll(c1);
		c3.removeAll(c2);
		System.out.printf("%s %s removeAll %s = %s\n", className, c1,
				c2, c3);
		// set intersection? yes if no duplicates. no otherwise.
		c3.clear();
		c3.addAll(c1);
		c3.retainAll(c2);
		System.out.printf("%s %s retainAll %s = %s\n", className, c1,
				c2, c3);

		// Iterator:
		// - hasNext(): boolean
		// - next(): <E>
		// - remove(): void - remove previous element returned by next()
		// not always implemented; can implement to throw
		// java.lang.UnsupportedOperationException
		c3 = new ArrayList<>(c1);
		c3.addAll(c2);
		Iterator<Integer> iterator = c3.iterator();
		System.out.println(
				"Iterator used to display all values and remove even values:");
		while (iterator.hasNext()) {
			int value = iterator.next();
			System.out.println(value);
			if (value % 2 == 0) // remove even values - "I can't even!"
				iterator.remove();
		}
		System.out.println(c3);

		// Interface Iterable<E> means a class has an iterator() method 
		// that returns an Iterator object and can be used with a 
		// for-each loop. All Collection classes are Iterable.

		// An ArrayList is like a dynamically resizable array
		// - has a capacity that can be beyond size() (trimToSize() for
		//   space efficiency)
		// - good for random access
		// - poor for insertion (must shift all elements to different
		//   indices)

		// A LinkedList is a doubly-linked list with links forward and
		//   backward between nodes
		// - poor for random access
		// - good for insertion

		final int SIZE = 100000;
		
		// ArrayList demo:
		ArrayList<Integer> aList = new ArrayList<>();
		for (int i = 0; i < SIZE; i++)
			aList.add(i);

		// Random access
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < SIZE / 2; i++) {
			int value = aList.get((int) (SIZE * Math.random()));
			value = value + 1;
		}
		long endTime = System.currentTimeMillis();
		System.out.printf("ArrayList random access: %d ms\n",
				endTime - startTime);

		// Right rotate half of elements, one at a time
		startTime = System.currentTimeMillis();
		for (int i = 0; i < SIZE / 2; i++) {
			int value = aList.remove(aList.size() - 1);
			aList.add(0, value);
		}
		endTime = System.currentTimeMillis();
		System.out.printf("ArrayList rotation: %d ms\n",
				endTime - startTime);

		// LinkedList demo:

		LinkedList<Integer> lList = new LinkedList<>();
		for (int i = 0; i < SIZE; i++)
			lList.add(i);

		// Random access
		startTime = System.currentTimeMillis();
		for (int i = 0; i < SIZE / 2; i++) {
			int value = lList.get((int) (SIZE * Math.random()));
			value = value + 1;
		}
		endTime = System.currentTimeMillis();
		System.out.printf("LinkedList random access: %d ms\n",
				endTime - startTime);
		// NOTE: This is why you should use an iterator/for-each loop
		//   for a LinkedList rather than a for loop with .get(index) 
		//   operations.

		// Right rotate half of elements, one at a time
		startTime = System.currentTimeMillis();
		for (int i = 0; i < SIZE / 2; i++) {
			lList.addFirst(lList.removeLast());
		}
		endTime = System.currentTimeMillis();
		System.out.printf("LinkedList rotation: %d ms\n",
				endTime - startTime);

		// So both ArrayLists and LinkedLists are AbstractLists, but with 
		// very different implementations and very different performance
		// for different tasks.
		// Conclusion: You should know _how_ data structures are 
		// implemented (CS 216) to make proper application of them.

		System.out.println("\n\nExercises:");
		
		// Set up random data for exercises:
		final int INIT_SEED = 0;
		Random random = new Random(INIT_SEED);
		int size = 7;
		System.out.println("Data:");
		ArrayList<ArrayList<Integer>> data 
			= new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < size; i++) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int j = 0; j < size; j++)
				list.add(random.nextInt(size));
			data.add(list);
			System.out.printf("[%d]: %s\n", i, list);
		}
		
		System.out.println(
				"(1.1) Print out ArrayLists formed with Collection operations without\n"
				 + "      modifying the original data:");
		System.out.println(
				"(1.1.1) Elements of data.get(0) that also occur in data.get(1):");
		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.addAll(data.get(0));
		answer.retainAll(data.get(1));
		System.out.println(answer);
		
		System.out.println(
				"(1.1.2) Elements of data.get(2) together with elements of data.get(3):");
		answer.clear();
		answer.addAll(data.get(2));
		answer.addAll(data.get(3));
		System.out.println(answer);

		
		System.out.println(
				"(1.1.3) Elements of data.get(4) without those that occur in data.get(5):");
		answer.clear();
		answer.addAll(data.get(4));
		answer.removeAll(data.get(5));
		System.out.println(answer);

		
		// Iterable ListSums definition for next exercise:
		// iterator() returns an Iterator that allows one to iterate
		//   through the sums of data.get(i) for successive i.
		class ListSums implements Iterable<Integer> {
			@Override
			// An iterator through sums of data ArrayLists
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					int i = 0; // current list index

					{
						// One can initialize as with a constructor here.
						// However, anonymous classes have no constructors.
					}
					
					@Override
					// return whether or not there is a next list sum
					public boolean hasNext() {
						return i < data.size();
					}

					@Override
					// return the sum of the next list
					public Integer next() { 
						// compute sum of list
						int sum = 0;
						for (int value : data.get(i))
							sum += value;
						++i; // advance list index
						return sum;
					}
				};
			}
		}
		System.out.println("(1.2) Use the Iterator of class ListSums to print out the sum of each\n"
				+ "      data ArrayList one per line.");
		Iterator<Integer> iter = new ListSums().iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		System.out.println(
				"(1.3) Define and use an Iterable to iterate through the even-valued\n" 
				+ "      integers of data.get(6), printing each on a separate line:");
		class Evens implements Iterable<Integer> {
			@Override
			// An iterator through sums of data ArrayLists
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					ArrayList<Integer> list = data.get(6);
					int i = 0; // current list index

					{
						while(i < list.size() && (list.get(i) % 2) != 0) {
							i++;
						}
					}
					
					@Override
					// return whether or not there is a next list sum
					public boolean hasNext() {
						return i < list.size();
					}

					@Override
					// return the sum of the next list
					public Integer next() { 
						int value = list.get(i++);
						while(i < list.size() && (list.get(i) % 2) != 0) {
							i++;
						}
						return value;
					}
				}; 
			}
		}
		iter = new ListSums().iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		System.out.println("(1.4) Uncomment the true statements:");
		// TODO
		System.out.println("An ArrayList is better than a LinkedList for random access.");
		//System.out.println("An ArrayList is better than a LinkedList for insertion.");
		//System.out.println("A LinkedList is better than an ArrayList for random access.");
		System.out.println("A LinkedList is better than an ArrayList for insertion.");
		
		// Checking whether or not data was modified:
		try {
			random.setSeed(INIT_SEED);
			for (int i = 0; i < size; i++) {
				ArrayList<Integer> list = data.get(i);
				if (list.size() != size)
					throw new Exception("data.get(" + i + ") modified - has the wrong size");
				for (int j = 0; j < size; j++)
					if (list.get(j) != random.nextInt(size))
						throw new Exception("data.get(" + i + ") modified - has modified value");
			}
			System.out.println("OK - data unmodified as specified");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\n");

		
		// Day 2 reading: 20.5-20.7

		// Comparator - a class for allowing comparison, searching, 
		//   sorting, etc., for classes that do not implement the 
		//   Comparable interface. See MahFravrit exercise example test 
		//   code.

		// The Collection class has a number of useful methods for lists and general
		// collections:

		aList.clear();
		aList.addAll(Arrays.asList(
				new Integer[] { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8 }));
		System.out.println(aList);
		Collections.sort(aList);
		System.out.println("Sorted: " + aList);
		System.out.println("Binary search for 1: "
				+ Collections.binarySearch(aList, 1));
		// The value returned when the search is successful is an
		// index (not necessarily unique) where the key was found.
		System.out.println("Binary search for 0: "
				+ Collections.binarySearch(aList, 0));
		// The value returned when an search is unsuccessful is 
		// (-<insertion index> - 1) where <insertion index> is 
		// the index where the key could be inserted to maintain sorted 
		// order.
		Collections.reverse(aList);
		System.out.println("Reversed: " + aList);
		Collections.shuffle(aList);
		System.out.println("Shuffled: " + aList);
		ArrayList<Integer> aList2 = new ArrayList<>(
				Arrays.asList(new Integer[] { 1, 2, 3 }));
		Collections.copy(aList, aList2); // dest, src
		System.out.println("Copied onto with " + aList2 + ": " + aList);
		Collections.fill(aList, 42);
		System.out.println("Filled with 42: " + aList);
		// For general collections:
		Collection<Integer> coll = aList2;
		System.out.println(
				"Max of " + aList2 + ": " + Collections.max(coll));
		System.out.println(
				"Min of " + aList2 + ": " + Collections.min(coll));
		System.out.println(aList + " and " + coll + " disjoint? "
				+ Collections.disjoint(aList, coll));
		Collection<Integer> coll2 = aList;
		System.out.println("Frequency of 42 in " + coll2 + " is "
				+ Collections.frequency(coll2, 42));
		List<Integer> nines = Collections.nCopies(5, 9);
		System.out
				.println("nCopies creates an immutable List: " + nines);

		
		System.out.println("\n\nExercises:");
		
		System.out.println(
				"(2.1) Without modifying original data, print the sorted data of\n"
				+ "      data.get(0):");
		
		answer = new ArrayList<Integer>(data.get(0));
		Collections.sort(answer);
		System.out.println(answer);
		
		System.out.println(
				"(2.2) On separate lines print out the result of performing\n"
				+ "      binary search of 0 through " + (size - 1) + " in the previous answer\n"
				+ "      using the appropriate method of Collections.");
		
		for(int i = 0; i < size; i++) {
			System.out.println(Collections.binarySearch(answer, i));
		}
		
		System.out.println(
				"(2.3) Do the same as in (2.2), except when value <v> is...:\n"
				+ "      ... found, print \"<v> found at index <i_found>\", or\n"
				+ "      ... not found, print \"<v> could be inserted at <i_insert>\"\n"
				+ "      where <i_found> is an index where a value was found, and\n"
				+ "      where <i_insert> is an index where a value could be inserted\n"
				+ "      to maintain sorted order.");
		for(int i = 0; i < size; i++) {
			int idx = Collections.binarySearch(answer, i);
			System.out.println( idx >= 0 
					? i + "found at index" + idx
					: i + "could be inserted at" + -(idx + 1));
		}
		
		System.out.println("(2.4) Using the appropriate Collections method, compute and print the\n"
				+ "      maximum value of data.get(1):");
		System.out.println(Collections.max(data.get(1)));
		
		System.out.println("(2.5) Using the appropriate Collections method, compute and print the\n"
				+ "      total frequency of 0 in all data ArrayLists:");
		int freq = 0;
		for(ArrayList<Integer> list: data) {
			freq += Collections.frequency(list, 0);
		}
		System.out.println(freq);
		
		// Checking whether or not data was modified:
		try {
			random.setSeed(INIT_SEED);
			for (int i = 0; i < size; i++) {
				ArrayList<Integer> list = data.get(i);
				if (list.size() != size)
					throw new Exception("data.get(" + i + ") modified - has the wrong size");
				for (int j = 0; j < size; j++)
					if (list.get(j) != random.nextInt(size))
						throw new Exception("data.get(" + i + ") modified - has modified value");
			}
			System.out.println("OK - data unmodified as specified");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\n");
		
		
		
		// Day 3 reading: 20.8-20.10

		// A Vector is like a synchronized (thread-safe) ArrayList.

		// LIFO Stack extends Vector with push, pop, peek, empty, search
		//   See ObjectFun.java, GenericShuffler.java, 
		//   DepthFirstSearcher.java, MyStack, etc. for prior examples.

		// FIFO Queue is an interface implemented by LinkedList
		//   See BreadthFirstSearcher.java for example.

		// LinkedList also implements the Deque (Double-Ended QUEue) 
		//   interface and is a _doubly_ linked list with next and 
		//   previous pointers between nodes. This allows efficient 
		//   removal at _both_ ends.
		LinkedList<Integer> ll = new LinkedList<>();
		ll.addFirst(1);
		System.out.println("addFirst(1): " + ll);
		ll.addLast(2);
		System.out.println("addLast(2): " + ll);
		ll.addFirst(3);
		System.out.println("addFirst(3): " + ll);
		System.out.println("getFirst(): " + ll.getFirst()); 
		// like singly-linked list queue peek
		System.out.println("getLast(): " + ll.getLast()); 
		// like stack peek
		ll.removeLast();
		System.out.println("removeLast(): " + ll);
		ll.removeFirst();
		System.out.println("removeFirst(): " + ll);
		ll.removeLast();
		System.out.println("removeLast(): " + ll);

		// Efficient Deque behavior comes at a memory cost for all of
		// the next, previous links

		// Additional Priority Queue example: Finding nearest points

		// Sample point class:

		class Point2D {
			double x, y;

			public Point2D(double x, double y) {
				this.x = x;
				this.y = y;
			}

			public double distanceTo(Point2D other) { 
				// Euclidean distance to other point
				double dx = other.x - x;
				double dy = other.y - y;
				return Math.sqrt(dx * dx + dy * dy);
			}

			@Override
			public String toString() {
				return "(" + x + ", " + y + ")";
			}
		}

		// Create target point that we find points closest to
		final Point2D targetPt = new Point2D(Math.random(),
				Math.random());
		System.out.println("Target point: " + targetPt);

		// Generate a number of points and insert into a Priority Queue
		// that returns closest points
		final int NUM_POINTS = 100;
		// Create priority queue that returns comparatively closer points
		//   as priority
		PriorityQueue<Point2D> priorityQueue = new PriorityQueue<Point2D>(
				NUM_POINTS, new Comparator<Point2D>() {
					public int compare(Point2D p1, Point2D p2) {
						return (int) Math.signum(targetPt.distanceTo(p1)
								- targetPt.distanceTo(p2));
					}
				});
		// Generate and add random points to priority queue
		for (int i = 0; i < NUM_POINTS; i++)
			priorityQueue
					.add(new Point2D(Math.random(), Math.random()));
		// Demonstrate removal in order of closest points
		System.out.println("Random points from closest to farthest:");
		while (!priorityQueue.isEmpty())
			System.out.println(priorityQueue.poll());

		
		System.out.println("\n\nExercises:");
		
		System.out.println("(3.1) Uncomment the true statement:");
		// TODO
		//System.out.println("For multithreaded access and use, use an ArrayList.");
		System.out.println("For multithreaded access and use, use a Vector.");

		System.out.println("(3.2) Using an enhanced for loop (i.e. for-each loop), push the given String\n"
				+ "      names onto a Stack. Then, create a loop that, while the Stack is\n"
				+ "      not empty, pops and prints each String:");
		String[] names = {"Ann", "Bob", "Cal", "Deb"};
		Stack<String> nameStack = new Stack<String>();
		for(String name: names) {
			nameStack.push(name);
		}
		while(!nameStack.isEmpty()) {
			System.out.println(nameStack.pop());
		}
		
		System.out.println("(3.3) Do the same as in exercise (3.2), except use a Queue with\n"
				+ "      methods offer and poll:");
		Queue<String> nameQueue = new LinkedList<String>();
		for(String name: names) {
			nameQueue.offer(name);
		}
		while(!nameQueue.isEmpty()) {
			System.out.println(nameQueue.poll());
		}
		
		System.out.println("(3.4) Do the same as in exercise (3.2), except use a Deque with\n"
				+ "      methods addLast and removeLast:");
		Deque<String> nameDeque = new LinkedList<>();
		for(String name: names) {
			nameDeque.addLast(name);
		}
		while(!nameDeque.isEmpty()) {
			System.out.println(nameDeque.removeLast());
		}
		
		System.out.println("(3.5) Do the same as in exercise (3.2), except use a Deque with\n"
				+ "      methods addLast and removeFirst:");
		nameDeque = new LinkedList<>();
		for(String name: names) {
			nameDeque.addLast(name);
		}
		while(!nameDeque.isEmpty()) {
			System.out.println(nameDeque.removeFirst());
		}
		
		
		System.out.println("(3.6) Create an Integer PriorityQueue, offer the values of data.get(2),\n"
				+ "      and poll all values of the PriorityQueue, printing each on a\n"
				+ "      separate line:");
		
		Queue<Integer> pQueue = new PriorityQueue<Integer>();
		for(int value: data.get(2)) {
			pQueue.offer(value);
		}
		while(!pQueue.isEmpty()) {
			System.out.println(pQueue.poll());
		}
		
		System.out.println("(3.7) Do the same as in (3.6), but specifying reverse order for the\n"
				+ "      PriorityQueue by constructing it with an appropriate\n"
				+ "      Comparator:");
		pQueue = new PriorityQueue<Integer>(size, Collections.reverseOrder());
		for(int value: data.get(2)) {
			pQueue.offer(value);
		}
		while(!pQueue.isEmpty()) {
			System.out.println(pQueue.poll());
		}
		
	}
}