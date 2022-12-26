package application.managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Jan Agh
 * @version 28.04.2022
 */
public class StateSaver<T>{
	
	private String filepath = null;
	private T object;
	
	private FileOutputStream output = null;
	private FileInputStream input = null;
	
	private ObjectOutputStream outObj = null;
	private ObjectInputStream inObj = null;
	
	public StateSaver(String filepath, T object) {
		this.filepath = filepath;
		this.object = object;
	}
	/**
	 * performs serialization
	 */
	public void serialize() throws IOException {
		if(this.output == null || this.outObj == null) this.writeStream();
		
		this.outObj.writeObject(this.object);
	}
	/**
	 * performs deserialization
	 */
	@SuppressWarnings("unchecked")
	public T deserialize() throws IOException, ClassNotFoundException {
		if(this.input == null || this.inObj == null) this.readStream();
		
		this.object = (T) this.inObj.readObject();
		
		return this.object;
	}
	/**
	 * opens a new stream for serialization to the given file
	 */
	private void writeStream() throws IOException {
		this.output = new FileOutputStream(this.filepath);
		this.outObj = new ObjectOutputStream(this.output);
	}
	/**
	 * opens a new stream for deserialization from the given file
	 */
	private void readStream() throws IOException, ClassNotFoundException {
		this.input = new FileInputStream(this.filepath);
		this.inObj = new ObjectInputStream(this.input);
	}
	/**
	 * closes the output stream
	 */
	public void closeWriteStream() throws IOException{
		this.outObj.close();
		this.output.close();
	}
	/**
	 * closes the input stream
	 */
	public void closeReadStream() throws IOException {
		this.inObj.close();
		this.input.close();
	}
	/**
	 * changes the object that will undergo serialization or deserialization
	 */
	public void setObject(T object) {
		this.object = object;
	}
}
