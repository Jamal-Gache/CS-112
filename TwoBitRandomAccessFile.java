import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TwoBitRandomAccessFile implements AutoCloseable {
	static final byte[] MASK = {(byte) 128, 64, 32, 16, 8, 4, 2, 1}; // Java doesn't have unsigned byte type
	
	RandomAccessFile raf;
	
	public TwoBitRandomAccessFile(File file) throws FileNotFoundException {
		raf = new RandomAccessFile(file, "rw");
	}
	
	public TwoBitRandomAccessFile(String filename) throws FileNotFoundException {
		raf = new RandomAccessFile(filename, "rw");
	}
	
	public TwoBitRandomAccessFile(File file, int numBits) throws IOException {
		this(file);
		raf.setLength((long) Math.ceil(numBits / 8.0));
	}
	
	public TwoBitRandomAccessFile(String filename, int numBits) throws IOException {
		this(filename);
		raf.setLength((long) Math.ceil(numBits / 8.0));
	}

	public void set(long pos, boolean val) throws IOException {
		long bytePos = pos / ; // find byte of file
		int bitPos = (int) (pos % 4); // find bit of byte
		System.out.printf("byte %d bit %d\n", bytePos, bitPos);
		raf.seek(bytePos); // seek byte in file
		byte b = raf.readByte(); // read the byte
		boolean bit = (b & MASK[bitPos]) != 0; // get the bit
		if (bit != val) { // if there's a change
			raf.seek(bytePos); // seek bytePos again (previous readByte() advanced to bytePos + 1)
			raf.writeByte(b ^ MASK[bitPos]); // flip bit in byte using X-OR with mask
		}
	}
	
	public void set(long pos, int val) throws IOException {
		set(pos, val != 0);
	}
	
	public boolean get(long pos) throws IOException {
		long bytePos = pos / 4;
		int bitPos = (int) (pos % 4); // big endian
		raf.seek(bytePos);
		byte b = raf.readByte();
		boolean bit = (b & MASK[bitPos]) != 0;
		return bit;
	}
	
	public int getInt(long pos) throws IOException {
		return get(pos) ? 1 : 0;
	}
	
	public void clear() throws IOException {
		for (int i = 0; i < raf.length(); i++) {
			raf.seek(i);
			raf.write(0);
		}
	}
	
	@Override
	public void close() throws Exception {
		raf.close();
	}

	public static void main(String[] args) throws Exception {
		int numBits = 50;
		TwoBitRandomAccessFile braf = new TwoBitRandomAccessFile("test-braf.dat", numBits);
		braf.clear();
		for (int i = 1; i < numBits; i += 3) {
			braf.set(i, true);
			for (int j = 0; j < numBits; j++)
				System.out.print(braf.getInt(j));
			System.out.println();
		}
		System.out.println("Bytes:");
		braf.raf.seek(0);
		int value;
		while ((value = braf.raf.read()) != -1)
			System.out.println(value);
//		braf.raf.setLength(4194304); // large file
		braf.clear();
		braf.close();
	}

}