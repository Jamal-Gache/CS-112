import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class MakeAJoke {
    private ArrayList<String> jokes;
    private Random random;

    public MakeAJoke() {
        jokes = new ArrayList<>();
        random = new Random();
    }
                                             
	public boolean loadJokes(String filename) {
	    jokes.clear();
	    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
	        String line;
	        StringBuilder current = null;
	
	        while ((line = in.readLine()) != null) {
	            if ("joke:".equals(line)) {
	                if (current != null) {
	                    addJoke(current.toString());
	                }
	                current = new StringBuilder();
	            } else if (current != null) {
	                current.append(line).append("\n");
	            }
	        }
	        if (current != null) {
	            addJoke(current.toString());
	        }
	        return true;
	    } catch (IOException e) {
	        return false;
	    }
	}

	public String getJoke() {
	    if (jokes.isEmpty()) {
	        return "";
	    }
	    int idx = random.nextInt(jokes.size());
	    return jokes.get(idx);
	}

	public String getJoke(int index) {
	    return jokes.get(index);
	}

	public void addJoke(String joke) {
        if (!joke.endsWith("\n")) {
            joke = joke + "\n";
        }
        jokes.add(joke);
    }

   
    public int size() {
        return jokes.size();
    }

    public boolean saveJokes(String filename) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            for (String joke : jokes) {
                out.write("joke:");
                out.newLine();
                out.write(joke);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        MakeAJoke mj = new MakeAJoke();
        if (mj.loadJokes("jokes.txt")) {
            System.out.print(mj.getJoke());
        } else {
            System.err.println("Failed to load jokes from jokes.txt");
        }
    }
}
