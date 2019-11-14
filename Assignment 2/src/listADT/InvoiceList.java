package listADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

import entities.Invoice;

public class InvoiceList implements Iterable<Invoice> {

	//Attributes
	private InvoiceNode<Invoice> head;
	private int size;
	private TotalComparator comp;
	
	//Constructor
	public InvoiceList(TotalComparator comp) {
		this.head = null;
		this.size = 0;
		this.comp = comp;
	}
	
	//Methods
	public InvoiceNode<Invoice> getHead() {
		return head;
	}

	public void setHead(InvoiceNode<Invoice> head) {
		this.head = head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public Iterator<Invoice> iterator() {
		return new IteratorClass();
	}
	
	class IteratorClass implements Iterator<Invoice>{
		private InvoiceNode<Invoice> currentNode = head;
		@Override
		public boolean hasNext() {
			if(currentNode.getNext() != null) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public Invoice next() {
			if(hasNext()) {
				Invoice invoice = currentNode.getValue(); 
				currentNode = currentNode.getNext();
				return invoice;
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
	
	//Method for adding items
	public void add(Invoice item) {
		if(head == null) {
			head = new InvoiceNode<Invoice>(item);
			size++;
		}
		else if(size == 1) {
			if(comp.compare(head.getValue(), item) > 0) {
				InvoiceNode<Invoice> next = head;
				head = new InvoiceNode<Invoice>(item);
				head.setNext(next);
			}
			else {
				head.setNext(new InvoiceNode<Invoice>(item));
			}
			size++;
		}
		else {
			if(comp.compare(head.getValue(), item) > 0) {
				InvoiceNode<Invoice> next = head;
				head = new InvoiceNode<Invoice>(item);
				head.setNext(next);
			}
			else if(comp.compare(head.getNext().getValue(), item) > 0) {
				InvoiceNode<Invoice> current = new InvoiceNode<Invoice>(item);
				InvoiceNode<Invoice> next = head.getNext();
				head.setNext(current);
				current.setNext(next);
			}
			else {
				InvoiceNode<Invoice> current = head;
				while(comp.compare(current.getValue(), current.getNext().getValue()) < 0) {
					current = current.getNext();
				}
				InvoiceNode<Invoice> next = current.getNext();
				current.setNext(new InvoiceNode<Invoice>(item));
				current.getNext().setNext(next);
			}
			size++;
		}
	}
	
	//Method for removing items at index
	public void remove(int index) {
		if(index<0 || index>size-1) {
			throw new IndexOutOfBoundsException();
		}
		else if(index==0) {
			head = null;
			size--;
		}
		else {
			InvoiceNode<Invoice> previous = head;
			for(int i=0; i<index-1; i++) {
				previous = previous.getNext();
			}
			previous.setNext(previous.getNext().getNext());
			size--;
		}
	}
}
