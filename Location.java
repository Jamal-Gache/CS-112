import java.util.*;

public class Location {

    private final String name;
    private final String description;
    private final Map<String, Location> exits = new HashMap<>();
    private final List<Item> items = new ArrayList<>();
    private boolean visited = false;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //EXITS
    public void addExit(String dir, Location target){ 
    	exits.put(dir, target); 
    }
    public Location getExit(String dir){
    	return exits.get(dir);
    }

    //ITEMS
    public void addItem(Item i){
    	items.add(i);
    }
    public Item removeItem(String name)  {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.name().equals(name)){ 
            	it.remove(); 
            	return i; 
            }}
        return null;
    }
    public Item getItem(String name) {
        for (Item i : items) {
        	if (i.name().equals(name)) {
        		return i;
        	}}
        return null;
    }

    //DESCRIPTIONS
    public String fullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(name.toUpperCase() + "\n-----------").append("\n").append(description).append("\n\n");
        listItems(sb);
        listExits(sb);
        visited = true;
        return sb.toString();
    }

    private String briefDescription() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("You are in ").append(name).append(".\n\n");
	    listItems(sb);
	    return sb.toString();
	}

	public String briefOrFull() {
        return visited ? briefDescription() : fullDescription();
    }

    private void listItems(StringBuilder sb) {
        if (!items.isEmpty()) {
            sb.append("You see:");
            for (Item i : items) sb.append(" \n").append("- " + i.name());
            sb.append("\n\n");
        }
    }

    private void listExits(StringBuilder sb) {
        sb.append("Exits:");
        for (String dir : exits.keySet()) sb.append(" \n").append("- " + dir);
        sb.append("\n\n");
    }
}
