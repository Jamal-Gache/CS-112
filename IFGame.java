import java.util.*;

public class IFGame{

    private Location current;
    private final List<Item> inventory = new ArrayList<>();
    private boolean echo = false;

    public static void main(String[] args){
    	Banner.print();
    	
    	IFGame game = new IFGame();
        if(( args.length > 0) && (args[0].equalsIgnoreCase("-e") )){
            game.echo = true;
        }
        game.buildWorld();
        game.loop();
    }

    private void buildWorld(){

    	Location lounge=new Location("CS Lounge",
    	        "Couches line the walls, Ethernet cables drape everywhere, and a single table is\n"+
    	        "surrounded by laptops. Professor-grown plants soak in the glow of monitor light.");

    	    Location office=new Location("Professor Neller’s Office",
    	        "Stacks of board games, shelves of books, two vintage computers, and puzzles on\n"+
    	        "every flat surface. The faint smell of solder and pizza crust.");

    	    Location csRoom=new Location("CS Classroom (Glat 103)",
    	        "Rows of desktop workstations face a smartboard stuck on a Java stack trace.");

    	    Location mathRoom=new Location("Math Classroom (Glat 105)",
    	        "Sunlit chalkboards packed with Greek letters and partially erased proofs.");

    	    Location econRoom=new Location("Economics Classroom (Glat 107)",
    	        "Supply-and-demand graphs adorn the walls, and a labelled projector waits up front.");

    	    Location bathroom=new Location("Hall Bathroom",
    	        "Tile floors, echoing acoustics, and a hand dryer that could lift a quadcopter.");

        lounge.addExit("east",office);
        office.addExit("west",lounge);

        lounge.addExit("south",csRoom);
        csRoom.addExit("north",lounge);

        csRoom.addExit("east",mathRoom);
        mathRoom.addExit("west",csRoom);

        mathRoom.addExit("east",econRoom);
        econRoom.addExit("west",mathRoom);

        econRoom.addExit("south",bathroom);
        mathRoom.addExit("south",bathroom);
        csRoom.addExit("south",bathroom);

        
        bathroom.addExit("north",econRoom);

        //ITEMS---------------
        
        //cs lounge
        Item hdmi=new Item("hdmi-adapter",
            "A lone mini-DP-to-HDMI dongle—the most borrowed object on campus.");
        Item plant=new Item("basil-plant",
            "A fragrant basil plant nested in a 3-D-printed pot engraved ‘Hydro-Bot’.",
            false);
        lounge.addItem(hdmi);
        lounge.addItem(plant);

        //Professor Neller’s Office
        Item cube=new Item("puzzle-cube",
            "A well-worn 3×3 Rubik’s Cube scrambled into a checkerboard.");
        
        Item card=new Item("punch-card",
            "An 80-column punch card whose holes spell ‘HELLO WORLD’.");
        
        Item boardGame=new Item("board-game",
            "A deluxe copy of Ticket to Ride balanced atop a PDP-11 manual.",
            false);
        
        office.addItem(cube);
        office.addItem(card);
        office.addItem(boardGame);

        //CS classroom
        Item projector=new Item("projector",
            "A ceiling-mounted projector with an HDMI port begging for input.",
            false);
        Item marker=new Item("whiteboard-marker",
            "A brand-new dry-erase marker—rare loot indeed.");
        csRoom.addItem(projector);
        csRoom.addItem(marker);

        // Math Classroom
        Item chalk=new Item("chalk",
            "A pristine stick of chalk ready for ∀-quantifiers.");
        Item proofBoard=new Item("proof board",
            "The day’s theorem: ‘Every pizza is a pie.’ Proof left to the reader.",
            false);
        mathRoom.addItem(chalk);
        mathRoom.addItem(proofBoard);

        //Econ Classroom
        Item graphPaper=new Item("graph-paper",
            "Grid paper half-filled with shifting supply curves.");
        econRoom.addItem(graphPaper);

        //Bathroom
        Item towel=new Item("paper-towel",
            "A single brown paper towel—the last of its kind.");
        Item mirror=new Item("mirror",
            "You look debugging-ready.",
            false);
        bathroom.addItem(towel);
        bathroom.addItem(mirror);

        //Start 
        current=office;
        System.out.println(current.fullDescription());
    }

    private void loop(){
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.print("> ");
            if(!in.hasNextLine()){
                break;
            }
            String raw = in.nextLine();
            if(echo){
                System.out.println(raw);
            }
            if(raw.trim().isEmpty()){
                continue;
            }
            if(handle(raw.toLowerCase())){
                break;
            }
        }
        in.close();
    }

    private boolean handle(String cmd){

        if(isDir(cmd) || cmd.startsWith("go ")){
            String dir = cmd.startsWith("go ") ? cmd.substring(3).trim() : cmd;
            move(dirMap(dir));
            return false;
        }

        if(cmd.equals("i") || cmd.equals("inventory")){
            if(inventory.isEmpty()){
                System.out.println("You are carrying nothing.");
            }else{
                System.out.println("You have:");
                for(Item it : inventory){
                    System.out.println("  " + it.name());
                }
            }
            return false;
        }

        if(cmd.equals("look")){
            System.out.println(current.fullDescription());
            return false;
        }

        if(cmd.startsWith("x ") || cmd.startsWith("examine ")){
            String target = cmd.startsWith("x ") ? cmd.substring(2).trim() : cmd.substring(8).trim();
            Item it = hereOrInv(target);
            if(it == null){
                System.out.println("You don't see that here.");
            }else{
                System.out.println(it.description());
            }
            return false;
        }

        if(cmd.startsWith("get ") || cmd.startsWith("take ")){
            String target = cmd.substring(4).trim();
            Item it = current.removeItem(target);
            if(it == null){
                System.out.println("You don't see that here.");
            }else if(!it.gettable()){
                current.addItem(it);
                System.out.println("You can't pick that up.");
            }else{
                inventory.add(it);
                System.out.println("Taken.");
            }
            return false;
        }

        if(cmd.startsWith("drop ")){
            String target = cmd.substring(5).trim();
            Item it = removeInv(target);
            if(it == null){
                System.out.println("You don't have that.");
            }else{
                current.addItem(it);
                System.out.println("Dropped.");
            }
            return false;
        }

        if(cmd.equals("quit")){
            System.out.println("Good-bye.");
            return true;
        }

        System.out.println("I don't understand that.");
        return false;
    }

    private void move(String dir){
        if(dir == null){
            System.out.println("I don't understand where you want to go.");
            return;
        }
        Location next = current.getExit(dir);
        if(next == null){
            System.out.println("You can't go that way.");
            return;
        }
        current = next;
        System.out.println(current.briefOrFull());
    }

    private static final Map<String,String> DIRS = createDirMap();

    private static Map<String,String> createDirMap(){
        Map<String,String> m = new HashMap<>();
        m.put("n","north");
        m.put("north","north");
        m.put("s","south");
        m.put("south","south");
        m.put("e","east");
        m.put("east","east");
        m.put("w","west");
        m.put("west","west");
        m.put("u","up");
        m.put("up","up");
        m.put("d","down");
        m.put("down","down");
        return Collections.unmodifiableMap(m);
    }

    private boolean isDir(String w){
        return DIRS.containsKey(w);
    }

    private String dirMap(String w){
        return DIRS.get(w);
    }

    private Item hereOrInv(String name){
        Item it = current.getItem(name);
        if(it != null){
            return it;
        }
        for(Item i : inventory){
            if(i.name().equals(name)){
                return i;
            }
        }
        return null;
    }

    private Item removeInv(String name){
        Iterator<Item> it = inventory.iterator();
        while(it.hasNext()){
            Item i = it.next();
            if(i.name().equals(name)){
                it.remove();
                return i;
            }
        }
        return null;
    }
}
