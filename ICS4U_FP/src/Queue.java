/* 
 * Date: June 16, 2022
 * Author: Beatrice Ge
 * Description: The Queue class: implements a Queue structure using an array of Order objects
 */

public class Queue {

	//instance variables
	private int front;
	private int end;
	private int maxSize;
	private Order[] orders;
	
	//constructors
	public Queue() 
	{
		front = -1;
		end = -1;
		orders = new Order[1];
		maxSize = orders.length;
	}
	
	public Queue(int n) 
	{
		front = -1; 
		end = -1;
		orders = new Order[n];
		maxSize = n;
	}
	
	//accessor methods
	/**Pre: None
	 * Post: Returns a String containing information about all orders in the queue
	 * Description: An overridden <.toString> method that returns a String with information about all 
	 *              orders in the queue */
	public String toString() {
		String info = "";
		if (!isEmpty()) {
			for (int i = front; i != end; i = (i + 1) % maxSize) {
				info += orders[i].toString() + "\n";
			}
			info += orders[end].toString();
		} else {
			info = "No orders";
		}
		return(info);
	}
	
	public Order front() {
		if (!isEmpty()) {
			return(orders[front]);
		} else {
			return(null);
		}
	}
	
	public int size() {
		if (!isEmpty()) {
			int diff = end - front + 1;
			if (diff <= 0) {
				diff = maxSize + diff;
			} 
			return(diff);
		}
		return(0);
	}
	
	//general getters
	public int getFront() {
		return front;
	}

	public int getEnd() {
		return end;
	}

	public int getMaxSize() {
		return maxSize;
	}
	
	//modifier methods
	public void enqueue(Order newOrder) {
		if (isFull()) {
			System.out.println("Max number of orders reached");
		} else if (isEmpty()) {
			front++;
			end++;
			orders[end] = newOrder;
		} else {
			end = (end + 1) % maxSize;
			orders[end] = newOrder;
		}
	}
	
	public Order dequeue() {
		if (!isEmpty()) {
			Order element = orders[front];
			if (front == end) {
				front = -1;
				end = -1;
			} else {
				front = (front + 1) % maxSize;
			}
			return(element);
		} else {
			return(null);
		}
	}
	
	public void makeEmpty() {
		front = -1;
		end = -1;
	}
	
	//checking methods
	public boolean isEmpty() {
		return(front == -1 && end == -1);
	}
	
	public boolean isFull() {
		return(front == ((end + 1) % maxSize));
	}

}
