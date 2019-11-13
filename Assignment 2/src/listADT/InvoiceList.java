package listADT;

import java.util.Iterator;

public class InvoiceList<T> implements Iterable<T> {

	//Attributes
	
	private InvoiceNode<T> head;
	private int size;
	//private Comparator<T> comp;  Comparator object
	
	//Constructor
	
	public InvoiceList() { //Comparator<T> comp  in parameter list
		this.head = null;
		this.size = 0;
		//this.comp = comp;
	}
	
	//Methods
	
	public InvoiceNode<T> getHead() {
		return head;
	}

	public void setHead(InvoiceNode<T> head) {
		this.head = head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	//FINISH
	@Override
	public Iterator<T> iterator() {
		return new IteratorClass<T>();
	}
	
	class IteratorClass<E> implements Iterator<T>{
		int index = 0;
		
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public T next() {
			// TODO
			return null;
			
		}
		
	}
	
	
	//Method for adding items of type T
	public void add(T value) {
		// TODO
	}
	
}
