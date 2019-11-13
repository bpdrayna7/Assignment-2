package listADT;

public class InvoiceNode<T> {

	private InvoiceNode<T> next;
	private T value;
	
	public InvoiceNode(T value) {
		this.value = value;
		next = null;
	}
	
	//Returns the next node for this node
	public InvoiceNode<T> getNext(){
		return next;
	}
	
	//Sets the next node for this node to the input node
	public void setNext(InvoiceNode<T> next) {
		this.next = next; 
	}
	
	//Returns the value for this node
	public T getValue() {
		return value;
	}
	
	//Sets the value for this node
	public void setValue(T value) {
		this.value = value;
	}
	
}
