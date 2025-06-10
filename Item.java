public class Item {

	private final String name;
	private final String description;
	private final boolean gettable;

	public Item(String name, String description) {
		this(name, description, true);
	}

	public Item(String name, String description, boolean gettable) {
		this.name = name;
		this.description = description;
		this.gettable = gettable;
	}

	public String name(){
		return name; 
	}
	public String description(){ 
		return description; 
	}
	public boolean gettable(){ 
		return gettable; 
	}
}
